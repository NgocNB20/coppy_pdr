// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

import java.util.List;

/**
 * #013 09_データ連携（受注データ）<br/>
 *
 * <pre>
 * プロモーション連携ロジッククラス
 * </pre>
 *
 * @author satoh
 */

public interface OrderPromotionInformationLogic {

    /**
     * プロモーション連携の最大連携明細が99を超えた場合 エラーメッセージ
     * <code>MSGCD_DETAIL_MAX</code>
     */
    public static final String MSGCD_DETAIL_MAX = "PDR-0014-001-A-";

    /**
     * 値引きあり メッセージ
     * <code>MSGCD_DISCOUNT_ON</code>
     */
    public static final String MSGCD_DISCOUNT_ON = "PDR-0003-005-A-";

    /**
     * プロモーションのみ適用（値引あり） メッセージ
     * <code>MSGCD_PROMOTION_DISCOUNT</code>
     */
    public static final String MSGCD_PROMOTION_DISCOUNT = "PDR-0436-005-A-";

    // 2023-renew No24 from here
    /**
     * プロモーションのみ適用（値引なし） メッセージ
     * <code>MSGCD_PROMOTION_NOT_DISCOUNT</code>
     */
    public static final String MSGCD_PROMOTION_NOT_DISCOUNT = "PDR-2023RENEW-24-003-";
    // 2023-renew No24 to here

    /**
     * プロモーションのみ適用（クーポン未適用） メッセージ
     * <code>MSGCD_ONLY_PROMOTION_NOT_COUPON</code>
     */
    public static final String MSGCD_ONLY_PROMOTION_NOT_COUPON = "PDR-0436-011-A-";

    /**
     * プロモーションのみ適用（クーポン不可） メッセージ
     * <code>MSGCD_PROMOTION_OUT_OF_SERVICE_COUPON</code>
     */
    public static final String MSGCD_PROMOTION_OUT_OF_SERVICE_COUPON = "PDR-0436-009-A-";

    /**
     * クーポン未適用 メッセージ
     * <code>MSGCD_NOT_COUPON_DISCOUNT</code>
     */
    public static final String MSGCD_NOT_COUPON_DISCOUNT = "PDR-0436-006-A-";

    /**
     * クーポン不可 メッセージ
     * <code>MSGCD_OUT_OF_SERVICE_COUPON</code>
     */
    public static final String MSGCD_OUT_OF_SERVICE_COUPON = "PDR-0436-007-A-";

    /**
     * クーポン・プロモーションどちらも適用 メッセージ
     * <code>MSGCD_PROMOTION_AND_COUPON</code>
     */
    public static final String MSGCD_PROMOTION_AND_COUPON = "PDR-0436-008-A-";

    /**
     * クーポン・プロモーションどちらも適用 メッセージ（プレゼント）
     * <code>MSGCD_PROMOTION_AND_COUPON_FOR_PRESENT</code>
     */
    public static final String MSGCD_PROMOTION_AND_COUPON_FOR_PRESENT = "PDR-0436-014-A-";

    // 2023-renew No24 from here
    /**
     * API処理結果メッセージをそのまま返却 メッセージ
     * <code>MSGCD_PROMOTION_API_MESSAGE</code>
     */
    public static final String MSGCD_PROMOTION_API_MESSAGE = "PDR-2023RENEW-24-001-";
    // 2023-renew No24 to here

    /** 同梱商品がECDBに存在しない商品の場合に使用する商品コード */
    public static final String DUMMY_GOODS_CODE = "dummy.goods.code";

    /** 追加方法：お届け先情報入力から追加 */
    public static final String ADD_TYPE_RECEIVER = "1";

    /** 桁埋め用:" "(スペース) */
    public static final String SPACE = " ";

    /**
     * プロモーション連携を行い
     * 受注DTOリストに反映を行います。
     *
     * @param receiveOrderDtoList 受注DTOリスト
     * @param checkMessageDtoList エラーメッセージ用List
     */
    void execute(List<ReceiveOrderDto> receiveOrderDtoList, List<CheckMessageDto> checkMessageDtoList);
}
// PDR Migrate Customization to here
