// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.restockannounce;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.restockannounce.RestockAnnounceSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.restockannounce.RestockAnnounceEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.List;

/**
 * 入荷お知らDAO<br/>
 *
 * @author Thang Doan (VJP)
 */
@Dao
@ConfigAutowireable
public interface RestockAnnounceDao {

    /**
     * 追加<br/>
     *
     * @param restockAnnounceEntity 入荷お知ら
     * @return 登録件数
     */
    @Insert(excludeNull = true)
    int insert(RestockAnnounceEntity restockAnnounceEntity);

    /**
     * 更新<br/>
     *
     * @param restockAnnounceEntity 入荷お知ら
     * @return 更新件数
     */
    @Update
    int update(RestockAnnounceEntity restockAnnounceEntity);

    /**
     * 登録若しくは更新<br/>
     *
     * @param restockAnnounceEntity 入荷お知ら
     * @return 登録若しくは更新件数
     */
    @Insert(sqlFile = true)
    int insertOrUpdate(RestockAnnounceEntity restockAnnounceEntity);

    /**
     * 会員の入荷お知らせ登録商品SEQリストを取得<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @return 入荷お知らせ商品登録件数
     */
    @Select
    List<Integer> getGoodsSeqList(Integer memberInfoSeq);

    /**
     * 入荷お知らせエンティティリスト取得<br/>
     *
     * @param conditionDto 入荷お知らせ検索条件DTO
     * @return 入荷お知らせエンティティリスト
     */
    @Select
    List<RestockAnnounceEntity> getSearchRestockAnnounceList(RestockAnnounceSearchForDaoConditionDto conditionDto,
                                                             SelectOptions selectOptions);
    // 2023-renew No65 from here

    /**
     * 複数削除<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsSeqs     商品SEQ配列
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteList(Integer memberInfoSeq, Integer[] goodsSeqs);

    /**
     * 会員に紐づく入荷お知ら入りを全て削除する<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteListByMemberInfoSeq(Integer memberInfoSeq);

    /**
     * 会員に紐づく入荷お知ら入りを削除する<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsCodes    商品コード配列
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteListByGoodsCode(Integer memberInfoSeq, String[] goodsCodes);
    // 2023-renew No65 to here
}
// 2023-renew No65 to here
