/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.conveni;

import jp.co.hankyuhanshin.itec.hitmall.entity.conveni.ConvenienceEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * コンビニ名称Daoクラス
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface ConvenienceDao {

    /**
     * インサート
     *
     * @param convenienceEntity コンビニ名称エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(ConvenienceEntity convenienceEntity);

    /**
     * アップデート
     *
     * @param convenienceEntity コンビニ名称エンティティ
     * @return 処理件数
     */
    @Update
    int update(ConvenienceEntity convenienceEntity);

    /**
     * デリート
     *
     * @param convenienceEntity コンビニ名称エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(ConvenienceEntity convenienceEntity);

    /**
     * エンティティ取得
     *
     * @param conveniSeq コンビニSEQ
     * @return コンビニ名称エンティティ
     */
    @Select
    ConvenienceEntity getEntity(Integer conveniSeq);

    /**
     * コンビニ名称リスト取得
     *
     * @param shopSeq           ショップSEQ
     * @param limitToUseConveni 使うコンビニに取得を制限するか true:制限する
     * @return コンビニ名称エンティティリスト
     */
    @Select
    List<ConvenienceEntity> getConvenienceList(Integer shopSeq, boolean limitToUseConveni);

    /**
     * エンティティ取得
     *
     * @param shopSeq     ショップSEQ
     * @param conveniCode 選択コンビニコード
     * @return コンビニ名称エンティティ
     */
    @Select
    ConvenienceEntity getEntityByConveniCode(Integer shopSeq, String conveniCode);

}
