/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupImageDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupImageTableLockLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商品グループ画像テーブルロック取得ロジック
 *
 * @author hirata
 * @version $Revision: 1.2 $
 */
@Component
public class GoodsGroupImageTableLockLogicImpl extends AbstractShopLogic implements GoodsGroupImageTableLockLogic {

    /**
     * 商品グループ画像DAO
     */
    private final GoodsGroupImageDao goodsGroupImageDao;

    @Autowired
    public GoodsGroupImageTableLockLogicImpl(GoodsGroupImageDao goodsGroupImageDao) {
        this.goodsGroupImageDao = goodsGroupImageDao;
    }

    /**
     * 商品グループ画像テーブルロック<br/>
     * 商品グループ画像テーブルのテーブルを排他取得する。<br/>
     */
    @Override
    public void execute() {

        // (1) 商品グループ画像テーブル排他取得
        goodsGroupImageDao.updateLockTableShareModeNowait();
    }
}
