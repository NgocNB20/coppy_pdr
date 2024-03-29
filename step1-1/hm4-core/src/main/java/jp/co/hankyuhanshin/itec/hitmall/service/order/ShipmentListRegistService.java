/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ShipmentRegistDto;

import java.util.List;

/**
 * 出荷リスト登録サービス<br/>
 *
 * @author yamaguchi
 * @version $Revision: 1.3 $
 */
public interface ShipmentListRegistService {

    /**
     * 出荷リスト登録のパラメータが0件の場合
     */
    public static final String MSGCD_ARGS_NULL = "SOO001201";

    /**
     * 出荷登録処理中にシステムエラーが発生した場合
     */
    public static final String MSGCD_SHIPMENTREGIST_FATAL = "SOO001202";

    /**
     * 出荷完了メールバッチ登録に失敗した場合
     */
    public static final String MSGCD_SHIPMENTMAIL_FATAL = "SOO001203";

    /**
     * 出荷完了メールバッチ登録に失敗した場合
     */
    public static final String MSGCD_SHIPMENT_SUCCESS_COUNT_INFO = "SOO001204";

    /**
     * 実行メソッド<br/>
     *
     * @param shipmentRegistDtoList 出荷登録DTOリスト
     * @return 処理結果メッセージリスト
     */
    List<CheckMessageDto> execute(List<ShipmentRegistDto> shipmentRegistDtoList, String administratorName);
}
