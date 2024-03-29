/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReplyType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire.QuestionDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire.QuestionReplyTotalDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire.QuestionnaireDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire.QuestionnaireReplyDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire.QuestionnaireReplyTotalDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireReplyDisplayDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireSearchForBackDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireReplyEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireReplyTotalEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire.QuestionnaireGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.QuestionnaireUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ZenHanConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * アンケート取得ロジックの実装クラス。<br />
 *
 * @author Pham Quang Dieu
 */
@Component
public class QuestionnaireGetLogicImpl extends AbstractShopLogic implements QuestionnaireGetLogic {

    /**
     * アンケートDAO
     */
    private final QuestionnaireDao questionnaireDao;

    /**
     * アンケート設問DAO
     */
    private final QuestionDao questionDao;

    /**
     * アンケート回答集計DAO
     */
    private final QuestionnaireReplyTotalDao questionnaireReplyTotalDao;

    /**
     * アンケート設問回答集計DAO
     */
    private final QuestionReplyTotalDao questionReplyTotalDao;

    /**
     * アンケート回答DAO
     */
    private final QuestionnaireReplyDao questionnaireReplyDao;

    /**
     * DateUtility
     */
    private final DateUtility dateUtility;

    /**
     * QuestionnaireUtility
     */
    private final QuestionnaireUtility questionnaireUtility;

    /**
     * コンストラクター
     *
     * @param questionnaireDao
     * @param questionDao
     * @param questionnaireReplyTotalDao
     * @param questionReplyTotalDao
     * @param questionnaireReplyDao
     * @param dateUtility
     * @param questionnaireUtility
     */
    @Autowired
    public QuestionnaireGetLogicImpl(QuestionnaireDao questionnaireDao,
                                     QuestionDao questionDao,
                                     QuestionnaireReplyTotalDao questionnaireReplyTotalDao,
                                     QuestionReplyTotalDao questionReplyTotalDao,
                                     QuestionnaireReplyDao questionnaireReplyDao,
                                     DateUtility dateUtility,
                                     QuestionnaireUtility questionnaireUtility) {
        this.questionnaireDao = questionnaireDao;
        this.questionDao = questionDao;
        this.questionnaireReplyTotalDao = questionnaireReplyTotalDao;
        this.questionReplyTotalDao = questionReplyTotalDao;
        this.questionnaireReplyDao = questionnaireReplyDao;
        this.dateUtility = dateUtility;
        this.questionnaireUtility = questionnaireUtility;
    }

    /**
     * アンケートSEQからアンケート情報を取得する。
     *
     * @param questionnaireSeq アンケートSEQ
     * @return アンケート
     */
    @Override
    public QuestionnaireEntity getQuestionnaire(Integer questionnaireSeq) {

        // shopseqを取得する
        Integer shopSeq = 1001;

        // アンケート情報を取得する
        return questionnaireDao.getEntityByQuestionnaireSeq(questionnaireSeq, shopSeq);
    }

    /**
     * アンケート検索条件Dtoからアンケート情報を取得する。
     *
     * @param conditionDto アンケート検索条件Dto
     * @return アンケート検索結果Dtoリスト
     */
    @Override
    public List<QuestionnaireSearchResultDto> getSearchResultDto(QuestionnaireSearchForBackDto conditionDto) {

        // shopseqを取得する
        Integer shopSeq = 1001;
        conditionDto.setShopSeq(shopSeq);

        // アンケート情報を取得する
        List<QuestionnaireSearchResultDto> resultList = questionnaireDao.getSearchResultList(conditionDto,
                                                                                             conditionDto.getPageInfo()
                                                                                                         .getSelectOptions()
                                                                                            );
        return resultList;

    }

    /**
     * アンケート回答集計から最新の登録日時を取得する。
     *
     * @return 最新の登録日時
     */
    @Override
    public Timestamp getCurrentRegistTime() {

        // 最新の登録日時を取得する
        return questionnaireReplyTotalDao.getMaxRegistTime();

    }

