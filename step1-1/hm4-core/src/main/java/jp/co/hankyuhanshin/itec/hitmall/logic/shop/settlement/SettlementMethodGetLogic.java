/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;

import java.util.Map;

/**
 * 決済方法取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
public interface SettlementMethodGetLogic {

    // LST0001;

    /**
     * ロジック実行<br/>
     *
     * @param settlementMethodSeq 決済方法SEQ
     * @return 決済方法エンティティ
     */
    SettlementMethodEntity execute(Integer settlementMethodSeq);

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodSeq 決済方法SEQ
     * @param shopSeq             ショップSEQ
     * @return 決済方法エンティティ
     */
    SettlementMethodEntity execute(Integer settlementMethodSeq, Integer shopSeq);

    /**
     * エンティティ取得<br/>
     *
     * @param settlementMethodType 決済方法種別
     * @param openStatus           status of settlement
     * @return 決済方法エンティティ
     */
    SettlementMethodEntity execute(HTypeSettlementMethodType settlementMethodType, HTypeOpenStatus openStatus);

    /**
     * 選択項目リストの作成に利用するデータを返却する<br/>
     *
     * @param shopSeq ショップSEQ
     * @return 決済方法結果を格納したMap
     */
    Map<String, String> getItemMapList(Integer shopSeq);
}
