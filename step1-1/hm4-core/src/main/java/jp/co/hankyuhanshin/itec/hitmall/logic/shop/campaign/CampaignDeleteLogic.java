/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;

/**
 * キャンペーン削除ロジック<br/>
 *
 * @author kimura
 * @version $Revision: 1.1 $
 */
public interface CampaignDeleteLogic {

    /**
     * ロジック実行<br/>
     *
     * @param campaignEntity キャンペーンエンティティ
     * @return 削除件数
     */
    int execute(CampaignEntity campaignEntity);
}
