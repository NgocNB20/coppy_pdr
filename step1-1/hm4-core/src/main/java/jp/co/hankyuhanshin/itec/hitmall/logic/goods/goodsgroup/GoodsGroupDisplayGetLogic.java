/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;

import java.util.List;

/**
 * 商品グループ表示取得<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface GoodsGroupDisplayGetLogic {
    // LGP0007

    /**
     * 商品グループ表示取得<br/>
     * 商品グループSEQをもとに商品グループ表示エンティティを取得する。<br/>
     *
     * @param goodsGroupSeq 商品グループSEQリスト
     * @return 商品グループ表示情報
     */
    GoodsGroupDisplayEntity execute(Integer goodsGroupSeq);

    /**
     * セット子商品グループ表示取得<br/>
     * 商品グループSEQをもとに商品グループ表示エンティティを取得する。<br/>
     *
     * @param goodsGroupSeqList 商品グループSEQリスト
     * @return 商品グループ表示情報
     */
    List<GoodsGroupDisplayEntity> execute(List<Integer> goodsGroupSeqList);
}
