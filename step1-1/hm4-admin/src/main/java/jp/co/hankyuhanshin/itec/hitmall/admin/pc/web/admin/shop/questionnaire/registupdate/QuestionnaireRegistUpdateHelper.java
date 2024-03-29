package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.questionnaire.registupdate;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReplyRequiredFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReplyType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReplyValidatePattern;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ZenHanConversionUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionnaireRegistUpdateHelper {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionnaireRegistUpdateHelper.class);

    /**
     * 　半角変換時にスキップする文字
     */
    protected static final Character[] SKIP_CHAR = new Character[] {'＆', '＜', '＞', '”', '’', '￥'};

    /**
     * 　変換Utilityクラス
     */
    private ConversionUtility conversionUtility;

    /**
     * 　日付関連Utilityクラス
     */
    private DateUtility dateUtility;

    /**
     * 　全角・半角変換Utilityクラス
     */
    private ZenHanConversionUtility zenHanConversionUtility;

    @Autowired
    public QuestionnaireRegistUpdateHelper(ConversionUtility conversionUtility,
                                           DateUtility dateUtility,
                                           ZenHanConversionUtility zenHanConversionUtility) {
        this.conversionUtility = conversionUtility;
        this.zenHanConversionUtility = zenHanConversionUtility;
        this.dateUtility = dateUtility;
    }

    /**
     * アンケート情報の初期値セット（アンケート新規登録時の初期表示）<br />
     * <pre>
     * 変更内容表示後、グローバルメニュー・サイドメニューから遷移してきたことを想定。
     * </pre>
     *
     * @param questionnaireRegistUpdateModel questionnaireRegistUpdateModel
     */
    public void toPageForNewRegistPage(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel) {
        questionnaireRegistUpdateModel.setQuestionnaireSeq(null);
        questionnaireRegistUpdateModel.setQuestionnaireKey(null);
        questionnaireRegistUpdateModel.setName(null);
        questionnaireRegistUpdateModel.setOpenStartDate(null);
        questionnaireRegistUpdateModel.setOpenStartTime(null);
        questionnaireRegistUpdateModel.setOpenEndDate(null);
        questionnaireRegistUpdateModel.setOpenEndTime(null);
        questionnaireRegistUpdateModel.setSiteMapFlag(null);
        questionnaireRegistUpdateModel.setMemo(null);
        questionnaireRegistUpdateModel.setNamePc(null);
        questionnaireRegistUpdateModel.setOpenStatusPc(null);
        questionnaireRegistUpdateModel.setFreeTextPc(null);
        questionnaireRegistUpdateModel.setCompleteTextPc(null);

        // 公開開始日時の初期化(変更可能)
        questionnaireRegistUpdateModel.setUpdateOpenStartTime(true);

        /*
         * アンケート設問の初期化
         */

        // アンケート設問リスト、作成
        List<QuestionnaireRegistUpdateModelItem> questionnaireRegistUpdateModelItems = new ArrayList<>();
        QuestionnaireRegistUpdateModelItem questionnaireRegistUpdateModelItem =
                        ApplicationContextUtility.getBean(QuestionnaireRegistUpdateModelItem.class);

        // 表示するアンケート設問のNoに、初期表示なので1を設定
        int index = 1;

        // 設問設定一覧データ：Noの初期化
        questionnaireRegistUpdateModelItem.setDspNo(index);

        // 設問設定一覧データ：表示順の初期化
        questionnaireRegistUpdateModelItem.setOrderDisplay(index);

        // 回答入力設定の変更可否の初期化（true:変更可能）
        questionnaireRegistUpdateModelItem.setUpdateQuestion(true);

        // アンケート設問一覧をアンケート設問一覧リストにセット
        questionnaireRegistUpdateModelItems.add(questionnaireRegistUpdateModelItem);

        questionnaireRegistUpdateModel.setIndexPageItems(questionnaireRegistUpdateModelItems);
    }

    /**
     * 画面表示項目のセット(アンケート登録時の再表示)<br />
     *
     * @param questionnaireRegistUpdateModel 登録更新画面ページ
     */
    public void toPageForLoad(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel) {

        QuestionnaireEntity questionnaire = questionnaireRegistUpdateModel.getPostUpdateQuestionnaire();
        List<QuestionEntity> questionEntityList = questionnaireRegistUpdateModel.getPostUpdateQuestionList();

        // アンケート情報をページにセット
        toPageQuestionnaire(questionnaire, questionnaireRegistUpdateModel);

        // アンケート設問情報をページにセット
        toPageQuestion(questionEntityList, questionnaireRegistUpdateModel);

        // 公開開始日時の変更可否チェック
        if (canUpdate(questionnaireRegistUpdateModel)) {
            questionnaireRegistUpdateModel.setUpdateOpenStartTime(true);
        } else {
            questionnaireRegistUpdateModel.setUpdateOpenStartTime(false);
        }

    }

    /**
     * 画面表示項目のセット(アンケート更新時の初期表示)<br />
     *
     * @param questionnaireRegistUpdateModel 登録更新画面ページ
     */
    public void toPageForLoadUpdate(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel) {

        QuestionnaireEntity questionnaire = questionnaireRegistUpdateModel.getPreUpdateQuestionnaire();
        List<QuestionEntity> questionEntityList = questionnaireRegistUpdateModel.getPreUpdateQuestionList();

        // アンケート情報をページにセット
        toPageQuestionnaire(questionnaire, questionnaireRegistUpdateModel);

        // アンケート設問情報をページにセット
        toPageQuestion(questionEntityList, questionnaireRegistUpdateModel);

        // 公開開始日時の変更可否チェック
        if (canUpdate(questionnaireRegistUpdateModel)) {
            questionnaireRegistUpdateModel.setUpdateOpenStartTime(true);
        } else {
            questionnaireRegistUpdateModel.setUpdateOpenStartTime(false);
        }

        // // 不正操作対策の情報をセットする
        questionnaireRegistUpdateModel.setScSeq(questionnaireRegistUpdateModel.getQuestionnaireSeq());
        questionnaireRegistUpdateModel.setDbSeq(questionnaire.getQuestionnaireSeq());

    }

    /**
     * 確認画面遷移時の処理。<br />
     * <pre>
     * 自動入力情報項目をセットし変更後アンケート情報を作成する。
     * </pre>
     *
     * @param questionnaire                  アンケートエンティティ
     * @param questionnaireRegistUpdateModel 登録更新画面ページ
     */
    public void toPageForConfirmQuestionnaire(QuestionnaireEntity questionnaire,
                                              QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel) {

        // 自動入力項目（公開開始日時、公開終了日時）のセット
        // 公開開始時間が設定されていない場合は「00:00:00」をセット
        questionnaireRegistUpdateModel.setOpenStartTime(
                        conversionUtility.toDefaultHms(questionnaireRegistUpdateModel.getOpenStartDate(),
                                                       questionnaireRegistUpdateModel.getOpenStartTime(),
                                                       ConversionUtility.DEFAULT_START_TIME
                                                      ));

        // 公開終了時間が設定されていない場合は「23：59：59」をセット
        questionnaireRegistUpdateModel.setOpenEndTime(
                        conversionUtility.toDefaultHms(questionnaireRegistUpdateModel.getOpenEndDate(),
                                                       questionnaireRegistUpdateModel.getOpenEndTime(),
                                                       ConversionUtility.DEFAULT_END_TIME
                                                      ));

        // アンケートエンティティにページ情報をセット

        /** アンケートSEQ（PK） */
        questionnaire.setQuestionnaireSeq(questionnaireRegistUpdateModel.getQuestionnaireSeq());

        /** アンケートキー */
        questionnaire.setQuestionnaireKey(questionnaireRegistUpdateModel.getQuestionnaireKey());

        /**アンケート名 */
        questionnaire.setName(questionnaireRegistUpdateModel.getName());

        /** アンケート表示名PC */
        questionnaire.setNamePc(questionnaireRegistUpdateModel.getNamePc());

        /**公開状態PC */
        questionnaire.setOpenStatusPc(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                    questionnaireRegistUpdateModel.getOpenStatusPc()
                                                                   ));

        /** 公開開始日時 */
        String openStartTime = ConversionUtility.DEFAULT_START_TIME;
        if (StringUtil.isNotEmpty(questionnaireRegistUpdateModel.getOpenStartTime())) {
            openStartTime = questionnaireRegistUpdateModel.getOpenStartTime();
        }

        questionnaire.setOpenStartTime(conversionUtility.toTimeStamp(questionnaireRegistUpdateModel.getOpenStartDate(),
                                                                     openStartTime
                                                                    ));

        /** 公開終了日時 */
        String openEndTime = ConversionUtility.DEFAULT_END_TIME;
        if (StringUtil.isNotEmpty(questionnaireRegistUpdateModel.getOpenEndTime())) {
            openEndTime = questionnaireRegistUpdateModel.getOpenEndTime();
        }
        questionnaire.setOpenEndTime(
                        conversionUtility.toTimeStamp(questionnaireRegistUpdateModel.getOpenEndDate(), openEndTime));

        /**説明文PC */
        questionnaire.setFreeTextPc(questionnaireRegistUpdateModel.getFreeTextPc());

        /**完了文PC */
        questionnaire.setCompleteTextPc(questionnaireRegistUpdateModel.getCompleteTextPc());

        /**サイトマップ出力 */
        questionnaire.setSiteMapFlag(EnumTypeUtil.getEnumFromValue(HTypeSiteMapFlag.class,
                                                                   questionnaireRegistUpdateModel.getSiteMapFlag()
                                                                  ));

        /** メモ */
        questionnaire.setMemo(questionnaireRegistUpdateModel.getMemo());

        // 変更後アンケートエンティティにもページ情報をセット
        /**変更後アンケート情報*/
        questionnaireRegistUpdateModel.setPostUpdateQuestionnaire(questionnaire);

    }

    /**
     * 入力内容を画面に反映
     * 再検索用
     *
     * @param questionEntityList             アンケート設問エンティティリスト
     * @param questionnaireRegistUpdateModel 詳細ページ
     */
    public void toPageForConfirmQuestion(List<QuestionEntity> questionEntityList,
                                         QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel) {
        List<QuestionEntity> resultList = new ArrayList<>();

        // アンケート設問エンティティリストにページ情報をセット
        for (QuestionnaireRegistUpdateModelItem questionnaireRegistUpdateModelItem : questionnaireRegistUpdateModel.getIndexPageItems()) {
            QuestionEntity question = null;
            // 更新の場合、更新前のEntityを使用する（画面入力項目以外の値を引き継ぐ為）
            if (CollectionUtil.isNotEmpty(questionnaireRegistUpdateModel.getPreUpdateQuestionList())) {
                for (QuestionEntity preQuestion : questionnaireRegistUpdateModel.getPreUpdateQuestionList()) {
                    if (preQuestion.getQuestionSeq().equals(questionnaireRegistUpdateModelItem.getQuestionSeq())) {
                        question = CopyUtil.deepCopy(preQuestion);
                        break;
                    }
                }
            }
            if (question == null) {
                question = ApplicationContextUtility.getBean(QuestionEntity.class);
            }

            /** アンケート設問SEQ（PK） */
            question.setQuestionSeq(questionnaireRegistUpdateModelItem.getQuestionSeq());

            /** アンケートSEQ (FK) */
            question.setQuestionnaireSeq(questionnaireRegistUpdateModel.getQuestionnaireSeq());

            /** 表示順 */
            question.setOrderDisplay(questionnaireRegistUpdateModelItem.getOrderDisplay());

            /** 質問内容 */
            question.setQuestion(questionnaireRegistUpdateModelItem.getQuestion());

            /** 公開状態 */
            question.setOpenStatus(EnumTypeUtil.getEnumFromValue(HTypeOpenDeleteStatus.class,
                                                                 questionnaireRegistUpdateModelItem.getOpenStatus()
                                                                ));

            /** 回答必須フラグ */
            question.setReplyRequiredFlag(EnumTypeUtil.getEnumFromValue(HTypeReplyRequiredFlag.class,
                                                                        questionnaireRegistUpdateModelItem.getReplyRequiredFlag()
                                                                       ));

            /** 回答形式種別 */
            question.setReplyType(EnumTypeUtil.getEnumFromValue(HTypeReplyType.class,
                                                                questionnaireRegistUpdateModelItem.getReplyType()
                                                               ));

            /** 回答文字種別 */
            question.setReplyValidatePattern(EnumTypeUtil.getEnumFromValue(HTypeReplyValidatePattern.class,
                                                                           questionnaireRegistUpdateModelItem.getReplyValidatePattern()
                                                                          ));

            /** 回答桁数 */
            question.setReplyMaxLength(
                            conversionUtility.toInteger(questionnaireRegistUpdateModelItem.getReplyMaxLength()));

            /** 選択肢 */
            question.setChoices(questionnaireRegistUpdateModelItem.getChoices());

            resultList.add(question);
        }

        questionEntityList = resultList;

        /**変更後アンケート設問情報*/
        questionnaireRegistUpdateModel.setPostUpdateQuestionList(questionEntityList);
    }

    /**
     * アンケート設問情報追加<br/>
     *
     * @param questionnaireRegistUpdateModel ページ
     */
    public void toPageForAddQestion(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel) {

        /*
         * アンケート設問の初期化
         */

        // アンケート設問リスト、作成
        List<QuestionnaireRegistUpdateModelItem> tempItems = questionnaireRegistUpdateModel.getIndexPageItems();
        QuestionnaireRegistUpdateModelItem tempItem =
                        ApplicationContextUtility.getBean(QuestionnaireRegistUpdateModelItem.class);

        // 追加するアンケート設問のNoに、既存アンケート設問+1を設定
        int index = tempItems.size() + 1;

        // 設問設定一覧データ：Noの初期化
        tempItem.setDspNo(index);

        // 設問設定一覧データ：表示順の初期化
        tempItem.setOrderDisplay(index);

        // 回答入力設定の変更可否の初期化（true:変更可能）
        tempItem.setUpdateQuestion(true);

        // アンケート設問一覧をアンケート設問一覧リストにセット
        tempItems.add(tempItem);

        questionnaireRegistUpdateModel.setIndexPageItems(tempItems);

        questionnaireRegistUpdateModel.setSeq(questionnaireRegistUpdateModel.getQuestionnaireSeq());
    }

    /**
     * アンケート設問情報削除<br/>
     *
     * @param questionnaireRegistUpdateModel ページ
     */
    public void toPageForDeleteQuestion(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel) {
        List<QuestionnaireRegistUpdateModelItem> questionnaireRegistUpdateModelItems =
                        questionnaireRegistUpdateModel.getIndexPageItems();

        // 画面からNoを取得する
        Integer selectDspNo = questionnaireRegistUpdateModel.getSelectDspNo();

        // 削除するアンケート設問情報を取得する
        for (QuestionnaireRegistUpdateModelItem questionnaireRegistUpdateModelItem : questionnaireRegistUpdateModelItems) {
            if (selectDspNo.equals(questionnaireRegistUpdateModelItem.getDspNo())) {
                questionnaireRegistUpdateModelItems.remove(questionnaireRegistUpdateModelItem);
                break;
            }
        }

        // Noを洗い替える
        setOrderDisplay(questionnaireRegistUpdateModelItems);

        questionnaireRegistUpdateModel.setIndexPageItems(questionnaireRegistUpdateModelItems);
        questionnaireRegistUpdateModel.setSeq(questionnaireRegistUpdateModel.getQuestionnaireSeq());
    }

    /**
     * アンケート表示順変更<br/>
     *
     * @param questionnaireRegistUpdateModel ページ
     * @param isUpFlg                        true: 上へ移動  false: 下へ移動
     */
    public void toPageForQuestionChange(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel,
                                        boolean isUpFlg) {
        int selectDspNo = questionnaireRegistUpdateModel.getSelectDspNo();
        int idx1 = selectDspNo - 1;
        int idx2 = idx1 + 1;
        if (isUpFlg) {
            idx2 = idx1 - 1;
        }

        List<QuestionnaireRegistUpdateModelItem> items = questionnaireRegistUpdateModel.getIndexPageItems();
        QuestionnaireRegistUpdateModelItem target1;
        QuestionnaireRegistUpdateModelItem target2;
        try {
            target1 = items.get(idx1);
            target2 = items.get(idx2);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("例外処理が発生しました", e);
            return;
        }

        items.set(idx2, target1);
        items.set(idx1, target2);
        setOrderDisplay(items);
    }

    /**
     * 表示順の再設定
     *
     * @param items 設問設定
     */
    protected void setOrderDisplay(List<QuestionnaireRegistUpdateModelItem> items) {
        int orderDisplay = 1;
        for (QuestionnaireRegistUpdateModelItem item : items) {
            item.setOrderDisplay(orderDisplay);
            item.setDspNo(orderDisplay++);
        }
    }

    /**
     * アンケートエンティティを画面にセット。<br />
     *
     * @param questionnaire                  アンケートエンティティ
     * @param questionnaireRegistUpdateModel 登録更新画面ページ
     */
    protected void toPageQuestionnaire(QuestionnaireEntity questionnaire,
                                       QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel) {

        // アンケートエンティティを画面にセット
        /*
         * 画面項目（基本共通設定）
         */
        /** アンケートSEQ */
        questionnaireRegistUpdateModel.setQuestionnaireSeq(questionnaire.getQuestionnaireSeq());
        /** パラメータSEQ */
        questionnaireRegistUpdateModel.setSeq(questionnaire.getQuestionnaireSeq());
        /** アンケートキー */
        questionnaireRegistUpdateModel.setQuestionnaireKey(questionnaire.getQuestionnaireKey());
        /**アンケート名称 */
        questionnaireRegistUpdateModel.setName(questionnaire.getName());
        /** 公開開始日 */
        questionnaireRegistUpdateModel.setOpenStartDate(conversionUtility.toYmd(questionnaire.getOpenStartTime()));
        /** 公開開始時間 */
        questionnaireRegistUpdateModel.setOpenStartTime(conversionUtility.toHms(questionnaire.getOpenStartTime()));
        /** 公開終了日 */
        questionnaireRegistUpdateModel.setOpenEndDate(conversionUtility.toYmd(questionnaire.getOpenEndTime()));
        /** 公開終了時間 */
        questionnaireRegistUpdateModel.setOpenEndTime(conversionUtility.toHms(questionnaire.getOpenEndTime()));
        /** ポイント */
        //questionnaireRegistUpdateModel.setPoint(questionnaire.getPoint());
        /**サイトマップ出力 */
        questionnaireRegistUpdateModel.setSiteMapFlag(EnumTypeUtil.getValue(questionnaire.getSiteMapFlag()));
        /** 管理用メモ */
        questionnaireRegistUpdateModel.setMemo(questionnaire.getMemo());

        /*
         * 画面項目（基本サイト別設定）
         */
        /** アンケート表示名PC */
        questionnaireRegistUpdateModel.setNamePc(questionnaire.getNamePc());
        /**アンケート公開状態PC */
        questionnaireRegistUpdateModel.setOpenStatusPc(EnumTypeUtil.getValue(questionnaire.getOpenStatusPc()));
        /**アンケート説明文PC */
        questionnaireRegistUpdateModel.setFreeTextPc(questionnaire.getFreeTextPc());
        /**アンケート回答完了文PC */
        questionnaireRegistUpdateModel.setCompleteTextPc(questionnaire.getCompleteTextPc());

    }

    /**
     * アンケート設問エンティティを画面にセット。<br />
     *
     * @param questionEntityList             アンケート設問エンティティ
     * @param questionnaireRegistUpdateModel 登録更新画面ページ
     */
    protected void toPageQuestion(List<QuestionEntity> questionEntityList,
                                  QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel) {
        List<QuestionnaireRegistUpdateModelItem> resultItemList = new ArrayList<>();

        // アンケート設問エンティティを画面にセット
        for (QuestionEntity questionEntity : questionEntityList) {

            QuestionnaireRegistUpdateModelItem questionnaireRegistUpdateModelItem =
                            ApplicationContextUtility.getBean(QuestionnaireRegistUpdateModelItem.class);

            /*
             * 画面項目（設問設定一覧）データ項目
             */
            /** 設問設定一覧データ：表示順 */
            questionnaireRegistUpdateModelItem.setDspNo(questionEntity.getOrderDisplay());

            /** 設問設定一覧データ：表示順 */
            questionnaireRegistUpdateModelItem.setOrderDisplay(questionEntity.getOrderDisplay());

            /** 設問設定一覧データ：設問内容 */
            questionnaireRegistUpdateModelItem.setQuestion(questionEntity.getQuestion());

            /** 設問設定一覧データ：公開状態 */
            questionnaireRegistUpdateModelItem.setOpenStatus(EnumTypeUtil.getValue(questionEntity.getOpenStatus()));

            /** 設問設定一覧データ：必須 */
            questionnaireRegistUpdateModelItem.setReplyRequiredFlag(
                            EnumTypeUtil.getValue(questionEntity.getReplyRequiredFlag()));

            /** 設問設定一覧データ：形式 */
            questionnaireRegistUpdateModelItem.setReplyType(EnumTypeUtil.getValue(questionEntity.getReplyType()));

            /** 設問設定一覧データ：文字種 */
            questionnaireRegistUpdateModelItem.setReplyValidatePattern(
                            EnumTypeUtil.getValue(questionEntity.getReplyValidatePattern()));

            /** 設問設定一覧データ：桁数 */
            questionnaireRegistUpdateModelItem.setReplyMaxLength(
                            conversionUtility.toString(questionEntity.getReplyMaxLength()));

            /** 設問設定一覧データ：選択肢 */
            questionnaireRegistUpdateModelItem.setChoices(questionEntity.getChoices());

            /** 設問設定一覧データ：選択肢(画面表示用) */
            questionnaireRegistUpdateModelItem.setChoicesDispItems(
                            conversionUtility.toDivArray(questionEntity.getChoices()));

            /** 設問設定一覧データ：必須  (画面テキスト表示用)*/
            questionnaireRegistUpdateModelItem.setReplyRequiredFlagDisp(
                            EnumTypeUtil.getValue(questionEntity.getReplyRequiredFlag()));

            /** 設問設定一覧データ：形式 (画面テキスト表示用)*/
            questionnaireRegistUpdateModelItem.setReplyTypeDisp(EnumTypeUtil.getValue(questionEntity.getReplyType()));

            /** 設問設定一覧データ：文字種(画面テキスト表示用) */
            questionnaireRegistUpdateModelItem.setReplyValidatePatternDisp(
                            EnumTypeUtil.getValue(questionEntity.getReplyValidatePattern()));

            /** アンケート設問SEQ（PK） */
            questionnaireRegistUpdateModelItem.setQuestionSeq(questionEntity.getQuestionSeq());

            /** 回答入力設定の更新可否*/
            if (canUpdate(questionnaireRegistUpdateModel)
                || questionnaireRegistUpdateModelItem.getQuestionSeq() == null) {
                questionnaireRegistUpdateModelItem.setUpdateQuestion(true);
            } else {
                questionnaireRegistUpdateModelItem.setUpdateQuestion(false);
            }

            resultItemList.add(questionnaireRegistUpdateModelItem);
        }
        questionnaireRegistUpdateModel.setIndexPageItems(resultItemList);
    }

    /**
     * アンケート変更可否チェック。<br />
     * <pre>
     * 変更前の公開開始日時が現在日時より未来日であることをチェックする。
     * 公開開始日時が現在日時より未来日の場合は変更可能。
     * 公開開始日時が現在日時より過去日の場合は変更不可。
     * </pre>
     *
     * @param questionnaireRegistUpdateModel 登録更新画面ページ
     * @return 変更可能な場合にtrueを返す
     */
    protected boolean canUpdate(QuestionnaireRegistUpdateModel questionnaireRegistUpdateModel) {

        // 新規登録の場合
        if (questionnaireRegistUpdateModel.isRegistQ()) {
            // 無条件にtrue（変更可能）
            return true;

            // 更新の場合
        } else {
            // 公開開始日時の取得(戻るボタンで再表示する場合もあるため、変更前の公開開始日時で判定する)
            Timestamp openStartTime = questionnaireRegistUpdateModel.getPreUpdateQuestionnaire().getOpenStartTime();

            // 現在日時の取得
            Timestamp currentTime = dateUtility.getCurrentTime();

            // 公開開始日時と現在日時を比較
            // 公開開始日時 > 現在日時ならtrueを返却(変更可能)
            // 公開開始日時 <= 現在日時ならfalseを返却(変更不可)
            return openStartTime.compareTo(currentTime) > 0;
        }
    }

}
