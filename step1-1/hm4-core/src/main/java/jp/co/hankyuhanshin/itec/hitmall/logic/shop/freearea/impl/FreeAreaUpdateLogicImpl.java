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
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * フリーエリア更新
 *
 * @author shibuya
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class FreeAreaUpdateLogicImpl extends AbstractShopLogic implements FreeAreaUpdateLogic {

    /**
     * フリーエリアDao
     */
    private final FreeAreaDao freeAreaDao;

    @Autowired
    public FreeAreaUpdateLogicImpl(FreeAreaDao freeAreaDao) {
        this.freeAreaDao = freeAreaDao;
    }

    /**
     * フリーエリア更新
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
        // 更新日をセット
        freeAreaEntity.setUpdateTime(dateUtility.getCurrentTime());

        // 更新処理
        return freeAreaDao.update(freeAreaEntity);
    }

    /**
     * パラメータチェック
     *
     * @param freeAreaEntity フリーエリアエンティティ
     */
    protected void checkParam(FreeAreaEntity freeAreaEntity) {

        ArgumentCheckUtil.assertNotNull("FreeAreaEntity", freeAreaEntity);
        ArgumentCheckUtil.assertGreaterThanZero("freeAreaSeq", freeAreaEntity.getFreeAreaSeq());
        ArgumentCheckUtil.assertGreaterThanZero("shopSeq", freeAreaEntity.getShopSeq());
        ArgumentCheckUtil.assertNotEmpty("freeAreaKey", freeAreaEntity.getFreeAreaKey());
        ArgumentCheckUtil.assertNotNull("openStartTime", freeAreaEntity.getOpenStartTime());
    }

}
