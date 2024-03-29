/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.order;

import jp.co.hankyuhanshin.itec.hitmall.front.dto.webapi.AbstractWebApiRequestDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携リクエストDTOクラス
 * 割引適用結果取得
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetDiscountsRequestDto extends AbstractWebApiRequestDto {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 顧客番号 */
    private Integer customerNo;

    /** 申込商品 (複数パイプ区切り) */
    private String goodsCode;

    /** 数量 (複数パイプ区切り) */
    private String quantity;

    /** 表示順 (複数パイプ区切り) */
    private String orderDisplay;
    // PDR Migrate Customization to here
}
