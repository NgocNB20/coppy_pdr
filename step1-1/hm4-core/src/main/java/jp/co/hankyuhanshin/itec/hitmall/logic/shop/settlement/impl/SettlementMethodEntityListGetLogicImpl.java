/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.settlement.SettlementMethodDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodEntityListGetLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 決済方法リスト取得ロジック実装クラス
 *
 * @author nakamura
 * @version $Revision: 1.4 $
 */
@Component
public class SettlementMethodEntityListGetLogicImpl extends AbstractShopLogic
                implements SettlementMethodEntityListGetLogic {

    /**
     * 決済方法Dao
     */
    private final SettlementMethodDao settlementMethodDao;

    @Autowired
    public SettlementMethodEntityListGetLogicImpl(SettlementMethodDao settlementMethodDao) {
        this.settlementMethodDao = settlementMethodDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param shopSeq ショップSeq
     * @return 決済方法エンティティ全件分のリスト（ソート：表示昇順）
     */
    @Override
    public List<SettlementMethodEntity> execute(Integer shopSeq) {

        // Daoメソッド実行
        return settlementMethodDao.getSettlementMethodList(shopSeq);
    }

}
