/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */
package jp.co.hankyuhanshin.itec.hitmall.front.dto.multipayment;

/**
 * PaymentClient の XxxInput DTO を拡張するための共通インターフェース<br />
 * カード登録連番モード、カード登録連番については下記資料参照。<br />
 * <li>04_製造\02_技術資料\添付資料\PGマルチペイメントサービス\060_モジュールタイプ（Java版_カード決済インタフェース仕様）_1.13.pdf</li>
 *
 * @author tm27400
 */
public interface HmPaymentClientInput {

    /**
     * カード登録連番モード：論理
     */
    public static final String SEQ_MODE_LOGICAL = "0";

    /**
     * カード登録連番モード：物理
     */
    public static final String SEQ_MODE_PHYSICAL = "1";

    /**
     * カード登録連番：0<br />
     * HIT-MALL3.4はカード登録数が1枚のため、連番は0固定
     */
    public static final int CARD_SEQ = 0;

    /**
     * 支払方法：一括
     */
    public static final String METHOD_LUMP_SUM = "1";

    /**
     * 支払方法：分割
     */
    public static final String METHOD_INSTALLMENT = "2";

    /**
     * 支払方法：ボーナス一括
     */
    public static final String METHOD_BONUS_LUMP_SUM = "3";

    /**
     * 支払方法：ボーナス分割
     */
    public static final String METHOD_BONUS_INSTALLMENT = "4";

    /**
     * 支払方法：リボ
     */
    public static final String METHOD_REVOLVING = "5";

    /**
     * OrderSeq をゲットする
     *
     * @return OrderSeq
     */
    Integer getOrderSeq();

    /**
     * OrderSeq をセットする
     *
     * @param orderSeq 受注SEQ
     */
    void setOrderSeq(Integer orderSeq);

    /**
     * OrderVersionNo をゲットする
     *
     * @return OrderVersionNo
     */
    Integer getOrderVersionNo();

    /**
     * OrderVersionNo
     *
     * @param orderVersionNo 受注履歴連番
     */
    void setOrderVersionNo(Integer orderVersionNo);
}