    /**
     * アンケートSEQからアンケート設問リストを取得する。
     *
     * @param questionnaireSeq アンケートSEQ
     * @return アンケート設問リスト
     */
    @Override
    public List<QuestionEntity> getQuestionList(Integer questionnaireSeq) {

        // アンケート設問リストを取得する
        List<QuestionEntity> questionEntityList = questionDao.getEntityByQuestionnaireSeq(questionnaireSeq);

        // アンケート設問リストから、アンケート回答画面表示用DTOリストを作成
        return questionEntityList;
    }

    /**
     * アンケートSEQからアンケート情報を取得する。
     *
     * @param questionnaireSeq アンケートSEQ
     * @return getDtoList　アンケート設問詳細画面情報リスト
     */
    @Override
    public List<QuestionDetailsDto> getDetailsDtoList(Integer questionnaireSeq) {
        List<QuestionDetailsDto> questionDetailsDtoList = new ArrayList<>();

        // アンケート設問情報リストを取得する
        List<QuestionEntity> questionEntityList = questionDao.getEntityByQuestionnaireSeq(questionnaireSeq);

        // アンケート設問情報1件毎に、アンケート設問回答集計情報リストを取得する
        for (QuestionEntity questionEntity : questionEntityList) {
            QuestionDetailsDto questionDetailsDto = ApplicationContextUtility.getBean(QuestionDetailsDto.class);

            // Dtoにアンケート設問情報をセットする
            questionDetailsDto.setQuestionEntity(questionEntity);

            // 選択形式のアンケート設問の場合、回答集計情報を取得する
            if (questionEntity.getReplyType().isSelectType()) {
                questionDetailsDto.setQuestionReplyTotalEntityList(
                                questionReplyTotalDao.getEntity(questionEntity.getQuestionSeq()));
            }

            questionDetailsDtoList.add(questionDetailsDto);
        }
        // DtoリストにDtoをセッする
        return questionDetailsDtoList;
    }

    /**
     * アンケートSEQからアンケート回答集計情報を取得する。
     *
     * @param questionnaireSeq アンケートSEQ
     * @return アンケート回答集計
     */
    @Override
    public QuestionnaireReplyTotalEntity getQuestionnaireReplyTotal(Integer questionnaireSeq) {

        // アンケート回答集計情報を取得する
        return questionnaireReplyTotalDao.getEntity(questionnaireSeq);

    }

    /**
     * アンケートキーからアンケート情報を取得する。
     *
     * @param questionnaireKey アンケートキー
     * @return アンケート
     */
    @Override
    public QuestionnaireEntity getQuestionnaireByKey(String questionnaireKey) {
        HTypeSiteType siteType = HTypeSiteType.FRONT_PC;
        return getQuestionnaireByKey(questionnaireKey, siteType);
    }

    /**
     * アンケートキーからアンケート情報を取得する。
     *
     * @param questionnaireKey アンケートキー
     * @param siteType         サイト種別
     * @return アンケート
     */
    protected QuestionnaireEntity getQuestionnaireByKey(String questionnaireKey, HTypeSiteType siteType) {
        // shopseqを取得する
        Integer shopSeq = 1001;

        // パラメータチェック
        ArgumentCheckUtil.assertNotEmpty("questionnaireKey", questionnaireKey);
        ArgumentCheckUtil.assertNotNull("siteType", siteType);

        // 現在日時を取得する
        Timestamp currentTime = dateUtility.getCurrentTime();

        // アンケート情報を取得する
        return questionnaireDao.getEntityByQuestionnaireKeyAndTime(questionnaireKey, shopSeq, currentTime, siteType);
    }

    /**
     * アンケートSEQからアンケート設問リストを取得し、アンケート回答画面表示用Dtoにセットする。
     *
     * @param questionnaireSeq アンケートSEQ
     * @return アンケート設問リスト
     */
    @Override
    public List<QuestionnaireReplyDisplayDto> getQuestionDtoList(Integer questionnaireSeq) {
        HTypeSiteType siteType = HTypeSiteType.FRONT_PC;
        return getQuestionDtoList(questionnaireSeq, siteType);
    }

