/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.index.OrderIndexSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;

import java.util.List;

/**
 * 受注インデックスリスト取得ロジック
 *
 * @author ueshima
 * @version $Revision: 1.2 $
 */
public interface OrderIndexListGetLogic {

    // LOO0028;

    /**
     * ロジック実行<br/>
     *
     * @param orderIndexConditionDto 受注インデックス検索条件Dto
     * @return 受注インデックスエンティティリスト
     */
    List<OrderIndexEntity> execute(OrderIndexSearchForDaoConditionDto orderIndexConditionDto);

    /**
     * 異常発生時受注インデックス取得<br/>
     *
     * @param orderSeq 受注SEQ
     * @return 受注インデックスエンティティリスト
     */
    OrderIndexEntity getAtEmergencyIndexData(Integer orderSeq);
}
