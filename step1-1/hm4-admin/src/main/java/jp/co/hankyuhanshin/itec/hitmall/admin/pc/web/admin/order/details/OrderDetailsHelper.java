/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.admin.utility.CommonInfoUtility;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.utility.CalculatePriceUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.stereotype.Component;

/**
 * 受注詳細系ページクラス<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class OrderDetailsHelper extends AbstractOrderDetailsHelper {

    public OrderDetailsHelper(DateUtility dateUtility,
                              ConversionUtility conversionUtility,
                              CommonInfoUtility commonInfoUtility,
                              OrderUtility orderUtility,
                              CalculatePriceUtility calculatePriceUtility) {
        super(dateUtility, conversionUtility, commonInfoUtility, orderUtility, calculatePriceUtility);
    }

    /**
     * 受注詳細系ページ項目設定<br/>
     *
     * @param page            Details page
     * @param receiveOrderDto receiveOrderDto
     */
    @Override
    public void toPage(AbstractOrderDetailsModel orderAbstractDetailsModel, ReceiveOrderDto receiveOrderDto) {
        super.toPage(orderAbstractDetailsModel, receiveOrderDto);
    }
}
