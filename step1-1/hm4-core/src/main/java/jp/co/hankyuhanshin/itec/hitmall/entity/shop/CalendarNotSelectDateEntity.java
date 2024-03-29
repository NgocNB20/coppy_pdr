/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.entity.shop;

import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Table;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * カレンダー選択不可日付クラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Entity
@Table(name = "CalendarNotSelectDate")
@Data
@Component
@Scope("prototype")
// 2023-renew No14 from here
public class CalendarNotSelectDateEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 予約不可日
     */
    @Column(name = "notPossibleReserveDate")
    private Timestamp notPossibleReserveDate;
}
// 2023-renew No14 to here
