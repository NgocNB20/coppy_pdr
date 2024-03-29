/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeDiscountsType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.AbstractWebApiResponseDto;
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
 * 割引適用結果取得
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
@Scope("prototype")
@Data
public class WebApiGetDiscountsResponseDto extends AbstractWebApiResponseDto {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 割引適用結果取得 詳細情報 */
    private List<WebApiGetDiscountsResponseDetailDto> info;

    /**
     * 割引適用結果取得 詳細情報を<br/>
     * 商品コードをキーにしたMAPで返却します。
     *
     * @return 商品コードをキーにした割引適用結果取得 詳細情報MAP
     */
    public Map<String, WebApiGetDiscountsResponseDetailDto> getMap() {

        if (this.info == null || this.info.size() == 0) {
            return null;
        }

        Map<String, WebApiGetDiscountsResponseDetailDto> map =
                        new HashMap<String, WebApiGetDiscountsResponseDetailDto>();

        for (WebApiGetDiscountsResponseDetailDto dto : this.info) {

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
