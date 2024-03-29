/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.front.dto.order.delivery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.order.goods.OrderGoodsEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * PDR#006 03_取りおきサービス<br/>
 *
 * <pre>
 * 取りおき情報DTOクラス
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderReserveDeliveryDto implements Serializable {

    // PDR Migrate Customization from here
    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 取りおき商品用 受注商品クラス
     */
    private OrderGoodsEntity orderGoodsEntity;
    // PDR Migrate Customization to here

}
