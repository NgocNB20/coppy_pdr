/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireReplyDisplayDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireSearchForBackDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireReplyTotalEntity;

import java.sql.Timestamp;
import java.util.List;

/**
 * アンケート情報取得ロジックのインタフェースクラス。<br />
 * <pre>
 * アンケート管理画面の各処理から利用する。
 * </pre>
 * * @author Pham Quang Dieu
 */
public interface QuestionnaireGetLogic {

    /**
     * アンケート情報を取得する。<br />
     * <pre>
     * アンケートSEQよりアンケート情報を取得する。
     * </pre>
     *
     * @param questionnaireSeq アンケートSEQ
     * @return アンケート
     */
    QuestionnaireEntity getQuestionnaire(Integer questionnaireSeq);

    /**
     * アンケート情報を取得する。<br />
     * <pre>
     * アンケート検索条件Dtoよりアンケート情報を取得する。
     * </pre>
     *
     * @param conditionDto アンケート検索条件Dto
     * @return アンケート検索結果Dtoリスト
     */
    List<QuestionnaireSearchResultDto> getSearchResultDto(QuestionnaireSearchForBackDto conditionDto);

    /**
     * アンケート回答集計から最新の登録日時を取得する。<br />
     * <pre>
     * 最新の登録日時を取得する。
     * </pre>
     *
     * @return 登録日時
     */
    Timestamp getCurrentRegistTime();

    /**
     * アンケート設問情報を取得する。<br />
     * <pre>
     * アンケートSEQよりアンケート設問情報を取得する。
     * </pre>
     *
     * @param questionnaireSeq アンケートSEQ
     * @return アンケート設問リスト
     */
    List<QuestionEntity> getQuestionList(Integer questionnaireSeq);

    /**
     * アンケート設問情報を取得する。<br />
     * <pre>
     * アンケートSEQよりアンケート設問情報・アンケート回答集計情報を取得する。
     * </pre>
     *
     * @param questionnaireSeq アンケートSEQ
     * @return アンケート設問詳細情報リスト
     */
    List<QuestionDetailsDto> getDetailsDtoList(Integer questionnaireSeq);

    /**
     * アンケート回答集計情報を取得する。<br />
     * <pre>
     * アンケートSEQよりアンケート回答集計情報を取得する。
     * </pre>
     *
     * @param questionnaireSeq アンケートSEQ
     * @return アンケート回答集計
     */
    QuestionnaireReplyTotalEntity getQuestionnaireReplyTotal(Integer questionnaireSeq);

    /**
     * アンケート情報を取得する。<br />
     * <pre>
     * アンケートキーよりアンケート情報を取得する。
     * </pre>
     *
     * @param questionnaireKey アンケートキー
     * @return アンケート
     */
    QuestionnaireEntity getQuestionnaireByKey(String questionnaireKey);

    /**
     * アンケート設問情報を取得し、アンケート画面表示用Dtoを作成する。<br />
     * <pre>
     * アンケートSEQよりアンケート設問情報を取得する。
     * </pre>
     *
     * @param questionnaireSeq アンケートSEQ
     * @return アンケート設問リスト
     */
    List<QuestionnaireReplyDisplayDto> getQuestionDtoList(Integer questionnaireSeq);

    /**
     * アンケート設問情報を取得し、アンケート画面表示用Dtoを作成する。<br />
     * <pre>
     * アンケートSEQよりアンケート設問情報を取得する。
     * </pre>
     *
     * @param questionnaireSeq アンケートSEQ
     * @param siteType         サイト種別
     * @return アンケート設問リスト
     */
    List<QuestionnaireReplyDisplayDto> getQuestionDtoList(Integer questionnaireSeq, HTypeSiteType siteType);

    /**
     * 注文用のアンケート情報を取得
     *
     * @param siteType サイト種別
     * @return アンケート
     */
    QuestionnaireEntity getOrderQuestionnaire(HTypeSiteType siteType);

    /**
     * 注文用のアンケート情報を取得
     *
     * @return アンケート
     */
    QuestionnaireEntity getOrderQuestionnaire();

    /**
     * アンケート回答情報を取得する。<br />
     * <pre>
     * アンケートSEQ、会員SEQよりアンケート回答情報を取得する。
     * </pre>
     *
     * @param memberInfoSeq    会員SEQ
     * @param questionnaireKey アンケートキー
     * @return アンケート回答
     */
    boolean getQuestionnaireReply(Integer memberInfoSeq, String questionnaireKey);
}
