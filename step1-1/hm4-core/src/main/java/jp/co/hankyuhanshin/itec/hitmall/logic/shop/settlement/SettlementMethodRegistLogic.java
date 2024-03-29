/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;

/**
 * 決済方法登録<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.1 $
 */
public interface SettlementMethodRegistLogic {

    /**
     * 決済方法登録失敗エラー<br/>
     * <code>MSGCD_SETTLEMENTMETHOD_INSERT_FAIL</code>
     */
    public static final String MSGCD_SETTLEMENTMETHOD_INSERT_FAIL = "LST000501";

    /**
     * 実行メソッド<br />
     *
     * @param settlementMethodEntity 決済方法エンティティ
     * @return 処理件数
     */
    int execute(SettlementMethodEntity settlementMethodEntity);
}
