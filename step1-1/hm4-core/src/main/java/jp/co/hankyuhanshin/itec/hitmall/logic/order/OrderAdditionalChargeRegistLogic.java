/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;

/**
 * 受注追加料金登録ロジック<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.2 $
 */
public interface OrderAdditionalChargeRegistLogic {

    /**
     * 実行メソッド<br/>
     *
     * @param orderAdditionalChargeEntity 受注追加料金
     * @return 処理件数
     */
    int execute(OrderAdditionalChargeEntity orderAdditionalChargeEntity);
}
