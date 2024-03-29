/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.OrderSummaryDao;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryCountInCurrentDayGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 受注サマリカウント情報取得ロジック<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
// 2023-renew No26 from here
@Component
public class OrderSummaryCountInCurrentDayGetLogicImpl extends AbstractShopLogic
                implements OrderSummaryCountInCurrentDayGetLogic {

    /**
     * 受注サマリDao<br/>
     */
    private final OrderSummaryDao orderSummaryDao;

    @Autowired
    public OrderSummaryCountInCurrentDayGetLogicImpl(OrderSummaryDao orderSummaryDao) {
        this.orderSummaryDao = orderSummaryDao;
    }

    /**
     * 本日における同一顧客の受注数を取得する<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @return 件数
     */
    @Override
    public int execute(Integer memberInfoSeq) {
        // パラメータチェック
        ArgumentCheckUtil.assertNotNull("memberInfoSeq", memberInfoSeq);
        return orderSummaryDao.getCountByMemberInfoSeqAndCurrentDay(memberInfoSeq);
    }

}
// 2023-renew No26 to here
