// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetStockResponseDetailDto;

import java.util.List;
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

public interface OrderGetStockLogic {

    /**
     * システムエラー発生
     * <code>MSGCD_SYSTEM_ERR</code>
     */
    public static final String MSGCD_SYSTEM_ERR = "PDR-0015-001-A-";

    /**
     * WEB-API連携 商品在庫数取得を行います。<br/>
     *
     * @param goodsCodeList 商品コードリスト
     * @param quantityList  数量リスト
     * @param customerNo    顧客番号
     * @return 商品コードをキーにした商品在庫数MAP
     */
    Map<String, WebApiGetStockResponseDetailDto> execute(List<String> goodsCodeList,
                                                         List<String> quantityList,
                                                         Integer customerNo);

}
// PDR Migrate Customization to here
