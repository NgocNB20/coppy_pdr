/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.graph;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 円グラフ表示用Dto
 * <pre>
 * データ単位の情報
 * </pre>
 *
 * @author kk32102
 */
@Data
@Component
@Scope("prototype")
public class PieGraphDataDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * データラベル
     */
    private String graphDataLabel;

    /**
     * データ値
     */
    private BigDecimal graphDataValue;

}
