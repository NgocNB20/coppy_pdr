/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.settlement.SettlementMethodPriceCommissionDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodPriceCommissionEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodPriceCommissionDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 決済方法金額別手数料削除<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.1 $
 */
@Component
public class SettlementMethodPriceCommissionDeleteLogicImpl extends AbstractShopLogic
                implements SettlementMethodPriceCommissionDeleteLogic {

    /**
     * 決済方法金額別手数料Dao
     */
    private final SettlementMethodPriceCommissionDao settlementMethodPriceCommissionDao;

    @Autowired
    public SettlementMethodPriceCommissionDeleteLogicImpl(SettlementMethodPriceCommissionDao settlementMethodPriceCommissionDao) {
        this.settlementMethodPriceCommissionDao = settlementMethodPriceCommissionDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodSeq 決済方法SEQ
     * @return 処理件数
     */
    @Override
    public int execute(Integer settlementMethodSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertGreaterThanZero("settlementMethodSeq", settlementMethodSeq);

        return settlementMethodPriceCommissionDao.deleteListBySettlementMethodSeq(settlementMethodSeq);
    }

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodPriceCommissionEntity 決済方法金額別手数料エンティティ
     * @return 処理件数
     */
    @Override
    public int execute(SettlementMethodPriceCommissionEntity settlementMethodPriceCommissionEntity) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("settlementMethodPriceCommissionEntity", settlementMethodPriceCommissionEntity);

        return settlementMethodPriceCommissionDao.delete(settlementMethodPriceCommissionEntity);
    }

}
