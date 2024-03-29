/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockAddImportListDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockDetailsResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockDownloadCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockSearchResultDto;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.List;
import java.util.stream.Stream;

/**
 * 入荷お知らせ商品Daoクラス
 *
 * @author st75001
 */
@Dao
@ConfigAutowireable
public interface ReStockDao {

    /**
     * 入荷お知らせ商品検索結果一覧取得
     *
     * @param conditionDto  商品検索条件DTO(管理機能用)
     * @param selectOptions 検索オプション
     * @return 商品検索結果DTOリスト
     */
    @Select
    List<ReStockSearchResultDto> getSearchReStockForBackList(ReStockSearchForBackDaoConditionDto conditionDto,
                                                             SelectOptions selectOptions);

    /**
     * 入荷お知らせ商品詳細一覧取得
     *
     * @param key キー（商品SEQ+入荷状態+配信ID+配信状況)
     * @return 商品検索結果DTOリスト
     */
    @Select
    List<ReStockDetailsResultDto> getDetailsReStockForBackList(String key);

    /**
     * 商品一括CSVダウンロード
     *
     * @param conditionDto 商品検索条件DTO(管理機能用)
     * @return 入荷お知らせ商品CSVDTO
     */
    @Select
    Stream<ReStockDownloadCsvDto> exportCsvByReStockSearchForBackDaoConditionDto(ReStockSearchForBackDaoConditionDto conditionDto);

    /**
     * 商品CSVダウンロード
     *
     * @param keyList キー（商品SEQ+入荷状態+配信ID+配信状況)
     * @return 入荷お知らせ商品CSVDTO
     */
    @Select
    Stream<ReStockDownloadCsvDto> exportCsvByReStockList(List<String> keyList);

    /**
     * 商品CSVダウンロード(会員指定あり)
     *
     * @param keyList キー（商品SEQ+入荷状態+配信ID+配信状況+会員SEQ)
     * @return 入荷お知らせ商品CSVDTO
     */
    @Select
    Stream<ReStockDownloadCsvDto> exportCsvByReStockMembersList(List<String> keyList);

    /**
     * 入荷お知らせメールアドレス帳登録リスト
     *
     * @param keyList キー（商品SEQ+入荷状態+配信ID+配信状況)
     * @return ダウンロード取得
     */
    @Select
    List<ReStockAddImportListDto> getReStockAddImportList(List<String> keyList);

    /**
     * 入荷お知らせメールアドレス帳登録リスト(会員指定あり)
     *
     * @param keyList キー（商品SEQ+入荷状態+配信ID+配信状況+会員SEQ)
     * @return ダウンロード取得
     */
    @Select
    List<ReStockAddImportListDto> getReStockAddImportMembersList(List<String> keyList);

}
