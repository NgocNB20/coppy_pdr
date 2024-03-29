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
public interface OrderSummaryCountGetLogic {

    /**
     * 受注サマリ情報を取得する<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @return 件数
     */
    int execute(Integer memberInfoSeq);
}
