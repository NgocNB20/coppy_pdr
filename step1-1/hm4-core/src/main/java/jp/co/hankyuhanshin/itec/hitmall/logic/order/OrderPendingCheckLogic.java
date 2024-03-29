// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

/**
 * #028 注文情報の保留<br/>
 *
 * <pre>
 * 注文保留ロジッククラス
 * </pre>
 *
 * @author satoh
 */

public interface OrderPendingCheckLogic {

    /** 判定結果:保留不要(0) */
    public static final String NOT_PENDING = "0";

    /**
     * システムエラー発生
     * <code>MSGCD_SYSTEM_ERR</code>
     */
    public static final String MSGCD_SYSTEM_ERR = "PDR-0015-001-A-";

    // 2023-renew No15 from here
    /** プロパティKEY：初回購入上限金額（コンビニ・郵便振込）*/
    public static final String KEY_TRANSFERPAYMENTUSE_FIRSTTIME_PURCHASE_LIMIT =
                    "transferpaymentuse.firsttime.purchase.limit";
    // 2023-renew No15 to here

    // 2023-renew No26 from here
    /** プロパティKEY：注文保留とする同一顧客の1日の複数回注文数 */
    public static final String KEY_MULTIPLE_ORDER_COUNT = "multiple.order.count";
    // 2023-renew No26 to here

    /**
     * 注文保留チェックを行います。
     *
     * @param receiveOrderDto 受注DTO
     * @param memberInfoSeq 会員SEQ
     */
    void checkOrderPending(ReceiveOrderDto receiveOrderDto, Integer memberInfoSeq);
}
// PDR Migrate Customization to here
