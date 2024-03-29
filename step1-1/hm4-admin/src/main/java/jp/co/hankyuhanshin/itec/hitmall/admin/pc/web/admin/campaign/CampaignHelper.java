package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.campaign;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.campaign.CampaignSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 広告検索Helper<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Component
public class CampaignHelper {

    /**
     * 検索条件生成<br/>
     *
     * @param campaignModel 広告検索
     */
    public void toConditionDto(CampaignModel campaignModel) {
        CampaignSearchForDaoConditionDto conditionDto = new CampaignSearchForDaoConditionDto();

        conditionDto.setCampaignCode(campaignModel.getCampaignCodeCondition());
        conditionDto.setCampaignName(campaignModel.getCampaignNameCondition());
        conditionDto.setNote(campaignModel.getNoteCondition());
        conditionDto.setPageInfo(campaignModel.getPageInfo());
        campaignModel.setConditionDto(conditionDto);
    }

    /**
     * 検索条件設定<br/>
     *
     * @param conditionDto 検索条件Dto
     * @param campaignModel 広告検索
     */
    public void toPageForLoad(CampaignSearchForDaoConditionDto conditionDto, CampaignModel campaignModel) {
        campaignModel.setCampaignCodeCondition(conditionDto.getCampaignCode());
        campaignModel.setCampaignNameCondition(conditionDto.getCampaignName());
        campaignModel.setNoteCondition(conditionDto.getNote());
    }

    /**
     * 検索結果生成<br/>
     *
     * @param list リスト
     * @param campaignModel 広告検索
     */
    public void toPageList(List<CampaignEntity> list, CampaignModel campaignModel) {

        int index = campaignModel.getConditionDto().getPageInfo().getOffset() + 1;

        List<CampaignModelItem> resultItemList = new ArrayList<>();

        for (CampaignEntity campaignEntity : list) {

            CampaignModelItem listPageItem = new CampaignModelItem();
            listPageItem.setResultNo(index++);
            listPageItem.setCampaignCode(campaignEntity.getCampaignCode());
            listPageItem.setCampaignName(campaignEntity.getCampaignName());
            listPageItem.setNote(campaignEntity.getNote());
            listPageItem.setCampaignCost(campaignEntity.getCampaignCost());
            listPageItem.setRedirectUrl(campaignEntity.getRedirectUrl());

            resultItemList.add(listPageItem);
        }
        campaignModel.setResultItems(resultItemList);
    }
}
