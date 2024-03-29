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
 * 商品セール情報取得
 * </pre>
 *
 * @author st75001
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetSaleRequestDto extends AbstractWebApiRequestDto {
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 指定日時 */
    private Timestamp designationDate;
}
