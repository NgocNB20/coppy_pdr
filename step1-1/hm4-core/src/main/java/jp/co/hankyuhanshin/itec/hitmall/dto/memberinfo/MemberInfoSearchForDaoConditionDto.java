/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeApproveStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMainMemberType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMedicalTreatmentFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberRankAutoFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMetalPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOnlineRegistFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendDirectMailFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendFaxPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSendMailPermitFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSex;
import jp.co.hankyuhanshin.itec.hmbase.dto.AbstractConditionDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * 会員Dao用検索条件Dtoクラス
 *
 * @author DtoGenerator
 */
@Data
@Component
@Scope("prototype")
public class MemberInfoSearchForDaoConditionDto extends AbstractConditionDto {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * 会員ランク
     */
    private List<String> memberRank;

    /**
     * 会員ランク自動設定
     */
    private HTypeMemberRankAutoFlag memberRankAutoFlag;

    /**
     * 会員ID
     */
    private String memberInfoId;

    /**
     * 会員SEQ
     */
    private Integer memberInfoSeq;

    /**
     * 氏名
     */
    private String memberInfoName;

    /**
     * 性別
     */
    private HTypeSex memberInfoSex;

    /**
     * 生年月日
     */
    private Timestamp memberInfoBirthday;

    /**
     * 電話番号
     */
    private String memberInfoTel;

    /**
     * 郵便番号
     */
    private String memberInfoZipCode;

    /**
     * 都道府県
     */
    private String memberInfoPrefecture;

    /**
     * 住所
     */
    private String memberInfoAddress;

    /**
     * 会員状態
     */
    private HTypeMemberInfoStatus memberInfoStatus;

    /**
     * 期間（FROM）
     */
    private String startDate;

    /**
     * 期間（TO）
     */
    private String endDate;

    /**
     * 期間種別
     */
    private String periodType;

    /**
     * 案内メール希望
     */
    private HTypeSendMailPermitFlag memberInfoSendMailPermitFlag;

    /**
     * 最終ログインユーザーエージェント
     */
    private String lastLoginUserAgent;

    /**
     * 最終ログインデバイス
     */
    private List<String> lastLoginDeviceType;

    /**
     * ポイント集計期間（FROM）
     */
    private String pointStartDate;

    /**
     * ポイント集計期間（TO）
     */
    private String pointEndDate;

    /**
     * 本会員フラグ
     */
    private HTypeMainMemberType mainMemberFlag;

    // PDR Migrate Customization from here
    //    /**
    //     *
    //     * PDR#11 08_データ連携（顧客情報）会員情報の項目追加・変更<br/>
    //     * 会員Dao用検索条件Dtoクラス<br/>
    //     *
    //     */
    /**
     * 顧客番号
     */
    private Integer customerNo;

    /**
     * 承認状態
     */
    private HTypeApproveStatus approveStatus;

    /**
     * オンライン登録状態
     */
    private HTypeOnlineRegistFlag onlineRegistFlag;

    /**
     * 顧客区分
     */
    private List<String> businessType;

    /**
     * FAXによるおトク情報希望フラグ
     */
    private HTypeSendFaxPermitFlag sendFaxPermitFlag;

    /**
     * DMによるおトク情報希望フラグ
     */
    private HTypeSendDirectMailFlag sendDirectMailFlag;

    /**
     * 金属商品価格お知らせメール
     */
    private HTypeMetalPermitFlag metalPermitFlag;

    /**
     * 診療項目1
     */
    private HTypeMedicalTreatmentFlag medicalTreatment1;

    /**
     * 診療項目2
     */
    private HTypeMedicalTreatmentFlag medicalTreatment2;

    /**
     * 診療項目3
     */
    private HTypeMedicalTreatmentFlag medicalTreatment3;

    /**
     * 診療項目4
     */
    private HTypeMedicalTreatmentFlag medicalTreatment4;

    /**
     * 診療項目5
     */
    private HTypeMedicalTreatmentFlag medicalTreatment5;

    /**
     * 診療項目6
     */
    private HTypeMedicalTreatmentFlag medicalTreatment6;

    /**
     * 診療項目7
     */
    private HTypeMedicalTreatmentFlag medicalTreatment7;

    /**
     * 診療項目8
     */
    private HTypeMedicalTreatmentFlag medicalTreatment8;

    /**
     * 診療項目9
     */
    private HTypeMedicalTreatmentFlag medicalTreatment9;

    /**
     * 診療項目10
     */
    private HTypeMedicalTreatmentFlag medicalTreatment10;

    /**
     * その他診療内容
     */
    private String medicalTreatmentMemo;
    // PDR Migrate Customization to here
}
