/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.helper.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * PDR#013 09_データ連携（受注データ）<br/>
 * <p>
 * 注文フロー共通データ変換ヘルパークラス
 *
 * @author kimura
 * @author satoh
 */
@Component
public class OrderConversionHelper {

    /**
     * コンストラクタ
     */
    @Autowired
    public OrderConversionHelper() {
        // nop
    }

    /**
     * Entityの変換処理
     * <pre>
     * 商品詳細Dto + 注文数量 ⇒ 受注商品Entity
     * </pre>
     *
     * @param goodsDetailsDto 商品詳細Dto
     * @param goodsCount      注文数量
     * @return 受注商品Entity
     */
    public OrderGoodsEntity toOrderGoodsEntity(GoodsDetailsDto goodsDetailsDto, BigDecimal goodsCount) {
        // 受注商品Entityの生成
        OrderGoodsEntity orderGoodsEntity = ApplicationContextUtility.getBean(OrderGoodsEntity.class);

        // 商品SEQ
        orderGoodsEntity.setGoodsSeq(goodsDetailsDto.getGoodsSeq());
        // 商品コード
        orderGoodsEntity.setGoodsCode(goodsDetailsDto.getGoodsCode());
        // 商品グループコード
        orderGoodsEntity.setGoodsGroupCode(goodsDetailsDto.getGoodsGroupCode());
        // 商品グループ表示名
        orderGoodsEntity.setGoodsGroupName(goodsDetailsDto.getGoodsGroupName());
        // 商品単価（税抜）
        orderGoodsEntity.setGoodsPrice(goodsDetailsDto.getGoodsPrice());
        // 税率
        orderGoodsEntity.setTaxRate(goodsDetailsDto.getTaxRate());
        // 商品消費税種別
        orderGoodsEntity.setGoodsTaxType(goodsDetailsDto.getGoodsTaxType());
        // 注文数量
        orderGoodsEntity.setGoodsCount(goodsCount);
        // 無料配送フラグ
        orderGoodsEntity.setFreeDeliveryFlag(goodsDetailsDto.getFreeDeliveryFlag());
        // 商品個別配送種別
        orderGoodsEntity.setIndividualDeliveryType(goodsDetailsDto.getIndividualDeliveryType());
        // 規格値１
        orderGoodsEntity.setUnitValue1(goodsDetailsDto.getUnitValue1());
        // 規格値２
        orderGoodsEntity.setUnitValue2(goodsDetailsDto.getUnitValue2());
        // JANコード
        orderGoodsEntity.setJanCode(goodsDetailsDto.getJanCode());
        // カタログコード
        orderGoodsEntity.setCatalogCode(goodsDetailsDto.getCatalogCode());
        // 納期
        orderGoodsEntity.setDeliveryType(goodsDetailsDto.getDeliveryType());
        // 受注連携設定01
        orderGoodsEntity.setOrderSetting1(goodsDetailsDto.getOrderSetting1());
        // 受注連携設定02
        orderGoodsEntity.setOrderSetting2(goodsDetailsDto.getOrderSetting2());
        // 受注連携設定03
        orderGoodsEntity.setOrderSetting3(goodsDetailsDto.getOrderSetting3());
        // 受注連携設定04
        orderGoodsEntity.setOrderSetting4(goodsDetailsDto.getOrderSetting4());
        // 受注連携設定05
        orderGoodsEntity.setOrderSetting5(goodsDetailsDto.getOrderSetting5());
        // 受注連携設定06
        orderGoodsEntity.setOrderSetting6(goodsDetailsDto.getOrderSetting6());
        // 受注連携設定07
        orderGoodsEntity.setOrderSetting7(goodsDetailsDto.getOrderSetting7());
        // 受注連携設定08
        orderGoodsEntity.setOrderSetting8(goodsDetailsDto.getOrderSetting8());
        // 受注連携設定09
        orderGoodsEntity.setOrderSetting9(goodsDetailsDto.getOrderSetting9());
        // 受注連携設定10
        orderGoodsEntity.setOrderSetting10(goodsDetailsDto.getOrderSetting10());

        return orderGoodsEntity;
    }
}
