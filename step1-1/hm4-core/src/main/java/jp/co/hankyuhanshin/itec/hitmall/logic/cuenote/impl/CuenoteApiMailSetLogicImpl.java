/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.impl;

import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiMailSetRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.AbstractCuenoteApiLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiMailSetLogic;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import net.arnx.jsonic.JSON;
import org.apache.commons.lang3.StringUtils;

/**
 * Cuenote API
 * メール文書セット複製API
 *
 * @author st75001
 *
 */
@Component
public class CuenoteApiMailSetLogicImpl extends AbstractCuenoteApiLogic implements CuenoteApiMailSetLogic {

    protected CuenoteApiMailSetRequestDto cuenoteApiMailSetRequestDto;

    /**
     * 接続URLを返却
     * @return 「HTTPステータスコード 」
     */
    @Override
    protected String getUri() {
        return urlCuenoteApi + pathMailSet;
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
        return "メール文書セット複製API";
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
        // オブジェクトからJSON変換
        return JSON.encode(cuenoteApiMailSetRequestDto, true);
    }

    /**
     *
     * メール文書セット複製API 実行
     *
     * @return メール文書セット複製DTO
     * @throws Exception 例外
     */
    @Override
    public String execute(CuenoteApiMailSetRequestDto paramCuenoteApiMailSetRequestDto) throws Exception {
        cuenoteApiMailSetRequestDto = paramCuenoteApiMailSetRequestDto;

        // API呼び出し
        super.execute(MessageFormat.format(getUri(), null), null, null);

        // ロケーションの形式で返却される（例：https://fc-demo67x.cuenote.jp/fcapi/v3.5/mail/{mail_Id}）
        return getMailSetId();

    }

    /**
     *
     * メール文書セットＩＤを返却
     *
     * @return レスポンス情報格納Dto
     */
    protected String getMailSetId() {
        if (StringUtils.isEmpty(authUser)) {
            // 認証ユーザ情報がなければモックと判定し、モック値を返却する。
            return "20880";
        }
        return getResponseHeader("Location");
    }

}
