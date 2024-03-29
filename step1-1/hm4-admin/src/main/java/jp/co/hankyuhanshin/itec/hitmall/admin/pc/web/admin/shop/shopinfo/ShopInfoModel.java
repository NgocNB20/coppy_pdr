/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.shopinfo;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVItems;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRDateCombo;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRDateGreaterEqual;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.ShopEntity;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

@Data
@HVRDateCombo(dateLeftTarget = "shopOpenStartDatePC", timeLeftTarget = "shopOpenStartTimePC",
              dateRightTarget = "shopOpenEndDatePC", timeRightTarget = "shopOpenEndTimePC")
@HVRDateGreaterEqual(target = "pointStartDateTime1", comparison = "pointStartDateTime1")
@HVRDateGreaterEqual(target = "pointStartDateTime2", comparison = "pointStartDateTime2")
public class ShopInfoModel extends AbstractModel {

    /**
     * ショップ情報エンティティ
     */
    private ShopEntity shopEntity;

    /**
     * ショップSEQ
     */
    private String shopSeq;

    /**
     * ショップ名pc
     */
    @NotEmpty
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(min = 1, max = 80)
    private String shopNamePC;

    /**
     * ショップ名携帯
     */
    //    @NotEmpty
    //    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    //    @HVSpecialCharacter(allowPunctuation = true)
    //    @Length(max = 80)
    private String shopNameMB;

    /**
     * ショップ公開状態PC<br/>
     */
    private Map<String, String> shopOpenStatusPCItems;

    /**
     * ショップ公開状態PC
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}")
    @HVItems(target = HTypeOpenStatus.class)
    private String shopOpenStatusPC;

    /**
     * ショップ公開状態MB<br/>
     */
    //    @HUItemList(component = "selectMap.shopOpenStatus")
    private Map<String, String> shopOpenStatusMBItems;

    /**
     * ショップ公開状態携帯
     */
    //    @NotEmpty
    //    @HVItems
    private String shopOpenStatusMB;

    /**
     * ショップ公開開始日（PC）
     */
    @HVDate
    @HCDate
    private String shopOpenStartDatePC;

    /**
     * ショップ公開開始時刻（PC）
     */
    @HVDate(pattern = DateUtility.HMS)
    @HCDate(pattern = DateUtility.HMS)
    private String shopOpenStartTimePC;

    /**
     * ショップ公開開始日＆時刻（PC）
     */

    private Timestamp shopOpenStartDateTimePC;

    /**
     * ショップ公開終了日（PC）
     */

    @HVDate
    @HCDate
    private String shopOpenEndDatePC;

    /**
     * ショップ公開終了時刻（PC）
     */

    @HVDate(pattern = DateUtility.HMS)
    @HCDate(pattern = DateUtility.HMS)
    private String shopOpenEndTimePC;

    /**
     * ショップ公開終了日＆時刻（PC）
     */

    private Timestamp shopOpenEndDateTimePC;

    /**
     * ショップ公開開始日（携帯）
     */

    //    @HVDate(pattern = DateUtility.YMD_SLASH)
    //    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String shopOpenStartDateMB;

    /**
     * ショップ公開開始時刻（携帯）
     */

    //    @HVDate(pattern = DateUtility.HMS)
    //    @HCDate(pattern = DateUtility.HMS)
    public String shopOpenStartTimeMB;

    /**
     * ショップ公開開始日＆時刻（携帯）
     */

    private Timestamp shopOpenStartDateTimeMB;

    /**
     * ショップ公開終了日（携帯）
     */

    //    @HVDate(pattern = DateUtility.YMD_SLASH)
    //    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String shopOpenEndDateMB;

    /**
     * ショップ公開終了時刻（携帯）
     */

    //    @HVDate(pattern = DateUtility.HMS)
    //    @HVDateCombo(dateLeftTarget = "shopOpenStartDateMB", timeLeftTarget = "shopOpenStartTimeMB", dateRightTarget = "shopOpenEndDateMB", timeRightTarget = "shopOpenEndTimeMB")
    //    @HCDate(pattern = DateUtility.HMS)
    private String shopOpenEndTimeMB;

    /**
     * ショップ公開終了日＆時刻（携帯）
     */

    private Timestamp shopOpenEndDateTimeMB;

    /**
     * meta-description
     */

    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    @HVSpecialCharacter(
                    allowCharacters = {'!', '#', '%', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '=', '@', '[',
                                    ']', '_', '|'})
    @Length(min = 1, max = 400)
    private String metaDescription;

    /**
     * meta-keyword
     */

    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    @HVSpecialCharacter(
                    allowCharacters = {'!', '#', '%', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '=', '@', '[',
                                    ']', '_', '|'})
    @Length(min = 1, max = 400)
    private String metaKeyword;

    /**
     * ポイント付与基準金額
     */

    //    @HCNumber
    private String pointAddBasePriceDefault;

    /**
     * ポイント付与率
     */

    //    @NotEmpty
    //    @HVNumber
    //    @HVNumberLength(integralMax = 3, fractionMax = 3)
    @Digits(integer = 3, fraction = 3)
    //    @HCHankaku
    //    @HCNumber(pattern = "#0.000")
    public String pointAddRateDefault;

