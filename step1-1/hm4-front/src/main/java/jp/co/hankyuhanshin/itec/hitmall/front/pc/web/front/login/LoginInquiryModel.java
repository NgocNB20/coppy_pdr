// 廃止不要機能
///*
// * Project Name : HIT-MALL4
// *
// * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
// *
// */
//
//package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.login;
//
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.Pattern;
//
//import org.hibernate.validator.constraints.Length;
//
//import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCHankaku;
//import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.RegularExpressionsConstants;
//import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.ValidatorConstants;
//import lombok.Data;
//
///**
// * 問合せ認証Model
// *
// * @author kaneda
// */
//@Data
//public class LoginInquiryModel extends AbstractModel {
//
//    /**
//     * URLパラメータ：お問い合わせ番号<br>
//     * ゲスト問合せ詳細画面にパラメータを引き継ぐ
//     */
//    private String icd;
//
//    /**
//     * お問い合わせ番号
//     */
//    @NotEmpty
//    @Length(min = 1, max = 12)
//    @Pattern(regexp = ValidatorConstants.REGEX_INQUIRY_CODE)
//    private String inquiryCode;
//
//    /**
//     * 電話番号
//     */
//    @NotEmpty
//    @Pattern(regexp = RegularExpressionsConstants.TELEPHONE_NUMBER_REGEX, message = "{HTelephoneNumberValidator.INVALID_detail}")
//    @Length(min = 1, max = 11)
//    @HCHankaku
//    private String inquiryTel;
//}
