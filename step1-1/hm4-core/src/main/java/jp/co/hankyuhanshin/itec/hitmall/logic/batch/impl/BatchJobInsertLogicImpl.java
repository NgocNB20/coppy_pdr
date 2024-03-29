/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.batch.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.batch.BatchJobDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.batch.BatchJobEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.batch.BatchJobInsertLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * メール送信入力情報登録<br/>
 *
 * @author kimura
 */
@Component
public class BatchJobInsertLogicImpl implements BatchJobInsertLogic {

    private final BatchJobDao batchJobDao;

    @Autowired
    public BatchJobInsertLogicImpl(BatchJobDao batchJobDao) {
        this.batchJobDao = batchJobDao;
    }

    /**
     * Batchリクエストデータ登録<br/>
     *
     * @param requestDataList Batchリクエストデータ(json)リスト
     * @return requestCode 要求コード
     * @throws JsonProcessingException
     */
    @Override
    public Integer execute(List<String> requestDataList) {

        int requestCode = batchJobDao.getRequestSeqNextVal();
        int requestNo = 0;
        int count = 0;

        for (String requestData : requestDataList) {
            BatchJobEntity batchJobEntity = new BatchJobEntity(requestCode, requestNo);
            batchJobEntity.setRequestData(requestData);
            count += batchJobDao.insert(batchJobEntity);
            requestNo++;
        }

        if (count > 0) {
            return requestCode;
        } else {
            return null;
        }
    }
}
