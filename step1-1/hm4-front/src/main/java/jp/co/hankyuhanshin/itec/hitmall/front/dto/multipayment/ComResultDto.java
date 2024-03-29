/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.front.dto.common.CheckMessageDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * PDR#022 ユーザー毎の支払方法表示制御<br/>
 *
 * <pre>
 * 決済通信結果返却用Dto
 *
 * 応答電文のレコード リスト 追加
 * 各取得キー追加
 * </pre>
 *
 * @author satoh
 */
@Data
@Component
@Scope("prototype")
// Paygent Customization from here
public class ComResultDto {

    // PDR Migrate Customization from here
    /**
     * 取得キー:カード番号
     */
    public static final String KEY_CARD_NUMBER = "card_number";

    /**
     * 取得キー:有効期限
     */
    public static final String KEY_CARD_VALID_TERM = "card_valid_term";

    /**
     * 取得キー:カード会社
     */
    public static final String KEY_CARD_BRAND = "card_brand";

    /**
     * 取得キー:顧客カードID
     */
    public static final String KEY_CUSTOMER_CARD_ID = "customer_card_id";

    /**
     * 取得キー:決済ID
     */
    public static final String KEY_PAYMENT_ID = "payment_id";

    /**
     * 取得キー:マーチャント取引ID
     */
    public static final String KEY_TRADING_ID = "trading_id";
    // PDR Migrate Customization to here

    /**
     * 処理結果
     */
    private String resultStatus;

    /**
     * レスポンスコード(異常終了時)
     */
    private String responseCode;

    /**
     * レスポンス詳細(異常終了時)
     */
    private String responseDetail;

    /**
     * エラーメッセージリスト
     */
    private List<CheckMessageDto> messageList;

    /**
     * 応答電文のレコード
     */
    private Map<String, String> resultMap;

    // PDR Migrate Customization from here
    /**
     * 応答電文のレコード リスト
     */
    private List<Map<String, String>> resultMapList;
    // PDR Migrate Customization to here
}
// Paygent Customization to here
