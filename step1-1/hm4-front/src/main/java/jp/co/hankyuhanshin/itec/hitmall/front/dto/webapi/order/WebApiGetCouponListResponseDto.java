/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order;

import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.AbstractWebApiResponseDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * WEB-API連携レスポンスDTOクラス
 * 利用可能クーポン一覧取得
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
@Scope("prototype")
@Data
// 2023-renew No24 from here
public class WebApiGetCouponListResponseDto extends AbstractWebApiResponseDto {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 利用可能クーポン一覧取得 詳細情報 */
    private List<WebApiGetCouponListResponseDetailDto> info;

}
// 2023-renew No24 to here
