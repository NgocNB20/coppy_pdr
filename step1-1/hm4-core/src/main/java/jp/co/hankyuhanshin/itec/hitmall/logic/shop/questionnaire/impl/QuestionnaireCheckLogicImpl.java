/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReplyType;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire.QuestionnaireDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.QuestionnaireCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.QuestionnaireUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * アンケートチェックロジックの実装クラス。<br />
 *
 * @author
 */
@Component
public class QuestionnaireCheckLogicImpl extends AbstractShopLogic implements QuestionnaireCheckLogic {

    /** アンケートDAO */
    private final QuestionnaireDao questionnaireDao;

    /** 日付関連ユーティリティクラス */
    private final DateUtility dateUtility;

    /** QuestionnaireUtility */
    private final QuestionnaireUtility questionnaireUtility;

    @Autowired
    public QuestionnaireCheckLogicImpl(QuestionnaireDao questionnaireDao,
                                       DateUtility dateUtility,
                                       QuestionnaireUtility questionnaireUtility) {
        this.questionnaireDao = questionnaireDao;
        this.dateUtility = dateUtility;
        this.questionnaireUtility = questionnaireUtility;
    }

    /**
     * 新規登録・確認時アンケートチェック処理。
     *<pre>
     *公開開始日時のチェック、アンケートキーと公開期間の重複チェックを行う。
     *</pre>
     * @param questionnaire アンケート
     * @param questionList アンケート設問リスト
     */
    @Override
    public void checkForRegistAtConfirm(QuestionnaireEntity questionnaire, List<QuestionEntity> questionList) {

        // 公開開始日時が現在より過去でないことを確認
        checkOpenStartTime(questionnaire);

        // アンケートキーと公開期間が既存のアンケートと重複していないことを確認
        checkQuestionnaireKey(questionnaire);

        // アンケート設問の公開状態が１つでも「公開」であること確認
        checkOpenStatus(questionList);

        // アンケート設問の選択肢が必須入力の場合、選択肢の値を確認
        checkChoices(questionList);

        // エラーを表示
        if (hasErrorList()) {
            throwMessage();
        }

    }

    /**
     * 更新・確認時アンケートチェック処理。
     *<pre>
     *公開開始日時のチェック、アンケートキーと公開期間の重複チェック、公開開始後の変更不可チェックを行う。
     *</pre>
     * @param preUpdateQuestionnaire 更新前のアンケート
     * @param postUpdateQuestionnaire 更新後のアンケート
     * @param questionList 更新前のアンケート設問リスト
     * @param isUpdate 公開開始日時が変更可能の場合、true
     *
     */
    @Override
    public void checkForUpdateAtConfirm(QuestionnaireEntity preUpdateQuestionnaire,
                                        QuestionnaireEntity postUpdateQuestionnaire,
                                        List<QuestionEntity> questionList,
                                        boolean isUpdate) {

        // 公開開始日時が変更可能の時、 公開開始日時が現在より過去でないことを確認
        if (isUpdate) {
            checkOpenStartTime(postUpdateQuestionnaire);
        }

        // アンケートキーと公開期間が既存のアンケートと重複していないことを確認
        checkQuestionnaireKey(postUpdateQuestionnaire);

        // アンケート設問の公開状態が１つでも「公開」であること確認
        checkOpenStatus(questionList);

        // アンケート設問の選択肢が必須入力の場合、選択肢の値を確認
        checkChoices(questionList);

        // エラーを表示
        if (hasErrorList()) {
            throwMessage();
        }

    }

    /**
     * 新規登録・登録更新時アンケートチェック処理。
     *<pre>
     *公開開始日時のチェック、アンケートキーと公開期間の重複チェックを行う。
     *</pre>
     * @param questionnaire アンケート
     * @param questionList アンケート設問リスト
     */
    @Override
    public void checkForRegistAtOnceRegist(QuestionnaireEntity questionnaire, List<QuestionEntity> questionList) {

        // 公開開始日時が現在より過去でないことを確認
        checkOpenStartTime(questionnaire);

        // アンケートキーと公開期間が既存のアンケートと重複していないことを確認
        checkQuestionnaireKey(questionnaire);

        // エラーを表示
        if (hasErrorList()) {
            throwMessage();
        }

    }

