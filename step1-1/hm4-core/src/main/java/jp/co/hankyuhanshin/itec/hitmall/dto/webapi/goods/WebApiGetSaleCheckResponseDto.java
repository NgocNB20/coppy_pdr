/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
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
 * WEB-API連携取得結果DTOクラス
 * 販売可否判定
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
@Scope("prototype")
@Data
// 2023-renew No11 from here
public class WebApiGetSaleCheckResponseDto extends AbstractWebApiResponseDto {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 販売可否判定 詳細情報 */
    private List<WebApiGetSaleCheckResponseDetailDto> info;

    /**
     * 情報取得 詳細情報を<br/>
     * 商品コードをキーにしたMAPで返却します。
     *
     * @return 商品コードをキーにした情報取得 詳細情報MAP
     */
    public Map<String, WebApiGetSaleCheckResponseDetailDto> getMap() {

        if (this.info == null || this.info.size() == 0) {
            return null;
        }

        Map<String, WebApiGetSaleCheckResponseDetailDto> map = new HashMap<>();

        for (WebApiGetSaleCheckResponseDetailDto dto : this.info) {
            map.put(dto.getGoodsCode(), dto);
        }

        return map;
    }

}
// 2023-renew No11 to here
