/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockDetailsResultDto;

import java.util.List;

/**
 * 入荷お知らせ商品詳細結果リスト取得<br/>
 *
 * @author st75001
 * @version $Revision: 1.2 $
 */
public interface ReStockDetailsResultListGetLogic {

    /**
     * 実行メソッド<br/>
     *
     * @param key キー（商品SEQ+入荷状態+配信ID+配信状況)
     * @return 商品検索結果DTOリスト
     */
    List<ReStockDetailsResultDto> execute(String key);
}
