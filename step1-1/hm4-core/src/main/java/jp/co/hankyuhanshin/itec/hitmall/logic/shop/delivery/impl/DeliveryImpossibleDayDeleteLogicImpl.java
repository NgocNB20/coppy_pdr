/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryImpossibleDayDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleDayEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleDayDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * お届け不可日削除ロジック<br/>
 *
 * @author Author: ty32113
 */
@Component
public class DeliveryImpossibleDayDeleteLogicImpl extends AbstractShopLogic
                implements DeliveryImpossibleDayDeleteLogic {

    /**
     * お届け不可日Dao
     */
    private final DeliveryImpossibleDayDao deliveryImpossibleDayDao;

    @Autowired
    public DeliveryImpossibleDayDeleteLogicImpl(DeliveryImpossibleDayDao deliveryImpossibleDayDao) {
        this.deliveryImpossibleDayDao = deliveryImpossibleDayDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param deliveryImpossibleDayDeleteEntity お届け不可日エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(DeliveryImpossibleDayEntity deliveryImpossibleDayDeleteEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("deliveryImpossibleDayEntity", deliveryImpossibleDayDeleteEntity);

        // 削除
        return deliveryImpossibleDayDao.deleteDeliveryImpossibleDayByYear(deliveryImpossibleDayDeleteEntity);
    }

}
