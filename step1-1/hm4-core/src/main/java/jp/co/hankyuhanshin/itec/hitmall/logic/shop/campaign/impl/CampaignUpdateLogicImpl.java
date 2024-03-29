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
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.CampaignUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * キャンペーン更新ロジック<br/>
 *
 * @author ueshima
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class CampaignUpdateLogicImpl extends AbstractShopLogic implements CampaignUpdateLogic {

    /**
     * キャンペーンDao
     */
    private final CampaignDao campaignDao;

    @Autowired
    public CampaignUpdateLogicImpl(CampaignDao campaignDao) {
        this.campaignDao = campaignDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param campaignEntity キャンペーンエンティティ
     * @return 更新件数
     */
    @Override
    public int execute(CampaignEntity campaignEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("campaignEntity", campaignEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 更新日時の更新
        campaignEntity.setUpdateTime(dateUtility.getCurrentTime());

        // キャンペーンの更新
        return campaignDao.updateCampaign(campaignEntity);
    }

}
