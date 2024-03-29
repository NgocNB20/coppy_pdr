/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.access.AccessInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.access.CampaignEffectDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.access.AccessCampaignEffectListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign.CampaignListGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 広告効果リスト取得<br/>
 *
 * @author kimura
 * @version $Revision: 1.4 $
 *
 */
@Component
public class CampaignListGetServiceImpl extends AbstractShopService implements CampaignListGetService {

    /** 広告効果リスト取得ロジック */
    private final AccessCampaignEffectListGetLogic accessCampaignEffectListGetLogic;

    @Autowired
    public CampaignListGetServiceImpl(AccessCampaignEffectListGetLogic accessCampaignEffectListGetLogic) {
        this.accessCampaignEffectListGetLogic = accessCampaignEffectListGetLogic;
    }

    /**
     * 広告効果リスト取得<br/>
     *
     * @param accessInfoSearchForDaoConditionDto 広告効果検索条件DTO
     * @return List<CampaignEffectDto> 広告効果リストDTO
     */
    @Override
    public List<CampaignEffectDto> execute(AccessInfoSearchForDaoConditionDto accessInfoSearchForDaoConditionDto) {

        accessInfoSearchForDaoConditionDto.setShopSeq(1001);

        return accessCampaignEffectListGetLogic.execute(accessInfoSearchForDaoConditionDto);
    }

}
