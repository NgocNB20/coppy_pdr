/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.order;

import jp.co.hankyuhanshin.itec.hitmall.annotation.csv.CsvColumn;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hmbase.constant.ValidatorConstants;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 出荷CSVアップロードDTO<br/>
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class ShipmentCsvDto implements Serializable {

    /**
     * 正規表現エラー
     */
    public static final String MSGCD_REGULAR_EXPRESSION_DELIVERY_CODE_ERR = "{AOX000810W}";

    /**
     * 正規表現エラー
     */
    public static final String MSGCD_REGULAR_EXPRESSION_ORDER_CODE_ERR = "{AOX000811W}";

    /**
     * 日付形式エラー
     */
    public static final String MSGCD_DATE_EXPRESSION_SHIPMENT_DATE_ERR = "{AOX000812W}";

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受注番号
     */
    @CsvColumn(order = 10, columnLabel = "受注番号")
    @NotEmpty
    @Pattern(regexp = ValidatorConstants.REGEX_ORDER_CODE, message = MSGCD_REGULAR_EXPRESSION_ORDER_CODE_ERR)
    @Length(min = 1, max = ValidatorConstants.LENGTH_ORDER_CODE_MAXIMUM)
    private String orderCode;

    /**
     * 注文連番
     */
    @CsvColumn(order = 20, columnLabel = "注文連番")
    @NotEmpty
    @Range(min = 1, max = 999)
    private String orderConsecutiveNo;

    /**
     * 出荷登録日
     */
    @CsvColumn(order = 30, columnLabel = "出荷登録日", dateFormat = "yyyy/MM/dd")
    @HVDate(message = MSGCD_DATE_EXPRESSION_SHIPMENT_DATE_ERR)
    private Timestamp shipmentDate;

    /**
     * 伝票番号
     */
    @CsvColumn(order = 40, columnLabel = "伝票番号")
    @Pattern(regexp = "[a-zA-Z0-9_-]+", message = MSGCD_REGULAR_EXPRESSION_DELIVERY_CODE_ERR)
    @Length(min = 1, max = 40)
    private String deliveryCode;

}
