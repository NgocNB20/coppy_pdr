/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.campaign.regist;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.campaign.CampaignEntity;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 広告新規登録Helper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Component
public class CampaignRegistHelper {

    /**
     * 変換ユーティリティ
     */
    private ConversionUtility conversionUtility;

    @Autowired
    public CampaignRegistHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * 再利用時の初期値の設定<br/>
     *
     * @param campaignRegistModel ページ
     * @param campaignEntity リストアイテム
     */
    public void init(CampaignRegistModel campaignRegistModel, CampaignEntity campaignEntity) {

        campaignRegistModel.setCampaignCode(campaignEntity.getCampaignCode());
        campaignRegistModel.setCampaignName(campaignEntity.getCampaignName());
        campaignRegistModel.setNote(campaignEntity.getNote());
        campaignRegistModel.setCampaignCost(conversionUtility.toString(campaignEntity.getCampaignCost()));
        campaignRegistModel.setRedirectUrl(campaignEntity.getRedirectUrl());
    }

    /**
     * 次画面へ遷移するためのDTOの設定<br/>
     *
     * @param campaignRegistModel ページ
     */
    public void toCampaignEntity(CampaignRegistModel campaignRegistModel) {
        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        CampaignEntity campaignEntity = ApplicationContextUtility.getBean(CampaignEntity.class);
        campaignEntity.setCampaignCode(campaignRegistModel.getCampaignCode());
        campaignEntity.setCampaignName(campaignRegistModel.getCampaignName());
        campaignEntity.setNote(campaignRegistModel.getNote());

        // 広告コスト未設定の場合、0を設定
        BigDecimal campaignCost = conversionUtility.toBigDecimal(campaignRegistModel.getCampaignCost());
        if (campaignCost == null) {
            campaignCost = BigDecimal.ZERO;
        }
        campaignEntity.setCampaignCost(campaignCost);

        campaignEntity.setRedirectUrl(campaignRegistModel.getRedirectUrl());
        campaignRegistModel.setCampaignEntity(campaignEntity);
    }

    /**
     * 確認画面から遷移してきた場合の設定<br/>
     *
     * @param campaignRegistModel ページ
     */
    public void init(CampaignRegistModel campaignRegistModel) {
        campaignRegistModel.setCampaignCode(campaignRegistModel.getCampaignEntity().getCampaignCode());
        campaignRegistModel.setCampaignName(campaignRegistModel.getCampaignEntity().getCampaignName());
        campaignRegistModel.setNote(campaignRegistModel.getCampaignEntity().getNote());
        campaignRegistModel.setCampaignCost(
                        conversionUtility.toString(campaignRegistModel.getCampaignEntity().getCampaignCost()));
        campaignRegistModel.setRedirectUrl(campaignRegistModel.getCampaignEntity().getRedirectUrl());
        campaignRegistModel.setCampaignUrlPc(PropertiesUtil.getSystemPropertiesValue("campaign.url.pc.html") + "?"
                                             + PropertiesUtil.getSystemPropertiesValue("campaign.param.key") + "="
                                             + campaignRegistModel.getCampaignEntity().getCampaignCode());
    }

}
