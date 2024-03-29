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
 * 督促メール送信バッチDao
 *
 * @author MN7017
 * @version $Revision:$
 */
@Dao
@ConfigAutowireable
public interface SettlementReminderDao {

    /**
     * 督促対象受注一覧取得
     *
     * @param shopSeq       ショップSEQ
     * @param thresholdDate 支払期限日までの残日数
     * @param today         処理日付
     * @return 受注サマリエンティティリスト
     */
    @Select
    List<OrderSummaryEntity> getReminderTargetOrderListForUpdate(Integer shopSeq, Date thresholdDate, Date today);
}
