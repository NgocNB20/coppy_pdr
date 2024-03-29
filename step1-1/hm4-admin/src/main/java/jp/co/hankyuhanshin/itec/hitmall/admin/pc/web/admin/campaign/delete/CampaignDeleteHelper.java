package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.campaign.delete;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import org.springframework.stereotype.Component;

/**
 * クーポン削除HELPERクラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class CampaignDeleteHelper {

    /**
     * 画面初期表示。<br />
     *
     * <pre>
     * クーポン情報を画面に反映する。
     * </pre>
     *
     * @param campaign     キャンペーンクラス
     * @param campaignModel クーポン登録削除確認ページクラス
     */
    public void toPageForLoad(CampaignEntity campaign, CampaignDeleteModel campaignModel) {
        campaignModel.setCampaignCode(campaign.getCampaignCode());
        campaignModel.setCampaignName(campaign.getCampaignName());
        campaignModel.setCampaignCost(campaign.getCampaignCost());
        campaignModel.setNote(campaign.getNote());
        campaignModel.setRedirectUrl(campaign.getRedirectUrl());
        campaignModel.setCampaignUrlPc(PropertiesUtil.getSystemPropertiesValue("campaign.url.pc.html") + "?"
                                       + PropertiesUtil.getSystemPropertiesValue("campaign.param.key") + "="
                                       + campaign.getCampaignCode());
        campaignModel.setCampaignEntity(campaign);
    }
}
