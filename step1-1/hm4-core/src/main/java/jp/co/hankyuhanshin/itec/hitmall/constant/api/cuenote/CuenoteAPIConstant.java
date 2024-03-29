/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.constant.api.cuenote;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * アクセス種別
 *
 * @author st75001
 */
@Getter
@AllArgsConstructor
public class CuenoteAPIConstant {

    /**
     * MTAの配信ステータス
     */
    /* wait */
    public static final String MTA_STATUS_WAIT = "wait";
    /* prepare */
    public static final String MTA_STATUS_PREPARE = "prepare";
    /* preparing */
    public static final String MTA_STATUS_PREPARING = "preparing";
    /* delivering */
    public static final String MTA_STATUS_DELIVERING = "delivering";
    /* done */
    public static final String MTA_STATUS_DONE = "done";
    /* suspended */
    public static final String MTA_STATUS_SUSPENDED = "suspended";
    /* canceled */
    public static final String MTA_STATUS_CANCELED = "canceled";

}
