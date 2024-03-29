/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.settlement.dao;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.Date;
import java.util.List;

/**
 * 決済期限切れメール送信バッチDao
 *
 * @author MN7017
 * @version $Revision:$
 */
@Dao
@ConfigAutowireable
public interface SettlementExpiredNotificationDao {

    /**
     * 決済期限切れ対象受注一覧取得
     *
     * @param shopSeq ショップSEQ
     * @param today   処理日
     * @return 受注サマリエンティティリスト
     */
    @Select
    List<OrderSummaryEntity> getExpiredTargetOrderList(Integer shopSeq, Date today);

}
