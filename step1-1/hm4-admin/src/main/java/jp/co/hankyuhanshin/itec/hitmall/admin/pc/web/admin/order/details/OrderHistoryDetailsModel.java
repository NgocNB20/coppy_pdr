/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCarrierType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalesFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 処理履歴詳細Page
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderHistoryDetailsModel extends AbstractOrderDetailsModel {

    /**
     * 検索条件
     */
    private OrderSearchConditionDto conditionDto;

    /**
     * チェックメッセージ
     */
    private String checkErrorMessage;

    /**
     * 受注履歴連番
     */
    private Integer orderVersionNo;

    // //////// 受注インデックス
    /**
     * 処理日時
     */
    private Timestamp processTime;

    // //////// 受注サマリー
    /**
     * 売上日時
     */
    private Timestamp salesTime;

    /**
     * 売上フラグ（必須）
     */
    private HTypeSalesFlag salesFlag = HTypeSalesFlag.OFF;

    /**
     * キャリア種別（必須）
     */
    private HTypeCarrierType carrierType;

    /**
     * コンストラクタ
     */
    public OrderHistoryDetailsModel() {
        // 変更箇所特定オブジェクト名 設定
        diffObjectNameOrderGoods = ApplicationContextUtility.getBean(OrderGoodsEntity.class).getClass().getSimpleName();
    }

    /**
     * 受注サマリ（OrderSummaryEntity）の変更箇所リスト
     */
    private List<String> modifiedOrderSummaryList;

    /**
     * 受注インデックス（OrderIndexEntity）の変更箇所リスト
     */
    private List<String> modifiedOrderIndexList;

    /**
     * 受注お客様（OrderPersonEntity）の変更箇所リスト
     */
    private List<String> modifiedOrderPersonList;

    /**
     * 受注商品（OrderGoodsEntity）の変更箇所リスト
     */
    private List<List<String>> modifiedOrderGoodsList;

    /**
     * 受注配送（OrderdeliveryEntity）の変更箇所リスト
     */
    private List<String> modifiedOrderDeliveryList;

    /**
     * 受注配送（DeliveryMethodEntity）の変更箇所リスト
     */
    private List<String> modifiedDeliveryMethod;

    /**
     * 受注決済（OrderSettlementEntity）の変更箇所リスト
     */
    private List<String> modifiedOrderSettlementList;

    /**
     * 受注決済詳細（SettlementMethodEntity）の変更箇所リスト
     */
    private List<String> modifiedSettlementMethodList;

    /**
     * 受注入金（OrderReceiptOfMoneyEntity）の変更箇所リスト
     */
    private List<String> modifiedReceiptMoneyList;

    /**
     * 受注請求（OrderBillEntity）の変更箇所リスト
     */
    private List<String> modifiedOrderBillList;

    /**
     * マルチペイメント請求（MulPayBillEntity）の変更箇所リスト
     */
    private List<String> modifiedMulPayBillList;

    /**
     * 受注追加料金（OrderAdditionalChargeEntity）の変更箇所リスト
     */
    private List<List<String>> modifiedAdditionalChargeList;

    /**
     * 受注メモ（OrderMemoEntity）の変更箇所リスト
     */
    private List<String> modifiedMemoList;

    /**
     * 差分スタイルクラス
     */
    private String DIFF_STYLE_CLASS = "diff";

    /**
     * デフォルトスタイルクラス
     */
    private String DEFAULT_STYLE_CLASS = "";

    /**
     * ダイナミックプロパティ<br/>
     * 商品数量が変更されてたら修正用のスタイルシート名を返します。<br/>
     * 上記項目はEntityには無く、ユーティリティで判定する
     *
     * @return スタイルシート名
     */
    public String getDiffGoodsCountTotalStyleClass() {
        OrderUtility orderUtility = ApplicationContextUtility.getBean(OrderUtility.class);

        if (originalReceiveOrder != null && modifiedReceiveOrder != null) {
            BigDecimal originalGoodsCountTotal = orderUtility.getGoodsCountTotal(originalReceiveOrder);
            BigDecimal modifyGoodsCountTotal = orderUtility.getGoodsCountTotal(modifiedReceiveOrder);
            if (originalGoodsCountTotal.compareTo(modifyGoodsCountTotal) != 0) {
                return DIFF_STYLE_CLASS;
            }
        }
        return DEFAULT_STYLE_CLASS;
    }

    /**
     * 受注商品
     */
    public final String diffObjectNameOrderGoods;

    /**
     * 受注DTO（修正前）
     */
    private ReceiveOrderDto originalReceiveOrder;

    /**
     * 受注DTO（修正後）
     */
    private ReceiveOrderDto modifiedReceiveOrder;

    /**
     * modifiedReceiveOrder に処理履歴詳細画面で取得した受注情報が入っている場合は true
     *
     * <pre>
     * 受注詳細修正確認画面　⇒　処理履歴詳細画面　⇒　ブラウザバックで修正実行
     * とすると処理履歴の内容で更新してしまう為、フラグでチェックする
     * </pre>
     */
    private boolean historyDetailsFlag;

    /**
     * 注文チェックメッセージDTO
     */
    private OrderMessageDto orderMessageDto;

    /**
     * 異常値の項目の表示スタイル
     */
    private String errStyleClass;

    /**
     * エラー内容
     */
    private String errContent;

    /**
     * クーポン割引額
     */
    private String diffCouponDiscountPriceClass;

    /**
     * クーポン利用制限対象種別
     */
    private String diffCouponLimitTargetTypeClass;

    /**
     * 決済方法がコンビニで決済方法に変更なし
     *
     * @return true 決済方法がコンビニで決済方法に変更なし
     */
    public boolean isConveniAndNoDiff() {
        if (HTypeSettlementMethodType.CONVENIENCE.equals(settlementMethodType)
            && HTypeSettlementMethodType.CONVENIENCE == originalReceiveOrder.getOrderSettlementEntity()
                                                                            .getSettlementMethodType()
            && originalReceiveOrder.getMulPayBillEntity()
                                   .getConvenience()
                                   .equals(modifiedReceiveOrder.getMulPayBillEntity().getConvenience())) {
            return true;
        }
        return false;
    }

    /**
     * 決済方法がペイジーで決済方法に変更なし
     *
     * @return true 決済方法がペイジーで決済方法に変更なし
     */
    public boolean isPayeasyAndNoDiff() {
        if (HTypeSettlementMethodType.PAY_EASY.equals(settlementMethodType)
            && HTypeSettlementMethodType.PAY_EASY == originalReceiveOrder.getOrderSettlementEntity()
                                                                         .getSettlementMethodType()) {
            return true;
        }
        return false;
    }

    /**
     * 決済方法がペイジーで決済方法に変更あり
     *
     * @return true 決済方法がペイジーで決済方法に変更あり
     */
    public boolean isPayeasyAndDiff() {
        if (HTypeSettlementMethodType.PAY_EASY.equals(settlementMethodType)
            && HTypeSettlementMethodType.PAY_EASY != originalReceiveOrder.getOrderSettlementEntity()
                                                                         .getSettlementMethodType()) {
            return true;
        }
        return false;
    }

    /**
     * 決済方法がその他で決済方法に変更なし
     *
     * @return true 決済方法がペイジーで決済方法に変更なし
     */
    public boolean isElseAndNoDiff() {
        if (!HTypeSettlementMethodType.CREDIT.equals(settlementMethodType)
            && !HTypeSettlementMethodType.CONVENIENCE.equals(settlementMethodType)
            && !HTypeSettlementMethodType.PAY_EASY.equals(settlementMethodType)) {
            if (settlementMethodType == originalReceiveOrder.getOrderSettlementEntity().getSettlementMethodType()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the cancelFlag is ON or OFF
     */
    public boolean isStateCancel() {
        if (cancelTime != null && cancelTime.compareTo(processTime) <= 0) {
            return true;
        }
        return false;
    }

    /**
     * キャンセル以降の処理の場合、true を返却する
     *
     * @return the cancelFlag is ON or OFF
     */
    public boolean isStateAfterCancel() {
        if (cancelTime != null && cancelTime.compareTo(processTime) < 0) {
            return true;
        }
        return false;
    }

    /**
     * 決済手数料計算フラグ<br/>
     * true..計算する<br/>
     */
    private boolean commissionCalcFlag;

}
