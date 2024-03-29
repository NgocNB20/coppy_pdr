/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.access;

import jp.co.hankyuhanshin.itec.hitmall.dto.access.AccessInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.access.CampaignCsvEffectDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.access.CampaignEffectDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.access.AccessInfoEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Stream;

/**
 * アクセス情報Daoクラス
 *
 * @author nakagawa
 * @version $Revision: 1.10 $
 */
@Dao
@ConfigAutowireable
public interface AccessInfoDao {

    /**
     * インサート<br/>
     *
     * @param accessInfoEntity アクセス情報エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(AccessInfoEntity accessInfoEntity);

    /**
     * アップデート<br/>
     *
     * @param accessInfoEntity アクセス情報エンティティ
     * @return 処理件数
     */
    @Update
    int update(AccessInfoEntity accessInfoEntity);

    /**
     * デリート<br/>
     *
     * @param accessInfoEntity アクセス情報エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(AccessInfoEntity accessInfoEntity);

    /**
     * 指定期間内のキャンペーンコードを取得<br/>
     * メソッドの説明・概要<br/>
     *
     * @param shopSeq   ショップSEQ
     * @param accessUid 端末識別情報
     * @param startDate 開始日
     * @param endDate   終了日
     * @return campaignCode キャンペーンコード
     */
    @Select
    String getEffectiveCampaignCode(Integer shopSeq, String accessUid, Timestamp startDate, Timestamp endDate);

    /**
     * エンティティ取得<br/>
     *
     * @param shopSeq       ショップSEQ
     * @param accessType    アクセス種別
     * @param accessDate    アクセス日付
     * @param siteType      サイト種別
     * @param goodsgroupseq 商品SEQ
     * @param accessUid     端末識別情報
     * @param campaignCode  キャンペーンコード
     * @return AccessInfoEntity アクセス情報エンティティ
     */
    @Select
    AccessInfoEntity getEntityForUpdate(Integer shopSeq,
                                        String accessType,
                                        Timestamp accessDate,
                                        String siteType,
                                        Integer goodsgroupseq,
                                        String accessUid,
                                        String campaignCode);

    /**
     * 広告効果リスト取得<br/>
     *
     * @param accessInfoSearchForDaoConditionDto アクセス情報Dao用検索条件Dto
     * @param selectOptions                      検索オプション
     * @return List<CampaignEffectDto> アクセス情報エンティティリスト
     */
    @Select
    List<CampaignEffectDto> getSearchAccessInfoList(AccessInfoSearchForDaoConditionDto accessInfoSearchForDaoConditionDto,
                                                    SelectOptions selectOptions);

    /**
     * 広告効果CSVリスト取得<br/>
     *
     * @param accessInfoSearchForDaoConditionDto アクセス情報Dao用検索条件Dto
     * @return
     */
    @Select
    Stream<CampaignCsvEffectDto> exportCsvSearchAccessInfoList(AccessInfoSearchForDaoConditionDto accessInfoSearchForDaoConditionDto);

    /**
     * アクセス数更新<br/>
     *
     * @param accessInfoEntity アクセス情報エンティティ
     * @return 処理件数
     */
    @Update(sqlFile = true)
    int updateAccessCount(AccessInfoEntity accessInfoEntity);

    /**
     * 指定期間内・指定件数のキャンペーン情報リストを取得<br/>
     *
     * @param shopSeq      ショップSEQ
     * @param accessUid    端末識別情報
     * @param prefix       接頭辞の正規表現
     * @param specifiedDay 指定期間
     * @param limit        上限数
     * @return アクセス情報リスト
     */
    @Select
    List<AccessInfoEntity> getEffectiveCampaignList(Integer shopSeq,
                                                    String accessUid,
                                                    String prefix,
                                                    int specifiedDay,
                                                    int limit);
}
