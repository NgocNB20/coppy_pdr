/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.CampaignDeleteLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * キャンペーン削除<br/>
 *
 * @author kimura
 * @version $Revision: 1.2 $
 *
 */
@Component
public class CampaignDeleteServiceImpl extends AbstractShopService implements CampaignDeleteService {

    /** キャンペーンロジック */
    private final CampaignDeleteLogic campaignDeleteLogic;

    @Autowired
    public CampaignDeleteServiceImpl(CampaignDeleteLogic campaignDeleteLogic) {
        this.campaignDeleteLogic = campaignDeleteLogic;
    }

    /**
     * キャンペーン削除<br/>
     *
     * @param campaignEntity キャンペーンエンティティ
     * @return int 削除件数
     */
    @Override
    public int execute(CampaignEntity campaignEntity) {
        return campaignDeleteLogic.execute(campaignEntity);
    }

}
