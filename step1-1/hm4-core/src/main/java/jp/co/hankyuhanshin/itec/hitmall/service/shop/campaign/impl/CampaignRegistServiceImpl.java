/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.CampaignRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignRegistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * キャンペーン登録<br/>
 *
 * @author kimura
 * @version $Revision: 1.3 $
 *
 */
@Component
public class CampaignRegistServiceImpl extends AbstractShopService implements CampaignRegistService {

    /** キャンペーン登録ロジック */
    private final CampaignRegistLogic campaignRegistLogic;

    @Autowired
    public CampaignRegistServiceImpl(CampaignRegistLogic campaignRegistLogic) {
        this.campaignRegistLogic = campaignRegistLogic;
    }

    /**
     * 広告情報登録<br/>
     *
     * @param campaignEntity 広告エンティティ
     * @return int 登録件数
     */
    @Override
    public int execute(CampaignEntity campaignEntity) {
        campaignEntity.setShopSeq(1001);
        return campaignRegistLogic.execute(campaignEntity);
    }

}
