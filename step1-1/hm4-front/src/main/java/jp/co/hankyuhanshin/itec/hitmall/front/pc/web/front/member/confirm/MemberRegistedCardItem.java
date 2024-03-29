// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.confirm;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 登録済みカード情報Itemクラス
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Data
@Component
@Scope("prototype")
public class MemberRegistedCardItem implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** カードID */
    private String cardId;

    /** カード会社 */
    private String cardBrand;

    /** カード番号 */
    private String cardNumber;

    /** 有効期限（月） */
    private String expirationDateMonth;

    /** 有効期限（年） */
    private String expirationDateYear;
}
// PDR Migrate Customization to here