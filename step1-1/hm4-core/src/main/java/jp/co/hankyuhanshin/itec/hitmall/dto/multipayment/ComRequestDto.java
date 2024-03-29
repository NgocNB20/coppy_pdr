/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeJobCode;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 【決済オプション部品（ペイジェント）】<br />
 * 決済送信情報用Dto
 *
 * @author y.kawai
 */
@Data
@Component
@Scope("prototype")
// Paygent Customization from here
public class ComRequestDto {

    /**
     * 通信APIへ渡す送信情報
     */
    private Map<String, String> requestMap = new HashMap<String, String>();

    /**
     * 注文番号
     */
    private String orderId;

    /**
     * 受注SEQ
     */
    private Integer orderSeq;

    /**
     * 受注履歴連番
     */
    private Integer orderVersionNo;

    /**
     * 処理区分
     */
    private HTypeJobCode jobCd;

    /**
     * 3Dセキュア使用フラグ
     */
    private String tdFlag;

    /**
     * ACS 呼出判定
     */
    private String acs;

    /**
     * 送信情報に値を設定します<br/>
     *
     * @param key キー
     * @param val 値
     */
    public void setVal(String key, String val) {
        requestMap.put(key, val);
    }
}
// Paygent Customization to here
