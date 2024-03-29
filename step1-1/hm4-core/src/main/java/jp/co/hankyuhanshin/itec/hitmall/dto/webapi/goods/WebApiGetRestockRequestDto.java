// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 *
 * <pre>
 * WEB-API連携リクエストDTOクラス
 * 商品入荷情報取得
 * </pre>
 *
 * @author st75001
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetRestockRequestDto extends AbstractWebApiRequestDto {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 申込商品 (複数パイプ区切り) */
    private String goodsCode;

}
// 2023-renew No65 from here