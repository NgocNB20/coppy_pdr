/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.index;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.index.OrderIndexSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;

import java.util.List;

/**
 * 受注インデックスリスト取得サービス<br/>
 *
 * @author yamaguchi
 * @version $Revision: 1.2 $
 */
public interface OrderIndexListGetService {

    /**
     * 実行メソッド<br/>
     *
     * @param conditionDto 受注インデックスDao用検索条件
     * @return 受注インデックスエンティティリスト
     */
    List<OrderIndexEntity> execute(OrderIndexSearchForDaoConditionDto conditionDto);
}
