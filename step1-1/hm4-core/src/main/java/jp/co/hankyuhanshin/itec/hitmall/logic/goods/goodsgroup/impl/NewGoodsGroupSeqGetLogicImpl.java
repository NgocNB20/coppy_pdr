/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.NewGoodsGroupSeqGetLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商品グループSEQ採番ロジック実装クラス
 *
 * @author hirata
 * @version $Revision: 1.2 $
 */
@Component
public class NewGoodsGroupSeqGetLogicImpl extends AbstractShopLogic implements NewGoodsGroupSeqGetLogic {

    /**
     * 商品グループDao
     */
    private final GoodsGroupDao goodsGroupDao;

    @Autowired
    public NewGoodsGroupSeqGetLogicImpl(GoodsGroupDao goodsGroupDao) {
        this.goodsGroupDao = goodsGroupDao;
    }

    /**
     * 実行メソッド
     *
     * @return 商品グループSEQ
     */
    @Override
    public Integer execute() {
        return goodsGroupDao.getGoodsGroupSeqNextVal();
    }
}
