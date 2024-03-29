/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryMethodTypeCarriageDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodTypeCarriageEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodTypeCarriageRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 配送区分別送料登録ロジック実装クラス
 *
 * @author negishi
 * @author Kaneko (itec) 2012/08/20 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class DeliveryMethodTypeCarriageRegistLogicImpl extends AbstractShopLogic
                implements DeliveryMethodTypeCarriageRegistLogic {

    /**
     * 配送方法DAO
     */
    private final DeliveryMethodTypeCarriageDao deliveryMethodTypeCarriageDao;

    @Autowired
    public DeliveryMethodTypeCarriageRegistLogicImpl(DeliveryMethodTypeCarriageDao deliveryMethodTypeCarriageDao) {
        this.deliveryMethodTypeCarriageDao = deliveryMethodTypeCarriageDao;
    }

    /**
     * ロジック実行
     *
     * @param deliveryMethodTypeCarriageEntity 配送区分別送料エンティティ
     * @return 登録件数
     */
    @Override
    public int execute(DeliveryMethodTypeCarriageEntity deliveryMethodTypeCarriageEntity) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("deliveryMethodTypeCarriageEntity", deliveryMethodTypeCarriageEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 更新日時設定
        deliveryMethodTypeCarriageEntity.setUpdateTime(dateUtility.getCurrentTime());
        // 登録日時設定
        deliveryMethodTypeCarriageEntity.setRegistTime(dateUtility.getCurrentTime());

        // 更新処理実行
        return deliveryMethodTypeCarriageDao.insert(deliveryMethodTypeCarriageEntity);
    }
}
