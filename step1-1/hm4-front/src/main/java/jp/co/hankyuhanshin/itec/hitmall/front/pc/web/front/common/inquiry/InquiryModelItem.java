// 廃止不要機能
///*
// * Project Name : HIT-MALL4
// *
// * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
// *
// */
//
//package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.inquiry;
//
//import java.io.Serializable;
//import java.sql.Timestamp;
//
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeInquiryRequestType;
//import lombok.Data;
//
///**
// * 問合せ ModelItem
// *
// * @author kn23834
// * @version $Revision: 1.0 $
// */
//@Data
//@Component
//@Scope("prototype")
//public class InquiryModelItem implements Serializable {
//
//    /**
//     * serialVersionUID
//     */
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 問い合わせ内容.部署名
//     */
//    private String divisionName;
//
//    /**
//     * 問い合わせ内容.担当者
//     */
//    private String representative;
//
//    /**
//     * 問い合わせ内容.問い合わせ日時
//     */
//    private Timestamp inquiryTime;
//
//    /**
//     * 問い合わせ内容.問い合わせ者TEL
//     */
//    private String contactTel;
//
//    /**
//     * 処理担当者
//     */
//    private String operator;
//
//    /**
//     * 問い合わせ内容.問い合わせ内容
//     */
//    private String inquiryBody;
//
//    /**
//     * 問い合わせ内容.発信者種別
//     */
//    private HTypeInquiryRequestType requestType;
//
//    /**
//     * お客様かどうか
//     *
//     * @return お客様の場合true
//     */
//    public boolean isConsumer() {
//        return HTypeInquiryRequestType.CONSUMER.equals(requestType);
//    }
//
//    /**
//     * 問合せ者TELが登録されているか
//     *
//     * @return 登録されている場合true
//     */
//    public boolean isExistsContactTel() {
//        return contactTel != null;
//    }
//
//}
