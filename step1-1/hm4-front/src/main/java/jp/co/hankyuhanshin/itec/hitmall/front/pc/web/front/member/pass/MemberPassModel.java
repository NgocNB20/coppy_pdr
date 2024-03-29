/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.pass;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVRStringEqual;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVRStringNotEqual;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.RegularExpressionsConstants;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.pass.validation.group.ValidCommonGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.pass.validation.group.ValidStringEqualGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.pass.validation.group.ValidStringNotEqualGroup;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * パスワード変更 Model
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
// PDR Migrate Customization from here
@EqualsAndHashCode(callSuper = false)
@HVRStringEqual(target = "memberInfoNewPassWordConfirm", comparison = "memberInfoNewPassWord",
                groups = ValidStringEqualGroup.class)
@HVRStringNotEqual(target = "memberInfoNewPassWordConfirm", comparison = "memberInfoPassWord",
                   message = "{HStringNotEqualValidator.EQUAL_detail}", groups = ValidStringNotEqualGroup.class)
// PDR Migrate Customization to here
@Data
public class MemberPassModel extends AbstractModel {

    /**
     * パスワード
     */
    @NotEmpty(groups = {ValidCommonGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ValidCommonGroup.class})
    @Pattern(regexp = RegularExpressionsConstants.PASSWORD_NUMBER_REGEX,
             message = "{HPasswordValidator.INVALID_detail}", groups = {ValidCommonGroup.class})
    private String memberInfoPassWord;

    /**
     * 新しいパスワード
     */
    @NotEmpty(groups = {ValidCommonGroup.class})
    @Pattern(regexp = RegularExpressionsConstants.PASSWORD_NUMBER_REGEX,
             message = "{HPasswordValidator.INVALID_detail}", groups = {ValidCommonGroup.class})
    private String memberInfoNewPassWord;

    // PDR Migrate Customization from here
    /**
     * 新しいパスワード(確認)
     */
    @NotEmpty(groups = {ValidCommonGroup.class})
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ValidCommonGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ValidCommonGroup.class})
    private String memberInfoNewPassWordConfirm;
    // PDR Migrate Customization to here

    /**
     * 会員情報エンティティ
     */
    private MemberInfoEntity memberInfoEntity;

}
