/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cuenote;

import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiGetDeliveryResponseDto;

/**
 * Cuenote API
 * 配信情報取得API
 *
 * @author st75001
 *
 */
public interface CuenoteApiGetDeliveryLogic extends CuenoteApiLogic {

    /**
     *
     * メール文書セット複製API 実行
     *
     * @return 配信情報予約APIリクエストDTO
     * @throws Exception 例外
     */
    CuenoteApiGetDeliveryResponseDto execute(String deliveryId) throws Exception;

}