    /**
     * アンケートSEQからアンケート設問リストを取得し、アンケート回答画面表示用Dtoにセットする。
     *
     * @param questionnaireSeq アンケートSEQ
     * @param siteType         サイト種別
     * @return アンケート設問リスト
     */
    @Override
    public List<QuestionnaireReplyDisplayDto> getQuestionDtoList(Integer questionnaireSeq, HTypeSiteType siteType) {
        // アンケート設問リストを取得する
        List<QuestionEntity> questionEntityList =
                        questionDao.getEntityByQuestionnaireSeqAndOpenstatus(questionnaireSeq, siteType);

        // アンケート設問リストから、アンケート回答画面表示用DTOリストを作成
        return setPropertiesForPage(questionEntityList, siteType.isFrontMB());
    }

    /**
     * アンケート回答画面表示用設定<br/>
     * アンケート設問リストから、アンケート回答画面表示用DTOリストを作成する。<br />
     *
     * @param questionEntityList アンケート設問リスト
     * @param isMobile           モバイルであればtrue。全角を半角に変換。
     * @return アンケート回答画面表示用DTOリスト
     */
    public List<QuestionnaireReplyDisplayDto> setPropertiesForPage(List<QuestionEntity> questionEntityList,
                                                                   boolean isMobile) {

        // アンケート回答画面表示用DTOリスト生成
        List<QuestionnaireReplyDisplayDto> questionnaireReplyDisplayDtoList = new ArrayList<>();

        // 表示番号
        // 画面上のアンケート設問の先頭に表示する1からの連番。
        int displayNumber = 1;

        // アンケート設問リストの値を加工してアンケート回答画面表示用DTOリストを作成していく
        for (QuestionEntity questionEntity : questionEntityList) {

            // アンケート回答画面表示用DTO生成
            QuestionnaireReplyDisplayDto questionnaireReplyDisplayDto =
                            ApplicationContextUtility.getBean(QuestionnaireReplyDisplayDto.class);

            // 欠番ありの表示順（ordarDisplay）で取得しているので、採番しなおす
            questionnaireReplyDisplayDto.setDisplayNumber(displayNumber);

            // 表示用DTOにアンケート設問エンティティをセット
            questionnaireReplyDisplayDto.setQuestionEntity(questionEntity);

            // 選択肢を分割して画面のコントロールに表示できるように設定する。
            setQuestionChoicesForPage(questionnaireReplyDisplayDto, isMobile);

            // DTOリストに追加
            questionnaireReplyDisplayDtoList.add(questionnaireReplyDisplayDto);

            displayNumber++;
        }
        return questionnaireReplyDisplayDtoList;
    }

