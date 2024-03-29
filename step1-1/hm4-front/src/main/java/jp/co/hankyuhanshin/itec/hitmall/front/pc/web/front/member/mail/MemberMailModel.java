/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.mail;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVMailAddress;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVRStringEqual;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVRStringNotEqual;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * メールアドレス変更 Model
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
@HVRStringNotEqual(target = "memberInfoNewMail", comparison = "memberInfoMail",
                   message = "{HStringNotEqualValidator.EQUAL_MAIL_detail}")
// PDR Migrate Customization from here
@HVRStringEqual(target = "memberInfoNewMailConfirm", comparison = "memberInfoNewMail")
@HVRStringNotEqual(target = "memberInfoNewMailConfirm", comparison = "memberInfoMail",
                   message = "{HStringNotEqualValidator.EQUAL_detail}")
// PDR Migrate Customization to here
public class MemberMailModel extends AbstractModel {

    /**
     * 現メールアドレス<br/>
     */
    private String memberInfoMail;

    /**
     * 新しいメールアドレス<br/>
     */
    @NotEmpty
    @HVMailAddress
    // 2023-renew No85-1 from here
    @Length(min = 1, max = 256)
    // 2023-renew No85-1 to here
    private String memberInfoNewMail;

    // PDR Migrate Customization from here
    /**
     * 新しいメールアドレス(確認)<br/>
     */
    @NotEmpty
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    @HVSpecialCharacter(allowPunctuation = true)
    private String memberInfoNewMailConfirm;
    // PDR Migrate Customization to here
}
