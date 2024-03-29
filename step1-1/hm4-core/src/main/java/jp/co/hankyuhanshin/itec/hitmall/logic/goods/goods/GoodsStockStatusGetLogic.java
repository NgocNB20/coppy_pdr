/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDto;

import java.util.List;
import java.util.Map;

/**
 * 商品規格の在庫状態取得ロジック<br/>
 *
 * @author hs32101
 */
public interface GoodsStockStatusGetLogic {

    /**
     * 商品規格の在庫状態の設定
     *
     * @param goodsDtoList 商品DTOリスト
     * @param shopSeq      ショップSEQ
     * @return 規格在庫ステータスMAP＜商品SEQ、在庫状況＞
     */
    Map<Integer, HTypeStockStatusType> execute(List<GoodsDto> goodsDtoList, Integer shopSeq);

}
