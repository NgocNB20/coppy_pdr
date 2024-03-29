/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * PDR#030 在庫切れ時の受注情報制御<br/>
 *
 * <pre>
 * 入荷次第お届け分DTOクラス
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDependingOnReceiptGoodsDto implements Serializable {

    // PDR Migrate Customization from here
    /**
     * シリアルバージョンUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受注商品クラス
     */
    private OrderGoodsEntity orderGoodsEntity;

    /**
     * 入荷次第お届け入荷日
     */
    private Timestamp stockDate;

    /**
     * 在庫管理フラグ
     */
    private HTypeStockManagementFlag stockManagementFlag;

    /**
     * キーとなる商品コードを返却します。
     *
     * @return 商品コード
     */
    public String getKey() {
        return this.orderGoodsEntity.getGoodsCode();
    }
    // PDR Migrate Customization to here
}
