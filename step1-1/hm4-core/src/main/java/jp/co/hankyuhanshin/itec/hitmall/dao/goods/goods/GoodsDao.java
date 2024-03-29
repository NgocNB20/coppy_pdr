/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitImageFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForBackDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsSearchResultForOrderRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsUnitDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Script;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.List;
import java.util.stream.Stream;

/**
 * 商品Daoクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Dao
@ConfigAutowireable
public interface GoodsDao {

    /**
     * 商品SEQ採番<br/>
     *
     * @return 商品SEQ
     */
    @Select
    int getGoodsSeqNextVal();

    /**
     * デリート
     *
     * @param goodsEntity 商品エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(GoodsEntity goodsEntity);

    /**
     * インサート
     *
     * @param goodsEntity 商品エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(GoodsEntity goodsEntity);

    /**
     * アップデート
     *
     * @param goodsEntity 商品エンティティ
     * @return 処理件数
     */
    @Update
    int update(GoodsEntity goodsEntity);

    /**
     * エンティティ取得
     *
     * @param goodsSeq 商品SEQ
     * @return 商品エンティティ
     */
    @Select
    GoodsEntity getEntity(Integer goodsSeq);

    /**
     * 商品情報取得
     *
     * @param shopSeq   ショップSEQ
     * @param goodsCode 商品コード
     * @return 商品エンティティ
     */
    @Select
    GoodsEntity getGoodsByCode(Integer shopSeq, String goodsCode);

    /**
     * 商品情報一覧取得
     *
     * @param conditionDto 商品検索条件DTO
     * @return 商品エンティティリスト
     */
    @Select
    List<GoodsEntity> getSearchGoodsList(GoodsSearchForDaoConditionDto conditionDto);

    /**
     * 商品情報一覧取得
     *
     * @param goodsSeqList 商品SEQリスト
     * @return 商品エンティティリスト
     */
    @Select
    List<GoodsEntity> getGoodsList(List<Integer> goodsSeqList);

    /**
     * 商品検索結果一覧取得
     *
     * @param conditionDto  商品検索条件DTO(管理機能用)
     * @param selectOptions 検索オプション
     * @return 商品検索結果DTOリスト
     */
    @Select
    List<GoodsSearchResultDto> getSearchGoodsForBackList(GoodsSearchForBackDaoConditionDto conditionDto,
                                                         SelectOptions selectOptions);

    /**
     * 商品詳細情報一覧取得
     *
     * @param goodsSeqList 商品SEQリスト
     * @return 商品詳細DTOリスト
     */
    @Select
    List<GoodsDetailsDto> getGoodsDetailsList(List<Integer> goodsSeqList);

    /**
     * 新規受注登録用の商品検索結果一覧取得
     *
     * @param conditionDto  商品検索条件DTO(管理機能用)
     * @param selectOptions 検索オプション
     * @return 商品検索結果DTOリスト
     */
    @Select
    List<GoodsSearchResultForOrderRegistDto> getSearchGoodsForOrderRegist(GoodsSearchForBackDaoConditionDto conditionDto,
                                                                          SelectOptions selectOptions);

    /**
     * 商品情報リスト取得（商品グループSEQ）
     *
     * @param goodsGroupSeq 商品グループSEQリスト
     * @return 商品エンティティリスト
     */
    @Select
    List<GoodsEntity> getGoodsListByGoodsGroupSeq(Integer goodsGroupSeq);

    /**
     * 商品コード指定、商品詳細情報の取得<br/>
     *
     * @param shopSeq         ショップSEQ
     * @param goodsCode       商品コード
     * @param siteType        サイト区分
     * @param goodsOpenStatus 商品公開状態
     * @return 商品詳細DTO
     */
    @Select
    GoodsDetailsDto getGoodsDetailsByShopSeqAndCode(Integer shopSeq,
                                                    String goodsCode,
                                                    HTypeSiteType siteType,
                                                    HTypeOpenDeleteStatus goodsOpenStatus);

    /**
     * 商品一括CSVダウンロード
     *
     * @param conditionDto 商品検索条件DTO(管理機能用)
     * @return ダウンロード取得
     */
    @Select
    Stream<GoodsCsvDto> exportCsvByGoodsSearchForBackDaoConditionDto(GoodsSearchForBackDaoConditionDto conditionDto);

    /**
     * 商品CSVダウンロード
     *
     * @param goodsSeqList 商品SEQリスト
     * @return ダウンロード取得
     */
    @Select
    Stream<GoodsCsvDto> exportCsvByGoodsSeqList(List<Integer> goodsSeqList);

    /**
     * 商品表示順更新
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @param goodsSeq      商品SEQ
     * @return 処理件数
     */
    @Update(sqlFile = true)
    int updateOrderDisplay(Integer goodsGroupSeq, Integer goodsSeq);

    // 2023-renew No76 from here

    /**
     * 規格画像有無更新
     */
    @Update(sqlFile = true)
    int updateUnitImageFlag(HTypeUnitImageFlag unitImageFlag, Integer goodsSeq);
    
    // 2023-renew No76 to here

    /**
     * 商品テーブルロック<br/>
     */
    @Script
    void updateLockTableShareModeNowait();

    /**
     * 公開状態&販売状態をチェック<br/>
     *
     * @param shopSeq   ショップSEQ
     * @param goodsCode 商品コード
     * @return 件数
     */
    @Select
    Integer getStatus(Integer shopSeq, String goodsCode);

    /**
     * 規格表示順重複番号リストを取得<br/>
     *
     * @param shopSeq       ショップSEQ
     * @param goodsGroupSeq 商品グループコード
     * @return 規格表示順重複番号リスト
     */
    @Select
    List<Integer> getDupulicateOrderDisplayList(Integer shopSeq, Integer goodsGroupSeq);

    /**
     * 規格値1リスト取得処理<br/>
     *
     * @param ggcd 商品グループコード
     * @param gcd  商品コード
     * @return 規格値1リスト
     */
    @Select
    List<GoodsUnitDto> getUnit1ListByGoodsCode(String ggcd, String gcd);

    /**
     * 規格値2リスト取得処理<br/>
     *
     * @param ggcd 商品グループコード
     * @param gcd  商品コード
     * @return 規格値2リスト
     */
    @Select
    List<GoodsUnitDto> getUnit2ListByGoodsCode(String ggcd, String gcd);

    /**
     * 規格が重複している件数を取得
     * <pre>
     * 登録（更新）対象の行を含めないため、goodsCodeを指定
     *
     * 以下のデータを登録しているケースで、パラメータにgoodsCodeが無いと
     * 3行全てがチェック対象となってしまう
     *
     * goodsGroupCode | goodsCode | 規格1 | 規格2
     * A0001          | A0001A    | 赤
     * A0001          | A0001B    | 白
     * A0001          | A0001C    | 黒
     * </pre>
     *
     * @param goodsGroupCode 商品管理番号
     * @param goodsCode      商品番号
     * @param unitvalue1     規格1
     * @param unitvalue2     規格2
     * @return 件数
     */
    @Select
    Integer getDuplicateCountByUnitvalue(String goodsGroupCode, String goodsCode, String unitvalue1, String unitvalue2);

    /**
     * 商品情報リスト取得（商品コード）
     *
     * @param goodsCodeList 商品コードリスト
     * @return 商品エンティティリスト
     */
    @Select
    List<String> getEntityListByGoodsCodeList(List<String> goodsCodeList);

    /**
     * 商品詳細情報一覧取得
     *
     * @param goodsCodeList 商品コードリスト
     * @return 商品詳細DTOリスト
     */
    @Select
    List<GoodsDetailsDto> getGoodsDetailsListByGoodsCodeList(List<String> goodsCodeList);

    /**
     * 商品情報リスト取得（商品管理番号）
     *
     * @param shopSeq        ショップSEQ (必須)
     * @param goodsGroupCode 商品管理番号 (必須)
     * @return 商品エンティティリスト
     */
    @Select
    List<GoodsEntity> getGoodsListByGoodsGroupCode(Integer shopSeq, String goodsGroupCode);

    // 2023-renew No92 from here
    /**
     * 商品詳細情報一覧取得
     *
     * @param goodsCodeList 商品コードリスト
     * @return 商品エンティティリスト
     */
    @Select
    List<GoodsEntity> getGoodsListByGoodsCodeList(List<String> goodsCodeList);
    // 2023-renew No92 to here

    // PDR Migrate Customization from here

    /**
     * 定期便対象の商品SEQリストを取得します。<br/>
     *
     * @param targetType         定期便対象区分
     * @param permissionTypeList 受付可否区分
     * @param goodsCodeList      商品コードリスト
     * @param limit              取得上限
     * @return 定期便対象商品SEQリスト
     */
    //    @Select
    //    List<Integer> getGoodsSeqListForPurchasedCommodityInformation(HTypeSubscriptionTargetType targetType, List<String> permissionTypeList, List<String> goodsCodeList, int limit);

    /**
     * 定期便対象の商品SEQリストを取得します。<br/>
     *
     * @param targetType               定期便対象区分
     * @param permissionTypeList       受付可否区分
     * @param recommendedFlag          定期便おすすめフラグ
     * @param salesAdvisabilitySeqList 販売可否判定SEQ
     * @return 定期便対象商品SEQリスト
     */
    //    @Select
    //    List<Integer> getGoodsSeqListForRecomendedInformation(HTypeSubscriptionTargetType targetType, List<String> permissionTypeList, HTypeSubscriptionRecommendedFlag recommendedFlag, List<Integer> salesAdvisabilitySeqList);

    // PDR Migrate Customization to here
}
