/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.coop;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoopId;
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
 * PDR#036 商品価格の基幹連携<br/>
 * 基幹連携日時履歴エンティティ<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Entity
@Table(name = "CoopDateHistory")
@Data
@Component
@Scope("prototype")
public class CoopDateHistoryEntity implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** 基幹連携ID */
    @Column(name = "coopId")
    @Id
    private HTypeCoopId coopId;

    /** 前回連携日時 */
    @Column(name = "lastCoopDate")
    private Timestamp lastCoopDate;

    /** 登録日時 */
    @Column(name = "registTime")
    private Timestamp registTime;

    /** 更新日時 */
    @Column(name = "updateTime")
    private Timestamp updateTime;

}
