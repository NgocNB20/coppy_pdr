/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;

/**
 * キャンペーン更新ロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
public interface CampaignUpdateLogic {

    /**
     * ロジック実行<br/>
     *
     * @param campaignEntity キャンペーンエンティティ
     * @return 更新件数
     */
    int execute(CampaignEntity campaignEntity);
}
