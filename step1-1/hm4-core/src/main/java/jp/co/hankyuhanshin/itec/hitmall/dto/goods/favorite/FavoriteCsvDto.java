/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite;

import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCHankaku;
import jp.co.hankyuhanshin.itec.hitmall.annotation.csv.CsvColumn;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.utility.SupplyDateTimeUtility;
import jp.co.hankyuhanshin.itec.hmbase.constant.ValidatorConstants;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Timestamp;

import static jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility.UPLOAD_TIME_FORMAT;

/**
 * お気に入り商品CSVダウンロードDtoクラス
 *
 * @author takashima
 */
@Entity
@Data
@Component
@Scope("prototype")
public class FavoriteCsvDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品SEQ
     */
    private Integer goodsSeq;

    /**
     * 商品グループコード
     */
    @CsvColumn(order = 10, columnLabel = "商品管理番号")
    @NotEmpty
    @Pattern(regexp = ValidatorConstants.REGEX_UL_GOODS_GROUP_CODE)
    private String goodsGroupCode;

    /**
     * 商品番号
     */
    @CsvColumn(order = 20, columnLabel = "商品番号")
    @NotEmpty
    @Pattern(regexp = ValidatorConstants.REGEX_UL_GOODS_CODE)
    private String goodsCode;

    /**
     * 商品名
     */
    @CsvColumn(order = 30, columnLabel = "商品名")
    @NotEmpty
    @Length(min = 0, max = 120)
    private String goodsGroupNameAdmin;

    /**
     * 顧客番号
     */
    @CsvColumn(order = 40, columnLabel = "顧客番号")
    @NotEmpty
    @HCHankaku
    @Length(min = 0, max = 9)
    private Integer customerNo;

    /**
     * セール状態
     */
    @CsvColumn(order = 50, columnLabel = "セール状態")
    @NotEmpty
    @HCHankaku
    private String saleStatus;

    /**
     * セールコード
     */
    @CsvColumn(order = 60, columnLabel = "セールコード")
    @Pattern(regexp = ValidatorConstants.REGEX_UL_SALE_CODE)
    private String saleCd;
    /**
     * セール終了日<br/>
     */
    @CsvColumn(order = 70, columnLabel = "セール終了日", dateFormat = DateUtility.YMD_SLASH_HMS,
               supplyDateType = SupplyDateTimeUtility.TYPE_START)
    @HVDate(pattern = UPLOAD_TIME_FORMAT, message = "{PKG-3680-002-S-W}")
    private Timestamp saleTo;

}
