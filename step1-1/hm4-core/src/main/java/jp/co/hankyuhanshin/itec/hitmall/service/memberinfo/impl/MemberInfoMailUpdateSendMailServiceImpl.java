/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeConfirmMailType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.confirmmail.ConfirmMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.StringTransformHelper;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.Transformer;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.confirmmail.ConfirmMailRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoMailUpdateSendMailService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MailUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.MemberInfoUtility;
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
 * 会員メールアドレス変更メール送信サービス<br/>
 *
 * @author natume
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Service
public class MemberInfoMailUpdateSendMailServiceImpl extends AbstractShopService
                implements MemberInfoMailUpdateSendMailService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberInfoMailUpdateSendMailServiceImpl.class);

    /**
     * 会員データチェックロジック<br/>
     */
    private final MemberInfoDataCheckLogic memberInfoDataCheckLogic;

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
    public MemberInfoMailUpdateSendMailServiceImpl(MemberInfoDataCheckLogic memberInfoDataCheckLogic,
                                                   ConfirmMailRegistLogic confirmMailRegistLogic,
                                                   MailTemplateGetLogic mailTemplateGetLogic,
                                                   MailSendService mailSendService,
                                                   MailUtility mailUtility) {
        this.memberInfoDataCheckLogic = memberInfoDataCheckLogic;
        this.confirmMailRegistLogic = confirmMailRegistLogic;
        this.mailTemplateGetLogic = mailTemplateGetLogic;
        this.mailSendService = mailSendService;
        this.mailUtility = mailUtility;
    }

    /**
     * 会員メールアドレス変更メール送信処理<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param mail          メールアドレス
     * @return メール送信結果
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public boolean execute(Integer memberInfoSeq, String mail) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
        ArgumentCheckUtil.assertNotEmpty("mail", mail);

        // 会員IDの重複チェック
        checkMemberInfoId(memberInfoSeq, mail);

        // 確認メール情報登録
        ConfirmMailEntity confirmMailEntity = registConfirmMail(memberInfoSeq, mail);

        // メール送信
        return sendMail(confirmMailEntity);
    }

    /**
     * 会員IDの重複チェック<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param mail          メールアドレス
     */
    protected void checkMemberInfoId(Integer memberInfoSeq, String mail) {

        // 会員SEQ, ユニークIDをセット
        MemberInfoEntity memberInfoEntity = ApplicationContextUtility.getBean(MemberInfoEntity.class);
        memberInfoEntity.setMemberInfoSeq(memberInfoSeq);
        // 会員業務Helper取得
        MemberInfoUtility memberInfoUtility = ApplicationContextUtility.getBean(MemberInfoUtility.class);
        memberInfoEntity.setMemberInfoUniqueId(memberInfoUtility.createShopUniqueId(1001, mail));

        // 会員データチェック実行
        memberInfoDataCheckLogic.execute(mail, HTypeMemberInfoStatus.ADMISSION);
    }

    /**
     * 確認メール情報登録<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param mail          メールアドレス
     * @return 確認メールエンティティ
     */
    protected ConfirmMailEntity registConfirmMail(Integer memberInfoSeq, String mail) {

        // 確認メール情報作成
        ConfirmMailEntity confirmMailEntity = ApplicationContextUtility.getBean(ConfirmMailEntity.class);
        confirmMailEntity.setShopSeq(1001);
        confirmMailEntity.setMemberInfoSeq(memberInfoSeq);
        confirmMailEntity.setConfirmMail(mail);
        confirmMailEntity.setConfirmMailType(HTypeConfirmMailType.MEMBERINFO_MAIL_UPDATE);

        // 確認メール情報登録
        int result = confirmMailRegistLogic.execute(confirmMailEntity);
        if (result == 0) {
            throwMessage(MSGCD_CONFIRMMAILENTITYDTO_REGIST_FAIL);
        }
        return confirmMailEntity;
    }

    /**
     * メール送信処理<br/>
     *
     * @param confirmMailEntity 確認メールエンティティ
     * @return メール送信結果
     */
    protected boolean sendMail(ConfirmMailEntity confirmMailEntity) {

        // 送信に使用するメールテンプレートを取得する
        MailTemplateEntity entity = this.mailTemplateGetLogic.execute(confirmMailEntity.getShopSeq(),
                                                                      HTypeMailTemplateType.EMAIL_MODIFICATION
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
        MailDto mailDto = mailUtility.createMailDto(HTypeMailTemplateType.EMAIL_MODIFICATION, entity, toList,
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
