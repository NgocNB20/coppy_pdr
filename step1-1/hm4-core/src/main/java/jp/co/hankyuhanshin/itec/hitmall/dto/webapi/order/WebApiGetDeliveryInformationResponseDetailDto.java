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
import java.util.List;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携取得結果DTOクラス
 * 配送情報取得 詳細情報
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class WebApiGetDeliveryInformationResponseDetailDto implements Serializable {

    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** お届け希望日、時間帯指定可否 */
    private String deliveryAssignFlag;

    /** 配送会社コード */
    private String deliveryCompanyCode;

    /** お届け可否 */
    private String deliveryFlag;

    /** お届け不可申込商品 */
    private String nodeliveryGoodsCode;

    // 2023-renew No14 from here
    /** 共通最短お届け日 */
    private Timestamp comEarlyReceiveDate;
    // 2023-renew No14 to here

    /** 配送情報取得 詳細情報-日付情報 */
    private List<WebApiGetDeliveryInformationResponseDetailDateInfoDto> dateInfo;

    // PDR Migrate Customization to here
}
