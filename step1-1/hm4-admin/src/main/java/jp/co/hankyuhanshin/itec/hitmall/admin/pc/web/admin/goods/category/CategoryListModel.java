/*
 * Project Name : HIT-MALL4
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRItems;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryExclusiveDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * カテゴリ管理
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@HVRItems(target = "extractCategoryName", comparison = "extractCategoryNameItems")
public class CategoryListModel extends AbstractModel {

    /**
     * カテゴリDTO
     */
    private CategoryDto categoryDto;

    /**
     * 全件出力タイプアイテム<br />
     */
    private Map<String, String> categoryOutDataAllItems;

    /**
     * 移動実行時用マップ
     */
    private Map<String, CategoryEntity> categoryMap;

    /**
     * 現在のカテゴリMAXSEQ
     */
    private Integer categoryMaxSeq;

    /**
     * CSVダウンロード件数限界値
     */
    private Integer csvLimit;

    /**
     * カテゴリ排他
     */
    private CategoryExclusiveDto categoryExclusive;

    /**
     * カテゴリー一覧が変更されます
     * POSTされる文字列：変更前のCategorySeqPath、変更後のCategorySeqPath、変更後のorderDisplay、カテゴリーの位置
     * カテゴリーの各要素の値を「,」で区切る
     * 各カテゴリ－を「/」で区切る
     **/
    @HVSpecialCharacter(allowPunctuation = true)
    private String dtCategory;

    /**
     * 一覧
     */
    private List<CategoryModelItem> resultItems;

    /**
     * 選択された行
     */
    private String resultIndex;

    /**
     * 抽出カテゴリー 表示/非表示フラグ
     */
    private boolean extractCategory;

    /**
     * 抽出カテゴリー：プルダウン値(カテゴリーID)
     */
    @HVSpecialCharacter(allowPunctuation = true)
    private String extractCategoryName;

    /**
     * 抽出カテゴリー名アイテム(プルダウン用)
     */
    private Map<String, String> extractCategoryNameItems;

    /**
     * 抽出カテゴリー(limit値)
     */
    private int extractCategoryLimit;

    /**
     * カテゴリSEQパス(画面受渡し用)
     * (カテゴリーボタンの展開保持に使用)
     */
    private String categorySeqPathTarget;

    /**
     * 初期表示フラグ(画面受渡し用)
     */
    private boolean initialDisplayList = false;

    /**
     * カテゴリー削除フラグ(カテゴリーボタンの展開保持に使用)
     */
    private boolean deleteCategory = false;

    /**
     * 抽出カテゴリー:「指定あり」かどうか(JSにて使用)
     * 指定あり：true,指定なし：false
     */
    private boolean extractCategorySpecified = false;

    //    /** @see CategoryModelItem#modify */
    //    private boolean modify;
    //
    //    /** @see CategoryModelItem#categoryId */
    //    private String categoryId;
    //
    //    /** @see CategoryModelItem#categoryName */
    //    private String categoryName;
    //
    //    /** @see CategoryModelItem#categoryLevel */
    //    private Integer categoryLevel;
    //
    //    /** @see CategoryModelItem#categorySeqPath */
    //    private String categorySeqPath;
    //
    //    /** @see CategoryModelItem#categoryOpenStatusPC */
    ////    @HCTypeLabel(component = "valuetype.openStatus")
    //    private HTypeOpenStatus categoryOpenStatusPC;
    //
    //    /** @see CategoryModelItem#categoryOpenStatusMB */
    ////    @HCTypeLabel(component = "valuetype.openStatus")
    //    private HTypeOpenStatus categoryOpenStatusMB;
    //
    //    /** @see CategoryModelItem#categoryOpenGoodsCountPC */
    //    @HCNumber
    //    private Integer categoryOpenGoodsCountPC;
    //
    //    /** @see CategoryModelItem#categoryOpenGoodsCountMB */
    //    @HCNumber
    //    private Integer categoryOpenGoodsCountMB;
    //
    //    /** @see CategoryModelItem#openFlg */
    //    private boolean openFlg;
    //
    //    /** @see CategoryModelItem#lowest */
    //    private boolean lowest;
    //
    //    /** @see CategoryModelItem#levelView */
    //    private String levelView;
    //
    //    /** @see CategoryModelItem#siteMapFlag */
    ////    @HCTypeLabel(component = "valuetype.siteMapFlag")
    //    private HTypeSiteMapFlag siteMapFlag;
    //
    //    /** @see CategoryModelItem#allOpenClose */
    ////    @PageScope
    //    private boolean allOpenClose;
    //
    //    /** @see CategoryModelItem#categoryViewStyle */
    //    private String categoryViewStyle;

    /**
     * カテゴリ木構造<br/>
     *
     * @return resultItems
     */
    public List<CategoryModelItem> getResultItems() {
        return resultItems;
    }

}
