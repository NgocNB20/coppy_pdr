//  Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetQuantityDiscountResultRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetQuantityDiscountResultResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetQuantityDiscountResultResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderQuantityDiscountLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.AbstractWebApiLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetQuantityDiscountResultLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * #013 09_データ連携（受注データ）<br/>
 *
 * <pre>
 * 数量割引取得結果連携ロジッククラス
 * </pre>
 *
 * @author satoh
 */
@Component
public class OrderQuantityDiscountLogicImpl extends AbstractShopLogic implements OrderQuantityDiscountLogic {

    /**
     * WEB-API連携クラス 数量割引適用結果取得
     */
    private final WebApiGetQuantityDiscountResultLogic webApiGetQuantityDiscountResultLogic;

    /**
     * 受注業務ユーティリティクラス
     */
    private final OrderUtility orderUtility;

    /**
     * 商品系ヘルパークラス
     */
    private final GoodsUtility goodsUtility;

    /**
     * 変換Utility
     */
    private final ConversionUtility conversionUtility;

    @Autowired
    public OrderQuantityDiscountLogicImpl(WebApiGetQuantityDiscountResultLogic webApiGetQuantityDiscountResultLogic,
                                          OrderUtility orderUtility,
                                          GoodsUtility goodsUtility,
                                          ConversionUtility conversionUtility) {
        this.webApiGetQuantityDiscountResultLogic = webApiGetQuantityDiscountResultLogic;
        this.orderUtility = orderUtility;
        this.goodsUtility = goodsUtility;
        this.conversionUtility = conversionUtility;
    }

    /**
     * 数量割引適用結果取得を行い、
     * 受注DTOに反映を行います。
     *
     * @param receiveOrderDto     受注DTO
     * @param taxRateMap          消費税率MAP
     * @param checkMessageDtoList エラーメッセージ用List
     */
    public void execute(ReceiveOrderDto receiveOrderDto,
                        Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> taxRateMap,
                        List<CheckMessageDto> checkMessageDtoList) {

        List<OrderGoodsEntity> orderGoodsEntityList = receiveOrderDto.getOrderDeliveryDto().getOrderGoodsEntityList();

        // 商品マスタMAP
        Map<Integer, GoodsDetailsDto> goodsDetailsDtoMap = receiveOrderDto.getMasterDto().getGoodsMaster();
        Map<String, WebApiGetQuantityDiscountResultResponseDetailDto> quantityDiscountResultMap =
                        executeWebApiGetQuantityDiscountResultResponse(orderGoodsEntityList,
                                                                       (receiveOrderDto.getOrderPersonEntity()).getCustomerNo()
                                                                      );

        for (OrderGoodsEntity entity : orderGoodsEntityList) {

            GoodsDetailsDto goodsDetailsDto = goodsDetailsDtoMap.get(entity.getGoodsSeq());

            // 商品コード
            String goodsCode = entity.getGoodsCode();

            // 数量割引適用結果
            WebApiGetQuantityDiscountResultResponseDetailDto discountDto = quantityDiscountResultMap.get(goodsCode);

            // discountDto取得後、taxRateDto取得前に心意気商品の商品コードの場合、kpを削除
            goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(entity.getGoodsCode());

            // 消費税率
            WebApiGetConsumptionTaxRateResponseDetailDto taxRateDto = taxRateMap.get(goodsCode);

            if (discountDto == null || goodsDetailsDto == null || taxRateDto == null) {
                // 必要な情報がない場合 エラー
                throwMessage(MSGCD_SYSTEM_ERR);
            }

            // 前回表示した価格と割引適用結果価格が一致しない場合
            if (entity.getGoodsPrice().compareTo(discountDto.getSalePrice()) != 0) {
                // 画面にメッセージを表示
                CheckMessageDto checkMessageDto = new CheckMessageDto();
                checkMessageDto.setMessageId(MSGCD_DISCOUNT_CHANGE);
                checkMessageDto.setArgs(new String[] {orderUtility.createErrDispGoodsName(entity)});
                checkMessageDtoList.add(checkMessageDto);
            }

            // 単価 更新
            entity.setGoodsPrice(discountDto.getSalePrice());
            // 数量割引グループコード
            entity.setGroupCode(discountDto.getSaleGroupCode());
            // セールコード
            entity.setSaleCode(discountDto.getSaleCode());
            // 備考
            entity.setNote(discountDto.getNote());
            // 注意事項
            entity.setHints(discountDto.getHints());
            // 価格
            entity.setPrice(discountDto.getPrice());
        }
    }

    /**
     * WEB-API連携 数量割引適用結果取得を行い<br />
     * 結果MAPを返却します。
     *
     * @param orderGoodsEntityList 受注商品クラス
     * @param customerNo           顧客番号
     * @return 数量割引適用結果取得
     */
    public Map<String, WebApiGetQuantityDiscountResultResponseDetailDto> executeWebApiGetQuantityDiscountResultResponse(
                    List<OrderGoodsEntity> orderGoodsEntityList,
                    Integer customerNo) {

        List<String> goodsCodeList = new ArrayList<>();
        List<String> quantityList = new ArrayList<>();

        // 重複商品を集約
        for (OrderGoodsEntity orderGoodsEntity : orderGoodsEntityList) {
            // 心意気商品の商品コードが連携されないよう、kpを削除
            String goodsCode =
                            goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(orderGoodsEntity.getGoodsCode());
            // 商品コードリストに既に商品コードが存在する場合、数量リストから同じインデックスの数値を取り出し、加算する
            // 心意気商品と通常商品の両方がカートに存在する場合に、一つのリクエストにまとめるための処理
            if (goodsCodeList.contains(goodsCode)) {
                int quantity = new BigDecimal(quantityList.get(goodsCodeList.indexOf(goodsCode))).intValue();
                quantity = quantity + orderGoodsEntity.getGoodsCount().intValue();
                quantityList.set(goodsCodeList.indexOf(goodsCode), new BigDecimal(quantity).toString());
            } else {
                goodsCodeList.add(goodsCode);
                quantityList.add(String.valueOf(orderGoodsEntity.getGoodsCount()));
            }
        }

        // Web-API リクエストDTO
        WebApiGetQuantityDiscountResultRequestDto reqDto =
                        ApplicationContextUtility.getBean(WebApiGetQuantityDiscountResultRequestDto.class);
        // 顧客番号
        reqDto.setCustomerNo(conversionUtility.toString(customerNo));

        // 複数存在する項目をパイプ区切りに変換
        // 申込商品
        reqDto.setGoodsCode(AbstractWebApiLogic.createStrPipeByList(goodsCodeList));
        // 数量
        reqDto.setQuantity(AbstractWebApiLogic.createStrPipeByList(quantityList));

        // WEB-API実行
        WebApiGetQuantityDiscountResultResponseDto consumptionTaxRateResponseDto =
                        (WebApiGetQuantityDiscountResultResponseDto) webApiGetQuantityDiscountResultLogic.execute(
                                        reqDto);

        return consumptionTaxRateResponseDto.getMap();
    }
}
//  Customization to here
