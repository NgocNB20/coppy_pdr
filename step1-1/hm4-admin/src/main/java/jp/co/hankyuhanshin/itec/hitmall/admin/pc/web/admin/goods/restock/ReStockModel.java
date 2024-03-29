/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.AllDownloadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayChangeGroup;
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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailDeliveryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReStockStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchForBackDaoConditionDto;
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
 * 入荷お知らせ商品検索ページ
 *
 * @author st75001
 */
@Data
@EqualsAndHashCode(callSuper = false)
@HVRDateGreaterEqual(target = "reStockTimeTo", comparison = "reStockTimeFrom",
                     groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
public class ReStockModel extends AbstractModel {

    /**
     * コンストラクタ<br/>
     * 初期値の設定<br/>
     */
    public ReStockModel() {
        super();
    }

    /**
     * メッセージコード(検索条件用)
     */
    private List<String> msgCodeList;

    /**
     * メッセージ引数マップ
     */
    private Map<String, String[]> msgArgMap;

    /**
     * 商品番号（複数番号検索用）の最大数
     */
    public static final int CONDITION_GOODS_CODE_LIST_LIMIT =
            PropertiesUtil.getSystemPropertiesValueToInt("reStock.search.goods.code.list.length");

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
    private List<ReStockResultItem> resultItems;

    /**
     * 行番号<br/>
     */
    private int resultIndex;

    /**
     * 検索条件保持<br/>
     */
    ReStockSearchForBackDaoConditionDto reStockSearchForBackDaoConditionDto;

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
     * 登録顧客件数<br/>
     */
    private String registMemberCountSort;

    /**
     * 入荷状態<br/>
     */
    private String reStockStatusSort;

    /**
     * 配信ID<br/>
     */
    private String deliveryIdSort;

    /**
     * 入荷メール送信状態<br/>
     */
    private String deliveryStatusSort;

    /**
     * 入荷日時<br/>
     */
    private String restockTimeSort;

    /************************************
     ** 検索条件
     ************************************/

    /**
     * 商品グループコード<br/>
     */
    @Length(max = ValidatorConstants.LENGTH_GOODS_GROUP_CODE_MAXIMUM,
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
    @Length(max = ValidatorConstants.LENGTH_GOODS_CODE_MAXIMUM,
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
    @Length(max = 120, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVBothSideSpace(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVSpecialCharacter(allowPunctuation = true,
                        groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String searchGoodsName;

    /**
     * 顧客番号<br/>
     */
    @HVNumber(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVBothSideSpace(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @Digits(fraction = 0, integer = 9, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String searchCustomerNo;

    /**
     * 配信ID<br/>
     */
    @HVNumber(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVBothSideSpace(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @Digits(fraction = 0, integer = 10, groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String searchDeliveryId;

    /**
     * 入荷状態<br/>
     */
    @HVItems(target = HTypeReStockStatus.class,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String[] reStockStatus;

    /**
     * 入荷状態Items<br/>
     */
    private Map<String, String> reStockStatusItems;

    /**
     * 入荷お知らせメール送信状況<br/>
     */
    @HVItems(target = HTypeMailDeliveryStatus.class,
             groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    private String[] deliveryStatus;

    /**
     * 入荷お知らせメール送信状況Items<br/>
     */
    private Map<String, String> deliveryStatusItems;

    /**
     * 入荷日時[from]<br/>
     */
    @HVDate(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String reStockTimeFrom;

    /**
     * 入荷日時[to]<br/>
     */
    @HVDate(groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCDate(pattern = DateUtility.YMD_SLASH)
    private String reStockTimeTo;

    /**
     * 商品番号（複数番号検索用）
     */
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
            groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HVSpecialCharacter(allowPunctuation = true,
            groups = {SearchGroup.class, AllDownloadGroup.class, DisplayChangeGroup.class})
    @HCHankaku
    private String conditionGoodsCodeList;

    /**
     * CSVダウンロード件数限界値
     */
    private Integer csvLimit;

    /**
     * 全件出力タイプ<br />
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}", groups = AllDownloadGroup.class)
    private String reStockOutDataAll;

    /**
     * 全件出力タイプアイテム<br />
     */
    private Map<String, String> reStockOutDataAllItems;

    /**
     * 選択出力タイプ<br />
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}", groups = DownloadTopGroup.class)
    private String reStockOutDataSelectTop;

    /**
     * 選択出力タイプアイテム<br />
     */
    private Map<String, String> reStockOutDataSelectTopItems;

    /**
     * 検索結果表示判定<br/>
     *
     * @return true=検索結果がnull以外(0件リスト含む), false=検索結果がnull
     */
    public boolean isResult() {
        return getResultItems() != null;
    }
}
