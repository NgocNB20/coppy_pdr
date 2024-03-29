/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;

/**
 * 受注インデックス取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.1 $
 */
public interface OrderIndexGetLogic {

    // LOO0001;

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq       受注SEQ
     * @param orderVersionNo 受注履歴番号
     * @return 受注インデックスエンティティ
     */
    OrderIndexEntity execute(Integer orderSeq, Integer orderVersionNo);
}
