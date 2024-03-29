/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 不足金額DTO<br/>
 * 配送方法ごとに不足金額情報を保持<br/>
 *
 * @author DtoGenerator
 */
@Data
@Component
@Scope("prototype")
public class ShortfallPriceDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 配送方法表示名(サイトごとで表示)<br/>
     * ※不足金額でサマリ(区切り文字="・"[中点])<br/>
     */
    private String deliveryMethodName;

    /**
     * 不足金額<br/>
     * ※高額割引送料下限額-商品合計金額<br/>
     */
    private BigDecimal shortfallPrice;

    /**
     * 送料無料達成フラグ<br/>
     * 不足金額が0円になっている(送料が無料になる)場合にtrue<br/>
     * true..送料無料
     */
    private boolean noCarriageFlag;
}
