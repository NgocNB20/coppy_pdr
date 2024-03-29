// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsRegistCheckDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

/**
 * PDR#004 01_販売可能商品の制御<br/>
 *
 * <pre>
 * カート商品追加
 * </pre>
 *
 * @author satoh
 */

public interface CartGoodsAddLogic {

    /**
     * カートに投入できない商品がある場合エラー
     * <code>MSGCD_CART_ADD_GOODS_ERR</code>
     */
    public static final String MSGCD_CART_ADD_GOODS_ERR = "PDR-0008-004-A-";
    /** ロガー */
    public static final Logger log = LoggerFactory.getLogger(CartGoodsAddLogic.class);

    /**
     * カートに商品を追加します。
     * ※通常のカートIN専用（セールde予約には非対応）
     *
     * <pre>
     * 大まかな流れはCartGoodsAddServiceのソースを流用
     * 投入可能商品チェックでエラーと判定された場合でも
     * カートに商品の追加を行います。
     * </pre>
     *
     * @param goodsCode     商品コード
     * @param count         数量
     * @param saleType      適用割引区分
     * @param accessUid     端末識別情報
     * @param memberInfoSeq 会員SEQ
     * @param siteType      サイト区分
     * @param cartGoodsRegistCheckDto カート投入チェックDto
     * @return エラー商品リスト
     */
    List<CheckMessageDto> execute(String goodsCode,
                                  BigDecimal count,
                                  String saleType,
                                  String accessUid,
                                  Integer memberInfoSeq,
                                  HTypeSiteType siteType,
                                  // 2023-renew No14 from here
                                  CartGoodsRegistCheckDto cartGoodsRegistCheckDto
                                  // 2023-renew No14 to here
                                 );
}
// PDR Migrate Customization to here
