// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveResponseDetailDto;

import java.util.Map;

/**
 * #006 03_取りおきサービス<br/>
 *
 * <pre>
 * 商品在庫情報ユーティリティクラス
 * </pre>
 *
 * @author satoh
 */

public interface OrderGetReserveLogic {

    /**
     * システムエラー発生
     * <code>MSGCD_SYSTEM_ERR</code>
     */
    public static final String MSGCD_SYSTEM_ERR = "PDR-0015-001-A-";

    /** 追加方法：アドレス帳から追加 */
    public static final String ADD_TYPE_ADDRESS_BOOK = "2";

    /**
     * WEB-API連携 取りおき情報取得を行います。<br/>
     *
     * @param orderDeliveryDto 受注配送Dtoクラス
     * @param customerNo       注文主の顧客番号
     * @return 取りおき情報MAP
     */
    Map<String, WebApiGetReserveResponseDetailDto> execute(OrderDeliveryDto orderDeliveryDto, Integer customerNo);
}
// PDR Migrate Customization to here
