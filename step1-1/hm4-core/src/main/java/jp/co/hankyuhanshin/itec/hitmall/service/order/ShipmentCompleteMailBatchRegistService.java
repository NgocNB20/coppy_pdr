/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.ShipmentRegistDto;

import java.util.List;

/**
 * 出荷完了メール自動送信サービス(非同期)<br/>
 *
 * @author USER
 * @version $Revision: 1.2 $
 */
public interface ShipmentCompleteMailBatchRegistService {

    /**
     * 出荷完了メール送信処理<br/>
     *
     * @param shipmentRegistDtoList 出荷処理対象の出荷情報
     * @return int 1:異常終了、0:正常終了
     */
    int execute(List<ShipmentRegistDto> shipmentRegistDtoList);
}
