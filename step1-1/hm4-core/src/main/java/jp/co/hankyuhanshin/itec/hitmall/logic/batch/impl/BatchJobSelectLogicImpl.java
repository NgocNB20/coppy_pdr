/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.batch.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.batch.BatchJobDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.batch.BatchJobEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.batch.BatchJobSelectLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.AssertionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Batchリクエストデータ取得<br/>
 *
 * @author kimura
 */
@Component
public class BatchJobSelectLogicImpl implements BatchJobSelectLogic {

    private final BatchJobDao batchJobDao;

    @Autowired
    public BatchJobSelectLogicImpl(BatchJobDao batchJobDao) {
        this.batchJobDao = batchJobDao;
    }

    /**
     * Batchリクエストデータ取得<br/>
     *
     * @param requestCode 要求コード
     * @return BatchJobEntityリスト
     * @throws JsonProcessingException
     */
    @Override
    public List<BatchJobEntity> execute(Integer requestCode) {
        AssertionUtil.assertNotNull("requestCode", requestCode);
        return batchJobDao.getEntityList(requestCode);
    }
}
