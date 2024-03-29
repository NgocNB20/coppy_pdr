/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGetReserveLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.AbstractWebApiLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetReserveLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * #006 03_取りおきサービス<br/>
 *
 * <pre>
 * 商品在庫情報ユーティリティクラス
 * </pre>
 *
 * @author satoh
 */
@Component
// PDR Migrate Customization from here
public class OrderGetReserveLogicImpl extends AbstractShopLogic implements OrderGetReserveLogic {

    /** WEB-API連携取得 取りおき情報取得 */
    private final WebApiGetReserveLogic webApiGetReserveLogic;

    /** 受注関連ユーティリティ */
    private final OrderUtility orderUtility;

    /** GoodsUtility */
    private final GoodsUtility goodsUtility;

    @Autowired
    public OrderGetReserveLogicImpl(OrderUtility orderUtility,
                                    GoodsUtility goodsUtility,
                                    WebApiGetReserveLogic webApiGetReserveLogic) {
        this.orderUtility = orderUtility;
        this.goodsUtility = goodsUtility;
        this.webApiGetReserveLogic = webApiGetReserveLogic;
    }

    /**
     * WEB-API連携 取りおき情報取得を行います。<br/>
     *
     * @param orderDeliveryDto 受注配送Dtoクラス
     * @param customerNo       注文主の顧客番号
     * @return 取りおき情報MAP
     */
    @Override
    public Map<String, WebApiGetReserveResponseDetailDto> execute(OrderDeliveryDto orderDeliveryDto,
                                                                  Integer customerNo) {

        List<String> goodsCodeList = new ArrayList<>();

        // 重複商品を集約
        for (String goodsCode : orderUtility.getGoodsCodeList(orderDeliveryDto.getOrderGoodsEntityList())) {
            // 心意気商品コードからkpを削除
            goodsCode = goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(goodsCode);
            // 重複なければリクエスト用商品コードリストに追加
            if (!goodsCodeList.contains(goodsCode)) {
                goodsCodeList.add(goodsCode);
            }
        }

        // Web-API リクエストDTO
        WebApiGetReserveRequestDto reqDto = new WebApiGetReserveRequestDto();

        // 顧客番号
        reqDto.setCustomerNo(customerNo);
        // 配送先顧客番号
        if (ADD_TYPE_ADDRESS_BOOK.equals(orderDeliveryDto.getAddType())) {
            // 住所録のお届け先を選択
            reqDto.setDeliveryCustomerNo(orderDeliveryDto.getCustomerNo());
        } else {
            reqDto.setDeliveryCustomerNo(customerNo);
        }
        // 配送先郵便番号
        reqDto.setDeliveryZipcode(orderDeliveryDto.getOrderDeliveryEntity().getReceiverZipCode());

        // 複数存在する項目をパイプ区切りに変換
        // 商品コード
        reqDto.setGoodsCode(AbstractWebApiLogic.createStrPipeByList(goodsCodeList));

        // WEB-API実行
        WebApiGetReserveResponseDto resDto = (WebApiGetReserveResponseDto) webApiGetReserveLogic.execute(reqDto);
        if (resDto.getMap() == null) {
            throwMessage(MSGCD_SYSTEM_ERR);
        }

        return resDto.getMap();
    }

}
// PDR Migrate Customization to here
