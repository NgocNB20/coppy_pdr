/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.access;

import jp.co.hankyuhanshin.itec.hitmall.dto.access.AccessInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.access.CampaignCsvEffectDto;

import java.util.stream.Stream;

/**
 * 広告効果CSV出力<br/>
 *
 * @author kimura
 * @version $Revision: 1.2 $
 */
public interface AccessCampaignCsvEffectListGetLogic {
    Stream<CampaignCsvEffectDto> execute(AccessInfoSearchForDaoConditionDto accessInfoSearchForDaoConditionDto);
}