    /**
     * 更新・登録更新時アンケートチェック処理。
     *<pre>
     *公開開始日時のチェック、アンケートキーと公開期間の重複チェック、公開開始後の変更不可チェックを行う。
     *</pre>
     * @param preUpdateQuestionnaire 更新前のアンケート
     * @param postUpdateQuestionnaire 更新後のアンケート
     * @param preQuestionList 更新前のアンケート設問リスト
     * @param postQuestionList 更新後のアンケート設問リスト
     * @param isUpdate 公開開始日時が変更可能の場合、true
     *
     */
    @Override
    public void checkForUpdateAtOnceRegist(QuestionnaireEntity preUpdateQuestionnaire,
                                           QuestionnaireEntity postUpdateQuestionnaire,
                                           List<QuestionEntity> preQuestionList,
                                           List<QuestionEntity> postQuestionList,
                                           boolean isUpdate) {

        // 公開開始日時が現在より過去の場合、変更不可項目が変更されていないことを確認
        checkCanChange(preUpdateQuestionnaire, postUpdateQuestionnaire, preQuestionList, postQuestionList);

        // アンケートキーと公開期間が既存のアンケートと重複していないことを確認
        checkQuestionnaireKey(postUpdateQuestionnaire);

        // エラーを表示
        if (hasErrorList()) {
            throwMessage();
        }

    }

    /**
     * 公開開始日チェック。<br /><br />
     * 公開開始日時が現在より未来であることを確認する。<br />
     * 公開開始日時が現在より過去の場合はエラーとしてメッセージを出力する。<br />
     *
     * @param questionnaire アンケート情報
     */
    public void checkOpenStartTime(QuestionnaireEntity questionnaire) {

        // 公開開始日時が現在より過去の場合はエラー
        if (!dateUtility.isAfterCurrentTime(questionnaire.getOpenStartTime())) {
            this.addErrorMessage(MSGCD_CANNOT_SET_OPENSTRATTIME);
        }

    }

    /**
     * アンケートキー登録可否チェック。<br /><br />
     * 過去に終了したアンケート、未来に開始されるアンケートに再利用不可期間（日）を<br />
     * 考慮して同一アンケートキーが存在する場合はエラーとしてメッセージを出力する。<br />
     *
     * @param questionnaire アンケート情報
     */
    public void checkQuestionnaireKey(QuestionnaireEntity questionnaire) {

        // アンケートキー
        String questionnaireKey = questionnaire.getQuestionnaireKey();
        // アンケーSEQ
        Integer questionnaireSeq = questionnaire.getQuestionnaireSeq();
        // アンケート開始日時
        Timestamp openStartTime = questionnaire.getOpenStartTime();
        // アンケート終了日時
        Timestamp openEndTime = questionnaire.getOpenEndTime();
        // ショップSEQ
        Integer shopSeq = 1001;

        // page情報のアンケートキーが重複不可期間の既存アンケートキーに使用されていないかをチェックする
        if (questionnaireSeq == null) {

            // 登録の場合の重複チェック
            if (questionnaireDao.checkQuestionnaireKeyByOpenTime(shopSeq, questionnaireKey, openStartTime, openEndTime)
                != 0) {
                this.addErrorMessage(MSGCD_REPETITION_QESTIONNAIREKEY);
            }

        } else {
            // 更新の場合の重複チェック
            if (questionnaireDao.checkQuestionnaireKeyByOpenTime(shopSeq, questionnaireKey, openStartTime, openEndTime)
                != 1) {
                this.addErrorMessage(MSGCD_REPETITION_QESTIONNAIREKEY);
            }

        }
    }

    /**
     * アンケート設問：公開状態チェック。<br />
     *<pre>
     *公開状態が１つでも「公開」の場合はtrueを返す。<br />
     *</pre>
     *
     * @param questionList アンケート設問リスト
     */
    public void checkOpenStatus(List<QuestionEntity> questionList) {

        Integer openCount = 0;

        // 公開状態が「公開」となっている場合は、openCountをカウント
        for (QuestionEntity questionEntity : questionList) {

            if (HTypeOpenDeleteStatus.OPEN.equals(questionEntity.getOpenStatus())) {
                openCount++;
            }
        }
        // 公開状態に１つも「公開」がない場合エラー
        if (openCount == 0) {
            this.addErrorMessage(MSGCD_NOTTHING_OPEN);
        }
    }

