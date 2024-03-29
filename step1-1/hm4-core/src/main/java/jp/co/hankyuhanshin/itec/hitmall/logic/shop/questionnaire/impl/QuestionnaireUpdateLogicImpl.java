package jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire.QuestionDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire.QuestionnaireDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.QuestionnaireUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * アンケート更新ロジックの実装クラス。<br />
 */
@Component
public class QuestionnaireUpdateLogicImpl extends AbstractShopLogic implements QuestionnaireUpdateLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionnaireUpdateLogicImpl.class);

    /**
     * アンケートDAO
     */

    private final QuestionnaireDao questionnaireDao;

    /**
     * アンケート設問DAO
     */
    private final QuestionDao questionDao;

    /**
     * DateUtility
     */
    private final DateUtility dateUtility;

    @Autowired
    public QuestionnaireUpdateLogicImpl(QuestionnaireDao questionnaireDao,
                                        QuestionDao questionDao,
                                        DateUtility dateUtility) {
        this.questionnaireDao = questionnaireDao;
        this.questionDao = questionDao;
        this.dateUtility = dateUtility;
    }

    /**
     * アンケート更新処理。
     *
     * @param questionnaireEntity    更新対象のアンケート情報
     * @param prequestionEntityList  更新対象のアンケート設問情報リスト
     * @param postquestionEntityList 更新対象のアンケート設問情報リスト
     */
    @Override
    public void execute(QuestionnaireEntity questionnaireEntity,
                        List<QuestionEntity> prequestionEntityList,
                        List<QuestionEntity> postquestionEntityList) {
        QuestionnaireEntity updateEntity = CopyUtil.deepCopy(questionnaireEntity);

        Timestamp currentTime = dateUtility.getCurrentTime();

        // アンケートテーブルに更新を行う
        updateQuestionnaireEntity(updateEntity, currentTime);

        // アンケート設問テーブルに更新を行う
        updateQuestionEntity(updateEntity, prequestionEntityList, postquestionEntityList, currentTime);

    }

    /**
     * アンケート削除処理。
     *
     * @param questionnaireEntity 更新対象のアンケート情報
     * @param questionEntityList  更新対象のアンケート設問情報リスト
     */
    @Override
    public void execute(QuestionnaireEntity questionnaireEntity, List<QuestionEntity> questionEntityList) {
        QuestionnaireEntity updateEntity = CopyUtil.deepCopy(questionnaireEntity);

        Timestamp currentTime = dateUtility.getCurrentTime();

        // アンケートテーブルに更新を行う
        updateQuestionnaireEntity(updateEntity, currentTime);

        // アンケート設問テーブルに更新(削除＋追加)を行う
        updateQuestionEntity(updateEntity, questionEntityList, currentTime);

    }

    /**
     * アンケート設問テーブルに更新を行う<br/>
     *
     * @param questionnaireEntity    更新対象のアンケート情報
     * @param prequestionEntityList  更新前のアンケート設問情報
     * @param postquestionEntityList 更新後のアンケート設問情報
     * @param currentTime            現在日時
     */
    protected void updateQuestionEntity(QuestionnaireEntity questionnaireEntity,
                                        List<QuestionEntity> prequestionEntityList,
                                        List<QuestionEntity> postquestionEntityList,
                                        Timestamp currentTime) {

        // アンケート設問エンティティを更新/追加/削除対象に振り分け
        List<QuestionEntity> updatePreQuestionList = new ArrayList<>();
        List<QuestionEntity> updatePostQuestionList = new ArrayList<>();
        List<QuestionEntity> deleteQuestionList = prequestionEntityList;
        List<QuestionEntity> insertQuestionList = postquestionEntityList;

        for (QuestionEntity prequestionEntity : prequestionEntityList) {
            for (QuestionEntity postquestionEntity : postquestionEntityList) {

                if (prequestionEntity.getQuestionSeq().equals(postquestionEntity.getQuestionSeq())) {
                    // 更新対象リスト（更新処理・削除対象リスト生成に使用）
                    updatePreQuestionList.add(prequestionEntity);
                    // 更新対象リスト(追加対象リスト生成のみに使用)
                    updatePostQuestionList.add(postquestionEntity);
                }

            }
        }
        // 削除対象リスト
        deleteQuestionList.removeAll(updatePreQuestionList);
        // 追加対象リスト
        insertQuestionList.removeAll(updatePostQuestionList);

        // 更新処理
        for (QuestionEntity questionEntity : updatePostQuestionList) {

            try {

                // アンケート設問情報更新日時をセットする
                questionEntity.setUpdateTime(currentTime);

                // アンケート設問テーブルを更新する
                questionDao.update(questionEntity);
            } catch (Exception e) {
                // 更新対象が他の人に更新されていたので排他エラーを投げる
                LOGGER.error("例外処理が発生しました", e);
                throwMessage(MSGCD_EXCLUSION_ERROR);
            }
        }

        // 削除処理
        for (QuestionEntity questionEntity : deleteQuestionList) {

            if (questionEntity.getQuestionSeq() != null) {
                try {
                    // アンケート設問テーブルにアンケート情報を削除する
                    questionDao.delete(questionEntity);
                } catch (Exception e) {
                    // 更新対象が他の人に更新されていたので排他エラーを投げる
                    LOGGER.error("例外処理が発生しました", e);
                    throwMessage(MSGCD_EXCLUSION_ERROR);
                }
            }
        }

        // 追加処理
        for (QuestionEntity questionEntity : insertQuestionList) {

            // アンケート設問情報に登録日時・更新日時をセットする
            questionEntity.setRegistTime(currentTime);
            questionEntity.setUpdateTime(currentTime);

            try {
                // アンケート設問テーブルに追加する
                questionDao.insert(questionEntity);
            } catch (Exception e) {
                // 更新対象が他の人に更新されていたので排他エラーを投げる
                LOGGER.error("例外処理が発生しました", e);
                throwMessage(MSGCD_EXCLUSION_ERROR);
            }
        }

    }

    /**
     * アンケートテーブルに更新を行う<br/>
     *
     * @param questionnaireEntity 更新対象のアンケート情報
     * @param currentTime         現在日時
     */
    protected void updateQuestionnaireEntity(QuestionnaireEntity questionnaireEntity, Timestamp currentTime) {

        // アンケート設問情報に更新日時をセットする
        questionnaireEntity.setUpdateTime(currentTime);

        // アンケートテーブルにアンケート情報を更新する
        int count = questionnaireDao.update(questionnaireEntity);
        if (count < 1) {
            // 更新対象が他の人に更新されていたので排他エラーを投げる
            throwMessage(MSGCD_EXCLUSION_ERROR);
        }
    }

    /**
     * アンケート設問テーブルに更新を行う<br/>
     *
     * @param questionnaireEntity 更新対象のアンケート情報
     * @param questionEntityList  更新対象のアンケート設問情報
     * @param currentTime         現在日時
     */
    protected void updateQuestionEntity(QuestionnaireEntity questionnaireEntity,
                                        List<QuestionEntity> questionEntityList,
                                        Timestamp currentTime) {

        for (QuestionEntity questionEntity : questionEntityList) {

            // アンケート設問情報に更新日時をセットする
            questionEntity.setUpdateTime(currentTime);

            try {
                // アンケート設問テーブルにアンケート情報を更新する
                questionDao.update(questionEntity);
            } catch (Exception e) {
                // 更新対象が他の人に更新されていたので排他エラーを投げる
                LOGGER.error("例外処理が発生しました", e);
                throwMessage(MSGCD_EXCLUSION_ERROR);
            }
        }

    }
}
