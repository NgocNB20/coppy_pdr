/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.batch;

import jp.co.hankyuhanshin.itec.hitmall.entity.batch.BatchJobEntity;

import java.util.List;

/**
 * Batchリクエストデータ取得<br/>
 *
 * @author kimura
 */
public interface BatchJobSelectLogic {

    /**
     * Batchリクエストデータ取得<br/>
     *
     * @param requestCode 要求コード
     * @return BatchJobEntityリスト
     */
    List<BatchJobEntity> execute(Integer requestCode);
}
