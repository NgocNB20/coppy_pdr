/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodSelectListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.DeliveryMethodSelectListGetService;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配送方法選択可能リスト取得サービス実装クラス
 *
 * @author negishi
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Service
public class DeliveryMethodSelectListGetServiceImpl extends AbstractShopService
                implements DeliveryMethodSelectListGetService {

    /**
     * 配送方法別送料リスト取得ロジック
     */
    private final DeliveryMethodSelectListGetLogic deliveryMethodSelectListGetLogic;

    /**
     * 受注関連ユーティリティ
     */
    private final OrderUtility orderUtility;

    @Autowired
    public DeliveryMethodSelectListGetServiceImpl(DeliveryMethodSelectListGetLogic deliveryMethodSelectListGetLogic,
                                                  OrderUtility orderUtility) {

        this.deliveryMethodSelectListGetLogic = deliveryMethodSelectListGetLogic;
        this.orderUtility = orderUtility;
    }

    /**
     * 実行メソッド
     *
     * @param receiveOrderDto 受注Dto
     * @param available       利用可能、不可能どちらかを指定。null..全部 / true..利用可能のみ / false..利用不可能のみ
     * @return 決済Dtoリスト
     */
    @Override
    public List<DeliveryDto> execute(ReceiveOrderDto receiveOrderDto, Boolean available, HTypeSiteType siteType) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("receiveOrderDto", receiveOrderDto);
        OrderSettlementEntity orderSettlementEntity = receiveOrderDto.getOrderSettlementEntity();
        List<OrderGoodsEntity> orderGoodsEntityList = receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList();
        OrderDeliveryEntity orderDeliveryEntity = receiveOrderDto.getOrderDeliveryDto().getOrderDeliveryEntity();
        ArgumentCheckUtil.assertNotNull("orderGoodsEntityList", orderGoodsEntityList);
        ArgumentCheckUtil.assertNotNull("orderSettlementEntity", orderSettlementEntity);
        ArgumentCheckUtil.assertNotNull("orderDeliveryEntity", orderDeliveryEntity);
        ArgumentCheckUtil.assertNotNull("goodsPriceTotal", orderSettlementEntity.getGoodsPriceTotal());
        ArgumentCheckUtil.assertNotEmpty("receiverZipCode", orderDeliveryEntity.getReceiverZipCode());
        // PDR Migrate Customization from here
        // 都道府県の必須チェック 削除
        //ArgumentCheckUtil.assertNotEmpty("receiverPrefecture", orderDeliveryEntity.getReceiverPrefecture());
        // PDR Migrate Customization to here

        // 無料配送商品が含まれているか検索
        HTypeFreeDeliveryFlag freeDeliveryFlag = getFreeDeliveryFlag(orderGoodsEntityList);

        // 配送方法Dao用検索条件DTO作成
        DeliverySearchForDaoConditionDto conditionDto = getComponent(DeliverySearchForDaoConditionDto.class);
        conditionDto.setShopSeq(1001);
        conditionDto.setTotalGoodsPrice(orderUtility.getGoodsPriceTotal(orderGoodsEntityList));
        // お届け先都道府県から、都道府県種別に変換
        // PDR Migrate Customization from here
        // 固定値で東京を設定するよう修正
        HTypePrefectureType prefectureType = HTypePrefectureType.TOKYO;
        // PDR Migrate Customization to here
        conditionDto.setPrefectureType(prefectureType);
        conditionDto.setZipcode(orderDeliveryEntity.getReceiverZipCode());

        // 共通情報からサイト種別を取得し、フロントの場合のみ公開されているものを取得
        if (siteType.isFront()) {
            conditionDto.setOpenStatusPC(HTypeOpenDeleteStatus.OPEN);
        }

        // 配送方法別送料リスト取得ロジック実行
        return deliveryMethodSelectListGetLogic.execute(conditionDto, null, freeDeliveryFlag, available);
    }

    /**
     * 実行メソッド
     *
     * @param receiveOrderDto 受注Dto
     * @return 決済Dtoリスト
     */
    @Override
    public List<DeliveryDto> execute(ReceiveOrderDto receiveOrderDto) {
        return execute(receiveOrderDto, null, HTypeSiteType.FRONT_PC);
    }

    /**
     * 無料配送商品ありチェック<br/>
     *
     * @param orderGoodsEntityList 受注商品エンティティリスト
     * @return 無料配送フラグ
     */
    protected HTypeFreeDeliveryFlag getFreeDeliveryFlag(List<OrderGoodsEntity> orderGoodsEntityList) {
        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
            if (HTypeFreeDeliveryFlag.ON.equals(orderGoodsEntity.getFreeDeliveryFlag())) {
                return HTypeFreeDeliveryFlag.ON;
            }
        }
        return HTypeFreeDeliveryFlag.OFF;
    }
}
