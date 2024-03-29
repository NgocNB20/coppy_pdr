/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;

import java.util.List;

/**
 * アンケートチェックロジックのインタフェースクラス。<br />
 * <pre>
 * アンケート管理画面の各処理から利用する。
 * </pre>
 * @author matsuda
 */
public interface QuestionnaireCheckLogic {

    /** 公開開始日時が現在より過去日の場合エラー */
    public static final String MSGCD_CANNOT_SET_OPENSTRATTIME = "PKG34-3552-101-L-W";

    /** アンケートキーと公開期間が既存のアンケートと重複した場合エラー */
    public static final String MSGCD_REPETITION_QESTIONNAIREKEY = "PKG34-3552-102-L-W";

    /** アンケート設問：公開状態がすべて「非公開」の場合エラー */
    public static final String MSGCD_NOTTHING_OPEN = "PKG34-3552-103-L-W";

    /** アンケート設問：選択肢に半角スラッシュがある場合エラー*/
    public static final String MSGCD_CHOICES_EXIST_SLASH = "PKG34-3552-104-L-W";

    /** アンケート設問：選択肢に重複がある場合エラー*/
    public static final String MSGCD_CHOICES_DUPLICATION = "PKG34-3552-105-L-W";

    /** アンケート設問：選択肢の上限件数を超えている場合エラー*/
    public static final String MSGCD_CHOICES_MAXCOUNT_OVER = "PKG34-3552-106-L-W";

    /** アンケート設問：選択肢毎の桁数上限を超えている場合エラー*/
    public static final String MSGCD_CHOICES_MAXLENGTH_OVER = "PKG34-3552-107-L-W";

    /** 公開中アンケートの変更不可項目を変更した場合エラー（アンケート設問の行番号表示なし）*/
    public static final String MSGCD_CANNOT_CHANGE = "PKG34-3552-108-L-W";

    /** 公開中アンケートの変更不可項目を変更した場合エラー（アンケート設問の行番号表示あり）*/
    public static final String MSGCD_CANNOT_CHANGE_QUESTION = "PKG34-3552-109-L-W";

    /** アンケート設問：選択肢の最大行数 */
    public static final int CHOICES_MAXCOUNT = 20;

    /** アンケート設問：選択肢１つあたりの最大桁数 */
    public static final int CHOICES_MAXLENGTH = 100;

    /**
     *新規登録・確認時アンケートチェック処理。<br />
     * <pre>
     *公開開始日時のチェック、アンケートキーと公開期間の重複チェックを行う。
     * </pre>
     *
     * @param questionnaire アンケート
     * @param questionList アンケート設問リスト
     */
    void checkForRegistAtConfirm(QuestionnaireEntity questionnaire, List<QuestionEntity> questionList);

    /**
     * 更新・確認時アンケートチェック処理。<br />
     * <pre>
     * 公開開始日時のチェック、アンケートキーと公開期間の重複チェック行う。
     * </pre>
     *
     * @param preUpdateQuestionnaire 更新前のアンケート
     * @param postUpdateQuestionnaire 更新後のアンケート
     * @param questionList 更新前のアンケート設問リスト
     * @param isUpdate 公開開始日時が変更可能の場合、true
     *
     */
    void checkForUpdateAtConfirm(QuestionnaireEntity preUpdateQuestionnaire,
                                 QuestionnaireEntity postUpdateQuestionnaire,
                                 List<QuestionEntity> questionList,
                                 boolean isUpdate);

    /**
     *新規登録・登録更新時アンケートチェック処理。<br />
     * <pre>
     *公開開始日時のチェック、アンケートキーと公開期間の重複チェックを行う。
     * </pre>
     *
     * @param questionnaire アンケート
     * @param questionList アンケート設問リスト
     */
    void checkForRegistAtOnceRegist(QuestionnaireEntity questionnaire, List<QuestionEntity> questionList);

    /**
     * 更新・登録更新時アンケートチェック処理。<br />
     * <pre>
     * 公開開始日時のチェック、アンケートキーと公開期間の重複チェック行う。
     * </pre>
     *
     * @param preUpdateQuestionnaire 更新前のアンケート
     * @param postUpdateQuestionnaire 更新後のアンケート
     * @param preQuestionList 更新前のアンケート設問リスト
     * @param postQuestionList 更新後のアンケート設問リスト
     * @param isUpdate 公開開始日時が変更可能の場合、true
     *
     */
    void checkForUpdateAtOnceRegist(QuestionnaireEntity preUpdateQuestionnaire,
                                    QuestionnaireEntity postUpdateQuestionnaire,
                                    List<QuestionEntity> preQuestionList,
                                    List<QuestionEntity> postQuestionList,
                                    boolean isUpdate);

    /**
     * アンケート情報が回答可能であることをチェックする。<br />
     * <dl>
     * <dt>回答可能
     * <dd>非公開状態/公開終了/削除/存在しないのいずれでもない<dd>
     * </dl>
     * @param questionnaireSeq ンケートSEQ
     * @return 回答可能なときtrueを返す
     */
    boolean checkQuestionaireReply(Integer questionnaireSeq);

}
