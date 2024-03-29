/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.dao.order.settlement;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderBeforePaymentDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 受注決済Daoクラス
 *
 * @author thang
 * @version $Revision: 1.0 $
 */
@Dao
@ConfigAutowireable
public interface OrderSettlementDao {

    /**
     * インサート
     *
     * @param orderSettlementEntity 受注決済エンティティ
     * @return 処理件数
     */
    @Insert(excludeNull = true)
    int insert(OrderSettlementEntity orderSettlementEntity);

    /**
     * アップデート
     *
     * @param orderSettlementEntity 受注決済エンティティ
     * @return 処理件数
     */
    @Update
    int update(OrderSettlementEntity orderSettlementEntity);

    /**
     * デリート
     *
     * @param orderSettlementEntity 受注決済エンティティ
     * @return 処理件数
     */
    @Delete
    int delete(OrderSettlementEntity orderSettlementEntity);

    /**
     * エンティティ取得
     *
     * @param orderSeq                 受注SEQ
     * @param orderSettlementVersionNo 受注決済連番
     * @return 受注決済エンティティ
     */
    @Select
    OrderSettlementEntity getEntity(Integer orderSeq, Integer orderSettlementVersionNo);

    /**
     * 最新の受注決済を取得する。<br/>
     *
     * @param orderSeq       受注SEQ
     * @param orderVersionNo 受注履歴連番
     * @return 受注決済エンティティ
     */
    @Select
    OrderSettlementEntity getCurrentOrderSettlement(Integer orderSeq, Integer orderVersionNo);

    /**
     * 過去に指定した決済方法を選択しているか<br/>
     *
     * @param orderSeq             受注SEQ
     * @param settlementMethodSeq  決済方法SEQ
     * @param settlementMethodType 決済種別
     * @return true 過去に指定した決済方法を選択していた
     */
    @Select
    boolean isSelectedSettlementMethodType(Integer orderSeq,
                                           Integer settlementMethodSeq,
                                           HTypeSettlementMethodType settlementMethodType);

    /**
     * 会員ランクチェック対象の受注決済商品合計金額を取得。
     *
     * @param memberInfoSeq 会員SEQ
     * @param startTime     対象期間開始日時
     * @param endTime       対象期間終了日時
     * @return 会員ランクチェック対象の受注決済商品合計金額
     */
    @Select
    BigDecimal getSearchListForRankingGoodsPriceTotal(Integer memberInfoSeq, Timestamp startTime, Timestamp endTime);

    // 2023-renew No14 from here

    /**
     * 顧客番号に紐づく会員の前回支払方法を取得する
     * ※ただし、伝票分割の受注の場合は、1件目の受注から取得する
     *
     * @param customerNo 顧客番号
     * @return 前回支払方法取得Dtoクラス
     */
    @Select
    OrderBeforePaymentDto getOrderBeforePayment(Integer customerNo);

    // 2023-renew No14 to here

}
