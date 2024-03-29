/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.reset;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVMailAddress;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVRSeparateDate;
import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.RegularExpressionsConstants;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * パスワードリセット Model
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
@HVRSeparateDate(targetYear = "memberInfoBirthdayYear", targetMonth = "memberInfoBirthdayMonth",
                 targetDate = "memberInfoBirthdayDay")
public class ResetModel extends AbstractModel {

    /**
     * 会員メール<br/>
     */
    @NotEmpty
    @HVMailAddress
    // 2023-renew No85-1 from here
    @Length(min = 1, max = 256)
    // 2023-renew No85-1 to here
    @HCHankaku
    private String memberInfoMail;

    // PDR Migrate Customization from here
    /**
     * 生年月日(年)<br/>
     */
    @Length(min = 1, max = 4)
    @HCDate(pattern = "yyyy")
    private String memberInfoBirthdayYear;

    /**
     * 生年月日(月)<br/>
     */
    @Length(min = 1, max = 2)
    @HCDate(pattern = "MM")
    private String memberInfoBirthdayMonth;

    /**
     * 生年月日(日)<br/>
     */
    @Length(min = 1, max = 2)
    @HCDate(pattern = "dd")
    private String memberInfoBirthdayDay;

    /** 会員TEL */
    @NotEmpty @Pattern(regexp = RegularExpressionsConstants.TELEPHONE_NUMBER_REGEX,
                       message = "{HTelephoneNumberValidator.INVALID_detail}")
    // 2023-renew No85-1 from here
    @Length(min = 6, max = 14)
    @HCHankaku
    private String memberInfoTelOrCustomerNo;
    // 2023-renew No85-1 to here
    // PDR Migrate Customization to here

}
