/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.relation.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsRelationDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.relation.GoodsRelationTableLockLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 関連商品テーブルロック取得ロジック
 *
 * @author hirata
 * @version $Revision: 1.2 $
 */
@Component
public class GoodsRelationTableLockLogicImpl extends AbstractShopLogic implements GoodsRelationTableLockLogic {

    /**
     * 関連商品DAO
     */
    private final GoodsRelationDao goodsRelationDao;

    @Autowired
    public GoodsRelationTableLockLogicImpl(GoodsRelationDao goodsRelationDao) {
        this.goodsRelationDao = goodsRelationDao;
    }

    /**
     * 関連商品テーブルロック<br/>
     * 関連商品テーブルのテーブルを排他取得する。<br/>
     */
    @Override
    public void execute() {

        // (1) 関連商品テーブル排他取得
        goodsRelationDao.updateLockTableShareModeNowait();
    }

}
