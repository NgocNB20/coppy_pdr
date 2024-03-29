/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.impl;

import jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.CampaignDataCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignDataCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * キャンペーン取得<br/>
 *
 * @author kimura
 * @version $Revision: 1.1 $
 *
 */
@Component
public class CampaignDataCheckServiceImpl extends AbstractShopService implements CampaignDataCheckService {

    /** キャンペーンデータ存在チェックロジック */
    private final CampaignDataCheckLogic campaignDataCheckLogic;

    @Autowired
    public CampaignDataCheckServiceImpl(CampaignDataCheckLogic campaignDataCheckLogic) {
        this.campaignDataCheckLogic = campaignDataCheckLogic;
    }

    /**
     * キャンペーン取得<br/>
     *
     * @param campaignCode キャンペーンコード
     * @return チェック結果
     */
    @Override
    public boolean execute(String campaignCode) {
        return campaignDataCheckLogic.execute(1001, campaignCode);
    }

}
