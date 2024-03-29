package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ConfirmGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVBothSideSpace;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRDateCombo;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRItems;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRSeparateDateTime;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.SpaceValidateMode;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hmbase.constant.ValidatorConstants;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

@Data
@HVRSeparateDateTime(targetDate = "openStartDate", targetTime = "openStartTime", groups = {ConfirmGroup.class})
@HVRSeparateDateTime(targetDate = "openEndDate", targetTime = "openEndTime", groups = {ConfirmGroup.class})
@HVRDateCombo(dateLeftTarget = "openStartDate", timeLeftTarget = "openStartTime", dateRightTarget = "openEndDate",
              timeRightTarget = "openEndTime", groups = {ConfirmGroup.class})
// PDR Migrate Customization from here
@HVRItems(target = "siteMapFlag", comparison = "siteMapFlagItems")
// PDR Migrate Customization to here
public class QuestionnaireRegistUpdateModel extends AbstractModel {

    /**
     * アンケート表示名PCの最大文字列長
     */
    public static final int LENGTH_NAME_PC_MAXIMUM = 100;

    /**
     * アンケート表示名モバイルの最大文字列長
     */
    public static final int LENGTH_NAME_MB_MAXIMUM = 100;

    /**
     * 説明文PCの最大文字列長
     */
    public static final int LENGTH_FREE_TEXT_PC_MAXIMUM = 20000;

    /**
     * 回答完了文PCの最大文字列長
     */
    public static final int LENGTH_COMPLETE_TEXT_PC_MAXIMUM = 20000;

    /**
     * 設問内容の最大文字列長
     */
    public static final int LENGTH_QUESTION_MAXIMUM = 200;

    /*
     * 画面項目（基本共通設定）
     */

    /**
     * アンケートキー
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @Length(max = ValidatorConstants.LENGTH_QUESTIONNAIRE_KEY_MAXIMUM, groups = {ConfirmGroup.class})
    @Pattern(regexp = ValidatorConstants.REGEX_QUESTIONNAIREKEY,
             message = ValidatorConstants.MSGCD_QUESTIONNAIREKEY_INVALID, groups = {ConfirmGroup.class})
    @HCHankaku
    private String questionnaireKey;

    /**
     * アンケートSEQ
     */
    private Integer questionnaireSeq;

    /**
     * アンケート名称
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    @Length(max = ValidatorConstants.LENGTH_QUESTIONNAIRE_NAME_MAXIMUM, groups = {ConfirmGroup.class})
    private String name;

    /**
     * アンケート公開開始日
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    @HCDate
    private String openStartDate;

    /**
     * アンケート公開開始時間
     */
    @HCDate(pattern = DateUtility.HMS)
    private String openStartTime;

    /**
     * アンケート公開終了日
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowCharacters = '/', groups = {ConfirmGroup.class})
    @HCDate
    private String openEndDate;

    /**
     * アンケート公開終了時間
     */
    @HCDate(pattern = DateUtility.HMS)
    private String openEndTime;

    /**
     * サイトマップ出力
     */
    // PDR Migrate Customization from here
    @NotEmpty(groups = {ConfirmGroup.class})
    private String siteMapFlag;
    // PDR Migrate Customization to here

    /**
     * 管理メモ
     */
    @Length(max = ValidatorConstants.LENGTH_MANAGEMENT_MEMO_MAXIMUM)
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String memo;

    /*
     * 画面項目（基本サイト別設定）
     */

    /**
     * アンケート表示名PC
     */
    @Length(max = LENGTH_NAME_PC_MAXIMUM)
    private String namePc;

    /**
     * アンケート公開状態PC
     */
    @NotEmpty(groups = {ConfirmGroup.class})
    private String openStatusPc;

