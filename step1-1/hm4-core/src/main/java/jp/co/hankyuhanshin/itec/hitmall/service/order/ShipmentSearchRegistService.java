/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;

import java.util.List;

/**
 * 検索条件出荷登録サービス<br/>
 *
 * @author yamaguchi
 * @version $Revision: 1.3 $
 */
public interface ShipmentSearchRegistService {

    /**
     * 出荷登録処理中にシステムエラーが発生した場合
     */
    public static final String MSGCD_SHIPMENTREGIST_FATAL = "SOO002301";

    /**
     * 出荷完了メールバッチ登録に失敗した場合
     */
    public static final String MSGCD_SHIPMENTMAIL_FATAL = "SOO002302";

    /**
     * 出荷処理件数
     */
    public static final String MSGCD_SHIPMENT_SUCCESS_COUNT_INFO = "SOO002303";

    /**
     * 出荷処理対象なし
     */
    public static final String MSGCD_SHIPMENT_NOTING_INFO = "SOO002304";

    /**
     * 実行メソッド<br/>
     *
     * @param orderSearchListConditionDto 受注検索一覧用条件Dto
     * @param sendMailFlag                メール送信フラグ
     * @return 処理結果メッセージリスト
     */
    List<CheckMessageDto> execute(OrderSearchConditionDto orderSearchListConditionDto,
                                  Boolean sendMailFlag,
                                  String administratorName);
}
