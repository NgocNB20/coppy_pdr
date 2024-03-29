/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.goods;

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
 * 取りおき情報取得
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
@Scope("prototype")
@Data
public class WebApiGetReserveResponseDto extends AbstractWebApiResponseDto {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 取りおき情報取得 詳細情報 */
    private List<WebApiGetReserveResponseDetailDto> info;

    /** 心意気価格商品 */
    private final String emotionPriceCode = "kp";

    /** 商品コード */
    private String goodCode;

    /**
     * 情報取得 詳細情報を<br/>
     * 商品コードをキーにしたMAPで返却します。
     *
     * @return 商品コードをキーにした情報取得 詳細情報MAP
     */
    public Map<String, WebApiGetReserveResponseDetailDto> getMap() {

        if (this.info == null || this.info.size() == 0) {
            return null;
        }

        Map<String, WebApiGetReserveResponseDetailDto> map = new HashMap<>();

        for (WebApiGetReserveResponseDetailDto dto : this.info) {
            goodCode = dto.getGoodsCode();

            if (HTypeDiscountsType.SALEON_EMOTION_PRICE.getValue().equals(dto.getDiscountFlag())) {
                goodCode = dto.getGoodsCode() + emotionPriceCode;
            }
            map.put(goodCode, dto);
        }

        return map;
    }
    // PDR Migrate Customization to here
}
