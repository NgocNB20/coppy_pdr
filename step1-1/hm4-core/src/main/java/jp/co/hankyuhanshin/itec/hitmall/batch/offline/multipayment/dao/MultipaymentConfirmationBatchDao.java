/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.multipayment.dao;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

/**
 * マルチペイメント入金確認Dao
 *
 * @author ty13627
 */
@Dao
@ConfigAutowireable
public interface MultipaymentConfirmationBatchDao {

    /**
     * 未入金マルチペイメント決済受注一覧取得
     *
     * @param shopSeq ショップSEQ
     * @return 受注サマリエンティティリスト
     */
    @Select
    List<OrderSummaryEntity> getUnpaidMulPayOrderList(Integer shopSeq);

    /**
     * 未入金マルチペイメント決済方法一覧取得
     *
     * @param orderSeq       受注SEQ
     * @param orderVersionNo 受注履歴連番
     * @return 決済方法サマリエンティティリスト
     */
    @Select
    SettlementMethodEntity getSettlementMethodByOrder(Integer orderSeq, Integer orderVersionNo);
}
