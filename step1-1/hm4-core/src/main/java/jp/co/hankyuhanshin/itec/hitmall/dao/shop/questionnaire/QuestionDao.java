/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * アンケート設問DAOクラス。<br />
 * <pre>
 * versionNoを利用した排他制御を行う為、CheckSingleRowUpdateを付与していない。
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Dao
@ConfigAutowireable
public interface QuestionDao {

    /**
     * アンケート設問を登録する。
     *
     * @param questionEntity アンケート設問
     * @return 登録件数
     */
    @Insert(excludeNull = true)
    int insert(QuestionEntity questionEntity);

    /**
     * アンケート設問を更新する。
     *
     * @param questionEntity アンケート設問
     * @return 更新件数
     */
    @Update
    int update(QuestionEntity questionEntity);

    /**
     * アンケート設問を削除する。
     *
     * @param questionEntity アンケート設問
     * @return 削除件数
     */
    @Delete
    int delete(QuestionEntity questionEntity);

    /**
     * アンケート設問を取得する。
     *
     * @param questionSeq アンケート設問SEQ
     * @return アンケートエンティティ
     */
    @Select
    QuestionEntity getEntity(Integer questionSeq);

    /**
     * アンケートSEQからアンケート設問情報を取得する。<br />
     * <pre>
     * 取得できない場合はNULL。
     * 更新・削除画面表示で利用する。
     * </pre>
     *
     * @param questionnaireSeq アンケートSEQ（FK）
     * @return アンケート設問リスト
     */
    @Select
    List<QuestionEntity> getEntityByQuestionnaireSeq(Integer questionnaireSeq);

    /**
     * アンケートSEQ、公開状態からアンケート設問情報を取得する。<br />
     * <pre>
     * 取得できない場合はNULL。
     * 更新・削除画面表示で利用する。
     * </pre>
     *
     * @param questionnaireSeq アンケートSEQ（FK）
     * @param siteType         サイト種別
     * @return アンケート設問リスト
     */
    @Select
    List<QuestionEntity> getEntityByQuestionnaireSeqAndOpenstatus(Integer questionnaireSeq, HTypeSiteType siteType);
}
