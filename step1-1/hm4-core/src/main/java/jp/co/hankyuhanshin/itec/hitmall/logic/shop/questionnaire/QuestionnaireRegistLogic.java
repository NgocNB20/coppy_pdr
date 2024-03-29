package jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionReplyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireReplyEntity;

import java.util.List;

/**
 * アンケート新規登録ロジックのインタフェースクラス。<br />
 * <pre>
 * アンケート管理画面の登録処理から利用する。
 * </pre>
 *
 * @author vinh.nv
 */
public interface QuestionnaireRegistLogic {
    /**
     * アンケート新規登録処理。<br />
     * <pre>
     * アンケート情報を元にアンケートテーブル、アンケート設問テーブル新規反映。
     * </pre>
     *
     * @param questionnaire 登録対象のアンケート
     * @param question      登録対象のアンケート設問
     */

    void execute(QuestionnaireEntity questionnaire, List<QuestionEntity> question);

    /**
     * アンケート回答新規登録処理。<br />
     * <pre>
     * アンケート回答情報を元にアンケート回答テーブル、アンケート設問回答テーブル新規反映。
     * </pre>
     *
     * @param questionnaireReplyEntity 登録対象のアンケート回答情報
     * @param questionReplyEntityList  登録対象のアンケート設問回答情報リスト
     */

    void registReplyEntity(QuestionnaireReplyEntity questionnaireReplyEntity,
                           List<QuestionReplyEntity> questionReplyEntityList);
}
