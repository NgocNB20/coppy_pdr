/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeEffectiveFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMailRequired;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodCommissionType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodPriceCommissionFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 決済詳細DTOクラス
 *
 * @author DtoGenerator
 * @version $Revision: 1.0 $
 */

@Data
@Component
@Scope("prototype")
public class SettlementDetailsDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 決済方法SEQ
     */
    private Integer settlementMethodSeq;

    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * 決済方法名
     */
    private String settlementMethodName;

    /**
     * 決済方法表示名PC
     */
    private String settlementMethodDisplayNamePC;

    /**
     * 決済方法表示名携帯
     */
    private String settlementMethodDisplayNameMB;

    /**
     * 公開状態PC
     */
    private HTypeOpenDeleteStatus openStatusPC;

    /**
     * 公開状態携帯
     */
    private HTypeOpenDeleteStatus openStatusMB;

    /**
     * 決済方法説明文PC
     */
    private String settlementNotePC;

    /**
     * 決済方法説明文携帯
     */
    private String settlementNoteMB;

    /**
     * 決済タイプ
     */
    private HTypeSettlementMethodType settlementMethodType;

    /**
     * 決済方法手数料種別
     */
    private HTypeSettlementMethodCommissionType settlementMethodCommissionType;

    /**
     * 請求種別
     */
    private HTypeBillType billType;

    /**
     * 配送方法SEQ
     */
    private Integer deliveryMethodSeq;

    /**
     * 一律手数料
     */
    private BigDecimal equalsCommission;

    /**
     * 決済方法金額別手数料フラグ
     */
    private HTypeSettlementMethodPriceCommissionFlag settlementMethodPriceCommissionFlag;

    /**
     * 高額割引下限金額
     */
    private BigDecimal largeAmountDiscountPrice;

    /**
     * 高額割引手数料
     */
    private BigDecimal largeAmountDiscountCommission;

    /**
     * 表示順
     */
    private Integer orderDisplay;

    /**
     * 最大購入金額
     */
    private BigDecimal maxPurchasedPrice;

    /**
     * 支払期限猶予日数
     */
    private Integer paymentTimeLimitDayCount;

    /**
     * 最小購入金額
     */
    private BigDecimal minPurchasedPrice;

    /**
     * 期限後取消猶予日数
     */
    private Integer cancelTimeLimitDayCount;

    /**
     * 決済関連メール要否フラグ
     */
    private HTypeMailRequired settlementMailRequired;

    /**
     * ｸﾚｼﾞｯﾄｶｰﾄﾞ登録機能有効化ﾌﾗｸﾞ
     */
    private HTypeEffectiveFlag enableCardNoHolding;

    /**
     * ｸﾚｼﾞｯﾄｾｷｭﾘﾃｨｺｰﾄﾞ有効化ﾌﾗｸﾞ
     */
    private HTypeEffectiveFlag enableSecurityCode;

    /**
     * ｸﾚｼﾞｯﾄ3Dｾｷｭｱ有効化ﾌﾗｸﾞ
     */
    private HTypeEffectiveFlag enable3dSecure;

    /**
     * ｸﾚｼﾞｯﾄ分割支払有効化ﾌﾗｸﾞ
     */
    private HTypeEffectiveFlag enableInstallment;

    /**
     * ｸﾚｼﾞｯﾄﾎﾞｰﾅｽ一括支払有効化ﾌﾗｸﾞ
     */
    private HTypeEffectiveFlag enableBonusSinglePayment;

    /**
     * ｸﾚｼﾞｯﾄﾎﾞｰﾅｽ分割支払有効化ﾌﾗｸﾞ
     */
    private HTypeEffectiveFlag enableBonusInstallment;

    /**
     * ｸﾚｼﾞｯﾄﾘﾎﾞ有効化ﾌﾗｸﾞ
     */
    private HTypeEffectiveFlag enableRevolving;

    /**
     * 上限金額
     */
    private BigDecimal maxPrice;

    /**
     * 手数料
     */
    private BigDecimal commission;
}
