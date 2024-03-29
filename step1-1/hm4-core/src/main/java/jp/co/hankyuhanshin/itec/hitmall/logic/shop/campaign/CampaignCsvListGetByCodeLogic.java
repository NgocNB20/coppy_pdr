package jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.campaign.CampaignCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.campaign.CampaignSearchForDaoConditionDto;

import java.util.stream.Stream;

/**
 * キャンペーンCSV出力<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
public interface CampaignCsvListGetByCodeLogic {
    Stream<CampaignCsvDto> execute(CampaignSearchForDaoConditionDto campaignSearchForDaoConditionDto);
}
