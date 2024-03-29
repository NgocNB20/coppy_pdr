/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;

/**
 * キャンペーン更新<br/>
 *
 * @author kimura
 * @version $Revision: 1.2 $
 *
 */
public interface CampaignUpdateService {

    /**
     * キャンペーン更新<br/>
     *
     * @param campaignEntity 広告エンティティ
     * @return int 更新件数
     */
    int execute(CampaignEntity campaignEntity);

}
