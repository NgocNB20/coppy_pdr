/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

/**
 * 受注サマリカウント情報取得ロジック<br/>
 *
 * @author negishi
 * @version $Revision: 1.1 $
 */
// 2023-renew No26 from here
public interface OrderSummaryCountInCurrentDayGetLogic {

    /**
     * 本日における同一顧客の受注数を取得する<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @return 件数
     */
    int execute(Integer memberInfoSeq);
}
// 2023-renew No26 to here
