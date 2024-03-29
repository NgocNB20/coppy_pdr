/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.administrator.confirmmail.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAdminConfirmMailType;
import jp.co.hankyuhanshin.itec.hitmall.dao.administrator.confirmmail.AdminConfirmMailDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.confirmmail.AdminConfirmMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.StringTransformHelper;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.Transformer;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.confirmmail.AdminConfirmMailLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MailUtility;
import jp.co.hankyuhanshin.itec.hmbase.helper.crypto.MD5Helper;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class is used to send mail to operator and update the DB
 *
 * @author Doan Thang (VTI Japan Co. Ltd)
 */
@Component
public class AdminConfirmMailLogicImpl extends AbstractShopLogic implements AdminConfirmMailLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminConfirmMailLogicImpl.class);

    /**
     * AdminConfirmMailDao instance
     */
    private final AdminConfirmMailDao adminConfirmMailDao;
    /**
     * DateUtility instance
     */
    private final DateUtility dateUtility;

    /**
     * AdminLogic instance
     */
    private final AdminLogic adminLogic;

    /**
     * メールテンプレート取得ロジック<br/>
     */
    private final MailTemplateGetLogic mailTemplateGetLogic;

    /**
     * メールUtility取得
     */
    private final MailUtility mailUtility;

    /**
     * メール送信サービス（同期送信）
     */
    private final MailSendService mailSendService;

    @Autowired
    public AdminConfirmMailLogicImpl(AdminConfirmMailDao adminConfirmMailDao,
                                     DateUtility dateUtility,
                                     AdminLogic adminLogic,
                                     MailTemplateGetLogic mailTemplateGetLogic,
                                     MailUtility mailUtility,
                                     MailSendService mailSendService) {
        this.adminConfirmMailDao = adminConfirmMailDao;
        this.dateUtility = dateUtility;
        this.adminLogic = adminLogic;
        this.mailTemplateGetLogic = mailTemplateGetLogic;
        this.mailUtility = mailUtility;
        this.mailSendService = mailSendService;
    }

    /**
     * This method will check the operator details and send mail to the operator
     *
     * @param mail            mail address entered on screen
     * @param administratorId administratorId entered on screen
     * @return true: mail send success, false: mail send failure
     */
    @Override
    public boolean passwordResetSendMail(String mail, String administratorId) {
        // パラメータチェック
        Integer shopSeq = 1001;
        ArgumentCheckUtil.assertNotEmpty("mail", mail);
        ArgumentCheckUtil.assertNotNull("administratorId", administratorId);

        AdministratorEntity administratorEntity = getAdministratorEntity(shopSeq, mail, administratorId);

        AdminConfirmMailEntity adminConfirmMailEntity = registAdminConfirmMail(administratorEntity);

        return sendMail(adminConfirmMailEntity, administratorEntity);
    }

    /**
     * This method will fetch the  operator details from DB and check them
     *
     * @param shopSeq         shopSeq
     * @param mail            mail
     * @param administratorId administratorId
     * @return AdministratorEntity
     */
    protected AdministratorEntity getAdministratorEntity(Integer shopSeq, String mail, String administratorId) {
        AdministratorEntity administratorEntity = adminLogic.getAdministrator(shopSeq, administratorId);
        if (administratorEntity == null) {
            throwMessage(MSGCD_ADMINISTRATORIDMAIL_FAIL);
        }
        if (!administratorEntity.getMail().equals(mail)) {
            throwMessage(MSGCD_ADMINISTRATORIDMAIL_FAIL);
        }
        return administratorEntity;
    }

    /**
     * This method will add entry to the AdminConfirmMail table
     *
     * @param administratorEntity administratorEntity
     * @return AdminConfirmMailEntity
     */
    protected AdminConfirmMailEntity registAdminConfirmMail(AdministratorEntity administratorEntity) {
        AdminConfirmMailEntity adminConfirmMailEntity = ApplicationContextUtility.getBean(AdminConfirmMailEntity.class);
        adminConfirmMailEntity.setShopSeq(administratorEntity.getShopSeq());
        adminConfirmMailEntity.setAdministratorSeq(administratorEntity.getAdministratorSeq());
        adminConfirmMailEntity.setAdminConfirmMail(administratorEntity.getMail());
        adminConfirmMailEntity.setAdminConfirmMailType(HTypeAdminConfirmMailType.PASSWORD_REISSUE);

        int result = this.getAdminConfirmMailEntityToRegister(adminConfirmMailEntity);
        if (result == 0) {
            throwMessage(MSGCD_CONFIRMMAIL_REGIST_FAIL);
        }
        return adminConfirmMailEntity;
    }

    /**
     * メール送信<br/>
     *
     * @param adminConfirmMailEntity AdminConfirmMailEntity
     * @param administratorEntity    AdministratorEntity
     * @return メール送信結果
     */
    protected boolean sendMail(AdminConfirmMailEntity adminConfirmMailEntity, AdministratorEntity administratorEntity) {

        // 送信に使用するメールテンプレートを取得する
        // 不要機能
        // MailTemplateEntity entity = this.mailTemplateGetLogic.execute(adminConfirmMailEntity.getShopSeq(), HTypeMailTemplateType.ADMIN_PASSWORD_NOTIFICATION);
        MailTemplateEntity entity = null;

        // テンプレートがない場合
        if (entity == null) {
            return false;
        }

        // 送信先取得
        List<String> toList = Collections.singletonList(adminConfirmMailEntity.getAdminConfirmMail());

        // メール用値マップの作成
        Transformer transformer = ApplicationContextUtility.getBean(StringTransformHelper.class);
        Map<String, String> mailContentsMap =
                        transformer.toValueMap(adminConfirmMailEntity.getAdminConfirmMailPassword());

        // メールDto作成
        // 不要機能
        // MailDto mailDto = mailUtility.createMailDto(HTypeMailTemplateType.ADMIN_PASSWORD_NOTIFICATION, entity, toList, mailContentsMap);
        MailDto mailDto = null;

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
     * Method to populate AdminConfirmMailEntity
     *
     * @param adminConfirmMailEntity adminConfirmMailEntity
     * @return 1 for successful DB insertion, 0 for DB insertion fail
     */
    protected int getAdminConfirmMailEntityToRegister(AdminConfirmMailEntity adminConfirmMailEntity) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("adminConfirmMailEntity", adminConfirmMailEntity);

        // 確認メール情報の設定
        setConfirmMailEntity(adminConfirmMailEntity);

        // 登録
        return adminConfirmMailDao.insert(adminConfirmMailEntity);
    }

    /**
     * 確認メール情報のセット<br/>
     *
     * @param adminConfirmMailEntity 確認メールエンティティ
     */
    protected void setConfirmMailEntity(AdminConfirmMailEntity adminConfirmMailEntity) {
        // 現在日時取得
        Timestamp currentTime = dateUtility.getCurrentTime();

        // 確認メールSEQ取得
        Integer adminConfirmMailSeq = adminConfirmMailDao.getConfirmMailSeqNextVal();

        /* パスワード作成 ハッシュ */

        // SEQ 8桁
        String seq = new DecimalFormat("00000000").format(adminConfirmMailSeq);

        // ハッシュ化 最高10回まで
        int count = 1;
        String password = null;
        while (true) {

            // 現日日時 ミリ秒20桁
            String time = new DecimalFormat("00000000000000000000").format(System.currentTimeMillis());

            // ハッシュ化
            MD5Helper helper = ApplicationContextUtility.getBean(MD5Helper.class);
            password = helper.createHash(seq + time);

            // 既存のハッシュパスワードと重複チェック
            if (password != null) {
                if (adminConfirmMailDao.getEntityByPassword(password) == null) {
                    break;
                }
            }

            // Max回数設定
            count++;
            if (count >= 10) {
                throwMessage(MSGCD_MAKE_CONFIRMMAILPASSWORD_FAIL);
            }
        }

        /* 各情報セット */
        adminConfirmMailEntity.setAdminConfirmMailSeq(adminConfirmMailSeq);
        adminConfirmMailEntity.setAdminConfirmMailPassword(password);
        adminConfirmMailEntity.setEffectiveTime(getExpiresTime());
        adminConfirmMailEntity.setUpdateTime(currentTime);
        adminConfirmMailEntity.setRegistTime(currentTime);
    }

    /**
     * 有効期限時間を作成する<br/>
     *
     * @return 有効期限時間
     */
    protected Timestamp getExpiresTime() {

        // システムプロパティの有効期限を取得
        String expiresTime = PropertiesUtil.getSystemPropertiesValue("admin.effective.time");
        return dateUtility.getAmountHourTimestamp(Integer.parseInt(expiresTime), true, dateUtility.getCurrentTime());
    }

    /**
     * This method will return AdminConfirmMailEntity depending upon the confirm mail type
     *
     * @param aid                  random id present in mail url.
     * @param adminConfirmMailType adminConfirmMailType
     * @return AdministratorEntity instance
     */
    @Override
    public AdministratorEntity getAdministratorEntityByMailType(String aid,
                                                                HTypeAdminConfirmMailType adminConfirmMailType) {
        // 入力チェック
        ArgumentCheckUtil.assertNotEmpty("password", aid);
        ArgumentCheckUtil.assertNotNull("confirmMailType", adminConfirmMailType);

        // 有効な確認メール情報取得
        return adminConfirmMailDao.getEntityByType(aid, adminConfirmMailType);
    }

}
