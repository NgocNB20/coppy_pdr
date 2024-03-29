/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryMethodDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 配送方法更新ロジック実装クラス
 *
 * @author negishi
 * @author Kaneko (itec) 2012/08/20 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class DeliveryMethodUpdateLogicImpl extends AbstractShopLogic implements DeliveryMethodUpdateLogic {

    /**
     * 配送方法DAO
     */
    private final DeliveryMethodDao deliveryMethodDao;

    @Autowired
    public DeliveryMethodUpdateLogicImpl(DeliveryMethodDao deliveryMethodDao) {
        this.deliveryMethodDao = deliveryMethodDao;
    }

    /**
     * ロジック実行
     *
     * @param deliveryMethodEntity 配送方法エンティティ
     * @return 更新件数
     */
    @Override
    public int execute(DeliveryMethodEntity deliveryMethodEntity) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("deliveryMethodEntity", deliveryMethodEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 更新日時設定
        deliveryMethodEntity.setUpdateTime(dateUtility.getCurrentTime());

        // 更新処理実行
        return deliveryMethodDao.update(deliveryMethodEntity);
    }
}
