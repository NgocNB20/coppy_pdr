package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.AllDownloadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayChangeGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DownloadBottomGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DownloadTopGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.SearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVItems;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRDateGreaterEqual;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRItems;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRNumberGreaterEqual;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hmbase.constant.ValidatorConstants;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

/**
 * 商品管理：商品検索ページ
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@HVRItems(target = "searchCategoryId", comparison = "searchCategoryIdItems",
          groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
@HVRDateGreaterEqual(target = "searchRegOrUpTimeTo", comparison = "searchRegOrUpTimeFrom",
                     groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
@HVRNumberGreaterEqual(target = "searchMaxSalesPossibleStockCount", comparison = "searchMinSalesPossibleStockCount",
                       groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
@HVRNumberGreaterEqual(target = "searchMaxPrice", comparison = "searchMinPrice",
                       groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
public class GoodsModel extends AbstractModel {

    /**
     * コンストラクタ<br/>
     * 初期値の設定<br/>
     */
    public GoodsModel() {
        setSite("1");
    }

    /**
     * サイト<br/>
     */
    @NotEmpty(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String site;

    /**
     * ページ番号<br/>
     */
    private String pageNumber;

    /**
     * 最大表示件数<br/>
     */
    private int limit;

    /**
     * ソート項目<br/>
     */
    private String orderField;

    /**
     * ソート条件<br/>
     */
    private boolean orderAsc;

    /**
     * 検索結果総件数<br/>
     */
    private int totalCount;

    /**
     * 検索一覧<br/>
     */
    private List<GoodsResultItem> resultItems;

    /**
     * 行番号<br/>
     */
    private int resultIndex;

    /**
     * 検索条件保持<br/>
     */
    private GoodsSearchForBackDaoConditionDto goodsSearchForBackDaoConditionDto;

    /* カテゴリパス */
    /**
     * カテゴリパスマップ
     */
    private Map<String, String> categoryPathMap;

    /************************************
     ** スコープ情報の初期化用
     ************************************/
    /**
     * 登録更新中フラグ<br/>
     */
    private boolean inputingFlg = false;

    /************************************
     ** ソート項目
     ************************************/
    /**
     * 商品グループコード<br/>
     */
    private String goodsGroupCodeSort;

    /**
     * 商品コード<br/>
     */
    private String goodsCodeSort;

    /**
     * 商品名<br/>
     */
    private String goodsGroupNameSort;

    /**
     * 規格1<br/>
     */
    private String unitValue1Sort;

    /**
     * 規格2<br/>
     */
    private String unitValue2Sort;

    /**
     * 公開状態PC<br/>
     */
    private String goodsOpenStatusPCSort;

    /**
     * 公開開始日時PC<br/>
     */
    private String goodsOpenStartTimePCSort;

    /**
     * 公開終了日時PC<br/>
     */
    private String goodsOpenEndTimePCSort;

    /**
     * 販売状態PC<br/>
     */
    private String saleStatusPCSort;

    /**
     * 販売開始日時PC<br/>
     */
    private String saleStartTimePCSort;

    /**
     * 販売終了日時PC<br/>
     */
    private String saleEndTimePCSort;

    /**
     * 単価<br/>
     */
    private String goodsPriceSort;

    /**
     * 個別配送<br/>
     */
    private String individualDeliveryTypeSort;

    /************************************
     ** 検索条件
     ************************************/
    /**
     * カテゴリーID<br/>
     */
    @HVSpecialCharacter(allowPunctuation = true,
                        groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String searchCategoryId;

    /**
     * カテゴリーID<br/>
     */
    private Map<String, String> searchCategoryIdItems;

    /**
     * 商品グループコード<br/>
     */
    @Length(min = 0, max = ValidatorConstants.LENGTH_GOODS_GROUP_CODE_MAXIMUM,
            groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVBothSideSpace(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVSpecialCharacter(allowPunctuation = true,
                        groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @Pattern(regexp = ValidatorConstants.REGEX_GOODS_GROUP_CODE,
             message = ValidatorConstants.MSGCD_REGEX_GOODS_GROUP_CODE,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCHankaku
    private String searchGoodsGroupCode;

    /**
     * 商品コード<br/>
     */
    @Length(min = 0, max = ValidatorConstants.LENGTH_GOODS_CODE_MAXIMUM,
            groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVBothSideSpace(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVSpecialCharacter(allowPunctuation = true,
                        groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @Pattern(regexp = ValidatorConstants.REGEX_GOODS_CODE, message = ValidatorConstants.MSGCD_REGEX_GOODS_CODE,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCHankaku
    private String searchGoodsCode;

    /**
     * JANコード<br/>
     */
    @Length(min = 0, max = ValidatorConstants.LENGTH_JAN_CODE_MAXIMUM,
            groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVBothSideSpace(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVSpecialCharacter(allowPunctuation = true,
                        groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @Pattern(regexp = ValidatorConstants.REGEX_JAN_CODE, message = ValidatorConstants.MSGCD_REGEX_JAN_CODE,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCHankaku
    private String searchJanCode;

    /**
     * カタログコード<br/>
     */
    @Length(max = ValidatorConstants.LENGTH_CATALOG_CODE_MAXIMUM,
            groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVSpecialCharacter(allowPunctuation = true,
                        groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @Pattern(regexp = ValidatorConstants.REGEX_CATALOG_CODE, message = ValidatorConstants.MSGCD_REGEX_CATALOG_CODE,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCHankaku
    private String searchCatalogCode;

    /**
     * 下限販売可能在庫数<br/>
     */
    @HVNumber(minus = true, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @Length(min = 0, max = 6, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCNumber
    private String searchMinSalesPossibleStockCount;

    /**
     * 上限販売可能在庫数<br/>
     */
    @HVNumber(minus = true, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @Length(min = 0, max = 6, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCNumber
    private String searchMaxSalesPossibleStockCount;

    /**
     * 商品名<br/>
     */
    @Length(min = 0, max = 120, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVBothSideSpace(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVSpecialCharacter(allowPunctuation = true,
                        groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String searchGoodsGroupName;

    /**
     * 下限価格<br/>
     */
    @Digits(integer = 8, fraction = 0, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVNumber(minus = true, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @Length(min = 0, max = 8, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCNumber
    private String searchMinPrice;

    /**
     * 上限価格<br/>
     */
    @Digits(integer = 8, fraction = 0, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVNumber(minus = true, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @Length(min = 0, max = 8, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCNumber
    private String searchMaxPrice;

    /**
     * 公開状態<br/>
     */
    @HVItems(target = HTypeOpenDeleteStatus.class,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String[] goodsOpenStatusArray;

    /**
     * 商品公開状態Items<br/>
     */
    private Map<String, String> goodsOpenStatusItems;

    /**
     * 商品販売状態<br/>
     */
    @HVItems(target = HTypeGoodsSaleStatus.class,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String[] goodsSaleStatusArray;

    /**
     * 商品販売状態Items<br/>
     */
    private Map<String, String> goodsSaleStatusItems;

    /**
     * 登録日／更新日選択フラグ
     */
    @HVItems(targetArray = {"0", "1"}, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String selectRegistOrUpdate;

    /**
     * 登録／更新日[from]<br/>
     */
    @HVDate(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String searchRegOrUpTimeFrom;

    /**
     * 登録／更新日[to]<br/>
     */
    @HVDate(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String searchRegOrUpTimeTo;

    /**
     * CSVダウンロード件数限界値
     */
    private Integer csvLimit;

    /**
     * 全件出力タイプ<br />
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}", groups = AllDownloadGroup.class)
    private String goodsOutDataAll;

    /**
     * 全件出力タイプアイテム<br />
     */
    private Map<String, String> goodsOutDataAllItems;

    /**
     * 選択出力タイプ<br />
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}", groups = DownloadTopGroup.class)
    private String goodsOutDataSelectTop;

    /**
     * 選択出力タイプアイテム<br />
     */
    private Map<String, String> goodsOutDataSelectTopItems;

    /**
     * 選択出力タイプ<br />
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}", groups = DownloadBottomGroup.class)
    private String goodsOutDataSelectBottom;

    /**
     * 選択出力タイプアイテム<br />
     */
    private Map<String, String> goodsOutDataSelectBottomItems;

    /**
     * 検索結果表示判定<br/>
     *
     * @return true=検索結果がnull以外(0件リスト含む), false=検索結果がnull
     */
    public boolean isResult() {
        return getResultItems() != null;
    }
}
