/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;

/**
 * キャンペーン削除<br/>
 *
 * @author kimura
 * @version $Revision: 1.2 $
 *
 */
public interface CampaignDeleteService {

    /**
     * キャンペーン削除<br/>
     *
     * @param campaignEntity 広告エンティティ
     * @return int 削除件数
     */
    int execute(CampaignEntity campaignEntity);

}
