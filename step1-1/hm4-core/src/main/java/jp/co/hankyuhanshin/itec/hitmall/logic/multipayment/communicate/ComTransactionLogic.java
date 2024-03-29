/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.ComResultDto;

/**
 * ペイジェントとの通信APIのラッパー
 * <pre>
 * ペイジェントとの通信をインターセプトする為だけに作成
 * </pre>
 *
 * @author y.kawai
 */
// Paygent Customization from here
public interface ComTransactionLogic {

    /**
     * ペイジェントの通信APIを実行
     *
     * @param request Map key：POSTパラメータ名 value：設定値
     * @return レスポンス情報
     */
    ComResultDto execute(ComRequestDto request);
}
// Paygent Customization to here
