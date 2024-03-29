/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.abook;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * アドレス帳画面 ModelItem
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
@Component
@Scope("prototype")
public class MemberAbookModelItem implements Serializable {

    /**
     * serialVersionUID<br/>
     */
    private static final long serialVersionUID = 1L;

    /**
     * アドレス帳SEQ（URLパラメータ）
     */
    private String abid;

    /**
     * アドレス帳SEQ
     */
    private Integer addressBookSeq;

    /**
     * 会員SEQ
     */
    private Integer memberInfoSeq;

    /**
     * アドレス帳名
     */
    private String addressBookName;

    /**
     * アドレス帳氏名(姓)
     */
    private String addressBookLastName;

    /**
     * アドレス帳氏名(名）
     */
    private String addressBookFirstName;

    /**
     * アドレス帳フリガナ(姓)
     */
    private String addressBookLastKana;

    /**
     * アドレス帳フリガナ(名)
     */
    private String addressBookFirstKana;

    /**
     * アドレス帳TEL
     */
    private String addressBookTel;

    /**
     * アドレス帳住所-郵便番号（上3桁）
     */
    private String addressBookZipCode1;

    /**
     * アドレス帳住所-郵便番号（下4桁）
     */
    private String addressBookZipCode2;

    /**
     * アドレス帳住所-都道府県
     */
    private String addressBookPrefecture;

    /**
     * アドレス帳住所-市区郡
     */
    private String addressBookAddress1;

    /**
     * アドレス帳住所-町村・番地
     */
    private String addressBookAddress2;

    /**
     * アドレス帳住所-それ以降の住所
     */
    private String addressBookAddress3;

    /**
     * 登録日時
     */
    private Timestamp registTime;

    /**
     * 更新日時
     */
    private Timestamp updateTime;

    // PDR Migrate Customization from here
    /** 住所録住所-郵便番号 */
    private String addressBookZipCode;

    /** お届け先顧客番号 */
    private Integer receiveCustomerNo;

    /** 事業所名フリガナ */
    private String addressBookNameKana;

    /** 　顧客区分　 */
    private Integer businessType;

    /** 住所録住所-方書1,2 */
    private String addressBookAddress4;
    // PDR Migrate Customization to here
}
