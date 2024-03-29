/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.OrderConfirmMailTransformHelper;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.OrderTransformHelper;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.Transformer;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderCompleteMailSendService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ReceiveOrderGetService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MailUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 注文受付完了メール送信サービス実装クラス
 *
 * @author negishi
 * @version $Revision: 1.6 $
 */
@Service
public class OrderCompleteMailSendServiceImpl extends AbstractShopService implements OrderCompleteMailSendService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCompleteMailSendServiceImpl.class);

    /**
     * 受注取得サービス
     */
    private final ReceiveOrderGetService receiveOrderGetService;

    /**
     * メールテンプレート取得ロジック<br/>
     */
    private final MailTemplateGetLogic mailTemplateGetLogic;

    /**
     * メール送信サービス（同期送信）
     */
    private final MailSendService mailSendService;

    /**
     * メールUtility取得
     */
    private final MailUtility mailUtility;

    @Autowired
    public OrderCompleteMailSendServiceImpl(ReceiveOrderGetService receiveOrderGetService,
                                            MailTemplateGetLogic mailTemplateGetLogic,
                                            MailSendService mailSendService,
                                            MailUtility mailUtility) {
        this.receiveOrderGetService = receiveOrderGetService;
        this.mailTemplateGetLogic = mailTemplateGetLogic;
        this.mailSendService = mailSendService;
        this.mailUtility = mailUtility;
    }

    /**
     * サービス実行<br/>
     *
     * @param orderSeq       受注SEQ
     * @param orderVersionNo 受注履歴連番
     * @return true..成功 / false..失敗
     */
    @Override
    public boolean execute(Integer orderSeq, Integer orderVersionNo) {

        // 引数チェック
        checkParameter(orderSeq);

        // 受注取得サービス実行
        ReceiveOrderDto receiveOrderDto = receiveOrderGetService.execute(orderSeq, orderVersionNo);
        if (receiveOrderDto == null) {
            throwMessage(MSGCD_RECEIVEORDERDTO_GET_NULL);
        }

        // 送信に使用するメールテンプレートを取得する
        MailTemplateEntity entity = this.mailTemplateGetLogic.execute(1001, HTypeMailTemplateType.ORDER_CONFIRMATION);

        // テンプレートがない場合
        if (entity == null) {
            return false;
        }

        // 送信先取得
        List<String> toList = Collections.singletonList(receiveOrderDto.getOrderPersonEntity().getOrderMail());

        // メール用値マップの作成
        Transformer transformer = ApplicationContextUtility.getBean(OrderTransformHelper.class);
        Map<String, String> mailContentsMap = transformer.toValueMap(receiveOrderDto);

        // メールDto作成
        MailDto mailDto = mailUtility.createMailDto(HTypeMailTemplateType.ORDER_CONFIRMATION, entity, toList,
                                                    mailContentsMap
                                                   );

        // メール送信
        try {
            mailSendService.execute(mailDto);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            return false;
        }

        return true;
    }

    /**
     * 引数チェック
     *
     * @param orderSeq 受注SEQ
     */
    protected void checkParameter(Integer orderSeq) {
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
    }

    // PDR Migrate Customization from here

    /**
     * サービス実行
     *
     * @param receiveOrderDtoList 受注DTOリスト
     * @return true..成功 / false..失敗
     */
    @Override
    public boolean execute(List<ReceiveOrderDto> receiveOrderDtoList) {

        // 送信に使用するメールテンプレートを取得する
        MailTemplateEntity entity = this.mailTemplateGetLogic.execute(1001, HTypeMailTemplateType.ORDER_CONFIRMATION);

        // テンプレートがない場合
        if (entity == null) {
            return false;
        }
        // 送信先取得
        List<String> toList =
                        Collections.singletonList(receiveOrderDtoList.get(0).getOrderPersonEntity().getOrderMail());
        // メール用値マップの作成
        Transformer transformer = ApplicationContextUtility.getBean(OrderConfirmMailTransformHelper.class);
        Map<String, String> mailContentsMap = transformer.toValueMap(receiveOrderDtoList);
        // メールDto作成
        MailDto mailDto = mailUtility.createMailDto(HTypeMailTemplateType.ORDER_CONFIRMATION, entity, toList,
                                                    mailContentsMap
                                                   );

        // メール送信
        try {
            mailSendService.execute(mailDto);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            return false;
        }

        return true;
    }
    // PDR Migrate Customization to here

}
