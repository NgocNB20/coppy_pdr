/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDisplayDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDisplayTableLockLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商品グループ表示テーブルロック<br/>
 *
 * @author hirata
 * @version $Revision: 1.2 $
 */
@Component
public class GoodsGroupDisplayTableLockLogicImpl extends AbstractShopLogic implements GoodsGroupDisplayTableLockLogic {

    /**
     * 商品グループ表示DAO
     */
    private final GoodsGroupDisplayDao goodsGroupDisplayDao;

    @Autowired
    public GoodsGroupDisplayTableLockLogicImpl(GoodsGroupDisplayDao goodsGroupDisplayDao) {
        this.goodsGroupDisplayDao = goodsGroupDisplayDao;
    }

    /**
     * 商品グループ表示テーブルロック<br/>
     */
    @Override
    public void execute() {

        // (1) 商品グループ表示テーブル排他取得
        goodsGroupDisplayDao.updateLockTableShareModeNowait();
    }
}