    /**
     * アンケート回答画面用選択肢セットメソッド<br/>
     * アンケート設問エンティティの選択肢を展開してアンケート回答画面に表示できるようにセットする。<br/>
     * 引数で渡されたDTOインスタンスのプロパティを変更する。
     *
     * @param questionnaireReplyDisplayDto アンケート回答画面表示用DTO
     * @param isMobile                     モバイルであればtrue。全角を半角に変換。
     */
    protected void setQuestionChoicesForPage(QuestionnaireReplyDisplayDto questionnaireReplyDisplayDto,
                                             boolean isMobile) {

        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("questionnaireReplyDisplayDto", questionnaireReplyDisplayDto);

        // アンケート回答画面表示用DTOからアンケート設問情報を取得
        QuestionEntity questionEntity = questionnaireReplyDisplayDto.getQuestionEntity();

        // アンケート設問は画面表示項目なのでDTOにセット
        // モバイルであれば半角に変換
        if (isMobile) {
            // 全角、半角の変換Helper取得
            ZenHanConversionUtility zenHanConversionUtility =
                            ApplicationContextUtility.getBean(ZenHanConversionUtility.class);
            String hankakuQuestion = zenHanConversionUtility.toHankaku(questionEntity.getQuestion());
            questionnaireReplyDisplayDto.setQuestion(hankakuQuestion);
        } else {
            questionnaireReplyDisplayDto.setQuestion(questionEntity.getQuestion());
        }

        // 必須入力設定から画面表示用の必須入力設定フラグをセット。公開であればtrue。
        questionnaireReplyDisplayDto.setReplyRequiredFlag(questionEntity.getReplyRequiredFlag());

        // コントロール（ラジオボタン OR チェックボックス OR プルダウン）に設定する選択肢作成。
        // 選択肢を分割してコントロール表示できるようにMapを作成。
        // モバイルであれば半角に変換（第2引数でコントロール）
        questionnaireReplyDisplayDto.setReplyType(questionEntity.getReplyType());
        HTypeReplyType replyType = questionnaireReplyDisplayDto.getReplyType();

        if (replyType != null) {
            if (replyType.isTextType()) {

                // テキストボックス OR テキストエリアの文字種をセット
                questionnaireReplyDisplayDto.setReplyValidatePattern(questionEntity.getReplyValidatePattern());
                // テキストボックス OR テキストエリアの桁数をセット
                questionnaireReplyDisplayDto.setReplyMaxLength(questionEntity.getReplyMaxLength());

            } else {

                Map<String, String> selectItems = createQuestionChoices(questionEntity, isMobile);
                if (replyType.equals(HTypeReplyType.PULLDOWN)) {
                    // プルダウンの選択肢をセット
                    questionnaireReplyDisplayDto.setQuestionPullDownItems(selectItems);

                } else if (replyType.equals(HTypeReplyType.RADIOBUTTON)) {
                    // ラジオボタンの選択肢をセット
                    questionnaireReplyDisplayDto.setQuestionRadioItems(selectItems);

                } else if (replyType.equals(HTypeReplyType.CHECKBOX)) {
                    // チェックボックスの選択肢をセット
                    questionnaireReplyDisplayDto.setQuestionCheckBoxItems(selectItems);
                }

            }
        }
    }

    /**
     * 選択肢分割（Map）<br/>
     * 選択肢を区切り文字で分割してMap化する<br />
     *
     * @param questionEntity アンケート設問エンティティ
     * @param isMobile       モバイルであればtrue。全角を半角に変換。
     * @return 処理件数
     */
    protected Map<String, String> createQuestionChoices(QuestionEntity questionEntity, boolean isMobile) {

        String questionChoices = questionEntity.getChoices();

        Map<String, String> selectItems = new LinkedHashMap<>();
        // 全角、半角の変換Helper取得
        ZenHanConversionUtility zenHanConversionUtility =
                        ApplicationContextUtility.getBean(ZenHanConversionUtility.class);

        // 選択肢区切り文字で分割
        String[] choices = questionnaireUtility.splitChoices(questionChoices);
        String labelString = null;

        for (String choicesStr : choices) {
            // モバイルであれば半角変換してラベルにセット
            if (isMobile) {
                labelString = zenHanConversionUtility.toHankaku(choicesStr);
            } else {
                labelString = choicesStr;
            }
            selectItems.put(labelString, choicesStr);
        }
        return selectItems;
    }

    @Override
    public QuestionnaireEntity getOrderQuestionnaire(HTypeSiteType siteType) {
        return getQuestionnaireByKey(questionnaireUtility.getQuestionnaireKeyOrder(), siteType);
    }

    @Override
    public QuestionnaireEntity getOrderQuestionnaire() {
        HTypeSiteType siteType = HTypeSiteType.FRONT_PC;
        return getOrderQuestionnaire(siteType);
    }

    @Override
    public boolean getQuestionnaireReply(Integer memberInfoSeq, String questionnaireKey) {
        QuestionnaireReplyEntity reply = questionnaireReplyDao.getQuestionnaireReply(memberInfoSeq, questionnaireKey);

        return reply != null;

    }
}
