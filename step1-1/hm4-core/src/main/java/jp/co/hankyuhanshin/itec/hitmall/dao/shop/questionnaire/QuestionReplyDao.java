/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionReplyEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * アンケート設問回答DAOクラス。<br />
 *
 * @author matsuda
 */
@Dao
@ConfigAutowireable
public interface QuestionReplyDao {

    /**
     * アンケート設問回答を登録する。
     *
     * @param questionReply アンケート回答
     * @return 登録件数
     */
    @Insert(excludeNull = true)
    int insert(QuestionReplyEntity questionReply);

    /**
     * アンケート設問回答を更新する。
     *
     * @param questionReply アンケート回答
     * @return 更新件数
     */
    @Update
    int update(QuestionReplyEntity questionReply);

    /**
     * アンケート設問回答を削除する。
     *
     * @param questionReply アンケート回答
     * @return 削除件数
     */
    @Delete
    int delete(QuestionReplyEntity questionReply);

}
