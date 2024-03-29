/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementMethodDto;

/**
 * 決済方法登録<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.1 $
 */
public interface SettlementMethodRegistService {

    /**
     * SST000301
     */
    public static final String MSGCD_COMMISSION_INSERT_ERROR = "SST000301";

    /**
     * SST000302
     */
    public static final String MSGCD_DELIVERY_DELETE_ERROR = "SST000302";

    /**
     * SST000303
     */
    public static final String MSGCD_DELIVERY_NULL_ERROR = "SST000303";

    /**
     * 実行メソッド<br/>
     *
     * @param settlementMethodDto 決済方法DTO
     * @return 処理件数
     */
    int execute(SettlementMethodDto settlementMethodDto);
}
