/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order.details;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeProcessType;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 処理履歴ページアイテムクラス<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class OrderProcessHistoryModelItem implements Serializable {

    /**
     * シリアル
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受注履歴連番
     */
    private Integer orderVersionNo;

    /**
     * 処理日時
     */
    private Timestamp processTime;

    /**
     * 処理担当者名
     */
    private String processPersonName;

    /**
     * 処理種別
     */
    private HTypeProcessType processType;

    /**
     * @return 受注履歴連番
     */
    public String getOrderVersionNoName() {
        return orderVersionNo.toString();
    }

}
