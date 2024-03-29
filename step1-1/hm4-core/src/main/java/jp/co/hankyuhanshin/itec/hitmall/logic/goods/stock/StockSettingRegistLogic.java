/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockSettingEntity;

import java.util.List;
import java.util.Map;

/**
 * 在庫設定登録<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
public interface StockSettingRegistLogic {
    // LGS0008

    /**
     * 処理件数マップ　在庫設定登録件数<br/>
     * <code>STOCK_SETTING_REGIST</code>
     */
    public static final String STOCK_SETTING_REGIST = "StockSettingRegist";

    /**
     * 処理件数マップ　在庫設定更新件数<br/>
     * <code>STOCK_SETTING_UPDATE</code>
     */
    public static final String STOCK_SETTING_UPDATE = "StockSettingUpdate";

    /**
     * 在庫設定登録<br/>
     *
     * @param goodsGroupSeq          商品グループSEQ
     * @param stockSettingEntityList 在庫設定エンティティリスト
     * @return 処理件数マップ
     */
    Map<String, Integer> execute(Integer goodsGroupSeq, List<StockSettingEntity> stockSettingEntityList);
}
