/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.cuenoteapi.pdr.common;

/**
 * CUENOTE-API連携 定数クラス<br/>
 * @author st75001
 */
public final class Constant {

    /**
     * インスタンス生成を抑止
     */
    private Constant() {

    }

    /** 例外発生時の返却値 */
    public static final int STATUS_ERROR_400 = 400;

    /** 例外発生時の返却値 */
    public static final int STATUS_ERROR_404 = 404;

    /** 例外発生時の返却値 */
    public static final int STATUS_ERROR_422 = 422;

    /** ストアド：正常終了 */
    public static final int STORED_OK_200 = 200;

    /** ストアド：正常終了 */
    public static final int STORED_OK_201 = 201;

}
