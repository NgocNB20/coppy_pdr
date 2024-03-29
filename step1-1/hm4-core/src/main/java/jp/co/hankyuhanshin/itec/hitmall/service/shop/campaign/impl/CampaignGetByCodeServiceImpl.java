/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.CampaignGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignGetByCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * キャンペーン取得<br/>
 *
 * @author kimura
 * @version $Revision: 1.3 $
 *
 */
@Component
public class CampaignGetByCodeServiceImpl extends AbstractShopService implements CampaignGetByCodeService {

    /** キャンペーン取得ロジック */
    private final CampaignGetByCodeLogic campaignGetByCodeLogic;

    @Autowired
    public CampaignGetByCodeServiceImpl(CampaignGetByCodeLogic campaignGetByCodeLogic) {
        this.campaignGetByCodeLogic = campaignGetByCodeLogic;
    }

    /**
     * キャンペーン取得<br/>
     *
     * @param campaignCode キャンペーンコード
     * @return キャンペーンエンティティ
     */
    @Override
    public CampaignEntity execute(String campaignCode) {
        return campaignGetByCodeLogic.execute(1001, campaignCode);
    }

}
