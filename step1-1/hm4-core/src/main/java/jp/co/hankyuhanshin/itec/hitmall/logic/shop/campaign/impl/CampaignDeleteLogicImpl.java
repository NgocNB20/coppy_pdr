/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeleteFlag;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.campaign.CampaignDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.CampaignDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * キャンペーン削除ロジック<br/>
 *
 * @author kimura
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 *
 */
@Component
public class CampaignDeleteLogicImpl extends AbstractShopLogic implements CampaignDeleteLogic {

    /**
     * キャンペーンDao
     */
    private final CampaignDao campaignDao;

    @Autowired
    public CampaignDeleteLogicImpl(CampaignDao campaignDao) {
        this.campaignDao = campaignDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param campaignEntity キャンペーンエンティティ
     * @return 削除件数
     */
    @Override
    public int execute(CampaignEntity campaignEntity) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("campaignEntity", campaignEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 更新日時の更新
        campaignEntity.setUpdateTime(dateUtility.getCurrentTime());
        campaignEntity.setDeleteFlag(HTypeDeleteFlag.ON);

        // キャンペーンの論理削除
        return campaignDao.updateCampaign(campaignEntity);
    }

}
