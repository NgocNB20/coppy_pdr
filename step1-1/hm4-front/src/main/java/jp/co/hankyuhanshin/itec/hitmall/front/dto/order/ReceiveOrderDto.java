/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.PointUseType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAllocationDeliveryType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeInvoiceAttachmentFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePendingType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.additional.OrderAdditionalChargeEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.bill.OrderBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.bill.OrderReceiptOfMoneyEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.member.SimultaneousOrderExclusionEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.memo.OrderMemoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.settlement.SettlementMethodEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * 受注Dtoクラス
 *
 * @author DtoGenerator
 */
@Data
@Component
@Scope("prototype")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceiveOrderDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受注サマリエンティティ
     */
    private OrderSummaryEntity orderSummaryEntity;

    /**
     * 受注インデックスエンティティ
     */
    private OrderIndexEntity orderIndexEntity;

    /**
     * 受注ご注文主エンティティ
     */
    private OrderPersonEntity orderPersonEntity;

    /**
     * 受注配送Dto
     */
    private OrderDeliveryDto orderDeliveryDto;

    /**
     * 受注決済エンティティ
     */
    private OrderSettlementEntity orderSettlementEntity;

    /**
     * 2回目受注決済エンティティ
     */
    private OrderSettlementEntity orderNextSettlementEntity;

    /**
     * 受注追加料金エンティティリスト
     */
    private List<OrderAdditionalChargeEntity> orderAdditionalChargeEntityList;

    /**
     * 受注請求エンティティ
     */
    private OrderBillEntity orderBillEntity;

    /**
     * 受注入金エンティティリスト
     */
    private List<OrderReceiptOfMoneyEntity> orderReceiptOfMoneyEntityList;

    /**
     * 受注メモエンティティ
     */
    private OrderMemoEntity orderMemoEntity;

    /**
     * 決済方法エンティティ
     */
    private SettlementMethodEntity settlementMethodEntity;

    /**
     * 2回目決済方法エンティティ
     */
    private SettlementMethodEntity nextSettlementMethodEntity;

    /**
     * マルチペイ請求
     */
    private MulPayBillEntity mulPayBillEntity;

    /**
     * 注文開始時に取得したマスタ情報
     */
    private OrderInfoMasterDto masterDto;

    /**
     * 同時注文排他情報
     */
    private SimultaneousOrderExclusionEntity simultaneousOrderExclusionEntity;

    /**
     * クーポンエンティティ。<br />
     * <pre>
     * 注文に対して適用しているクーポン。
     * 未適用時はnull。
     * </pre>
     */
    private CouponEntity coupon;

    /**
     * クーポン適用フラグ<br/>
     * 決済方法選択：クーポン適用ボタンを押下した場合
     * trueとする。
     * <p>
     * クーポンチェックに使用
     */
    private boolean applyCouponFlg = false;

    /**
     * 受注一時情報Dto
     */
    private OrderTempDto orderTempDto;

    /**
     * 受注その他情報Dto
     */
    private OrderOtherDataDto orderOtherDataDto;

    /**
     * オーソリ期限日（決済日付＋オーソリ保持期間）
     */
    private Timestamp authoryLimitDate;

    /**
     * 再オーソリフラグ
     * true:再オーソリ処理時 false：通常受注修正時
     */
    private boolean reAuthoryFlag = false;

    /**
     * カード登録、変更により定期注文リカバリフラグ
     * true:カード登録、変更により定期注文リカバリ false：通常受注修正時
     */
    private boolean recoveryAuthoryFlag = false;

    /**
     * （今回）利用ポイント。<br />
     * <pre>
     * 今回の注文で利用するポイントを格納する。
     * 画面で入力されたポイントを保持する目的で追加したフィールドであるので、
     * 参照系画面では、OrderSettlement#usePointより利用ポイントを取得すること。
     * </pre>
     */
    private BigDecimal usePoint = BigDecimal.ZERO;

    /**
     * （今回）ポイント利用額。<br />
     * <pre>
     * 今回の注文で利用するポイントを格納する。
     * 画面で入力されたポイントを保持する目的で追加したフィールドであるので、
     * 参照系画面では、OrderSettlement#usePointより利用ポイントを取得すること。
     * </pre>
     */
    private BigDecimal pointDiscountPrice = BigDecimal.ZERO;

    /**
     * ポイント利用方法
     */
    private PointUseType pointUseType = PointUseType.NONE;

    /**
     * 確定ポイント
     */
    private BigDecimal activatePoint = BigDecimal.ZERO;

    /**
     * 獲得ポイント。 <br />
     * <pre>
     * 料金計算時に計算してセットする。
     * 画面で入力されたポイントを保持する目的で追加したフィールドであるので、
     * 参照系画面では、PointUtil#getAcquisitionPoint(Point)を利用して獲得ポイントを取得すること。
     * ※受注修正の場合、DXOが共通化されているため受注詳細画面、受注履歴詳細画面でも本フィールドを参照する。
     * </pre>
     */
    private BigDecimal acquisitionPoint;

    /**
     * クーポンコード。<br />
     * <pre>
     * 画面で入力されたクーポンコード。
     * 料金計算処理内でクーポンの利用可否を判定する為に必要。
     * </pre>
     */
    private String couponCode;

    // 2023-renew No24 from here
    /**
     * クーポン名<br />
     */
    private String couponName;
    // 2023-renew No24 to here

    /**
     * 前回決済手数料<br/>
     */
    private BigDecimal originalCommission;

    /**
     * 前回送料<br/>
     */
    private BigDecimal originalCarriage;

    /**
     * 納品書要否保持フラグ
     */
    private HTypeInvoiceAttachmentFlag invoiceAttachmentSetFlag = HTypeInvoiceAttachmentFlag.OFF;

    /**
     * 2回目お届け日
     */
    private Timestamp nextDeliveryDateTime;

    /**
     * 次回送料
     */
    private BigDecimal nextCarriage;

    /**
     * 定期注文フラグ
     */
    private boolean periodicOrderFlag;

    /**
     * 注文保留理由
     */
    private String orderWaitingMemo;

    // PDR Migrate Customization from here
    /**
     * 保留区分
     */
    private HTypePendingType pendingType;

    /**
     * 保留区分セット
     */
    private Set<HTypePendingType> pendingTypeSet;

    /**
     * 配送方法振分区分
     */
    private HTypeAllocationDeliveryType allocationDeliveryType;

    /**
     * オーソリ実行フラグ
     */
    private boolean executeAuthori;

    /**
     * 関連受注番号
     */
    private String relationOrderCode;

    /**
     * プロモーションコード
     */
    private String promotionCode;
    // PDR Migrate Customization to here

    /**
     * 3Dセキュアを必要とする注文かを判定
     *
     * @return true..必要
     */
    public boolean is3DSecureOrder() {
        return mulPayBillEntity != null && "1".equals(mulPayBillEntity.getAcs());
    }

    /**
     * 決済方法種別がコンビニ
     *
     * @return true:コンビニ false:コンビニ以外
     */
    public boolean isConvenience() {
        return HTypeSettlementMethodType.CONVENIENCE == orderSettlementEntity.getSettlementMethodType();
    }

    /**
     * 決済方法種別がペイジー
     *
     * @return true:ペイジー false:ペイジー以外
     */
    public boolean isPayEasy() {
        return HTypeSettlementMethodType.PAY_EASY == orderSettlementEntity.getSettlementMethodType();
    }

    /**
     * 受注状態が出荷済み
     *
     * @return true:出荷済み false:出荷済み以外
     */
    public boolean isShipment() {
        return HTypeOrderStatus.SHIPMENT_COMPLETION == orderSummaryEntity.getOrderStatus();
    }

}
