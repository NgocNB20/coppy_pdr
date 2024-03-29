/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.order;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCarrierType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailRequired;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberRank;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderAgeType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderSex;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePointActivateFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePointType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRepeatPurchaseType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReservationDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalesFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSend;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeWaitingFlag;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 受注検索受注一覧用Dtoクラス
 *
 * @author DtoGenerator
 */
@Entity
@Data
@Component
@Scope("prototype")
public class OrderSearchOrderResultDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受注SEQ
     */
    private Integer orderSeq;

    /**
     * 受注履歴連番
     */
    private Integer orderVersionNo;

    /**
     * 受注種別
     */
    private HTypeOrderType orderType;

    /**
     * 受注コード
     */
    private String orderCode;

    /**
     * 受注日時
     */
    private Timestamp orderTime;

    /**
     * 売上日時
     */
    private Timestamp salesTime;

    /**
     * キャンセル日時
     */
    private Timestamp cancelTime;

    /**
     * 売上フラグ
     */
    private HTypeSalesFlag salesFlag;

    /**
     * キャンセルフラグ
     */
    private HTypeCancelFlag cancelFlag;

    /**
     * 保留中フラグ
     */
    private HTypeWaitingFlag waitingFlag;

    /**
     * 受注状態
     */
    private HTypeOrderStatus orderStatus;

    /**
     * 商品金額合計
     */
    private BigDecimal goodsPriceTotal;

    /**
     * 配送料
     */
    private BigDecimal orderDeliveryCarriage;

    /**
     * 受注金額
     */
    private BigDecimal orderPrice;

    /**
     * 入金累計
     */
    private BigDecimal receiptPriceTotal;

    /**
     * 受注サイト種別
     */
    private HTypeSiteType orderSiteType;

    /**
     * 受注デバイス種別
     */
    private HTypeDeviceType orderDeviceType;

    /**
     * キャリア種別
     */
    private HTypeCarrierType carrierType;

    /**
     * 決済方法SEQ
     */
    private Integer settlementMethodSeq;

    /**
     * 会員SEQ
     */
    private Integer memberInfoSeq;

    /**
     * 会員ランク
     */
    private HTypeMemberRank memberRank;

    /**
     * 都道府県種別
     */
    private HTypePrefectureType prefectureType;

    /**
     * ご注文主性別
     */
    private HTypeOrderSex orderSex;

    /**
     * ご注文主年代
     */
    private HTypeOrderAgeType orderAgeType;

    /**
     * リピート購入種別
     */
    private HTypeRepeatPurchaseType repeatPurchaseType;

    /**
     * 更新カウンタ
     */
    private Integer versionNo;

    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * 決済関連メール要否フラグ
     */
    private HTypeMailRequired settlementMailRequired;

    /**
     * 督促メール送信済みフラグ
     */
    private HTypeSend reminderSentFlag;

    /**
     * 期限切れメール送信済みフラグ
     */
    private HTypeSend expiredSentFlag;

    /**
     * クーポン適用前受注金額
     */
    private BigDecimal preCouponDiscountOrderPrice;

    /**
     * ポイント利用前受注金額
     */
    private BigDecimal prePointDiscountOrderPrice;

    /**
     * ポイント確定フラグ<br/>
     * <pre>
     * 0:未確定 1:確定
     * </pre>
     */
    private HTypePointActivateFlag pointActivateFlag;

    /**
     * 定期受注SEQ
     */
    private Integer periodicOrderSeq;

    /**
     * ユーザーエージェント
     */
    private String userAgent;

    /**
     * フリーエリアキー
     */
    private String freeAreaKey;

    /**
     * 登録日時
     */
    private Timestamp registTime;

    /**
     * 更新日時
     */
    private Timestamp updateTime;

    /**
     * 受注商品連番
     */
    private Integer orderGoodsVersionNo;

    // クーポン情報
    /**
     * クーポン割引額
     */
    private BigDecimal couponDiscountPrice;

    // ポイント情報
    /**
     * ポイントSEQ
     */
    private Integer pointSeq;

    /**
     * ポイント連番
     */
    private Integer pointVersionNo;

    /**
     * 利用ポイント
     * セッターにてポイント利用額の算出を行っている為、
     * #setUsePointを利用すること
     */
    private BigDecimal usePoint;

    /**
     * ポイント利用額
     */
    private BigDecimal pointDiscountPrice;

    /**
     * ポイント種別
     */
    private HTypePointType pointType;

    /**
     * ポイント数
     */
    private BigDecimal point;

    /**
     * 獲得ポイント合計
     */
    private BigDecimal totalAcquisitionPoint;

    /**
     * ポイント付与基準金額
     */
    private BigDecimal orderPointAddBasePrice;

    /**
     * ポイント付与率
     */
    private BigDecimal orderPointAddRate;

    // 決済方法情報
    /**
     * 決済方法名
     */
    private String settlementMethodName;

    // 受注配送方法情報
    /**
     * お届け時間帯
     */
    private String receiverTimeZone;

    /**
     * ご注文主氏名
     */
    private String orderName;

    /**
     * ご注文主フリガナ
     */
    private String orderKana;

    /**
     * ご注文主電話番号
     */
    private String orderTel;

    /**
     * ご注文主連絡先電話番号
     */
    private String orderContactTel;

    /**
     * ご注文主メールアドレス
     */
    private String orderMail;

    /**
     * お届け先氏名
     */
    private String receiverName;

    /**
     * お届け先フリガナ
     */
    private String receiverKana;

    /**
     * お届け先電話番号
     */
    private String receiverTel;

    /**
     * 注文連番
     */
    private Integer orderConsecutiveNo;

    /**
     * 伝票番号
     */
    private String deliveryCode;

    /**
     * 出荷日
     */
    private Timestamp shipmentdate;

    /**
     * 出荷状態
     */
    private String shipmentStatus;

    /**
     * 予約配送フラグ
     */
    private HTypeReservationDeliveryFlag reservationDeliveryFlag;

    /**
     * 配送方法備考
     */
    private String deliveryNote;

    /**
     * 入金日時
     */
    private Timestamp receiptTime;

    /**
     * 異常フラグ
     */
    private HTypeEmergencyFlag emergencyFlag;

    /**
     * 入金状態
     * "1"=未入金
     * "2"=入金済み
     * "3"=過不足
     */
    private String paymentStatus;

    /**
     * 配送方法名称
     */
    private String deliveryMethodName;

    /**
     * 検索結果表示用受注状態
     */
    private String orderStatusForSearchResult;

    /**
     * @param usePoint the usePoint to set
     */
    public void setUsePoint(BigDecimal usePoint) {
        this.usePoint = usePoint;
    }
}
