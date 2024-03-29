package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.AbstractGoodsRegistUpdateModel;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.AddGoodsGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.DeleteGoodsGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.DownGoodsGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.UpGoodsGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.registupdate.validation.group.UploadImageGroup;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVImageJpegFile;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVItems;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRDateCombo;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRItems;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAlcoholFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDentalMonopolySalesFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeExcludeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsClassType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGroupPriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGroupSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeIndividualDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSnsLinkFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.icon.GoodsInformationIconDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品管理：商品登録更新ページ
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@HVRDateCombo(dateLeftTarget = "goodsOpenStartDatePC", timeLeftTarget = "goodsOpenStartTimePC",
              dateRightTarget = "goodsOpenEndDatePC", timeRightTarget = "goodsOpenEndTimePC",
              groups = {ConfirmGroup.class})
@HVRItems(target = "taxRate", comparison = "taxRateItems", groups = {ConfirmGroup.class})
public class GoodsRegistUpdateModel extends AbstractGoodsRegistUpdateModel {

    /**
     * 商品タイプが試供品 かつ テキストエリアが未入力の場合エラー：PKG-3559-001-A-
     */
    public static final String MSGCD_NOT_INPUT_SAMPLE_LIMIT = "PKG-3559-001-A-";

    /**
     * 商品タイプが試供品以外 かつ テキストエリアが入力状態の場合エラー：PKG-3559-002-A-
     */
    public static final String MSGCD_INPUT_SAMPLE_LIMIT = "PKG-3559-002-A-";

    /**
     * 商品タイプが試供品 かつ プルダウンが未選択の場合エラー：PKG-3559-003-A-
     */
    public static final String MSGCD_NOT_SELECT_ONLY_BUY = "PKG-3559-003-A-";

    /**
     * 商品タイプが試供品以外 かつ プルダウンが選択状態の場合エラー：PKG-3559-004-A-
     */
    public static final String MSGCD_SELECT_ONLY_BUY = "PKG-3559-004-A-";

    /**
     * 商品タイプが定期商品 かつ購入間隔がが未設定の場合エラー：PKG-3554-009-A-
     */
    public static final String MSGCD_NOT_INPUT_PURCHASE_INTERVAL = "PKG-3554-009-A-";

    /**
     * 商品タイプが定期商品以外 かつ購入間隔が設定の場合エラー：PKG-3554-010-A-
     */
    public static final String MSGCD_INPUT_PURCHASE_INTERVAL = "PKG-3554-010-A-";

    /**
     * 商品タイプが定期商品 かつギフト設定が設定不可でない場合エラー：PKG-3554-011-A-
     */
    public static final String MSGCD_PERIODIC_GIFT_SETTING_ON = "PKG-3554-011-A-";

    /**
     * コンストラクタ<br/>
     * 初期値の設定<br/>
     */
    public GoodsRegistUpdateModel() {
        super();

        /** 修正箇所のオブジェクト名 / スタイル 設定 */
        diffObjectNameGoods = ApplicationContextUtility.getBean(GoodsEntity.class).getClass().getSimpleName();
    }

    /***************************************************************************************************************************
     ** 商品基本設定
     ***************************************************************************************************************************/
    // 2023-renew No64 from here
    /**
     * 商品名（管理用）<br/>
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 1, max = 120, groups = {ConfirmGroup.class})
    private String goodsGroupNameAdmin;
    // 2023-renew No64 to here
    /************************************
     ** 入力項目
     ************************************/
    /**
     * 登録日時<br/>
     */
    private Timestamp registTime;

    /**
     * 更新日時<br/>
     */
    private Timestamp updateTime;

    /**
     * 新着日時<br/>
     */
    @HVDate(groups = {ConfirmGroup.class})
    @HCDate
    private String whatsnewDate;

    /**
     * 税率
     */
    @NotNull(message = "{HRequiredValidator.REQUIRED_detail}", groups = {ConfirmGroup.class})
    private BigDecimal taxRate;

    /**
     * 税率Items
     */
    private Map<BigDecimal, String> taxRateItems;

