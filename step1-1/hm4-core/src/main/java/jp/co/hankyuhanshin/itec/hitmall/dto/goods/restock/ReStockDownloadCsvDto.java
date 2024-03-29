/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.annotation.csv.CsvColumn;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailDeliveryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReStockStatus;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 入荷お知らせ商品CSVダウンロードDtoクラス
 *
 * @author st75001
 */
@Entity
@Data
@Component
@Scope("prototype")
public class ReStockDownloadCsvDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品管理番号
     */
    @CsvColumn(order = 10, columnLabel = "商品管理番号")
    private String goodsGroupCode;

    /**
     * 商品コード
     */
    @CsvColumn(order = 20, columnLabel = "商品番号")
    private String goodsCode;

    /**
     * 商品名
     */
    @CsvColumn(order = 30, columnLabel = "商品名")
    private String goodsName;

    /**
     * 顧客番号
     */
    @CsvColumn(order = 40, columnLabel = "顧客番号")
    private Integer customerNo;

    /**
     * 会員ID
     */
    @CsvColumn(order = 50, columnLabel = "会員ID")
    private String memberInfoId;

    /**
     * 配信ID
     */
    @CsvColumn(order = 60, columnLabel = "配信ID")
    private Integer deliveryId;

    /**
     * 入荷状態
     */
    @CsvColumn(order = 70, columnLabel = "入荷状態", enumOutputType = "value")
    private HTypeReStockStatus reStockStatus;

    /**
     * 入荷メール送信状態
     */
    @CsvColumn(order = 80, columnLabel = "入荷メール送信状態", enumOutputType = "value")
    private HTypeMailDeliveryStatus deliveryStatus;

    /**
     * 入荷日時
     */
    @CsvColumn(order = 90, columnLabel = "入荷日時", dateFormat = DateUtility.YMD_SLASH_HMS)
    private Timestamp reStockTime;

    /**
     * 入荷お知らせ登録日時
     */
    @CsvColumn(order = 100, columnLabel = "入荷お知らせ登録日時", dateFormat = DateUtility.YMD_SLASH_HMS)
    private Timestamp reStockRegistTime;
}
