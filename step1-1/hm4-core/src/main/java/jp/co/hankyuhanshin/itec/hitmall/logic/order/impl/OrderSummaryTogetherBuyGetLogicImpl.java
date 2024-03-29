// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.OrderSummaryDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryTogetherBuyGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 受注サマリ情報取得ロジック<br/>
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Component
public class OrderSummaryTogetherBuyGetLogicImpl extends AbstractShopLogic implements OrderSummaryTogetherBuyGetLogic {

    /**
     * 受注サマリDao<br/>
     */
    private final OrderSummaryDao orderSummaryDao;

    @Autowired
    public OrderSummaryTogetherBuyGetLogicImpl(OrderSummaryDao orderSummaryDao) {
        this.orderSummaryDao = orderSummaryDao;
    }

    /**
     * よく一緒に購入される商品クラスリスト取得
     *
     * @param goodsGroupSeq 商品グループSEQ
     * @return よく一緒に購入される商品クラスリスト
     */
    @Override
    public List<GoodsTogetherBuyGroupEntity> execute(Integer goodsGroupSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertGreaterThanZero("goodsGroupSeq", goodsGroupSeq);

        // よく一緒に購入される商品クラス取得
        return orderSummaryDao.getGoodsTogetherBuy(goodsGroupSeq);
    }

}
// 2023-renew No21 to here