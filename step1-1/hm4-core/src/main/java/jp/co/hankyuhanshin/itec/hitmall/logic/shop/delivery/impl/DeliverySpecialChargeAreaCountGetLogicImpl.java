/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliverySpecialChargeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliverySpecialChargeAreaCountGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 配送特別料金エリアカウント取得ロジック実装クラス
 *
 * @author negishi
 * @version $Revision: 1.1 $
 */
@Component
public class DeliverySpecialChargeAreaCountGetLogicImpl extends AbstractShopLogic
                implements DeliverySpecialChargeAreaCountGetLogic {

    /**
     * 配送方法Dao
     */
    private final DeliverySpecialChargeAreaDao deliverySpecialChargeAreaDao;

    @Autowired
    public DeliverySpecialChargeAreaCountGetLogicImpl(DeliverySpecialChargeAreaDao deliverySpecialChargeAreaDao) {
        this.deliverySpecialChargeAreaDao = deliverySpecialChargeAreaDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param deliveryMethodSeq 配送方法SEQ
     * @return 件数
     */
    @Override
    public int execute(Integer deliveryMethodSeq) {
        // 引数チェック
        ArgumentCheckUtil.assertNotNull("deliveryMethodSeq", deliveryMethodSeq);

        // 件数取得実行
        return deliverySpecialChargeAreaDao.getCount(deliveryMethodSeq);
    }
}
