/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.order;

import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 前回支払方法取得Dtoクラス
 *
 * @author ota-s5
 */
@Data
@Entity
@Component
@Scope("prototype")
// 2023-renew No14 from here
public class OrderBeforePaymentDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 決済タイプ（決済方法種別）
     */
    private String settlementMethodType;

    /**
     * オーダーID（ペイジェント通信：決済情報照会用）
     */
    private String orderId;

    /**
     * 顧客カードID
     */
    private String customerCardId;

    /**
     * 前回支払方法取得Dtoの値を全て取り消す
     */
    public void remove() {
        this.setSettlementMethodType(null);
        this.setOrderId(null);
        this.setCustomerCardId(null);
    }

}
// 2023-renew No14 to here
