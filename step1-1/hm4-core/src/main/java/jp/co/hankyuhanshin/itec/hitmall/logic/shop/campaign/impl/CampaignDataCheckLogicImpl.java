/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.campaign.CampaignDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.CampaignDataCheckLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * キャンペーンデータチェックロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
@Component
public class CampaignDataCheckLogicImpl extends AbstractShopLogic implements CampaignDataCheckLogic {

    /**
     * キャンペーンDao
     */
    private final CampaignDao campaignDao;

    @Autowired
    public CampaignDataCheckLogicImpl(CampaignDao campaignDao) {
        this.campaignDao = campaignDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param shopSeq ショップSEQ
     * @param campaignCode キャンペーンコード
     * @return true データあり、false データなし
     */
    @Override
    public boolean execute(Integer shopSeq, String campaignCode) {
        return campaignDao.dataCheck(shopSeq, campaignCode) != 0;
    }

}
