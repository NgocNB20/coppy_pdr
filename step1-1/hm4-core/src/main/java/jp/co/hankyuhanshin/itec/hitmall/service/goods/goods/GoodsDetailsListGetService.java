/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;

import java.util.List;

/**
 * 商品詳細DTOリスト取得<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.2 $
 */
public interface GoodsDetailsListGetService {

    /**
     * 実行メソッド<br/>
     *
     * @param goodsSeqList 商品SEQリスト
     * @return 商品詳細DTOリスト
     */
    List<GoodsDetailsDto> execute(List<Integer> goodsSeqList);
}
