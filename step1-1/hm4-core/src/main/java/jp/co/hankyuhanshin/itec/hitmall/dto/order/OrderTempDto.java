/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * PDR#022 ユーザー毎の支払方法表示制御<br/>
 *
 * <pre>
 * 受注一時情報Dtoクラス
 *
 * カード会社 追加
 * 顧客カードID 追加
 * 決済ID 追加
 * </pre>
 *
 * @author satoh
 */
@Data
@Component
@Scope("prototype")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderTempDto implements Serializable {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderTempDto.class);

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * クレジットカード番号
     */
    private String cardNo;

    /**
     * カード有効期限(YYMM)
     */
    private String expire;

    /**
     * セキュリティーコード
     */
    private String securityCode;

    /**
     * お支払い区分
     */
    private String method;

    /**
     * 分割回数
     */
    private Integer payTimes;

    /**
     * コンビニコード
     */
    private String convenience;

    /**
     * コンビニ名称
     */
    private String conveniName;

    /**
     * コンビニSEQ
     */
    private Integer conveniSeq;

    /**
     * 本人認証パスワード入力画面URL(カード決済の通信Output項目)
     */
    private String acsUrl;

    /**
     * 本人認証サービス要求電文(カード決済の通信Output項目)
     */
    private String paReq;

    /**
     * 決済代行会社会員ID
     */
    private String paymentMemberId;

    /**
     * この決済で登録済みカードを使用するならtrue
     */
    private boolean useRegistCardFlg;

    /**
     * カード保存フラグ(今回のカード情報を保存するを選択した場合)
     */
    private boolean saveFlg;

    /**
     * 登録されたカードがあるならtrue
     */
    private boolean registCredit;

    /**
     * カート情報
     */
    private CartDto cartDto;

    /**
     * 全額クーポン支払フラグ
     */
    private boolean canUseCouponSettlementFlg;

    /**
     * 全額ポイント支払フラグ
     */
    private boolean canUseAllPointSettlementFlg;

    /**
     * トークン
     */
    private String token;

    // Paygent Customization from here
    /**
     * カード預かり通信用トークン
     */
    private String keepToken;

    /**
     * 応答コンテンツ
     */
    private String dispHtml;
    // Paygent Customization to here

    // PDR Migrate Customization from here
    /**
     * カード会社
     */
    private String cardBrand;

    /**
     * 顧客カードID
     */
    private String customerCardId;

    /**
     * 決済ID
     */
    private String paymentId;
    // PDR Migrate Customization to here

    /**
     * @return カード有効期限(YYMM)のうち、年(YY)部分
     */
    public String getExpireYear() {
        if (StringUtil.isEmpty(expire)) {
            return null;
        }

        try {
            return expire.substring(0, 2);
        } catch (RuntimeException e) {
            LOGGER.error("例外処理が発生しました", e);
        }

        return null;
    }

    /**
     * @return カード有効期限(YYMM)のうち、月(MM)部分
     */
    public String getExpireMonth() {
        if (StringUtil.isEmpty(expire)) {
            return null;
        }

        try {
            return expire.substring(2);
        } catch (RuntimeException e) {
            LOGGER.error("例外処理が発生しました", e);
        }

        return null;
    }

    /**
     * 別のカードを使うボタン押下フラグ
     */
    private boolean preCreditInformationFlag;

}
