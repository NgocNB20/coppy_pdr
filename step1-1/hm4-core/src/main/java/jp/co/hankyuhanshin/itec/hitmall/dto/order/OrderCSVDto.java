/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.order;

import jp.co.hankyuhanshin.itec.hitmall.annotation.csv.CsvColumn;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 受注CSV出力用Dto
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@Data
@Entity
@Component
@Scope("prototype")
public class OrderCSVDto {

    /**
     * 行種別
     */
    @CsvColumn(order = 10, columnLabel = "行種別")
    private String lineType;

    /**
     * 受注番号
     */
    @CsvColumn(order = 20, columnLabel = "受注番号")
    private String orderCode;

    /**
     * 注文連番
     */
    @CsvColumn(order = 30, columnLabel = "注文連番")
    private String orderConsecutiveNo;

    /**
     * 受注種別
     */
    @CsvColumn(order = 40, columnLabel = "受注種別")
    private String orderType;

    /**
     * 受付サイト
     */
    @CsvColumn(order = 50, columnLabel = "受付サイト")
    private String orderSiteType;

    /**
     * 受注日時
     */
    @CsvColumn(order = 60, columnLabel = "受注日時")
    private String orderTime;

    /**
     * 受注状態
     */
    @CsvColumn(order = 70, columnLabel = "受注状態")
    private String orderStatus;

    /**
     * 出荷日
     */
    @CsvColumn(order = 80, columnLabel = "出荷日")
    private String shipmentDate;

    /**
     * 出荷状態
     */
    @CsvColumn(order = 90, columnLabel = "出荷状態")
    private String shipmentStatus;

    /**
     * キャンセル日時
     */
    @CsvColumn(order = 110, columnLabel = "キャンセル日時")
    private String cancelTime;

    /**
     * 受注金額
     */
    @CsvColumn(order = 120, columnLabel = "受注金額（税込）")
    private String orderPrice;

    /**
     * 入金累計額
     */
    @CsvColumn(order = 130, columnLabel = "入金累計額")
    private String receiptPriceTotal;

    /**
     * 入金状態
     */
    @CsvColumn(order = 140, columnLabel = "入金状態")
    private String paymentStatus;

    /**
     * 最終入出金日
     */
    @CsvColumn(order = 150, columnLabel = "最終入出金日")
    private String receiptYmd;

    /**
     * 最終入出金額
     */
    @CsvColumn(order = 160, columnLabel = "最終入出金額")
    private String receiptPrice;

    /**
     * 商品管理番号
     */
    @CsvColumn(order = 510, columnLabel = "商品管理番号")
    private String goodsGroupCode;

    /**
     * 商品番号
     */
    @CsvColumn(order = 520, columnLabel = "商品番号")
    private String goodsCode;

    /**
     * JANコード
     */
    @CsvColumn(order = 530, columnLabel = "JANコード")
    private String janCode;

    /**
     * カタログコード
     */
    @CsvColumn(order = 540, columnLabel = "カタログコード")
    private String catalogCode;

    /**
     * 商品名
     */
    @CsvColumn(order = 550, columnLabel = "商品名")
    private String goodsGroupName;

    /**
     * 規格1
     */
    @CsvColumn(order = 560, columnLabel = "規格1")
    private String unitValue1;

    /**
     * 規格2
     */
    @CsvColumn(order = 570, columnLabel = "規格2")
    private String unitValue2;

    /**
     * 価格
     */
    @CsvColumn(order = 580, columnLabel = "価格（税抜）")
    private String goodsPrice;

    /**
     * 配送料
     */
    @CsvColumn(order = 590, columnLabel = "配送料（税抜）")
    private String orderDeliveryCarriage;

    /**
     * 数量
     */
    @CsvColumn(order = 600, columnLabel = "数量")
    private String goodsCount;

    /**
     * 小計
     */
    @CsvColumn(order = 610, columnLabel = "小計（税抜）")
    private String summaryPrice;

    /**
     * 販売開始日時PC
     */
    @CsvColumn(order = 620, columnLabel = "販売開始日時PC", dateFormat = "yyyy/MM/dd HH:mm:ss")
    private Timestamp saleStartTimePC;

