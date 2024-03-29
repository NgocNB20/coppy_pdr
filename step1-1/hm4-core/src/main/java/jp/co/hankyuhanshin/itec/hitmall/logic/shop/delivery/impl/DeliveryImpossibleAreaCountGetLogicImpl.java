/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryImpossibleAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleAreaCountGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 配送不可能エリアカウント取得ロジック実装クラス
 *
 * @author negishi
 * @version $Revision: 1.1 $
 */
@Component
public class DeliveryImpossibleAreaCountGetLogicImpl extends AbstractShopLogic
                implements DeliveryImpossibleAreaCountGetLogic {

    /**
     * 配送方法Dao
     */
    private final DeliveryImpossibleAreaDao deliveryImpossibleAreaDao;

    @Autowired
    public DeliveryImpossibleAreaCountGetLogicImpl(DeliveryImpossibleAreaDao deliveryImpossibleAreaDao) {
        this.deliveryImpossibleAreaDao = deliveryImpossibleAreaDao;
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
        return deliveryImpossibleAreaDao.getCount(deliveryMethodSeq);
    }
}
