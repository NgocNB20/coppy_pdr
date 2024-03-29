/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.dto.saleannounce;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailDeliveryStatus;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 配信情報取得結果Dtoクラス
 *
 * @author takashima
 * @version $Revision: 1.0 $
 */
@Entity
@Data
@Component
@Scope("prototype")
public class SaleAnnounceMailListResultDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * key<br/>
     */
    private String key;

    /**
     * 商品SEQ (FK)（必須）
     */
    @Column(name = "goodsSeq")
    @Id
    private Integer goodsSeq;

    /**
     * 会員SEQ (FK)（必須）
     */
    @Column(name = "memberInfoSeq")
    @Id
    private Integer memberInfoSeq;

    /**
     * 配信ID
     */
    @Column(name = "deliveryId")
    @Id
    private String deliveryId;

    /**
     * 配信状況（必須）
     */
    @Column(name = "deliveryStatus")
    @Id
    private HTypeMailDeliveryStatus deliveryStatus;

    /**
     * 配信日時
     */
    @Column(name = "deliveryTime")
    @Id
    private Timestamp deliveryTime;

    /**
     * 登録日時（必須）
     */
    @Column(name = "registTime")
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    @Column(name = "updateTime")
    private Timestamp updateTime;
}
