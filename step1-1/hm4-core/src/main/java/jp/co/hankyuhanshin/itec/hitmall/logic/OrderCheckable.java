/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

/**
 * 対照の受注情報が購入可能な情報であるかをチェックする<br/>
 *
 * @author ozaki
 */
public interface OrderCheckable {

    /**
     * マップキー：配送（内部処理固定値）
     */
    String DELIVERY = "delivety";

    /**
     * マップキー：決済（内部処理固定値）
     */
    String SETTLEMENT = "settlement";

    /**
     * 商品の利用可能配送方法、利用可能決済方法の区切り文字
     */
    String SEPARATOR = "/";

    /**
     * 商品の公開状態が削除の場合
     */
    String MSGCD_GOODS_OPNE_STATE_DELETE_ERROR = "LOX000201";

    /**
     * 商品の販売状態が削除の場合
     */
    String MSGCD_GOODS_SALE_STATE_DELETE_ERROR = "LOX000202";

    /**
     * 受注商品の購入数量＞実在庫-安全在庫の場合
     */
    String MSGCD_STOCK_SHORTAGE_GOODS = "LOX000203";

    /**
     * 実在庫-安全在庫≦０の場合
     */
    String MSGCD_NO_STOCK_GOODS = "LOX000204";

    /**
     * 受注商品の購入数量＞商品最大購入可能数
     */
    String MSGCD_PURCHSEDMAX_OVER = "LOX000205";

    /**
     * 商品の組み合わせにより選択可能な配送方法が無い場合
     */
    String MSGCD_SELECT_DELIVERYMETHOD_ZERO = "LOX000206";

    /**
     * 商品の組み合わせにより選択可能な決済方法が無い場合
     */
    String MSGCD_SELECT_SETTLEMENTMETHOD_ZERO = "LOX000207";

    /**
     * 個別配送の商品が複数存在している場合
     */
    String MSGCD_INDIVIDUALDELIVERY_ERROR = "LOX000208";

    /**
     * 個別配送の商品と通常配送の商品が混在している場合
     */
    String MSGCD_NOMAL_INDIVIDUAL_BOTH_ERROR = "LOX000209";

    /**
     * 選択した配送方法が商品の組み合わせで選択可能な配送方法ではない場合
     */
    String MSGCD_DELIVERYMETHOD_ERROR = "LOX000210";

    /**
     * お届け先の住所が配送不可能エリアに指定されている場合
     */
    String MSGCD_DELIVERY_IMPOSSIBLE_AREA = "LOX000211";

    /**
     * 配送種別＝都道府県別で、お届け先が未設定の都道府県、且つ特別料金エリアにも設定が無い場合
     */
    String MSGCD_PREFECTURE_ERROR = "LOX000212";

    /**
     * 配送種別＝金額別で受注金額が設定されている上限金額の最大値を超えている場合
     */
    String MSGCD_DELIVERY_PURCHSEDMAX_OVER = "LOX000213";

    /**
     * 選択した決済方法が商品の組み合わせで選択可能な決済方法ではない場合
     */
    String MSGCD_SETTLEMENTMETHOD_ERROR = "LOX000214";

    /**
     * 選択した決済方法が利用できる配送方法を選択していない場合
     */
    String MSGCD_SETTLEMENTMETHOD_DEF = "LOX000215";

    /**
     * 受注金額が選択した決済方法の最大購入金額を超えている場合
     */
    String MSGCD_SETTLEMENT_PURCHSEDMAX_OVER = "LOX000216";

    /**
     * 決済種別＝コンビニ決済でご注文主の電話番号が9桁～11桁の範囲外の場合
     */
    String TELEPHONE_NUMBE_LENGTH_ERROR = "LOX000217";

    /**
     * 公開中でない商品が存在する
     */
    String MSGCD_NO_OPEN_GOODS_EXIST = "LOX000218";

    /**
     * 販売中でない商品が存在する
     */
    String MSGCD_NO_SALE_GOODS_EXIST = "LOX000219";

    /**
     * 決済変更で不可の場合
     */
    String MSGCD_SETTLEMENT_CHANGE_ERROR = "LOX000220";

    /**
     * 同受注で過去に同一決済方法を選択しているため変更不可の場合
     */
    String MSGCD_POST_SETTLEMENT_ERROR = "LOX000221";

    /**
     * 同受注で金額に変更があるため決済不可の場合
     */
    String MSGCD_SETTLEMENT_CHANGE_AMOUNT_ERROR = "LOX000222";

