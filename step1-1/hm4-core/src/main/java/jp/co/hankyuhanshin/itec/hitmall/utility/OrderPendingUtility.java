//  Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.utility;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePendingType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * #028 注文情報の保留<br/>
 *
 * <pre>
 * 注文保留Utilityクラス
 * </pre>
 *
 * @author satoh
 */

@Component
public class OrderPendingUtility {

    /**
     * 受注DTOクラスの注文保留セットに区分を追加します。<br/>
     * 受注DTOクラスに設定されている注文保留区分と<br/>
     * 引数に設定されている注文保留区分を比較し<br/>
     * 優先度の高いものを受注DTOに再設定します。
     *
     * @param orderDto    受注DTO
     * @param pendingType 注文保留区分
     */
    public void checkPrimaryPending(ReceiveOrderDto orderDto, HTypePendingType pendingType) {

        // 保留区分セットに追加
        if (orderDto.getPendingTypeSet() == null) {
            orderDto.setPendingTypeSet(new HashSet<>());
        }
        orderDto.getPendingTypeSet().add(pendingType);

        // 現在設定されている保留区分
        HTypePendingType currentPendingType = orderDto.getPendingType();

        if (currentPendingType == null) {
            if (pendingType != null) {
                orderDto.setPendingType(pendingType);
            }
            return;
        }

        if (pendingType == null) {
            return;
        }

        // 現在の保留区分の優先度
        Integer currentPendingTypePri = HTypePendingType.PRIMARY_MAP.get(currentPendingType);

        // 引数に設定されている保留区分の優先度
        Integer pendingTypePri = HTypePendingType.PRIMARY_MAP.get(pendingType);

        // 引数に設定されている保留区分の優先度のが高い場合は保留区分を入れ替え
        if (pendingTypePri < currentPendingTypePri) {

            orderDto.setPendingType(pendingType);
        }
    }

}
//  Customization to here
