/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchOrderResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ShipmentRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSearchOrderListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ShipmentCompleteMailBatchRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ShipmentRegistService;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ShipmentSearchRegistService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 検索条件出荷登録サービス<br/>
 *
 * @author yamaguchi
 */
@Service
public class ShipmentSearchRegistServiceImpl extends AbstractShipmentRegistService
                implements ShipmentSearchRegistService {

    /**
     * 受注検索受注一覧取得ロジック
     */
    private final OrderSearchOrderListGetLogic orderSearchOrderListGetLogic;

    @Autowired
    public ShipmentSearchRegistServiceImpl(ShipmentRegistService shipmentRegistService,
                                           ShipmentCompleteMailBatchRegistService mailBatchRegistService,
                                           OrderSearchOrderListGetLogic orderSearchOrderListGetLogic) {

        super(shipmentRegistService, mailBatchRegistService);
        this.orderSearchOrderListGetLogic = orderSearchOrderListGetLogic;
    }

    /**
     * 実行メソッド<br/>
     *
     * @param orderSearchListConditionDto 受注検索一覧用条件Dto
     * @param sendMailFlag                メール送信フラグ
     * @return 処理結果メッセージリスト
     */
    @Override
    public List<CheckMessageDto> execute(OrderSearchConditionDto orderSearchListConditionDto,
                                         Boolean sendMailFlag,
                                         String administratorName) {
        List<CheckMessageDto> result = new ArrayList<>();
        List<CheckMessageDto> checkMessageDtoList = new ArrayList<>();

        // 引数チェック
        ArgumentCheckUtil.assertNotNull("orderSearchListConditionDto", orderSearchListConditionDto);
        ArgumentCheckUtil.assertNotNull("shopSeq", orderSearchListConditionDto.getShopSeq());

        // 出荷完了保持リスト（出荷件数保持用）
        List<ShipmentRegistDto> successList = new ArrayList<>();
        // 出荷完了保持リスト（出荷完了メール送信用）
        List<ShipmentRegistDto> mailSendList = new ArrayList<>();

        orderSearchListConditionDto.setCancelFlag(HTypeCancelFlag.OFF.getValue());
        orderSearchListConditionDto.setEmergencyFlag(HTypeEmergencyFlag.OFF.getValue());

        // 受注検索（受注一覧）取得処理を実行
        List<OrderSearchOrderResultDto> orderSearchOrderResultDtoList =
                        orderSearchOrderListGetLogic.execute(orderSearchListConditionDto);

        if (orderSearchOrderResultDtoList.isEmpty()) {
            CheckMessageDto checkMessageDto = toCheckMessageDto(MSGCD_SHIPMENT_NOTING_INFO, null, false);
            result.add(checkMessageDto);
            return result;
        }
        for (int index = 0; index < orderSearchOrderResultDtoList.size(); index++) {
            OrderSearchOrderResultDto orderSearchOrderResultDto = orderSearchOrderResultDtoList.get(index);
            ShipmentRegistDto shipmentRegistDto = getShipmentRegistDto(orderSearchOrderResultDto, sendMailFlag);

            // 出荷登録処理実行
            int i = registor(shipmentRegistDto, checkMessageDtoList, MSGCD_SHIPMENTREGIST_FATAL, administratorName);

            if (i > 0) {
                successList.add(shipmentRegistDto);
                // 出荷対象の商品数量が0の場合、出荷完了メールは送信しない
                if (shipmentRegistDto.isGoodsCountNotZeroFlag()) {
                    mailSendList.add(shipmentRegistDto);
                }
            }
        }

        if (!successList.isEmpty()) {
            CheckMessageDto checkMessageDto =
                            toCheckMessageDto(MSGCD_SHIPMENT_SUCCESS_COUNT_INFO, new Object[] {successList.size()},
                                              false
                                             );
            result.add(checkMessageDto);
        }

        // 出荷完了メール送信タスク登録
        mailBatchRegist(mailSendList, checkMessageDtoList, MSGCD_SHIPMENTMAIL_FATAL);

        result.addAll(checkMessageDtoList);

        return result;
    }

    /**
     * 出荷登録DTO作成<br/>
     *
     * @param orderSearchOrderResultDto 検索結果情報
     * @param sendMailFlag              メール送信フラグ
     * @return 出荷登録情報
     */
    protected ShipmentRegistDto getShipmentRegistDto(OrderSearchOrderResultDto orderSearchOrderResultDto,
                                                     Boolean sendMailFlag) {
        ShipmentRegistDto shipmentRegistDto = ApplicationContextUtility.getBean(ShipmentRegistDto.class);
        shipmentRegistDto.setOrderCode(orderSearchOrderResultDto.getOrderCode());
        shipmentRegistDto.setOrderConsecutiveNo(orderSearchOrderResultDto.getOrderConsecutiveNo());
        shipmentRegistDto.setSendMailFlag(sendMailFlag);
        return shipmentRegistDto;
    }

}
