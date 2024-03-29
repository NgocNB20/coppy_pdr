/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.common;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 定数クラス<br/>
 *
 * @author k-katoh
 */
public final class Constant {

    /**
     * インスタンス生成を抑止
     */
    private Constant() {

    }

    /** 例外発生時の返却値 */
    public static final int STATUS_ERROR = 99;

    /** ストアド：正常終了 */
    public static final int STORED_OK = 0;

    /** プロモーション連携時のレコード件数 */
    public static final int PROMOTION_DETAIL_NUM = 99;
}
