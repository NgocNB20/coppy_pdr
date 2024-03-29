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

import java.util.List;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携取得結果DTOクラス
 * シリーズ商品価格情報取得
 * </pre>
 *
 * @author s.kume
 */
@Component
@Scope("prototype")
@Data
public class WebApiGetSeriesPriceResponseDto extends AbstractWebApiResponseDto {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** シリーズ商品価格情報取得 詳細情報 */
    private List<WebApiGetSeriesPriceResponseDetailDto> info;
    // PDR Migrate Customization to here
}
