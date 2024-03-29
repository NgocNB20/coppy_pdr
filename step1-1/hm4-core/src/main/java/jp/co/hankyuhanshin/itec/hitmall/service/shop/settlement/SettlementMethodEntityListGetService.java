/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;

import java.util.List;

/**
 * 決済方法リスト取得サービス
 *
 * @author nakamura
 * @version $Revision: 1.1 $
 */
public interface SettlementMethodEntityListGetService {

    /**
     * 実行メソッド
     *
     * @return 決済方法エンティティ全件分のリスト（ソート：表示昇順）
     */
    List<SettlementMethodEntity> execute();

}