    /**
     * 代金回収不能であるため他決済へ変更しなければならない
     */
    String MSGCD_IMPOSSIBLE_RECEIPTMONEY_ERROR = "LOX000223";

    /**
     * 受注金額が選択した決済方法の最低購入金額に達していない場合
     */
    String MSGCD_SETTLEMENT_PURCHSEDMIN_OVER = "LOX000224";

    /**
     * 選択された配送方法が削除されている場合
     */
    String MSGCD_DELIVERY_OPNE_STATE_DELETE_ERROR = "LOX000225";

    /**
     * 選択された決済方法が削除されている場合
     */
    String MSGCD_SETTLEMENT_OPNE_STATE_DELETE_ERROR = "LOX000226";

    /**
     * 選択された配送方法が公開以外の場合
     */
    String MSGCD_DELIVERY_NO_OPEN_ERROR = "LOX000227";

    /**
     * 選択された決済方法が公開以外の場合
     */
    String MSGCD_SETTLEMENT_NO_OPEN_ERROR = "LOX000228";

    /**
     * 選択されたお届け希望日が選択可能期間外の場合
     */
    String MSGCD_DELIVERY_RECEIVER_DATE_RANGE_ERROR = "LOX000229";

    /**
     * 商品情報がラッピング商品のみの場合
     */
    String MSGCD_WRAPPING_GOODS_ONLY_ERROR = "LOX000230";

    /**
     * 商品情報にラッピング商品が複数設定されている場合
     */
    String MSGCD_WRAPPING_GOODS_DUPLICATE_ERROR = "LOX000231";

    /**
     * 全額割引決済を選択しているが受注金額が0円ではない場合
     */
    String MSGCD_UNUSABLE_DISCOUNT_SETTLEMENT = "LOD000101";

    /**
     * 全額クーポン決済を選択しているが受注金額が0円ではない場合
     */
    String MSGCD_UNUSABLE_ALL_COUPON_SETTLEMENT = "LOD000102";

    /**
     * 全額ポイント決済を選択しているが受注金額が0円ではない場合
     */
    // 全額クーポンでも使われているのでは？ポイント削除のタイミングで確認すること
    String MSGCD_UNUSABLE_ALL_POINT_SETTLEMENT = "POINT-0001-004-L-E";

    /**
     * ゲストが試供品を購入する場合：PKG-3559-005-L-
     */
    String MSGCD_GUEST_BUY_SAMPLE_ERROR = "PKG-3559-005-L-";

    /**
     * 購入限度回数≦過去の購入数の場合：PKG-3559-006-L-
     */
    String MSGCD_PURCHASE_LIMIT_OVER_ERROR = "PKG-3559-006-L-";

    /**
     * 定期注文で複数配送の場合：PKG-3556-003-L-
     */
    String MSGCD_PERIODIC_MULTIPLE_ERROR = "PKG-3556-003-L-";

    /**
     * "試供品のみ購入不可"で試供品のみの場合：PKG-3559-007-L-
     */
    String MSGCD_SAMPLE_ONLY_ERROR = "PKG-3559-007-L-";

    /**
     * 複数配送未対応で複数配送をしようとした場合：PKG-3556-004-L-
     */
    String MSGCD_MOBILE_MULTIPLE_DELIVERY_ERROR = "PKG-3556-004-L-";

    /**
     * 予約商品"同時購入不可"と通常商品が混ざっている場合：PKG-3553-045-L-
     */
    String MSGCD_RESERVATION_MIXED_NORMAL_ERROR = "PKG-3553-045-L-";

    /**
     * エラーメッセージコード：未成年チェックエラー
     */
    String MSGCD_ALCOHOL_CHECK_ERROR = "PKG-4113-002-L-";

    /**
     * エラーメッセージコード：酒類商品購入時生年月日未入力エラー
     */
    String MSGCD_ALCOHOL_BIRTHDAY_NO_INPUT_ERROR = "PKG-4113-005-L-";

    /**
     * Entity欠損の不正操作
     */
    String MSGCD_ENTITY_ILLEGAL_OPERATION = "ORDER-CHECK-DELIVERY-001";

    /**
     * 初期処理<br/>
     * 請求金額を計算します。<br/>
     *
     * @param receiveOrderDto 受注DTO
     * @return 注文メッセージDTO
     */
    OrderMessageDto execute(ReceiveOrderDto receiveOrderDto,
                            String memberInfoId,
                            HTypeSiteType siteType,
                            Integer memberInfoSeq,
                            String adminId);

}
