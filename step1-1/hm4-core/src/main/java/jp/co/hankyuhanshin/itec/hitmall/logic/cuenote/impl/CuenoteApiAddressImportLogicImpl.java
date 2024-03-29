/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.impl;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;

import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiAddressImportRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiAddressImportResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.AbstractCuenoteApiLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiAddressImportLogic;
import jp.co.hankyuhanshin.itec.hmbase.utility.CsvExtractUtility;
import net.arnx.jsonic.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Cuenote API
 * アドレス帳インポートAPI
 *
 * @author st75001
 *
 */
@Component
public class CuenoteApiAddressImportLogicImpl extends AbstractCuenoteApiLogic implements CuenoteApiAddressImportLogic {

    protected List<CuenoteApiAddressImportRequestDto> cuenoteApiAddressImportRequestDtoList;

    protected String path;

    /**
     * 接続URLを返却(未使用、引数ありを利用)
     * @return 「HTTPステータスコード 」
     */
    @Override
    protected String getUri() {
        return urlCuenoteApi + path;
    }

    /**
     * 成功時HTTPステータスコード を返却
     * @return 「HTTPステータスコード 」
     */
    @Override
    protected int getSuccessHttpStatusCode() {
        return HttpStatus.SC_OK;
    }

    /**
     * ログ出力用のプレフィックスを返します。
     * @return 「API名/APIを利用する機能/」
     */
    @Override
    protected String getLogPrefix() {
        return "アドレス帳インポートAPI";
    }

    /**
     * Content-Typeを返却する<br/>
     *
     * @return Content-Type
     */
    @Override
    protected String getContentType() {

        if (StringUtils.isEmpty(authUser)) {
            // モックの場合JSONを返却する
            return "application/json; charset=UTF-8";
        }

        return "text/csv; charset=UTF-8";
    }

    /**
     * HTTPステータスコード を返却する
     * @return 「HTTPステータスコード 」
     */
    @Override
    public int getHttpStatusCode() {
        return httpStatusCode;
    }
    /**
     * ポストデータ作成
     *
     * @return ポストデータ
     */
    @Override
    protected String createPostData() {

        return convertDtoToCsvString(cuenoteApiAddressImportRequestDtoList);
    }

    // DTOをCSV形式の文字列に変換
    protected String convertDtoToCsvString(List<CuenoteApiAddressImportRequestDto> cuenoteApiAddressImportRequestDtoList) {

        if (StringUtils.isEmpty(authUser)) {
            // モックのJSON形式で返却
            return JSON.encode(cuenoteApiAddressImportRequestDtoList.get(0), true);
        }

        StringBuilder sb = new StringBuilder();
        // ヘッダ行を追加
        sb.append(CUENOTE_ADBOOK_IMPORT_CSV_HEADER);
        // データ行を追加
        for (CuenoteApiAddressImportRequestDto cuenoteApiAddressImportRequestDto : cuenoteApiAddressImportRequestDtoList) {
            sb.append("\r\n");
            sb.append(cuenoteApiAddressImportRequestDto.email).append(",");
            sb.append(cuenoteApiAddressImportRequestDto.officeName).append(",");
            sb.append(cuenoteApiAddressImportRequestDto.goodsInfo);
        }
        byte[] utf8Bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
        String utf8String = new String(utf8Bytes, StandardCharsets.UTF_8);

        return utf8String;
    }

    /**
     *
     * アドレス帳インポートAPI 実行
     *
     * @return アドレス帳インポートAPIレスポンスDTO
     * @throws Exception 例外
     */
    @Override
    public CuenoteApiAddressImportResponseDto execute(List<CuenoteApiAddressImportRequestDto> paramCuenoteApiAddressImportRequestDtoList, String paramPath) throws Exception {

        cuenoteApiAddressImportRequestDtoList = paramCuenoteApiAddressImportRequestDtoList;
        path = paramPath;

        // API呼び出し
        return super.execute(MessageFormat.format(getUri(), null), CuenoteApiAddressImportResponseDto.class, POST_REQUEST_X_MODE_REPLACE);

    }

}
