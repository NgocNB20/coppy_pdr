/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.icon;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;

/**
 * アイコン削除Logic
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface GoodsInformationIconDeleteLogic {

    /**
     * アイコン削除
     *
     * @param goodsInformationIconEntity 商品インフォメーションアイコンエンティティ
     * @return 処理件数
     */
    int execute(GoodsInformationIconEntity goodsInformationIconEntity);

}
