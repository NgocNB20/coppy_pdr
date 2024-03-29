/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.orderhistory;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 注文履歴商品Dtoクラス
 *
 * @author DtoGenerator
 */
@Entity
@Data
@Component
@Scope("prototype")
public class OrderHistoryGoodsDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    // 受注商品情報

    /**
     * 受注SEQ
     */
    private Integer orderSeq;

    /**
     * 商品グループSEQ
     */
    private Integer goodsGroupSeq;

    /**
     * 商品グループコード
     */
    private String goodsGroupCode;

    /**
     * 商品SEQ
     */
    private Integer goodsSeq;

    /**
     * 商品コード
     */
    private String goodsCode;

    /**
     * 商品名
     */
    private String goodsGroupName;

    /**
     * 規格値１
     */
    private String unitValue1;

    /**
     * 規格値２
     */
    private String unitValue2;

    /**
     * 商品数量
     */
    private BigDecimal goodsCount;

    // 商品グループ情報

    /**
     * 商品公開状態PC
     */
    private HTypeOpenDeleteStatus goodsOpenStatusPC;

    /**
     * 商品公開開始日時PC
     */
    private Timestamp openStartTimePC;

    /**
     * 商品公開終了日時PC
     */
    private Timestamp openEndTimePC;
}
