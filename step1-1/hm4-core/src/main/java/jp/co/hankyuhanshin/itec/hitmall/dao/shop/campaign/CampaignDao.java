/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.shop.campaign;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.campaign.CampaignCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.campaign.CampaignSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
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
 * キャンペーンDaoクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Dao
@ConfigAutowireable
public interface CampaignDao {

    /**
     * インサート<br/>
     *
     * @param campaignEntity キャンペーンエンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(CampaignEntity campaignEntity);

    /**
     * アップデート<br/>
     *
     * @param campaignEntity キャンペーンエンティティ
     * @return 処理件数
     */
    @Update
    int update(CampaignEntity campaignEntity);

    /**
     * デリート<br/>
     *
     * @param campaignEntity キャンペーンエンティティ
     * @return 処理件数
     */
    @Delete
    int delete(CampaignEntity campaignEntity);

    /**
     * キャンペーン情報件数取得<br/>
     *
     * @param shopSeq ショップSEQ
     * @param campaignCode キャンペーンコード
     * @return キャンペーン情報件数
     */
    @Select
    int dataCheck(Integer shopSeq, String campaignCode);

    /**
     * キャンペーン情報取得<br/>
     *
     * @param shopSeq ショップSEQ
     * @param campaignCode キャンペーンコード
     * @return キャンペーンエンティティ
     */
    @Select
    CampaignEntity getCampaignByCode(Integer shopSeq, String campaignCode);

    /**
     * キャンペーン情報リスト取得<br/>
     *
     * @param campaignSearchForDaoConditionDto キャンペーン検索条件DTO
     * @return キャンペーンエンティティリスト
     */
    @Select
    List<CampaignEntity> getSearchCampaignList(CampaignSearchForDaoConditionDto campaignSearchForDaoConditionDto,
                                               SelectOptions selectOptions);

    /**
     * キャンペーン情報の更新<br/>
     *
     * @param campaignEntity キャンペーンエンティティ
     * @return 更新件数
     */
    @Update(sqlFile = true)
    int updateCampaign(CampaignEntity campaignEntity);

    /**
     * キャンペーンCSVダウンロード<br/>
     *
     * @param campaignSearchForDaoConditionDto キャンペーン検索条件DTO
     * @return ダウンロード取得
     */
    @Select
    Stream<CampaignCsvDto> exportCsvSearchCampaignList(CampaignSearchForDaoConditionDto campaignSearchForDaoConditionDto);
}
