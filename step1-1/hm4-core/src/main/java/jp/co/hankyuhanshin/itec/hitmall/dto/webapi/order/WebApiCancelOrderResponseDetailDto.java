/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * WEB-API連携取得結果DTO
 * 注文キャンセル 詳細情報
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
// 2023-renew No68 from here
public class WebApiCancelOrderResponseDetailDto implements Serializable {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 注文キャンセル結果 */
    private Integer cancelResult;

    /** 受注番号 */
    private Integer orderNo;

    /** お届け希望日 */
    private Timestamp receiveDate;

}
// 2023-renew No68 to here
