/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.settlement.OrderSettlementDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderBeforePaymentDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetBeforePaymentRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetBeforePaymentResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetBeforePaymentResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.SearchTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderBeforePaymentLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetBeforePaymentLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.ComTransactionUtility;
import jp.co.hankyuhanshin.itec.hitmall.utility.OrderUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.util.MapUtils;

import java.util.List;
import java.util.Map;

/**
 * 前回支払方法取得ロジッククラス
 *
 * @author ota-s5
 */
@Component
// 2023-renew No14 from here
public class OrderBeforePaymentLogicImpl extends AbstractShopLogic implements OrderBeforePaymentLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderBeforePaymentLogicImpl.class);

    /**
     * WEB-API連携 前回支払方法取得
     */
    private final WebApiGetBeforePaymentLogic webApiGetBeforePaymentLogic;

    /**
     * 決済情報照会ロジック（ペイジェント）
     */
    private final SearchTranLogic searchTranLogic;

    /**
     * 受注決済Daoクラス
     */
    private final OrderSettlementDao orderSettlementDao;

    /**
     * 通信ユーティリティ（ペイジェント）
     */
    private final ComTransactionUtility comTransactionUtility;

    /**
     * 受注Utility
     */
    private final OrderUtility orderUtility;

    @Autowired
    public OrderBeforePaymentLogicImpl(WebApiGetBeforePaymentLogic webApiGetBeforePaymentLogic,
                                       SearchTranLogic searchTranLogic,
                                       OrderSettlementDao orderSettlementDao,
                                       ComTransactionUtility comTransactionUtility,
                                       OrderUtility orderUtility) {
        this.webApiGetBeforePaymentLogic = webApiGetBeforePaymentLogic;
        this.searchTranLogic = searchTranLogic;
        this.orderSettlementDao = orderSettlementDao;
        this.comTransactionUtility = comTransactionUtility;
        this.orderUtility = orderUtility;
    }

    /**
     * WEB-API連携 前回支払方法取得 を用いて前回利用した支払方法を取得する。
     * なお、基幹側で取得した決済が選択不可（前回決済なし、APIエラー、ECで利用不可の決済、請求なし決済）の場合、HM側DBから再度取得する。
     *
     * @param customerNo 顧客番号
     * @param settlementMethodTypeList 決済タイプリスト（画面選択可能な決済のみを設定）
     * @return 前回支払方法取得Dtoクラス
     */
    public OrderBeforePaymentDto execute(Integer customerNo, List<String> settlementMethodTypeList) {

        // 基幹システムから前回支払方法を取得
        OrderBeforePaymentDto orderBeforePaymentDto = webApiGetBeforePayment(customerNo, settlementMethodTypeList);
        if (StringUtils.isNotEmpty(orderBeforePaymentDto.getSettlementMethodType())) {
            // 正常に取得できた場合、ここで処理を終了して前回支払方法取得Dtoを返却する
            return orderBeforePaymentDto;
        }

        // 基幹システムから取得できなかった場合、HM内DBデータから前回支払方法を取得
        return hmDbGetBeforePayment(customerNo, settlementMethodTypeList);

    }

    /**
     * 基幹システムから前回支払方法を取得
     *
     * @param customerNo 顧客番号
     * @param settlementMethodTypeList 決済タイプリスト（画面選択可能な決済のみを設定）
     * @return 前回支払方法取得Dtoクラス
     */
    private OrderBeforePaymentDto webApiGetBeforePayment(Integer customerNo, List<String> settlementMethodTypeList) {

        // 前回支払方法取得Dtoクラス
        OrderBeforePaymentDto orderBeforePaymentDto = ApplicationContextUtility.getBean(OrderBeforePaymentDto.class);

        // 前回支払方法取得（WEB-API連携）
        WebApiGetBeforePaymentResponseDto responseDto = executeWebApiGetBeforePayment(customerNo);
        if (ObjectUtils.isEmpty(responseDto)) {
            // APIエラー時、以降処理はスキップ
            return orderBeforePaymentDto;
        }
        List<WebApiGetBeforePaymentResponseDetailDto> info = responseDto.getInfo();
        if (CollectionUtils.isEmpty(info)) {
            // 基幹側に前回決済が存在しなかった場合、以降処理はスキップ
            return orderBeforePaymentDto;
        }

        for (WebApiGetBeforePaymentResponseDetailDto detailDto : info) {
            // 決済タイプをHM側の値に変換してセット（この時、HM側に存在しない決済はNULLに変換）
            orderBeforePaymentDto.setSettlementMethodType(
                            orderUtility.conversionSettlementMethod(detailDto.getBeforePaymentType()));
            // 決済IDをセット
            orderBeforePaymentDto.setOrderId(detailDto.getPaymentId());
            break;
        }

        // 前回支払方法取得Dtoクラスにセットされた値を精査し、返却用にカスタマイズ
        customizeOrderBeforePaymentDto(orderBeforePaymentDto, settlementMethodTypeList);

        return orderBeforePaymentDto;

    }

    /**
     * HM内DBデータから前回支払方法を取得
     *
     * @param customerNo 顧客番号
     * @param settlementMethodTypeList 決済タイプリスト（画面選択可能な決済のみを設定）
     * @return 前回支払方法取得Dtoクラス
     */
    private OrderBeforePaymentDto hmDbGetBeforePayment(Integer customerNo, List<String> settlementMethodTypeList) {

        // HM内DBデータから前回支払方法取得Dtoクラスを取得
        OrderBeforePaymentDto orderBeforePaymentDto = orderSettlementDao.getOrderBeforePayment(customerNo);
        if (ObjectUtils.isEmpty(orderBeforePaymentDto)) {
            // HM内DBデータに前回決済が存在しなかった場合、以降処理はスキップ
            return ApplicationContextUtility.getBean(OrderBeforePaymentDto.class);
        }

        // 前回支払方法取得Dtoクラスにセットされた値を精査し、返却用にカスタマイズ
        customizeOrderBeforePaymentDto(orderBeforePaymentDto, settlementMethodTypeList);

        return orderBeforePaymentDto;

    }

    /**
     * WEB-API連携 前回支払方法取得
     *
     * @param customerNo 顧客番号
     * @return WEB-API連携 前回支払方法取得 レスポンスDTOクラス
     */
    private WebApiGetBeforePaymentResponseDto executeWebApiGetBeforePayment(Integer customerNo) {

        // リクエストを作成
        WebApiGetBeforePaymentRequestDto reqDto =
                        ApplicationContextUtility.getBean(WebApiGetBeforePaymentRequestDto.class);
        reqDto.setCustomerNo(customerNo);

        // WEB-API実行 前回支払方法取得
        WebApiGetBeforePaymentResponseDto responseDto = null;
        try {
            responseDto = (WebApiGetBeforePaymentResponseDto) webApiGetBeforePaymentLogic.execute(reqDto);
        } catch (RuntimeException e) {
            // APIエラー時もログだけ吐いて処理続行
            LOGGER.warn("WEB-API連携 前回支払方法取得に失敗しました。", e);
        }

        return responseDto;

    }

    /**
     * 前回支払方法取得Dtoクラスにセットされた値を精査し、返却用にカスタマイズする
     *
     * @param orderBeforePaymentDto 前回支払方法取得Dtoクラス
     * @param settlementMethodTypeList 決済タイプリスト（画面選択可能な決済のみを設定）
     */
    private void customizeOrderBeforePaymentDto(OrderBeforePaymentDto orderBeforePaymentDto,
                                                List<String> settlementMethodTypeList) {

        // 決済タイプ（決済方法種別）
        String settlementMethodType = orderBeforePaymentDto.getSettlementMethodType();

        if (StringUtils.isEmpty(settlementMethodType)) {
            // HM側に存在しない決済の場合、Dtoを初期化して以降処理はスキップ
            orderBeforePaymentDto.remove();
            return;
        }

        if (!settlementMethodTypeList.contains(settlementMethodType)) {
            // 画面選択不可の決済タイプの場合、Dtoを初期化して以降処理はスキップ
            orderBeforePaymentDto.remove();
            return;
        }

        if (HTypeSettlementMethodType.CREDIT.getValue().equals(settlementMethodType)) {
            // クレジット決済の場合、ペイジェント通信：決済情報照会より顧客カードIDをセット
            // ※顧客カードIDは登録済カードを利用した決済でしか取得できない。
            //   新規入力カードの場合はNULLになるが、そのままセット（FRONT側で一番上のカードを選択させるので、当ロジック内で考慮は不要）
            orderBeforePaymentDto.setCustomerCardId(getCustomerCardIdForPaygent(orderBeforePaymentDto.getOrderId()));
        }

    }

    /**
     * 決済IDを用いてペイジェント通信 決済情報照会 を行い、
     * その決済で使用されたクレジットカードの顧客カードIDを返却する
     *
     * @param orderId 決済ID
     * @return 顧客カードID
     */
    private String getCustomerCardIdForPaygent(String orderId) {

        // 決済IDが空の場合、スキップ
        if (StringUtils.isEmpty(orderId)) {
            return null;
        }

        // 決済情報照会（ペイジェント通信）
        Map<String, String> resultMap = null;
        try {
            ComResultDto searchTradeOutput = searchTranLogic.getPaymentInfo(orderId);
            if (comTransactionUtility.isNotFound(searchTradeOutput)) {
                // 対象の取引が存在しない場合
                LOGGER.info("ペイジェントに取引は登録されていません。");
            } else if (comTransactionUtility.isErrorOccurred(searchTradeOutput)) {
                // 想定外のエラーコードが返却された場合
                LOGGER.error("取引の参照に失敗しました。" + StringUtils.join(new Object[] {searchTradeOutput.getResponseCode(),
                                searchTradeOutput.getResponseDetail()}, ":"));
            } else {
                // 正常
                resultMap = searchTradeOutput.getResultMap();
            }
        } catch (Throwable th) {
            LOGGER.error("取引の参照に失敗しました。予期せぬエラーが発生しました。", th);
        }

        // 顧客カードIDを返す
        return MapUtils.isEmpty(resultMap) ? null : resultMap.get(ComResultDto.KEY_CUSTOMER_CARD_ID);

    }

}
// 2023-renew No14 to here
