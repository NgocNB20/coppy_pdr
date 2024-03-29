/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryImpossibleAreaListDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryImpossibleAreaListDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配送不可能エリア削除Service実装クラス
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
@Service
public class DeliveryImpossibleAreaListDeleteServiceImpl extends AbstractShopService
                implements DeliveryImpossibleAreaListDeleteService {

    /**
     * 配送特別料金エリア削除Logic
     */
    private final DeliveryImpossibleAreaListDeleteLogic deliveryImpossibleAreaListDeleteLogic;

    @Autowired
    public DeliveryImpossibleAreaListDeleteServiceImpl(DeliveryImpossibleAreaListDeleteLogic deliveryImpossibleAreaListDeleteLogic) {
        this.deliveryImpossibleAreaListDeleteLogic = deliveryImpossibleAreaListDeleteLogic;
    }

    /**
     * 配送特別料金エリア情報を削除します
     *
     * @param entityList List&lt;DeliveryImpossibleAreaEntity&gt;
     * @return int 処理結果
     */
    @Override
    public int execute(List<DeliveryImpossibleAreaEntity> entityList) {
        return deliveryImpossibleAreaListDeleteLogic.execute(entityList);
    }
}
