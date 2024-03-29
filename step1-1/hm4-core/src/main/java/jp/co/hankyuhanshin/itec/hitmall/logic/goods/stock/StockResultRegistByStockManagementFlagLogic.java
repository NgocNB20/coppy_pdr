/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;

/**
 * 在庫管理フラグ変更の入庫実績を登録<br/>
 * <pre>
 * 入庫数は0固定
 * 商品稼働率分析の棚卸在庫数を計算する際に、
 * 指定された集計日の商品情報が必要となるため、
 * 在庫管理フラグ変更時に入庫実績を登録する。
 * </pre>
 *
 * @author ito
 */
public interface StockResultRegistByStockManagementFlagLogic {

    /**
     * 実行メソッド<br/>
     *
     * @param goodsEntity 商品エンティティ
     * @return 登録件数
     */
    int execute(GoodsEntity goodsEntity, String administratorName);

}
