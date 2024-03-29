/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReStockStatus;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 入荷結果会員情報更新DTOクラス
 *
 * @author st75001
 */
@Entity
@Data
@Component
@Scope("prototype")
public class ReStockMemberUpdateDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 会員SEQ
     */
    private Integer memberInfoSeq;

    /**
     * 商品SEQ
     */
    private Integer goodsSeq;

    /**
     * 商品コード
     */
    private String goodsCode;

    /**
     * 入荷フラグ
     */
    private HTypeReStockStatus reStockStatus;
}
