package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Component
@Scope("prototype")
public class QuestionnaireRegistUpdateModelItem implements Serializable {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 設問内容の最大文字列長　*/
    public static final int LENGTH_QUESTION_MAXIMUM = 200;

    /*
     * 画面項目（設問設定一覧）データ項目
     */
    /** 設問設定一覧データ：No */
    private Integer dspNo;

    /** 設問設定一覧データ：表示順 */
    private Integer orderDisplay;

    /** 設問設定一覧データ：設問内容 */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(max = LENGTH_QUESTION_MAXIMUM, groups = {ConfirmGroup.class})
    private String question;

    /** 設問設定一覧データ：公開状態 */
    @NotEmpty(groups = {ConfirmGroup.class})
    private String openStatus;

    /** 設問設定一覧データ：必須 */
    @NotEmpty(groups = {ConfirmGroup.class})
    private String replyRequiredFlag;

    /** 設問設定一覧データ：形式 */
    @NotEmpty(groups = {ConfirmGroup.class})
    private String replyType;

    /** 設問設定一覧データ：文字種 */
    private String replyValidatePattern;

    /** 設問設定一覧データ：桁数 */
    private String replyMaxLength;

    /** 設問設定一覧データ：選択肢 */
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String choices;

    /** 設問設定一覧データ：選択肢リスト（画面表示用） */
    private String[] choicesDispItems;

    /** 設問設定一覧データ：選択肢（画面表示用）  */
    private String choicesDisp;

    /** 設問設定一覧データ：必須（画面テキスト表示用）  */
    private String replyRequiredFlagDisp;

    /** 設問設定一覧データ：形式（画面テキスト表示用）  */
    private String replyTypeDisp;

    /** 設問設定一覧データ：文字種 （画面テキスト表示用） */
    private String replyValidatePatternDisp;
    /*
     * 画面項目以外
     */
    /** 設問設定一覧データ：アンケート設問SEQ */
    private Integer questionSeq;

    /**
     * 回答入力設定の変更可否<br />
     * <pre>
     * 回答入力設定が変更可能な場合、true。
     * </pre>
     */
    private boolean isUpdateQuestion;

}
