/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupForSiteMapDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkGoodsFeedTsvDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
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
import java.util.stream.Stream;

/**
 * 商品グループDaoクラス
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface GoodsGroupDao {

    /**
     * 固定文字列 "円"
     */
    public static final String YEN = "円";
    /**
     * 固定文字列 "～"
     */
    public static final String MARK = "～";

    /**
     * 商品グループSEQ採番<br/>
     *
     * @return 商品グループSEQ
     */
    @Select
    int getGoodsGroupSeqNextVal();

    /**
     * インサート
     *
     * @param goodsGroup 商品グループエンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(GoodsGroupEntity goodsGroup);

    /**
     * アップデート
     *
     * @param goodsGroup 商品グループエンティティ
     * @return 処理件数
     */
    @Update
    int update(GoodsGroupEntity goodsGroup);

    /**
     * デリート
     *
     * @param goodsGroup 商品グループエンティティ
     * @return 処理件数
     */
    @Delete
    int delete(GoodsGroupEntity goodsGroup);

    /**
     * エンティティ取得
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @return 商品グループエンティティ
     */
    @Select
    GoodsGroupEntity getEntity(Integer goodsGroupSeq);

    /**
     * 商品グループ詳細リスト取得<br/>
     * <pre>
     * 商品を取得するとともに、セール情報を取得する。<br/>
     * 取得するセールは一意となる。<br/>
     * ●取得セール
     * 通常          ：開催中かつ公開中
     * シークレット ：(開催中かつ公開中)or通常商品公開状態が「非公開」…未認証のセール対象商品を表示しないため
     * 予約          ：公開中かつ（開催前or開催中）
     * </pre>
     *
     * @param conditionDto  商品グループ検索条件DTO
     * @param selectOptions 検索オプション
     * @return 商品グループ詳細DTOリスト
     */
    @Select
    List<GoodsGroupDetailsDto> getSearchGoodsGroupDetailsList(GoodsGroupSearchForDaoConditionDto conditionDto,
                                                              SelectOptions selectOptions);

    /**
     * 商品グループリスト取得
     *
     * @param goodsGroupSeqList 商品グループSEQリスト
     * @return 商品グループエンティティリスト
     */
    @Select
    List<GoodsGroupEntity> getGoodsGroupList(List<Integer> goodsGroupSeqList);

    /**
     * 商品グループレコード排他取得
     *
     * @param goodsGroupSeqList 商品グループSEQリスト
     * @param versionNo         更新カウンタ
     * @return 商品グループエンティティリスト
     */
    @Select
    List<GoodsGroupEntity> getGoodsGroupBySeqForUpdate(List<Integer> goodsGroupSeqList, Integer versionNo);

    /**
     * 商品グループ情報取得(商品グループコード）
     *
     * @param shopSeq        ショップSEQ
     * @param goodsGroupCode 商品グループコード
     * @param goodsCode      商品コード
     * @param siteType       サイト区分
     * @param openStatus     公開状態
     * @return 商品グループエンティティ
     */
    @Select
    GoodsGroupEntity getGoodsGroupByCode(Integer shopSeq,
                                         String goodsGroupCode,
                                         String goodsCode,
                                         HTypeSiteType siteType,
                                         HTypeOpenDeleteStatus openStatus);

    /**
     * 商品グループ情報取得(ショップSEQ、商品グループコードリスト）
     *
     * @param shopSeq            ショップSEQ
     * @param goodsGroupCodeList 商品グループコードリスト
     * @return 商品グループエンティティリスト
     */
    @Select
    List<GoodsGroupEntity> getGoodsGroupByCodeList(Integer shopSeq, List<String> goodsGroupCodeList);

    /**
     * 商品グループテーブルロック<br/>
     */
    @Script
    void updateLockTableShareModeNowait();

    /**
     * 商品グループテーブルロック<br/>
     */
    @Script
    void updateLockTableExclusiveModeWait();

    /**
     * 商品グループ情報取得(商品グループコード）
     * ※商品グループコードの重複チェック用
     *
     * @param shopSeq        ショップSEQ
     * @param goodsGroupCode 商品グループコード
     * @return 商品グループエンティティ
     */
    @Select
    GoodsGroupEntity checkDuplicateByCode(Integer shopSeq, String goodsGroupCode);

    /**
     * サイトマップXML出力バッチ用
     *
     * @param siteType    サイト種別
     * @param urlType     URL種別
     * @param currentTime バッチ起動時間
     * @return サイトマップXML用商品グループ情報DTOリスト
     */
    @Select
    List<GoodsGroupForSiteMapDto> getGoodsGroupForSiteMap(String siteType, String urlType, Timestamp currentTime);

    // 2023-renew No3 from here

    /**
     * 商品フィード出力バッチ用
     *
     * @param currentTime バッチ起動時間
     * @return 商品フィードTSVDTOリスト
     */
    @Select
    List<UkGoodsFeedTsvDto> getUkGoodsFeedTsvList(Timestamp currentTime);
    // 2023-renew No3 to here

    // 2023-renew No92 from here

    /**
     * 商品グループリスト取得
     *
     * @param goodsGroupSeqs 商品グループSEQリスト
     * @return 商品グループエンティティリスト
     */
    @Select
    List<GoodsGroupEntity> getGoodsGroupListBySeqList(List<Integer> goodsGroupSeqs);
    // 2023-renew No92 to here

    // 2023-renew No21 from here

    /**
     * [半年以内に購入された商品]から
     * 商品グループSEQを取得する。
     *
     * @return 商品グループSEQリスト
     */
    @Select
    List<Integer> getGoodsGroupSeqList();
    // 2023-renew No21 to here
}
