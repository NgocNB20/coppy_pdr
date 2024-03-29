/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryImpossibleAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleAreaListDeleteLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 配送不可能エリア削除Logic実装クラス
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
@Component
public class DeliveryImpossibleAreaListDeleteLogicImpl extends AbstractShopLogic
                implements DeliveryImpossibleAreaListDeleteLogic {

    /**
     * 配送不可能エリアDao
     */
    private final DeliveryImpossibleAreaDao deliveryImpossibleAreaDao;

    @Autowired
    public DeliveryImpossibleAreaListDeleteLogicImpl(DeliveryImpossibleAreaDao deliveryImpossibleAreaDao) {
        this.deliveryImpossibleAreaDao = deliveryImpossibleAreaDao;
    }

    /**
     * 配送特別料金エリア情報を削除します
     *
     * @param entityList List&lt;DeliveryImpossibleAreaEntity&gt;
     * @return int 処理結果
     */
    @Override
    public int execute(List<DeliveryImpossibleAreaEntity> entityList) {
        int result = 0;

        for (DeliveryImpossibleAreaEntity entity : entityList) {
            result += deliveryImpossibleAreaDao.delete(entity);
        }

        return result;
    }
}
