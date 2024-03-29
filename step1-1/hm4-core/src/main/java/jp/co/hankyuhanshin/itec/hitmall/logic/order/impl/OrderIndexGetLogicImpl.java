/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.index.OrderIndexDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 受注インデックス取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
@Component
public class OrderIndexGetLogicImpl extends AbstractShopLogic implements OrderIndexGetLogic {

    /**
     * 受注インデックスDao
     */
    private final OrderIndexDao orderIndexDao;

    @Autowired
    public OrderIndexGetLogicImpl(OrderIndexDao orderIndexDao) {
        this.orderIndexDao = orderIndexDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderSeq       受注SEQ
     * @param orderVersionNo 受注履歴番号
     * @return 受注インデックスエンティティ
     */
    @Override
    public OrderIndexEntity execute(Integer orderSeq, Integer orderVersionNo) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderVersionNo", orderVersionNo);

        // 受注インデックス検索
        return orderIndexDao.getEntity(orderSeq, orderVersionNo);
    }

}
