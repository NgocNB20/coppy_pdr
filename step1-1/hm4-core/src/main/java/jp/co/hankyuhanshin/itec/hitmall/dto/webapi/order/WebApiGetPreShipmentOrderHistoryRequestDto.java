/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携リクエストDTOクラス
 * 注文履歴（出荷済）取得
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
// PDR Migrate Customization from here-
@Component
@Scope("prototype")
// PDR Migrate Customization to here-
public class WebApiGetPreShipmentOrderHistoryRequestDto extends AbstractWebApiRequestDto {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 顧客番号 */
    private Integer customerNo;

    // 2023-renew No52 from here
    /** 絞り込み開始日 */
    private Timestamp searchStartDay;

    /** 絞り込み終了日 */
    private Timestamp searchEndDay;
    // 2023-renew No52 to here
    // PDR Migrate Customization to here
}
