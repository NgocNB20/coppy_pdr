/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.CampaignUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 広告情報更新<br/>
 *
 * @author kimura
 * @version $Revision: 1.2 $
 *
 */
@Component
public class CampaignUpdateServiceImpl extends AbstractShopService implements CampaignUpdateService {

    /** キャンペーン更新ロジック */
    private final CampaignUpdateLogic campaignUpdateLogic;

    @Autowired
    public CampaignUpdateServiceImpl(CampaignUpdateLogic campaignUpdateLogic) {
        this.campaignUpdateLogic = campaignUpdateLogic;
    }

    /**
     * キャンペーン更新<br/>
     *
     * @param campaignEntity 広告エンティティ
     * @return int 更新件数
     */
    @Override
    public int execute(CampaignEntity campaignEntity) {
        return campaignUpdateLogic.execute(campaignEntity);
    }

}