    /**
     * 商品金額合計
     */
    @CsvColumn(order = 1010, columnLabel = "商品金額合計（税抜）")
    private String goodsPriceTotal;

    /**
     * 手数料
     */
    @CsvColumn(order = 1020, columnLabel = "手数料（税抜）")
    private String settlementCommission;

    /**
     * 配送料合計
     */
    @CsvColumn(order = 1030, columnLabel = "配送料合計（税抜）")
    private String carriage;

    /**
     * その他追加料金
     */
    @CsvColumn(order = 1040, columnLabel = "その他追加料金（税抜）")
    private String othersPriceTotal;

    /**
     * 消費税額
     */
    @CsvColumn(order = 1050, columnLabel = "消費税額")
    private String taxPrice;

    /**
     * クーポンID
     */
    @CsvColumn(order = 1100, columnLabel = "クーポンID")
    private String couponId;

    /**
     * クーポン名
     */
    @CsvColumn(order = 1110, columnLabel = "クーポン名")
    private String couponName;

    /**
     * クーポン割引額
     */
    @CsvColumn(order = 1120, columnLabel = "クーポン割引額")
    private String couponDiscountPrice;

    /**
     * お客様姓
     */
    @CsvColumn(order = 2010, columnLabel = "お客様姓")
    private String orderLastName;

    /**
     * お客様名
     */
    @CsvColumn(order = 2020, columnLabel = "お客様名")
    private String orderFirstName;

    /**
     * お客様セイ
     */
    @CsvColumn(order = 2030, columnLabel = "お客様セイ")
    private String orderLastKana;

    /**
     * お客様メイ
     */
    @CsvColumn(order = 2040, columnLabel = "お客様メイ")
    private String orderFirstKana;

    /**
     * お客様郵便番号
     */
    @CsvColumn(order = 2050, columnLabel = "お客様郵便番号")
    private String orderZipCode;

    /**
     * お客様都道府県
     */
    @CsvColumn(order = 2060, columnLabel = "お客様都道府県")
    private String orderPrefecture;

    /**
     * お客様市区郡
     */
    @CsvColumn(order = 2070, columnLabel = "お客様市区郡")
    private String orderAddress1;

    /**
     * お客様町村・番地
     */
    @CsvColumn(order = 2080, columnLabel = "お客様町村・番地")
    private String orderAddress2;

    /**
     * お客様マンション・建物名
     */
    @CsvColumn(order = 2090, columnLabel = "お客様マンション・建物名")
    private String orderAddress3;

    /**
     * お客様電話番号
     */
    @CsvColumn(order = 2100, columnLabel = "お客様電話番号")
    private String orderTel;

    /**
     * お客様連絡先電話番号
     */
    @CsvColumn(order = 2110, columnLabel = "お客様連絡先電話番号")
    private String orderContactTel;

    /**
     * お客様メールアドレス
     */
    @CsvColumn(order = 2120, columnLabel = "お客様メールアドレス")
    private String orderMail;

    /**
     * お客様生年月日
     */
    @CsvColumn(order = 2130, columnLabel = "お客様生年月日")
    private String orderBirthday;

    /**
     * お客様年代
     */
    @CsvColumn(order = 2140, columnLabel = "お客様年代")
    private String orderAgeType;

    /**
     * お客様性別
     */
    @CsvColumn(order = 2150, columnLabel = "お客様性別")
    private String orderSex;

    /**
     * リピート種別
     */
    @CsvColumn(order = 2160, columnLabel = "リピート種別")
    private String repeatPurchaseType;

    /**
     * お届け先姓
     */
    @CsvColumn(order = 4010, columnLabel = "お届け先姓")
    private String receiverLastName;

    /**
     * お届け先名
     */
    @CsvColumn(order = 4020, columnLabel = "お届け先名")
    private String receiverFirstName;

    /**
     * お届け先セイ
     */
    @CsvColumn(order = 4030, columnLabel = "お届け先セイ")
    private String receiverLastKana;

    /**
     * お届け先メイ
     */
    @CsvColumn(order = 4040, columnLabel = "お届け先メイ")
    private String receiverFirstKana;

