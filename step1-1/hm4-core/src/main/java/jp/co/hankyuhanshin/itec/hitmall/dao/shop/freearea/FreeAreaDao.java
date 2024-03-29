/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.shop.freearea;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.freearea.FreeAreaSearchDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkContentsFeedTsvDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.sql.Timestamp;
import java.util.List;

/**
 * フリーエリアDao
 *
 * @author thang
 */
@Dao
@ConfigAutowireable
public interface FreeAreaDao {

    /**
     * インサート
     *
     * @param freeAreaEntity フリーエリア情報
     * @return 登録件数
     */
    @Insert(excludeNull = true)
    int insert(FreeAreaEntity freeAreaEntity);

    /**
     * アップデート
     *
     * @param freeAreaEntity フリーエリア情報
     * @return 更新件数
     */
    @Update
    int update(FreeAreaEntity freeAreaEntity);

    /**
     * デリート
     *
     * @param freeAreaEntity フリーエリア情報
     * @return 削除件数
     */
    @Delete
    int delete(FreeAreaEntity freeAreaEntity);

    /**
     * エンティティ取得
     *
     * @param freeAreaSeq フリーエリアSEQ
     * @return フリーエリアエンティティ
     */
    @Select
    FreeAreaEntity getEntity(Integer freeAreaSeq);

    /**
     * フリーエリア情報取得
     * <pre>
     * ・常時、「公開開始日時が　現在日時を含んで　過去」を条件とする
     * ・ソート順は「公開開始日時　降順」
     * ・取得件数は1件
     * </pre>
     *
     * @param shopSeq     ショップSEQ
     * @param freeAreaKey フリーエリアキー
     * @return フリーエリアエンティティ
     */
    @Select
    FreeAreaEntity getFreeAreaByKey(Integer shopSeq, String freeAreaKey);

    /**
     * フリーエリア情報取得
     * <pre>
     * ・常時、「公開開始日時が　現在日時を含んで　過去」を条件とする
     * ・ソート順は「公開開始日時　降順」
     * ・取得件数は1件
     * </pre>
     *
     * @param shopSeq       ショップSEQ
     * @param freeAreaKey   フリーエリアキー
     * @param memberInfoSeq 会員SEQ
     * @return フリーエリアエンティティ
     */
    @Select
    FreeAreaEntity getFreeAreaByKeyAndMember(Integer shopSeq, String freeAreaKey, Integer memberInfoSeq);

    /**
     * フリーエリア情報取得
     * <ul>
     *   <li>引数のショップSEQ、フリーエリアSEQが完全一致</li>
     *   <li>公開開始日時は条件に含まない</li>
     *   <li>ソート条件・取得件数上限は設定なし</li>
     * </ul>
     *
     * @param shopSeq     ショップSEQ
     * @param freeAreaSeq フリーエリアSEQ
     * @return フリーエリアエンティティ
     */
    @Select
    FreeAreaEntity getFreeAreaByShopSeq(Integer shopSeq, Integer freeAreaSeq);

    /**
     * フリーエリアリスト取得<br/>
     *
     * @param conditionDto  検索条件DTO
     * @param selectOptions 検索オプション
     * @return フリーエリアエンティティリスト
     */
    @Select
    List<FreeAreaEntity> getFreeAreaDtoExceptBodyList(FreeAreaSearchDaoConditionDto conditionDto,
                                                      SelectOptions selectOptions);

    /**
     * フリーエリアリスト取得
     * <ul>
     *   <li>引数のキー・公開開始日時が完全一致</li>
     *   <li>引数のフリーエリアSEQがnullでない場合、そのフリーエリアSEQは対象外とする</li>
     *   <li>ソート条件・取得件数上限は設定なし</li>
     * </ul>
     *
     * @param freeAreaEntity フリーエリアエンティティ
     * @return フリーエリアエンティティリスト
     */
    @Select
    List<FreeAreaEntity> getSameKeyOpenStartTimeEntityList(FreeAreaEntity freeAreaEntity);

    /**
     * サイトマップXML出力バッチ用
     *
     * @param urlType     URL種別
     * @param currentTime バッチ起動時間
     * @return フリーエリアエンティティリスト
     */
    @Select
    List<FreeAreaEntity> getEntityForSiteMap(String urlType, Timestamp currentTime);

    // 2023-renew No36-1, No61,67,95 from here

    /**
     * コンテンツフィード出力バッチ用
     *
     * @param currentTime バッチ起動時間
     * @return コンテンツフィードTSVDTOリスト
     */
    @Select
    List<UkContentsFeedTsvDto> getUkContentsFeedTsvList(Timestamp currentTime);
    // 2023-renew No36-1, No61,67,95 to here
}
