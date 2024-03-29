/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryGoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * カテゴリ登録商品登録<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
public interface CategoryGoodsRegistLogic {
    // LGC0017

    /**
     * 処理件数マップ　カテゴリ登録商品登録件数<br/>
     * <code>CATEGORY_GOODS_REGIST</code>
     */
    public static final String CATEGORY_GOODS_REGIST = "CategoryGoodsRegist";

    /**
     * 処理件数マップ　カテゴリ登録商品削除件数<br/>
     * <code>CATEGORY_GOODS_REGIST</code>
     */
    public static final String CATEGORY_GOODS_DELETE = "CategoryGoodsDelete";

    /**
     * 商品グループSEQ不一致エラー<br/>
     * <code>MSGCD_GOODSGROUP_MISMATCH_FAIL</code>
     */
    public static final String MSGCD_GOODSGROUP_MISMATCH_FAIL = "LGC001701";

    /**
     * カテゴリ登録商品登録<br/>
     *
     * @param goodsGroupSeq           商品グループSEQ
     * @param categoryGoodsEntityList カテゴリ登録商品エンティティリスト
     * @return 処理件数マップ
     */
    Map<String, Integer> execute(Integer goodsGroupSeq, List<CategoryGoodsEntity> categoryGoodsEntityList);
}
