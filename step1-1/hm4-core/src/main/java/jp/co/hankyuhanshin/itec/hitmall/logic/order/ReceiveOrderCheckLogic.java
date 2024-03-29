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
 * 受注可能チェックロジック
 *
 * @author hirata
 * @version $Revision: 1.8 $
 */
public interface ReceiveOrderCheckLogic {

    /**
     * 受注可能チェック<br/>
     * 受注金額再計算あり。
     *
     * @param receiveOrderDto 受注DTO
     * @return メッセージDTO
     */
    OrderMessageDto execute(ReceiveOrderDto receiveOrderDto,
                            String memberInfoId,
                            HTypeSiteType siteType,
                            Integer memberInfoSeq,
                            String adminId);

    /**
     * 受注可能チェック<br/>
     *
     * @param receiveOrderDto 受注DTO
     * @param calculateFlag   受注計算フラグ。true..計算する / false..計算しない
     * @return メッセージDTO
     */
    OrderMessageDto execute(ReceiveOrderDto receiveOrderDto,
                            boolean calculateFlag,
                            String memberInfoId,
                            HTypeSiteType siteType,
                            Integer memberInfoSeq,
                            String adminId);

    /**
     * 受注修正チェック処理<br/>
     * 受注金額再計算あり
     *
     * @param dto     編集中の受注DTO
     * @param baseDto 編集前の受注DTO
     * @return メッセージDTO
     */
    OrderMessageDto execute(ReceiveOrderDto dto,
                            ReceiveOrderDto baseDto,
                            String memberInfoId,
                            HTypeSiteType siteType,
                            Integer memberInfoSeq,
                            String adminId);

    /**
     * 受注修正チェック処理<br/>
     *
     * @param dto                編集中の受注DTO
     * @param baseDto            編集前の受注DTO
     * @param calculateFlag      受注計算フラグ。true..計算する / false..計算しない
     * @param commissionCalcFlag 決済手数料適用フラグ。true..計算前の手数料を適用する
     * @param carriageCalcFlag   送料適用フラグ。true..計算前の送料を適用する
     * @return メッセージDTO
     */
    OrderMessageDto execute(ReceiveOrderDto dto,
                            ReceiveOrderDto baseDto,
                            boolean calculateFlag,
                            boolean commissionCalcFlag,
                            boolean carriageCalcFlag,
                            String memberInfoId,
                            HTypeSiteType siteType,
                            Integer memberInfoSeq,
                            String adminId);

}
