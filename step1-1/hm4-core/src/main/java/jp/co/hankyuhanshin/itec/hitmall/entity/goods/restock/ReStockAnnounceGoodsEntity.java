/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.goods.restock;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReStockStatus;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 入荷お知らせメールクラス
 *
 * @author EntityGenerator
 * @version $Revision: 1.0 $
 */
@Entity
@Table(name = "ReStockAnnounceGoods")
@Data
@Component
@Scope("prototype")
public class ReStockAnnounceGoodsEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 商品SEQ（必須）
     */
    @Column(name = "goodsSeq")
    @Id
    private Integer goodsSeq;

    /**
     * 入荷状態（必須）
     */
    @Column(name = "reStockStatus")
    private HTypeReStockStatus reStockStatus;

    /**
     * 入荷日時
     */
    @Column(name = "reStockTime")
    private Timestamp reStockTime;

    /**
     * 登録日時（必須）
     */
    @Column(name = "registTime", updatable = false)
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    @Column(name = "updateTime")
    private Timestamp updateTime;

}