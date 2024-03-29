/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTaxRateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderInfoMasterDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryMethodDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.tax.TaxRateEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.tax.TaxGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderInfoMasterGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.delivery.DeliveryMethodDetailsGetService;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 注文情報マスタ取得Service<br/>
 *
 * @author h_hakogi
 */
@Service
public class OrderInfoMasterGetServiceImpl extends AbstractShopService implements OrderInfoMasterGetService {

    /**
     * 全配送方法エンティティリスト取得サービス
     */
    private final DeliveryMethodDetailsGetService deliveryMethodDetailsGetService;

    /**
     * 消費税情報取得Logic
     */
    private final TaxGetLogic taxGetLogic;

    private final OrderUtility orderUtility;

    @Autowired
    public OrderInfoMasterGetServiceImpl(DeliveryMethodDetailsGetService deliveryMethodDetailsGetService,
                                         TaxGetLogic taxGetLogic,
                                         OrderUtility orderUtility) {

        this.deliveryMethodDetailsGetService = deliveryMethodDetailsGetService;
        this.taxGetLogic = taxGetLogic;
        this.orderUtility = orderUtility;
    }

    /**
     * サービス実行<br/>
     *
     * @param cartDto カートDto
     * @return 注文情報マスタDto
     */
    @Override
    public OrderInfoMasterDto execute(CartDto cartDto) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("cartDto", cartDto);

        OrderInfoMasterDto orderInfoMasterDto = ApplicationContextUtility.getBean(OrderInfoMasterDto.class);

        // 商品マスタ設定
        orderInfoMasterDto.setGoodsMaster(createGoodsMaster(cartDto));

        // 配送方法マスタ
        orderInfoMasterDto.setDeliveryMethodMaster(getDeliveryMethodMaster(cartDto));

        // 税率マスタ
        orderInfoMasterDto.setTaxRateMaster(getTaxRateMaster());

        return orderInfoMasterDto;
    }

    /**
     * 商品マスタ取得<br/>
     *
     * @param cartDto カートDTO
     * @return 商品マスタ
     */
    protected Map<Integer, GoodsDetailsDto> createGoodsMaster(CartDto cartDto) {
        // 商品マスタ取得
        Map<Integer, GoodsDetailsDto> goodsDetailsMap = new HashMap<>();
        for (CartGoodsDto dto : cartDto.getCartGoodsDtoList()) {
            GoodsDetailsDto goodsDetailsDto = dto.getGoodsDetailsDto();
            goodsDetailsMap.put(goodsDetailsDto.getGoodsSeq(), goodsDetailsDto);
        }
        return goodsDetailsMap;
    }

    /**
     * 配送方法マスタ取得<br/>
     *
     * @param cartDto カートDTO
     * @return 配送方法マスタ
     */
    protected Map<Integer, DeliveryMethodDetailsDto> getDeliveryMethodMaster(CartDto cartDto) {

        // 配送方法マスタ取得
        Set<Integer> seqSet = new HashSet<>();
        // PDR Migrate Customization from here
        // ダミーの配送方法を取得します。
        seqSet.add(Integer.parseInt(OrderUtility.DUMMY_DELIVERY_METHOD_SEQ));
        // PDR Migrate Customization to here
        Map<Integer, DeliveryMethodDetailsDto> dMap = deliveryMethodDetailsGetService.execute(new ArrayList<>(seqSet));
        return dMap;
    }

    /**
     * 税率マスタ取得<br/>
     *
     * @return 税率マスタ
     */
    protected Map<HTypeTaxRateType, TaxRateEntity> getTaxRateMaster() {
        return taxGetLogic.getEffectiveTaxRateMap();
    }

}
