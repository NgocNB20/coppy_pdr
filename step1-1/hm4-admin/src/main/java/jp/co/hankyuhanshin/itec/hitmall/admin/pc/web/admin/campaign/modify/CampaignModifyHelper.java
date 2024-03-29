/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.campaign.modify;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 広告更新Helper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Component
public class CampaignModifyHelper {

    /**
     * 変換ユーティリティ
     */
    private ConversionUtility conversionUtility;

    @Autowired
    public CampaignModifyHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * 初期値の設定<br/>
     *
     * @param campaignModifyModel ページ
     * @param campaignEntity エンティティ
     */
    public void init(CampaignModifyModel campaignModifyModel, CampaignEntity campaignEntity) {

        campaignModifyModel.setCampaignCode(campaignEntity.getCampaignCode());
        campaignModifyModel.setCampaignName(campaignEntity.getCampaignName());
        campaignModifyModel.setNote(campaignEntity.getNote());
        campaignModifyModel.setCampaignCost(conversionUtility.toString(campaignEntity.getCampaignCost()));
        campaignModifyModel.setRedirectUrl(campaignEntity.getRedirectUrl());
        campaignModifyModel.setCampaignUrlPc(PropertiesUtil.getSystemPropertiesValue("campaign.url.pc.html") + "?"
                                             + PropertiesUtil.getSystemPropertiesValue("campaign.param.key") + "="
                                             + campaignEntity.getCampaignCode());
        campaignModifyModel.setCampaignEntity(campaignEntity);
    }

    /**
     * 次画面へ遷移するためのDTOの設定<br/>
     *
     * @param modifyPage ページ
     */
    public void toCampaignEntity(CampaignModifyModel modifyPage) {

        modifyPage.getCampaignEntity().setCampaignName(modifyPage.getCampaignName());
        modifyPage.getCampaignEntity().setNote(modifyPage.getNote());

        // 広告コスト未設定の場合、0を設定
        BigDecimal campaignCost = conversionUtility.toBigDecimal(modifyPage.getCampaignCost());
        if (campaignCost == null) {
            campaignCost = BigDecimal.ZERO;
        }
        modifyPage.getCampaignEntity().setCampaignCost(campaignCost);

        modifyPage.getCampaignEntity().setRedirectUrl(modifyPage.getRedirectUrl());
    }

    /**
     * 確認画面から遷移してきた場合の設定<br/>
     *
     * @param modifyPage ページ
     */
    public void init(CampaignModifyModel campaignModifyModel) {

        campaignModifyModel.setCampaignCode(campaignModifyModel.getCampaignEntity().getCampaignCode());
        campaignModifyModel.setCampaignName(campaignModifyModel.getCampaignEntity().getCampaignName());
        campaignModifyModel.setNote(campaignModifyModel.getCampaignEntity().getNote());
        campaignModifyModel.setCampaignCost(
                        conversionUtility.toString(campaignModifyModel.getCampaignEntity().getCampaignCost()));
        campaignModifyModel.setRedirectUrl(campaignModifyModel.getCampaignEntity().getRedirectUrl());
        campaignModifyModel.setCampaignUrlPc(PropertiesUtil.getSystemPropertiesValue("campaign.url.pc.html") + "?"
                                             + PropertiesUtil.getSystemPropertiesValue("campaign.param.key") + "="
                                             + campaignModifyModel.getCampaignEntity().getCampaignCode());
    }
}
