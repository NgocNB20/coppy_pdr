/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * セールde予約情報 Item
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
// 2023-renew No14 from here
public class ReserveItem implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * デフォルト初期表示件数
     */
    public static final Integer DEFAULT_INIT_DISPLAY = 6;

    /**
     * デフォルト最大表示件数
     */
    public static final Integer DEFAULT_LIMIT_DISPLAY = 50;

    /**
     * デフォルト数量(入力)
     */
    public static final String DEFAULT_INPUT_GOODS_COUNT = "1";

    /**
     * 商品コード
     */
    private String goodsCode;

    /**
     * 数量（今すぐお届け分）
     */
    private BigDecimal inputDeliveryNowGoodsCount;

    /**
     * 初期表示時の行数
     */
    private Integer initCount;

    /**
     * セールde予約情報リスト
     */
    @Valid
    private List<ReserveDetailItem> reserveDetailItemList;

}
// 2023-renew No14 to here
