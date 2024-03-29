/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品グループ表示マップ取得<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
public interface GoodsGroupDisplayMapGetLogic {
    // LGP0014

    /**
     * 商品グループ表示マップ取得<br/>
     * 商品グループSEQをもとに商品グループ表示エンティティマップを取得する。<br/>
     *
     * @param goodsGroupSeq 商品グループSEQリスト
     * @return 商品グループ表示マップ
     */
    Map<Integer, GoodsGroupDisplayEntity> execute(List<Integer> goodsGroupSeq);
}
