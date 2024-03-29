//  Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.utility;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.CollectionUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePendingType;
import jp.co.hankyuhanshin.itec.hitmall.front.dto.order.ReceiveOrderDto;
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

    /**
     * 受注DTOクラスに設定されている注文保留区分セットから<br/>
     * 指定された保留区分を削除し<br/>
     * 残りの保留区分セットから優先順位の一番高いものを保留区分に再設定します。
     *
     * @param orderDto    受注DTO
     * @param pendingType 注文保留区分
     */
    public void clearPrimaryPendingSet(ReceiveOrderDto orderDto, HTypePendingType pendingType) {

        // 保留区分セットに含まれていない場合
        if (CollectionUtil.isEmpty(orderDto.getPendingTypeSet()) || !orderDto.getPendingTypeSet()
                                                                             .contains(pendingType)) {
            // 処理終了
            return;
        }

        // 指定された保留区分を削除
        orderDto.getPendingTypeSet().remove(pendingType);

        // 現在設定されている保留区分が指定された保留区分でない場合
        if (!orderDto.getPendingType().equals(pendingType)) {
            // 処理終了
            return;
        }
        // 現在設定されている保留区分をクリア
        orderDto.setPendingType(null);

        // 指定された保留区分を削除した際に保留区分セットが0件になった場合
        if (CollectionUtil.isEmpty(orderDto.getPendingTypeSet())) {
            // 処理終了
            return;
        }

        // 次に優先順位の高い保留区分を設定
        for (HTypePendingType pendingTypeSet : HTypePendingType.PRIMARY_MAP.keySet()) {

            for (HTypePendingType currentPendingType : orderDto.getPendingTypeSet()) {
                if (pendingTypeSet.equals(currentPendingType)) {
                    orderDto.setPendingType(currentPendingType);
                    return;
                }
            }
        }
    }
}
//  Customization to here
