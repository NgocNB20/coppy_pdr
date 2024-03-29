/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.MulPayShopDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayShopEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.MulPayShopLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * マルチペイショップロジック実装クラス<br/>
 *
 * @author is40701
 */
@Component
public class MulPayShopLogicImpl implements MulPayShopLogic {

    /**
     * マルチペイメントショップDao
     */
    private final MulPayShopDao mulPayShopDao;

    @Autowired
    public MulPayShopLogicImpl(MulPayShopDao mulPayShopDao) {
        this.mulPayShopDao = mulPayShopDao;
    }

    /**
     * マルペイショップID取得<br/>
     *
     * @param shopSeq ショップSEQ
     * @return String マルペイショップID
     */
    @Override
    public String getMulPayShopId(Integer shopSeq) {
        MulPayShopEntity mulPayShopEntity = mulPayShopDao.getEntityByShopSeq(shopSeq);
        if (null == mulPayShopEntity) {
            return null;
        }
        return mulPayShopEntity.getShopId();
    }

}
