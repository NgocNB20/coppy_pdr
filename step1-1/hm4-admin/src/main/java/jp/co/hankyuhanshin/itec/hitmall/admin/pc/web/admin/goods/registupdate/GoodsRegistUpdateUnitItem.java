/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.AddGoodsGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.DeleteGoodsGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.DownGoodsGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.UpGoodsGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.UploadUnitImageGroup;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVItems;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRDateCombo;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatusWithNoDeleted;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeLandSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hmbase.constant.ValidatorConstants;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 商品管理：商品登録更新（商品規格設定）ページ情報<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
@HVRDateCombo(dateLeftTarget = "saleStartDatePC", timeLeftTarget = "saleStartTimePC", dateRightTarget = "saleEndDatePC",
              timeRightTarget = "saleEndTimePC",
              groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                              DownGoodsGroup.class})
public class GoodsRegistUpdateUnitItem implements Serializable {

    /**
     * シリアルバージョンID<br/>
     */
    private static final long serialVersionUID = 1L;

    /************************************
     ** 商品規格項目
     ************************************/
    /**
     * 販売開始日時PC<br/>
     */
    private Timestamp saleStartDateTimePC;

    /**
     * 販売終了日時PC<br/>
     */
    private Timestamp saleEndDateTimePC;

    /**
     * No
     */
    private Integer unitDspNo;

    /**
     * 商品SEQ<br/>
     */
    private Integer goodsSeq;

