/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cuenote;

import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiAddressImportRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiAddressImportResponseDto;

import java.util.List;

/**
 * Cuenote API
 * アドレス帳インポートAPI
 *
 * @author st75001
 *
 */
public interface CuenoteApiAddressImportLogic extends CuenoteApiLogic {

    String CUENOTE_ADBOOK_IMPORT_CSV_HEADER = "email,office_name,goods_info";

    String POST_REQUEST_X_MODE_REPLACE = "replace";

    /**
     *
     * アドレス帳インポートAPI 実行
     *
     * @return アドレス帳インポートAPIDTO
     * @throws Exception 例外
     */
    CuenoteApiAddressImportResponseDto execute(List<CuenoteApiAddressImportRequestDto> cuenoteApiAddressImportRequestDtoList, String path) throws Exception;

}
