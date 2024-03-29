/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComResultDto;

/**
 * 【決済オプション部品（ペイジェント）】
 * マルチペイメント検索通信処理ロジック
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
// Paygent Customization from here
public interface SearchTranLogic {

    /**
     * 決済情報照会をする。
     *
     * @param orderId 決済ID
     * @return レスポンス情報
     */
    ComResultDto getPaymentInfo(String orderId);
}
// Paygent Customization to here