    /**
     * アンケート設問：選択肢の各種チェック。<br />
     *<pre>
     *選択肢が必須入力の場合、選択肢の各種チェックを行う。<br />
     *</pre>
     *
     * @param questionList アンケート設問リスト
     */
    public void checkChoices(List<QuestionEntity> questionList) {

        for (QuestionEntity questionEntity : questionList) {

            HTypeReplyType replyType = questionEntity.getReplyType();

            // 形式が「ラジオボタン/プルダウン/チェックボックス」である場合、選択肢に重複がないことを確認
            if (replyType != null && replyType.isSelectType()) {

                // 選択肢を改行で区切って配列にセット
                String[] choicesArray = questionnaireUtility.splitChoices(questionEntity.getChoices());
                // 確認用のハッシュセット
                Set<String> checkHash = new HashSet<>();

                // 選択肢に半角スラッシュがある場合エラー

                if (questionEntity.getChoices().indexOf("/") != -1) {
                    this.addErrorMessage(
                                    MSGCD_CHOICES_EXIST_SLASH,
                                    new Object[] {String.valueOf(questionList.indexOf(questionEntity) + 1)}
                                        );
                }

                // 選択肢の上限件数を超えている場合エラー
                if (choicesArray.length > CHOICES_MAXCOUNT) {
                    this.addErrorMessage(MSGCD_CHOICES_MAXCOUNT_OVER, new Object[] {CHOICES_MAXCOUNT,
                                    String.valueOf(questionList.indexOf(questionEntity) + 1)});
                }

                for (String choicesStr : choicesArray) {

                    // 選択肢に重複がある場合エラー
                    if (checkHash.contains(choicesStr)) {
                        this.addErrorMessage(
                                        MSGCD_CHOICES_DUPLICATION,
                                        new Object[] {String.valueOf(questionList.indexOf(questionEntity) + 1)}
                                            );
                    } else {
                        checkHash.add(choicesStr);
                    }

                    // 選択肢毎の桁数上限を超えている場合エラー
                    if (choicesStr.length() > CHOICES_MAXLENGTH) {
                        this.addErrorMessage(MSGCD_CHOICES_MAXLENGTH_OVER, new Object[] {CHOICES_MAXLENGTH,
                                        String.valueOf(questionList.indexOf(questionEntity) + 1)});
                    }

                }

            }
        }
    }

    /**
     * 変更不可項目の変更有無チェック。<br />
     *<pre>
     *選択肢が必須入力の場合、選択肢の各種チェックを行う。<br />
     *</pre>
     *
     * @param preUpdateQuestionnaire 変更前アンケート
     * @param postUpdateQuestionnaire 変更後アンケート
     * @param preQuestionList 変更前アンケート設問リスト
     * @param postQuestionList 変更後アンケート設問リスト
     */
    public void checkCanChange(QuestionnaireEntity preUpdateQuestionnaire,
                               QuestionnaireEntity postUpdateQuestionnaire,
                               List<QuestionEntity> preQuestionList,
                               List<QuestionEntity> postQuestionList) {

        // 公開開始日時が現在より過去の場合、以下のチェックを行う
        if (!dateUtility.isAfterCurrentTime(postUpdateQuestionnaire.getOpenStartTime())) {

            // 公開開始日時が変更されている場合、エラー
            if (!preUpdateQuestionnaire.getOpenStartTime().equals(postUpdateQuestionnaire.getOpenStartTime())) {
                this.addErrorMessage(MSGCD_CANNOT_CHANGE, new Object[] {"公開開始日時"});
            }

            for (QuestionEntity preQuestion : preQuestionList) {

                for (QuestionEntity postQuestion : postQuestionList) {

                    // 更新時しかここには来ないので、SEQのNULLチェックはしない
                    if (preQuestion.getQuestionSeq().equals(postQuestion.getQuestionSeq())) {

                        // 回答入力設定-必須が変更されている場合、エラー
                        if (!preQuestion.getReplyRequiredFlag()
                                        .getValue()
                                        .equals(postQuestion.getReplyRequiredFlag().getValue())) {
                            this.addErrorMessage(MSGCD_CANNOT_CHANGE_QUESTION, new Object[] {"回答入力設定-必須",
                                            String.valueOf(postQuestion.getOrderDisplay())});

                        }

                        // 回答入力設定-形式が変更されている場合、エラー
                        if (!preQuestion.getReplyType().equals(postQuestion.getReplyType())) {
                            this.addErrorMessage(MSGCD_CANNOT_CHANGE_QUESTION, new Object[] {"回答入力設定-形式",
                                            String.valueOf(postQuestion.getOrderDisplay())});

                        }

                        // 回答入力設定-選択肢が変更されている場合、エラー
                        if (preQuestion.getChoices() != null && !preQuestion.getChoices()
                                                                            .equals(postQuestion.getChoices())) {
                            this.addErrorMessage(MSGCD_CANNOT_CHANGE_QUESTION, new Object[] {"回答入力設定-選択肢",
                                            String.valueOf(postQuestion.getOrderDisplay())});

                        }
                    }

                }

            }
        }
    }

    /**
     * アンケート回答可否チェック。<br />
     *<pre>
     *非公開状態/公開終了/削除/存在しないのいずれでもない場合はtrueを返す。<br />
     *</pre>
     *
     * @param questionnaireSeq アンケートSEQ
     * @return 回答可能な場合trueを返す
     */
    @Override
    public boolean checkQuestionaireReply(Integer questionnaireSeq) {

        // 現在日時を取得する
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        Timestamp currentTime = dateUtility.getCurrentTime();

        // ショップSEQを取得する
        Integer shopSeq = 1001;

        // page情報のアンケートSEQがアンケート回答可能であることをチェックする
        if (questionnaireDao.checkQuestionnaireSeqByQuestionnaireKeyAndTime(shopSeq, questionnaireSeq, currentTime)
            == 1) {
            // 1件の場合は回答可能
            return true;
        }
        // 1件以外の場合は回答不可
        return false;

    }
}
