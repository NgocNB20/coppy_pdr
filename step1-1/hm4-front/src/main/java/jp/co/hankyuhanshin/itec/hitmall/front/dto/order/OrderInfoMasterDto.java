/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeTaxRateType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.goods.goods.GoodsDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.shop.delivery.DeliveryMethodDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.shop.tax.TaxRateEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 注文情報マスタDto
 *
 * @author DtoGenerator
 */
@Data
@Component
@Scope("prototype")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderInfoMasterDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品マスタマップ
     */
    private Map<Integer, GoodsDetailsDto> goodsMaster;

    /**
     * 配送マスタマップ
     */
    private Map<Integer, DeliveryMethodDetailsDto> deliveryMethodMaster;

    /**
     * 税率マスタマップ
     */
    private Map<HTypeTaxRateType, TaxRateEntity> taxRateMaster;

    /**
     * @return 標準税率
     */
    public BigDecimal getTaxRateStandard() {
        // PDR Migrate Customization from here
        // PDR案件はHM標準の税率マスタを使わない
        return BigDecimal.ZERO;
        // PDR Migrate Customization to here
    }

    /**
     * @return 軽減税率
     */
    public BigDecimal getTaxRateReduced() {
        // PDR Migrate Customization from here
        // PDR案件はHM標準の税率マスタを使わない
        return BigDecimal.ZERO;
        // PDR Migrate Customization to here
    }
}
