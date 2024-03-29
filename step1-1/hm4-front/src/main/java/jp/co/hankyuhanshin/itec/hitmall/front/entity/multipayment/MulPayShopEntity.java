/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.multipayment;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * マルチペイメント用ショップ設定
 *
 * @author EntityGenerator
 */
@Data
@Component
@Scope("prototype")
public class MulPayShopEntity implements Serializable {

    /**
     * シリアル
     */
    private static final long serialVersionUID = 4258226645549343245L;

    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * ショップID
     */
    private String shopId;

    /**
     * ショップパスワード
     */
    private String shopPass;

    /**
     * アクセスURL
     */
    private String shopAccessUrl;

    /**
     * 3Dセキュア表示店舗名
     */
    private String tdTenantName;

    /**
     * HTTP_ACCEPT
     */
    private String httpAccept;

    /**
     * HTTP_USER_AGENT
     */
    private String httpUserAgent;

    /**
     * 加盟店舗自由項目１
     */
    private String clientField1;

    /**
     * 加盟店舗自由項目２
     */
    private String clientField2;

    /**
     * 加盟店舗自由項目３
     */
    private String clientField3;

    /**
     * 加盟店自由項目返却フラグ
     */
    private String clientFieldFlag;

    /**
     * 加盟店メールアドレス
     */
    private String shopMailAddress;

    /**
     * POS レジ表示欄-ショップ名
     */
    private String registerDisp1;

    /**
     * POS レジ表示欄2
     */
    private String registerDisp2;

    /**
     * POS レジ表示欄3
     */
    private String registerDisp3;

    /**
     * POS レジ表示欄4
     */
    private String registerDisp4;

    /**
     * POS レジ表示欄5
     */
    private String registerDisp5;

    /**
     * POS レジ表示欄6
     */
    private String registerDisp6;

    /**
     * POS レジ表示欄7
     */
    private String registerDisp7;

    /**
     * POS レジ表示欄8
     */
    private String registerDisp8;

    /**
     * レシート表示欄1
     */
    private String receiptsDisp1;

    /**
     * レシート表示欄2
     */
    private String receiptsDisp2;

    /**
     * レシート表示欄3
     */
    private String receiptsDisp3;

    /**
     * レシート表示欄4
     */
    private String receiptsDisp4;

    /**
     * レシート表示欄5
     */
    private String receiptsDisp5;

    /**
     * レシート表示欄6
     */
    private String receiptsDisp6;

    /**
     * レシート表示欄7
     */
    private String receiptsDisp7;

    /**
     * レシート表示欄8
     */
    private String receiptsDisp8;

    /**
     * レシート表示欄9
     */
    private String receiptsDisp9;

    /**
     * レシート表示欄10
     */
    private String receiptsDisp10;

    /**
     * レシート表示欄11
     */
    private String receiptsDisp11;

    /**
     * レシート表示欄12
     */
    private String receiptsDisp12;

    /**
     * レシート表示欄13
     */
    private String receiptsDisp13;

    /**
     * SUICA決済開始メール付加情報
     */
    private String suicaAddInfo1;

    /**
     * SUICA決済完了メール付加情報
     */
    private String suicaAddInfo2;

    /**
     * SUICA決済内容確認画面付加情報
     */
    private String suicaAddInfo3;

    /**
     * SUICA決済完了画面付加情情報
     */
    private String suicaAddInfo4;

    /**
     * EDY決済開始メール付加情報
     */
    private String edyAddInfo1;

    /**
     * EDY決済完了メール付加情報
     */
    private String edyAddInfo2;

    /**
     * 商品・サービス名
     */
    private String itemName;

}
