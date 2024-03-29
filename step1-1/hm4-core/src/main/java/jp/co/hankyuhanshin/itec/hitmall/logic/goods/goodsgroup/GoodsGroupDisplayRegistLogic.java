/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;

/**
 * 商品グループ表示登録<br/>
 *
 * @author hirata
 * @version $Revision: 1.2 $
 */
public interface GoodsGroupDisplayRegistLogic {
    // LGP0008

    /**
     * 商品グループ表示登録<br/>
     *
     * @param goodsGroupDisplayEntity 商品グループ表示エンティティ
     * @return 登録した件数
     */
    int execute(GoodsGroupDisplayEntity goodsGroupDisplayEntity);
}
