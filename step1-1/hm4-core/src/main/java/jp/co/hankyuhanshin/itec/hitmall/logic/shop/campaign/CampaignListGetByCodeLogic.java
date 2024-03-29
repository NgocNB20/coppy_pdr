/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.campaign.CampaignSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;

import java.util.List;

/**
 * キャンペーンリスト取得ロジック<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
public interface CampaignListGetByCodeLogic {

    /**
     * ロジック実行<br/>
     *
     * @param campaignSearchForDaoConditionDto キャンペーン検索条件DTO
     * @return list キャンペーンエンティティリスト
     */
    List<CampaignEntity> execute(CampaignSearchForDaoConditionDto campaignSearchForDaoConditionDto);
}
