/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupTableLockLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商品グループテーブルロック<br/>
 *
 * @author hirata
 * @version $Revision: 1.2 $
 */
@Component
public class GoodsGroupTableLockLogicImpl extends AbstractShopLogic implements GoodsGroupTableLockLogic {

    /**
     * 商品グループDAO
     */
    private final GoodsGroupDao goodsGroupDao;

    @Autowired
    public GoodsGroupTableLockLogicImpl(GoodsGroupDao goodsGroupDao) {
        this.goodsGroupDao = goodsGroupDao;
    }

    /**
     * 商品グループテーブルロック<br/>
     */
    @Override
    public void execute() {

        // (1) カテゴリテーブル排他取得
        goodsGroupDao.updateLockTableShareModeNowait();
    }

    @Override
    public void executeWait() {
        goodsGroupDao.updateLockTableExclusiveModeWait();
    }
}
