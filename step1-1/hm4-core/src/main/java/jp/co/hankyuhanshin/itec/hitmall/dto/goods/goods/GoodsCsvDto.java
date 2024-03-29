/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.annotation.csv.CsvColumn;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoolSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDentalMonopolySalesFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmotionPriceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsClassType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGroupPriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGroupSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeLandSendFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitImageFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.utility.SupplyDateTimeUtility;
import jp.co.hankyuhanshin.itec.hmbase.constant.ValidatorConstants;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import static jp.co.hankyuhanshin.itec.hitmall.constant.ValidatorConstantsPdr.REGEX_COOLlSEND;
import static jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility.UPLOAD_TIME_FORMAT;
import static jp.co.hankyuhanshin.itec.hmbase.constant.ValidatorConstants.REGEX_UL_CATALOG_CODE;

/**
 * 商品CSVダウンロードDtoクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Entity
@Data
@Component
@Scope("prototype")
public class GoodsCsvDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品グループコード
     */
    @CsvColumn(order = 10, columnLabel = "商品管理番号")
    @NotEmpty
    @Pattern(regexp = ValidatorConstants.REGEX_UL_GOODS_GROUP_CODE, message = "{PKG-3680-013-S-W}")
    private String goodsGroupCode;

    // 2023-renew No64 from here
    /**
     * 商品名（管理用）
     */
    @CsvColumn(order = 20, columnLabel = "商品名")
    //    @NotEmpty
    @Length(min = 0, max = 120)
    private String goodsGroupNameAdmin;
    // 2023-renew No64 to here

    /**
     * 商品タイプ
     */
    // ※不使用項目(バリデーションなし/内部処理なし)
    @CsvColumn(order = 30, columnLabel = "商品タイプ")
    private String goodsType;

    // PDR Migrate Customization from here
    /**
     * カタログ表示順
     */
    @CsvColumn(order = 40, columnLabel = "カタログ表示順")
    @HVNumber
    @Digits(integer = 4, fraction = 0)
    private Integer catalogDisplayOrder;

    /**
     * 商品販売区分⇒薬品区分
     */
    @CsvColumn(order = 50, columnLabel = "薬品区分", enumOutputType = "value")
    private HTypeGoodsClassType goodsClassType;

    /**
     * 歯科専売可否フラグ
     */
    @CsvColumn(order = 60, columnLabel = "歯科専売可否フラグ", enumOutputType = "value")
    private HTypeDentalMonopolySalesFlg dentalMonopolySalesFlg;
    // PDR Migrate Customization to here

    /**
     * 購入限度回数
     */
    // ※不使用項目(バリデーションなし/内部処理なし)
    @CsvColumn(order = 70, columnLabel = "購入限度回数")
    //    @Range(min = 0, max = 999)
    private String purchaseLimitCount;

    /**
     * 試供品のみ購入
     */
    // ※不使用項目(バリデーションなし/内部処理なし)
    @CsvColumn(order = 80, columnLabel = "試供品のみ購入")
    private String sampleOnlyPurchaseFlag;

    /**
     * 利用可能決済方法
     */
    // ※不使用項目(バリデーションなし/内部処理なし)
    @CsvColumn(order = 90, columnLabel = "利用可能決済方法")
    //    @Pattern(regexp = "^(|[[0-9]{4}(/[0-9]{4})*]{0,100})$", message = "{AGG001204W}")
    private String possibleSettlementMethod;

    /**
     * 商品公開状態
     */
    @CsvColumn(order = 100, columnLabel = "公開状態PC", enumOutputType = "value")
    private HTypeOpenDeleteStatus goodsOpenStatusPC;

    /**
     * 商品公開開始日時
     */
    @CsvColumn(order = 110, columnLabel = "公開開始日時PC", dateFormat = DateUtility.YMD_SLASH_HMS,
               supplyDateType = SupplyDateTimeUtility.TYPE_START)
    @HVDate(pattern = UPLOAD_TIME_FORMAT, message = "{PKG-3680-002-S-W}")
    private Timestamp openStartTimePC;

    /**
     * 商品公開終了日時
     */
    @CsvColumn(order = 120, columnLabel = "公開終了日時PC", dateFormat = DateUtility.YMD_SLASH_HMS,
               supplyDateType = SupplyDateTimeUtility.TYPE_END)
    @HVDate(pattern = UPLOAD_TIME_FORMAT, message = "{PKG-3680-002-S-W}")
    private Timestamp openEndTimePC;

    /**
     * カテゴリ設定
     */
    @CsvColumn(order = 130, columnLabel = "カテゴリー設定")
    @Pattern(regexp = "^(|" + ValidatorConstants.REGEX_UL_CATEGORY_ID + "(/" + ValidatorConstants.REGEX_UL_CATEGORY_ID
                      + ")*)$", message = "{PKG-3680-006-S-W}")
    private String categoryGoodsSetting;

    //2023-renew No64 from here
    /**
     * 商品名1
     */
    @CsvColumn(order = 140, columnLabel = "商品表示名PC１")
    @Length(min = 0, max = 120)
    private String goodsGroupName1;

    /**
     * 商品名1_公開開始日時
     */
    @CsvColumn(order = 150, columnLabel = "商品表示名PC１_公開開始日時", dateFormat = DateUtility.YMD_SLASH_HMS,
               supplyDateType = SupplyDateTimeUtility.TYPE_START)
    @HVDate(pattern = UPLOAD_TIME_FORMAT, message = "{PKG-3680-002-S-W}")
    private Timestamp goodsGroupName1OpenStartTime;

    /**
     * 商品名2
     */
    @CsvColumn(order = 160, columnLabel = "商品表示名PC２")
    @Length(min = 0, max = 120)
    private String goodsGroupName2;

    /**
     * 商品名2_公開開始日時
     */
    @CsvColumn(order = 170, columnLabel = "商品表示名PC２_公開開始日時", dateFormat = DateUtility.YMD_SLASH_HMS,
               supplyDateType = SupplyDateTimeUtility.TYPE_START)
    @HVDate(pattern = UPLOAD_TIME_FORMAT, message = "{PKG-3680-002-S-W}")
    private Timestamp goodsGroupName2OpenStartTime;
    //2023-renew No64 to here

    /**
     * フリーエリアPC
     */
    @CsvColumn(order = 180, columnLabel = "シリーズPRコメント１")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(max = 4000)
    private String goodsNote10;

    //2023-renew No64 from here
    /**
     * シリーズPRコメントPC_公開開始日時
     */
    @CsvColumn(order = 190, columnLabel = "シリーズPRコメント１_公開開始日時", dateFormat = DateUtility.YMD_SLASH_HMS,
               supplyDateType = SupplyDateTimeUtility.TYPE_START)
    @HVDate(pattern = UPLOAD_TIME_FORMAT, message = "{PKG-3680-002-S-W}")
    private Timestamp goodsNote10OpenStartTime;

    /**
     * シリーズPRコメントPC2
     */
    @CsvColumn(order = 200, columnLabel = "シリーズPRコメント２")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(min = 0, max = 4000)
    private String goodsNote10Sub;

    /**
     * シリーズPRコメントPC2_公開開始日時
     */
    @CsvColumn(order = 210, columnLabel = "シリーズPRコメント２_公開開始日時", dateFormat = DateUtility.YMD_SLASH_HMS,
               supplyDateType = SupplyDateTimeUtility.TYPE_START)
    @HVDate(pattern = UPLOAD_TIME_FORMAT, message = "{PKG-3680-002-S-W}")
    private Timestamp goodsNote10SubOpenStartTime;
    //2023-renew No64 to here

    // 2023-renew No0 from here
    /**
     * 商品説明09
     */
    @CsvColumn(order = 220, columnLabel = "無料サンプルはこちら")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(min = 0, max = 4000)
    private String goodsNote9;
    // 2023-renew No0 to here

    /**
     * アイコンPC
     */
    @CsvColumn(order = 230, columnLabel = "アイコンPC")
    @Pattern(regexp = "^(|[0-9]{8}(/[0-9]{8}){0,15})$", message = "{PKG-3680-007-S-W}")
    private String informationIconPC;

    /**
     * 商品表示名モバイル
     */
    // ※不使用項目(バリデーションなし/内部処理なし)
    @CsvColumn(order = 240, columnLabel = "商品表示名モバイル")
    //    @Length(max = 120)
    private String nameMB;

    /**
     * インフォメーションアイコン携帯
     */
    // ※不使用項目(バリデーションなし/内部処理なし)
    @CsvColumn(order = 250, columnLabel = "アイコンモバイル")
    //    @Pattern(regexp = "^(|[[0-9]{8}(/[0-9]{8})*]{0,150})$", message = "{AGG001208W}")
    private String informationIconMB;

    /**
     * 索引用語
     */
    @CsvColumn(order = 260, columnLabel = "索引用語")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(max = 1000)
    private String searchKeyword;

    /**
     * 商品概要PC
     */
    @CsvColumn(order = 270, columnLabel = "商品概要１")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(min = 0, max = 4000)
    private String goodsNote1;

    //2023-renew No64 from here
    /**
     * 商品概要_公開開始日時
     */
    @CsvColumn(order = 280, columnLabel = "商品概要１_公開開始日時", dateFormat = DateUtility.YMD_SLASH_HMS,
               supplyDateType = SupplyDateTimeUtility.TYPE_START)
    @HVDate(pattern = UPLOAD_TIME_FORMAT, message = "{PKG-3680-002-S-W}")
    private Timestamp goodsNote1OpenStartTime;

    /**
     * 商品概要2
     */
    @CsvColumn(order = 290, columnLabel = "商品概要２")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(min = 0, max = 4000)
    private String goodsNote1Sub;

    /**
     * 商品概要2_公開開始日時
     */
    @CsvColumn(order = 300, columnLabel = "商品概要２_公開開始日時", dateFormat = DateUtility.YMD_SLASH_HMS,
               supplyDateType = SupplyDateTimeUtility.TYPE_START)
    @HVDate(pattern = UPLOAD_TIME_FORMAT, message = "{PKG-3680-002-S-W}")
    private Timestamp goodsNote1SubOpenStartTime;
    //2023-renew No64 to here

    /**
     * 商品詳細１PC
     */
    @CsvColumn(order = 310, columnLabel = "特徴１")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(min = 0, max = 4000)
    private String goodsNote2;

    //2023-renew No64 from here
    /**
     * 特徴_公開開始日時
     */
    @CsvColumn(order = 320, columnLabel = "特徴１_公開開始日時", dateFormat = DateUtility.YMD_SLASH_HMS,
               supplyDateType = SupplyDateTimeUtility.TYPE_START)
    @HVDate(pattern = UPLOAD_TIME_FORMAT, message = "{PKG-3680-002-S-W}")
    private Timestamp goodsNote2OpenStartTime;

    /**
     * 特徴2
     */
    @CsvColumn(order = 330, columnLabel = "特徴２")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(min = 0, max = 4000)
    private String goodsNote2Sub;

    /**
     * 特徴2_公開開始日時
     */
    @CsvColumn(order = 340, columnLabel = "特徴２_公開開始日時", dateFormat = DateUtility.YMD_SLASH_HMS,
               supplyDateType = SupplyDateTimeUtility.TYPE_START)
    @HVDate(pattern = UPLOAD_TIME_FORMAT, message = "{PKG-3680-002-S-W}")
    private Timestamp goodsNote2SubOpenStartTime;
    //2023-renew No64 to here

    /**
     * 商品詳細２PC
     */
    @CsvColumn(order = 350, columnLabel = "お客様の声")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(min = 0, max = 4000)
    private String goodsNote3;

    // 2023-renew No0 from here
    // 2023-renew No11 from here
    /**
     * 商品詳細２１
     */
    @CsvColumn(order = 360, columnLabel = "スタッフの声")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(max = 4000)
    private String goodsNote21;
    // 2023-renew No11 to here
    // 2023-renew No0 to here

    /**
     * 商品詳細３PC
     */
    @CsvColumn(order = 370, columnLabel = "注意事項１")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(min = 0, max = 4000)
    private String goodsNote4;

    //2023-renew No64 from here
    /**
     * 注意事項_公開開始日時
     */
    @CsvColumn(order = 380, columnLabel = "注意事項１_公開開始日時", dateFormat = DateUtility.YMD_SLASH_HMS,
               supplyDateType = SupplyDateTimeUtility.TYPE_START)
    @HVDate(pattern = UPLOAD_TIME_FORMAT, message = "{PKG-3680-002-S-W}")
    private Timestamp goodsNote4OpenStartTime;

    /**
     * 注意事項2
     */
    @CsvColumn(order = 390, columnLabel = "注意事項２")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(min = 0, max = 4000)
    private String goodsNote4Sub;

    /**
     * 注意事項2_公開開始日時
     */
    @CsvColumn(order = 400, columnLabel = "注意事項２_公開開始日時", dateFormat = DateUtility.YMD_SLASH_HMS,
               supplyDateType = SupplyDateTimeUtility.TYPE_START)
    @HVDate(pattern = UPLOAD_TIME_FORMAT, message = "{PKG-3680-002-S-W}")
    private Timestamp goodsNote4SubOpenStartTime;
    //2023-renew No64 to here

    /**
     * 商品詳細４PC
     */
    @CsvColumn(order = 410, columnLabel = "その他")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(min = 0, max = 4000)
    private String goodsNote5;

    /**
     * 商品詳細５PC
     */
    @CsvColumn(order = 420, columnLabel = "フリーエリアPC")
    @HVSpecialCharacter(allowPunctuation = true)
    // 2023-renew No19 from here
    @Length(min = 0, max = 8000)
    // 2023-renew No19 to here
    private String goodsNote6;

    /**
     * 商品詳細７PC
     */
    @CsvColumn(order = 430, columnLabel = "バッファエリア１")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(min = 0, max = 4000)
    private String goodsNote7;

    /**
     * 商品説明08
     */
    @CsvColumn(order = 440, columnLabel = "バッファエリア２")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(min = 0, max = 4000)
    private String goodsNote8;

    /**
     * 商品詳細１１
     */
    @CsvColumn(order = 450, columnLabel = "メーカー")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(max = 4000)
    private String goodsNote11;

    /**
     * 商品詳細１２
     */
    @CsvColumn(order = 460, columnLabel = "洗濯アイコン")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(max = 4000)
    private String goodsNote12;

    // 2023-renew No0 from here
    /**
     * 商品詳細２２
     */
    @CsvColumn(order = 470, columnLabel = "商品詳細アイコン（上位4件）")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(max = 4000)
    private String goodsNote22;
    // 2023-renew No0 to here

    /**
     * 商品詳細１３
     */
    @CsvColumn(order = 480, columnLabel = "商品詳細アイコン")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(max = 4000)
    private String goodsNote13;

    /**
     * 表示状態-商品グループ詳細画像1
     */
    // ※不使用項目(バリデーションなし/内部処理なし)
    @CsvColumn(order = 490, columnLabel = "詳細画像１_表示状態")
    private String groupImageDisplayFlag1;

    /**
     * 表示状態-商品グループ詳細画像2
     */
    // ※不使用項目(バリデーションなし/内部処理なし)
    @CsvColumn(order = 500, columnLabel = "詳細画像２_表示状態")
    private String groupImageDisplayFlag2;

    /**
     * 表示状態-商品グループ詳細画像3
     */
    // ※不使用項目(バリデーションなし/内部処理なし)
    @CsvColumn(order = 510, columnLabel = "詳細画像３_表示状態")
    private String groupImageDisplayFlag3;

    /**
     * 表示状態-商品グループ詳細画像4
     */
    // ※不使用項目(バリデーションなし/内部処理なし)
    @CsvColumn(order = 520, columnLabel = "詳細画像４_表示状態")
    private String groupImageDisplayFlag4;

    /**
     * 表示状態-商品グループ詳細画像5
     */
    // ※不使用項目(バリデーションなし/内部処理なし)
    @CsvColumn(order = 530, columnLabel = "詳細画像５_表示状態")
    private String groupImageDisplayFlag5;

    /**
     * 表示状態-商品グループ詳細画像6
     */
    // ※不使用項目(バリデーションなし/内部処理なし)
    @CsvColumn(order = 540, columnLabel = "詳細画像６_表示状態")
    private String groupImageDisplayFlag6;

    /**
     * 表示状態-商品グループ詳細画像7
     */
    // ※不使用項目(バリデーションなし/内部処理なし)
    @CsvColumn(order = 550, columnLabel = "詳細画像７_表示状態")
    private String groupImageDisplayFlag7;

    /**
     * 表示状態-商品グループ詳細画像8
     */
    // ※不使用項目(バリデーションなし/内部処理なし)
    @CsvColumn(order = 560, columnLabel = "詳細画像８_表示状態")
    private String groupImageDisplayFlag8;

    /**
     * 表示状態-商品グループ詳細画像9
     */
    // ※不使用項目(バリデーションなし/内部処理なし)
    @CsvColumn(order = 570, columnLabel = "詳細画像９_表示状態")
    private String groupImageDisplayFlag9;

    /**
     * 表示状態-商品グループ詳細画像10
     */
    // ※不使用項目(バリデーションなし/内部処理なし)
    @CsvColumn(order = 580, columnLabel = "詳細画像１０_表示状態")
    private String groupImageDisplayFlag10;

    /**
     * 値引きコメント
     */
    @CsvColumn(order = 590, columnLabel = "シリーズセールコメント")
    @Length(max = 50)
    private String goodsPreDiscountPrice;

    /**
     * 規格管理フラグ
     */
    @CsvColumn(order = 600, columnLabel = "規格表示", enumOutputType = "value")
    private HTypeUnitManagementFlag unitManagementFlag;

    /**
     * 規格1表示名
     */
    @CsvColumn(order = 610, columnLabel = "規格１表示名")
    @Length(min = 0, max = 100)
    @HVBothSideSpace(startWith = SpaceValidateMode.DENY_SPACE, endWith = SpaceValidateMode.DENY_SPACE)
    private String unitTitle1;

    /**
     * 規格2表示名
     */
    @CsvColumn(order = 620, columnLabel = "規格２表示名")
    @Length(min = 0, max = 100)
    @HVBothSideSpace(startWith = SpaceValidateMode.DENY_SPACE, endWith = SpaceValidateMode.DENY_SPACE)
    private String unitTitle2;

    // 2023-renew No0 from here
    /**
     * 商品詳細１４
     */
    @CsvColumn(order = 630, columnLabel = "商品説明１４")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(max = 4000)
    private String goodsNote14;

    /**
     * 商品詳細１５
     */
    @CsvColumn(order = 640, columnLabel = "商品説明１５")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(max = 4000)
    private String goodsNote15;

    /**
     * 商品詳細１６
     */
    @CsvColumn(order = 650, columnLabel = "商品説明１６")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(max = 4000)
    private String goodsNote16;

    /**
     * 商品詳細１７
     */
    @CsvColumn(order = 660, columnLabel = "商品説明１７")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(max = 4000)
    private String goodsNote17;

    /**
     * 商品詳細１８
     */
    @CsvColumn(order = 670, columnLabel = "商品説明１８")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(max = 4000)
    private String goodsNote18;

    /**
     * 商品詳細１９
     */
    @CsvColumn(order = 680, columnLabel = "商品説明１９")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(max = 4000)
    private String goodsNote19;

    /**
     * 商品詳細２０
     */
    @CsvColumn(order = 690, columnLabel = "商品説明２０")
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(max = 4000)
    private String goodsNote20;
    // 2023-renew No0 to here

    /**
     * 在庫管理フラグ
     */
    @CsvColumn(order = 700, columnLabel = "売り切りフラグ", enumOutputType = "value")
    private HTypeStockManagementFlag stockManagementFlag;

    /**
     * 関連商品_商品管理番号
     */
    @CsvColumn(order = 710, columnLabel = "関連商品_商品管理番号")
    @Pattern(regexp = "^(|" + ValidatorConstants.REGEX_UL_GOODS_GROUP_CODE + "(/"
                      + ValidatorConstants.REGEX_UL_GOODS_GROUP_CODE + ")*)$", message = "{PKG-3680-009-S-W}")
    private String goodsRelationGoodsGroupCode;

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品_商品管理番号
     */
    @CsvColumn(order = 720, columnLabel = "よく一緒に購入される商品_商品管理番号")
    @Pattern(regexp = "^(|" + ValidatorConstants.REGEX_UL_GOODS_GROUP_CODE + "(/"
                      + ValidatorConstants.REGEX_UL_GOODS_GROUP_CODE + ")*)$", message = "{PKG-3680-009-S-W}")
    private String goodsTogetherBuyGroupGoodsGroupCode;
    // 2023-renew No21 to here

    /**
     * 商品番号
     */
    @CsvColumn(order = 1000, columnLabel = "商品番号")
    @NotEmpty
    @Pattern(regexp = ValidatorConstants.REGEX_UL_GOODS_CODE, message = "{PKG-3680-010-S-W}")
    private String goodsCode;

    /**
     * 規格表示順
     */
    @CsvColumn(order = 1010, columnLabel = "商品表示順")
    @Range(min = 1, max = 9999)
    private Integer orderDisplay;

    /**
     * JANコード
     */
    @CsvColumn(order = 1020, columnLabel = "JANコード")
    @Pattern(regexp = ValidatorConstants.REGEX_UL_JAN_CODE, message = "{PKG-3680-012-S-W}")
    private String janCode;

    /**
     * カタログコード
     */
    @CsvColumn(order = 1030, columnLabel = "カタログ番号")
    @Pattern(regexp = "^" + REGEX_UL_CATALOG_CODE, message = "{PKG-3680-011-S-W}")
    private String catalogCode;

    /**
     * 規格1
     */
    @CsvColumn(order = 1040, columnLabel = "規格１")
    @Length(min = 0, max = 100)
    private String unitValue1;

    /**
     * 規格2
     */
    @CsvColumn(order = 1050, columnLabel = "規格２")
    @Length(min = 0, max = 100)
    private String unitValue2;

    // 2023-renew No76 from here
    /**
     * 規格画像有無
     */
    @CsvColumn(order = 1060, columnLabel = "規格画像有無", enumOutputType = "value")
    private HTypeUnitImageFlag unitImageFlag;
    // 2023-renew No76 to here

    /**
     * 販売状態
     */
    @CsvColumn(order = 1070, columnLabel = "販売状態PC", enumOutputType = "value")
    private HTypeGoodsSaleStatus saleStatusPC;

    /**
     * 販売開始日時
     */
    @CsvColumn(order = 1080, columnLabel = "販売開始日時PC", dateFormat = DateUtility.YMD_SLASH_HMS,
               supplyDateType = SupplyDateTimeUtility.TYPE_START)
    @HVDate(pattern = UPLOAD_TIME_FORMAT, message = "{PKG-3680-002-S-W}")
    private Timestamp saleStartTimePC;

    /**
     * 販売終了日時
     */
    @CsvColumn(order = 1090, columnLabel = "販売終了日時PC", dateFormat = DateUtility.YMD_SLASH_HMS,
               supplyDateType = SupplyDateTimeUtility.TYPE_END)
    @HVDate(pattern = UPLOAD_TIME_FORMAT, message = "{PKG-3680-002-S-W}")
    private Timestamp saleEndTimePC;

    // 2023-renew AddNo5 from here
    /**
     * 価格
     */
    @CsvColumn(order = 1100, columnLabel = "価格")
    @Range(min = 0, max = 99999999)
    private BigDecimal goodsPriceInTax;

    /**
     * セール価格
     */
    @CsvColumn(order = 1110, columnLabel = "セール価格")
    @Range(min = 0, max = 99999999)
    private BigDecimal preDiscountPrice;
    // 2023-renew AddNo5 to here

    /**
     * 注文上限
     */
    @CsvColumn(order = 1120, columnLabel = "最大注文可能数")
    @Range(min = 1, max = 9999)
    private BigDecimal purchasedMax;

    // PDR Migrate Customization from here
    /**
     * 保留フラグ
     */
    @CsvColumn(order = 1130, columnLabel = "保留フラグ", enumOutputType = "value")
    private HTypeReserveFlag reserveFlag;

    /**
     * 管理商品コード
     */
    @CsvColumn(order = 1140, columnLabel = "管理商品コード")
    @Pattern(regexp = "^" + REGEX_UL_CATALOG_CODE, message = "{AGG001211W}")
    private String goodsManagementCode;

    /**
     * 商品分類コード
     */
    @CsvColumn(order = 1150, columnLabel = "商品分類コード")
    @Pattern(regexp = "^[0-9]{0,4}", message = "{PDR-0010-001-L-E}")
    private String goodsDivisionCode;

    /**
     * カテゴリー1
     */
    @CsvColumn(order = 1160, columnLabel = "カテゴリー１")
    @Pattern(regexp = "^[0-9]{0,4}", message = "{PDR-0010-001-L-E}")
    private String goodsCategory1;

    /**
     * カテゴリー2
     */
    @CsvColumn(order = 1170, columnLabel = "カテゴリー２")
    @Pattern(regexp = "^[0-9]{0,4}", message = "{PDR-0010-001-L-E}")
    private String goodsCategory2;

    /**
     * カテゴリー3
     */
    @CsvColumn(order = 1180, columnLabel = "カテゴリー３")
    @Pattern(regexp = "^[0-9]{0,4}", message = "{PDR-0010-001-L-E}")
    private String goodsCategory3;

    /**
     * 陸送商品フラグ
     */
    @CsvColumn(order = 1190, columnLabel = "陸送商品フラグ", enumOutputType = "value")
    private HTypeLandSendFlag landSendFlag;

    /**
     * クール便フラグ
     */
    @CsvColumn(order = 1200, columnLabel = "クール便フラグ", enumOutputType = "value")
    private HTypeCoolSendFlag coolSendFlag;

    /**
     * クール便適用期間From<br/>
     * 値の中にyyyyがなく閏年の判定ができないため厳密な日付チェックを行っていない<br/>
     * MMが01～12、ddが01～31であるかをチェックしている<br/>
     */
    @CsvColumn(order = 1210, columnLabel = "クール便適用期間Ｆｒｏｍ")
    @Pattern(regexp = REGEX_COOLlSEND, message = "{PDR-0007-017-S-E}")
    private String coolSendFrom;

    /**
     * クール便適用期間To<br/>
     * 値の中にyyyyがなく閏年の判定ができないため厳密な日付チェックを行っていない<br/>
     * MMが01～12、ddが01～31であるかをチェックしている<br/>
     */
    @CsvColumn(order = 1220, columnLabel = "クール便適用期間Ｔｏ")
    @Pattern(regexp = REGEX_COOLlSEND, message = "{PDR-0007-017-S-E}")
    private String coolSendTo;

    /**
     * 単位
     */
    @CsvColumn(order = 1230, columnLabel = "単位")
    @Length(max = 4)
    private String unit;

    /**
     * 価格記号表示フラグ
     */
    @CsvColumn(order = 1240, columnLabel = "価格記号表示フラグ", enumOutputType = "value")
    private HTypePriceMarkDispFlag priceMarkDispFlag;

    /**
     * セール価格記号表示フラグ
     */
    @CsvColumn(order = 1250, columnLabel = "セール価格記号表示フラグ", enumOutputType = "value")
    private HTypeSalePriceMarkDispFlag salePriceMarkDispFlag;

    /**
     * 心意気価格保持区分
     */
    @CsvColumn(order = 1260, columnLabel = "心意気価格保持区分", enumOutputType = "value")
    private HTypeEmotionPriceType emotionPriceType;
    // PDR Migrate Customization to here
}
