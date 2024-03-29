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
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodPriceCommissionListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 決済方法金額別手数料リスト取得<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.1 $
 */
@Component
public class SettlementMethodPriceCommissionListGetLogicImpl extends AbstractShopLogic
                implements SettlementMethodPriceCommissionListGetLogic {

    /**
     * 決済方法金額別手数料DAO
     */
    private final SettlementMethodPriceCommissionDao settlementMethodPriceCommissionDao;

    @Autowired
    public SettlementMethodPriceCommissionListGetLogicImpl(SettlementMethodPriceCommissionDao settlementMethodPriceCommissionDao) {
        this.settlementMethodPriceCommissionDao = settlementMethodPriceCommissionDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodSeq 決済方法SEQ
     * @return 決済方法金額別手数料リスト
     */
    @Override
    public List<SettlementMethodPriceCommissionEntity> execeute(Integer settlementMethodSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertGreaterThanZero("settlementMethodSeq", settlementMethodSeq);

        // 取得ロジック処理実行
        return settlementMethodPriceCommissionDao.getListBySettlementMethodSeq(settlementMethodSeq);
    }

}
