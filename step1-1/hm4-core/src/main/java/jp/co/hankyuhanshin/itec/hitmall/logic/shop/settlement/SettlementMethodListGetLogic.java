/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementSearchForDaoConditionDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 決済方法リスト取得ロジック
 *
 * @author negishi
 * @version $Revision: 1.4 $
 */
public interface SettlementMethodListGetLogic {

    /**
     * システムプロパティから最大決済金額を取得するためのキー
     */
    public static final String ORDER_MAX_AMOUNT_KEY = "order.max.amount";

    /**
     * 選択可能な決済方法がない level=error
     */
    public static final String MSGCD_NO_SETTLEMENT_METHOD = "LST000201";

    /**
     * 実行メソッド
     *
     * @param conditionDto                  配送方法Dao用検索条件DTO
     * @param settlementMethodSeqList       決済方法SEQリスト
     * @param totalGoodsPrice               商品合計金額
     * @param settlementCharge              決済金額
     * @param selectedDeliveryMethodSeqList 配送方法SEQリスト（画面から選択されたもの）
     * @param carriage                      送料（画面から選択された配送方法のやつ）
     * @param available                     利用可能、不可能どちらかを指定。null..全部 / true..利用可能のみ / false..利用不可能のみ
     * @return 決済Dtoリスト
     */
    List<SettlementDto> execute(SettlementSearchForDaoConditionDto conditionDto,
                                List<Integer> settlementMethodSeqList,
                                BigDecimal totalGoodsPrice,
                                BigDecimal settlementCharge,
                                List<Integer> selectedDeliveryMethodSeqList,
                                BigDecimal carriage,
                                Boolean available);

    /**
     * 実行メソッド
     *
     * @param conditionDto                  配送方法Dao用検索条件DTO
     * @param settlementMethodSeqList       決済方法SEQリスト
     * @param totalGoodsPrice               商品合計金額
     * @param settlementCharge              決済金額
     * @param selectedDeliveryMethodSeqList 配送方法SEQリスト（画面から選択されたもの）
     * @param carriage                      送料（画面から選択された配送方法のやつ）
     * @return 決済Dtoリスト
     */
    List<SettlementDto> execute(SettlementSearchForDaoConditionDto conditionDto,
                                List<Integer> settlementMethodSeqList,
                                BigDecimal totalGoodsPrice,
                                BigDecimal settlementCharge,
                                List<Integer> selectedDeliveryMethodSeqList,
                                BigDecimal carriage);

    /**
     * 実行メソッド
     *
     * @param settlementMethodSeqList 決済方法SEQリスト
     * @param deliveryMethodSeqList   配送方法SEQリスト
     * @param allowCreditFlag         クレジット決済許可フラグ
     * @return 決済詳細DTOリスト
     */
    List<SettlementDto> execute(List<Integer> settlementMethodSeqList,
                                List<Integer> deliveryMethodSeqList,
                                Boolean allowCreditFlag);

}
