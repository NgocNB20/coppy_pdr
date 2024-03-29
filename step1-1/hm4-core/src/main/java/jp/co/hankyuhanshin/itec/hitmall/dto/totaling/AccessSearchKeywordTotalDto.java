/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.totaling;

import jp.co.hankyuhanshin.itec.hitmall.annotation.csv.CsvColumn;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 検索キーワード集計Dtoクラス
 *
 * @author DtoGenerator
 */
@Entity
@Data
@Component
@Scope("prototype")
public class AccessSearchKeywordTotalDto implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 検索キーワード
     */
    @CsvColumn(order = 10, columnLabel = "検索キーワード")
    private String searchKeyword;

    /**
     * 検索回数
     */
    @CsvColumn(order = 20, columnLabel = "検索回数")
    private Integer searchCount;

    /**
     * 検索結果数
     */
    @CsvColumn(order = 30, columnLabel = "検索結果数", numberFormat = "##0.00")
    private BigDecimal searchResultCount;

    /**
     * 行のClass
     */
    private String lineClass;

    /**
     * 合計行フラグ
     */
    private boolean totalFlag;
}
