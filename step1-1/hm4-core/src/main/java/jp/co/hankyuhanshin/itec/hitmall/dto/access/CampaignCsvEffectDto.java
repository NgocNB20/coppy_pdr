/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.access;

import jp.co.hankyuhanshin.itec.hitmall.annotation.csv.CsvColumn;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * キャンペーンCsvDtoクラス
 *
 * @author kimura
 * @version $Revision: 1.2 $
 */
@Entity
@Data
@Component
@Scope("prototype")
public class CampaignCsvEffectDto implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** キャンペーンコード */
    @CsvColumn(order = 10, columnLabel = "広告コード")
    private String campaignCode;

    /** キャンペーン名 */
    @CsvColumn(order = 20, columnLabel = "キャンペーン名")
    private String campaignName;

    /** 備考 */
    @CsvColumn(order = 30, columnLabel = "備考")
    private String note;

    /** クリック数 */
    @CsvColumn(order = 40, columnLabel = "クリック数")
    private Integer clickCount;

    /** 注文完了数 */
    @CsvColumn(order = 50, columnLabel = "注文完了数")
    private Integer orderCount;

    /** 注文完了比率 */
    @CsvColumn(order = 60, columnLabel = "注文完了比率")
    private String orderCountRatio;

    /** 会員登録数 */
    @CsvColumn(order = 70, columnLabel = "会員登録数")
    private Integer memberRegistCount;

    /** 会員登録比率 */
    @CsvColumn(order = 80, columnLabel = "会員登録比率")
    private String memberRegistCountRatio;

    /** 会員退会数 */
    @CsvColumn(order = 110, columnLabel = "会員退会数")
    private Integer memberDeleteCount;

    /** 会員退会比率 */
    @CsvColumn(order = 120, columnLabel = "会員退会比率")
    private String memberDeleteCountRatio;

    /** 広告コスト */
    @CsvColumn(order = 150, columnLabel = "広告コスト（税込）")
    private BigDecimal advertiseCost;

    /** 受注金額 */
    @CsvColumn(order = 160, columnLabel = "受注金額")
    private BigDecimal orderPrice;

    /** コスト比率 */
    @CsvColumn(order = 170, columnLabel = "コスト比率")
    private String advertiseCostRatio;

}
