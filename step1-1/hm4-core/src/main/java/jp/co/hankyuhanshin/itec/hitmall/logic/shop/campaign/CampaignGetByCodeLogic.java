/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;

/**
 * キャンペーン取得（キャンペーンコード）ロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.1 $
 */
public interface CampaignGetByCodeLogic {

    // LSC0005

    /**
     * ロジック実行<br/>
     *
     * @param shopSeq ショップSEQ
     * @param campaignCode キャンペーンコード
     * @return キャンペーンエンティティ
     */
    CampaignEntity execute(Integer shopSeq, String campaignCode);
}
