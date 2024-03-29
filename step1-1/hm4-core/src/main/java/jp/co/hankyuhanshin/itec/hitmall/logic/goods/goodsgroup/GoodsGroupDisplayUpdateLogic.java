/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;

/**
 * 商品グループ表示更新<br/>
 *
 * @author hirata
 * @version $Revision: 1.2 $
 */
public interface GoodsGroupDisplayUpdateLogic {
    // LGP0009

    /**
     * 商品グループ表示更新<br/>
     *
     * @param goodsGroupDisplayEntity 商品グループ表示エンティティ
     * @return 更新した件数
     */
    int execute(GoodsGroupDisplayEntity goodsGroupDisplayEntity);
}
