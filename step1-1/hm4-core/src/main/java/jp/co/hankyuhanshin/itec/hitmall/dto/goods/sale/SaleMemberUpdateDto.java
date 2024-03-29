/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.sale;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFavoriteSaleStatus;
import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * セール結果会員情報更新DTOクラス
 *
 * @author st75001
 */
@Entity
@Data
@Component
@Scope("prototype")
public class SaleMemberUpdateDto implements Serializable {

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
     * セール状態
     */
    private HTypeFavoriteSaleStatus saleStatus;
}
