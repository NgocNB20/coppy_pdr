/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.PaymentRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryListGetSearchForUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderReceiptOfMoneyRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderReceiptOfMoneySearchRegistService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 検索条件入金登録サービス<br/>
 *
 * @author yamaguchi
 * @version $Revision: 1.4 $
 */
@Service
public class OrderReceiptOfMoneySearchRegistServiceImpl extends AbstractShopService
                implements OrderReceiptOfMoneySearchRegistService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderReceiptOfMoneySearchRegistServiceImpl.class);

    /**
     * 受注検索受注サマリー排他取得ロジック
     */
    private final OrderSummaryListGetSearchForUpdateLogic orderSummaryListGetSearchForUpdateLogic;

    /**
     * 入金登録処理
     */
    private final OrderReceiptOfMoneyRegistService orderReceiptOfMoneyRegistService;

    @Autowired
    public OrderReceiptOfMoneySearchRegistServiceImpl(OrderSummaryListGetSearchForUpdateLogic orderSummaryListGetSearchForUpdateLogic,
                                                      OrderReceiptOfMoneyRegistService orderReceiptOfMoneyRegistService) {

        this.orderSummaryListGetSearchForUpdateLogic = orderSummaryListGetSearchForUpdateLogic;
        this.orderReceiptOfMoneyRegistService = orderReceiptOfMoneyRegistService;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param orderSearchListConditionDto 受注検索一覧用条件Dto
     * @return 処理件数（受注単位）
     */
    @Override
    public List<CheckMessageDto> execute(OrderSearchConditionDto orderSearchListConditionDto,
                                         String administratorName) {
        List<CheckMessageDto> result = new ArrayList<>();
        List<CheckMessageDto> messageList = new ArrayList<>();
        int count = 0;

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSearchListConditionDto", orderSearchListConditionDto);
        ArgumentCheckUtil.assertNotNull("shopSeq", orderSearchListConditionDto.getShopSeq());

        // 検索条件に該当する受注サマリー情報を排他制御をかけて取得する
        List<OrderSummaryEntity> orderSummaryEntityList =
                        orderSummaryListGetSearchForUpdateLogic.execute(orderSearchListConditionDto);

        if (orderSummaryEntityList.isEmpty()) {
            CheckMessageDto checkMessageDto = toCheckMessageDto(MSGCD_PAYMENT_NOTING_INFO, null, false);
            result.add(checkMessageDto);
            return result;
        }

        for (int index = 0; index < orderSummaryEntityList.size(); index++) {
            OrderSummaryEntity orderSummaryEntity = orderSummaryEntityList.get(index);
            PaymentRegistDto paymentRegistDto = getPaymentRegistDto(orderSummaryEntity);
            try {
                CheckMessageDto res = orderReceiptOfMoneyRegistService.execute(paymentRegistDto, orderSummaryEntity,
                                                                               administratorName
                                                                              );
                if (res == null) {
                    count++;
                } else {
                    messageList.add(res);
                }
            } catch (AppLevelListException e) {
                LOGGER.error("例外処理が発生しました", e);
                this.addErrorMessage(e);
            }
        }

        if (this.hasErrorMessage()) {
            this.throwMessage();
        }

        if (count > 0) {
            result.add(toCheckMessageDto(MSGCD_SUCCESS_COUNT_INFO, new Object[] {count}, false));
        }

        result.addAll(messageList);

        return result;
    }

    /**
     * 入金登録DTO作成<br/>
     *
     * @param orderSummaryEntity 受注サマリ
     * @return 入金登録DTO
     */
    protected PaymentRegistDto getPaymentRegistDto(OrderSummaryEntity orderSummaryEntity) {
        PaymentRegistDto paymentRegistDto = ApplicationContextUtility.getBean(PaymentRegistDto.class);

        paymentRegistDto.setOrderCode(orderSummaryEntity.getOrderCode());
        paymentRegistDto.setOrderVersionNo(orderSummaryEntity.getOrderVersionNo());
        paymentRegistDto.setReceiptPrice(null);
        paymentRegistDto.setReceiptTime(null);

        return paymentRegistDto;
    }

    /**
     * エラー内容からメッセージリスト作成<br/>
     *
     * @param msgCode メッセージコード
     * @param args    引数
     * @param isError エラーフラグ
     * @return メッセージ情報
     */
    protected CheckMessageDto toCheckMessageDto(String msgCode, Object[] args, boolean isError) {
        CheckMessageDto checkMessageDto = ApplicationContextUtility.getBean(CheckMessageDto.class);
        checkMessageDto.setMessageId(msgCode);
        checkMessageDto.setMessage(getMessage(msgCode, args));
        checkMessageDto.setError(isError);
        return checkMessageDto;
    }

    /**
     * メッセージ取得
     *
     * @param msgCode コード
     * @param args    引数
     * @return メッセージ
     */
    protected String getMessage(String msgCode, Object[] args) {
        return AppLevelFacesMessageUtil.getAllMessage(msgCode, args).getMessage();
    }

}
