/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.inquiry;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCZenkaku;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCZenkakuKana;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVMailAddress;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.RegularExpressionsConstants;
import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.ValidatorConstants;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Map;

/**
 * お問い合わせ Model
 *
 * @author kaneda
 */
@Data
public class InquiryModel extends AbstractModel {

    /**
     * お問い合わせ分類
     */
    @NotEmpty(message = "{HRequiredValidator.REQUIRED_detail}")
    private String inquiryGroup;

    /**
     * お問い合わせ分類名
     */
    private String inquiryGroupName;

    /**
     * お問い合わせ分類リスト
     */
    private Map<String, String> inquiryGroupItems;

    /**
     * 氏名（姓）
     */
    @NotEmpty
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}")
    @Length(min = 1, max = 16)
    @HCZenkaku
    private String inquiryLastName;

    /**
     * 氏名（名）
     */
    @NotEmpty
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_REGEX, message = "{HZenkakuValidator.INVALID_detail}")
    @Length(min = 1, max = 16)
    @HCZenkaku
    private String inquiryFirstName;

    /**
     * フリガナ（セイ）
     */
    @NotEmpty
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_KANA_REGEX,
             message = "{HZenkakuKanaValidator.INVALID_detail}")
    @Length(min = 1, max = 40)
    @HCZenkakuKana
    private String inquiryLastKana;

    /**
     * フリガナ（メイ）
     */
    @NotEmpty
    @Pattern(regexp = RegularExpressionsConstants.ZENKAKU_KANA_REGEX,
             message = "{HZenkakuKanaValidator.INVALID_detail}")
    @Length(min = 1, max = 40)
    @HCZenkakuKana
    private String inquiryFirstKana;

    /**
     * メールアドレス
     */
    @NotEmpty
    @HVMailAddress
    @Length(min = 1, max = 160)
    @HCHankaku
    private String inquiryMail;

    /**
     * 電話番号
     */
    @NotEmpty
    @Pattern(regexp = "[\\d]*$")
    @Length(min = 1, max = 11)
    @HCHankaku
    private String inquiryTel;

    /**
     * 問い合わせ内容
     */
    @NotEmpty
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(min = 1, max = ValidatorConstants.LENGTH_INQUIRYBODY_MAXIMUM)
    @HCZenkaku
    private String inquiryBody;

    /**
     * 商品コード
     */
    private String gcd;

    /**
     * 商品管理コード
     */
    private String ggcd;

    /**
     * 規格1
     */
    private String redirectU1Lbl;

    /**
     * 規格2
     */
    private String unitSelect2Label;

    /**
     * 商品グループ名
     */
    private String goodsGroupName;

    /**
     * 規格タイトル１
     */
    private String unitTitle1;

    /**
     * 規格タイトル２
     */
    private String unitTitle2;
}
