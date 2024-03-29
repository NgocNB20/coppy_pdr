// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDetailDto;

import java.util.List;
import java.util.Map;

/**
 * #013 09_データ連携（受注データ）<br/>
 *
 * <pre>
 * WEB-API連携 消費税率取得ロジッククラス
 * </pre>
 *
 * @author satoh
 */

public interface OrderGetConsumptionTaxRateLogic {

    /**
     * システムエラー発生
     * <code>MSGCD_SYSTEM_ERR</code>
     */
    public static final String MSGCD_SYSTEM_ERR = "PDR-0015-001-A-";

    /**
     * WEB-API連携 消費税率取得を行い<br />
     * 結果を返却します。
     *
     * @param goodsCodeList 商品コードリスト
     * @return 消費税率MAP
     */
    Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> execute(List<String> goodsCodeList);

}
// PDR Migrate Customization to here
