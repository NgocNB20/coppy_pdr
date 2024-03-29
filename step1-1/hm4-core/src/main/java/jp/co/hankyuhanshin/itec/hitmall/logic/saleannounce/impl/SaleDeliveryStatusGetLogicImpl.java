/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.saleannounce.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.saleannounce.SaleAnnounceDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.saleannounce.SaleAnnounceMailListResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.saleannounce.SaleDeliveryStatusGetLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * セールお知らせメール配信状況取得
 *
 * @author takashima
 * @version $Revision: 1.3 $
 */
@Component
public class SaleDeliveryStatusGetLogicImpl extends AbstractShopLogic implements SaleDeliveryStatusGetLogic {

    /**
     * セールお知らせDAO
     */
    private final SaleAnnounceDao saleAnnounceDao;

    @Autowired
    public SaleDeliveryStatusGetLogicImpl(SaleAnnounceDao saleAnnounceDao) {
        this.saleAnnounceDao = saleAnnounceDao;
    }

    /**
     * 実行メソッド<br/>
     *
     * @return セールお知らせエンティティリスト
     */
    @Override
    public List<SaleAnnounceMailListResultDto> execute() {

        // セールお知らせ配信状況取得（未配信：0、配信中：1）
        return saleAnnounceDao.getDeliveryStatus();
    }
}
