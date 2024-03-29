/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.icon;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;

/**
 * アイコン削除
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface GoodsInformationIconDeleteService {

    /**
     * アイコン削除
     *
     * @param iconEntity 商品インフォメーションアイコンエンティティ
     * @return 処理結果マップ
     */
    int execute(GoodsInformationIconEntity iconEntity);
}
