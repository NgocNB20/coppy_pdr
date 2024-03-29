/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;

import java.util.List;

/**
 * 検索条件入金登録サービス<br/>
 *
 * @author yamaguchi
 * @version $Revision: 1.5 $
 */
public interface OrderReceiptOfMoneySearchRegistService {

    /**
     * 処理件数=0件の場合
     */
    public static final String MSGCD_RESULT_ZERO = "SOO002101";

    /**
     * 成功処理件数メッセージ
     */
    public static final String MSGCD_SUCCESS_COUNT_INFO = "SOO002102";

    /**
     * 処理対象が0件の場合
     */
    public static final String MSGCD_PAYMENT_NOTING_INFO = "SOO002103";

    /**
     * 実行メソッド<br/>
     *
     * @param orderSearchListConditionDto 受注検索一覧用条件Dto
     * @return 処理件数（受注単位）
     */
    List<CheckMessageDto> execute(OrderSearchConditionDto orderSearchListConditionDto, String administratorName);
}
