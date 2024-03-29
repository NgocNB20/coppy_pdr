/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.addressbook;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAddressBookApproveFlag;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.SequenceGenerator;
import org.seasar.doma.Table;
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
@Entity
@Table(name = "AddressBook")
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
    @Column(name = "addressBookSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "addressBookSeq")
    private Integer addressBookSeq;

    /**
     * 会員SEQ (FK)（必須）
     */
    @Column(name = "memberInfoSeq")
    private Integer memberInfoSeq;

    /**
     * アドレス帳名（必須）
     */
    @Column(name = "addressBookName")
    private String addressBookName;

    /**
     * アドレス帳氏名(姓)（必須）
     */
    @Column(name = "addressBookLastName")
    private String addressBookLastName;

    /**
     * アドレス帳氏名(名）
     */
    @Column(name = "addressBookFirstName")
    private String addressBookFirstName;

    /**
     * アドレス帳フリガナ(姓)（必須）
     */
    @Column(name = "addressBookLastKana")
    private String addressBookLastKana;

    /**
     * アドレス帳フリガナ(名)
     */
    @Column(name = "addressBookFirstKana")
    private String addressBookFirstKana;

    /**
     * アドレス帳TEL
     */
    @Column(name = "addressBookTel")
    private String addressBookTel;

    /**
     * アドレス帳住所-郵便番号（必須）
     */
    @Column(name = "addressBookZipCode")
    private String addressBookZipCode;

    /**
     * アドレス帳住所-都道府県（必須）
     */
    @Column(name = "addressBookPrefecture")
    private String addressBookPrefecture;

    /**
     * アドレス帳住所-市区郡（必須）
     */
    @Column(name = "addressBookAddress1")
    private String addressBookAddress1;

    /**
     * アドレス帳住所-町村・番地
     */
    @Column(name = "addressBookAddress2")
    private String addressBookAddress2;

    /**
     * アドレス帳住所-それ以降の住所
     */
    @Column(name = "addressBookAddress3")
    private String addressBookAddress3;

    /**
     * 登録日時（必須）
     */
    @Column(name = "registTime")
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    @Column(name = "updateTime")
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
    @Column(name = "addressBookAddress4")
    private String addressBookAddress4;

    /** 住所録住所-方書2 */
    @Column(name = "addressBookAddress5")
    private String addressBookAddress5;

    /** 顧客番号 */
    @Column(name = "customerNo")
    private Integer customerNo;

    /** 住所録承認フラグ */
    @Column(name = "addressBookApproveFlag")
    private HTypeAddressBookApproveFlag addressBookApproveFlagPdr;
    // PDR Migrate Customization to here

}
