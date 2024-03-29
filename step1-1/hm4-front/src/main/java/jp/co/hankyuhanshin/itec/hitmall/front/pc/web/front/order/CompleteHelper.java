/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoShop;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order.common.OrderCommonModel;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.OrderUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注文完了画面 Helper
 *
 * @author kimura
 */
@Component
public class CompleteHelper {

    // PDR Migrate Customization from here
    /**
     * 受注関連Utility
     */
    private final OrderUtility orderUtility;

    /**
     * GoodsUtility
     */
    private final GoodsUtility goodsUtility;

    /**
     * 金額計算ユーティリティ
     */
    private CalculatePriceUtility calculatePriceUtility;

    /**
     * コンストラクタ
     *
     * @param orderUtility 受注関連Utility
     * @param goodsUtility GoodsUtility
     */
    @Autowired
    public CompleteHelper(OrderUtility orderUtility,
                          GoodsUtility goodsUtility,
                          CalculatePriceUtility calculatePriceUtility) {
        this.orderUtility = orderUtility;
        this.goodsUtility = goodsUtility;
        this.calculatePriceUtility = calculatePriceUtility;
    }
    // PDR Migrate Customization to here

    /**
     * 画面表示・再表示<br/>
     *
     * @param orderCommonModel 注文フロー共通Model
     * @param confirmModel   注文内容確認画面Model
     */
    public void toPageForLoad(OrderCommonModel orderCommonModel, ConfirmModel confirmModel) {

        // 2023-renew No14 from here
        List<ReceiveOrderDto> receiveOrderDtoList = confirmModel.getReceiveOrderDtoList();
        if (receiveOrderDtoList == null) {
            // 画面リロード時はセッション削除済なので処理スキップ
            return;
        }

        // 受注コードリスト
        confirmModel.setOrderCodeList(new ArrayList<>());
        for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoList) {
            confirmModel.getOrderCodeList().add(receiveOrderDto.getOrderSummaryEntity().getOrderCode());
        }
        // 2023-renew No14 to here

    }

    /**
     * eコマース用データ送信準備を行う
     *
     * @param confirmModel   注文内容確認画面Model
     * @param commonInfoShop ショップ情報（共通情報）
     * @return eコマース用データ送信
     */
    public GoogleAnalyticsInfo toGoogleAnalyticsInfo(ConfirmModel confirmModel, CommonInfoShop commonInfoShop) {
        GoogleAnalyticsInfo googleAnalyticsInfo = new GoogleAnalyticsInfo();

        // PDR Migrate Customization from here
        List<ReceiveOrderDto> receiveOrderDtoList = confirmModel.getReceiveOrderDtoList();
        if (receiveOrderDtoList == null) {
            // 画面リロード時はセッション削除済なので空で返却する
            return googleAnalyticsInfo;
        }

        String orderCode = null;
        BigDecimal orderTotal = BigDecimal.ZERO;
        BigDecimal carriage = BigDecimal.ZERO;
        for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoList) {
            // 受注金額合計を算出
            orderTotal = orderTotal.add(receiveOrderDto.getOrderSummaryEntity().getOrderPrice());
            // 受注コード、送料は最後の受注のものをセットすればOK（2023/11/08 PDRチーム確認済）
            orderCode = receiveOrderDto.getOrderSummaryEntity().getOrderCode();
            carriage = receiveOrderDto.getOrderSettlementEntity().getCarriage();
        }

        // ショップ名
        googleAnalyticsInfo.setShopName(commonInfoShop.getShopNamePC());
        // 受注コード
        googleAnalyticsInfo.setOrderCode(orderCode);
        // 受注金額合計
        googleAnalyticsInfo.setOrderPriceTotal(orderTotal);
        // 消費税額：税計算していないので、0固定
        googleAnalyticsInfo.setTaxPrice(BigDecimal.ZERO);
        // 送料
        googleAnalyticsInfo.setCarriage(carriage);
        // PDR Migrate Customization to here

        // 商品リスト
        googleAnalyticsInfo.setGoodsItemList(new ArrayList<>());
        // PDR Migrate Customization from here
        for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoList) {
            // 受注DTO単位のGAItemDtoリストを作成し 追加
            googleAnalyticsInfo.getGoodsItemList().addAll(createGoogleAnalyticsSalesItem(receiveOrderDto));
        }
        // PDR Migrate Customization to here
        return googleAnalyticsInfo;
    }

    // PDR Migrate Customization from here

    /**
     * GoogleAnalyticsSalesItemのリストを作成（Google Analytics）
     *
     * @param receiveOrderDto receiveOrderDto
     * @return GoogleAnalyticsSalesItemを貯めたリスト
     */
    private List<GoogleAnalyticsSalesItem> createGoogleAnalyticsSalesItem(ReceiveOrderDto receiveOrderDto) {

        // 同商品を個々に計上できないため、同商品はマージし数量を足し込んでいく
        Map<Integer, GoogleAnalyticsSalesItem> gaModelItemsMap = new HashMap<>();

        for (OrderGoodsEntity orderGoods : orderUtility.getALLGoodsEntityList(receiveOrderDto)) {

            Integer goodsSeq = orderGoods.getGoodsSeq();
            // すでに作成済みの商品情報から取得
            GoogleAnalyticsSalesItem gaModelItems = gaModelItemsMap.get(goodsSeq);

            if (gaModelItems == null) {
                // 初めて登場する商品なので、新たに作成
                gaModelItems = new GoogleAnalyticsSalesItem();

                // 商品コード
                gaModelItems.setGoodsCode(orderGoods.getGoodsCode());
                // カテゴリー名：空
                gaModelItems.setCategory("");
                // 商品単価（現行PDRが税込で連携していたので、HM4でも税込計算）
                gaModelItems.setGoodsPrice(calculatePriceUtility.getTaxIncludedPrice(orderGoods.getGoodsPrice(),
                                                                                     orderGoods.getTaxRate()
                                                                                    ));
                // 商品数量
                gaModelItems.setOrderGoodsCount(orderGoods.getGoodsCount());
                // 商品名
                gaModelItems.setGoodsName(goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(orderGoods));
                // 規格値１
                gaModelItems.setUnitValue1(orderGoods.getUnitValue1());
                // 規格値２
                gaModelItems.setUnitValue2(orderGoods.getUnitValue2());

                // Mapに格納
                gaModelItemsMap.put(goodsSeq, gaModelItems);
            } else {
                // すでに作成済みの商品は、数量だけを足しこむ
                gaModelItems.setOrderGoodsCount(gaModelItems.getOrderGoodsCount().add(orderGoods.getGoodsCount()));
            }
        }
        // リストに変換して返却
        return new ArrayList<>(gaModelItemsMap.values());

    }

    // PDR Migrate Customization to here
    // 2023-renew No3-taglog from here

    /**
     * UK連携用データ送信準備を行う
     * @param confirmModel 注文内容確認画面Model
     * @return UK用データ送信
     */
    public UkTaglogCheckoutInfo toUkTaglogInfo(ConfirmModel confirmModel) {
        UkTaglogCheckoutInfo info = new UkTaglogCheckoutInfo();
        List<ReceiveOrderDto> receiveOrderDtoList = confirmModel.getReceiveOrderDtoList();
        if (receiveOrderDtoList == null) {
            // 画面リロード時はセッション削除済なので空で返却する
            return info;
        }

        Map<Integer, UkTaglogCheckoutInfo> goodsMap = new HashMap<Integer, UkTaglogCheckoutInfo>();
        StringBuilder sb = new StringBuilder();

        for (ReceiveOrderDto receiveOrderDto : receiveOrderDtoList) {
            List<OrderGoodsEntity> orderGoodsEntityList = orderUtility.getALLGoodsEntityList(receiveOrderDto);

            for (OrderGoodsEntity orderGoods : orderGoodsEntityList) {
                Integer goodsSeq = orderGoods.getGoodsSeq();
                UkTaglogCheckoutInfo workInfo = goodsMap.get(goodsSeq);

                // 価格が違うのでSKU単位でMapを作成する
                // ユニサーチ側は商品グループコード単位でDB管理しているので、商品グループコードで連携する点に注意
                if (workInfo == null) {
                    workInfo = new UkTaglogCheckoutInfo();
                    workInfo.setGoodsGroupCode(orderGoods.getGoodsGroupCode());
                    // 実際のエンドユーザーの購入金額に近い金額を連携して欲しいとUKから回答あり。
                    // 定価でなく、実際の購入単価（セール込み）を連携するように修正
                    workInfo.setGoodsPrice(orderGoods.getGoodsPrice());
                    workInfo.setGoodsCountTotal(orderGoods.getGoodsCount());
                    goodsMap.put(goodsSeq, workInfo);
                } else {
                    workInfo.setGoodsCountTotal(workInfo.getGoodsCountTotal().add(orderGoods.getGoodsCount()));
                }
            }
        }

        for (Map.Entry<Integer, UkTaglogCheckoutInfo> entry : goodsMap.entrySet()) {
            UkTaglogCheckoutInfo value = entry.getValue();
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(value.getGoodsGroupCode())
              .append(":")
              .append(value.getGoodsCountTotal())
              .append(":")
              .append(value.getGoodsPrice());
        }
        info.setCheckoutString(sb.toString());
        return info;
    }

    // 2023-renew No3-taglog to here

}
