/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireReplyTotalEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.sql.Timestamp;

/**
 * アンケート回答集計DAOクラス。<br />
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Dao
@ConfigAutowireable
public interface QuestionnaireReplyTotalDao {

    /**
     * アンケート回答集計を登録する。
     *
     * @param questionnaireReplyTotal アンケート回答集計
     * @return 登録件数
     */
    @Insert(excludeNull = true)
    int insert(QuestionnaireReplyTotalEntity questionnaireReplyTotal);

    /**
     * アンケート回答集計を更新する。
     *
     * @param questionnaireReplyTotal アンケート回答集計
     * @return 更新件数
     */
    @Update
    int update(QuestionnaireReplyTotalEntity questionnaireReplyTotal);

    /**
     * アンケート回答集計を削除する。
     *
     * @param questionnaireReplyTotal アンケート回答集計
     * @return 削除件数
     */
    @Delete
    int delete(QuestionnaireReplyTotalEntity questionnaireReplyTotal);

    /**
     * アンケート回答集計を取得する。
     *
     * @param questionnaireSeq アンケートSEQ
     * @return アンケート回答集計エンティティ
     */
    @Select
    QuestionnaireReplyTotalEntity getEntity(Integer questionnaireSeq);

    /**
     * アンケート回答集計から最新の登録日時を取得する。
     *
     * @return 登録日時
     */
    @Select
    Timestamp getMaxRegistTime();

    /**
     * アンケート回答集計情報を削除する。
     * アンケート回答集計用
     *
     * @param currentTime          現在日時
     * @param totalingCompleteTime 集計完了判定日時
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteForQuestionnaireTotaling(Timestamp currentTime, Timestamp totalingCompleteTime);

    /**
     * アンケート回答集計情報を登録する。
     * アンケート回答集計用
     *
     * @param currentTime          現在日時
     * @param totalingCompleteTime 集計完了判定日時
     * @return 登録件数
     */
    @Insert(sqlFile = true, excludeNull = true)
    int insertForQuestionnaireTotaling(Timestamp currentTime, Timestamp totalingCompleteTime);
}
