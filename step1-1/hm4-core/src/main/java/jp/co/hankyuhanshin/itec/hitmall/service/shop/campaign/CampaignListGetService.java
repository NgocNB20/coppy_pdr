/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign;

import jp.co.hankyuhanshin.itec.hitmall.dto.access.AccessInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.access.CampaignEffectDto;

import java.util.List;

/**
 * 広告効果リスト取得<br/>
 *
 * @author kimura
 * @version $Revision: 1.5 $
 *
 */
public interface CampaignListGetService {

    /**
     * 広告効果リスト取得<br/>
     *
     * @param accessInfoSearchForDaoConditionDto 広告効果リスト検索条件DTO
     * @return List<CampaignEffectDto> 広告効果リストDTO
     */
    List<CampaignEffectDto> execute(AccessInfoSearchForDaoConditionDto accessInfoSearchForDaoConditionDto);

}
