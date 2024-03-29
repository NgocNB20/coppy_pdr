/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.update;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.AdministratorModel;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAdministratorStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Map;

/**
 * 運営者情報変更入力・確認画面Helperクラス<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class AdministratorUpdateHelper {

    /**
     * 日付関連Helper取得
     */
    public DateUtility dateUtility;

    /**
     * Reset value of login failure count
     */
    private static final Integer RESET_LOGIN_FAILURE_COUNT = 0;

    /**
     * コンストラクター
     *
     * @param dateUtility
     */
    @Autowired
    public AdministratorUpdateHelper(DateUtility dateUtility) {
        this.dateUtility = dateUtility;
    }

    /************************************
     ** 運営者情報変更入力画面用
     ************************************/
    /**
     * ページに反映<br/>
     *
     * @param administratorEntity 運営者詳細エンティティ
     * @param indexPage           運営者情報修正入力画面
     */
    public void toPageForLoad(AdministratorEntity administratorEntity, AdministratorUpdateModel model) {

        if (administratorEntity != null) {

            String administratorName = administratorEntity.getAdministratorLastName();
            if (administratorEntity.getAdministratorFirstName() != null) {
                administratorName = administratorName + " " + administratorEntity.getAdministratorFirstName();
            }
            String administratorKana = administratorEntity.getAdministratorLastKana();
            if (administratorEntity.getAdministratorFirstKana() != null) {
                administratorKana = administratorKana + " " + administratorEntity.getAdministratorFirstKana();
            }

            model.setAdministratorSeq(administratorEntity.getAdministratorSeq());
            model.setAdministratorId(administratorEntity.getAdministratorId());
            model.setAdministratorFirstName(administratorEntity.getAdministratorFirstName());
            model.setAdministratorLastName(administratorEntity.getAdministratorLastName());
            model.setAdministratorFirstKana(administratorEntity.getAdministratorFirstKana());
            model.setAdministratorLastKana(administratorEntity.getAdministratorLastKana());
            model.setAdministratorMail(administratorEntity.getMail());

            model.setAdministratorStatus(administratorEntity.getAdministratorStatus().getValue());
            model.setUseStartDate(administratorEntity.getUseStartDate());
            model.setUseEndDate(administratorEntity.getUseEndDate());
            if (!model.isEditFlag()) {
                model.setOldAdministratorStatus(administratorEntity.getAdministratorStatus().getValue());
                model.setOldUseStartDate(administratorEntity.getUseStartDate());
                model.setOldUseEndDate(administratorEntity.getUseEndDate());
            }

            model.setAdministratorGroupSeq(administratorEntity.getAdminAuthGroupSeq().toString());
            model.setAdministratorGroupName(administratorEntity.getAdminAuthGroup().getAuthGroupDisplayName());

            model.setPasswordChangeTime(administratorEntity.getPasswordChangeTime());
            model.setPasswordExpiryDate(dateUtility.formatYmdWithSlash(administratorEntity.getPasswordExpiryDate()));

            // 確認画面からの遷移時はロック解除で値が変わっている可能性があるため、元の値から取得する
            if (model.isEditFlag()) {
                model.setAccountLockTime(model.getOriginalEntity().getAccountLockTime());
                model.setLoginFailureCount(model.getOriginalEntity().getLoginFailureCount());
            } else {
                model.setAccountLockTime(administratorEntity.getAccountLockTime());
                model.setLoginFailureCount(administratorEntity.getLoginFailureCount());
            }

            // Get the maximum count for entering the invalid password
            int maxLoginFailureCount = PropertiesUtil.getSystemPropertiesValueToInt("admin.account.lock.count");

            // Check whether account is locked or not
            if (model.getAccountLockTime() != null && model.getLoginFailureCount() >= maxLoginFailureCount) {
                model.setLoginFailureAccountLock(true);
            } else if (administratorEntity.getPasswordExpiryDate() != null) {
                // Get the next day of password expiry date
                Timestamp nextDayOfPwdExpiryDate =
                                dateUtility.getAmountDayTimestamp(1, true, administratorEntity.getPasswordExpiryDate());
                if (dateUtility.isBeforeCurrentDate(nextDayOfPwdExpiryDate)) {
                    model.setPwdExpiredAccountLock(true);
                }
            } else {
                model.setLoginFailureAccountLock(false);
                model.setPwdExpiredAccountLock(false);
            }

            // パスワード変更要求フラグ
            model.setPasswordNeedChangeFlag(administratorEntity.getPasswordNeedChangeFlag());
            if (model.getPasswordNeedChangeFlag() == HTypePasswordNeedChangeFlag.ON) {
                model.setPasswordNeedChange(true);
            }
        }

        // 運営者詳細DTO
        model.setModifiedEntity(administratorEntity);

        // 確認画面から遷移した場合は作成しない、初回のみ
        if (!model.isEditFlag()) {
            // 運営者エンティティ。修正箇所比較用に持っておく。
            AdministratorEntity administratorEntityCopy = CopyUtil.deepCopy(administratorEntity);
            model.setOriginalEntity(administratorEntityCopy);
        }
    }

    /**
     * 画面から運営者情報詳細DTOに変換
     *
     * @param indexPage 運営者情報変更入力画面
     * @return 運営者情報エンティティ
     */
    public AdministratorEntity toAdministratorEntityForUpdate(AdministratorUpdateModel model) {

        // 運営者詳細AdministratorEntityを取得
        // AdministratorEntity administratorDetailsDto =
        // indexPage.getOriginalEntity();
        // 運営者情報エンティティを作成
        AdministratorEntity administratorEntity = toAdministratorEntityForConfirm(model);
        // indexPage.setModifiedEntity(administratorEntity);

        return administratorEntity;

    }

    /**
     * 画面から運営者情報エンティティに変換
     *
     * @param indexPage 運営者情報変更入力画面
     * @return 運営者情報エンティティ
     */
    public AdministratorEntity toAdministratorEntityForConfirm(AdministratorUpdateModel model) {

        for (Map.Entry<String, String> entry : model.getAdministratorGroupSeqItems().entrySet()) {
            if (entry.getKey().equalsIgnoreCase(model.getAdministratorGroupSeq())) {
                model.setAdministratorGroupName(entry.getValue());
                break;
            }
        }

        AdministratorEntity admin = ApplicationContextUtility.getBean(AdministratorEntity.class);
        admin.setShopSeq(model.getOriginalEntity().getShopSeq());
        admin.setAdministratorSeq(model.getOriginalEntity().getAdministratorSeq());
        admin.setAdministratorStatus(
                        EnumTypeUtil.getEnumFromValue(HTypeAdministratorStatus.class, model.getAdministratorStatus()));
        admin.setAdministratorId(model.getAdministratorId());
        if (StringUtils.isNotEmpty(model.getAdministratorPassword())) {
            // パスワード暗号化
            PasswordEncoder passwordEncoder =
                            ApplicationContextUtility.getBeanByName("encoderAdmin", PasswordEncoder.class);
            admin.setAdministratorPassword(passwordEncoder.encode(model.getAdministratorPassword()));
        }
        admin.setMail(model.getAdministratorMail());
        admin.setAdministratorLastName(model.getAdministratorLastName());
        admin.setAdministratorFirstName(model.getAdministratorFirstName());
        admin.setAdministratorLastKana(model.getAdministratorLastKana());
        admin.setAdministratorFirstKana(model.getAdministratorFirstKana());

        if (!model.getAdministratorStatus().equals(model.getOldAdministratorStatus())) {

            // 日付関連Helper取得
            DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

            Timestamp currentTime = dateUtility.getCurrentTime();
            if (HTypeAdministratorStatus.USE.equals(admin.getAdministratorStatus())) {
                admin.setUseStartDate(currentTime);
                admin.setUseEndDate(null);
            } else {
                admin.setUseStartDate(model.getOldUseStartDate());
                admin.setUseEndDate(currentTime);
            }
        } else {
            admin.setUseStartDate(model.getOldUseStartDate());
            admin.setUseEndDate(model.getOldUseEndDate());
        }

        admin.setRegistTime(model.getOriginalEntity().getRegistTime());
        admin.setUpdateTime(model.getOriginalEntity().getUpdateTime());

        admin.setAdminAuthGroupSeq(Integer.parseInt(model.getAdministratorGroupSeq()));
        admin.setAdminAuthGroup(ApplicationContextUtility.getBean(AdminAuthGroupEntity.class));
        admin.getAdminAuthGroup().setAdminAuthGroupSeq(Integer.parseInt(model.getAdministratorGroupSeq()));
        admin.getAdminAuthGroup().setAuthGroupDisplayName(model.getAdministratorGroupName());

        // パスワード変更要求フラグ
        model.setPasswordNeedChangeFlag(HTypePasswordNeedChangeFlag.getFlagByBoolean(model.isPasswordNeedChange()));

        // ロック情報
        if (!(StringUtil.isEmpty(model.getAdministratorPassword()))) {
            admin.setLoginFailureCount(RESET_LOGIN_FAILURE_COUNT);
            admin.setAccountLockTime(null);
            admin.setPasswordNeedChangeFlag(HTypePasswordNeedChangeFlag.ON);
        } else {
            admin.setAccountLockTime(model.getAccountLockTime());
            admin.setLoginFailureCount(model.getLoginFailureCount());
            admin.setPasswordNeedChangeFlag(model.getPasswordNeedChangeFlag());
        }

        admin.setPasswordChangeTime(model.getPasswordChangeTime());
        admin.setPasswordExpiryDate(model.getOriginalEntity().getPasswordExpiryDate());

        // パスワードSHA256暗号化済みフラグの取得追加
        admin.setPasswordSHA256EncryptedFlag(model.getOriginalEntity().getPasswordSHA256EncryptedFlag());

        return admin;
    }

    /**
     * 運営者モデルを運営者更新モデルに変換
     *
     * @param updateModel
     * @param model
     * @return 運営者更新モデル
     */
    public AdministratorUpdateModel convertModel(AdministratorUpdateModel updateModel, AdministratorModel model) {
        updateModel.setAccountLockTime(model.getAccountLockTime());
        updateModel.setLoginFailureCount(model.getLoginFailureCount());
        updateModel.setPasswordChangeTime(model.getPasswordChangeTime());
        updateModel.setPasswordExpiryDate(model.getPasswordExpiryDate());
        updateModel.setAdministratorId(model.getAdministratorId());
        updateModel.setAdministratorLastName(model.getAdministratorLastName());
        updateModel.setAdministratorLastKana(model.getAdministratorLastKana());
        updateModel.setAdministratorMail(model.getAdministratorMail());
        updateModel.setAdministratorStatus(model.getAdministratorStatus().getLabel());
        updateModel.setOldUseStartDate(model.getUseStartDate());
        updateModel.setOldUseEndDate(model.getUseEndDate());
        updateModel.setAdministratorSeq(model.getAdministratorSeq());
        updateModel.setAdministratorFirstName(model.getAdministratorFirstName());
        updateModel.setAdministratorFirstKana(model.getAdministratorFirstKana());
        updateModel.setAdministratorGroupSeq(model.getAdministratorGroupSeq());
        updateModel.setAdministratorPassword(model.getAdministratorPassword());

        return updateModel;
    }

    /************************************
     ** 運営者情報変更確認画面用
     ************************************/
    /**
     * 運営者詳細詳細DTOからページに変換
     *
     * @param admin 運営者詳細DTO
     * @param page  運営者修正確認画面
     */
    public void toPageForLoadConfirm(AdministratorEntity admin, AdministratorUpdateModel model) {

        // 運営者情報
        if (admin != null) {

            String administratorName = admin.getAdministratorLastName();
            if (admin.getAdministratorFirstName() != null) {
                administratorName = administratorName + " " + admin.getAdministratorFirstName();
            }
            String administratorKana = admin.getAdministratorLastKana();
            if (admin.getAdministratorFirstKana() != null) {
                administratorKana = administratorKana + " " + admin.getAdministratorFirstKana();
            }

            model.setAdministratorSeq(admin.getAdministratorSeq());
            model.setAdministratorId(admin.getAdministratorId());
            model.setAdministratorName(administratorName);
            model.setAdministratorKana(administratorKana);
            model.setAdministratorMail(admin.getMail());
            model.setAdministratorStatus(admin.getAdministratorStatus().getValue());
            model.setUseStartDate(admin.getUseStartDate());
            model.setUseEndDate(admin.getUseEndDate());

            model.setAdministratorPassword(admin.getAdministratorPassword());
            model.setAdministratorLastName(admin.getAdministratorLastName());
            model.setAdministratorFirstName(admin.getAdministratorFirstName());
            model.setAdministratorLastKana(admin.getAdministratorLastKana());
            model.setAdministratorFirstKana(admin.getAdministratorFirstKana());
            model.setAdministratorGroupSeqConfirm(admin.getAdminAuthGroupSeq());

            if (admin.getAdminAuthGroup() != null) {
                model.setAdministratorGroupName(admin.getAdminAuthGroup().getAuthGroupDisplayName());
            }

            model.setPasswordChangeTime(admin.getPasswordChangeTime());
            model.setAccountLockTime(admin.getAccountLockTime());
            model.setLoginFailureCount(admin.getLoginFailureCount());
            model.setPasswordExpiryDate(dateUtility.formatYmdWithSlash(admin.getPasswordExpiryDate()));

            // Get the maximum count for entering the invalid password
            int maxLoginFailureCount = PropertiesUtil.getSystemPropertiesValueToInt("admin.account.lock.count");

            // パスワードが変更された場合、フラグをすべて落とす
            if (StringUtil.isNotEmpty(admin.getAdministratorPassword())) {
                model.setModifyLoginFailureAccountLock(false);
                model.setModifyPwdExpiredAccountLock(false);
            } else if (admin.getAccountLockTime() != null && admin.getLoginFailureCount() >= maxLoginFailureCount) {
                model.setModifyLoginFailureAccountLock(true);
            } else {
                // Get the next day of password expiry date
                Timestamp nextDayOfPwdExpiryDate =
                                dateUtility.getAmountDayTimestamp(1, true, admin.getPasswordExpiryDate());
                if (dateUtility.isBeforeCurrentDate(nextDayOfPwdExpiryDate)) {
                    model.setModifyPwdExpiredAccountLock(true);
                }
            }

            // パスワード変更要求フラグ
            model.setPasswordNeedChangeFlag(admin.getPasswordNeedChangeFlag());
        }
    }
}
