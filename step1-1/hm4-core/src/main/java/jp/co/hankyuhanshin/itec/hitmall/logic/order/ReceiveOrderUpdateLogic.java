/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;

/**
 * 受注修正ロジック
 *
 * @author hirata
 */
public interface ReceiveOrderUpdateLogic {

    /**
     * 実行メソッド<br/>
     *
     * @param receiveOrderDto 修正内容を保持した受注DTO
     */
    void execute(ReceiveOrderDto receiveOrderDto,
                 String memberInfoId,
                 HTypeSiteType siteType,
                 HTypeDeviceType deviceType,
                 Integer memberInfoSeq,
                 String userAgent,
                 String administratorLastName,
                 String administratorFirstName);

    /**
     * キャンセル受注の更新を行う<br/>
     * 受注キャンセルチェックを行わない
     *
     * @param receiveOrderDto 修正内容を保持した受注DTO
     */
    void executeCancelOrderUpdate(ReceiveOrderDto receiveOrderDto,
                                  String memberInfoId,
                                  HTypeSiteType siteType,
                                  HTypeDeviceType deviceType,
                                  Integer memberInfoSeq,
                                  String userAgent,
                                  String administratorLastName,
                                  String administratorFirstName);
}
