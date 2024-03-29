// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetStockRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetStockResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGetStockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.AbstractWebApiLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetStockLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * #005 02_商品在庫数の制御<br/>
 *
 * <pre>
 * WEB-API連携 商品在庫数取得ロジッククラス
 * </pre>
 *
 * @author satoh
 */
@Component
public class OrderGetStockLogicImpl extends AbstractShopLogic implements OrderGetStockLogic {

    /** WEB-API連携取得  商品在庫数取得 */
    private final WebApiGetStockLogic webApiGetStockLogic;

    /** GoodsUtility */
    private final GoodsUtility goodsUtility;

    @Autowired
    public OrderGetStockLogicImpl(GoodsUtility goodsUtility, WebApiGetStockLogic webApiGetStockLogic) {
        this.goodsUtility = goodsUtility;
        this.webApiGetStockLogic = webApiGetStockLogic;
    }

    /**
     * WEB-API連携 商品在庫数取得を行います。<br/>
     *
     * @param goodsCodeList 商品コードリスト
     * @param quantityList  数量リスト
     * @param customerNo    顧客番号
     * @return 商品コードをキーにした商品在庫数MAP
     */
    @Override
    public Map<String, WebApiGetStockResponseDetailDto> execute(List<String> goodsCodeList,
                                                                List<String> quantityList,
                                                                Integer customerNo) {

        List<String> goodsCodeListForReq = new ArrayList<>();
        List<String> quantityListForReq = new ArrayList<>();

        for (int index = 0; index < goodsCodeList.size(); index++) {
            // 心意気商品コードからkpを削除
            String goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(goodsCodeList.get(index));
            // 重複なければリクエスト用商品コードリストに追加
            if (goodsCodeListForReq.contains(goodsCode)) {
                int quantity = Integer.parseInt(quantityList.get(index));
                quantity = quantity + Integer.parseInt(quantityListForReq.get(goodsCodeListForReq.indexOf(goodsCode)));
                quantityListForReq.set(goodsCodeListForReq.indexOf(goodsCode), new BigDecimal(quantity).toString());
            } else {
                goodsCodeListForReq.add(goodsCode);
                quantityListForReq.add(quantityList.get(index));
            }
        }

        WebApiGetStockRequestDto reqDto = new WebApiGetStockRequestDto();

        // 申込商品 (複数パイプ区切り)
        reqDto.setGoodsCode(AbstractWebApiLogic.createStrPipeByList(goodsCodeListForReq));

        // 数量 (複数パイプ区切り)
        reqDto.setQuantity(AbstractWebApiLogic.createStrPipeByList(quantityListForReq));

        // 顧客番号
        reqDto.setCustomerNo(customerNo);

        WebApiGetStockResponseDto stockDto = (WebApiGetStockResponseDto) webApiGetStockLogic.execute(reqDto);

        if (stockDto.getMap() == null) {
            // 在庫情報MAPが取得できなかった場合はnullで返却
            throwMessage(MSGCD_SYSTEM_ERR);
        }

        return stockDto.getMap();
    }
}
// PDR Migrate Customization to here