    /**
     * アンケート説明文PC
     */
    @Length(max = LENGTH_FREE_TEXT_PC_MAXIMUM, groups = {ConfirmGroup.class})
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true, groups = {ConfirmGroup.class})
    private String freeTextPc;

    /**
     * アンケート回答完了文PC
     */
    @Length(max = LENGTH_COMPLETE_TEXT_PC_MAXIMUM, groups = {ConfirmGroup.class})
    @HVBothSideSpace(startWith = SpaceValidateMode.ALLOW_SPACE, endWith = SpaceValidateMode.ALLOW_SPACE,
                     groups = {ConfirmGroup.class})
    @HVSpecialCharacter(allowPunctuation = true)
    private String completeTextPc;

    /*
     * 画面項目（設問設定一覧）データリスト
     */

    /**
     * 設問設定 リスト
     */
    @Valid
    private List<QuestionnaireRegistUpdateModelItem> indexPageItems;

    /*
     * 画面項目（設問設定一覧）データ項目
     */

    private Integer dspNo;

    private Integer orderDisplay;

    @NotEmpty
    @HVSpecialCharacter(allowPunctuation = true)
    @Length(max = LENGTH_QUESTION_MAXIMUM)
    private String question;

    @NotEmpty
    private String openStatus;

    @NotEmpty
    private String replyRequiredFlag;

    @NotEmpty
    private String replyType;

    private String replyValidatePattern;

    @HCHankaku
    private String replyMaxLength;

    private String choices;

    /**
     *
     */
    private String[] choicesDispItems;

    /**
     *
     */
    private String choicesDisp;

    /**
     *
     */
    private String replyRequiredFlagDisp;

    /**
     *
     */
    private String replyTypeDisp;

    /**
     *
     */
    private String replyValidatePatternDisp;

    /*
     * 表示項目以外
     */

    /**
     * 選択表示番号
     */
    private Integer selectDspNo;

    /**
     * アンケート状態 class
     */
    private String questionnaireStatusClass;

    private Integer questionSeq;

    private boolean isUpdateQuestion;

    /*
     * プルダウンリスト
     */

    /**
     * アンケート公開状態PCリスト <br />
     * <pre>
     * sqlMap.diconの「公開状態（2択）」から設定
     * </pre>
     */
    private Map<String, String> openStatusPcItems;

    // PDR Migrate Customization from here
    /**
     * サイトマップ出力
     */
    private Map<String, String> siteMapFlagItems;
    // PDR Migrate Customization to here

    /**
     * 設問設定一覧データ：公開状態リスト  <br />
     * <pre>
     * sqlMap.diconの「公開状態（2択）」から設定
     * </pre>
     */
    private Map<String, String> openStatusItems;

    /**
     * 設問設定一覧データ：必須リスト
     */
    private Map<String, String> replyRequiredFlagItems;

    /**
     * 設問設定一覧データ：形式リスト
     */
    private Map<String, String> replyTypeItems;

    /**
     * 設問設定一覧データ：文字種リスト
     */
    private Map<String, String> replyValidatePatternItems;

    /**
     * 変更前のアンケート情報。<br />
     * <pre>
     * 変更前後での差分を確認するために使用。
     * </pre>
     */
    private QuestionnaireEntity preUpdateQuestionnaire;

    /**
     * 変更前のアンケート設問情報。<br />
     * <pre>
     * 変更前後での差分を確認するために使用。
     * </pre>
     */
    private QuestionEntity preUpdateQuestion;

    /**
     * 変更前
     * のアンケート設問情報リスト。<br />
     * <pre>
     * 変更前後での差分を確認するために使用。
     * </pre>
     */
    private List<QuestionEntity> preUpdateQuestionList;

    /**
     * 変更後のアンケート情報。<br />
     * <pre>
     * 変更前後での差分を確認するために使用。
     * </pre>
     */
    private QuestionnaireEntity postUpdateQuestionnaire;

    /**
     * 変更後のアンケート設問情報。<br />
     * <pre>
     * 変更前後での差分を確認するために使用。
     * </pre>
     */
    private QuestionEntity postUpdateQuestion;

    /**
     * 変更後のアンケート設問情報リスト。<br />
     * <pre>
     * 変更前後での差分を確認するために使用。
     * </pre>
     */
    private List<QuestionEntity> postUpdateQuestionList;

    /**
     * 前画面が確認画面であるかを判断するフラグ<br/>
     * true:確認画面
     */
    private boolean fromConfirm;

    /**
     * 確認画面遷移時に終了時間を変更したかを判断
     */

    private boolean isChangeEndTime;

    /**
     * 公開開始日時が更新かを判断。<br />
     * <pre>
     * 更新時に公開開始日時が変更可能かどうかを判断する。
     * </pre>
     */
    private boolean isUpdateOpenStartTime;

    /**
     * 登録更新画面描画時にパラメータで渡されたSeq<br />
     * 　画面描画時の画面情報と登録情報に差異がないかをチェックするのに利用する
     */
    private Integer scSeq;

    /**
     * 登録内容描画時にDBから取得したSeq<br />
     * 画面描画時の画面情報と登録情報に差異がないかをチェックするのに利用する
     */
    private Integer dbSeq;

    /**
     * 登録更新画面に表示するデータのシーケンス<br/>
     * <p>
     * [注意]実装時に@GetParameters("seq")を各実装pageクラスでつける必要がある<br />
     */
    private Integer seq;

    /**
     * アンケートが公開中かを判断するためのフラグ。<br />
     * <pre>
     * 変更確認ダイアログを表示するために使用する。
     * </pre>
     */
    private boolean isOpen;

    private List<String> modifiedQuestionnaireList;

    private List<String> modifiedQuestionList;
    /*
     * 条件判断
     */

    /**
     * 登録か更新かを判断する。<br />
     * <pre>
     * 新規登録時と更新時でチェックを切り分けるため。
     * </pre>
     *
     * @return 新規登録時にtrueを返す
     */
    public boolean isRegistQ() {
        return questionnaireSeq == null;
    }

    /**
     * 登録か更新かを判断する。<br />
     * <pre>
     * 新規登録と更新で行うチェックを切り分けるため。
     * </pre>
     *
     * @return 更新時にtrueを返す
     */
    public boolean isUpdateQ() {
        return questionnaireSeq != null;

    }

    /**
     * 確認ダイアログを表示するかを判断する。<br />
     * <pre>
     * 開催中のアンケート情報変更時に確認を促すために使用する。
     * </pre>
     *
     * @return ダイアログを表示する必要がある場合trueを返す
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * 変更の可否を判断する。<br />
     * <pre>
     * 変更可能のときは「更新」ボタンを表示。
     * 変更不可のときは「変更不可」イメージを表示。
     * </pre>
     *
     * @return 変更可能な場合にtrueを返す
     */
    public boolean isUpdateOpenStartTime() {
        return isUpdateOpenStartTime;
    }

    /**
     * 変更の可否を判断する。<br />
     * <pre>
     * 変更可能のときは「更新」ボタンを表示。
     * 変更不可のときは「変更不可」イメージを表示。
     * </pre>
     *
     * @return 変更可能な場合にtrueを返す
     */
    public boolean isUpdateQuestion() {
        return isUpdateQuestion;
    }

    /**
     * アンケート設問の削除可能判定<br/>
     * アンケート設問が1件の場合は削除不可
     *
     * @return true=削除可能
     */
    public boolean isQuestionDeletable() {
        return CollectionUtil.getSize(indexPageItems) > 1;
    }

    /**
     * アンケート設問の追加可能判定<br/>
     * アンケート設問が上限数以上の場合は追加不可
     *
     * @return true=追加可能
     */
    public boolean isQuestionAddable() {
        int max = PropertiesUtil.getSystemPropertiesValueToInt("question.regist.maxcount");
        return CollectionUtil.getSize(indexPageItems) < max;
    }

    public String isDiffItems(String field, int index) {
        if (isUpdateQ()) {
            QuestionEntity questionEntity = postUpdateQuestionList.get(index);
            if (questionEntity.getQuestionSeq() == null) {
                return "diff";
            }
            String fieldCompare = "ArrayList[" + index + "]." + field;
            if (modifiedQuestionList.contains(fieldCompare)) {
                return "diff";
            }
        }

        return "";
    }
}
