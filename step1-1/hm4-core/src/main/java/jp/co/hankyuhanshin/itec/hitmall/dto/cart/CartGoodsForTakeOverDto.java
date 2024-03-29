/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.cart;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveDeliveryFlag;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * カート一括登録用引継DTO
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class CartGoodsForTakeOverDto implements Serializable {
    // PDR Migrate Customization from here
    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品グループSEQ
     */
    private Integer goodsGroupSeq;

    /**
     * 商品SEQ
     */
    private Integer goodsSeq;

    /**
     * 商品コード
     */
    private String goodsCode;

    /**
     * 商品数量
     */
    private BigDecimal goodsCount;
    // PDR Migrate Customization to here
    // 2023-renew No14 from here
    /**
     * 取りおきお届け希望日
     */
    private Timestamp reserveDeliveryDate;

    /**
     * 取りおきフラグ
     */
    private HTypeReserveDeliveryFlag reserveFlag = HTypeReserveDeliveryFlag.OFF;
    // 2023-renew No14 to here

}
