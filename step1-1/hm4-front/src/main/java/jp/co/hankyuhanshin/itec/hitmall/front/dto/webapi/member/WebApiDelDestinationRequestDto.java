/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.member;

import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.AbstractWebApiRequestDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * PDR#432 【1.7次】基幹リニューアル対応　【要望No.11】　基幹お届け先との同期<br/>
 *
 * <pre>
 * WEB-API連携リクエストDTOクラス
 * お届け先削除
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
// PDR Migrate Customization from here
@Component
@Scope("prototype")
// PDR Migrate Customization to here
public class WebApiDelDestinationRequestDto extends AbstractWebApiRequestDto {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 顧客番号 */
    private Integer customerNo;

    /** お届け先顧客番号 */
    private Integer receiveCustomerNo;
    // PDR Migrate Customization to here
}
