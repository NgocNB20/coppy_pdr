/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.history;

import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePaymentType;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.ConvenienceUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 注文履歴 Model
 *
 * @author kimura
 */
@Data
public class MemberHistoryModel extends AbstractModel {

    /************************************
     ** 注文状況.値　画面スタイル用の定数一覧
     ************************************/

    /**
     * 商品準備中
     */
    public static final String GOODS_PREPARING_STATUS = "0";

    /**
     * 入金確認中
     */
    public static final String PAYMENT_CONFIRMING_STATUS = "1";

    /**
     * 出荷完了
     */
    public static final String SHIPMENT_COMPLETION_STATUS = "2";

    /**
     * キャンセル
     */
    public static final String CANCEL_STATUS = "3";

    /************************************
     ** 一覧画面
     ************************************/

    /**
     * 注文履歴一覧情報
     */
    private List<MemberHistoryModelItem> orderHistoryItems;

    /**
     * 注文履歴一覧：ページ番号
     */
    private String pnum;

    /**
     * 注文履歴一覧：最大表示件数
     */
    private int limit;

    /**
     * 注文履歴の有無<br/>
     *
     * @return true..無 false..有
     */
    public boolean isOrderHistoryEmpty() {

        if (orderHistoryItems == null) {
            return true;
        }
        return orderHistoryItems.isEmpty();
    }

    /************************************
     ** 詳細画面
     ************************************/

    /**
     * 受注SQL（URLパラメタ）
     */
    private String ocd;

    /**
     * ocd保管用変数
     * <pre>
     * ocdがRedirectScopeだと、画面をリロードした時に保存されていないため
     * 再度認証画面に飛ばされる。
     * しかし、ocdは認証画面に引き継ぎたい値のためRedirectScopeである必要がある。
     * </pre>
     */
    private String saveOcd;

    /************************************
     ** 注文情報
     ************************************/
    /**
     * 受注番号
     */
    private String orderCode;

    /**
     * 受注日時
     */
    private Timestamp orderTime;

    /**
     * 注文状況
     * 受注状態(入金確認中、商品準備中、出荷完了) 、 キャンセル 、 保留
     */
    private String status;

    /**
     * 注文状況.値
     * 受注状態(入金確認中、商品準備中、出荷完了) 、 キャンセル 、 保留
     */
    private String statusValue;

    /**
     * 決済方法
     */
    private String settlementMethodName;

    /**
     * お支払い金額
     */
    private BigDecimal orderPrice;

    /**
     * 入金累計金額
     */
    private BigDecimal receiptPriceTotal;

    /**
     * 会員SEQ
     */
    private Integer memberInfoSeq;

    /************************************
     ** 合計情報
     ************************************/

    /**
     * 商品合計金額
     */
    private BigDecimal goodsPriceTotal;

    /**
     * 送料
     */
    private BigDecimal carriage;

    /**
     * 決済手数料
     */
    private BigDecimal settlementCommission;

    /**
     * クーポン名
     */
    private String couponName;

    /**
     * クーポン割引額
     */
    private BigDecimal couponDiscountPrice;

    /**
     * 消費税
     */
    private BigDecimal taxPrice;

    /**
     * 標準税率消費税
     */
    private BigDecimal standardTaxPrice;

    /**
     * 軽減税率消費税
     */
    private BigDecimal reducedTaxPrice;

    /**
     * 標準税率対象金額
     */
    private BigDecimal standardTaxTargetPrice;

    /**
     * 軽減税率対象金額
     */
    private BigDecimal reducedTaxTargetPrice;

    /************************************
     ** ご注文主
     ************************************/
    /**
     * 氏名(姓)
     */
    private String orderPersonLastName;

    /**
     * 氏名(名)
     */
    private String orderPersonFirstName;

    /**
     * フリガナ(セイ)
     */
    private String orderPersonLastKana;

    /**
     * フリガナ(メイ)
     */
    private String orderPersonFirstKana;

    /**
     * メールアドレス
     */
    private String orderMail;

    /**
     * 電話番号
     */
    private String orderTel;

    /**
     * 連絡先電話番号
     */
    private String orderContactTel;

    /**
     * 住所-郵便番号1
     */
    private String orderZipCode1;

    /**
     * 住所-郵便番号2
     */
    private String orderZipCode2;

    /**
     * 住所-都道府県
     */
    private String orderPrefecture;

    /**
     * 住所-市区郡
     */
    private String orderAddress1;

    /**
     * 住所-町名
     */
    private String orderAddress2;

    /**
     * 住所-番地
     */
    private String orderAddress3;

    /************************************
     ** お届け先
     ************************************/
    /**
     * お届け先アイテム
     */
    private HistoryModelDeliveryItem orderDeliveryItem;

    /************************************
     ** 決済情報
     ************************************/
    /**
     * 決済区分
     */
    private HTypeSettlementMethodType settlementMethodType;

    /**
     * カードブランド表示名
     */
    private String cardbranddisplay;

    /**
     * お支払い方法
     */
    private String paymentTypeDisplay;

    /**
     * 分割回数
     */
    private String paytimes;

    /**
     * コンビニコード
     */
    private String convenienceCode;

    /**
     * コンビニ名称
     */
    private String convenienceName;

    /**
     * 受付番号
     */
    private String receiptNo;

    /**
     * 確認番号
     */
    private String confNo;

    /**
     * 払込期限
     */
    private Timestamp paymentTimeLimitDate;

