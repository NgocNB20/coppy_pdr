/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.campaign.modify;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hmbase.constant.ValidatorConstants;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * 広告更新
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CampaignModifyModel extends AbstractModel {

    /**
     * コンストラクタ<br/>
     * 初期値の設定<br/>
     *
     */
    public CampaignModifyModel() {
        super();

        diffObjectName = ApplicationContextUtility.getBean(CampaignEntity.class).getClass().getSimpleName();
    }

    /** 修正箇所の比較に使用するオブジェクト名 */
    private final String diffObjectName;

    /**
     * 正規表現チェック(商品グループコード・商品コード用)
     */
    public static final String regPatternForCode = "[a-zA-Z0-9_-]+";

    /** 変更前キャンペーンエンティティ */
    private CampaignEntity originalCampaignEntity;

    /** 変更箇所リスト */
    private List<String> modifiedList;

    /** キャンペーン */
    private CampaignEntity campaignEntity;

    /** 遷移フラグ */
    private boolean confirm;

    /** キャンペーンコード */
    @Pattern(regexp = regPatternForCode, message = ValidatorConstants.MSGCD_CAMPAIGN_CODE_INVALID)
    @NotEmpty
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
    @HVBothSideSpace(startWith = SpaceValidateMode.DENY_SPACE, endWith = SpaceValidateMode.DENY_SPACE)
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
     * 画面表示モード
     */
    private String md;
}
