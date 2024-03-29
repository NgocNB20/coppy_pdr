/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.orderhistory.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.orderhistory.CancelOrderHistoryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.CancelOrderTransformHelper;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.Transformer;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.orderhistory.CancelOrderSendMailService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MailUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 注文キャンセルメール送信サービス
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Service
// 2023-renew No68 from here
public class CancelOrderSendMailServiceImpl extends AbstractShopService implements CancelOrderSendMailService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderSendMailServiceImpl.class);

    /**
     * 会員情報取得ロジック
     */
    private final MemberInfoGetLogic memberInfoGetLogic;

    /**
     * メールテンプレート取得ロジック
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

    /**
     * コンストラクタ
     */
    @Autowired
    public CancelOrderSendMailServiceImpl(MemberInfoGetLogic memberInfoGetLogic,
                                          MailTemplateGetLogic mailTemplateGetLogic,
                                          MailSendService mailSendService,
                                          MailUtility mailUtility) {
        this.memberInfoGetLogic = memberInfoGetLogic;
        this.mailTemplateGetLogic = mailTemplateGetLogic;
        this.mailSendService = mailSendService;
        this.mailUtility = mailUtility;
    }

    /**
     * 注文キャンセルメール送信
     *
     * @param memberInfoSeq 会員SEQ
     * @param cancelOrderHistoryDto 注文履歴キャンセルDtoクラス
     * @return メール送信結果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public boolean execute(Integer memberInfoSeq, CancelOrderHistoryDto cancelOrderHistoryDto) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);

        // 会員情報取得
        MemberInfoEntity memberInfoEntity = memberInfoGetLogic.execute(memberInfoSeq);
        if (memberInfoEntity == null) {
            throwMessage(MSGCD_MEMBERINFOENTITYDTO_NULL);
        }

        // 送信に使用するメールテンプレートを取得する
        MailTemplateEntity entity = this.mailTemplateGetLogic.execute(1001, HTypeMailTemplateType.ORDER_CANCEL);

        // テンプレートがない場合
        if (entity == null) {
            return false;
        }

        // 送信先取得
        List<String> toList = Collections.singletonList(memberInfoEntity.getMemberInfoMail());

        // メール用値マップの作成
        Transformer transformer = ApplicationContextUtility.getBean(CancelOrderTransformHelper.class);
        Map<String, String> mailContentsMap = transformer.toValueMap(cancelOrderHistoryDto);

        // メールDto作成
        MailDto mailDto =
                        mailUtility.createMailDto(HTypeMailTemplateType.ORDER_CANCEL, entity, toList, mailContentsMap);

        // メール送信
        try {
            mailSendService.execute(mailDto);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            return false;
        }

        return true;
    }

}
// 2023-renew No68 to here
