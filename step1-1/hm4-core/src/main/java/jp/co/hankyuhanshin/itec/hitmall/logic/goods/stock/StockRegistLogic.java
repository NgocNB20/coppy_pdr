/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockEntity;

import java.util.List;

/**
 * 在庫登録<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
public interface StockRegistLogic {
    // LGS0004

    /**
     * 在庫登録<br/>
     *
     * @param stockEntityList 在庫エンティティリスト
     * @return 登録した件数
     */
    int execute(List<StockEntity> stockEntityList, String administratorName);
}
