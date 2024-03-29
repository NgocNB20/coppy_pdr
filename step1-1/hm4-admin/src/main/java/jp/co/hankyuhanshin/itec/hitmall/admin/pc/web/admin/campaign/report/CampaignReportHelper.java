/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.campaign.report;

import jp.co.hankyuhanshin.itec.hitmall.dto.access.AccessInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.access.CampaignEffectDto;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 広告効果測定Helper
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class CampaignReportHelper {

    /** 変換Helper取得 */
    private final ConversionUtility conversionUtility;

    /** コンストラクタ */
    @Autowired
    public CampaignReportHelper(ConversionUtility conversionUtility) {
        this.conversionUtility = conversionUtility;
    }

    /**
     * 検索条件生成<br/>
     *
     * @param campaignReportModel ページ情報
     * @return アクセス情報Dao用検索条件Dto
     */
    public AccessInfoSearchForDaoConditionDto toConditionDto(CampaignReportModel campaignReportModel) {

        AccessInfoSearchForDaoConditionDto conditionDto =
                        ApplicationContextUtility.getBean(AccessInfoSearchForDaoConditionDto.class);

        // 広告コード
        conditionDto.setCampaignCode(campaignReportModel.getCampaignCode());
        // 広告名
        conditionDto.setCampaignName(campaignReportModel.getCampaignName());
        // 備考
        conditionDto.setNote(campaignReportModel.getNote());

        if (campaignReportModel.getCampaignDateFrom() != null) {
            conditionDto.setCampaignFromDate(conversionUtility.toTimeStamp(campaignReportModel.getCampaignDateFrom()));
        }

        if (campaignReportModel.getCampaignDateTo() != null) {
            conditionDto.setCampaignToDate(conversionUtility.toTimeStamp(campaignReportModel.getCampaignDateTo()));
        }

        return conditionDto;
    }

    /**
     * 検索結果生成<br/>
     *
     * @param list                リスト
     * @param conditionDto        アクセス情報Dao用検索条件Dto
     * @param campaignReportModel ページ
     */
    public void toPageLoad(List<CampaignEffectDto> list,
                           AccessInfoSearchForDaoConditionDto conditionDto,
                           CampaignReportModel campaignReportModel) {

        int resultNo = conditionDto.getOffset() + 1;

        List<CampaignReportModelItem> resultItemList = new ArrayList<>();

        // 変換Helper取得
        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        for (CampaignEffectDto campaignEffectDto : list) {

            CampaignReportModelItem modelItem = ApplicationContextUtility.getBean(CampaignReportModelItem.class);

            modelItem.setResultNo(resultNo++);
            modelItem.setCampaignCode(campaignEffectDto.getCampaignCode());
            modelItem.setCampaignName(campaignEffectDto.getCampaignName());
            modelItem.setNote(campaignEffectDto.getNote());
            modelItem.setClickCount(campaignEffectDto.getClickCount());
            modelItem.setOrderCount(campaignEffectDto.getOrderCount());
            modelItem.setOrderCountRatio(campaignEffectDto.getOrderCountRatio());
            modelItem.setMemberRegistCount(campaignEffectDto.getMemberRegistCount());
            modelItem.setMemberRegistCountRatio(campaignEffectDto.getMemberRegistCountRatio());
            modelItem.setMemberDeleteCount(campaignEffectDto.getMemberDeleteCount());
            modelItem.setMemberDeleteCountRatio(campaignEffectDto.getMemberDeleteCountRatio());

            if (campaignEffectDto.getAdvertiseCost() != null) {
                modelItem.setAdvertiseCost(conversionUtility.toString(new DecimalFormat("#,###").format(
                                conversionUtility.toBigDecimal(campaignEffectDto.getAdvertiseCost()))) + " 円");
            }
            modelItem.setOrderPrice(conversionUtility.toString(new DecimalFormat("#,###").format(
                            conversionUtility.toBigDecimal(campaignEffectDto.getOrderPrice()))) + " 円");
            modelItem.setAdvertiseCostRatio(campaignEffectDto.getAdvertiseCostRatio());

            resultItemList.add(modelItem);
        }
        campaignReportModel.setResultItems(resultItemList);
        // 件数セット
        campaignReportModel.setTotalCount(conditionDto.getTotalCount());
    }
}
