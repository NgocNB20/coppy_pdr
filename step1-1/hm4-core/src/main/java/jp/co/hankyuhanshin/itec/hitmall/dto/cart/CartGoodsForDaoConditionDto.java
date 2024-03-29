/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.cart;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hmbase.dto.AbstractConditionDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * カート商品Dao用検索条件Dtoクラス
 *
 * @author DtoGenerator
 */
@Data
@Component
@Scope("prototype")
// PDR Migrate Customization from here
public class CartGoodsForDaoConditionDto extends AbstractConditionDto implements Serializable {
    // PDR Migrate Customization to here

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ショップSEQ
     */
    private Integer shopSeq;

    /**
     * 端末識別情報
     */
    private String accessUid;

    /**
     * 会員SEQ
     */
    private Integer memberInfoSeq;

    /**
     * サイト区分
     */
    private HTypeSiteType siteType;

    // ※カート商品の検索ではページング制御不要のため、PageInfoは使わない（AbstractConditionDtoの継承はしない）
    /**
     * 並替項目
     */
    private String orderField;

    /**
     * 並替昇順フラグ
     */
    private boolean orderAsc;

    // 2023-renew No14 from here
    /**
     * 商品SEQ
     */
    private Integer goodsSeq;

    /**
     * 取りおきフラグ
     */
    private HTypeReserveDeliveryFlag reserveFlag;
    // 2023-renew No14 to here

}
