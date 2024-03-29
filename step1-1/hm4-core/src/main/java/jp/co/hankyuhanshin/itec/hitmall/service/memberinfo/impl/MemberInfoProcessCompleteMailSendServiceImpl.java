/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.MemberInfoTransformHelper;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.Transformer;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoProcessCompleteMailSendService;
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
 * 会員処理完了メール送信サービス<br/>
 *
 * @author natume
 * @version $Revision: 1.1 $
 */
@Service
public class MemberInfoProcessCompleteMailSendServiceImpl extends AbstractShopService
                implements MemberInfoProcessCompleteMailSendService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberInfoProcessCompleteMailSendServiceImpl.class);

    /**
     * 会員情報取得ロジック<br/>
     */
    private final MemberInfoGetLogic memberInfoGetLogic;

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
    public MemberInfoProcessCompleteMailSendServiceImpl(MemberInfoGetLogic memberInfoGetLogic,
                                                        MailTemplateGetLogic mailTemplateGetLogic,
                                                        MailSendService mailSendService,
                                                        MailUtility mailUtility) {
        this.memberInfoGetLogic = memberInfoGetLogic;
        this.mailTemplateGetLogic = mailTemplateGetLogic;
        this.mailSendService = mailSendService;
        this.mailUtility = mailUtility;
    }

    /**
     * 会員登録完了メール送信処理<br/>
     *
     * @param memberInfoSeq    会員SEQ
     * @param mailTemplateType メールテンプレートタイプ(会員登録完了/会員退会完了/会員メールアドレス変更)
     * @return メール送信結果
     */
    @Override
    public boolean execute(Integer memberInfoSeq, HTypeMailTemplateType mailTemplateType) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);

        // 会員情報取得
        MemberInfoEntity memberInfoEntity = memberInfoGetLogic.execute(memberInfoSeq);
        if (memberInfoEntity == null) {
            throwMessage(MSGCD_MEMBERINFOENTITYDTO_NULL);
        }

        // 送信に使用するメールテンプレートを取得する
        MailTemplateEntity entity = this.mailTemplateGetLogic.execute(memberInfoEntity.getShopSeq(), mailTemplateType);

        // テンプレートがない場合
        if (entity == null) {
            return false;
        }

        // 送信先取得
        List<String> toList = Collections.singletonList(memberInfoEntity.getMemberInfoMail());

        // メール用値マップの作成
        Transformer transformer = ApplicationContextUtility.getBean(MemberInfoTransformHelper.class);
        Map<String, String> mailContentsMap = transformer.toValueMap(memberInfoEntity);

        // メールDto作成
        MailDto mailDto = mailUtility.createMailDto(mailTemplateType, entity, toList, mailContentsMap);

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
