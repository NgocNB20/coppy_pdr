/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryMethodTypeCarriageDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodTypeCarriageDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 配送区分別送料削除ロジック実装クラス
 *
 * @author negishi
 * @version $Revision: 1.1 $
 */
@Component
public class DeliveryMethodTypeCarriageDeleteLogicImpl extends AbstractShopLogic
                implements DeliveryMethodTypeCarriageDeleteLogic {

    /**
     * 配送方法DAO
     */
    private final DeliveryMethodTypeCarriageDao deliveryMethodTypeCarriageDao;

    @Autowired
    public DeliveryMethodTypeCarriageDeleteLogicImpl(DeliveryMethodTypeCarriageDao deliveryMethodTypeCarriageDao) {
        this.deliveryMethodTypeCarriageDao = deliveryMethodTypeCarriageDao;
    }

    /**
     * ロジック実行
     *
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 削除件数
     */
    @Override
    public int execute(Integer deliveryMethodSeq) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("deliveryMethodSeq", deliveryMethodSeq);

        // 削除処理実行
        return deliveryMethodTypeCarriageDao.deleteList(deliveryMethodSeq);
    }
}
