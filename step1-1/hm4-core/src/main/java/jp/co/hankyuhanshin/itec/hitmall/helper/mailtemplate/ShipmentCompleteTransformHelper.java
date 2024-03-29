/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetShipmentInformationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetShipmentInformationResponseDetailGoodsInfoDto;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.MailTemplateDummyMapUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 注文系メールテンプレート用トランスフォーマ。
 *
 * @author tm27400
 * @author natsume
 * @author Tomo (itec) ダミー値マップ動的差し替え対応
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component("shipmentCompleteTransformHelper")
public class ShipmentCompleteTransformHelper implements Transformer {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentCompleteTransformHelper.class);

    /**
     * 日付関連Utilityクラス
     */
    private final DateUtility dateUtility;

    /**
     * 商品系ヘルパークラス
     */
    private final GoodsUtility goodsUtility;

    @Autowired
    public ShipmentCompleteTransformHelper(DateUtility dateUtility, GoodsUtility goodsUtility) {
        this.dateUtility = dateUtility;
        this.goodsUtility = goodsUtility;
    }

    /**
     * ダミープレースホルダを返す
     *
     * @return ダミープレースホルダ
     */
    @Override
    public Map<String, String> getDummyPlaceholderMap() {
        // メールテンプレート用ダミー値マップ取得用Helper取得
        MailTemplateDummyMapUtility mailTemplateDummyMapUtility =
                        ApplicationContextUtility.getBean(MailTemplateDummyMapUtility.class);

        return mailTemplateDummyMapUtility.getDummyValueMap(getResourceName());
    }

    /**
     * 値マップを取得する
     *
     * @param arguments 引数
     * @return 値マップ
     */
    @Override
    public Map<String, String> toValueMap(Object... arguments) {

        Format priceFormat = new DecimalFormat("#,##0");

        // 引数チェック
        this.checkArguments(arguments);
        // メールテンプレート用ダミー値マップ取得用Helper取得
        MailTemplateDummyMapUtility mailTemplateDummyMapUtility =
                        ApplicationContextUtility.getBean(MailTemplateDummyMapUtility.class);

        if (arguments == null) {
            return mailTemplateDummyMapUtility.getDummyValueMap(getResourceName());
        } else if (arguments.length == 0) {
            return new HashMap<>();
        }

        // 出荷情報取得Web-API結果Dto
        WebApiGetShipmentInformationResponseDetailDto shipmentInformationDetailDto =
                        (WebApiGetShipmentInformationResponseDetailDto) arguments[0];

        Map<String, String> valueMap = new LinkedHashMap<String, String>();

        valueMap.put("NAME", shipmentInformationDetailDto.getOfficeName());
        valueMap.put("RECEIVER_NAME", shipmentInformationDetailDto.getReceiveOfficeName());
        valueMap.put("RECEIVER_ZIP_CODE", shipmentInformationDetailDto.getReceiveZipcode());
        // 住所連結用文字列
        StringBuilder addressJoint = new StringBuilder();
        // 住所連結
        addressJoint.append(shipmentInformationDetailDto.getReceiveAddress1());
        addressJoint.append(shipmentInformationDetailDto.getReceiveAddress2());
        // お届け先住所(建物名・部屋番号)があれば連結
        if (shipmentInformationDetailDto.getReceiveAddress3() != null) {
            addressJoint.append(shipmentInformationDetailDto.getReceiveAddress3());
        }
        // お届け先住所(方書1)があれば連結
        if (shipmentInformationDetailDto.getReceiveAddress4() != null) {
            addressJoint.append(shipmentInformationDetailDto.getReceiveAddress4());
        }
        // お届け先住所(方書2)があれば連結
        if (shipmentInformationDetailDto.getReceiveAddress5() != null) {
            addressJoint.append(shipmentInformationDetailDto.getReceiveAddress5());
        }
        valueMap.put("RECEIVER_ADDRESS", addressJoint.toString());
        valueMap.put("ORDER_CODE", shipmentInformationDetailDto.getOrderNo().toString());
        valueMap.put("INVOICE_NO", shipmentInformationDetailDto.getInvoiceNo());
        valueMap.put("SUB_TOTAL_PRICE",
                     priceFormat.format(new BigDecimal(shipmentInformationDetailDto.getSubTotalPrice()))
                    );
        valueMap.put("DISCOUNT", priceFormat.format(new BigDecimal(shipmentInformationDetailDto.getDiscountPrice())));
        valueMap.put("CARRIAGE", priceFormat.format(new BigDecimal(shipmentInformationDetailDto.getShippingPrice())));
        valueMap.put("TAX", priceFormat.format(new BigDecimal(shipmentInformationDetailDto.getTaxPrice())));
        valueMap.put("TOTAL_PRICE", priceFormat.format(new BigDecimal(shipmentInformationDetailDto.getTotalPrice())));
        valueMap.put("DELIVERY_METHOD", shipmentInformationDetailDto.getDeliveryName());
        valueMap.put("PAYMENT_METHOD", shipmentInformationDetailDto.getPaymentName());
        if (shipmentInformationDetailDto.getDeliveryDesignatedDay() != null) {
            valueMap.put("RECEIVER_DATE", dateUtility.format(shipmentInformationDetailDto.getDeliveryDesignatedDay(),
                                                             DateUtility.YMD_SLASH
                                                            ));
        } else {
            valueMap.put("RECEIVER_DATE", "");
        }
        if (shipmentInformationDetailDto.getDeliveryDesignatedTimeName() != null) {
            valueMap.put("RECEIVER_TIME_ZONE", shipmentInformationDetailDto.getDeliveryDesignatedTimeName());
        } else {
            valueMap.put("RECEIVER_TIME_ZONE", "");
        }
        valueMap.put("DELIVERY_METHOD_URL", shipmentInformationDetailDto.getDeliveryComfirmURL());

        // 商品情報
        List<WebApiGetShipmentInformationResponseDetailGoodsInfoDto> goodsInfoList =
                        shipmentInformationDetailDto.getGoodsList();
        if (goodsInfoList != null) {
            int counter = 1;
            for (WebApiGetShipmentInformationResponseDetailGoodsInfoDto goodsInfo : goodsInfoList) {
                valueMap.put("GOODS_CODE" + LOOP_STR + counter, goodsInfo.getGoodsNo());
                goodsInfo.setGoodsName(
                                goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(goodsInfo.getGoodsName(),
                                                                                           goodsInfo.getDiscountFlag()
                                                                                          ));
                valueMap.put("GOODS_NAME" + LOOP_STR + counter, goodsInfo.getGoodsName());
                valueMap.put("GOODS_COUNT" + LOOP_STR + counter, goodsInfo.getCount().toString());
                valueMap.put("UNIT" + LOOP_STR + counter, goodsInfo.getUnitName());
                valueMap.put("UNIT_PRICE" + LOOP_STR + counter,
                             priceFormat.format(new BigDecimal(goodsInfo.getUnitPrice()))
                            );
                valueMap.put("PRICE" + LOOP_STR + counter, priceFormat.format(new BigDecimal(goodsInfo.getPrice())));
                counter++;
            }
            valueMap.put("COUNTER_TOTAL", String.valueOf(counter - 1));
        }
        return valueMap;
    }

    /**
     * 数値を金額形式（#,##0）にフォーマットする。
     *
     * @param num 数値
     * @return 金額形式の文字列
     */
    protected String priceFormat(BigDecimal num) {
        if (num == null) {
            return "0";
        }

        Format priceFormat = new DecimalFormat("#,##0");
        return priceFormat.format(num);
    }

    /**
     * 引数の有効性を確認する
     *
     * @param arguments 引数
     */
    protected void checkArguments(Object[] arguments) {

        // オブジェクトがない場合はテスト送信用とみなす
        if (arguments == null) {
            return;
        }

        if (arguments.length != 1) {
            RuntimeException e = new IllegalArgumentException("プレースホルダ用値マップに変換できません：引数の数が合いません。");
            LOGGER.warn("引数チェックエラー", e);
            throw e;
        }

        if (!(arguments[0] instanceof WebApiGetShipmentInformationResponseDetailDto)) {
            RuntimeException e = new IllegalArgumentException("プレースホルダ用値マップに変換できません：引数の型が合いません。");
            LOGGER.warn("引数チェックエラー", e);
            throw e;
        }
    }

    /**
     * リソースファイル名<br/>
     *
     * @return リソースファイル名
     */
    @Override
    public String getResourceName() {
        return "ShipmentCompleteTransformHelper";
    }

}
