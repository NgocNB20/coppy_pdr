/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireReplyCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireReplyCsvSearchDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireReplyEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.stream.Stream;

/**
 * アンケート回答DAOクラス。<br />
 *
 * @author matsuda
 */
@Dao
@ConfigAutowireable
public interface QuestionnaireReplyDao {

    /**
     * アンケート回答を登録する。
     *
     * @param questionnaireReply アンケート回答
     * @return 登録件数
     */
    @Insert(excludeNull = true)
    int insert(QuestionnaireReplyEntity questionnaireReply);

    /**
     * アンケート回答を更新する。
     *
     * @param questionnaireReply アンケート回答
     * @return 更新件数
     */
    @Update
    int update(QuestionnaireReplyEntity questionnaireReply);

    /**
     * アンケート回答を削除する。
     *
     * @param questionnaireReply アンケート回答
     * @return 削除件数
     */
    @Delete
    int delete(QuestionnaireReplyEntity questionnaireReply);

    /**
     * 会員検索全件CSV出力
     *
     * @param conditionDto アンケート回答検索条件Dto
     * @return 出力件数
     */
    @Select
    Stream<QuestionnaireReplyCsvDto> getSearchResultList(QuestionnaireReplyCsvSearchDto conditionDto);

    /**
     * 会員SEQとアンケートキーから<br/>
     * 現在公開中のアンケート回答を取得します。<br/>
     *
     * @param memberInfoSeq    会員SEQ
     * @param questionnaireKey アンケートキー
     * @return アンケート回答結果
     */
    @Select
    QuestionnaireReplyEntity getQuestionnaireReply(Integer memberInfoSeq, String questionnaireKey);
}
