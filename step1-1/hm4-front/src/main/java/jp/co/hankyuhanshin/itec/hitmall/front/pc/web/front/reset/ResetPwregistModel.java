/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.reset;

import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVRStringEqual;
import jp.co.hankyuhanshin.itec.hitmall.front.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.front.base.constant.RegularExpressionsConstants;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.web.AbstractModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * パスワードリセット登録 Model
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
@HVRStringEqual(target = "memberInfoNewPassWordConfirm", comparison = "memberInfoNewPassWord")
public class ResetPwregistModel extends AbstractModel {

    /**
     * メールパラメータ<br/>
     */
    private String mid;

    /**
     * 会員情報<br/>
     */
    private MemberInfoEntity memberInfoEntity;

    /**
     * パスワード<br/>
     */
    @NotEmpty
    @Pattern(regexp = RegularExpressionsConstants.PASSWORD_NUMBER_REGEX,
             message = "{HPasswordValidator.INVALID_detail}")
    private String memberInfoNewPassWord;

    /**
     * パスワード確認<br/>
     */
    @NotEmpty
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE)
    @HVSpecialCharacter(allowPunctuation = true)
    private String memberInfoNewPassWordConfirm;

    /**
     * 有効なメールからの遷移かどうか。
     * ※デフォルトtrue。Controllerで正常処理実行時のみfalseが設定
     */
    private boolean errorUrl = true;

}
