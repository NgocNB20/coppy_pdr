/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.saleannounce;

import jp.co.hankyuhanshin.itec.hitmall.dto.saleannounce.SaleAnnounceMailListResultDto;

import java.util.List;

/**
 * セール商品のメール配信状況取得<br/>
 *
 * @author takashima
 * @version $Revision: 1.2 $
 */
public interface SaleDeliveryStatusGetLogic {

    /**
     * 実行メソッド<br/>
     *
     * @return セールお知らせDTO
     */
    List<SaleAnnounceMailListResultDto> execute();
}
