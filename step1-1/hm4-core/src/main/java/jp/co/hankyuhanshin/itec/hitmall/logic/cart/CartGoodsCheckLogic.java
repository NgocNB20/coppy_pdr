/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;

import java.util.List;
import java.util.Map;

/**
 * カート商品チェック<br/>
 * カートに投入されている商品内容をチェックし、カート商品チェックメッセージマップを返す<br/>
 *
 * @author ozaki
 * @version $Revision: 1.3 $
 */
public interface CartGoodsCheckLogic {

    /** システムプロパティキー：カート投入可能商品件数 */
    public static final String SYS_KEY_MAX_CART_GOODS_COUNT = "max.cart.goods.count";

    // PDR Migrate Customization from here
    /** システムプロパティキー：カート投入可能商品価格 */
    public static final String SYS_KEY_MAX_CART_GOODS_PRICE = "max.cart.goods.price";
    // PDR Migrate Customization to here

    /** 公開状態チェックメッセージ<非公開> */
    public static final String MSGCD_OPEN_STATUS_HIKOUKAI = "LCC000602";

    /** 公開期間チェックメッセージ<公開前> */
    public static final String MSGCD_OPEN_BEFORE = "LCC000604";

    /** 公開期間チェックメッセージ<公開終了> */
    public static final String MSGCD_OPEN_END = "LCC000605";

    /** 販売状態チェックメッセージ<非販売> */
    public static final String MSGCD_SALE_STATUS_HIHANBAI = "LCC000606";

    /** 販売期間チェックメッセージ<販売前> */
    public static final String MSGCD_SALE_BEFORE = "LCC000608";

    /** 販売期間チェックメッセージ<販売終了> */
    public static final String MSGCD_SALE_END = "LCC000609";

    /** 在庫切れチェックメッセージ<在庫切れ> */
    public static final String MSGCD_NO_STOCK = "LCC000610";

    /** 在庫不足チェックメッセージ<在庫不足> */
    public static final String MSGCD_LESS_STOCK = "LCC000611";

    /** 最大購入可能数超過チェックメッセージ */
    public static final String MSGCD_PURCHASED_MAX_OVER = "LCC000612";

    /** カート内最大商品件数チェックメッセージ */
    public static final String MSGCD_CART_GOODS_MAX_OVER = "LCC000615";

    // PDR Migrate Customization from here
    /** 商品合計金額超過チェックメッセージ */
    public static final String MSGCD_CART_TOTAL_PRICE_MAX_OVER = "LCC000616";
    // PDR Migrate Customization to here

    /** 個別配送商品あり */
    public static final String MSGCD_INDIVIDUAL_DELIVERY = "LCC000617";

    /** 酒類フラグエラー */
    public static final String MSGCD_ALCOHOL_CHECK_ERROR = "PKG-4113-001-L-";

    // PDR Migrate Customization from here
    /** 購入制限チェックメッセージ<注文数量制限> */
    public static final String MSGCD_NO_POSSIBLE = "LCC000621";
    // PDR Migrate Customization to here

    // 2023-renew No2 from here
    /** 販売可否判定．販売可否判定結果 = 「0:販売不可」 */
    public static final String MSGCD_ERROR_SALE_CHECK_NO = "PDR-2023RENEW-2-001-";
    // 2023-renew No2 to here

    // 2023-renew No14 from here
    /** セールde予約不可 */
    public static final String MSGCD_NOT_RESERVE = "PDR-2023RENEW-14-001-";
    // 2023-renew No14 to here

    /**
     * カート商品チェック<br/>
     * カートに投入されている商品内容をチェックし、カート商品チェックメッセージマップを返す<br/>
     *
     * @param cartDto          カート情報
     * @param siteType         サイト区分
     * @param isLogin          会員ログインしているか否かの判定
     * @param businessType     顧客区分
     * @param confDocumentType 確認書類区分
     * @return エラー情報MAP
     */
    Map<Integer, List<CheckMessageDto>> execute(CartDto cartDto,
                                                HTypeSiteType siteType,
                                                Boolean isLogin,
                                                String businessType,
                                                String confDocumentType,
                                                Integer memberInfoSeq);

}
