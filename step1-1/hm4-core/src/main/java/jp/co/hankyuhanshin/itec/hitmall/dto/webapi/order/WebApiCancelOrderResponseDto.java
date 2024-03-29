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

import java.util.List;

/**
 *
 * WEB-API連携レスポンスDTOクラス
 * 注文キャンセル
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
@Scope("prototype")
@Data
// 2023-renew No68 from here
public class WebApiCancelOrderResponseDto extends AbstractWebApiResponseDto {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 注文キャンセルレスポンスDTOリスト */
    private List<WebApiCancelOrderResponseDetailDto> info;

}
// 2023-renew No68 to here
