/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.campaign.CampaignDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.CampaignGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * キャンペーン取得（キャンペーンコード）ロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
@Component
public class CampaignGetByCodeLogicImpl extends AbstractShopLogic implements CampaignGetByCodeLogic {

    /**
     * キャンペーンDao
     */
    private final CampaignDao campaignDao;

    @Autowired
    public CampaignGetByCodeLogicImpl(CampaignDao campaignDao) {
        this.campaignDao = campaignDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param shopSeq ショップSEQ
     * @param campaignCode キャンペーンコード
     * @return キャンペーンエンティティ
     */
    @Override
    public CampaignEntity execute(Integer shopSeq, String campaignCode) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);
        ArgumentCheckUtil.assertNotNull("campaignCode", campaignCode);

        // キャンペーンの検索
        return campaignDao.getCampaignByCode(shopSeq, campaignCode);
    }

}
