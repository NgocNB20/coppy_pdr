package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * ログインモデル
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
public class LoginModel extends AbstractModel {

    /**
     * 管理者ID<br/>
     */
    @NotEmpty
    @Length(min = 1, max = 20)
    @HCHankaku
    private String administratorId;

    /**
     * 管理者パスワード<br/>
     */
    @NotEmpty
    @HVSpecialCharacter(allowPunctuation = true)
    private String administratorPassWord;

    /**
     * パスワード再設定リンクを表示するか<br/>
     *
     * @return true:表示する / false:表示しない
     */
    public boolean isPwdResetDisplayFlag() {
        int pwdResetDisplayVal = PropertiesUtil.getSystemPropertiesValueToInt("admin.password.reset.display");
        return pwdResetDisplayVal != 0;
    }

}