    /**
     * 酒類フラグ<br/>
     */
    @HVItems(target = HTypeAlcoholFlag.class)
    private String alcoholFlag;

    /**
     * 酒類フラグ(ラジオボタン)<br/>
     */
    private Map<String, String> alcoholFlagItems;

    /************************************
     ** 外部連携設定
     ************************************/
    /**
     * SNS連携フラグItems<br/>
     */
    private Map<String, String> snsLinkFlagItems;

    /**
     * SNS連携フラグ
     */
    @HVItems(target = HTypeSnsLinkFlag.class, groups = {ConfirmGroup.class})
    private String snsLinkFlag;
    // PDR Migrate Customization from here
    /**
     * 商品販売区分⇒薬品区分
     */
    private HTypeGoodsClassType goodsClassType;

    /**
     * カタログ表示順
     */
    private Integer catalogDisplayOrder;

    /**
     * 歯科専売可否フラグ
     */
    private HTypeDentalMonopolySalesFlg dentalMonopolySalesFlg;
    // PDR Migrate Customization to here
    /**
     * 個別配送Items<br/>
     */
    private Map<String, String> individualDeliveryTypeItems;

    /**
     * 個別配送<br/>
     */
    @HVItems(target = HTypeIndividualDeliveryType.class, groups = {ConfirmGroup.class})
    private String individualDeliveryType;

    /**
     * 無料配送<br/>
     */
    private Map<String, String> freeDeliveryFlagItems;

    /**
     * 無料配送<br/>
     */
    @HVItems(target = HTypeFreeDeliveryFlag.class, groups = {ConfirmGroup.class})
    private String freeDeliveryFlag;

    /************************************
     ** 商品公開状態PC
     ************************************/
    /**
     * 商品公開状態PCItems<br/>
     */
    private Map<String, String> goodsOpenStatusPCItems;

    /**
     * 商品公開状態PC<br/>
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}", groups = {ConfirmGroup.class})
    @HVItems(target = HTypeOpenDeleteStatus.class, groups = {ConfirmGroup.class})
    private String goodsOpenStatusPC;

    /**
     * 商品公開開始日PC<br/>
     */
    @HVDate(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    @HCDate
    private String goodsOpenStartDatePC;

    /**
     * 商品公開開始時刻(時分秒)PC<br/>
     */
    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = ':', groups = {ConfirmGroup.class})
    @HCDate(pattern = "HH:mm:ss")
    private String goodsOpenStartTimePC;

    /**
     * 商品公開終了日PC<br/>
     */
    @HVDate(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    @HCDate
    private String goodsOpenEndDatePC;

    /**
     * 商品公開終了時刻(時分秒)PC<br/>
     */
    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = ':', groups = {ConfirmGroup.class})
    @HCDate(pattern = "HH:mm:ss")
    private String goodsOpenEndTimePC;

    //2023-renew No64 from here
    /**
     * 商品グループ名１<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(max = 120, groups = {ConfirmGroup.class})
    private String goodsGroupName1;

    /**
     * 商品グループ名１公開開始日時<br/>商品グループ名２公開開始日時
     */
    @HVDate(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    @HCDate
    private String goodsGroupName1OpenStartDate;

    /**
     * 商品グループ名１公開開始日時<br/>
     */
    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = ':', groups = {ConfirmGroup.class})
    @HCDate(pattern = "HH:mm:ss")
    private String goodsGroupName1OpenStartTime;

    /**
     * 商品グループ名２<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(max = 120, groups = {ConfirmGroup.class})
    private String goodsGroupName2;

    /**
     * 商品グループ名２公開開始日時<br/>
     */
    @HVDate(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    @HCDate
    private String goodsGroupName2OpenStartDate;

    /**
     * 商品グループ名２公開開始日時<br/>
     */
    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = ':', groups = {ConfirmGroup.class})
    @HCDate(pattern = "HH:mm:ss")
    private String goodsGroupName2OpenStartTime;
    //2023-renew No64 to here

