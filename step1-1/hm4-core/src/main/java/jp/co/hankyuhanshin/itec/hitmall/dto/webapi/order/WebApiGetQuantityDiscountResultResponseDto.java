/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDiscountsType;
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
 * WEB-API連携リクエストDTOクラス
 * 数量割引適用結果取得
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
@Scope("prototype")
@Data
public class WebApiGetQuantityDiscountResultResponseDto extends AbstractWebApiResponseDto {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 数量割引適用結果取得 詳細情報 */
    private List<WebApiGetQuantityDiscountResultResponseDetailDto> info;

    /**
     * 数量割引適用結果取得 詳細情報を<br/>
     * 商品コードをキーにしたMAPで返却します。
     *
     * @return 商品コードをキーにした数量割引適用結果取得 詳細情報MAP
     */
    public Map<String, WebApiGetQuantityDiscountResultResponseDetailDto> getMap() {

        if (this.info == null || this.info.size() == 0) {
            return null;
        }

        Map<String, WebApiGetQuantityDiscountResultResponseDetailDto> map =
                        new HashMap<String, WebApiGetQuantityDiscountResultResponseDetailDto>();

        for (WebApiGetQuantityDiscountResultResponseDetailDto dto : this.info) {
            // 心意気商品の場合、商品コード末尾にkpをつける
            if (HTypeDiscountsType.SALEON_EMOTION_PRICE.getValue().equals(dto.getSaleType())) {
                if (!dto.getGoodsCode().endsWith("kp")) {
                    map.put(dto.getGoodsCode() + "kp", dto);
                } else {
                    map.put(dto.getGoodsCode(), dto);
                }
            } else {
                map.put(dto.getGoodsCode(), dto);
            }
        }

        return map;
    }
    // PDR Migrate Customization to here
}
