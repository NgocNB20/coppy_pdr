/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.goods.favorite;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite.FavoriteSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.List;
import java.util.stream.Stream;

/**
 * お気に入り商品Daoクラス
 *
 * @author takasima
 */
@Dao
@ConfigAutowireable
public interface FavoriteSearchDao {

    /**
     * デリート
     *
     * @param favoriteEntity お気に入りエンティティ
     * @return 処理件数
     */
    @Delete
    int delete(FavoriteEntity favoriteEntity);

    /**
     * インサート
     *
     * @param favoriteEntity お気に入りエンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(FavoriteEntity favoriteEntity);

    /**
     * アップデート
     *
     * @param  favoriteEntity お気に入りエンティティ
     * @return 処理件数
     */
    @Update
    int update(FavoriteEntity favoriteEntity);

    /**
     * お気に入り商品検索結果一覧取得
     *
     * @param conditionDto  お気に入り商品検索条件DTO(管理機能用)
     * @param selectOptions 検索オプション
     * @return お気に入り商品検索結果DTOリスト
     */
    @Select
    List<FavoriteSearchResultDto> getSearchFavoriteForBackList(FavoriteSearchForBackDaoConditionDto conditionDto,
                                                               SelectOptions selectOptions);

    /**
     * お気に入り商品一括CSVダウンロード
     *
     * @param conditionDto お気に入り商品検索条件DTO(管理機能用)
     * @return ダウンロード取得
     */
    @Select
    Stream<FavoriteCsvDto> exportCsvByFavoriteSearchForBackDaoConditionDto(FavoriteSearchForBackDaoConditionDto conditionDto);

    /**
     * 商品CSVダウンロード
     *
     * @param favoriteSeqList お気に入り商品SEQリスト
     * @return ダウンロード取得
     */
    @Select
    Stream<FavoriteCsvDto> exportCsvByFavoriteSeqList(List<Integer> favoriteSeqList);
}
