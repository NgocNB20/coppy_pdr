// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDeliveryInformationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDeliveryInformationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDeliveryInformationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderDeliveryInformationLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.AbstractWebApiLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetDeliveryInformationLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * #026 休業日の配送制御<br/>
 *
 * <pre>
 * WEB-API連携 配送情報取得ロジッククラス
 * </pre>
 *
 * @author satoh
 */
@Component
public class OrderDeliveryInformationLogicImpl extends AbstractShopLogic implements OrderDeliveryInformationLogic {

    /** ロガー */
    protected Logger log = LoggerFactory.getLogger(OrderDeliveryInformationLogicImpl.class);

    /** 日付関連Utility */
    private final DateUtility dateUtility;

    /** WEB-API連携クラス 配送情報取得 */
    private final WebApiGetDeliveryInformationLogic webApiGetDeliveryInformationLogic;

    /** 受注関連ユーティリティ */
    private final OrderUtility orderUtility;

    /** GoodsUtility */
    private final GoodsUtility goodsUtility;

    @Autowired
    public OrderDeliveryInformationLogicImpl(DateUtility dateUtility,
                                             OrderUtility orderUtility,
                                             GoodsUtility goodsUtility,
                                             WebApiGetDeliveryInformationLogic webApiGetDeliveryInformationLogic) {
        this.dateUtility = dateUtility;
        this.orderUtility = orderUtility;
        this.goodsUtility = goodsUtility;
        this.webApiGetDeliveryInformationLogic = webApiGetDeliveryInformationLogic;
    }

    /**
     * WEB-API連携 配送情報取得を行い<br />
     * 結果を返却します。
     *
     * @param orderGoodsEntityList 受注商品エンティティリスト
     * @param reqDto               配送情報取得 リクエストデータ
     * @return 配送情報取得 詳細情報
     */
    public WebApiGetDeliveryInformationResponseDetailDto execute(List<OrderGoodsEntity> orderGoodsEntityList,
                                                                 WebApiGetDeliveryInformationRequestDto reqDto) {

        // 商品コードリスト
        List<String> goodsCodeList = new ArrayList<String>();

        for (String goodsCode : orderUtility.getGoodsCodeList(orderGoodsEntityList)) {
            // 心意気商品コードからkpを削除
            goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(goodsCode);
            // 重複なければリクエスト用商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        }
        reqDto.setGoodsCode(AbstractWebApiLogic.createStrPipeByList(goodsCodeList));

        log.info("配送情報取得Web-API呼出（開始）：" + dateUtility.format(dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));
        WebApiGetDeliveryInformationResponseDto resDto =
                        (WebApiGetDeliveryInformationResponseDto) webApiGetDeliveryInformationLogic.execute(reqDto);
        log.info("配送情報取得Web-API呼出（終了）：" + dateUtility.format(dateUtility.getCurrentTime(), DateUtility.YMD_SLASH_HMS));
        // 処理結果のステータスが「0:正常」以外の場合 または 詳細情報がない場合 はnullで返却
        if (!AbstractWebApiLogic.WEB_API_STATUS_SUCCESS.equals(resDto.getResult().getStatus())
            || CollectionUtil.isEmpty(resDto.getInfo())) {
            return null;
        }

        return resDto.getInfo().get(0);
    }

    /**
     * WEB-API連携 配送情報取得を行い<br />
     * 結果を返却します。
     *
     * @param orderGoodsEntityList 受注商品エンティティリスト
     * @param reqDto               配送情報取得 リクエストデータ
     * @param checkMessageDtoList  エラーメッセージ用List
     * @return 配送情報取得 詳細情報
     */
    public WebApiGetDeliveryInformationResponseDetailDto execute(List<OrderGoodsEntity> orderGoodsEntityList,
                                                                 WebApiGetDeliveryInformationRequestDto reqDto,
                                                                 List<CheckMessageDto> checkMessageDtoList) {

        WebApiGetDeliveryInformationResponseDetailDto resDetailDto = execute(orderGoodsEntityList, reqDto);

        // 詳細情報が取得できなかった場合
        if (resDetailDto == null) {
            // 処理終了
            return null;
        }

        // 配送情報取得 結果 お届け不可の場合
        if (HTypeDeliveryFlag.OFF.getValue().equals(resDetailDto.getDeliveryFlag())) {

            List<String> nodeliveryGoodsCodeList =
                            AbstractWebApiLogic.creatListByStrPipe(resDetailDto.getNodeliveryGoodsCode());

            for (String nodeliveryGoodsCode : nodeliveryGoodsCodeList) {

                for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
                    // 商品コードが一致しない場合はスキップ
                    if (!nodeliveryGoodsCode.equals(orderGoodsEntity.getGoodsCode())) {
                        continue;
                    }

                    // カートに表示する商品名に規格名も含める
                    StringBuilder displayGoodsName = new StringBuilder();

                    if (orderGoodsEntity.getUnitValue1() != null) {
                        displayGoodsName.append("(");
                        // 規格管理ありの場合は規格値１は必須項目なので必ず取得できる
                        displayGoodsName.append(orderGoodsEntity.getUnitValue1());
                        // 規格値２の値がNULLの場合は商品表示名（規格値１）のみをエラーメッセージに表示する
                        // 規格値２の値が存在する場合は規格値２をエラーメッセージに表示する
                        if (orderGoodsEntity.getUnitValue2() != null) {
                            displayGoodsName.append("/");
                            displayGoodsName.append(orderGoodsEntity.getUnitValue2());
                        }
                        displayGoodsName.append(")");
                    }
                    // エラーメッセージを追加
                    CheckMessageDto checkMessageDto = new CheckMessageDto();
                    checkMessageDto.setMessageId(MSGCD_DELIVERY_ERROR);
                    checkMessageDto.setArgs(new String[] {orderGoodsEntity.getGoodsGroupName() + displayGoodsName});
                    checkMessageDtoList.add(checkMessageDto);

                }
            }
        }

        return resDetailDto;
    }
}
// PDR Migrate Customization to here
