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
import java.util.List;

/**
 * 送料無料メッセージDTO<br/>
 *
 * @author hs32101
 */
@Data
@Component
@Scope("prototype")
public class NoCarriageMessageDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 不足金額DTOリスト
     */
    private List<ShortfallPriceDto> shortfallDisplayDtoList;

    /**
     * 価格帯下限額<br/>
     * 不足金額DTO内の最小不足金額(0円以外)<br/>
     */
    private BigDecimal minPriceRange;

    /**
     * 価格帯上限額<br/>
     * 不足金額DTO内の最小不足金額+HTML指定の加算額<br/>
     * ※加算額指定なしの場合は、0<br/>
     */
    private BigDecimal maxPriceRange;

    /**
     * 全配送方法送料無料フラグ<br/>
     * 全配送方法が送料無料の場合true<br/>
     * 商品検索への価格帯検索リンクの表示判定などに使用<br/>
     * true..全配送方法送料無料<br/>
     */
    private boolean allNoCarriageFlag;

}
