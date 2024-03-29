/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.favorite;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.AllDownloadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayChangeGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DownloadBottomGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DownloadTopGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.SearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVItems;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRDateGreaterEqual;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFavoriteSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hmbase.constant.ValidatorConstants;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
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
 * 商品管理：お気に入り商品検索ページ
 *
 * @author takashima
 */
@Data
@EqualsAndHashCode(callSuper = false)
@HVRDateGreaterEqual(target = "searchSaleStartTimeTo", comparison = "searchSaleStartTimeFrom",
                     groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
@HVRDateGreaterEqual(target = "searchSaleEndTimeTo", comparison = "searchSaleEndTimeFrom",
                     groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
public class FavoriteModel extends AbstractModel {

    /**
     * 受注番号（複数番号検索用）の最大数
     */
    public static final int CONDITION_GOODS_CODE_LIST_LIMIT =
                    PropertiesUtil.getSystemPropertiesValueToInt("favorite.search.goods.code.list.length");

    /**
     * コンストラクタ<br/>
     * 初期値の設定<br/>
     */
    public FavoriteModel() {
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
    private List<FavoriteResultItem> resultItems;

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
     * 顧客番号<br/>
     */
    private String customerNoSort;

    /**
     * セール状態<br/>
     */
    private String saleStatusSort;

    /**
     * セールコード<br/>
     */
    private String saleCodeSort;

    /**
     * セール終了日（セール期間To）<br/>
     */
    private String saleToSort;

    /************************************
     ** 検索条件
     ************************************/

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
     * 商品名<br/>
     */
    @Length(min = 0, max = 120, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVBothSideSpace(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVSpecialCharacter(allowPunctuation = true,
                        groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String searchGoodsGroupNameAdmin;

    /**
     * 顧客番号<br/>
     */
    @HVNumber(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @Digits(fraction = 0, integer = 9, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String searchCustomerNo;

    /**
     * セール状態<br/>
     */
    @HVItems(target = HTypeFavoriteSaleStatus.class,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String[] favoriteSaleStatusArray;

    /**
     * セール状態<br/>
     */
    private Map<String, String> favoriteSaleStatusItems;

    /**
     * セールコード<br/>
     */
    @HCHankaku
    @Pattern(regexp = ValidatorConstants.REGEX_SALE_CODE, message = ValidatorConstants.MSGCD_REGEX_SALE_CODE,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVBothSideSpace(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @Length(min = 0, max = 5, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String searchSaleCode;

    /**
     * セール開始日[from]<br/>
     */
    @HVDate(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String searchSaleStartTimeFrom;

    /**
     * セール開始日[to]<br/>
     */
    @HVDate(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String searchSaleStartTimeTo;

    /**
     * セール終了日[from]<br/>
     */
    @HVDate(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String searchSaleEndTimeFrom;

    /**
     * セール終了日[to]<br/>
     */
    @HVDate(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String searchSaleEndTimeTo;

    /**
     * 商品番号（複数番号検索用）
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVSpecialCharacter(allowPunctuation = true,
                        groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCHankaku
    private String conditionOrderCodeList;

    /**
     * メッセージコード(検索条件用)
     */
    private List<String> msgCodeList;

    /**
     * メッセージ引数マップ
     */
    private Map<String, String[]> msgArgMap;

    /**
     * CSVダウンロード件数限界値
     */
    private Integer csvLimit;

    /**
     * 全件出力タイプ<br />
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}", groups = AllDownloadGroup.class)
    private String favoriteOutDataAll;

    /**
     * 全件出力タイプアイテム<br />
     */
    private Map<String, String> favoriteOutDataAllItems;

    /**
     * 選択出力タイプ<br />
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}", groups = DownloadTopGroup.class)
    private String favoriteOutDataSelectTop;

    /**
     * 選択出力タイプアイテム<br />
     */
    private Map<String, String> favoriteOutDataSelectTopItems;

    /**
     * 選択出力タイプ<br />
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}", groups = DownloadBottomGroup.class)
    private String favoriteOutDataSelectBottom;

    /**
     * 選択出力タイプアイテム<br />
     */
    private Map<String, String> favoriteOutDataSelectBottomItems;

    /**
     * 検索結果表示判定<br/>
     *
     * @return true=検索結果がnull以外(0件リスト含む), false=検索結果がnull
     */
    public boolean isResult() {
        return getResultItems() != null;
    }

}
