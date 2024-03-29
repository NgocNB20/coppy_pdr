/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;

import java.util.List;

/**
 * 商品グループの在庫状況取得ロジック<br/>
 *
 * @author Kaneko　2013/03/01
 */
public interface GoodsGroupStockStatusGetLogic {

    /**
     * 商品グループの在庫状況の設定
     *
     * @param goodsDtoList 商品DTOリスト
     * @return 在庫状況
     */
    HTypeStockStatusType execute(List<GoodsDto> goodsDtoList);

}
