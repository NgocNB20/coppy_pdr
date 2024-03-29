/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.memo.OrderMemoDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.memo.OrderMemoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderMemoGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 受注メモエンティティ取得ロジック<br/>
 *
 * @author USER
 * @version $Revision: 1.4 $
 */
@Component
public class OrderMemoGetLogicImpl extends AbstractShopLogic implements OrderMemoGetLogic {

    /**
     * 受注メモDAO
     */
    private final OrderMemoDao orderMemoDao;

    @Autowired
    public OrderMemoGetLogicImpl(OrderMemoDao orderMemoDao) {
        this.orderMemoDao = orderMemoDao;
    }

    /**
     * 実行ロジック<br/>
     * （指定バージョンを取得）<br/>
     *
     * @param orderSeq           受注SEQ
     * @param orderMemoVersionNo 履歴連番
     * @return 受注メモエンティティ
     */
    @Override
    public OrderMemoEntity execute(Integer orderSeq, Integer orderMemoVersionNo) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);
        ArgumentCheckUtil.assertNotNull("orderMemoVersionNo", orderMemoVersionNo);

        return orderMemoDao.getEntity(orderSeq, orderMemoVersionNo);
    }

}
