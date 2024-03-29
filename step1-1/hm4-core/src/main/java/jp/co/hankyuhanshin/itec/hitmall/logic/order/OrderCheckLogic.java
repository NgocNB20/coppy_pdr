/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

/**
 * 注文可能チェックロジック
 *
 * @author ozaki
 * @version $Revision: 1.8 $
 */
public interface OrderCheckLogic {

    /**
     * 商品設定時の実行メソッド
     *
     * @param receiveOrderDto 受注Dto
     * @return 注文メッセージDto
     */
    OrderMessageDto executeForGoodsSetting(ReceiveOrderDto receiveOrderDto);

    /**
     * 実行メソッド
     *
     * @param receiveOrderDto 受注Dto
     * @return 注文メッセージDto
     */
    OrderMessageDto execute(ReceiveOrderDto receiveOrderDto,
                            String memberInfoId,
                            HTypeSiteType siteType,
                            Integer memberInfoSeq,
                            String adminId);

    /**
     * 実行メソッド
     *
     * @param receiveOrderDto 受注Dto
     * @param calculateFlag   受注金額算出フラグ
     * @return 注文メッセージDto
     */
    OrderMessageDto execute(ReceiveOrderDto receiveOrderDto,
                            boolean calculateFlag,
                            String memberInfoId,
                            HTypeSiteType siteType,
                            Integer memberInfoSeq,
                            String adminId);

}
