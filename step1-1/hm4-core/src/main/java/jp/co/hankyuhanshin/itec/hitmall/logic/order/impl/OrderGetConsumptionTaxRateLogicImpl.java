// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetConsumptionTaxRateRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGetConsumptionTaxRateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.AbstractWebApiLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetConsumptionTaxRateLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * #025 消費税の計算<br/>
 *
 * <pre>
 * WEB-API連携 消費税率取得ロジッククラス
 * </pre>
 *
 * @author satoh
 */
@Component
public class OrderGetConsumptionTaxRateLogicImpl extends AbstractShopLogic implements OrderGetConsumptionTaxRateLogic {
    /** WEB-API連携クラス 消費税率取得 */
    private final WebApiGetConsumptionTaxRateLogic webApiGetConsumptionTaxRateLogic;

    /** GoodsUtility */
    private final GoodsUtility goodsUtility;

    @Autowired
    public OrderGetConsumptionTaxRateLogicImpl(GoodsUtility goodsUtility,
                                               WebApiGetConsumptionTaxRateLogic webApiGetConsumptionTaxRateLogic) {
        this.goodsUtility = goodsUtility;
        this.webApiGetConsumptionTaxRateLogic = webApiGetConsumptionTaxRateLogic;
    }

    /**
     * WEB-API連携 消費税率取得を行い<br />
     * 結果を返却します。
     *
     * @param goodsCodeList 商品コードリスト
     * @return 消費税率MAP
     */
    @Override
    public Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> execute(List<String> goodsCodeList) {

        // リクエスト用商品コードリスト
        List<String> goodsCodeListForReq = new ArrayList<>();
        for (String goodsCode : goodsCodeList) {
            // 心意気商品コードからkpを削除
            goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(goodsCode);
            // 重複なければリクエスト用商品コードリストに追加
            if (!goodsCodeListForReq.contains(goodsCode)) {
                goodsCodeListForReq.add(goodsCode);
            }
        }
        WebApiGetConsumptionTaxRateRequestDto reqDto = new WebApiGetConsumptionTaxRateRequestDto();

        // 送料の消費税率取得コードを追加
        goodsCodeListForReq.add(WebApiGetConsumptionTaxRateRequestDto.CARRIAGE_CODE);

        // プロモーション割引の消費税率取得コードを追加
        goodsCodeListForReq.add(WebApiGetConsumptionTaxRateRequestDto.PROMO_DISCOUNT_CODE);

        reqDto.setGoodsCode(AbstractWebApiLogic.createStrPipeByList(goodsCodeListForReq));

        WebApiGetConsumptionTaxRateResponseDto consumptionTaxRateResponseDto =
                        (WebApiGetConsumptionTaxRateResponseDto) webApiGetConsumptionTaxRateLogic.execute(reqDto);

        if (consumptionTaxRateResponseDto.getMap() == null) {
            throwMessage(MSGCD_SYSTEM_ERR);
        }

        return consumptionTaxRateResponseDto.getMap();
    }
}
// PDR Migrate Customization to here
