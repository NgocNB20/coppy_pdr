/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentCompleteMail;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ShipmentRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.OrderTransformHelper;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.Transformer;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.AsyncMailSendService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ReceiveOrderGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ShipmentCompleteMailBatchRegistService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MailUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 出荷完了メール自動送信サービス(非同期)<br/>
 *
 * @author $Author: tateishi $
 * @author hakogi(itec) 2012/02/10 チケット #2814対応
 */
@Service
public class ShipmentCompleteMailBatchRegistServiceImpl extends AbstractShopService
                implements ShipmentCompleteMailBatchRegistService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentCompleteMailBatchRegistServiceImpl.class);

    /**
     * 受注取得サービス
     */
    private final ReceiveOrderGetService receiveOrderGetService;

    /**
     * メールテンプレート取得ロジック<br/>
     */
    private final MailTemplateGetLogic mailTemplateGetLogic;

    /**
     * メール送信サービス（非同期送信）
     */
    private final AsyncMailSendService asyncMailSendService;

    /**
     * メールUtility取得
     */
    private final MailUtility mailUtility;

    @Autowired
    public ShipmentCompleteMailBatchRegistServiceImpl(ReceiveOrderGetService receiveOrderGetService,
                                                      MailTemplateGetLogic mailTemplateGetLogic,
                                                      AsyncMailSendService asyncMailSendService,
                                                      MailUtility mailUtility) {
        this.receiveOrderGetService = receiveOrderGetService;
        this.mailTemplateGetLogic = mailTemplateGetLogic;
        this.asyncMailSendService = asyncMailSendService;
        this.mailUtility = mailUtility;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param shipmentRegistDtoList 出荷処理対象の出荷情報
     * @return int 1:異常終了、0:正常終了
     */
    @Override
    public int execute(List<ShipmentRegistDto> shipmentRegistDtoList) {

        try {
            // 完了送信対象のデータが存在しない場合、バッチ登録を行わずに処理を終了する
            if (shipmentRegistDtoList == null || shipmentRegistDtoList.isEmpty()) {
                return 0;
            }

            String mailFlag = PropertiesUtil.getSystemPropertiesValue("shipmentCompleteMail");

            if (HTypeShipmentCompleteMail.MAIL_NOT_SEND.getValue().equals(mailFlag)) {
                LOGGER.info("出荷完了メールを送信しません。(Sytem.properties.shipmentCompleteMail:" + mailFlag + ")");
                return 0;
            } else {
                LOGGER.info("出荷完了メールを送信します。(Sytem.properties.shipmentCompleteMail:" + mailFlag + ") -- 出荷完了メール送信タスク登録");
            }

            // ショップSEQ取得
            final Integer shopSeq = 1001;

            // メールテンプレートを取得
            MailTemplateEntity entity =
                            mailTemplateGetLogic.execute(shopSeq, HTypeMailTemplateType.SHIPMENT_NOTIFICATION);

            if (entity == null) {
                LOGGER.error("出荷完了のメールテンプレートが取得できません。");
                LOGGER.error("出荷完了メールの送信は行いません。");
                return 1;
            }

            // メールDtoを作成
            List<MailDto> mailDtoList = new ArrayList<>();

            // メールDTOリスト設定
            setMailDtoList(shipmentRegistDtoList, entity, mailDtoList);

            // 出荷完了メール送信
            return sendMail(mailDtoList);
        } catch (Exception e) {
            LOGGER.error("エラー -- 出荷完了メールの送信に失敗しました。");
            LOGGER.error("情報 -- " + e.getMessage() + " -- ", e);
            return 1;
        }
    }

    /**
     * メールDTOリスト設定<br/>
     *
     * @param shipmentRegistDtoList 出荷処理対象の出荷情報
     * @param entity                メールテンプレート
     * @param mailDtoList           メールDTOリスト
     * @param customParams          案件用引数
     */
    protected void setMailDtoList(List<ShipmentRegistDto> shipmentRegistDtoList,
                                  MailTemplateEntity entity,
                                  List<MailDto> mailDtoList,
                                  Object... customParams) {
        for (ShipmentRegistDto shipmentRegistDto : shipmentRegistDtoList) {
            String orderCode = shipmentRegistDto.getOrderCode();

            if (StringUtil.isEmpty(orderCode)) {
                continue;
            }

            // 受注情報を取得
            ReceiveOrderDto receiveOrderDto = receiveOrderGetService.execute(orderCode);

            if (receiveOrderDto == null) {
                LOGGER.error("受注番号：" + orderCode + " の受注情報が取得できません。");
                LOGGER.error("受注番号：" + orderCode + " の出荷完了メールの送信は行いません。");
                continue;
            }

            // 注文主情報を取得
            OrderPersonEntity orderPersonEntity = receiveOrderDto.getOrderPersonEntity();

            if (orderPersonEntity == null) {
                LOGGER.error("受注番号：" + orderCode + " の注文主情報が取得できません。");
                LOGGER.error("受注番号：" + orderCode + " の出荷完了メールの送信は行いません。");
                continue;
            }

            // 送信先を設定
            entity.setMailTemplateToAddress(orderPersonEntity.getOrderMail());
            List<String> toList = Collections.singletonList(orderPersonEntity.getOrderMail());
            // メール用値マップの作成
            Transformer transformer = ApplicationContextUtility.getBean(OrderTransformHelper.class);
            Map<String, String> mailContents = transformer.toValueMap(receiveOrderDto);

            // １メール分の送信情報
            MailDto mailDto = mailUtility.createMailDto(HTypeMailTemplateType.SHIPMENT_NOTIFICATION, entity, toList,
                                                        mailContents
                                                       );

            // メールDTO設定
            mailDtoList.add(mailDto);
        }
    }

    /**
     * 出荷完了メール送信<br/>
     *
     * @param mailDtoList  メールDTOリスト
     * @param customParams 案件用引数
     */
    protected int sendMail(List<MailDto> mailDtoList, Object... customParams) throws JsonProcessingException {
        if (!mailDtoList.isEmpty()) {
            return asyncMailSendService.execute(mailDtoList);
        }
        return 0;
    }
}
