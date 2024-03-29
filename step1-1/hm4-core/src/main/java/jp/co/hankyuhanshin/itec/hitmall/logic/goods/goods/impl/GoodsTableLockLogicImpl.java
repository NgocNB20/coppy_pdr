/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsTableLockLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商品テーブルロック<br/>
 *
 * @author hirata
 * @version $Revision: 1.1 $
 */
@Component
public class GoodsTableLockLogicImpl extends AbstractShopLogic implements GoodsTableLockLogic {

    /**
     * 商品DAO
     */
    private final GoodsDao goodsDao;

    @Autowired
    public GoodsTableLockLogicImpl(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    /**
     * 商品テーブルロック<br/>
     */
    @Override
    public void execute() {

        // (1) 商品テーブル排他取得
        goodsDao.updateLockTableShareModeNowait();
    }
}
