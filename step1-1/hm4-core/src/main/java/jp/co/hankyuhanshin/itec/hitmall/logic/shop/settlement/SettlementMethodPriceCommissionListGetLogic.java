/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodPriceCommissionEntity;

import java.util.List;

/**
 * 決済方法金額別手数料リスト取得<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.1 $
 */
public interface SettlementMethodPriceCommissionListGetLogic {

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodSeq 決済方法SEQ
     * @return 決済方法金額別手数料リスト
     */
    List<SettlementMethodPriceCommissionEntity> execeute(Integer settlementMethodSeq);
}
