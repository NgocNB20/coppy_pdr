// 廃止不要機能
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

//package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.inquiry;
//
//import java.io.Serializable;
//import java.sql.Timestamp;
//
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
//import lombok.Data;
//
///**
// * お問い合わせ一覧画面 ModelItem
// *
// * @author kimura
// */
//@Data
//@Component
//@Scope("prototype")
//public class MemberInquiryModelItem implements Serializable {
//
//    /**
//     * コンストラクタ
//     *
//     * @param dateUtility
//     */
//    public MemberInquiryModelItem(DateUtility dateUtility) {
//        this.dateUtility = dateUtility;
//    }
//
//    /**
//     * DateUtility
//     */
//    private DateUtility dateUtility;
//
//    /**
//     * serialVersionUID
//     */
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 問い合わせ.お問い合わせ番号
//     */
//    private String inquiryCode;
//
//    /**
//     * 問い合わせ分類.問い合わせ分類名
//     */
//    private String inquiryGroupName;
//
//    /**
//     * 問い合わせ.問い合わせ状態
//     */
//    private String inquiryStatus;
//
//    /**
//     * 問い合わせ.問い合わせ状態.値
//     */
//    private String inquiryStatusValue;
//
//    /**
//     * 問い合わせ.初回問い合わせ日時
//     */
//    private Timestamp firstInquiryTime;
//
//    /**
//     * 問い合わせ.問い合わせ種別
//     */
//    private String inquiryType;
//
//    /**
//     * お問い合わせ番号を取得する
//     *
//     * @return お問い合わせ番号
//     */
//    public String getIcd() {
//        return inquiryCode;
//    }
//}
