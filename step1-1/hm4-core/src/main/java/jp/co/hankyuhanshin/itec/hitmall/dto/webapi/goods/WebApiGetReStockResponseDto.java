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
 *
 * <pre>
 * WEB-API連携取得結果DTOクラス
 * 商品入荷情報取得
 * </pre>
 *
 * @author st75001
 */
@Component
@Scope("prototype")
@Data
public class WebApiGetReStockResponseDto extends AbstractWebApiResponseDto {
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 商品入荷情報取得 詳細情報 */
    private List<WebApiGetReStockResponseDetailDto> info;
}
