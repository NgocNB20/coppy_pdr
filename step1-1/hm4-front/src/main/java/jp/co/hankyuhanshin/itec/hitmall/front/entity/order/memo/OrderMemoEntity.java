/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.entity.order.memo;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 受注メモクラス
 *
 * @author EntityGenerator
 */
@Data
@Component
@Scope("prototype")
public class OrderMemoEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 受注SEQ（必須）
     */
    private Integer orderSeq;

    /**
     * 受注メモ連番（必須）
     */
    private Integer orderMemoVersionNo;

    /**
     * メモ
     */
    private String memo;

    /**
     * お客様へのメッセージ
     */
    private String customerMemo;

    /**
     * 登録日時（必須）
     */
    private Timestamp registTime;

    /**
     * 更新日時（必須）
     */
    private Timestamp updateTime;
}
