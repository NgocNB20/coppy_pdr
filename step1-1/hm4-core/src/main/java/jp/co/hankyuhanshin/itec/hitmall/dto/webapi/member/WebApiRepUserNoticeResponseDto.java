/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.member;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * PDR#429 1.7次　基幹リニューアル対応　【要望No.6,7,8】　フロント会員更新追加<br/>
 *
 * <pre>
 * WEB-API連携レスポンスDTOクラス
 * 会員お知らせ情報更新
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
@Scope("prototype")
@Data
public class WebApiRepUserNoticeResponseDto extends AbstractWebApiResponseDto {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;
    // PDR Migrate Customization to here
}
