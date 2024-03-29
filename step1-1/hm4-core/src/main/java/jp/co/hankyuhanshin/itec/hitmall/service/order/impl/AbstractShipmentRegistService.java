/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ShipmentRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ShipmentCompleteMailBatchRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ShipmentRegistService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static jp.co.hankyuhanshin.itec.hitmall.service.order.ShipmentRegistService.MSGCD_SALES_AUTHORI_ERROR;

/**
 * 出荷処理抽象クラス<br/>
 * 複数件の受注に対して出荷処理を行う為にトランザクションを設定<br/>
 * <br/>
 * 出荷処理中にエラーが発生した場合は、このクラスでキャッチして、引数のメッセージリストへセットする<br/>
 *
 * @author yamaguchi
 * @version $Revision: 1.5 $
 */
@Component
public abstract class AbstractShipmentRegistService extends AbstractShopService {

    /**
     * ロガー
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractShipmentRegistService.class);

    /**
     * 出荷登録サービス
     */
    private final ShipmentRegistService shipmentRegistService;

    /**
     * 出荷完了メール送信バッチタスク登録サービス
     */
    private final ShipmentCompleteMailBatchRegistService mailBatchRegistService;

    @Autowired
    public AbstractShipmentRegistService(ShipmentRegistService shipmentRegistService,
                                         ShipmentCompleteMailBatchRegistService mailBatchRegistService) {

        this.shipmentRegistService = shipmentRegistService;
        this.mailBatchRegistService = mailBatchRegistService;
    }

    /**
     * 出荷登録処理<br/>
     *
     * @param shipmentRegistDto   出荷登録DTO
     * @param checkMessageDtoList チェックメッセージDtoリスト（エラー発生時にメッセージがセットされる）
     * @param fatalCode           まるめ込みメッセージコード（アプリケーションエラー以外のエラーが発生したした場合にセットするエラーコード）
     * @return 処理件数
     */
    protected int registor(ShipmentRegistDto shipmentRegistDto,
                           List<CheckMessageDto> checkMessageDtoList,
                           String fatalCode,
                           String administratorName) {
        try {
            // 出荷登録処理実行
            CheckMessageDto checkMessageDto = shipmentRegist(shipmentRegistDto, administratorName);
            if (checkMessageDto != null) {
                // オーソリ通信エラーメッセージ（GMOから返却されたエラーコードに対応するメッセージ）をログへ出力する
                LOGGER.error(shipmentRegistDto.getOrderCode() + ":" + checkMessageDto.getMessage());
                // オーソリ通信エラー発生時、請求決済エラーが発生した旨をメッセージに追加する
                // なお、請求決済エラーとなった出荷データは処理件数には含めないものとする
                CheckMessageDto authoriErrMessageDto = toCheckMessageDto(MSGCD_SALES_AUTHORI_ERROR,
                                                                         new Object[] {shipmentRegistDto.getOrderCode()},
                                                                         true
                                                                        );
                checkMessageDtoList.add(authoriErrMessageDto);
                return 0;
            }
            return 1;
        } catch (AppLevelListException ale) {
            LOGGER.error("例外処理が発生しました", ale);
            setErrorForCheckMessageDtoList(checkMessageDtoList, ale);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            CheckMessageDto checkMessageDto =
                            toCheckMessageDtoFatal(fatalCode, new Object[] {shipmentRegistDto.getOrderCode()}, e);
            checkMessageDtoList.add(checkMessageDto);
        }
        return 0;
    }

    /**
     * 出荷登録サービス実行<br/>
     * <ul>1件コミットの為のメソッド</ul>
     *
     * @param shipmentRegistDto 出荷登録DTO
     * @return チェックメッセージDTO
     */
    protected CheckMessageDto shipmentRegist(ShipmentRegistDto shipmentRegistDto, String administratorName) {
        return shipmentRegistService.execute(shipmentRegistDto, administratorName);
    }

    /**
     * 出荷完了メール送信バッチタスク登録サービス実行<br/>
     *
     * @param shipmentRegistDtoList 出荷登録DTO
     * @param checkMessageDtoList   チェックメッセージDtoリスト（エラー発生時にメッセージがセットされる）
     * @param fatalCode             まるめ込みメッセージコード（アプリケーションエラー以外のエラーが発生したした場合にセットするエラーコード）
     * @return 1=処理成功
     */
    protected int mailBatchRegist(List<ShipmentRegistDto> shipmentRegistDtoList,
                                  List<CheckMessageDto> checkMessageDtoList,
                                  String fatalCode) {
        try {
            mailBatchRegistService.execute(shipmentRegistDtoList);
            return 1;
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            toCheckMessageDtoFatal(fatalCode, null, e);
        }
        return 0;
    }

    /**
     * エラー内容メッセージリストへ追加<br/>
     *
     * @param checkMessageDtoList チェックメッセージDtoリスト
     * @param listException       アプリケーションレベルの例外リスト
     */
    protected void setErrorForCheckMessageDtoList(List<CheckMessageDto> checkMessageDtoList,
                                                  AppLevelListException listException) {
        List<AppLevelException> eList = listException.getErrorList();
        for (int index = 0; index < eList.size(); index++) {
            checkMessageDtoList.add(toCheckMessageDto(eList.get(index)));
        }
        eList.removeAll(eList);
    }

    /**
     * エラー内容からメッセージDTOを作成<br/>
     *
     * @param e アプリケーションレベルの例外
     * @return チェックメッセージDto
     */
    protected CheckMessageDto toCheckMessageDto(AppLevelException e) {
        CheckMessageDto checkMessageDto = ApplicationContextUtility.getBean(CheckMessageDto.class);
        checkMessageDto.setMessageId(e.getMessageCode());
        checkMessageDto.setMessage(e.getAppLevelFacesMessage().getDetail());
        return checkMessageDto;
    }

    /**
     * システムエラー内容をログへ出力し、メッセージDTOを返す<br/>
     *
     * @param msgCode メッセージコード
     * @param args    メッセージパラメータ
     * @param cause   Exception
     * @return チェックメッセージDto
     */
    protected CheckMessageDto toCheckMessageDtoFatal(String msgCode, Object[] args, Throwable cause) {
        LOGGER.error("serviceName：" + this.getClass().getName(), cause);
        try {
            throw new AppLevelException(msgCode, args, cause);
        } catch (AppLevelException ae) {
            LOGGER.error("例外処理が発生しました", ae);
            return toCheckMessageDto(ae);
        }
    }

    /**
     * エラー内容からメッセージリスト作成<br/>
     *
     * @param msgCode メッセージコード
     * @param args    メッセージの引数
     * @param isError true:メッセージコードがエラーの場合に設定、false:メッセージコードがエラー以外の場合に設定
     * @return チェックメッセージDto
     */
    protected CheckMessageDto toCheckMessageDto(String msgCode, Object[] args, boolean isError) {
        CheckMessageDto checkMessageDto = ApplicationContextUtility.getBean(CheckMessageDto.class);
        checkMessageDto.setMessageId(msgCode);
        checkMessageDto.setMessage(getMessage(msgCode, args));
        checkMessageDto.setError(isError);
        return checkMessageDto;
    }

    /**
     * メッセージ取得
     *
     * @param msgCode コード
     * @param args    メッセージの引数
     * @return メッセージ
     */
    protected String getMessage(String msgCode, Object[] args) {
        return AppLevelFacesMessageUtil.getAllMessage(msgCode, args).getMessage();
    }

}
