/*
 * Project Name : HIT-MALL4
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * カテゴリ管理
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class CategoryGoodsListModel extends AbstractModel {

    /**
     * ページ番号
     */
    private String pageNumber;

    /**
     * 最大表示件数
     */
    private int limit;

    /**
     * カテゴリー内商品一覧の検索総件数
     */
    private int totalCount;

    /**
     * カテゴリエンティティ情報
     */
    private CategoryEntity categoryEntity;

    /**
     * 一覧
     */
    private List<CategoryGoodslistItem> resultItems;

    /**
     * 一覧(登録実行用)
     */
    private List<CategoryGoodslistItem> tempItems;

    //    /** 選択された行 */
    //    private int resultIndex;

    /**
     * カテゴリID
     */
    private String categoryId;

    /**
     * カテゴリ名
     */
    private String categoryName;

    //    /** 連番 */
    //    private Integer resultNo;
    //
    //    /** 商品グループコード */
    //    private String goodsGroupCode;
    //
    //    /** 商品グループ名 */
    //    private String goodsGroupName;
    //
    //    /** 商品公開状態(PC) */
    //    private String goodsOpenStatusPC;
    //
    //    /** 商品公開開始日時(PC) */
    //    private Timestamp goodsOpenFromDateTimePC;
    //
    //    /** 商品公開終了日時(PC) */
    //    private Timestamp goodsOpenToDateTimePC;
    //
    //    /** 商品公開状態(MB) */
    //    private String goodsOpenStatusMB;
    //
    //    /** 商品公開開始日時(MB) */
    //    private Timestamp goodsOpenFromDateTimeMB;
    //
    //    /** 商品公開終了日時(MB) */
    //    private Timestamp goodsOpenToDateTimeMB;

    /**
     * No
     */
    private String no;

    /**
     * No
     */
    private String targetNo;

    /**
     * ターゲット親カテゴリID
     */
    private String targetParentCategoryId;

    /**
     * カテゴリSEQパス(画面受渡し用)
     * (カテゴリーボタンの展開保持に使用)
     */
    private String categorySeqPathTarget;

    /**
     * 抽出カテゴリー：プルダウン値(プルダウン値画面受渡し用)
     */
    private String extractCategoryName;

    /**
     * 初期表示(カテゴリー一覧)
     */
    private boolean initialDisplayList;

    /**
     * 検索結果表示判定<br/>
     *
     * @return true=検索結果がnull以外(0件リスト含む), false=検索結果がnull
     */
    public boolean isResult() {
        return getResultItems() != null;
    }

}
