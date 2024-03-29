/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cuenote;

import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiDeliveryReserveRequestDto;

/**
 * Cuenote API
 * 配信情報予約API
 *
 * @author st75001
 *
 */
public interface CuenoteApiDeliveryReserveLogic extends CuenoteApiLogic {

    /**
     *
     * 配信情報予約API 実行
     *
     * @return 配信情報予約APIリクエストDTO
     * @throws Exception 例外
     */
    String execute(CuenoteApiDeliveryReserveRequestDto cuenoteApiDeliveryReserveRequestDto) throws Exception;

}
