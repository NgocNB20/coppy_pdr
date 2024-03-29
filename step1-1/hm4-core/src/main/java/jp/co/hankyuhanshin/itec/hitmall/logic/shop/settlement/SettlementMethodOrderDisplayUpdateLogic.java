/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;

import java.util.List;

/**
 * 決済方法表示順更新ロジック<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.1 $
 */
public interface SettlementMethodOrderDisplayUpdateLogic {

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodList 決済方法エンティティリスト
     * @param shopSeq              ショップSEQ
     * @return 処理件数
     */
    int execute(List<SettlementMethodEntity> settlementMethodList, Integer shopSeq);
}
