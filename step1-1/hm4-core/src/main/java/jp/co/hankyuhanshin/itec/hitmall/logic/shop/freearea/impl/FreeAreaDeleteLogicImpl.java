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
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea.FreeAreaDeleteLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * フリーエリア削除
 *
 * @author nose
 */
@Component
public class FreeAreaDeleteLogicImpl extends AbstractShopLogic implements FreeAreaDeleteLogic {

    /**
     * フリーエリアDao
     */
    private final FreeAreaDao freeAreaDao;

    @Autowired
    public FreeAreaDeleteLogicImpl(FreeAreaDao freeAreaDao) {
        this.freeAreaDao = freeAreaDao;
    }

    /**
     * フリーエリア削除
     *
     * @param freeAreaEntity フリーエリア情報
     * @return 処理件数
     */
    @Override
    public int execute(FreeAreaEntity freeAreaEntity) {
        // パラメータチェック
        this.checkParam(freeAreaEntity);

        // 削除処理
        return freeAreaDao.delete(freeAreaEntity);
    }

    /**
     * パラメータチェック
     *
     * @param freeAreaEntity フリーエリア情報
     */
    protected void checkParam(FreeAreaEntity freeAreaEntity) {

        ArgumentCheckUtil.assertNotNull("FreeAreaEntity", freeAreaEntity);
    }

}