    /************************************
     ** 選択されたカテゴリSEQリスト
     ************************************/
    /**
     * カテゴリSEQリスト<br/>
     */
    private List<Integer> selectedCategorySeqList;

    /************************************
     ** リスト項目
     ************************************/
    /**
     * カテゴリItem<br/>
     */
    private GoodsRegistUpdateCategoryItem category;
    /**
     * カテゴリItems<br/>
     */
    @Valid
    private List<GoodsRegistUpdateCategoryItem> categoryItems;
    // PDR Migrate Customization from here
    /**
     * シリーズ価格記号表示フラグ
     */
    private HTypeGroupPriceMarkDispFlag groupPriceMarkDispFlag;

    /**
     * シリーズセール価格記号表示フラグ
     */
    private HTypeGroupSalePriceMarkDispFlag groupSalePriceMarkDispFlag;

    /**
     * SALEアイコンフラグ
     */
    private HTypeSaleIconFlag saleIconFlag;

    /**
     * お取りおきアイコンフラグ
     */
    private HTypeReserveIconFlag reserveIconFlag;

    /**
     * NEWアイコンフラグ
     */
    private HTypeNewIconFlag newIconFlag;

    /**
     * SALEアイコン画像パス
     */
    private String imageFilePathSaleIcon;

    /**
     * お取りおきアイコン画像パス
     */
    private String imageFilePathReserveIcon;

    /**
     * NEWアイコン画像パス
     */
    private String imageFilePathNewIcon;
    // PDR Migrate Customization to here
    /***************************************************************************************************************************
     ** 商品詳細設定
     ***************************************************************************************************************************/

    /**
     * ページ番号
     */
    private Integer pageNumber;

    /**
     * 前リンク
     */
    private String prevLink;

    /**
     * 番号リンク
     */
    private String numberLink;

    /**
     * 次リンク
     */
    private String nextLink;

    /**
     * 総件数
     */
    private int totalCount;

    /************************************
     ** 入力項目
     ************************************/

    // 2023-renew searchKeyword-addition from here
    /**
     * 検索キーワード<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(max = 1000)
    public String searchKeyword;
    // 2023-renew searchKeyword-addition to here

    /**
     * metaDescription<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(
                    allowCharacters = {'!', '#', '%', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '=', '@', '[',
                                    ']', '_', '|'}, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 400, groups = {ConfirmGroup.class})
    private String metaDescription;

    /**
     * metaKeyword<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(
                    allowCharacters = {'!', '#', '%', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '=', '@', '[',
                                    ']', '_', '|'}, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 200, groups = {ConfirmGroup.class})
    private String metaKeyword;

    /**
     * 選択画像<br/>
     */
    private String selectImage;

    /************************************
     ** 全アイコン情報リスト
     ************************************/
    /**
     * インフォメーションアイコン情報リスト<br/>
     * （ショップに登録された全アイコンリスト）
     */
    private List<GoodsInformationIconDto> iconList;

    /***************************************************************************************************************************
     ** 商品詳細テキスト設定
     ***************************************************************************************************************************/
    /************************************
     ** 入力項目
     ************************************/
    /**
     * 商品納期<br/>
     */
    @Length(min = 0, max = 50, groups = {ConfirmGroup.class})
    private String deliveryType;

    /**
     * 商品説明01<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote1;

    //2023-renew No64 from here
    /**
     * 商品説明01公開開始日時<br/>
     */
    @HVDate(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    @HCDate
    private String goodsNote1OpenStartTimeDate;

    /**
     * 商品説明01公開開始日時<br/>
     */
    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = ':', groups = {ConfirmGroup.class})
    @HCDate(pattern = "HH:mm:ss")
    private String goodsNote1OpenStartTimeTime;

