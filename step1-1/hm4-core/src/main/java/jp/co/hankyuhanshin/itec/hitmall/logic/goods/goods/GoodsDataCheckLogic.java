/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;

import java.util.List;

/**
 * 商品データチェック<br/>
 *
 * @author hirata
 * @version $Revision: 1.5 $
 */
public interface GoodsDataCheckLogic {
    // LGG0005

    /**
     * 商品コード重複エラー<br/>
     * <code>MSGCD_GOODSCODE_REPETITION_FAIL</code>
     */
    public static final String MSGCD_GOODSCODE_REPETITION_FAIL = "LGG000501";

    /**
     * 商品データチェック<br/>
     * 商品エンティティリストの登録・更新前チェックを行う。<br/>
     *
     * @param goodsEntityList 商品エンティティリスト
     * @param shopSeq         ショップSEQ
     * @param goodsGroupCode  商品グループコード
     */
    void execute(List<GoodsEntity> goodsEntityList, Integer shopSeq, String goodsGroupCode);
}
