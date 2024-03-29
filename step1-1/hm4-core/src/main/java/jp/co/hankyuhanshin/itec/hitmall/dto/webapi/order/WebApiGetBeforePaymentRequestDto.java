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

/**
 *
 * WEB-API連携リクエストDTOクラス
 * 前回支払方法取得
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
@Scope("prototype")
@Data
// 2023-renew No14 from here
public class WebApiGetBeforePaymentRequestDto extends AbstractWebApiRequestDto {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 顧客番号 */
    private Integer customerNo;

}
// 2023-renew No14 to here
