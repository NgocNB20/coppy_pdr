// 2023-renew AddNo2 from here
package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.MemberInfoUpdateTransformHelper;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoUpdateSendMailLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MailUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 会員情報変更通知メール送信
 *
 * @author Thang Doan (VJP)
 */
@Component
public class MemberInfoUpdateSendMailLogicImpl extends AbstractShopLogic implements MemberInfoUpdateSendMailLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberInfoUpdateSendMailLogicImpl.class);

    /**
     * メール送信ロジック<br/>
     */
    private final MailSendService mailsendService;

    /**
     * メールテンプレート取得ロジック<br/>
     */
    private final MailTemplateGetLogic mailTemplateGetLogic;

    /**
     * メールUtility取得
     */
    private final MailUtility mailUtility;

    @Autowired
    public MemberInfoUpdateSendMailLogicImpl(MailSendService mailsendService,
                                             MailTemplateGetLogic mailTemplateGetLogic,
                                             MailUtility mailUtility) {
        this.mailsendService = mailsendService;
        this.mailTemplateGetLogic = mailTemplateGetLogic;
        this.mailUtility = mailUtility;
    }

    /**
     * メール送信
     *
     * @param memberInfoSeq
     * @param memberInfoAfterChange
     * @param memberInfoBeforeChange
     * @param imageAddCount
     * @param modifiedList
     * @param medicalTreatmentTitleList
     * @return メール送信結果
     */
    @Override
    public void execute(Integer memberInfoSeq,
                        MemberInfoEntity memberInfoAfterChange,
                        MemberInfoEntity memberInfoBeforeChange,
                        Integer imageAddCount,
                        List<String> modifiedList,
                        List<String> medicalTreatmentTitleList) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
        ArgumentCheckUtil.assertNotNull("memberInfoAfterChange", memberInfoAfterChange);
        ArgumentCheckUtil.assertNotNull("memberInfoBeforeChange", memberInfoBeforeChange);
        ArgumentCheckUtil.assertNotNull("imageAddCount", imageAddCount);
        ArgumentCheckUtil.assertNotNull("modifiedList", modifiedList);
        ArgumentCheckUtil.assertNotEmpty("medicalTreatmentTitleList", medicalTreatmentTitleList);

        // 送信に使用するメールテンプレートを取得する
        MailTemplateEntity entity = this.mailTemplateGetLogic.execute(memberInfoBeforeChange.getShopSeq(),
                                                                      HTypeMailTemplateType.UPDATE_MEMBERINFO_COMPLETE_NOTIFICATION_FOR_MEMBER_MAIL
                                                                     );
        // テンプレートがない場合
        if (entity == null) {
            LOGGER.warn("会員情報変更通知メールのテンプレート取得に失敗しました");
            return;
        }
        // メール用値マップの作成
        MemberInfoUpdateTransformHelper transformer =
                        ApplicationContextUtility.getBean(MemberInfoUpdateTransformHelper.class);
        boolean isSendMailAdmin = transformer.isSendMailAdmin(modifiedList);
        // 会員向け
        this.sendMail(memberInfoAfterChange, memberInfoBeforeChange, imageAddCount, modifiedList,
                      medicalTreatmentTitleList, entity, transformer, false
                     );
        if (isSendMailAdmin) {
            // 管理者向け
            this.sendMail(memberInfoAfterChange, memberInfoBeforeChange, imageAddCount, modifiedList,
                          medicalTreatmentTitleList, entity, transformer, true
                         );
        }
    }

    /**
     * メール送信
     *
     * @param memberInfoAfterChange
     * @param memberInfoBeforeChange
     * @param imageAddCount
     * @param modifiedList
     * @param medicalTreatmentTitleList
     * @param entity
     * @param transformer
     * @param isMailAdminTemplate
     * @return
     */
    private void sendMail(MemberInfoEntity memberInfoAfterChange,
                          MemberInfoEntity memberInfoBeforeChange,
                          Integer imageAddCount,
                          List<String> modifiedList,
                          List<String> medicalTreatmentTitleList,
                          MailTemplateEntity entity,
                          MemberInfoUpdateTransformHelper transformer,
                          boolean isMailAdminTemplate) {
        // 送信先取得
        List<String> toList = null;
        HTypeMailTemplateType mailTemplateType = isMailAdminTemplate
                        ? HTypeMailTemplateType.UPDATE_MEMBERINFO_COMPLETE_NOTIFICATION_FOR_ADMIN_MAIL
                        : HTypeMailTemplateType.UPDATE_MEMBERINFO_COMPLETE_NOTIFICATION_FOR_MEMBER_MAIL;
        if (isMailAdminTemplate) {
            String mailAdmin = PropertiesUtil.getSystemPropertiesValue("recipient");
            toList = Collections.singletonList(mailAdmin);
        } else {
            toList = Collections.singletonList(memberInfoBeforeChange.getMemberInfoMail());
        }
        // メール本文の設定
        Map<String, String> mailContentsMap =
                        transformer.toValueMap(memberInfoAfterChange, memberInfoBeforeChange, imageAddCount,
                                               modifiedList, medicalTreatmentTitleList);
        // メールDto作成
        MailDto mailDto = mailUtility.createMailDto(mailTemplateType, entity, toList, mailContentsMap);

        // メール送信
        try {
            mailsendService.execute(mailDto);
        } catch (Exception e) {
            LOGGER.warn("会員情報変更通知メール送信に失敗しました。", e);
            throw e;
        }
    }
}
// 2023-renew AddNo2 to here
