package jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire.QuestionDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire.QuestionReplyDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire.QuestionnaireDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire.QuestionnaireReplyDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionReplyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireReplyEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.QuestionnaireRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * アンケート新規登録ロジックの実装クラス。<br />
 *
 * @author vinh.nv
 */
@Component
public class QuestionnaireRegistLogicImpl extends AbstractShopLogic implements QuestionnaireRegistLogic {

    /**
     * アンケートDAO
     */
    private final QuestionnaireDao questionnaireDao;

    /**
     * アンケート設問DAO
     */
    private final QuestionDao questionDao;

    /**
     * アンケートDAO
     */
    private final QuestionnaireReplyDao questionnaireReplyDao;

    /**
     * アンケート設問DAO
     */
    private final QuestionReplyDao questionReplyDao;

    @Autowired
    public QuestionnaireRegistLogicImpl(QuestionnaireDao questionnaireDao,
                                        QuestionDao questionDao,
                                        QuestionReplyDao questionReplyDao,
                                        QuestionnaireReplyDao questionnaireReplyDao) {
        this.questionnaireDao = questionnaireDao;
        this.questionDao = questionDao;
        this.questionReplyDao = questionReplyDao;
        this.questionnaireReplyDao = questionnaireReplyDao;
    }

    /**
     * アンケート新規登録処理。
     *
     * @param questionnaireEntity 登録対象のアンケート情報
     * @param questionEntityList  登録対象のアンケート設問情報リスト
     */
    @Override
    public void execute(QuestionnaireEntity questionnaireEntity, List<QuestionEntity> questionEntityList) {

        // 現在日時を取得する
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        Timestamp currentTime = dateUtility.getCurrentTime();

        // アンケートテーブルに新規登録を行う
        registQuestionnaireEntity(questionnaireEntity, currentTime);

        // アンケート設問テーブルに新規登録を行う
        registQuestionEntity(questionnaireEntity, questionEntityList, currentTime);

    }

    /**
     * アンケートテーブルに新規登録を行う<br/>
     *
     * @param questionnaireEntity 登録対象のアンケート情報
     * @param currentTime         現在日時
     */
    protected void registQuestionnaireEntity(QuestionnaireEntity questionnaireEntity, Timestamp currentTime) {

        // アンケート情報にshopSEQをセットする
        questionnaireEntity.setShopSeq(1001);

        // アンケート情報に登録日時・更新日時をセットする
        questionnaireEntity.setRegistTime(currentTime);
        questionnaireEntity.setUpdateTime(currentTime);

        // アンケートテーブルにアンケート情報を登録する
        questionnaireDao.insert(questionnaireEntity);
    }

    /**
     * アンケート設問テーブルに新規登録を行う<br/>
     *
     * @param questionnaireEntity 登録対象のアンケート情報
     * @param questionEntityList  登録対象のアンケート設問情報リスト
     * @param currentTime         現在日時
     */
    protected void registQuestionEntity(QuestionnaireEntity questionnaireEntity,
                                        List<QuestionEntity> questionEntityList,
                                        Timestamp currentTime) {

        for (QuestionEntity questionEntity : questionEntityList) {

            // アンケート設問情報にアンケートSEQをセットする
            questionEntity.setQuestionnaireSeq(questionnaireEntity.getQuestionnaireSeq());

            // アンケート設問情報に登録日時・更新日時をセットする
            questionEntity.setRegistTime(currentTime);
            questionEntity.setUpdateTime(currentTime);

            // アンケート設問テーブルにアンケート情報を登録する
            questionDao.insert(questionEntity);
        }

    }

    /**
     * アンケート回答新規登録処理。
     *
     * @param questionnaireReplyEntity 登録対象のアンケート回答情報
     * @param questionReplyEntityList  登録対象のアンケート設問回答情報リスト
     */
    @Override
    public void registReplyEntity(QuestionnaireReplyEntity questionnaireReplyEntity,
                                  List<QuestionReplyEntity> questionReplyEntityList) {

        // 現在日時を取得する
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        Timestamp currentTime = dateUtility.getCurrentTime();

        // アンケートテーブルに新規登録を行う
        registQuestionnaireReplyEntity(questionnaireReplyEntity, currentTime);

        // アンケート設問テーブルに新規登録を行う
        registQuestionReplyEntity(questionnaireReplyEntity, questionReplyEntityList, currentTime);

    }

    /**
     * アンケート回答テーブルに新規登録を行う<br/>
     *
     * @param questionnaireReplyEntity 登録対象のアンケート回答情報
     * @param currentTime              現在日時
     */
    protected void registQuestionnaireReplyEntity(QuestionnaireReplyEntity questionnaireReplyEntity,
                                                  Timestamp currentTime) {

        // 登録日時をセットする
        questionnaireReplyEntity.setRegistTime(currentTime);

        // アンケート回答テーブルにアンケート回答情報を登録する
        questionnaireReplyDao.insert(questionnaireReplyEntity);
    }

    /**
     * アンケート設問テーブルに新規登録を行う<br/>
     *
     * @param questionnaireReplyEntity 登録対象のアンケート回答情報
     * @param questionReplyEntityList  登録対象のアンケート設問回答情報リスト
     * @param currentTime              現在日時
     */
    protected void registQuestionReplyEntity(QuestionnaireReplyEntity questionnaireReplyEntity,
                                             List<QuestionReplyEntity> questionReplyEntityList,
                                             Timestamp currentTime) {

        for (QuestionReplyEntity questionReplyEntity : questionReplyEntityList) {

            // アンケート回答SEQ をセットする
            questionReplyEntity.setQuestionnaireReplySeq(questionnaireReplyEntity.getQuestionnaireReplySeq());

            // 登録日時をセットする
            questionReplyEntity.setRegistTime(currentTime);

            // アンケート設問回答テーブルにアンケート設問回答情報を登録する
            questionReplyDao.insert(questionReplyEntity);

        }
    }
}
