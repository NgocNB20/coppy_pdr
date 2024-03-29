/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.PaymentRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryListGetForUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderReceiptOfMoneyListRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.OrderReceiptOfMoneyRegistService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 入金リスト登録サービス<br/>
 *
 * @author yamaguchi
 * @version $Revision: 1.4 $
 */
@Service
public class OrderReceiptOfMoneyListRegistServiceImpl extends AbstractShopService
                implements OrderReceiptOfMoneyListRegistService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderReceiptOfMoneyListRegistServiceImpl.class);

    /**
     * 受注サマリー排他取得ロジック
     */
    private final OrderSummaryListGetForUpdateLogic orderSummaryListGetForUpdateLogic;

    /**
     * 入金登録処理サービス
     */
    private final OrderReceiptOfMoneyRegistService orderReceiptOfMoneyRegistService;

    @Autowired
    public OrderReceiptOfMoneyListRegistServiceImpl(OrderSummaryListGetForUpdateLogic orderSummaryListGetForUpdateLogic,
                                                    OrderReceiptOfMoneyRegistService orderReceiptOfMoneyRegistService) {

        this.orderSummaryListGetForUpdateLogic = orderSummaryListGetForUpdateLogic;
        this.orderReceiptOfMoneyRegistService = orderReceiptOfMoneyRegistService;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param paymentRegistDtoList 入金情報
     * @return 処理件数
     */
    @Override
    public List<CheckMessageDto> execute(List<PaymentRegistDto> paymentRegistDtoList, String administratorName) {
        Integer shopSeq = 1001;

        ArgumentCheckUtil.assertNotEmpty("paymentRegistDtoList", paymentRegistDtoList);
        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);

        int ordercodelistsize = paymentRegistDtoList.size();

        // 受注SEQリスト作成
        List<String> orderCodeList = new ArrayList<>();
        for (int index = 0; index < paymentRegistDtoList.size(); index++) {
            PaymentRegistDto paymentRegistDto = paymentRegistDtoList.get(index);
            // リスト内受注番号重複チェック
            if (orderCodeList.contains(paymentRegistDto.getOrderCode())) {
                ordercodelistsize--;
            } else {
                // 受注番号をリストに追加
                orderCodeList.add(paymentRegistDto.getOrderCode());
            }
        }

        // 受注サマリレコードロック
        List<OrderSummaryEntity> orderSummaryEntityList =
                        orderSummaryListGetForUpdateLogic.execute(orderCodeList, shopSeq);

        // 入金登録DTOリスト件数 = 受注サマリーエンティティリスト件数チェック
        if (ordercodelistsize != orderSummaryEntityList.size()) {
            throwMessage(MSGCD_LISTCOUNT_LOCKCOUNT_DEF);
        }

        List<CheckMessageDto> result = new ArrayList<>();
        List<CheckMessageDto> messageList = new ArrayList<>();
        int count = 0;

        for (int index = 0; index < paymentRegistDtoList.size(); index++) {
            PaymentRegistDto paymentRegistDto = paymentRegistDtoList.get(index);
            OrderSummaryEntity orderSummaryEntity =
                            getOrderSummaryEntity(orderSummaryEntityList, paymentRegistDto.getOrderCode());
            // 入金登録処理実行
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
     * 受注サマリーエンティティリストから
     * 受注SEQに該当する受注サマリーエンティティを取得する
     *
     * @param orderSummaryEntityList 受注サマリーエンティティリスト
     * @param orderCode              受注番号
     * @return 受注サマリーエンティティ
     */
    protected OrderSummaryEntity getOrderSummaryEntity(List<OrderSummaryEntity> orderSummaryEntityList,
                                                       String orderCode) {
        Iterator<OrderSummaryEntity> orderSummaryIte = orderSummaryEntityList.iterator();
        while (orderSummaryIte.hasNext()) {
            OrderSummaryEntity orderSummaryEntity = orderSummaryIte.next();
            if (orderCode.compareTo(orderSummaryEntity.getOrderCode()) == 0) {
                return orderSummaryEntity;
            }
        }
        throwMessage(MSGCD_LISTCOUNT_LOCKCOUNT_DEF);
        return null;
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
