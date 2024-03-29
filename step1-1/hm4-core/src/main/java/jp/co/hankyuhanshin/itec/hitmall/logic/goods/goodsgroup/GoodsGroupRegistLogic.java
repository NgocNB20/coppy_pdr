/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;

/**
 * 商品グループ登録<br/>
 *
 * @author hirata
 * @version $Revision: 1.2 $
 */
public interface GoodsGroupRegistLogic {
    // LGP0004

    /**
     * 商品グループ登録<br/>
     *
     * @param goodsGroupEntity 商品グループエンティティ
     * @return 登録した件数
     */
    int execute(GoodsGroupEntity goodsGroupEntity);
}
