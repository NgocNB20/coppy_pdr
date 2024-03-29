/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;

/**
 * 商品グループ更新<br/>
 *
 * @author hirata
 * @version $Revision: 1.3 $
 */
public interface GoodsGroupUpdateLogic {
    // LGP0005

    /**
     * 商品グループ更新<br/>
     *
     * @param goodsGroupEntity 商品グループエンティティ
     * @return 更新した件数
     */
    int execute(GoodsGroupEntity goodsGroupEntity);
}
