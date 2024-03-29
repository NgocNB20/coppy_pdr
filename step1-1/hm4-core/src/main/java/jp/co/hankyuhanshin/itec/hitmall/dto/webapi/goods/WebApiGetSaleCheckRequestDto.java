/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * WEB-API連携リクエストDTOクラス
 * 販売可否判定
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
// 2023-renew No11 from here
public class WebApiGetSaleCheckRequestDto extends AbstractWebApiRequestDto {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 申込商品 (複数パイプ区切り) */
    private String goodsCode;

    /** 顧客番号 */
    private int customerNo;

}
// 2023-renew No11 to here
