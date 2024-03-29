package jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;

import java.util.List;

/**
 * アンケート更新ロジックのインタフェースクラス。<br />
 * <pre>
 * アンケート管理画面の更新処理から利用する。
 * </pre>
 *
 * @author vinh.nv

 */
public interface QuestionnaireUpdateLogic {

    /** 修正対象のクーポンが事前に修正されていた場合エラー（排他） */
    public static final String MSGCD_EXCLUSION_ERROR = "PKG34-3552-201-L-E";

    /**
     * アンケート更新処理。<br />
     * <pre>
     * アンケート情報を元にアンケートテーブル更新反映。
     * アンケート設問テーブル更新反映。
     * </pre>
     *
     * @param questionnaire 更新対象のアンケート
     * @param prequestionList 更新前のアンケート設問リスト
     * @param postquestionList 更新後のアンケート設問リスト
     */

    void execute(QuestionnaireEntity questionnaire,
                 List<QuestionEntity> prequestionList,
                 List<QuestionEntity> postquestionList);

    /**
     * アンケート更新処理。<br />
     * <pre>
     * アンケート情報を元にアンケートテーブル更新反映。
     * アンケート設問テーブル更新反映。
     * </pre>
     *
     * @param questionnaire 更新対象のアンケート
     * @param questionList 更新前のアンケート設問リスト
     */
    void execute(QuestionnaireEntity questionnaire, List<QuestionEntity> questionList);
}
