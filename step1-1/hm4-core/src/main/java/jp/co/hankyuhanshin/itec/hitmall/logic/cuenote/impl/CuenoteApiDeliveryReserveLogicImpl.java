/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiDeliveryReserveRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.AbstractCuenoteApiLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiDeliveryReserveLogic;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import net.arnx.jsonic.JSON;

/**
 * Cuenote API
 * 配信情報予約API
 *
 * @author st75001
 *
 */
@Component
public class CuenoteApiDeliveryReserveLogicImpl extends AbstractCuenoteApiLogic implements CuenoteApiDeliveryReserveLogic {

    protected CuenoteApiDeliveryReserveRequestDto cuenoteApiDeliveryReserveRequestDto;

    /**
     * 接続URLを返却
     * @return 「HTTPステータスコード 」
     */
    @Override
    protected String getUri() {
        return urlCuenoteApi + pathDeliveryReserve;
    }

    /**
     * 成功時HTTPステータスコード を返却
     * @return 「HTTPステータスコード 」
     */
    @Override
    protected int getSuccessHttpStatusCode() {
        return HttpStatus.SC_CREATED;
    }

    /**
     * ログ出力用のプレフィックスを返します。
     * @return 「API名/APIを利用する機能/」
     */
    @Override
    protected String getLogPrefix() {
        return "配信情報予約API";
    }

    /**
     * ポストデータ作成
     *
     * @return ポストデータ
     */
    @Override
    protected String createPostData() {
        // オブジェクトからJSON変換
        return JSON.encode(cuenoteApiDeliveryReserveRequestDto, true);
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
     * 配信情報予約API 実行
     *
     * @return メール文書セット複製DTO
     * @throws Exception 例外
     */
    @Override
    public String execute(CuenoteApiDeliveryReserveRequestDto paramCuenoteApiDeliveryReserveRequestDto) throws Exception {

        cuenoteApiDeliveryReserveRequestDto = paramCuenoteApiDeliveryReserveRequestDto;

        // API呼び出し
        super.execute(MessageFormat.format(getUri(), null), null, null);

        // ロケーションの形式で返却される（例：https://fc-demo67x.cuenote.jp/delivery/v3.5/mail/{delivery_Id}）
        return getDeliveryId();

    }

    /**
     *
     * 配信ＩＤを返却
     *
     * @return レスポンス情報格納Dto
     */
    protected String getDeliveryId() {
        if (StringUtils.isEmpty(authUser)) {
            // 認証ユーザ情報がなければモックと判定し、モック値を返却する。
            return "20880";
        }
        return getResponseHeader("Location");
    }
}
