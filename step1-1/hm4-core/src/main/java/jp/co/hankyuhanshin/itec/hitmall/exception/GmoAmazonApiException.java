/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 * $Id:$
 */
package jp.co.hankyuhanshin.itec.hitmall.exception;

import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;

import java.util.List;

/**
 * 注文確定時にGMO通信でエラーが返却された場合の判別をするためのException
 *
 * @author yamazaki
 */
@SuppressWarnings("serial")
public class GmoAmazonApiException extends AppLevelListException {

    /**
     * 引数 errorList からGmoForAmazonApiExceptionを作成する<br/>
     *
     * @param errorList AppLevelExceptionリスト
     */
    public GmoAmazonApiException(List<AppLevelException> errorList) {
        super(errorList);
    }

}
