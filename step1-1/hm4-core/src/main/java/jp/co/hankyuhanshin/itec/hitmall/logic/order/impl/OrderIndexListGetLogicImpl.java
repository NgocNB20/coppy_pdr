/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.order.index.OrderIndexDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.index.OrderIndexSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderIndexListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 受注インデックスリスト取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
@Component
public class OrderIndexListGetLogicImpl extends AbstractShopLogic implements OrderIndexListGetLogic {

    /**
     * 受注インデックスDao
     */
    private final OrderIndexDao orderIndexDao;

    @Autowired
    public OrderIndexListGetLogicImpl(OrderIndexDao orderIndexDao) {
        this.orderIndexDao = orderIndexDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param orderIndexConditionDto 受注インデックス検索条件Dto
     * @return 受注インデックスエンティティリスト
     */
    @Override
    public List<OrderIndexEntity> execute(OrderIndexSearchForDaoConditionDto orderIndexConditionDto) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderIndexConditionDto", orderIndexConditionDto);

        // 受注インデックス検索
        return orderIndexDao.getSearchOrderIndex(
                        orderIndexConditionDto, orderIndexConditionDto.getPageInfo().getSelectOptions());
    }

    /**
     * 異常発生時受注インデックス取得<br/>
     *
     * @param orderSeq 受注SEQ
     * @return 受注インデックスエンティティリスト
     */
    @Override
    public OrderIndexEntity getAtEmergencyIndexData(Integer orderSeq) {

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSeq", orderSeq);

        // 異常発生時受注インデックス取得
        return orderIndexDao.getAtEmergencyIndexData(orderSeq);
    }

}
