/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionReplyTotalEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.sql.Timestamp;
import java.util.List;

/**
 * アンケート設問回答集計DAOクラス。
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Dao
@ConfigAutowireable
public interface QuestionReplyTotalDao {

    /**
     * アンケート設問回答集計を登録する。
     *
     * @param questionReplyTotal アンケート設問回答集計
     * @return 登録件数
     */
    @Insert(excludeNull = true)
    int insert(QuestionReplyTotalEntity questionReplyTotal);

    /**
     * アンケート設問回答集計を更新する。
     *
     * @param questionReplyTotal アンケート設問回答集計
     * @return 更新件数
     */
    @Update
    int update(QuestionReplyTotalEntity questionReplyTotal);

    /**
     * アンケート設問回答集計を削除する。
     *
     * @param questionReplyTotal アンケート設問回答集計
     * @return 削除件数
     */
    @Delete
    int delete(QuestionReplyTotalEntity questionReplyTotal);

    /**
     * アンケート設問回答集計を取得する。
     *
     * @param questionSeq アンケート設問SEQ
     * @return アンケート設問回答集計エンティティ
     */
    @Select
    List<QuestionReplyTotalEntity> getEntity(Integer questionSeq);

    /**
     * アンケート設問回答集計を削除する。
     * アンケート回答集計用。
     *
     * @param currentTime          現在日時
     * @param totalingCompleteTime 集計完了判定日時
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteForQuestionnaireTotaling(Timestamp currentTime, Timestamp totalingCompleteTime);

    /**
     * アンケート設問回答集計を登録する。
     * 　アンケート回答集計用。
     *
     * @param currentTime          現在日時
     * @param totalingCompleteTime 集計完了判定日時
     * @return 登録件数
     */
    @Insert(sqlFile = true, excludeNull = true)
    int insertForQuestionnaireTotaling(Timestamp currentTime, Timestamp totalingCompleteTime);

}
