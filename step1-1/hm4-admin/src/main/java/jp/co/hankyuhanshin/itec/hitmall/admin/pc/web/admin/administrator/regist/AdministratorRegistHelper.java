/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.administrator.regist;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAdministratorStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.sql.Timestamp;
import java.util.Map;

/**
 * 新規運営者登録入力・確認画面Helperクラス<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class AdministratorRegistHelper {

    /**
     * 日付関連Helper取得
     */
    public DateUtility dateUtility;

    /**
     * コンストラクタ
     *
     * @param dateUtility
     */
    @Autowired
    public AdministratorRegistHelper(DateUtility dateUtility) {
        this.dateUtility = dateUtility;
    }

    /************************************
     ** 新規運営者登録入力画面Helper
     ************************************/
    /**
     * 画面から運営者情報エンティティに変換
     *
     * @param indexPage 新規運営者登録入力画面
     * @return 運営者情報エンティティ
     */
    public AdministratorEntity toAdministratorEntityForConfirm(AdministratorRegistModel administratorRegistModel) {
        for (Map.Entry<String, String> entry : administratorRegistModel.getAdministratorGroupSeqItems().entrySet()) {
            if (entry.getKey().equalsIgnoreCase(administratorRegistModel.getAdministratorGroupSeq())) {
                administratorRegistModel.setAdministratorGroupName(entry.getValue());
                break;
            }
        }

        AdministratorEntity administratorEntity = ApplicationContextUtility.getBean(AdministratorEntity.class);
        administratorEntity.setAdministratorId(administratorRegistModel.getAdministratorId());
        administratorEntity.setAdministratorPassword(administratorRegistModel.getAdministratorPassword());
        administratorEntity.setAdministratorLastName(administratorRegistModel.getAdministratorLastName());
        administratorEntity.setAdministratorFirstName(administratorRegistModel.getAdministratorFirstName());
        administratorEntity.setAdministratorLastKana(administratorRegistModel.getAdministratorLastKana());
        administratorEntity.setAdministratorFirstKana(administratorRegistModel.getAdministratorFirstKana());
        administratorEntity.setMail(administratorRegistModel.getAdministratorMail());
        administratorEntity.setAdministratorStatus(EnumTypeUtil.getEnumFromValue(HTypeAdministratorStatus.class,
                                                                                 administratorRegistModel.getAdministratorStatus()
                                                                                ));
        administratorEntity.setAdminAuthGroupSeq(
                        StringUtils.isEmpty(administratorRegistModel.getAdministratorGroupSeq()) ?
                                        null :
                                        Integer.parseInt(administratorRegistModel.getAdministratorGroupSeq()));
        administratorRegistModel.setPasswordNeedChangeFlag(HTypePasswordNeedChangeFlag.ON);

        return administratorEntity;
    }

    /**
     * ページ情報の初期処理
     *
     * @param administratorRegistModel 新規運営者登録画面
     */
    public void clearPage(AdministratorRegistModel administratorRegistModel) {

        administratorRegistModel.setInputMd(null);
        administratorRegistModel.setAdministratorId(null);
        administratorRegistModel.setAdministratorPassword(null);
        administratorRegistModel.setAdministratorLastName(null);
        administratorRegistModel.setAdministratorFirstName(null);
        administratorRegistModel.setAdministratorLastKana(null);
        administratorRegistModel.setAdministratorFirstKana(null);
        administratorRegistModel.setAdministratorMail(null);
        administratorRegistModel.setAdministratorStatus(null);
        administratorRegistModel.setAdministratorGroupSeq(null);
        administratorRegistModel.setAdministratorGroupName(null);
    }

    /************************************
     ** 新規運営者確認画面Helper
     ************************************/
    /**
     * 運営者詳細詳細DTOからページに変換
     *
     * @param admin       運営者詳細DTO
     * @param confirmPage 運営者登録確認画面
     */
    public void toPageForLoad(AdministratorEntity admin, AdministratorRegistModel administratorRegistModel) {

        // 利用開始/終了日時セット
        Timestamp currentTime = dateUtility.getCurrentTime();
        administratorRegistModel.setUseStartDate(currentTime);
        if (administratorRegistModel.getAdministratorStatus().equals(HTypeAdministratorStatus.STOP.getValue())) {
            administratorRegistModel.setUseEndDate(currentTime);
        }
    }

    /**
     * 登録する運営者情報の作成<br/>
     *
     * @param page 運営者登録確認画面
     * @return 登録する運営者情報
     */
    public AdministratorEntity toEntityForRegist(AdministratorRegistModel administratorRegistModel) {

        // パスワード暗号化
        PasswordEncoder passwordEncoder =
                        ApplicationContextUtility.getBeanByName("encoderAdmin", PasswordEncoder.class);

        AdministratorEntity admin = ApplicationContextUtility.getBean(AdministratorEntity.class);
        admin.setAdministratorId(administratorRegistModel.getAdministratorId());
        admin.setAdministratorPassword(passwordEncoder.encode(administratorRegistModel.getAdministratorPassword()));
        admin.setAdministratorLastName(administratorRegistModel.getAdministratorLastName());
        admin.setAdministratorFirstName(administratorRegistModel.getAdministratorFirstName());
        admin.setAdministratorLastKana(administratorRegistModel.getAdministratorLastKana());
        admin.setAdministratorFirstKana(administratorRegistModel.getAdministratorFirstKana());
        admin.setMail(administratorRegistModel.getAdministratorMail());
        admin.setAdministratorStatus(EnumTypeUtil.getEnumFromValue(HTypeAdministratorStatus.class,
                                                                   administratorRegistModel.getAdministratorStatus()
                                                                  ));
        admin.setAdminAuthGroupSeq(Integer.parseInt(administratorRegistModel.getAdministratorGroupSeq()));
        admin.setUseStartDate(administratorRegistModel.getUseStartDate());
        admin.setUseEndDate(administratorRegistModel.getUseEndDate());
        admin.setPasswordNeedChangeFlag(administratorRegistModel.getPasswordNeedChangeFlag());

        return admin;
    }
}
