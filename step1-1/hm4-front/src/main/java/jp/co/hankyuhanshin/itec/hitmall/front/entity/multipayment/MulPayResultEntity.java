/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeProcessedFlag;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * ﾏﾙﾁﾍﾟｲﾒﾝﾄ決済結果
 *
 * @author EntityGenerator
 */
@Data
@Component
@Scope("prototype")
public class MulPayResultEntity implements Serializable {

    /**
     * シリアル
     */
    private static final long serialVersionUID = -9190927375006207185L;

    /**
     * ﾏﾙﾁﾍﾟｲﾒﾝﾄ決済結果SEQ
     */
    private Integer mulPayResultSeq;

    /**
     * 受信方法
     */
    private String receiveMode;

    /**
     * 入金処理済みフラグ
     */
    private HTypeProcessedFlag processedFlag;

    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * 受注SEQ
     */
    private Integer orderSeq;

    /**
     * オーダーID
     */
    private String orderId;

    /**
     * 現状態
     */
    private String status;

    /**
     * 処理区分
     */
    private String jobCd;

    /**
     * 処理日時
     */
    private String processDate;

    /**
     * 商品コード
     */
    private String itemCode;

    /**
     * 利用金額
     */
    private BigDecimal amount;

    /**
     * 税送料
     */
    private BigDecimal tax;

    /**
     * サイトID
     */
    private String siteId;

    /**
     * 会員ID
     */
    private String memberId;

    /**
     * カード番号
     */
    private String cardNo;

    /**
     * カード有効期限
     */
    private String expire;

    /**
     * 通貨コード
     */
    private String currency;

    /**
     * 仕向け先会社コード
     */
    private String forward;

    /**
     * 支払方法
     */
    private String method;

    /**
     * 支払回数
     */
    private Integer payTimes;

    /**
     * トランザクションID
     */
    private String tranId;

    /**
     * 承認番号
     */
    private String approve;

    /**
     * 処理日付
     */
    private String tranDate;

    /**
     * エラーコード
     */
    private String errCode;

    /**
     * エラー詳細コード
     */
    private String errInfo;

    /**
     * 加盟店自由項目1
     */
    private String clientField1;

    /**
     * 加盟店自由項目2
     */
    private String clientField2;

    /**
     * 加盟店自由項目3
     */
    private String clientField3;

    /**
     * 決済方法
     */
    private String payType;

    /**
     * 支払先コンビニコード
     */
    private String cvsCode;

    /**
     * CVS確認番号
     */
    private String cvsConfNo;

    /**
     * CVS受付番号
     */
    private String cvsReceiptNo;

    /**
     * EDY受付番号
     */
    private String edyReceiptNo;

    /**
     * EDY注文番号
     */
    private String edyOrderNo;

    /**
     * SUICA受付番号
     */
    private String suicaReceiptNo;

    /**
     * SUICA注文番号
     */
    private String suicaOrderNo;

    /**
     * お客様番号
     */
    private String custId;

    /**
     * 収納機関番号
     */
    private String bkCode;

    /**
     * 確認番号
     */
    private String confNo;

    /**
     * 支払期限日時
     */
    private String paymentTerm;

    /**
     * 暗号化決済番号
     */
    private String encryptReceiptNo;

    /**
     * 入金確定日時
     */
    private String finishDate;

    /**
     * 受付日時
     */
    private String receiptDate;

    /**
     * キャンセル金額
     */
    private BigDecimal cancelAmount;

    /**
     * キャンセル税送料金額
     */
    private BigDecimal cancelTax;

    /**
     * AmazonオーダーリファレンスID
     */
    private String amazonOrderReferenceID;

    /**
     * 登録日時
     */
    private Timestamp registTime;

    /**
     * 更新日時
     */
    private Timestamp updateTime;

}