    /**
     * 商品コード<br/>
     */
    @NotEmpty(groups = {ConfirmGroup.class, AddGoodsGroup.class, UpGoodsGroup.class, DownGoodsGroup.class,
                    UploadUnitImageGroup.class})
    @Pattern(regexp = ValidatorConstants.REGEX_GOODS_CODE, message = ValidatorConstants.MSGCD_REGEX_GOODS_CODE,
             groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                             DownGoodsGroup.class})
    @Length(min = 1, max = 20,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                            DownGoodsGroup.class})
    private String goodsCode;

    /**
     * 単価＝商品単価（税抜）<br/>
     * <pre>※画面非表示項目（最低価格・最高価格を表示）</pre>
     */
    @NotEmpty(groups = {ConfirmGroup.class, AddGoodsGroup.class, UpGoodsGroup.class, DownGoodsGroup.class})
    @HVNumber(groups = {ConfirmGroup.class, AddGoodsGroup.class, UpGoodsGroup.class, DownGoodsGroup.class})
    @Digits(integer = 8, fraction = 0,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, UpGoodsGroup.class, DownGoodsGroup.class})
    @HCNumber
    private String goodsPrice;

    // 2023-renew addNo5 from here
    /**
     * 価格(最低)
     */
    @NotEmpty(groups = {ConfirmGroup.class, AddGoodsGroup.class, UpGoodsGroup.class, DownGoodsGroup.class})
    @HVNumber(groups = {ConfirmGroup.class, AddGoodsGroup.class, UpGoodsGroup.class, DownGoodsGroup.class})
    @Digits(integer = 10, fraction = 0,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, UpGoodsGroup.class, DownGoodsGroup.class})
    @HCNumber
    private String goodsPriceInTaxLow;

    /**
     * 価格（最高）
     */
    @NotEmpty(groups = {ConfirmGroup.class, AddGoodsGroup.class, UpGoodsGroup.class, DownGoodsGroup.class})
    @HVNumber(groups = {ConfirmGroup.class, AddGoodsGroup.class, UpGoodsGroup.class, DownGoodsGroup.class})
    @Digits(integer = 10, fraction = 0,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, UpGoodsGroup.class, DownGoodsGroup.class})
    @HCNumber
    private String goodsPriceInTaxHight;
    // 2023-renew addNo5 to here

    /**
     * 規格表示順<br/>
     */
    private Integer orderDisplay;

    /**
     * JANコード<br/>
     */
    @Pattern(regexp = ValidatorConstants.REGEX_JAN_CODE, message = ValidatorConstants.MSGCD_REGEX_JAN_CODE,
             groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                             DownGoodsGroup.class})
    @Length(min = 0, max = ValidatorConstants.LENGTH_JAN_CODE_MAXIMUM,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                            DownGoodsGroup.class})
    private String janCode;

    /**
     * カタログコード<br/>
     */
    @Pattern(regexp = ValidatorConstants.REGEX_CATALOG_CODE, message = ValidatorConstants.MSGCD_REGEX_CATALOG_CODE,
             groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                             DownGoodsGroup.class})
    @Length(max = ValidatorConstants.LENGTH_CATALOG_CODE_MAXIMUM,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                            DownGoodsGroup.class})
    private String catalogCode;

    /**
     * 規格値１<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                                     DownGoodsGroup.class})
    @HVSpecialCharacter(allowPunctuation = true,
                        groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                                        DownGoodsGroup.class})
    @Length(min = 0, max = 100,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                            DownGoodsGroup.class})
    private String unitValue1;

    /**
     * 規格値２<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                                     DownGoodsGroup.class})
    @HVSpecialCharacter(allowPunctuation = true,
                        groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                                        DownGoodsGroup.class})
    @Length(min = 0, max = 100,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                            DownGoodsGroup.class})
    private String unitValue2;

    /**
     * 値引き前単価＝値引前単価（税込み）
     * <pre>※画面非表示項目（セール価格（最低）・セール価格（最高）を表示）</pre>
     */
    @HVNumber
    @Length(max = 8, min = 0,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                            DownGoodsGroup.class})
    @Digits(integer = 8, fraction = 0,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                            DownGoodsGroup.class})
    @HCNumber
    public String preDiscountPrice;

    // 2023-renew addNo5 from here
    /**
     * セール価格（最低）
     */
    @HVNumber
    @Length(max = 10, min = 0,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                            DownGoodsGroup.class})
    @Digits(integer = 10, fraction = 0,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                            DownGoodsGroup.class})
    @HCNumber
    private String preDiscountPriceLow;

    /**
     * セール価格（最高）
     */
    @HVNumber
    @Length(max = 10, min = 0,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                            DownGoodsGroup.class})
    @Digits(integer = 10, fraction = 0,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                            DownGoodsGroup.class})
    @HCNumber
    private String preDiscountPriceHight;
    // 2023-renew addNo5 to here

    /**
     * 販売状態PC<br/>
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}",
              groups = {ConfirmGroup.class, AddGoodsGroup.class, UpGoodsGroup.class, DownGoodsGroup.class})
    @HVItems(target = HTypeGoodsSaleStatusWithNoDeleted.class,
             groups = {ConfirmGroup.class, AddGoodsGroup.class, UpGoodsGroup.class, DownGoodsGroup.class})
    private String goodsSaleStatusPC;

    /**
     * 販売開始年月日PC<br/>
     */
    @HCDate
    @HVDate(groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                    DownGoodsGroup.class})
    @HVSpecialCharacter(allowCharacters = '/',
                        groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                                        DownGoodsGroup.class})
    private String saleStartDatePC;

    /**
     * 販売開始時刻PC<br/>
     */
    @HCDate(pattern = DateUtility.HMS)
    @HVDate(pattern = DateUtility.HMS,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                            DownGoodsGroup.class})
    @HVSpecialCharacter(allowCharacters = ':',
                        groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                                        DownGoodsGroup.class})
    private String saleStartTimePC;

    /**
     * 販売開始日時PC<br/>
     */
    @HCDate
    @HVDate(groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                    DownGoodsGroup.class})
    @HVSpecialCharacter(allowCharacters = '/',
                        groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                                        DownGoodsGroup.class})
    private String saleEndDatePC;

    /**
     * 販売終了時刻PC<br/>
     */
    @HCDate(pattern = DateUtility.HMS)
    @HVDate(pattern = DateUtility.HMS,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                            DownGoodsGroup.class})
    @HVSpecialCharacter(allowCharacters = ':',
                        groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                                        DownGoodsGroup.class})
    private String saleEndTimePC;

    /**
     * 最大購入可能数<br/>
     */
    @NotEmpty(groups = {ConfirmGroup.class, AddGoodsGroup.class, UpGoodsGroup.class, DownGoodsGroup.class})
    @HVNumber(groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                    DownGoodsGroup.class})
    @Range(min = 1, max = 9999,
           groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                           DownGoodsGroup.class})
    @Digits(integer = 4, fraction = 0,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                            DownGoodsGroup.class})
    @HCNumber
    private String purchasedMax;
    // PDR Migrate Customization from here
    /**
     * 管理商品コード
     */
    private String goodsManagementCode;

    /**
     * 商品分類コード
     */
    private Integer goodsDivisionCode;

    /**
     * カテゴリー1
     */
    private Integer goodsCategory1;

    /**
     * カテゴリー2
     */
    private Integer goodsCategory2;

    /**
     * カテゴリー3
     */
    private Integer goodsCategory3;

    /**
     * 陸送商品フラグ
     */
    private HTypeLandSendFlag landSendFlag;

    /**
     * クール便フラグ
     */
    private HTypeCoolSendFlag coolSendFlag;

    /**
     * クール便適用期間From
     */
    private String coolSendFrom;

    /**
     * クール便適用期間To
     */
    private String coolSendTo;

    /**
     * 保留フラグ
     */
    private HTypeReserveFlag reserveFlag;

    /**
     * 単位
     */
    private String unit;

    /**
     * 価格記号表示フラグ
     */
    private HTypePriceMarkDispFlag priceMarkDispFlag;

    /**
     * セール価格記号表示フラグ
     */
    private HTypeSalePriceMarkDispFlag salePriceMarkDispFlag;

    /**
     * 心意気価格保持区分
     */
    private HTypeEmotionPriceType emotionPriceType;

    /**
     * クール便適用期間を表示するか否か<br/>
     *
     * @return true：クール便適用期間を表示
     */
    public boolean isCoolSendAreaDisplay() {
        if (coolSendFrom != null) {
            return true;
        }
        return false;
    }
    // PDR Migrate Customization to here

    /**
     * 商品規格登録有無<br/>
     * 商品規格が新規登録（商品SEQ=null）かどうかを返す
     *
     * @return true=規格登録
     */
    public boolean isGoodsRegist() {
        return (goodsSeq == null);
    }

    /**
     * @return true=規格登録
     */
    public boolean isPreDiscountPriceRegist() {
        return (preDiscountPrice != null);
    }
}
