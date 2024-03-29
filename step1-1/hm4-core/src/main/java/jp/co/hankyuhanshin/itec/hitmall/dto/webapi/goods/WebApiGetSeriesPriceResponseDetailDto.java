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
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携取得結果DTOクラス
 * シリーズ商品価格情報取得 詳細情報
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
@Scope("prototype")
@Data
public class WebApiGetSeriesPriceResponseDetailDto implements Serializable {
    // PDR Migrate Customization from here
    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 商品グループ管理番号 */
    private String goodsGroupCode;

    // 2023-renew AddNo5 from here
    // 2023-renew AddNo5 to here

    /** シリーズセールコメント */
    private String seriesSaleComment;

    /** NEWアイコンフラグ */
    private Integer newIconFlag;

    /** お取りおきアイコンフラグ */
    private Integer reserveIconFlag;

    /** SALEアイコンフラグ */
    private Integer saleIconFlag;

    // 2023-renew No92 from here
    /** アウトレットアイコンフラグ */
    private Integer outletIconFlag;
    // 2023-renew No92 to here
    // PDR Migrate Customization to here
}