    /**
     * 収納機関番号
     */
    private String bkCode;

    /**
     * お客様番号
     */
    private String custId;

    /**
     * ペイメントURL
     */
    private String paymentUrl;

    /**
     * その他備考
     */
    private String othersNote;

    /************************************
     ** 受注追加料金情報
     ************************************/

    /**
     * 追加料金リスト
     */
    private List<HistoryModelAdditionalChargeItem> orderAdditionalChargeItems;

    /**
     * Pay-Easy 暗号化決済番号
     */
    private String code;

    /**
     * クーポンを利用しているかを判定する。<br />
     * <pre>
     * クーポン割引額が0でない場合は、利用していると判定。
     * 0の場合は、利用していないと判定。
     * </pre>
     *
     * @return 利用している場合：true、利用していない場合：false
     */
    public boolean isUseCoupon() {
        return couponDiscountPrice != null && couponDiscountPrice.compareTo(BigDecimal.ZERO) != 0;
    }

    /**
     * コンビニ受付番号（4桁-7桁）
     *
     * @return コンビニ受付番号（4桁-7桁）
     */
    public String getReceiptNoWithHyphen() {
        String ret = "";
        if (receiptNo != null && receiptNo.length() >= 11) {
            return receiptNo.substring(0, 4) + "-" + receiptNo.substring(4, 11);
        }
        return ret;
    }

    /**
     * 決済方法がクレジット
     * ※すっきり定義できないのでべた書き
     *
     * @return クレジットの場合true
     */
    public boolean isCredit() {
        return HTypeSettlementMethodType.CREDIT.equals(this.settlementMethodType);
    }

    /**
     * お支払い方法が分割
     * ※すっきり定義できないのでべた書き
     *
     * @return 分割の場合true
     */
    public boolean isInstallment() {
        return HTypePaymentType.INSTALLMENT.getLabel().equals(this.paymentTypeDisplay);
    }

    /**
     * 決済方法がコンビニ
     * ※すっきり定義できないのでべた書き
     *
     * @return コンビニの場合true
     */
    public boolean isConveni() {
        return HTypeSettlementMethodType.CONVENIENCE.equals(this.settlementMethodType);
    }

    /**
     * 決済方法がコンビニの表示パターン１
     *
     * @return コンビニ決済判定の結果
     */
    public boolean isConveni1() {
        ConvenienceUtility convenienceUtility = ApplicationContextUtility.getBean(ConvenienceUtility.class);
        return convenienceUtility.isConveni1(convenienceCode);
    }

    /**
     * 決済方法がコンビニの表示パターン２
     *
     * @return コンビニ決済判定の結果
     */
    public boolean isConveni2() {
        ConvenienceUtility convenienceUtility = ApplicationContextUtility.getBean(ConvenienceUtility.class);
        return convenienceUtility.isConveni2(convenienceCode);
    }

    /**
     * 決済方法がコンビニの表示パターン３
     *
     * @return コンビニ決済判定の結果
     */
    public boolean isConveni3() {
        ConvenienceUtility convenienceUtility = ApplicationContextUtility.getBean(ConvenienceUtility.class);
        return convenienceUtility.isConveni3(convenienceCode);
    }

    /**
     * 決済方法がコンビニの表示パターン４
     *
     * @return コンビニ決済判定の結果
     */
    public boolean isConveni4() {
        ConvenienceUtility convenienceUtility = ApplicationContextUtility.getBean(ConvenienceUtility.class);
        return convenienceUtility.isConveni4(convenienceCode);
    }

    /**
     * 決済方法がコンビニの表示パターン５
     *
     * @return コンビニ決済判定の結果
     */
    public boolean isConveni5() {
        ConvenienceUtility convenienceUtility = ApplicationContextUtility.getBean(ConvenienceUtility.class);
        return convenienceUtility.isConveni5(convenienceCode);
    }

    /**
     * 決済方法がペイジー
     * ※すっきり定義できないのでべた書き
     *
     * @return ペイジーの場合true
     */
    public boolean isPayeasy() {
        return HTypeSettlementMethodType.PAY_EASY.equals(this.settlementMethodType);
    }

    /**
     * @return 追加料金エンティティ数
     */
    public BigDecimal getOrderAdditionalChargeItemsCount() {
        if (orderAdditionalChargeItems == null || orderAdditionalChargeItems.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(orderAdditionalChargeItems.size());
    }

    /**
     * PayEasyの金融機関選択ボタン表示の判断を行なう。<br/>
     * 以下の場合は表示しない<br/>
     * ・払込期限が、システム日付より大きい場合<br/>
     * ・入金済みの場合<br/>
     * ・キャンセルの場合<br/>
     *
     * @return true：表示する場合
     */
    public boolean isViewJlpbnkSelectButton() {

        // ペイジーのみ
        if (!isPayeasy()) {
            return false;
        }

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        Timestamp now = dateUtility.getCurrentDate();
        // 払込期限が、システム日付より大きい場合は、表示しない。
        if (paymentTimeLimitDate != null && now.after(paymentTimeLimitDate)) {
            return false;
        }

        // 入金済みの場合は表示しない
        if (orderPrice.compareTo(BigDecimal.ZERO) > 0 && receiptPriceTotal.compareTo(orderPrice) == 0) {
            return false;
        }

        // キャンセルの場合は表示しない
        if (status.equals(HTypeCancelFlag.ON.getLabel())) {
            return false;
        }
        return true;
    }
}
