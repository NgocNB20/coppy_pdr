/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.delivery.OrderDeliveryDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderDeliveryDtoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 受注配送Dto取得Logic実装クラス
 *
 * @author s_tsuru
 */
@Component
public class OrderDeliveryDtoGetLogicImpl extends AbstractShopLogic implements OrderDeliveryDtoGetLogic {

    /**
     * 受注配送Dao
     */
    private final OrderDeliveryDao orderDeliveryDao;

    /**
     * 受注商品取得Logic
     */
    private final OrderGoodsListGetLogic orderGoodsListGetLogic;

    /**
     * 配送方法取得ロジック
     */
    private final DeliveryMethodGetLogic deliveryMethodGetLogic;

    @Autowired
    public OrderDeliveryDtoGetLogicImpl(OrderDeliveryDao orderDeliveryDao,
                                        OrderGoodsListGetLogic orderGoodsListGetLogic,
                                        DeliveryMethodGetLogic deliveryMethodGetLogic) {
        this.orderDeliveryDao = orderDeliveryDao;
        this.orderGoodsListGetLogic = orderGoodsListGetLogic;
        this.deliveryMethodGetLogic = deliveryMethodGetLogic;
    }

    /**
     * メソッド実行<br/>
     *
     * @param orderSeq               受注SEQ
     * @param orderDeliveryVersionNo 受注配送連番
     * @param orderGoodsVersionNo    受注商品連番
     * @return List&lt;OrderDeliveryDto&gt;
     */
    @Override
    public OrderDeliveryDto execute(Integer orderSeq, Integer orderDeliveryVersionNo, Integer orderGoodsVersionNo) {
        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderDeliveryVersionNo", orderDeliveryVersionNo);
        ArgumentCheckUtil.assertNotNull("orderGoodsVersionNo", orderGoodsVersionNo);

        // 受注配送Dtoリスト取得
        List<OrderDeliveryEntity> orderDeliveryEntityList =
                        orderDeliveryDao.getDtoListByOrderSeq(orderSeq, orderDeliveryVersionNo);
        if (orderDeliveryEntityList == null || orderDeliveryEntityList.isEmpty()) {
            return null;
        }

        OrderDeliveryDto orderDeliveryDto = ApplicationContextUtility.getBean(OrderDeliveryDto.class);

        // TODO-QUAD-1222
        // 受注商品Entityリスト
        for (OrderDeliveryEntity orderDeliveryEntity : orderDeliveryEntityList) {

            orderDeliveryDto.setOrderDeliveryEntity(orderDeliveryEntity);

            // 配送方法の取得
            DeliveryMethodEntity deliveryMethodEntity =
                            deliveryMethodGetLogic.execute(orderDeliveryEntity.getDeliveryMethodSeq());
            if (deliveryMethodEntity == null) {
                throwMessage(MSGCD_DELIVERYMETHODENTITYDTO_NULL);
            }
            orderDeliveryDto.setDeliveryMethodEntity(deliveryMethodEntity);

            // 受注商品の取得
            List<OrderGoodsEntity> orderGoodsEntityList = orderGoodsListGetLogic.execute(orderSeq, orderGoodsVersionNo,
                                                                                         orderDeliveryEntity.getOrderConsecutiveNo()
                                                                                        );
            if (CollectionUtil.isEmpty(orderGoodsEntityList)) {
                throwMessage(MSGCD_ORDERGOODSENTITYDTOLIST_EMPTY);
            }
            orderDeliveryDto.setOrderGoodsEntityList(orderGoodsEntityList);

            // 前回送料
            orderDeliveryDto.setOriginalCarriage(orderDeliveryDto.getOrderDeliveryEntity().getCarriage());
        }

        return orderDeliveryDto;
    }
}
