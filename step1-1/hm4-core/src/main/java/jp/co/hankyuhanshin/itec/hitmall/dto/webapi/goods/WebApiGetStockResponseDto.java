/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携取得結果DTOクラス
 * 商品在庫数取得
 * </pre>
 *
 * @author satoh
 */
@Component
@Scope("prototype")
@Data
public class WebApiGetStockResponseDto extends AbstractWebApiResponseDto {

    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 商品在庫数取得 詳細情報 */
    private List<WebApiGetStockResponseDetailDto> info;

    /**
     * 商品在庫数取得 詳細情報を<br/>
     * 商品コードをキーにしたMAPで返却します。
     *
     * @return 商品コードをキーにした商品在庫数取得 詳細情報MAP
     */
    public Map<String, WebApiGetStockResponseDetailDto> getMap() {

        if (this.info == null || this.info.size() == 0) {
            return null;
        }

        Map<String, WebApiGetStockResponseDetailDto> map = new HashMap<String, WebApiGetStockResponseDetailDto>();

        for (WebApiGetStockResponseDetailDto dto : this.info) {

            map.put(dto.getGoodsCode(), dto);
        }

        return map;
    }
    // PDR Migrate Customization to here
}
