/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.addressbook;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAddressBookApproveFlag;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * アドレス帳クラス
 *
 * @author EntityGenerator
 * @version $Revision: 1.0 $
 */
@Data
@Component
@Scope("prototype")
public class AddressBookEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * アドレス帳SEQ（必須）
     */
    private Integer addressBookSeq;

    /**
     * 会員SEQ (FK)（必須）
     */
    private Integer memberInfoSeq;

    /**
     * アドレス帳名（必須）
     */
    private String addressBookName;

    /**
     * アドレス帳氏名(姓)（必須）
     */
    private String addressBookLastName;

    /**
     * アドレス帳氏名(名）
     */
    private String addressBookFirstName;

    /**
     * アドレス帳フリガナ(姓)（必須）
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
     * アドレス帳住所-郵便番号（必須）
     */
    private String addressBookZipCode;

    /**
     * アドレス帳住所-都道府県（必須）
     */
    private String addressBookPrefecture;

    /**
     * アドレス帳住所-市区郡（必須）
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
     * 登録日時（必須）
     */
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    private Timestamp updateTime;

    // PDR Migrate Customization from here
    //    /**
    //     * PDR#037 住所録情報の取り込み<br/>
    //     *
    //     * <pre>
    //     * 住所録Entityクラス
    //     * ・住所録住所-方書1 追加
    //     * ・住所録住所-方書2 追加
    //     * ・顧客番号 追加
    //     * ・住所録承認フラグ 追加
    //     * </pre>
    //     *
    //     */
    /** 住所録住所-方書1 */
    private String addressBookAddress4;

    /** 住所録住所-方書2 */
    private String addressBookAddress5;

    /** 顧客番号 */
    private Integer customerNo;

    /** 住所録承認フラグ */
    private HTypeAddressBookApproveFlag addressBookApproveFlagPdr;
    // PDR Migrate Customization to here

}
