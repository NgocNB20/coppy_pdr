/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dto.goods.favorite;

import lombok.Data;
import org.seasar.doma.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * お気に入り商品検索結果Dtoクラス
 *
 * @author takashima
 * @version $Revision: 1.0 $
 */
@Entity
@Data
@Component
@Scope("prototype")
public class FavoriteSearchResultDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品SEQ
     */
    private Integer goodsSeq;

    /**
     * 会員SEQ
     */
    private Integer memberInfoSeq;

    /**
     * 商品グループコード
     */
    private String goodsGroupCode;

    /**
     * 商品コード
     */
    private String goodsCode;

    /**
     * 商品名
     */
    private String goodsGroupNameAdmin;

    /**
     * 顧客番号
     */
    private String customerNo;

    /**
     * セール状態<br/>
     */
    private String saleStatus;

    /**
     * セールコード<br/>
     */
    private String saleCd;

    /**
     * セール終了日（セール期間To）<br/>
     */
    private Timestamp saleTo;

}
