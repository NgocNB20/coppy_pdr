// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.goods.togetherbuy;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * よく一緒に購入される商品登録<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
public interface GoodsTogetherBuyGroupRegistLogic {
    // LGR0002

    /**
     * 処理件数マップ　よく一緒に購入される商品登録件数<br/>
     * <code>GOODS_RELATION_REGIST</code>
     */
    public static final String GOODS_TOGETHER_BUY_GROUP_REGIST = "GoodsTogetherBuyGroupRegist";

    /**
     * 処理件数マップ　よく一緒に購入される商品更新件数<br/>
     * <code>GOODS_RELATION_UPDATE</code>
     */
    public static final String GOODS_TOGETHER_BUY_GROUP_UPDATE = "GoodsTogetherBuyGroupUpdate";

    /**
     * 処理件数マップ　よく一緒に購入される商品削除件数<br/>
     * <code>GOODS_RELATION_DELETE</code>
     */
    public static final String GOODS_TOGETHER_BUY_GROUP_DELETE = "GoodsTogetherBuyGroupDelete";

    /**
     * 商品グループSEQ不一致エラー<br/>
     * <code>MSGCD_GOODSGROUP_MISMATCH_FAIL</code>
     */
    public static final String MSGCD_GOODSGROUP_MISMATCH_FAIL = "LGR000201";

    /**
     * よく一緒に購入される商品登録<br/>
     *
     * @param goodsGroupSeq           商品グループSEQ
     * @param goodsTogetherBuyGroupEntityList よく一緒に購入される商品エンティティリスト
     * @return 処理件数マップ
     */
    Map<String, Integer> execute(Integer goodsGroupSeq, List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyGroupEntityList);

    /**
     * よく一緒に購入される商品登録<br/>
     *
     * @param goodsTogetherBuyGroupEntity           よく一緒に購入される商品クラス
     * @return 処理件数マップ
     */
    int execute(GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity);
}
// 2023-renew No21 to here
