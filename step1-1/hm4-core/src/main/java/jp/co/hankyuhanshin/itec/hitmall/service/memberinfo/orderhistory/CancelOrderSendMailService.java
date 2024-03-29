/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.orderhistory;

import jp.co.hankyuhanshin.itec.hitmall.dto.memberinfo.orderhistory.CancelOrderHistoryDto;

/**
 * 注文キャンセルメール送信サービス
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
// 2023-renew No68 from here
public interface CancelOrderSendMailService {

    /**
     * 会員情報取得失敗エラー
     */
    String MSGCD_MEMBERINFOENTITYDTO_NULL = "SMM001001";

    /**
     * 注文キャンセルメール送信
     *
     * @param memberInfoSeq         会員SEQ
     * @param cancelOrderHistoryDto 注文履歴キャンセルDtoクラス
     * @return メール送信結果
     */
    boolean execute(Integer memberInfoSeq, CancelOrderHistoryDto cancelOrderHistoryDto);

}
// 2023-renew No68 to here
