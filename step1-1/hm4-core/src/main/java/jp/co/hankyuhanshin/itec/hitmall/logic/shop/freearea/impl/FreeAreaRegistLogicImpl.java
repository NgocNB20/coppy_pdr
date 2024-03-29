/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.shop.freearea.FreeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaRegistLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * フリーエリア登録
 *
 * @author shibuya
 */
@Component
public class FreeAreaRegistLogicImpl extends AbstractShopLogic implements FreeAreaRegistLogic {

    /**
     * フリーエリアDao
     */
    private final FreeAreaDao freeAreaDao;

    @Autowired
    public FreeAreaRegistLogicImpl(FreeAreaDao freeAreaDao) {
        this.freeAreaDao = freeAreaDao;
    }

    /**
     * フリーエリア登録
     *
     * @param freeAreaEntity フリーエリアエンティティ
     * @return 処理件数
     */
    @Override
    public int execute(FreeAreaEntity freeAreaEntity) {
        // パラメータチェック
        this.checkParam(freeAreaEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 登録・更新日をセット
        Timestamp currentTime = dateUtility.getCurrentTime();
        freeAreaEntity.setRegistTime(currentTime);
        freeAreaEntity.setUpdateTime(currentTime);

        // 登録処理
        return freeAreaDao.insert(freeAreaEntity);
    }

    /**
     * パラメータチェック
     *
     * @param freeAreaEntity フリーエリアエンティティ
     */
    protected void checkParam(FreeAreaEntity freeAreaEntity) {

        ArgumentCheckUtil.assertNotNull("FreeAreaEntity", freeAreaEntity);
        ArgumentCheckUtil.assertGreaterThanZero("shopSeq", freeAreaEntity.getShopSeq());
        ArgumentCheckUtil.assertNotEmpty("freeAreaKey", freeAreaEntity.getFreeAreaKey());
        ArgumentCheckUtil.assertNotNull("openStartTime", freeAreaEntity.getOpenStartTime());
    }

}
