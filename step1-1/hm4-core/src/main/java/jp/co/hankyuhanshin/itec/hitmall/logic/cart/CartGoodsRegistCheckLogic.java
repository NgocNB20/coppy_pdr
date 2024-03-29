/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsForTakeOverDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsRegistCheckDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * カート投入チェック<br/>
 * カートに投入されている商品内容をチェックし、カート商品チェックメッセージマップを返す<br/>
 *
 * @author ozaki
 * @author Nishigaki Mio (Itec) 2011/09/01 チケット #2692 対応
 * @author sugita(itec) 2012/01/17 チケット #2777 対応
 */
public interface CartGoodsRegistCheckLogic {

    /** システムプロパティのキー（カート最大商品数） */
    public static final String SYS_KEY_MAX_CART_GOODS_COUNT = "max.cart.goods.count";

    /** システムプロパティのキー（同一商品カートマージフラグ） */
    public static final String CART_GOODS_MERGE = "cartgoods.merge";

    /** カート内最大商品数量超過チェックメッセージ */
    public static final String MSGCD_CART_GOODS_CNT_MAX_OVER = "LCC000620";

    /** 公開状態チェックメッセージ<非公開> */
    public static final String MSGCD_OPEN_STATUS_HIKOUKAI = "LCC000705";

    /** 公開期間チェックメッセージ<公開前> */
    public static final String MSGCD_OPEN_BEFORE = "LCC000707";

    /** 公開期間チェックメッセージ<公開終了> */
    public static final String MSGCD_OPEN_END = "LCC000708";

    /** 販売状態チェックメッセージ<非販売> */
    public static final String MSGCD_SALE_STATUS_HIHANBAI = "LCC000709";

    /** 販売期間チェックメッセージ<販売前> */
    public static final String MSGCD_SALE_BEFORE = "LCC000711";

    /** 販売期間チェックメッセージ<販売終了> */
    public static final String MSGCD_SALE_END = "LCC000712";

    /** カート内最大商品件数チェックメッセージ */
    public static final String MSGCD_CART_GOODS_MAX_OVER = "LCC000715";

    /** 商品詳細情報取得失敗 */
    public static final String MSGCD_GOODSDETAILSDTO_NULL = "LCC000716";

    // PDR Migrate Customization from here
    /** カート内最大商品数超過 */
    public static final String MSGCD_CART_MAX_ITEMS_OVER = "BTB-3293-001-L-";
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
     * カート投入チェック
     * カートに投入されている商品内容をチェックし、カート商品チェックメッセージマップを返す
     *
     * @param goodsDetailsDto         商品詳細DTO
     * @param count                   数量
     * @param shopSeq                 ショップSEQ
     * @param memberInfoSeq           会員SEQ
     * @param accessUid               端末識別情報
     * @param siteType                サイト区分
     * @param cartGoodsRegistCheckDto カート投入チェックDto
     * @return エラーリスト
     */
    List<CheckMessageDto> execute(GoodsDetailsDto goodsDetailsDto,
                                  BigDecimal count,
                                  Integer shopSeq,
                                  Integer memberInfoSeq,
                                  String accessUid,
                                  HTypeSiteType siteType,
                                  // 2023-renew No14 from here
                                  CartGoodsRegistCheckDto cartGoodsRegistCheckDto
                                  // 2023-renew No14 to here
                                 );

    // PDR Migrate Customization from here

    /**
     * 一括注文用のカート投入チェック（簡易版）
     * <pre>
     * カートマージなしの場合は以下の場合エラー
     *   ⇒ "カート商品数" ＋ "一括登録商品数" > カート内最大商品数
     *
     * カートマージありの場合は以下の場合エラー
     *   ⇒ カートマージ後の件数("カート商品" ＋ "一括登録商品") > カート内最大商品数
     * </pre>
     *
     * @param registCartGoodsList カート一括登録用の商品情報
     * @param conditionDto        カート一括登録用の商品情報
     */
    void execute(List<CartGoodsForTakeOverDto> registCartGoodsList, CartGoodsForDaoConditionDto conditionDto);

    // PDR Migrate Customization to here

}
