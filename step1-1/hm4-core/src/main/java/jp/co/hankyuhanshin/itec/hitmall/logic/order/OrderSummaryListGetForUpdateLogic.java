/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;

import java.util.List;

/**
 * 受注サマリー排他取得ロジック<br/>
 * 受注サマリーを取得する際に対象のデータレコードをロックします。<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.3 $
 */
public interface OrderSummaryListGetForUpdateLogic {

    /**
     * SQLエラー
     */
    public static final String MSGCD_SELECT_FORUPDATE__FATAL = "LOO005001";

    /**
     * 実行メソッド概要<br/>
     *
     * @param orderSeqList 受注SEQリスト
     * @return 受注サマリリスト
     */
    List<OrderSummaryEntity> execute(List<Integer> orderSeqList);

    /**
     * 実行メソッド概要<br/>
     *
     * @param orderCodeList 受注コードリスト
     * @param shopSeq       ショップSEQ
     * @return 受注サマリリスト
     */
    List<OrderSummaryEntity> execute(List<String> orderCodeList, Integer shopSeq);
}
