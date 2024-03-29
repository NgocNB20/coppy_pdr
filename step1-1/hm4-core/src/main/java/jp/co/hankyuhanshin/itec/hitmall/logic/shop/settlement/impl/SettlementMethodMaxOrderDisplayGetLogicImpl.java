/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.settlement.SettlementMethodDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodMaxOrderDisplayGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 最大表示順取得ロジック
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
@Component
public class SettlementMethodMaxOrderDisplayGetLogicImpl extends AbstractShopLogic
                implements SettlementMethodMaxOrderDisplayGetLogic {

    /**
     * 決済方法Dao
     */
    private final SettlementMethodDao settlementMethodDao;

    @Autowired
    public SettlementMethodMaxOrderDisplayGetLogicImpl(SettlementMethodDao settlementMethodDao) {
        this.settlementMethodDao = settlementMethodDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param shopSeq ショップSEQ
     * @return 表示順の最大値
     */
    @Override
    public Integer execute(Integer shopSeq) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);

        // 表示順の取得
        return settlementMethodDao.getMaxOrderDisplay(shopSeq);
    }

}