    /**
     * 商品説明01sub<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote1Sub;

    /**
     * 商品説明01sub公開開始日時<br/>
     */
    @HVDate(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    @HCDate
    private String goodsNote1SubOpenStartTimeDate;

    /**
     * 商品説明01Sub公開開始日時<br/>
     */
    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = ':', groups = {ConfirmGroup.class})
    @HCDate(pattern = "HH:mm:ss")
    private String goodsNote1SubOpenStartTimeTime;
    //2023-renew No64 to here
    /**
     * 商品説明02<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote2;

    //2023-renew No64 from here
    /**
     * 商品説明02公開開始日時<br/>
     */
    @HVDate(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    @HCDate
    private String goodsNote2OpenStartTimeDate;

    /**
     * 商品説明02公開開始日時<br/>
     */
    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = ':', groups = {ConfirmGroup.class})
    @HCDate(pattern = "HH:mm:ss")
    private String goodsNote2OpenStartTimeTime;

    /**
     * 商品説明02Sub<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote2Sub;

    /**
     * 商品説明02Sub公開開始日時<br/>
     */
    @HVDate(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    @HCDate
    private String goodsNote2SubOpenStartTimeDate;

    /**
     * 商品説明02Sub公開開始日時<br/>
     */
    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = ':', groups = {ConfirmGroup.class})
    @HCDate(pattern = "HH:mm:ss")
    private String goodsNote2SubOpenStartTimeTime;
    //2023-renew No64 to here

    /**
     * 商品説明03<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote3;

    /**
     * 商品説明04<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote4;

    //2023-renew No64 from here
    /**
     * 商品説明04公開開始日時<br/>
     */
    @HVDate(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    @HCDate
    private String goodsNote4OpenStartTimeDate;

    /**
     * 商品説明04公開開始日時<br/>
     */
    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = ':', groups = {ConfirmGroup.class})
    @HCDate(pattern = "HH:mm:ss")
    private String goodsNote4OpenStartTimeTime;

    /**
     * 商品説明04Sub<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote4Sub;

    /**
     * 商品説明04Sub公開開始日時<br/>
     */
    @HVDate(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    @HCDate
    private String goodsNote4SubOpenStartTimeDate;

    /**
     * 商品説明04Sub公開開始日時<br/>
     */
    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = ':', groups = {ConfirmGroup.class})
    @HCDate(pattern = "HH:mm:ss")
    private String goodsNote4SubOpenStartTimeTime;
    //2023-renew No64 to here

    /**
     * 商品説明05<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote5;

    /**
     * 商品説明06<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    // 2023-renew No19 from here
    @Length(min = 0, max = 8000, groups = {ConfirmGroup.class})
    // 2023-renew No19 to here
    private String goodsNote6;

    /**
     * 商品説明07<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote7;

    /**
     * 商品説明08<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote8;

    /**
     * 商品説明09<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote9;

    /**
     * 商品説明10<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote10;

    //2023-renew No64 from here
    /**
     * 商品説明10公開開始日時<br/>
     */
    @HVDate(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    @HCDate
    private String goodsNote10OpenStartTimeDate;

    /**
     * 商品説明10公開開始日時<br/>
     */
    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = ':', groups = {ConfirmGroup.class})
    @HCDate(pattern = "HH:mm:ss")
    private String goodsNote10OpenStartTimeTime;

