/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.memo.OrderMemoEntity;

/**
 * 受注メモエンティティ取得ロジック<br/>
 *
 * @author USER
 * @version $Revision: 1.2 $
 */
public interface OrderMemoGetLogic {

    /**
     * 実行ロジック<br/>
     * （指定バージョンを取得）<br/>
     *
     * @param orderSeq           受注SEQ
     * @param orderMemoVersionNo 受注履歴連番
     * @return 受注メモ
     */
    OrderMemoEntity execute(Integer orderSeq, Integer orderMemoVersionNo);
}
