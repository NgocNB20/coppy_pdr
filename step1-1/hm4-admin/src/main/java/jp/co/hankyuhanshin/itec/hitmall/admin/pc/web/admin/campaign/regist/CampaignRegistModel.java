/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.campaign.regist;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hmbase.constant.ValidatorConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 広告新規登録
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class CampaignRegistModel extends AbstractModel {

    /**
     * 画面表示モード
     */
    public static final String FLASH_MD = "md";

    /**
     * 正規表現チェック(商品グループコード・商品コード用)
     */
    public static final String regPatternForCode = "[a-zA-Z0-9_-]+";

    /** キャンペーン */
    private CampaignEntity campaignEntity;

    /** 遷移フラグ */
    private boolean confirm;

    /** 戻るボタンの表示 */
    private boolean button;

    /** キャンペーンコード */
    @NotEmpty
    @Pattern(regexp = regPatternForCode, message = ValidatorConstants.MSGCD_CAMPAIGN_CODE_INVALID)
    @Length(max = 12)
    private String campaignCode;

    /** キャンペーン名 */
    @NotEmpty
    @Length(max = 80)
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    @HVSpecialCharacter(allowPunctuation = true)
    private String campaignName;

    /** 備考 */
    @Length(max = 200)
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    @HVSpecialCharacter(allowPunctuation = true)
    private String note;

    /** リダイレクトURL */
    @URL
    @NotEmpty
    @Length(max = 200)
    private String redirectUrl;

    /** 広告コスト */
    @HVNumber
    @Digits(integer = 8, fraction = 0)
    @HCNumber
    private String campaignCost;

    /** 広告URL PC */
    private String campaignUrlPc;

    /** 不正操作を判断するためのフラグ */
    private boolean normality;

    /**
     * 新規登録フラグ
     * true : regist || false : resue
     */
    private boolean registFlg;

}
