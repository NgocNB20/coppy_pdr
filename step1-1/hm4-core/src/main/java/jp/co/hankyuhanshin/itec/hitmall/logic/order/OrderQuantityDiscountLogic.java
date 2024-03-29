// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDetailDto;

import java.util.List;
import java.util.Map;

/**
 * #013 09_データ連携（受注データ）<br/>
 *
 * <pre>
 * 数量割引取得結果連携ロジッククラス
 * </pre>
 *
 * @author satoh
 */

public interface OrderQuantityDiscountLogic {

    /**
     * システムエラー発生
     * <code>MSGCD_SYSTEM_ERR</code>
     */
    public static final String MSGCD_SYSTEM_ERR = "PDR-0015-001-A-";

    /**
     * 割引価格 変更 メッセージ
     * <code>MSGCD_DISCOUNT_CHANGE</code>
     */
    public static final String MSGCD_DISCOUNT_CHANGE = "PDR-0007-001-A-";

    /**
     * 数量割引適用結果取得を行い、
     * 受注DTOに反映を行います。
     *
     * @param receiveOrderDto     受注DTO
     * @param taxRateMap          消費税率MAP
     * @param checkMessageDtoList エラーメッセージ用List
     */
    void execute(ReceiveOrderDto receiveOrderDto,
                 Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> taxRateMap,
                 List<CheckMessageDto> checkMessageDtoList);
}
// PDR Migrate Customization to here
