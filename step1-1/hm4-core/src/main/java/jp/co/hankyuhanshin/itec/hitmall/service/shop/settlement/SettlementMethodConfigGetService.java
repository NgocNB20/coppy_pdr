/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementMethodDto;

/**
 * 決済方法詳細設定取得<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.1 $
 */
public interface SettlementMethodConfigGetService {

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodSeq 決済方法SEQ
     * @return 決済方法DTO
     */
    SettlementMethodDto execute(Integer settlementMethodSeq);
}
