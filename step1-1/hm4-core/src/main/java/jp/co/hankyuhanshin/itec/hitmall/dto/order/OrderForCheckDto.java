/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.order;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 注文チェック内で持回っているデータを格納するDtoクラス<br/>
 *
 * @author s_tsuru
 */
@Data
@Component
@Scope("prototype")
public class OrderForCheckDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 会員SEQ
     */
    private Integer memberInfoSeq;

    /**
     * 商品SEQリスト
     */
    private List<Integer> goodsSeqList;

    /**
     * 決済SEQリスト
     */
    private List<Integer> settlementList;

    /**
     * 配送SEQリスト &lt;Key=注文連番, Value=可能配送SEQリスト&gt;
     */
    private List<Integer> deliveryList;

    /**
     * 商品数量マップ &lt;Key=商品SEQ, Value=数量&gt;
     */
    private Map<Integer, BigDecimal> goodsCntMap;

    /**
     * チェック済み商品SEQリスト
     */
    private List<Integer> checkedGoodsSeqList;

    /**
     * 商品購入履歴
     */
    private Map<Integer, Integer> historyGoods;

}
