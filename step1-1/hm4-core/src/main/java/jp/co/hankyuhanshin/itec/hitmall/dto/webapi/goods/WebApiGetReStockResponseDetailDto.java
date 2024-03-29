/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * <pre>
 * WEB-API連携取得結果DTOクラス
 * 商品入荷情報取得 詳細情報
 * </pre>
 *
 * @author st75001
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetReStockResponseDetailDto implements Serializable {
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 商品コード */
    private String goodsCode;

    /** 在庫数 */
    private BigDecimal stockQuantity;
}
