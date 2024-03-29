/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.saleannounce;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailDeliveryStatus;
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
 * セールお知らせメールクラス
 *
 * @author takashima
 * @version $Revision: 1.0 $
 */
@Entity
@Table(name = "SaleAnnounceMail")
@Data
@Component
@Scope("prototype")
public class SaleAnnounceMailEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

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
    private String deliveryId;

    /**
     * 配信状況（必須）
     */
    @Column(name = "deliveryStatus")
    private HTypeMailDeliveryStatus deliveryStatus;

    /**
     * 配信日時
     */
    @Column(name = "deliveryTime")
    private Timestamp deliveryTime;

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
