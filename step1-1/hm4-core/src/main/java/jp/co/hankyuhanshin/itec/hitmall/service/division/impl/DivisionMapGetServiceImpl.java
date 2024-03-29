/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.service.division.impl;

import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminAuthGroupLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.category.CategoryDetailsGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery.DeliveryMethodGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGroupGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement.SettlementMethodGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.tax.TaxGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.division.DivisionMapGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 区分値リスト取得サービス
 *
 * @author kaneda
 */
@Service
public class DivisionMapGetServiceImpl extends AbstractShopService implements DivisionMapGetService {

    private InquiryGroupGetLogic inquiryGroupGetLogic;

    private SettlementMethodGetLogic settlementMethodGetLogic;

    private DeliveryMethodGetLogic deliveryMethodGetLogic;

    private CategoryDetailsGetLogic categoryDetailsGetLogic;

    private AdminAuthGroupLogic adminAuthGroupLogic;

    private TaxGetLogic taxGetLogic;

    /**
     * コンストラクタ
     *
     * @param inquiryGroupGetLogic
     */
    @Autowired
    public DivisionMapGetServiceImpl(InquiryGroupGetLogic inquiryGroupGetLogic,
                                     SettlementMethodGetLogic settlementMethodGetLogic,
                                     DeliveryMethodGetLogic deliveryMethodGetLogic,
                                     CategoryDetailsGetLogic categoryDetailsGetLogic,
                                     AdminAuthGroupLogic adminAuthGroupLogic,
                                     TaxGetLogic taxGetLogic) {
        this.inquiryGroupGetLogic = inquiryGroupGetLogic;
        this.settlementMethodGetLogic = settlementMethodGetLogic;
        this.deliveryMethodGetLogic = deliveryMethodGetLogic;
        this.categoryDetailsGetLogic = categoryDetailsGetLogic;
        this.adminAuthGroupLogic = adminAuthGroupLogic;
        this.taxGetLogic = taxGetLogic;
    }

    @Override
    public Map<String, String> getInquiryGroupMapList() {

        Integer shopSeq = 1001;

        return inquiryGroupGetLogic.getItemMapList(shopSeq);
    }

    @Override
    public Map<String, String> getSettlementMapList() {

        Integer shopSeq = 1001;

        return settlementMethodGetLogic.getItemMapList(shopSeq);
    }

    @Override
    public Map<String, String> getDeliveryMapList() {

        Integer shopSeq = 1001;

        return deliveryMethodGetLogic.getItemMapList(shopSeq);
    }

    @Override
    public Map<String, String> getCategoryMapList() {

        Integer shopSeq = 1001;

        return categoryDetailsGetLogic.getItemMapList(shopSeq);
    }

    @Override
    public Map<String, String> getAdminAuthGroupMapList() {

        Integer shopSeq = 1001;

        return adminAuthGroupLogic.getItemMapList(shopSeq);
    }

    @Override
    public Map<BigDecimal, String> getTaxRateMapList() {

        return taxGetLogic.getItemMapList();
    }

}