    /**
     * ポイント適用開始日1
     */

    //    @HVDate(pattern = DateUtility.YMD_SLASH)
    //    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String pointStartDate1;

    /**
     * ポイント適用開始時刻1
     */

    //    @HVDate(pattern = DateUtility.HMS)
    //    @HCDate(pattern = DateUtility.HMS)
    private String pointStartTime1;

    /**
     * ポイント適用開始日＆時刻1
     */

    private String pointStartDateTime1;

    /**
     * ポイント適用終了日1
     */

    //    @HVDate(pattern = DateUtility.YMD_SLASH)
    //    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String pointEndDate1;

    /**
     * ポイント適用終了時刻1
     */

    //    @HVDate(pattern = DateUtility.HMS)
    //    @HVDateCombo(dateLeftTarget = "pointStartDate1", timeLeftTarget = "pointStartTime1", dateRightTarget = "pointEndDate1", timeRightTarget = "pointEndTime1")
    //    @HCDate(pattern = DateUtility.HMS)
    private String pointEndTime1;

    /**
     * ポイント適用終了日＆時刻1
     */

    //    @HVDateGreaterEqual(targetId = "pointStartDateTime1")
    private String pointEndDateTime1;

    /**
     * ポイント付与基準金額1
     */

    //    @HCNumber
    private String pointAddBasePrice1;

    /**
     * ポイント付与率1
     */

    //    @HVNumber
    //    @HVNumberLength(integralMax = 3, fractionMax = 3)
    //    @Digits(integer =3,fraction = 3)
    //    @HCHankaku
    //    @HCNumber(pattern = "#0.000")
    private String pointAddRate1;

    /**
     * ポイント適用開始日2
     */

    //    @HVDate(pattern = DateUtility.YMD_SLASH)
    //    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String pointStartDate2;

    /**
     * ポイント適用開始時刻2
     */

    //    @HVDate(pattern = DateUtility.HMS)
    //    @HCDate(pattern = DateUtility.HMS)
    private String pointStartTime2;

    /**
     * ポイント適用開始日＆時刻2
     */

    private String pointStartDateTime2;

    /**
     * ポイント適用終了日2
     */

    //    @HVDate(pattern = DateUtility.YMD_SLASH)
    //    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String pointEndDate2;

    /**
     * ポイント適用終了時刻2
     */

    //    @HVDate(pattern = DateUtility.HMS)
    //    @HVDateCombo(dateLeftTarget = "pointStartDate2", timeLeftTarget = "pointStartTime2", dateRightTarget = "pointEndDate2", timeRightTarget = "pointEndTime2")
    //    @HCDate(pattern = DateUtility.HMS)
    private String pointEndTime2;

    /**
     * ポイント適用終了日＆時刻2
     */

    //    @HVDateGreaterEqual(targetId = "pointStartDateTime2")
    private String pointEndDateTime2;

    /**
     * ポイント付与基準金額2
     */

    //    @HCNumber
    private String pointAddBasePrice2;

    /**
     * ポイント付与率2
     */
    //    @HVNumber
    //    @HVNumberLength(integralMax = 3, fractionMax = 3)
    //    @Digits(integer =3,fraction = 3)
    //    @HCHankaku
    //    @HCNumber(pattern = "#0.000")
    private String pointAddRate2;

    /**
     * 自動承認フラグ
     */
    //    @NotEmpty
    //    @HVItems
    private String autoApprovalFlag;

    /**
     * 自動承認フラグ
     */
    //    @HUItemList(component = "valuetype.autoApprovalFlag")
    private Map<String, String> autoApprovalFlagItems;

    /**
     * レビューデフォルトポイント付与数
     */
    //    @HCNumber(pattern = "####.##")
    //    @HVNumber
    //    @HVNumberLength(integralMin = 0, fractionMax = 0)
    //    @Digits(integer =0,fraction = 0)
    //    @Length(max = 8)
    private BigDecimal reviewDefaultPoint;

    /************************************
     **  判定
     ************************************/
    /**
     * ショップ公開開始日時PC、ショップ公開終了日時PCのいずれかが設定されている。<br/>
     *
     * @return true : 設定されている || false : いずれもnull
     */
    public boolean isOpenDateTimePC() {
        return shopOpenStartTimePC != null || shopOpenEndTimePC != null;
    }

    /**
     * ショップ公開開始日時MB、ショップ公開終了日時MBのいずれかが設定されている。<br/>
     *
     * @return true : 設定されている || false : いずれもnull
     */
    public boolean isOpenDateTimeMB() {
        return shopOpenStartTimeMB != null || shopOpenEndTimeMB != null;
    }

    /**
     * ポイント付与率１が存在するか判定<br/>
     *
     * @return true:存在する false:存在しない
     */
    public boolean isDispPointAddRate1() {
        return StringUtil.isNotEmpty(pointAddRate1);
    }

    /**
     * ポイント付与率２が存在するか判定<br/>
     *
     * @return true:存在する false:存在しない
     */
    public boolean isDispPointAddRate2() {
        return StringUtil.isNotEmpty(pointAddRate2);
    }
}
