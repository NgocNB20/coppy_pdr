/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;

/**
 * キャンペーン取得<br/>
 *
 * @author kimura
 * @version $Revision: 1.2 $
 *
 */
public interface CampaignGetByCodeService {

    /**
     * キャンペーン取得<br/>
     *
     * @param campaignCode キャンペーンコード
     * @return キャンペーンエンティティ
     */
    CampaignEntity execute(String campaignCode);

}
