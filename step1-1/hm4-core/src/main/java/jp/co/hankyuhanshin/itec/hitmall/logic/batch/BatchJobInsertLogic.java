/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.batch;

import java.util.List;

/**
 * Batchリクエストデータ登録<br/>
 *
 * @author kimura
 */
public interface BatchJobInsertLogic {

    /**
     * Batchリクエストデータ登録<br/>
     *
     * @param requestData Batchリクエストデータ(json)リスト
     * @return requestCode 要求コード
     */
    Integer execute(List<String> requestData);
}
