/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementMethodDto;

/**
 * 決済方法更新<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.1 $
 */
public interface SettlementMethodUpdateService {

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodDto 決済方法DTO
     * @return 処理件数
     */
    int execute(SettlementMethodDto settlementMethodDto);
}
