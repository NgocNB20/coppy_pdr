// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * PDR#013 09_データ連携（受注データ）<br/>
 *
 * <pre>
 * 受注連携ロジック
 * </pre>
 *
 * @author satoh
 */
public interface AddOrderInformationLogic {
    // 固定値
    /** 入力担当者 (990：インターネット受注) */
    public static final String INPUT_USER_ID_FIXED_VALUE = "990";

    /** 確定担当者 (990：インターネット受注) */
    public static final String COMFIRM_USER_ID_FIXED_VALUE = "990";

    /** 受付方法 (5：インターネット) */
    public static final String ORDER_TYPE_FIXED_VALUE = "5";

    /** 広告媒体コード：00000001 */
    public static final String MEDIA_CODE_FIXED_VALUE = "00000001";

    /** 倉庫:1 */
    public static final String STOCKROOM_CODE_FIXED_VALUE = "1";

    /** 使用ポイント:0 */
    public static final String USE_POINT_FIXED_VALUE = "0";

    /** クレジットカード会社：51 */
    public static final String CREDIT_COMPANY_CODE_FIXED_VALUE = "51";

    /** クレジットカード番号:9999999999999999 */
    public static final String CREDIT_CARD_NO_FIXED_VALUE = "9999999999999999";

    /** クレジット有効期限:9999 */
    public static final String CREDIT_EXPIRATION_DATE_FIXED_VALUE = "9999";

    /** クレジット支払回数:1 */
    public static final String CREDIT_SPLIT_NUMBER_FIXED_VALUE = "1";

    /** 請求書:1 */
    public static final String REQUISITION_TYPE_FIXED_VALUE = "1";

    /** ポイント値引:0 */
    public static final BigDecimal POINT_DISCOUNT_PRICE_FIXED_VALUE = BigDecimal.ZERO;

    /** プロモーション値引:0 */
    public static final BigDecimal PROMOTION_DISCOUNT_PRICE_FIXED_VALUE = BigDecimal.ZERO;

    /** 値引:0 */
    public static final BigDecimal DISCOUNT_PRICE_FIXED_VALUE = BigDecimal.ZERO;

    /** 同梱商品フラグ:OFF(0) */
    public static final String BUNDLE_FLAG_OFF = "0";

    /** 同梱商品フラグ:ON(1) */
    public static final String BUNDLE_FLAG_ON = "1";

    /**
     * WEB-API連携 受注連携を行います。
     *
     * @param receiveOrderDtoList 受注DTOリスト
     */
    void execute(List<ReceiveOrderDto> receiveOrderDtoList);
}
// PDR Migrate Customization to here
