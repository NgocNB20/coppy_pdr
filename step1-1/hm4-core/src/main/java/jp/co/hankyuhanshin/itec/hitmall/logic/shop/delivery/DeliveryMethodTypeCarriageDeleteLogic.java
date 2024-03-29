/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

/**
 * 配送区分別送料削除ロジック
 *
 * @author negishi
 * @version $Revision: 1.1 $
 */
public interface DeliveryMethodTypeCarriageDeleteLogic {

    /**
     * ロジック実行
     *
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 削除件数
     */
    int execute(Integer deliveryMethodSeq);

}