    /**
     * お届け先郵便番号
     */
    @CsvColumn(order = 4050, columnLabel = "お届け先郵便番号")
    private String receiverZipCode;

    /**
     * お届け先都道府県
     */
    @CsvColumn(order = 4060, columnLabel = "お届け先都道府県")
    private String receiverPrefecture;

    /**
     * お届け先市区郡
     */
    @CsvColumn(order = 4070, columnLabel = "お届け先市区郡")
    private String receiverAddress1;

    /**
     * お届け先町村・番地
     */
    @CsvColumn(order = 4080, columnLabel = "お届け先町村・番地")
    private String receiverAddress2;

    /**
     * お届け先マンション・建物名
     */
    @CsvColumn(order = 4090, columnLabel = "お届け先マンション・建物名")
    private String receiverAddress3;

    /**
     * お届け先電話番号
     */
    @CsvColumn(order = 4100, columnLabel = "お届け先電話番号")
    private String receiverTel;

    /**
     * 配送方法
     */
    @CsvColumn(order = 4110, columnLabel = "配送方法")
    private String deliveryMethodName;

    /**
     * お届け希望日
     */
    @CsvColumn(order = 4120, columnLabel = "お届け希望日")
    private String receiverDate;

    /**
     * お届け時間指定
     */
    @CsvColumn(order = 4130, columnLabel = "お届け時間指定")
    private String receiverTimeZone;

    /**
     * 納品書
     */
    @CsvColumn(order = 4140, columnLabel = "納品書")
    private String invoiceAttachmentFlag;

    /**
     * 配送メモ
     */
    @CsvColumn(order = 4150, columnLabel = "配送メモ")
    private String deliveryNote;

    /**
     * 配送伝票番号
     */
    @CsvColumn(order = 4160, columnLabel = "配送伝票番号")
    private String deliveryCode;

    /**
     * 決済方法
     */
    @CsvColumn(order = 6010, columnLabel = "決済方法")
    private String settlementMethodName;

    /**
     * 請求状態
     */
    @CsvColumn(order = 6020, columnLabel = "請求状態")
    private String billStatus;

    /**
     * 異常フラグ
     */
    @CsvColumn(order = 6030, columnLabel = "異常フラグ")
    private String emergencyFlag;

    /**
     * 支払期限日
     */
    @CsvColumn(order = 6040, columnLabel = "支払期限日")
    private String paymentTimeLimitDate;

    /**
     * メモ
     */
    @CsvColumn(order = 6050, columnLabel = "メモ")
    private String memo;

    /**
     * 会員SEQ
     */
    @CsvColumn(order = 6060, columnLabel = "会員SEQ")
    private Integer memberInfoSeq;

    /**
     * 受注連携設定01
     */
    @CsvColumn(order = 6080, columnLabel = "受注連携設定01")
    private String orderSetting1;

    /**
     * 受注連携設定02
     */
    @CsvColumn(order = 6090, columnLabel = "受注連携設定02")
    private String orderSetting2;

    /**
     * 受注連携設定03
     */
    @CsvColumn(order = 6100, columnLabel = "受注連携設定03")
    private String orderSetting3;

    /**
     * 受注連携設定04
     */
    @CsvColumn(order = 6110, columnLabel = "受注連携設定04")
    private String orderSetting4;

    /**
     * 受注連携設定05
     */
    @CsvColumn(order = 6120, columnLabel = "受注連携設定05")
    private String orderSetting5;

    /**
     * 受注連携設定06
     */
    @CsvColumn(order = 6130, columnLabel = "受注連携設定06")
    private String orderSetting6;

    /**
     * 受注連携設定07
     */
    @CsvColumn(order = 6140, columnLabel = "受注連携設定07")
    private String orderSetting7;

    /**
     * 受注連携設定08
     */
    @CsvColumn(order = 6150, columnLabel = "受注連携設定08")
    private String orderSetting8;

    /**
     * 受注連携設定09
     */
    @CsvColumn(order = 6160, columnLabel = "受注連携設定09")
    private String orderSetting9;

    /**
     * 受注連携設定10
     */
    @CsvColumn(order = 6170, columnLabel = "受注連携設定10")
    private String orderSetting10;
}
