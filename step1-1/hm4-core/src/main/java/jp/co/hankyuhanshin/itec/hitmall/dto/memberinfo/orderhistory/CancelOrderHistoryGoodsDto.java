/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.orderhistory;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 注文履歴キャンセル商品Dtoクラス
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
// 2023-renew No68 from here
public class CancelOrderHistoryGoodsDto implements Serializable {

    /** シリアルバージョンUID */
    private static final long serialVersionUID = 1L;

    /** 商品コード */
    private String goodsCode;

    /** 商品名 */
    private String goodsName;

    /** 数量 */
    private String goodsCount;

    /** 単位 */
    private String unitName;

    /** 適用割引 */
    private String discountFlag;

}
// 2023-renew No68 to here
