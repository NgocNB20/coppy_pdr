/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodPriceCommissionEntity;

/**
 * 決済方法金額別手数料削除<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.1 $
 */
public interface SettlementMethodPriceCommissionDeleteLogic {

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodSeq 決済方法SEQ
     * @return 処理件数
     */
    int execute(Integer settlementMethodSeq);

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodPriceCommissionEntity 決済方法金額別手数料エンティティ
     * @return 処理件数
     */
    int execute(SettlementMethodPriceCommissionEntity settlementMethodPriceCommissionEntity);
}
