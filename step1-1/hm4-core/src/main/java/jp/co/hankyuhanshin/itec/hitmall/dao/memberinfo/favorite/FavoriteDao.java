/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.favorite;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.favorite.FavoriteSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.List;

/**
 * お気に入りDAO<br/>
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface FavoriteDao {

    /**
     * 追加<br/>
     *
     * @param favoriteEntity お気に入り情報
     * @return 登録件数
     */
    @Insert(excludeNull = true)
    int insert(FavoriteEntity favoriteEntity);

    /**
     * 更新<br/>
     *
     * @param favoriteEntity お気に入り情報
     * @return 更新件数
     */
    @Update
    int update(FavoriteEntity favoriteEntity);

    /**
     * 削除<br/>
     *
     * @param favoriteEntity お気に入り情報
     * @return 削除件数
     */
    @Delete
    int delete(FavoriteEntity favoriteEntity);

    /**
     * エンティティ取得<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsSeq      商品SEQ
     * @return お気に入りエンティティ
     */
    @Select
    FavoriteEntity getEntity(Integer memberInfoSeq, Integer goodsSeq);

    /**
     * お気に入りエンティティリスト取得<br/>
     *
     * @param conditionDto お気に入り検索条件DTO
     * @return お気に入りエンティティリスト
     */
    @Select
    List<FavoriteEntity> getSearchFavoriteList(FavoriteSearchForDaoConditionDto conditionDto,
                                               SelectOptions selectOptions);

    /**
     * 会員のお気に入り登録商品SEQリストを取得<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @return お気に入り商品登録件数
     */
    @Select
    List<Integer> getGoodsSeqList(Integer memberInfoSeq);

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
     * 会員に紐づくお気に入り商品を全て削除する<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteListByMemberInfoSeq(Integer memberInfoSeq);

    /**
     * 会員に紐づくお気に入り商品を削除する<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsCodes    商品コード配列
     * @return 削除件数
     */
    @Delete(sqlFile = true)
    int deleteListByGoodsCode(Integer memberInfoSeq, String[] goodsCodes);

    /**
     * お気に入り取得
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsSeqList  商品SEQリスト
     * @return お気に入りリスト
     */
    @Select
    List<FavoriteEntity> getFavoriteEntityList(Integer memberInfoSeq, List<Integer> goodsSeqList);

    /**
     * 商品に紐づくお気に入り登録商品リストを取得<br/>
     *
     * @param goodsSeqList 商品SEQリスト
     * @return お気に入り商品登録件数
     */
    @Select
    List<FavoriteEntity> getFavoriteEntityListByGoodsSeqList(List<Integer> goodsSeqList);

}
