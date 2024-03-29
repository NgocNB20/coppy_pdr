/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品登録<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
public interface GoodsRegistLogic {
    // LGG0003

    /**
     * 処理件数マップ　商品登録件数<br/>
     * <code>GOODS_REGIST</code>
     */
    public static final String GOODS_REGIST = "GoodsRegist";

    /**
     * 処理件数マップ　商品更新件数<br/>
     * <code>GOODS_UPDATE</code>
     */
    public static final String GOODS_UPDATE = "GoodsUpdate";

    /**
     * 商品グループSEQ不一致エラー<br/>
     * <code>MSGCD_GOODSGROUP_MISMATCH_FAIL</code>
     */
    public static final String MSGCD_GOODSGROUP_MISMATCH_FAIL = "LGG000301";

    /**
     * 商品登録<br/>
     *
     * @param goodsGroupSeq   商品グループSEQ
     * @param goodsEntityList 商品エンティティリスト
     * @return 処理件数マップ
     */
    Map<String, Integer> execute(Integer goodsGroupSeq, List<GoodsEntity> goodsEntityList, String administratorName);
}
