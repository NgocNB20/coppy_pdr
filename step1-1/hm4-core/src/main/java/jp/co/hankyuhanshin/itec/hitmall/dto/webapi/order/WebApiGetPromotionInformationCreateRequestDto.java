/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携リクエストDTOクラス
 * プロモーション リクエスト作成用
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetPromotionInformationCreateRequestDto implements Serializable {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 桁埋め用:0 */
    private static final String ZERO = "0";

    /** 桁埋め用:" "(スペース) */
    private static final String SPACE = " ";

    /** プロモ連携 最大処理数:99 */
    public static final int LIST_MAX_COUNT = 99;

    /** 予約フラグ:0(取りおき以外) */
    public static final String RESERVATION_FLAG_OFF = "0";

    /** 予約フラグ:1(取りおき) */
    public static final String RESERVATION_FLAG_ON = "1";

    // 桁数
    /** 桁数:すべて */
    // 2023-renew No14 from here
    private static final int ALL_DIGITS = 162;
    // 2023-renew No14 to here

    /** 桁数:受注番号 */
    private static final int ORDERNO_DIGITS = 7;

    /** 桁数:顧客番号 */
    private static final int CUSTOMERNO_DIGITS = 9;

    /** 桁数:管理商品コード */
    private static final int GOODSMANAGEMENTCODE_DIGITS = 16;

    /** 桁数:申込商品コード */
    private static final int GOODSCODE_DIGITS = 12;

    /** 桁数:商品分類コード */
    private static final int GOODSDIVISIONCODE_DIGITS = 4;

    /** 桁数:カテゴリー */
    private static final int CATEGORY_DIGITS = 4;

    /** 桁数:セールコード */
    private static final int SALECODE_DIGITS = 8;

    /** 桁数:予約フラグ */
    private static final int RESERVATIONFLAG_DIGITS = 1;

    /** 桁数:合計金額 */
    private static final int TOTALGOODSPRICE_DIGITS = 13;

    /** 桁数:数量 */
    private static final int GOODSCOUNT_DIGITS = 3;

    /** 桁数:合計金額 */
    private static final int GOODSPRICE_DIGITS = 13;

    /** 桁数:倉庫コード(固定値:01) */
    private static final int WAREHOUSECODE_DIGITS = 2;

    /** 桁数:出荷予定日 */
    private static final int SHIPPINGDATE_DIGITS = 8;

    // 2023-renew No14 from here
    /** 桁数:配達指定日 */
    private static final int DELIVERYDESIGNATEDDAY_DIGITS = 8;
    // 2023-renew No14 to here

    /** 桁数:受注連番 */
    private static final int ORDERSEQ_DIGITS = 3;

    /** 桁数:クーポンコード */
    private static final int COUPONCODE_DIGITS = 10;

    /** 桁数:適用割引 */
    private static final int SALETYPE_DIGITS = 1;

    /** 桁数:お届け先顧客番号 */
    private static final int RECEIVECUSTOMERNO_DIGITS = 9;

    /** 桁数:入荷予定日 */
    private static final int STOCKDATE_DIGITS = 8;

    // 固定値
    /** 固定値：受付方法 */
    private static final String RECEPTIONTYPENAME_FIXED = "05";

    /** 固定値：広告媒体コード */
    private static final String ADVERTISING_FIXED = "00000001";

    /** 固定値：伝票種別 */
    private static final String DOCUMENTTYPE_FIXED = "0";

    /** 固定値：定期回数 */
    private static final String PERIODICALTIMES_FIXED = "0000";

    /** 固定値：倉庫コード */
    private static final String WAREHOUSECODE_FIXED = "01";

    /** 固定値：出荷予定日(99991231) */
    public static final String SHIPPINGDATE_FIXED = "99991231";

    // 2023-renew No14 from here
    /** 固定値：配達指定日(99991231) */
    public static final String DELIVERYDESIGNATEDDAY_FIXED = "99991231";
    // 2023-renew No14 to here

    /** 固定値：入荷予定日(99991231) */
    public static final String STOCKDATE_FIXED = "99991231";

    // 詳細内容

    /** 2:受注番号 */
    private String orderNo;

    /** 3:顧客番号 */
    private String customerNo;

    /** 4:受付方法(固定値:05) */
    private String receptionTypeName = RECEPTIONTYPENAME_FIXED;

    /** 5:広告媒体コード(固定:00000001) */
    private String advertising = ADVERTISING_FIXED;

    /** 6:管理商品コード */
    private String goodsManagementCode;

    /** 7:申込商品コード */
    private String goodsCode;

    /** 8:商品分類コード */
    private String goodsDivisionCode;

    /** 9:カテゴリー1 */
    private String category1;

    /** 10:カテゴリー2 */
    private String category2;

    /** 11:カテゴリー3 */
    private String category3;

    /** 12:セールコード */
    private String saleCode;

    /** 13:予約フラグ */
    private String reservationFlag;

    /** 14:伝票種別(固定値:0) */
    private String documentType = DOCUMENTTYPE_FIXED;

    /** 15:定期回数(固定値:0000) */
    private String periodicalTimes = PERIODICALTIMES_FIXED;

    /** 16:合計金額 */
    private String totalGoodsPrice;

    /** 17:数量 */
    private String goodsCount;

    /** 18:合計金額 */
    private String goodsPrice;

    /** 19:倉庫コード(固定値:01) */
    private String warehouseCode = WAREHOUSECODE_FIXED;

    /** 20:出荷予定日 */
    private String shippingDate;

    // 2023-renew No14 from here
    /** 21:配達指定日 */
    private String deliveryDesignatedDay;
    // 2023-renew No14 to here

    /** 22:受注連番 */
    private String orderSeq;

    /** 23:クーポンコード */
    private String couponCode;

    /** 24:適用割引 */
    private String saleType;

    /** 25:お届け先顧客番号 */
    private String receiveCustomerNo;

    /** 26:入荷予定日 */
    private String stockDate;

    /**
     * プロモーション リクエスト用データを作成
     *
     * @return リクエスト用データ
     */
    public String createGetPromotionInformationRequestDetailInfo() {

        StringBuilder str = new StringBuilder();

        str.append(orderNo); // 受注番号
        str.append(customerNo); // 顧客番号
        str.append(receptionTypeName); // 受付方法
        str.append(advertising); // 広告媒体コード
        str.append(goodsManagementCode); // 管理商品コード
        str.append(goodsCode); // 申込商品コード
        str.append(goodsDivisionCode); // 商品分類コード
        str.append(category1); // カテゴリー1
        str.append(category2); // カテゴリー2
        str.append(category3); // カテゴリー3
        str.append(saleCode); // セールコード
        str.append(reservationFlag); // 予約フラグ
        str.append(documentType); // 伝票種別
        str.append(periodicalTimes); // 定期回数
        str.append(totalGoodsPrice); // 合計金額
        str.append(goodsCount); // 数量
        str.append(goodsPrice); // 金額
        str.append(warehouseCode); // 倉庫コード
        str.append(shippingDate); // 出荷予定日
        // 2023-renew No14 from here
        str.append(deliveryDesignatedDay); // 配達指定日
        // 2023-renew No14 to here
        str.append(orderSeq); // 受注連番
        str.append(couponCode); // クーポンコード
        str.append(saleType); // 適用割引
        str.append(receiveCustomerNo); // お届け先顧客番号
        str.append(stockDate); // 入荷予定日

        if (str.length() != ALL_DIGITS) {
            // 桁数不足 又は オーバー
            return null;
        }

        return str.toString();
    }

    /**
     * 2:受注番号 設定
     *
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = StringUtils.leftPad(StringUtils.defaultString(orderNo), ORDERNO_DIGITS, ZERO);
    }

    /**
     * 3:顧客番号 設定
     *
     * @param customerNo the customerNo to set
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = StringUtils.leftPad(StringUtils.defaultString(customerNo), CUSTOMERNO_DIGITS, ZERO);
    }

    /**
     * 6:管理商品コード 設定
     *
     * @param goodsManagementCode the goodsManagementCode to set
     */
    public void setGoodsManagementCode(String goodsManagementCode) {
        this.goodsManagementCode =
                        StringUtils.rightPad(StringUtils.defaultString(goodsManagementCode), GOODSMANAGEMENTCODE_DIGITS,
                                             SPACE
                                            );
    }

    /**
     * 7:申込商品コード 設定
     *
     * @param goodsCode the goodsCode to set
     */
    public void setGoodsCode(String goodsCode) {
        this.goodsCode = StringUtils.rightPad(StringUtils.defaultString(goodsCode), GOODSCODE_DIGITS, SPACE);
    }

    /**
     * 8:商品分類コード 設定
     *
     * @param goodsDivisionCode the goodsDivisionCode to set
     */
    public void setGoodsDivisionCode(String goodsDivisionCode) {
        this.goodsDivisionCode =
                        StringUtils.leftPad(StringUtils.defaultString(goodsDivisionCode), GOODSDIVISIONCODE_DIGITS,
                                            ZERO
                                           );
    }

    /**
     * 9:カテゴリー1 設定
     *
     * @param category1 the category1 to set
     */
    public void setCategory1(String category1) {
        this.category1 = StringUtils.leftPad(StringUtils.defaultString(category1), CATEGORY_DIGITS, ZERO);
    }

    /**
     * 10:カテゴリー2 設定
     *
     * @param category2 the category2 to set
     */
    public void setCategory2(String category2) {
        this.category2 = StringUtils.leftPad(StringUtils.defaultString(category2), CATEGORY_DIGITS, ZERO);
    }

    /**
     * 11:カテゴリー3 設定
     *
     * @param category3 the category3 to set
     */
    public void setCategory3(String category3) {
        this.category3 = StringUtils.leftPad(StringUtils.defaultString(category3), CATEGORY_DIGITS, ZERO);
    }

    /**
     * 12:セールコード 設定
     *
     * @param saleCode the saleCode to set
     */
    public void setSaleCode(String saleCode) {
        this.saleCode = StringUtils.rightPad(StringUtils.defaultString(saleCode), SALECODE_DIGITS, SPACE);
    }

    /**
     * 13:予約フラグ 設定
     *
     * @param reservationFlag the reservationFlag to set
     */
    public void setReservationFlag(String reservationFlag) {
        this.reservationFlag =
                        StringUtils.leftPad(StringUtils.defaultString(reservationFlag), RESERVATIONFLAG_DIGITS, ZERO);
    }

    /**
     * 16:合計金額 設定
     *
     * @param totalGoodsPrice the totalGoodsPrice to set
     */
    public void setTotalGoodsPrice(String totalGoodsPrice) {
        this.totalGoodsPrice =
                        StringUtils.leftPad(StringUtils.defaultString(totalGoodsPrice), TOTALGOODSPRICE_DIGITS, ZERO);
    }

    /**
     * 17:数量 設定
     *
     * @param goodsCount the goodsCount to set
     */
    public void setGoodsCount(String goodsCount) {
        this.goodsCount = StringUtils.leftPad(StringUtils.defaultString(goodsCount), GOODSCOUNT_DIGITS, ZERO);
    }

    /**
     * 18:金額 設定
     *
     * @param goodsPrice the goodsPrice to set
     */
    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = StringUtils.leftPad(StringUtils.defaultString(goodsPrice), GOODSPRICE_DIGITS, ZERO);
    }

    /**
     * 20:出荷予定日 設定
     *
     * @param shippingDate the shippingDate to set
     */
    public void setShippingDate(String shippingDate) {

        if (StringUtils.isEmpty(shippingDate)) {
            // 空の場合は固定値
            this.shippingDate = SHIPPINGDATE_FIXED;
        } else {
            this.shippingDate = StringUtils.leftPad(shippingDate, SHIPPINGDATE_DIGITS, ZERO);
        }
    }

    // 2023-renew No14 from here

    /**
     * 21:配達指定日 設定
     *
     * @param deliveryDesignatedDay the deliveryDesignatedDay to set
     */
    public void setDeliveryDesignatedDay(String deliveryDesignatedDay) {

        if (StringUtils.isEmpty(deliveryDesignatedDay)) {
            // 空の場合は固定値
            this.deliveryDesignatedDay = DELIVERYDESIGNATEDDAY_FIXED;
        } else {
            this.deliveryDesignatedDay = StringUtils.leftPad(deliveryDesignatedDay, DELIVERYDESIGNATEDDAY_DIGITS, ZERO);
        }
    }

    // 2023-renew No14 to here

    /**
     * 22:受注連番 設定
     *
     * @param orderSeq the orderSeq to set
     */
    public void setOrderSeq(String orderSeq) {
        this.orderSeq = StringUtils.leftPad(StringUtils.defaultString(orderSeq), ORDERSEQ_DIGITS, ZERO);
    }

    /**
     * 22:受注連番 取得
     *
     * @return orderSeq
     */
    public String getOrderSeq() {
        return this.orderSeq;
    }

    /**
     * 23:クーポンコード 設定
     *
     * @param couponCode the couponCode to set
     */
    public void setCouponCode(String couponCode) {
        this.couponCode = StringUtils.rightPad(StringUtils.defaultString(couponCode), COUPONCODE_DIGITS, SPACE);
    }

    /**
     * 24:適用割引 設定
     *
     * @param saleType the saleType to set
     */
    public void setSaleType(String saleType) {
        this.saleType = StringUtils.rightPad(StringUtils.defaultString(saleType), SALETYPE_DIGITS, SPACE);
    }

    /**
     * 25:お届け先顧客番号 設定
     *
     * @param receiveCustomerNo the receiveCustomerNo to set
     */
    public void setReceiveCustomerNo(String receiveCustomerNo) {
        if (SPACE.equals(receiveCustomerNo)) {
            // 新しいお届け先の場合（新規届け先の場合、呼出元で固定値スペースを設定している為）
            this.receiveCustomerNo =
                            StringUtils.leftPad(StringUtils.defaultString(receiveCustomerNo), RECEIVECUSTOMERNO_DIGITS,
                                                SPACE
                                               );
        } else {
            this.receiveCustomerNo =
                            StringUtils.leftPad(StringUtils.defaultString(receiveCustomerNo), RECEIVECUSTOMERNO_DIGITS,
                                                ZERO
                                               );
        }
    }

    /**
     * 26:入荷予定日 設定
     *
     * @param stockDate the stockDate to set
     */
    public void setStockDate(String stockDate) {

        if (StringUtils.isEmpty(stockDate)) {
            // 空の場合は固定値
            this.stockDate = STOCKDATE_FIXED;
        } else {
            this.stockDate = StringUtils.leftPad(stockDate, STOCKDATE_DIGITS, ZERO);
        }
    }

    // PDR Migrate Customization to here

}
