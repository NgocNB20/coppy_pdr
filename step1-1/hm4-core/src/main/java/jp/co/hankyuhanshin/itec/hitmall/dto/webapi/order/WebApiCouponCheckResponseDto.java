/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order;

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
 * WEB-API連携レスポンスDTOクラス
 * クーポン有効性チェック
 * </pre>
 *
 * @author
 */
@Component
@Scope("prototype")
@Data
public class WebApiCouponCheckResponseDto extends AbstractWebApiResponseDto {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** クーポン取得 詳細情報 */
    private List<WebApiCouponCheckResponseDetailDto> info;

    /**
     * クーポン有効性チェック 詳細情報を<br/>
     * クーポン名称をキーにしたMAPで返却します。
     *
     * @return クーポン名称をキーにしたクーポン有効性チェック 詳細情報MAP
     */
    public Map<String, WebApiCouponCheckResponseDetailDto> getMap() {

        if (this.info == null || this.info.size() == 0) {
            return null;
        }

        Map<String, WebApiCouponCheckResponseDetailDto> map = new HashMap<String, WebApiCouponCheckResponseDetailDto>();

        for (WebApiCouponCheckResponseDetailDto dto : this.info) {

            map.put(dto.getCouponName(), dto);
        }

        return map;
    }
    // PDR Migrate Customization to here
}
