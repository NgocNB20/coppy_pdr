/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cuenote;

import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiMailSetRequestDto;

/**
 * Cuenote API
 * メール文書セット複製API
 *
 * @author st75001
 *
 */
public interface CuenoteApiMailSetLogic extends CuenoteApiLogic {

    /**
     *
     * メール文書セット複製API 実行
     *
     * @return セットしたメール文書ID
     * @throws Exception 例外
     */
    String execute(CuenoteApiMailSetRequestDto cuenoteApiMailSetRequestDto) throws Exception;

}
