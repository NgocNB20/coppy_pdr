/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireSearchForBackDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.sql.Timestamp;
import java.util.List;

/**
 * アンケートDAOクラス。<br />
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Dao
@ConfigAutowireable
public interface QuestionnaireDao {

    /**
     * アンケートを登録する。
     *
     * @param questionnaire アンケート
     * @return 登録件数
     */
    @Insert(excludeNull = true)
    int insert(QuestionnaireEntity questionnaire);

    /**
     * アンケートを更新する。
     *
     * @param questionnaire アンケート
     * @return 更新件数
     */
    @Update
    int update(QuestionnaireEntity questionnaire);

    /**
     * アンケートを削除する。
     *
     * @param questionnaire アンケート
     * @return 削除件数
     */
    @Delete
    int delete(QuestionnaireEntity questionnaire);

    /**
     * アンケートを取得する。
     *
     * @param questionnaireSeq アンケートSEQ
     * @return アンケートエンティティ
     */
    @Select
    QuestionnaireEntity getEntity(Integer questionnaireSeq);

    /**
     * 引数と同じアンケートキーの利用件数を返す。<br />
     * <pre>
     * 再利用不可期間のアンケートの中で同じアンケートキーを利用している件数を返す。
     * アンケート登録更新時のアンケートキーチェックで利用する。
     * </pre>
     *
     * @param shopSeq          ショップSEQ
     * @param questionnaireKey アンケートキー
     * @param openStartTime    公開開始日時
     * @param openEndTime      公開終了日時
     * @return 重複件数
     */
    @Select
    int checkQuestionnaireKeyByOpenTime(Integer shopSeq,
                                        String questionnaireKey,
                                        Timestamp openStartTime,
                                        Timestamp openEndTime);

    /**
     * アンケートSEQからアンケート情報を取得する。<br />
     * <pre>
     * 取得できない場合はNULL。
     * 更新・削除画面表示で利用する。
     * </pre>
     *
     * @param questionnaireSeq アンケートSEQ
     * @param shopSeq          ショップSEQ
     * @return アンケート
     */
    @Select
    QuestionnaireEntity getEntityByQuestionnaireSeq(Integer questionnaireSeq, Integer shopSeq);

    /**
     * アンケートエンティティList取得。<br />
     * <pre>
     * 検索条件を元に対象のアンケート情報リストを取得する。
     * アンケート検索で利用する。
     * </pre>
     *
     * @param conditionDto アンケート検索条件Dto
     * @return アンケート検索結果Dtoリスト
     */
    @Select
    List<QuestionnaireSearchResultDto> getSearchResultList(QuestionnaireSearchForBackDto conditionDto,
                                                           SelectOptions selectOptions);

    /**
     * アンケートキーからアンケート情報を取得する。<br />
     * <pre>
     * 取得できない場合はNULL。
     * 更新・削除画面表示で利用する。
     * </pre>
     *
     * @param questionnaireKey アンケートキー
     * @param shopSeq          ショップSEQ
     * @return アンケート
     */
    @Select()
    QuestionnaireEntity getEntityByQuestionnaireKey(String questionnaireKey, Integer shopSeq);

    /**
     * アンケートキー,からアンケート情報を取得する。<br />
     * <pre>
     * 取得できない場合はNULL。
     * 更新・削除画面表示で利用する。
     * </pre>
     *
     * @param questionnaireKey アンケートキー
     * @param shopSeq          ショップSEQ
     * @param currentTime      現在日時
     * @param siteType         サイト種別
     * @return アンケート
     */
    @Select
    QuestionnaireEntity getEntityByQuestionnaireKeyAndTime(String questionnaireKey,
                                                           Integer shopSeq,
                                                           Timestamp currentTime,
                                                           HTypeSiteType siteType);

    /**
     * 引数と同じアンケートキーの利用件数を返す。<br />
     * <pre>
     * 再利用不可期間のアンケートの中で同じアンケートキーを利用している件数を返す。
     * アンケート登録更新時のアンケートキーチェックで利用する。
     * </pre>
     *
     * @param shopSeq          ショップSEQ
     * @param questionnaireSeq アンケーSEQ
     * @param currentTime      現在日時
     * @return 重複件数
     */
    @Select
    int checkQuestionnaireSeqByQuestionnaireKeyAndTime(Integer shopSeq,
                                                       Integer questionnaireSeq,
                                                       Timestamp currentTime);

    /**
     * 集計完了フラグを完了済みに更新。<br/>
     *
     * @param totalingCompleteTime 集計完了日時
     */
    @Update(sqlFile = true)
    int updateTotalingCompleteFlag(Timestamp totalingCompleteTime);

    /**
     * サイトマップXML出力バッチ用
     *
     * @param siteType    サイト種別
     * @param currentTime バッチ起動時間
     * @return サイトマップXML用アンケート情報DTOリスト
     */
    @Select
    List<QuestionnaireEntity> getEntityForSiteMap(String siteType, Timestamp currentTime);
}
