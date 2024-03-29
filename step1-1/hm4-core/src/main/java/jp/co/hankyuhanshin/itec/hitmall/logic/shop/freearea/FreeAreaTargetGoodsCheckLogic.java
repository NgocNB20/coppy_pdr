/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea;

/**
 * フリーエリア対象商品チェック
 *
 * @author h_hakogi
 */
public interface FreeAreaTargetGoodsCheckLogic {

    /**
     * 商品番号重複エラー<br/>
     */
    public static final String MSGCD_DUPLICATION_TARGET_GOODS = "PKG-3558-001-L-";

    /**
     * 商品番号最大件数オーバー<br/>
     */
    public static final String MSGCD_MAX_TARGET_GOODS_OVER = "PKG-3558-002-L-";

    /**
     * 商品が存在しない場合エラー<br/>
     */
    public static final String MSGCD_NOT_EXIST_TARGET_GOODS = "PKG-3558-003-L-";

    /**
     * 指定可能外の商品種別エラー<br/>
     */
    public static final String MSGCD_GOODS_TYPE_ERROR = "PKG-3558-004-L-";

    /**
     * 個別配送商品エラー<br/>
     */
    public static final String MSGCD_INDIVIDUAL_DELIVERY_ERROR = "PKG-3558-005-L-";

    /**
     * 会員ランク限定商品エラー<br/>
     */
    public static final String MSGCD_MEMBER_RANK_ERROR = "PKG-3558-006-L-";

    /**
     * 非公開商品エラー<br/>
     */
    public static final String MSGCD_NOOPEN_GOODS_ERROR = "PKG-3558-007-L-";

    /**
     * 予約商品と通常商品の同時購入不可エラー<br/>
     */
    public static final String MSGCD_SIMULTANEOUS_PURCHASE_ERROR = "PKG-3558-009-L-";

    /**
     * 利用可能な配送方法なし<br/>
     */
    public static final String MSGCD_NO_DELIVERY_ERROR = "PKG-3558-010-L-";

    /**
     * 利用可能な決済方法なし<br/>
     */
    public static final String MSGCD_NO_SETTLEMENT_ERROR = "PKG-3558-011-L-";

    /**
     * フリーエリア対象商品チェック
     *
     * @param targetGoods 対象商品
     */
    void execute(String targetGoods);

}
