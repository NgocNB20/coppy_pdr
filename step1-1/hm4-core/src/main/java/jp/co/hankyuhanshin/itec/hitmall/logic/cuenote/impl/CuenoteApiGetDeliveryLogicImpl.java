/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiGetDeliveryResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.AbstractCuenoteApiLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiGetDeliveryLogic;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
/**
 * Cuenote API
 * 配信情報取得API
 *
 * @author st75001
 *
 */
@Component
public class CuenoteApiGetDeliveryLogicImpl extends AbstractCuenoteApiLogic implements CuenoteApiGetDeliveryLogic {

    /**
     * 接続URLを返却
     * @return 「HTTPステータスコード 」
     */
    @Override
    protected String getUri() {
        return urlCuenoteApi + pathGetDelivery;
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
        return "配信情報取得API";
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
     *
     * メール文書セット複製API 実行
     *
     * @return メール文書セット複製DTO
     * @throws Exception 例外
     */
    @Override
    public CuenoteApiGetDeliveryResponseDto execute(String deliveryId) throws Exception {

        // API呼び出し
        return super.execute(MessageFormat.format(getUri(), deliveryId), CuenoteApiGetDeliveryResponseDto.class, null);

    }

}
