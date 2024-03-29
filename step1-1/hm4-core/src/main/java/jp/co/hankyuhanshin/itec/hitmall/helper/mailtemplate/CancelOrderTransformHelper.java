/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.orderhistory.CancelOrderHistoryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.orderhistory.CancelOrderHistoryGoodsDto;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.MailTemplateDummyMapUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
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
import java.util.Map;

/**
 * 注文キャンセルメールデータ変換ヘルパークラス
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
// 2023-renew No68 from here
public class CancelOrderTransformHelper implements Transformer {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderTransformHelper.class);

    /**
     * 日付関連Utilityクラス
     */
    private final DateUtility dateUtility;

    /**
     * 商品系ヘルパークラス
     */
    private final GoodsUtility goodsUtility;

    /**
     * コンストラクタ
     */
    @Autowired
    public CancelOrderTransformHelper(DateUtility dateUtility, GoodsUtility goodsUtility) {
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

    @Override
    public String getResourceName() {
        return "CancelOrderTransformHelper";
    }

    /**
     * テンプレートタイプ00の
     * メール送信に使用する値マップを作成する。
     *
     * @param arguments 引数
     * @return 値マップ
     */
    @Override
    public Map<String, String> toValueMap(Object... arguments) {

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

        CancelOrderHistoryDto cancelOrderHistoryDto = (CancelOrderHistoryDto) arguments[0];

        Map<String, String> valueMap = new LinkedHashMap<>();

        // 事業所名
        valueMap.put("M_LAST_NAME", (cancelOrderHistoryDto.getOfficeName()));
        // ご注文日時
        valueMap.put("ORDER_TIME", dateUtility.format(cancelOrderHistoryDto.getOrderDate(), DateUtility.YMD_SLASH_HM));
        // ご注文番号
        valueMap.put("ORDER_CODE", cancelOrderHistoryDto.getOrderNo());
        // キャンセル日時
        valueMap.put("ORDER_CANCEL_TIME",
                     dateUtility.format(cancelOrderHistoryDto.getCancelTime(), DateUtility.YMD_SLASH_HM)
                    );
        // お届け日
        valueMap.put("RECEIVER_DATE", cancelOrderHistoryDto.getReceiveDate());
        // お届け先名
        valueMap.put("RECEIVER_NAME", cancelOrderHistoryDto.getReceiveOfficeName());
        // お届け先郵便番号
        valueMap.put("RECEIVER_ZIP_CODE", cancelOrderHistoryDto.getReceiveZipcode());
        // お届け先住所
        valueMap.put("RECEIVER_ADDRESS", buildReceiverAddress(cancelOrderHistoryDto));
        // お支払い方法
        valueMap.put("PAYMENT_METHOD", cancelOrderHistoryDto.getPaymentType());
        // 適用クーポン番号
        valueMap.put("COUPON_CODE", cancelOrderHistoryDto.getCouponNo());
        // 適用クーポン名
        valueMap.put("COUPON_NAME", cancelOrderHistoryDto.getCouponName());

        // 合計金額
        if (cancelOrderHistoryDto.getPaymentTotalPrice() != null) {
            Format priceFormat = new DecimalFormat("#,##0");
            valueMap.put("TOTAL_PRICE",
                         priceFormat.format(new BigDecimal(cancelOrderHistoryDto.getPaymentTotalPrice()))
                        );
        }

        // 商品情報
        int counter = 1;
        for (CancelOrderHistoryGoodsDto goodsInfo : cancelOrderHistoryDto.getGoodsList()) {
            // 商品コード
            valueMap.put("GOODS_CODE" + LOOP_STR + counter,
                         goodsUtility.convertEmotionPriceGoodsCodeToNormalGoodsCode(goodsInfo.getGoodsCode())
                        );
            // 商品名
            valueMap.put("GOODS_NAME" + LOOP_STR + counter,
                         goodsUtility.convertEmotionPriceGoodsNameToNormalGoodsName(goodsInfo.getGoodsName(),
                                                                                    goodsInfo.getDiscountFlag()
                                                                                   )
                        );
            // 数量
            valueMap.put("GOODS_COUNT" + LOOP_STR + counter, goodsInfo.getGoodsCount());
            // 単位
            valueMap.put("UNIT" + LOOP_STR + counter, goodsInfo.getUnitName());
            counter++;
        }
        valueMap.put("COUNTER_TOTAL", String.valueOf(counter));

        return valueMap;
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

        if (!(arguments[0] instanceof CancelOrderHistoryDto)) {
            RuntimeException e = new IllegalArgumentException("プレースホルダ用値マップに変換できません：引数の型が合いません。");
            LOGGER.warn("引数チェックエラー", e);
            throw e;
        }
    }

    /**
     * 住所項目を連結してお届け先住所を作成
     *
     * @param cancelOrderHistoryDto 注文履歴キャンセルDtoクラス
     * @return お届け先住所
     */
    private String buildReceiverAddress(CancelOrderHistoryDto cancelOrderHistoryDto) {

        StringBuilder receiverAddress = new StringBuilder();

        if (StringUtil.isNotEmpty(cancelOrderHistoryDto.getReceiveAddress1())) {
            receiverAddress.append(cancelOrderHistoryDto.getReceiveAddress1());
        }
        if (StringUtil.isNotEmpty(cancelOrderHistoryDto.getReceiveAddress2())) {
            receiverAddress.append(cancelOrderHistoryDto.getReceiveAddress2());
        }
        if (StringUtil.isNotEmpty(cancelOrderHistoryDto.getReceiveAddress3())) {
            receiverAddress.append(cancelOrderHistoryDto.getReceiveAddress3());
        }
        if (StringUtil.isNotEmpty(cancelOrderHistoryDto.getReceiveAddress4())) {
            receiverAddress.append(cancelOrderHistoryDto.getReceiveAddress4());
        }
        if (StringUtil.isNotEmpty(cancelOrderHistoryDto.getReceiveAddress5())) {
            receiverAddress.append(cancelOrderHistoryDto.getReceiveAddress5());
        }

        return receiverAddress.toString();
    }

}
// 2023-renew No68 to here
