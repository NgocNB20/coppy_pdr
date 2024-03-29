/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;

/**
 * 受注サマリー取得ロジック<br/>
 * 受注サマリーを取得する際に対象のデータレコードをロックします。<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.4 $
 */
public interface OrderSummaryGetForUpdateLogic {

    /**
     * SQLエラー
     */
    public static final String MSGCD_SELECT_FORUPDATE__FATAL = "LOO005101";

    /**
     * 実行メソッド<br/>
     *
     * @param orderCode      受注番号
     * @param orderVersionNo 受注履歴連番
     * @param shopSeq        ショップSEQ
     * @return 受注サマリ
     */
    OrderSummaryEntity execute(String orderCode, Integer orderVersionNo, Integer shopSeq);

}
