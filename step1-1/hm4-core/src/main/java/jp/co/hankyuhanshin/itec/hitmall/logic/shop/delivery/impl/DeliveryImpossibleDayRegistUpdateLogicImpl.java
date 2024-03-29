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
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleDayGetByYearAndDateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleDayRegistUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * お届け不可日登録ロジック<br/>
 *
 * @author Author: ty32113
 */
@Component
public class DeliveryImpossibleDayRegistUpdateLogicImpl extends AbstractShopLogic
                implements DeliveryImpossibleDayRegistUpdateLogic {

    /**
     * お届け不可日Dao
     */
    private final DeliveryImpossibleDayDao deliveryImpossibleDayDao;

    /**
     * お届け不可日取得ロジック
     */
    private final DeliveryImpossibleDayGetByYearAndDateLogic deliveryImpossibleDayGetByYearAndDateLogic;

    @Autowired
    public DeliveryImpossibleDayRegistUpdateLogicImpl(DeliveryImpossibleDayDao deliveryImpossibleDayDao,
                                                      DeliveryImpossibleDayGetByYearAndDateLogic deliveryImpossibleDayGetByYearAndDateLogic) {
        this.deliveryImpossibleDayDao = deliveryImpossibleDayDao;
        this.deliveryImpossibleDayGetByYearAndDateLogic = deliveryImpossibleDayGetByYearAndDateLogic;
    }

    /**
     * ロジック実行<br/>
     *
     * @param deliveryImpossibleDayEntity お届け不可日エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(DeliveryImpossibleDayEntity deliveryImpossibleDayEntity) {

        // 存在チェック
        DeliveryImpossibleDayEntity resultEntity =
                        deliveryImpossibleDayGetByYearAndDateLogic.execute(deliveryImpossibleDayEntity.getYear(),
                                                                           deliveryImpossibleDayEntity.getDate(),
                                                                           deliveryImpossibleDayEntity.getDeliveryMethodSeq()
                                                                          );
        int result = 0;

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 登録日時の設定
        deliveryImpossibleDayEntity.setRegistTime(dateUtility.getCurrentTime());

        if (resultEntity != null) {
            // レコードが存在する場合は更新
            result = deliveryImpossibleDayDao.update(deliveryImpossibleDayEntity);
        } else {
            // レコードが存在しない場合は登録
            result = deliveryImpossibleDayDao.insert(deliveryImpossibleDayEntity);
        }

        return result;
    }

}
