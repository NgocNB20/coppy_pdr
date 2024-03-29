/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.TempMemberInfoAssociateOrderLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.ReceiveOrderUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ReceiveOrderGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 仮会員情報受注情報紐付けロジックの実装<br/>
 *
 * @author s_nose
 */
@Component
public class TempMemberInfoAssociateOrderLogicImpl extends AbstractShopLogic
                implements TempMemberInfoAssociateOrderLogic {

    /**
     * ReceiveOrderUpdateLogic
     */
    private final ReceiveOrderUpdateLogic receiveOrderUpdateLogic;

    /**
     * ReceiveOrderGetService
     */
    private final ReceiveOrderGetService receiveOrderGetService;

    @Autowired
    public TempMemberInfoAssociateOrderLogicImpl(ReceiveOrderUpdateLogic receiveOrderUpdateLogic,
                                                 ReceiveOrderGetService receiveOrderGetService) {
        this.receiveOrderUpdateLogic = receiveOrderUpdateLogic;
        this.receiveOrderGetService = receiveOrderGetService;
    }

    /**
     * 仮会員情報受注情報紐付け処理<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param orderSeq      受注SEQ
     */
    @Override
    public void execute(Integer memberInfoSeq,
                        Integer orderSeq,
                        String memberInfoId,
                        HTypeSiteType siteType,
                        HTypeDeviceType deviceType,
                        String userAgent,
                        String administratorLastName,
                        String administratorFirstName) {
        ArgumentCheckUtil.assertGreaterThanZero("memberInfoSeq", memberInfoSeq);
        ArgumentCheckUtil.assertGreaterThanZero("orderSeq", orderSeq);

        // 会員情報紐付け処理
        ReceiveOrderDto receiveOrderDto = receiveOrderGetService.execute(orderSeq);
        if (HTypeCancelFlag.ON == receiveOrderDto.getOrderSummaryEntity().getCancelFlag()) {
            // キャンセル済みは処理しない
            return;
        }
        receiveOrderDto.getOrderSummaryEntity().setMemberInfoSeq(memberInfoSeq);
        receiveOrderDto.getOrderPersonEntity().setMemberInfoSeq(memberInfoSeq);

        receiveOrderUpdateLogic.execute(receiveOrderDto, memberInfoId, siteType, deviceType, memberInfoSeq, userAgent,
                                        administratorLastName, administratorFirstName
                                       );
    }

}
