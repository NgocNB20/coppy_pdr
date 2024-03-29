/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.password.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeConfirmMailType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.confirmmail.ConfirmMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.StringTransformHelper;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.Transformer;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.confirmmail.ConfirmMailRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.password.PasswordResetSendMailService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MailUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.MemberInfoUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * パスワード再設定メール送信サービス<br/>
 *
 * @author natume
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Service
public class PasswordResetSendMailServiceImpl extends AbstractShopService implements PasswordResetSendMailService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordResetSendMailServiceImpl.class);

    /**
     * 会員情報取得ロジック<br/>
     */
    private final MemberInfoGetLogic memberInfoGetLogic;

    /**
     * 確認メール登録ロジック<br/>
     */
    private final ConfirmMailRegistLogic confirmMailRegistLogic;

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
    public PasswordResetSendMailServiceImpl(MemberInfoGetLogic memberInfoGetLogic,
                                            ConfirmMailRegistLogic confirmMailRegistLogic,
                                            MailTemplateGetLogic mailTemplateGetLogic,
                                            MailSendService mailSendService,
                                            MailUtility mailUtility) {
        this.memberInfoGetLogic = memberInfoGetLogic;
        this.confirmMailRegistLogic = confirmMailRegistLogic;
        this.mailTemplateGetLogic = mailTemplateGetLogic;
        this.mailSendService = mailSendService;
        this.mailUtility = mailUtility;
    }

    /**
     * パスワード再設定メール送信処理<br/>
     *
     * @param mail               メールアドレス
     * @param memberInfoBirthDay 生年月日
     * @return メール送信結果
     */
    @Override
    public boolean execute(String mail, Timestamp memberInfoBirthDay) {

        // パラメータチェック
        Integer shopSeq = 1001;
        ArgumentCheckUtil.assertNotEmpty("mail", mail);
        ArgumentCheckUtil.assertNotNull("memberInfoBirthDay", memberInfoBirthDay);

        // 会員情報取得
        MemberInfoEntity memberInfoEntity = getMemberInfoEntity(shopSeq, mail, memberInfoBirthDay);

        // 確認メール情報登録
        ConfirmMailEntity confirmMailEntity = registConfirmMail(memberInfoEntity);

        // メール送信
        return sendMail(confirmMailEntity);
    }

    /**
     * 会員情報取得<br/>
     *
     * @param shopSeq            ショップSEQ
     * @param mail               メールアドレス
     * @param memberInfoBirthDay 会員生年月日
     * @return 会員エンティティ
     */
    protected MemberInfoEntity getMemberInfoEntity(Integer shopSeq, String mail, Timestamp memberInfoBirthDay) {

        // ユニークIDで取得する 大文字小文字の区別をなくす為
        // 会員業務Helper取得
        MemberInfoUtility memberInfoUtility = ApplicationContextUtility.getBean(MemberInfoUtility.class);
        String shopUniqueId = memberInfoUtility.createShopUniqueId(shopSeq, mail);
        MemberInfoEntity memberInfoEntity = memberInfoGetLogic.execute(shopUniqueId);
        if (memberInfoEntity == null) {
            throwMessage(MSGCD_MEMBERINFOENTITYDTO_NULL);
        }

        // 生年月日不一致エラー メッセージを分けたい時用
        if (!memberInfoEntity.getMemberInfoBirthday().equals(memberInfoBirthDay)) {
            throwMessage(MSGCD_MEMBERINFOBIRTHDAY_FAIL);
        }
        return memberInfoEntity;
    }

    /**
     * 確認メール情報登録<br/>
     *
     * @param memberInfoEntity 会員エンティティ
     * @return 確認メールエンティティ
     */
    protected ConfirmMailEntity registConfirmMail(MemberInfoEntity memberInfoEntity) {

        // 確認メール情報作成
        ConfirmMailEntity confirmMailEntity = ApplicationContextUtility.getBean(ConfirmMailEntity.class);
        confirmMailEntity.setShopSeq(memberInfoEntity.getShopSeq());
        confirmMailEntity.setMemberInfoSeq(memberInfoEntity.getMemberInfoSeq());
        confirmMailEntity.setConfirmMail(memberInfoEntity.getMemberInfoMail());
        confirmMailEntity.setConfirmMailType(HTypeConfirmMailType.PASSWORD_REISSUE);

        // 確認メール情報登録
        int result = confirmMailRegistLogic.execute(confirmMailEntity);
        if (result == 0) {
            throwMessage(MSGCD_CONFIRMMAIL_REGIST_FAIL);
        }
        return confirmMailEntity;
    }

    /**
     * メール送信<br/>
     *
     * @param confirmMailEntity 確認メール情報登録
     * @return メール送信結果
     */
    protected boolean sendMail(ConfirmMailEntity confirmMailEntity) {

        // 送信に使用するメールテンプレートを取得する
        MailTemplateEntity entity = this.mailTemplateGetLogic.execute(confirmMailEntity.getShopSeq(),
                                                                      HTypeMailTemplateType.PASSWORD_NOTIFICATION
                                                                     );

        // テンプレートがない場合
        if (entity == null) {
            return false;
        }

        // 送信先取得
        List<String> toList = Collections.singletonList(confirmMailEntity.getConfirmMail());

        // メール用値マップの作成
        Transformer transformer = ApplicationContextUtility.getBean(StringTransformHelper.class);
        Map<String, String> mailContentsMap = transformer.toValueMap(confirmMailEntity.getConfirmMailPassword());

        // メールDto作成
        MailDto mailDto = mailUtility.createMailDto(HTypeMailTemplateType.PASSWORD_NOTIFICATION, entity, toList,
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
}
