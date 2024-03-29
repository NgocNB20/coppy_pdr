package jp.co.hankyuhanshin.itec.hitmall.service.shop.campaign;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.campaign.CampaignSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;

import java.util.List;

/**
 * 広告リスト取得<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
public interface CampaignListGetByCodeService {

    /**
     * 広告リスト取得<br/>
     *
     * @param campaignSearchForDaoConditionDto 広告リスト検索条件DTO
     * @return List<CampaignEntity> キャンペーンエンティティリスト
     */
    List<CampaignEntity> execute(CampaignSearchForDaoConditionDto campaignSearchForDaoConditionDto);

}
