/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment;

import javax.servlet.http.HttpServletRequest;

/**
 * マルチペイメント決済結果通知受付サーブレットロジック<br/>
 *
 * @author na65101 STS Nakamura 2020/03/11 #4181 GMO経由AmazonPay対応
 */
public interface MulPayNotificationReceiverLogic {

    /**
     * 実行処理<br/>
     * HTTPリクエストのパラメータチェックやマルペイ請求やマルペイ決済結果への登録・更新処理を行う。<br/>
     *
     * @param req リクエスト
     */
    void execute(HttpServletRequest req);

}
