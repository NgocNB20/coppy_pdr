/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.administrator.AdministratorSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 運営者検索・詳細・削除確認Helper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class AdministratorHelper {

    /**
     * 日付関連Helper取得
     */
    public DateUtility dateUtility;

    /**
     * コントローラー
     *
     * @param dateUtility
     */
    @Autowired
    public AdministratorHelper(DateUtility dateUtility) {
        this.dateUtility = dateUtility;
    }

    /************************************
     ** 運営者検索用
     ************************************/
    /**
     * 検索条件Dtoの作成<br/>
     *
     * @param indexPage 運営者一覧ページ
     * @return 運営者索条件Dto
     */
    public AdministratorSearchForDaoConditionDto toAdministratorConditionDtoForSearch(AdministratorModel indexPage) {
        AdministratorSearchForDaoConditionDto administratorConditionDto =
                        (AdministratorSearchForDaoConditionDto) ApplicationContextUtility.getBean(
                                        AdministratorSearchForDaoConditionDto.class);
        return administratorConditionDto;
    }

    /**
     * 検索結果をページに反映<br/>
     *
     * @param adminList 検索結果リスト
     * @param indexPage 運営者一覧ページ
     */
    public void toPageForSearch(List<AdministratorEntity> adminList, AdministratorModel model) {

        // オフセット + 1をNoにセット
        int index = 1;
        List<AdministratorPageItem> resultItemList = new ArrayList<>();
        for (AdministratorEntity admin : adminList) {
            AdministratorPageItem item = ApplicationContextUtility.getBean(AdministratorPageItem.class);
            item.setResultNo(index++);
            item.setAdministratorSeq(admin.getAdministratorSeq());
            item.setResultAdministratorId(admin.getAdministratorId());
            item.setResultAdministratorLastName(admin.getAdministratorLastName());
            item.setResultAdministratorFirstName(admin.getAdministratorFirstName());
            item.setResultAdministratorLastKana(admin.getAdministratorLastKana());
            item.setResultAdministratorFirstKana(admin.getAdministratorFirstKana());
            item.setResultMail(admin.getMail());
            item.setResultAdministratorStatus(admin.getAdministratorStatus().getLabel());
            item.setResultUseStartDate(admin.getUseStartDate());
            item.setResultUseEndDate(admin.getUseEndDate());
            // 運営者には必ず所属権限グループがあるため null チェックは行わない
            item.setResultAdministratorGroupName(admin.getAdminAuthGroup().getAuthGroupDisplayName());
            resultItemList.add(item);
        }
        model.setResultItems(resultItemList);
    }

    /************************************
     ** 運営者詳細用
     ************************************/
    /**
     * ページに反映<br/>
     *
     * @param admin 運営者エンティティ
     * @param page  運営者詳細ページ
     */
    public void toPageForLoadDetails(AdministratorEntity admin, AdministratorModel model) {

        if (admin == null) {
            return;
        }

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
        model.setAdministratorStatus(admin.getAdministratorStatus());
        model.setUseStartDate(admin.getUseStartDate());
        model.setUseEndDate(admin.getUseEndDate());

        // admin オブジェクトがあるのであれば権限グループ情報は必ずあるため null チェックは行わない
        model.setAdministratorGroupName(admin.getAdminAuthGroup().getAuthGroupDisplayName());
        model.setAdministratorEntity(admin);

        model.setPasswordChangeTime(admin.getPasswordChangeTime());
        model.setAccountLockTime(admin.getAccountLockTime());
        model.setLoginFailureCount(admin.getLoginFailureCount());
        model.setPasswordExpiryDate(dateUtility.formatYmdWithSlash(admin.getPasswordExpiryDate()));

        // Get the maximum count for entering the invalid password
        int maxLoginFailureCount = PropertiesUtil.getSystemPropertiesValueToInt("admin.account.lock.count");

        // Check whether account is locked or not
        if (admin.getAccountLockTime() != null && admin.getLoginFailureCount() >= maxLoginFailureCount) {
            model.setLoginFailureAccountLock(true);
        } else if (admin.getPasswordExpiryDate() != null) {
            // Get the next day of password expiry date
            Timestamp nextDayOfPwdExpiryDate =
                            dateUtility.getAmountDayTimestamp(1, true, admin.getPasswordExpiryDate());
            if (dateUtility.isBeforeCurrentDate(nextDayOfPwdExpiryDate)) {
                model.setPwdExpiredAccountLock(true);
            }
        } else {
            model.setLoginFailureAccountLock(false);
            model.setPwdExpiredAccountLock(false);
        }

        // パスワード変更要求フラグ
        HTypePasswordNeedChangeFlag passwordNeedChangeFlag = admin.getPasswordNeedChangeFlag();
        model.setPasswordNeedChangeFlag(
                        passwordNeedChangeFlag == null ? HTypePasswordNeedChangeFlag.OFF : passwordNeedChangeFlag);
    }

    /************************************
     ** 運営者削除確認用
     ************************************/
    /**
     * ページに反映<br/>
     *
     * @param admin 運営者詳細Dto
     * @param page  運営者削除確認ページ
     */
    public void toPageForLoadDeleteConfirm(AdministratorEntity admin, AdministratorModel model) {

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
            model.setAdministratorFirstName(admin.getAdministratorFirstName());
            model.setAdministratorLastName(admin.getAdministratorLastName());
            model.setAdministratorName(administratorName);
            model.setAdministratorFirstKana(admin.getAdministratorFirstKana());
            model.setAdministratorLastKana(admin.getAdministratorLastKana());
            model.setAdministratorKana(administratorKana);
            model.setAdministratorMail(admin.getMail());
            model.setAdministratorStatus(admin.getAdministratorStatus());
            model.setUseStartDate(admin.getUseStartDate());
            model.setUseEndDate(admin.getUseEndDate());
            model.setAdministratorGroupName(admin.getAdminAuthGroup().getAuthGroupDisplayName());
        }

        // 運営者エンティティ
        model.setAdministratorEntity(admin);

    }

    /**
     * 削除する運営者情報の作成<br/>
     *
     * @param page 運営者削除確認画面
     * @return 削除する運営者情報
     */
    public AdministratorEntity toAdministratorEntityForAdministratorDelete(AdministratorModel model) {

        AdministratorEntity admin = ApplicationContextUtility.getBean(AdministratorEntity.class);
        admin.setAdministratorSeq(model.getAdministratorSeq());
        admin.setAdministratorStatus(model.getAdministratorStatus());
        admin.setAdministratorId(model.getAdministratorId());
        admin.setAdministratorPassword(model.getAdministratorPassword());
        admin.setMail(model.getAdministratorMail());
        admin.setAdministratorLastName(model.getAdministratorLastName());
        admin.setAdministratorFirstName(model.getAdministratorFirstName());
        admin.setAdministratorLastKana(model.getAdministratorLastKana());
        admin.setAdministratorFirstKana(model.getAdministratorFirstKana());
        admin.setUseStartDate(model.getUseStartDate());
        admin.setUseEndDate(model.getUseEndDate());
        admin.setAdminAuthGroupSeq(Integer.parseInt(model.getAdministratorGroupSeq()));

        return admin;
    }
}