    /**
     * 商品説明10Sub<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote10Sub;

    /**
     * 商品説明10Sub公開開始日時<br/>
     */
    @HVDate(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    @HCDate
    private String goodsNote10SubOpenStartTimeDate;

    /**
     * 商品説明10Sub公開開始日時<br/>
     */
    @HVDate(pattern = "HH:mm:ss", groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = ':', groups = {ConfirmGroup.class})
    @HCDate(pattern = "HH:mm:ss")
    private String goodsNote10SubOpenStartTimeTime;
    //2023-renew No64 to here

    /**
     * 商品説明11<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote11;

    /**
     * 商品説明12<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote12;

    /**
     * 商品説明13<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote13;

    /**
     * 商品説明14<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote14;

    /**
     * 商品説明15<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote15;

    /**
     * 商品説明16<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote16;

    /**
     * 商品説明17<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote17;

    /**
     * 商品説明18<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote18;

    /**
     * 商品説明19<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote19;

    /**
     * 商品説明20<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote20;

    // 2023-renew No11 from here
    /**
     * 商品説明21<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote21;
    // 2023-renew No11 to here

    // 2023-renew No0 from here
    /**
     * 商品説明22<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String goodsNote22;
    // 2023-renew No0 to here

    /***************************************************************************************************************************
     ** 受注連携設定
     ***************************************************************************************************************************/
    /************************************
     ** 入力項目
     ************************************/
    /**
     * 受注連携設定01<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String orderSetting1;

    /**
     * 受注連携設定02<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String orderSetting2;

    /**
     * 受注連携設定03<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String orderSetting3;

    /**
     * 受注連携設定04<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String orderSetting4;

    /**
     * 受注連携設定05<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String orderSetting5;

    /**
     * 受注連携設定06<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String orderSetting6;

    /**
     * 受注連携設定07<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String orderSetting7;

    /**
     * 受注連携設定08<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String orderSetting8;

    /**
     * 受注連携設定09<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String orderSetting9;

    /**
     * 受注連携設定10<br/>
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(min = 0, max = 4000, groups = {ConfirmGroup.class})
    private String orderSetting10;

    /***************************************************************************************************************************
     ** 商品規格設定
     ***************************************************************************************************************************/
    /************************************
     ** 規格表示設定
     ************************************/
    /**
     * 規格管理フラグ<br/>
     */
    private Map<String, String> unitManagementFlagItems;

    /**
     * 規格管理フラグ<br/>
     */
    private String unitManagementFlag;

    /**
     * 規格１表示名<br/>
     */
    @HVSpecialCharacter(allowPunctuation = true,
                        groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                                        DownGoodsGroup.class})
    @Length(min = 0, max = 100,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                            DownGoodsGroup.class})
    private String unitTitle1;

    /**
     * 規格２表示名<br/>
     */
    @HVSpecialCharacter(allowPunctuation = true,
                        groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                                        DownGoodsGroup.class})
    @Length(min = 0, max = 100,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                            DownGoodsGroup.class})
    private String unitTitle2;

    /**
     * 値引きコメント<br/>
     */
    @HVSpecialCharacter(allowPunctuation = true,
                        groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                                        DownGoodsGroup.class})
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    @Length(min = 0, max = 50,
            groups = {ConfirmGroup.class, AddGoodsGroup.class, DeleteGoodsGroup.class, UpGoodsGroup.class,
                            DownGoodsGroup.class})
    private String goodsPreDiscountPrice;

    /**
     * 選択表示番号<br/>
     */
    private Integer selectDspNo;

    /************************************
     ** 規格設定
     ************************************/
    /**
     * 規格数（hidden項目）<br/>
     */
    private int unitCount;

    /**
     * 商品規格リスト<br/>
     */
    @Valid
    private List<GoodsRegistUpdateUnitItem> unitItems;

    /**
     * 商品販売状態PC（商品規格内）<br/>
     */
    private Map<String, String> goodsSaleStatusPCItems;

    /**
     * 商品販売状態PC（商品規格内ダミー退避用）<br/>
     */
    private Map<String, String> dummyGoodsSaleStatusPCItems;

    /************************************
     ** 判定
     ************************************/
    /**
     * 規格削除可能判定<br/>
     * 規格が1件の場合は規格削除不可
     *
     * @return true=規格削除可能
     */
    public boolean isUnitDeletable() {
        return (unitCount > 1);
    }

    /**
     * @return the goodsSaleStatusPCItems
     */
    public Map<String, String> getGoodsSaleStatusPCItems() {
        // 不具合回避用です
        if (dummyGoodsSaleStatusPCItems == null && goodsSaleStatusPCItems != null) {
            dummyGoodsSaleStatusPCItems = goodsSaleStatusPCItems;
        }
        if (dummyGoodsSaleStatusPCItems != null && goodsSaleStatusPCItems == null) {
            goodsSaleStatusPCItems = dummyGoodsSaleStatusPCItems;
        }
        return goodsSaleStatusPCItems;
    }

    /***************************************************************************************************************************
     ** 商品画像設定
     ***************************************************************************************************************************/
    /**
     * アップロード画像ファイル削除失敗メッセージ
     */
    public static final String MSGCD_FAIL_DELETE = "AGG001403";

    /**
     * 一覧画像表示順
     */
    public static final Integer ORDERDISPLAY_LIST = 0;

    /************************************
     ** 商品グループ詳細画像項目
     ************************************/

    /**
     * 選択画像No（hidden項目）
     */
    private Integer selectImageNo;

    /**
     * 商品画像アイテムリスト
     */
    @Valid
    private List<GoodsRegistUpdateImageItem> goodsImageItems;

    // *****************************************************************************
    // 新HIT-MALL：複数画像アップロード - START
    // *****************************************************************************
    /**
     * 商品グループ詳細画像
     */
    @JsonIgnore
    @HVImageJpegFile(groups = UploadImageGroup.class)
    private MultipartFile[] uploadedGoodsImages;

    /***************************************************************************************************************************
     ** 商品在庫設定
     ***************************************************************************************************************************/
    /************************************
     ** 入力項目
     ************************************/
    /**
     * 在庫管理フラグ<br/>
     */
    private String stockManagementFlag;

    /************************************
     ** リスト項目
     ************************************/
    /**
     * 商品在庫リスト<br/>
     */
    @Valid
    private List<GoodsRegistUpdateStockItem> stockItems;

    private Integer unitImagesItemNo;

    /**
     * 選択画像<br/>
     */
    private String selectUnitImage;

    /**
     * 選択画像No（hidden項目）
     */
    private Integer selectUnitImageNo;

    /**
     * 規格画像リスト
     */
    private List<GoodsRegistUpdateUnitImage> unitImagesItems;

    /************************************
     ** コンボボックス
     ************************************/
    /**
     * 在庫管理フラグ<br/>
     */
    private Map<String, String> stockManagementFlagItems;

    /************************************
     ** 判定
     ************************************/
    /**
     * 商品規格0件判定<br/>
     *
     * @return true=商品規格0件
     */
    public boolean isGoodsUnitNoData() {
        return (unitCount == 0);
    }

    /***************************************************************************************************************************
     ** 関連商品設定
     ***************************************************************************************************************************/
    /************************************
     ** 関連商品リスト項目
     ************************************/
    /**
     * 関連商品グループ情報<br/>
     */
    private List<GoodsRelationEntity> tmpGoodsRelationEntityList;

    /**
     * 関連商品グループ情報（ページ間リダイレクト受け渡し用）<br/>
     */
    private List<GoodsRelationEntity> redirectGoodsRelationEntityList;

    /**
     * 関連商品リスト<br/>
     */
    @Valid
    private List<GoodsRegistUpdateRelationItem> relationItems;

    /**
     * 関連商品リスト（空）<br/>
     */
    @Valid
    private List<GoodsRegistUpdateRelationItem> relationNoItems;

    // 2023-renew No21 from here

    /**
     * よく一緒に購入される商品グループ情報<br/>
     */
    private List<GoodsTogetherBuyGroupEntity> tmpGoodsTogetherBuyGroupEntityList;

    /**
     * よく一緒に購入される商品グループ情報（ページ間リダイレクト受け渡し用）<br/>
     */
    private List<GoodsTogetherBuyGroupEntity> redirectGoodsTogetherBuyGroupEntityList;

    /**
     * よく一緒に購入される商品リスト（空）<br/>
     */
    @Valid
    private List<GoodsRegistUpdateTogetherBuyItem> togetherBuyItems;

    /**
     * 除外フラグ
     */
    private HTypeExcludeFlag excludingFlag;

    /**
     * 除外
     */
    private boolean excluding;

    // 2023-renew No21 to here

    /************************************
     ** 関連商品リスト表示項目
     ************************************/
    /**
     * 関連商品リスト 選択関連商品グループコード<br/>
     */
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String selectRelationGoodsGroupCode;

    // 2023-renew No21 from here
    /**
     * よく一緒に購入される商品リスト 選択よく一緒に購入される商品グループコード<br/>
     */
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String selectGoodsTogetherBuyGroupCode;
    // 2023-renew No21 to here

    /************************************
     ** 表示判定
     ************************************/
    /**
     * 関連商品追加可能判定<br/>
     *
     * @return true=関連商品追加可能
     */
    public boolean isAddPossible() {
        if (tmpGoodsRelationEntityList == null) {
            tmpGoodsRelationEntityList = new ArrayList<>();
        }
        return (tmpGoodsRelationEntityList.size() < getGoodsRelationAmount());
    }

    /***************************************************************************************************************************
     ** 商品登録更新確認ページ
     ***************************************************************************************************************************/
    /************************************
     ** 変更箇所表示用
     ************************************/

    /**
     * 商品DTO商品グループ名
     */
    public final String diffObjectNameGoods;

    /**
     * 商品グループ(GoodsGroupDto)変更箇所リスト
     */
    private List<String> modifiedGoodeGroupList = new ArrayList<>();

    /**
     * 商品(GoodsEntity)変更箇所リスト
     */
    private List<String> modifiedCommonGoodsList = new ArrayList<>();

    /**
     * 商品グループ（GoodsGroupEntity）の変更箇所リスト
     */
    private List<String> modifiedGoodsGroupList;

    /**
     * 商品グループ表示（GoodsGroupDisplayEntity）の変更箇所リスト
     */
    private List<String> modifiedGoodsGroupDspList;

    /**
     * 商品Dtoの商品（GoodsEntity）の変更箇所リスト
     */
    private List<List<String>> modifiedGoodsList;

    /**
     * 商品Dtoの在庫Dto（StockDto）の変更箇所リスト
     */
    private List<List<String>> modifiedStockList;

    /**
     * 関連商品（GoodsRelationEntity）の変更箇所リスト
     */
    private List<List<String>> modifiedGoodsRelationList;

    // 2023-renew No21 from here
    /**
     * よく一緒に購入されている商品（GoodsTogetherBuyGroupEntity）の変更箇所リスト
     */
    private List<List<String>> modifiedGoodsTogetherBuyGroupList;
    // 2023-renew No21 to here

    /**
     * カテゴリ登録商品リスト(変更前)<br/>
     */
    private List<CategoryGoodsEntity> masterCategoryGoodsEntityList = new ArrayList<>();

    /**
     * インフォメーションアイコンPC(変更前)<br/>
     */
    private List<String> masterInformationIconPcList = new ArrayList<>();

    /**
     * 商品Dtoリスト(変更前)<br/>
     */
    private List<GoodsDto> masterGoodsDtoList = new ArrayList<>();

    /**
     * 関連商品エンティティリスト(変更前)<br/>
     */
    private List<GoodsRelationEntity> masterGoodsRelationEntityList = new ArrayList<>();

    // 2023-renew No21 from here
    /**
     * よく一緒に購入されている商品エンティティリスト(変更前)<br/>
     */
    private List<GoodsTogetherBuyGroupEntity> masterGoodsTogetherBuyGroupEntityList = new ArrayList<>();
    // 2023-renew No21 to here

    /**
     * 商品グループ画像Dtoマップ(変更前) key=連番, values=GoodsGroupImageEntity <br/>
     */
    private Map<Integer, GoodsGroupImageEntity> masterGoodsGroupImageEntityMap = new HashMap<>();

    /************************************
     ** 表示項目（カテゴリ設定部分）
     ************************************/
    /**
     * 登録カテゴリリスト<br/>
     */
    private List<CategoryEntity> registCategoryEntityList;

    /**
     * カテゴリリスト<br/>
     */
    private List<CategoryEntity> categoryEntityList;

    /**
     * 登録カテゴリリスト（画面表示用 改行付き）<br/>
     */
    private String registCategory;

    /************************************
     ** リスト項目（商品規格設定）
     ************************************/
    /**
     * 商品規格リスト<br/>
     */
    private List<GoodsRegistUpdateUnitItem> confirmUnitItems;

    /************************************
     ** リスト項目（商品在庫部分）
     ************************************/
    /**
     * 商品在庫リスト<br/>
     */
    private GoodsRegistUpdateStockItem stock;

    /************************************
     ** 商品グループ詳細画像項目
     ************************************/

    /**
     * 商品グループ詳細画像アイテムリスト
     */
    private List<GoodsRegistUpdateImageItem> confirmGoodsImageItems;

    /**
     * 公開開始日PC<br/>
     */
    private Timestamp openStartDatePC;

    /**
     * 公開終了日PC<br/>
     */
    private Timestamp openEndDatePC;

    //2023-renew No64 from here
    /**
     * 商品グループ名1公開開始日時<br/>
     */
    private Timestamp goodsNote1OpenStartDateTime;

    /**
     * 商品グループ名1Sub公開開始日時<br/>
     */
    private Timestamp goodsNote1SubOpenStartDateTime;

    /**
     * 商品グループ名2公開開始日時<br/>
     */
    private Timestamp goodsNote2OpenStartDateTime;

    /**
     * 商品グループ名2Sub公開開始日時<br/>
     */
    private Timestamp goodsNote2SubOpenStartDateTime;

    /**
     * 商品グループ名4公開開始日時<br/>
     */
    private Timestamp goodsNote4OpenStartDateTime;

    /**
     * 商品グループ名4Sub公開開始日時<br/>
     */
    private Timestamp goodsNote4SubOpenStartDateTime;

    /**
     * 商品グループ名10公開開始日時<br/>
     */
    private Timestamp goodsNote10OpenStartDateTime;

    /**
     * 商品グループ名10Sub公開開始日時<br/>
     */
    private Timestamp goodsNote10SubOpenStartDateTime;
    //2023-renew No64 to here

    /**
     * 公開開始時間PC<br/>
     */
    private Timestamp openStartTimePC;

    /**
     * 公開終了時間PC<br/>
     */
    private Timestamp openEndTimePC;

    /**
     * 商品カテゴリーアイテムリスト
     */
    private List<GoodsCategoryTreeItem> categoryTrees;

    /**
     * 商品カテゴリーアイテムリスト（復元用）
     */
    private List<GoodsCategoryTreeItem> categoryTreesForRecovery;

    /**
     * 画面リロード後の自動スクロールのターゲットポジション<br/>
     */
    private String targetAutoScrollTagId;

    // 2023-renew No92 from here
    /**
     * アウトレットアイコンフラグ<br/>
     */
    private HTypeOutletIconFlag outletIconFlag;

    /**
     * アウトレットアイコン画像パス
     */
    private String imageFilePathOutletIcon;
    // 2023-renew No92 to here
    // PDR Migrate Customization from here

    /**
     * SALEアイコン表示判定<br/>
     *
     * @return true:○を表示 false:-を表示
     */
    public boolean isCheckedSaleIcon() {
        return HTypeSaleIconFlag.ON.equals(this.saleIconFlag);
    }

    /**
     * お取りおきアイコン表示判定<br/>
     *
     * @return true:○を表示 false:-を表示
     */
    public boolean isCheckedReserveIcon() {
        return HTypeReserveIconFlag.ON.equals(this.reserveIconFlag);
    }

    /**
     * NEWアイコン表示判定<br/>
     *
     * @return true:○を表示 false:-を表示
     */
    public boolean isCheckedNewIcon() {
        return HTypeNewIconFlag.ON.equals(this.newIconFlag);
    }

    // 2023-renew No92 from here

    /**
     * アウトレットアイコン表示判定<br/>
     *
     * @return 0:非表示 1:表示
     */
    public boolean isCheckedOutletIcon() {
        return HTypeOutletIconFlag.ON.equals(this.outletIconFlag);
    }
    // 2023-renew No92 to here
    // PDR Migrate Customization to here
}
