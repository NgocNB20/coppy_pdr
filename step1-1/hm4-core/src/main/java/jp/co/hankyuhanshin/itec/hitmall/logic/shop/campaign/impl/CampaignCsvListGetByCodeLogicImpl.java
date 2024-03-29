package jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.campaign.CampaignDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.campaign.CampaignCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.campaign.CampaignSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.campaign.CampaignCsvListGetByCodeLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * キャンペーンCSV出力ロジック<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Component
public class CampaignCsvListGetByCodeLogicImpl extends AbstractShopLogic implements CampaignCsvListGetByCodeLogic {

    /**
     * キャンペーンDao
     */
    private final CampaignDao campaignDao;

    @Autowired
    public CampaignCsvListGetByCodeLogicImpl(CampaignDao campaignDao) {
        this.campaignDao = campaignDao;
    }

    @Override
    public Stream<CampaignCsvDto> execute(CampaignSearchForDaoConditionDto conditionDto) {
        return campaignDao.exportCsvSearchCampaignList(conditionDto);
    }
}
