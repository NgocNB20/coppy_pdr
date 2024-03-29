/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsMixedTaxCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.CheckMessageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 商品番号に複数税率が含まれるかチェックするLogic
 *
 * @author okawa
 */
@Component
public class OrderGoodsMixedTaxCheckLogicImpl extends AbstractShopLogic implements OrderGoodsMixedTaxCheckLogic {

    /**
     * CheckMessageUtility
     */
    private final CheckMessageUtility checkMessageUtility;

    @Autowired
    public OrderGoodsMixedTaxCheckLogicImpl(CheckMessageUtility checkMessageUtility) {
        this.checkMessageUtility = checkMessageUtility;
    }

    @Override
    public List<CheckMessageDto> checkOrderGoodsMixedTax(ReceiveOrderDto receiveOrderDto, String messageId) {

        // 商品番号ごとに税率を格納するMap
        Map<String, Set<BigDecimal>> goodsCodeToTaxRatesMap = getTaxRatesInGoodsCodeMap(receiveOrderDto);

        // エラーメッセージリストを作成
        List<CheckMessageDto> checkMessageDtoList = new ArrayList<>();

        // 同一商品内に複数の税率が混在するかチェック
        for (String goodsCodeKey : goodsCodeToTaxRatesMap.keySet()) {
            // 複数の税率が混在する場合、警告を表示
            if (goodsCodeToTaxRatesMap.get(goodsCodeKey).size() > 1) {
                CheckMessageDto error =
                                checkMessageUtility.createCheckMessageDto(messageId, new Object[] {goodsCodeKey});
                checkMessageDtoList.add(error);
            }
        }

        return checkMessageDtoList;
    }

    /**
     * 商品番号に含まれる税率Mapを取得<br/>
     *
     * @param receiveOrderDto 受注Dto
     * @return 商品番号ごとの税率Map
     */
    protected Map<String, Set<BigDecimal>> getTaxRatesInGoodsCodeMap(ReceiveOrderDto receiveOrderDto) {
        // 商品番号ごとに税率を格納するMap
        Map<String, Set<BigDecimal>> goodsCodeToTaxRatesMap = new LinkedHashMap<>();

        for (OrderGoodsEntity orderGoodsEntity : receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList()) {
            // 数量が0の場合はチェック対象外
            if (BigDecimal.ZERO.compareTo(orderGoodsEntity.getGoodsCount()) == 0) {
                continue;
            }

            // 商品番号ごとに税率を格納
            if (goodsCodeToTaxRatesMap.containsKey(orderGoodsEntity.getGoodsCode())) {
                // 既存商品番号に税率を格納
                Set<BigDecimal> taxSet = goodsCodeToTaxRatesMap.get(orderGoodsEntity.getGoodsCode());
                taxSet.add(orderGoodsEntity.getTaxRate());
            } else {
                // 新規商品番号に税率を格納
                Set<BigDecimal> taxSet = new HashSet<>();
                taxSet.add(orderGoodsEntity.getTaxRate());
                goodsCodeToTaxRatesMap.put(orderGoodsEntity.getGoodsCode(), taxSet);
            }
        }

        return goodsCodeToTaxRatesMap;
    }

}
