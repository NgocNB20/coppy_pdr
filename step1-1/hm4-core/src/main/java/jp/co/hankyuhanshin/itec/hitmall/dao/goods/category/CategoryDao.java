/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.goods.category;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCategoryType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryExclusiveDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryGoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategoryGoodsSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.category.CategorySearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkGoodsFeedCategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.category.CategoryEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Script;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * カテゴリDaoクラス
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface CategoryDao {

    /**
     * インサート
     *
     * @param categoryEntity カテゴリエンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(CategoryEntity categoryEntity);

    /**
     * アップデート
     *
     * @param categoryEntity カテゴリエンティティ
     * @return 処理件数
     */
    @Update
    int update(CategoryEntity categoryEntity);

    /**
     * デリート
     *
     * @param categoryEntity カテゴリエンティティ
     * @return 処理件数
     */
    @Delete
    int delete(CategoryEntity categoryEntity);

    /**
     * カテゴリSEQ採番<br/>
     *
     * @return カテゴリSEQ
     */
    @Select
    int getCategorySeqNextVal();

    /**
     * エンティティ取得
     *
     * @param categorySeq カテゴリSEQ
     * @return カテゴリエンティティ
     */
    @Select
    CategoryEntity getEntity(Integer categorySeq);

    /**
     * エンティティ取得
     *
     * @param shopSeq    ショップSEQ
     * @param categoryId カテゴリID
     * @return カテゴリエンティティ
     */
    @Select
    CategoryEntity getCategory(Integer shopSeq, String categoryId);

    /**
     * カテゴリ詳細DTOリスト取得
     *
     * @param conditionDto      カテゴリ検索条件DTO
     * @param currentCategoryId 現在のカテゴリID
     * @return カテゴリ詳細DTOリスト
     */
    @Select
    List<CategoryDetailsDto> getSearchCategoryList(CategorySearchForDaoConditionDto conditionDto,
                                                   String currentCategoryId);

    /**
     * 指定されたカテゴリIDに紐づく、親カテゴリと子カテゴリの詳細DTOリスト取得
     *
     * @param categoryId カテゴリID
     * @return カテゴリ詳細DTOリスト
     */
    @Select
    List<CategoryDetailsDto> getCategoryListByCategoryId(String categoryId);

    /**
     * カテゴリエンティティリスト取得
     *
     * @param conditionDto カテゴリ検索条件DTO
     * @return カテゴリエンティティリスト
     */
    @Select
    List<CategoryEntity> getCategoryList(CategorySearchForDaoConditionDto conditionDto);

    /**
     * カテゴリ排他取得（カテゴリSEQリスト）
     *
     * @param categorySeqList カテゴリSEQリスト
     * @return カテゴリエンティティリスト
     */
    @Select
    List<CategoryEntity> getCategoryBySeqForUpdate(List<Integer> categorySeqList);

    /**
     * 選択項目用リスト取得<br/>
     *
     * @param shopSeq ショップSEQ
     * @return 問い合わせ結果を格納したMap
     */
    @Select
    List<Map<String, Object>> getItemMapList(Integer shopSeq);

    /**
     * カテゴリエンティティリスト取得（カテゴリSEQ）
     *
     * @param categorySeqList カテゴリSEQリスト
     * @return カテゴリエンティティリスト
     */
    @Select
    List<CategoryEntity> getCategoryListBySeqList(List<Integer> categorySeqList);

    /**
     * 指定レベルの最大の並び順を取得
     *
     * @param shopSeq         ショップSEQ
     * @param categorySeqPath カテゴリSEQパス
     * @param categoryLevel   レベル
     * @return int 指定レベルの最大の並び順
     */
    @Select
    int getMaxOrderdisplay(Integer shopSeq, String categorySeqPath, Integer categoryLevel);

    // 2023-renew categoryCSV from here

    /**
     * カテゴリCsvダウンロード<br/>
     *
     * @param conditionDto   カテゴリ検索条件Dto
     * @return
     */
    @Select
    Stream<CategoryCsvDto> exportCsvByCategorySearchForDaoConditionDto(CategorySearchForDaoConditionDto conditionDto);
    // 2023-renew categoryCSV to here

    /**
     * カテゴリテーブルロック<br/>
     */
    @Script
    void updateLockTableShareModeNowait();

    /**
     * 指定「categorySeqPath」「categorylevel」、「orderdisplay」に紐づくカテゴリIDリストを取得
     * (並び順の連番用)
     *
     * @param shopSeq         ショップSEQ
     * @param categorySeqPath 親のカテゴリSEQパス
     * @param categoryLevel   レベル
     * @param orderdisplay    表示順
     * @return カテゴリIDリスト
     */
    @Select
    List<String> getSortOrderdisplayCategoryId(Integer shopSeq,
                                               String categorySeqPath,
                                               Integer categoryLevel,
                                               Integer orderdisplay);

    /**
     * カテゴリIDに紐づく公開商品ｸﾞﾙｰﾌﾟ数を取得
     * ※カテゴリIDに紐づく子階層の公開商品数も合わせて取得する
     *
     * @param categorySeqList 取得するカテゴリのカテゴリSEQリスト
     * @return 公開商品件数
     */
    @Select
    List<CategoryEntity> getCategoryOpenGoodsCount(List<Integer> categorySeqList);

    /**
     * カテゴリに紐づく商品ｸﾞﾙｰﾌﾟﾘｽﾄを取得
     *
     * @param conditionDto  カテゴリ情報Dao用検索条件Dto
     * @param selectOptions Doma検索オプション
     * @return 商品ｸﾞﾙｰﾌﾟﾘｽﾄ
     */
    @Select
    List<CategoryGoodsDetailsDto> getCategoryGoodsList(CategoryGoodsSearchForDaoConditionDto conditionDto,
                                                       SelectOptions selectOptions);

    /**
     * 現在のMAXSEQを取得
     *
     * @return MAXSEQ
     */
    @Select
    int getCategoryMaxCategorySeq();

    /**
     * カテゴリ情報取得<br/>
     *
     * @param conditionDto カテゴリ検索条件DTO
     * @return カテゴリ情報
     */
    @Select
    CategoryDetailsDto getCategoryInfo(CategorySearchForDaoConditionDto conditionDto);

    /**
     * カテゴリ詳細DTOリスト取得
     *
     * @param conditionDto カテゴリ検索条件DTO
     * @return カテゴリ詳細DTOリスト
     */
    @Select
    List<CategoryDetailsDto> getCategoryInfoList(CategorySearchForDaoConditionDto conditionDto);

    /**
     * サイトマップXML出力バッチ用
     *
     * @param siteType    サイト種別
     * @param currentTime バッチ起動時間
     * @return カテゴリリスト
     */
    @Select
    List<CategoryEntity> getCategoryForSiteMap(String siteType, Timestamp currentTime);

    /**
     * Method to get list of CategoryDetailsDto by list of category Id
     *
     * @param categoryIdList categoryIdList
     * @param shopSeq        shopSeq
     * @param siteType       siteType
     * @param openStatus     openStatus
     * @return list of CategoryDetailsDto
     */
    @Select
    List<CategoryDetailsDto> getCategoryDetailsDtoListByCategoryId(List<String> categoryIdList,
                                                                   int shopSeq,
                                                                   HTypeSiteType siteType,
                                                                   HTypeOpenStatus openStatus);

    /**
     * カテゴリ排他取得
     *
     * @return カテゴリ排他Dtoクラス
     */
    @Select
    CategoryExclusiveDto getExclusiveCategory();

    /**
     * 子階層のカテゴリーパスを更新
     *
     * @param categoryEntity カテゴリーEntity
     * @return 処理件数
     */
    @Update
    int updateCategoryPath(CategoryEntity categoryEntity);

    // PDR Migrate Customization from here

    /**
     * 公開中のカテゴリーデータを取得します。（JSON用）
     *
     * @param conditionDto       カテゴリ検索条件DTO
     * @param startCategorylevel 開始カテゴリー階層
     * @param endCategorylevel   終了カテゴリー階層
     * @param categoryType       カテゴリー種類
     * @return カテゴリー詳細DTOリスト
     */
    @Select
    List<CategoryDetailsDto> getCategoryDataJsonList(CategorySearchForDaoConditionDto conditionDto,
                                                     Integer startCategorylevel,
                                                     Integer endCategorylevel,
                                                     HTypeCategoryType categoryType);

    /**
     * カテゴリーIDツリー取得
     *
     * @param shopSeq ショップSEQ
     * @param categoryId カテゴリーID
     * @return カテゴリーIDツリーリスト
     */
    @Select
    List<String> getCategoryIdTree(int shopSeq, String categoryId);

    // PDR Migrate Customization to here

    // 2023-renew No3 from here

    /**
     * 商品フィード連携用のカテゴリを取得する
     * @return カテゴリリスト
     */
    @Select
    List<UkGoodsFeedCategoryDto> getUkGoodsFeedCategory(String delimiter);
    // 2023-renew No3 to here
}
