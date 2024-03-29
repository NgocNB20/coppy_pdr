// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.restockannounce;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReStockStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRestockAnnounceWatchFlag;
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
 * 入荷お知らエンティティ
 *
 * @author Thang Doan (VJP)
 */
@Entity
@Table(name = "RestockAnnounce")
@Data
@Component
@Scope("prototype")
public class RestockAnnounceEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 会員SEQ (FK)（必須）
     */
    @Column(name = "memberInfoSeq")
    @Id
    private Integer memberInfoSeq;

    /**
     * 商品SEQ (FK)（必須）
     */
    @Column(name = "goodsSeq")
    @Id
    private Integer goodsSeq;

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
// 2023-renew No65 from here
