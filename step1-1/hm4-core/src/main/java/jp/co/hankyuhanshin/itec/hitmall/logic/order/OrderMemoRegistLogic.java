/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.memo.OrderMemoEntity;

/**
 * 受注メモ登録処理<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.2 $
 */
public interface OrderMemoRegistLogic {

    /**
     * 実行メソッド<br/>
     *
     * @param orderMemoEntity 受注メモ
     * @return 処理件数
     */
    int execute(OrderMemoEntity orderMemoEntity);
}
