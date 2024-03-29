/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.delete;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVMailAddress;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.RegularExpressionsConstants;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 会員登録解除 model
 *
 * @author kimura
 */
@Data
public class MemberDeleteModel extends AbstractModel {

    /**
     * 会員ID
     */
    @NotEmpty
    @HVMailAddress
    @Length(min = 1, max = 160)
    @HCHankaku
    private String memberInfoId;

    /**
     * 会員パスワード
     */
    @NotEmpty
    @HVSpecialCharacter(allowPunctuation = true)
    private String memberInfoPassWord;
}
