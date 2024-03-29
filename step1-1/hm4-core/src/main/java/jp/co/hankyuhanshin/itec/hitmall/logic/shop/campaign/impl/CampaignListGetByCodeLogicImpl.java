package jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.campaign.CampaignDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.campaign.CampaignSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.CampaignListGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * キャンペーンリスト取得ロジック<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Component
public class CampaignListGetByCodeLogicImpl extends AbstractShopLogic implements CampaignListGetByCodeLogic {

    /**
     * キャンペーンDao
     */
    private final CampaignDao campaignDao;

    @Autowired
    public CampaignListGetByCodeLogicImpl(CampaignDao campaignDao) {
        this.campaignDao = campaignDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param campaignSearchForDaoConditionDto キャンペーン検索条件DTO
     * @return キャンペーンエンティティリスト
     */
    @Override
    public List<CampaignEntity> execute(CampaignSearchForDaoConditionDto campaignSearchForDaoConditionDto) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("campaignSearchForDaoConditionDto", campaignSearchForDaoConditionDto);

        // キャンペーンの検索
        return campaignDao.getSearchCampaignList(
                        campaignSearchForDaoConditionDto,
                        campaignSearchForDaoConditionDto.getPageInfo().getSelectOptions()
                                                );
    }
}
