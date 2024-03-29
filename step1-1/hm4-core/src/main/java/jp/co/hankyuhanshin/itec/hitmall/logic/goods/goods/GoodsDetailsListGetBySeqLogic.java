/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;

import java.util.List;

/**
 * 商品詳細DTOリスト取得ロジック<br/>
 *
 * @author USER
 * @version $Revision: 1.3 $
 */
public interface GoodsDetailsListGetBySeqLogic {

    /**
     * 実行メソッド<br/>
     *
     * @param goodsSeqList 商品SEQリスト
     * @return 商品詳細DTOリスト
     */
    List<GoodsDetailsDto> execute(List<Integer> goodsSeqList);

}
