/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.member;

import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.AbstractWebApiResponseDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * PDR#432 【1.7次】基幹リニューアル対応　【要望No.11】　基幹お届け先との同期<br/>
 *
 * <pre>
 * WEB-API連携レスポンスDTOクラス
 * お届け先参照
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
@Scope("prototype")
@Data
public class WebApiGetDestinationResponseDto extends AbstractWebApiResponseDto {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** お届け先情報 詳細情報 */
    private List<WebApiGetDestinationResponseDetailDto> info;
    // PDR Migrate Customization to here
}
