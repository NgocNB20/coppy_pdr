/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.order;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCouponLimitTargetType;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;

/**
 * 受注キャンセルサービス<br/>
 *
 * @author USER
 * @version $Revision: 1.5 $
 */
public interface ReceiveOrderCancelService {

    /**
     * 受注サマリー取得不可能エラー
     */
    public static final String MSGCD_ORDERSUMMARYENTITYDTO_NULL = "SOO002601";

    /**
     * 受注更新エラー
     */
    public static final String MSGCD_ORDERCANCEL_FAIL = "SOO002602";

    /**
     * 受注更新エラー
     */
    public static final String MSGCD_CONVENIBILL_CHANGE_ERROR = "SOO002603";

    /**
     * 実行メソッド<br/>
     *
     * @param orderCode      受注番号
     * @param orderVersionNo 受注履歴連番
     * @return チェックメッセージDTO（エラー時のみ）
     */
    CheckMessageDto execute(String orderCode, Integer orderVersionNo, String administratorName);

    /**
     * 実行メソッド<br/>
     *
     * @param orderCode             受注番号
     * @param orderVersionNo        受注履歴連番
     * @param memo                  管理者メモ
     * @param couponLimitTargetType クーポン利用制限対象種別
     * @return チェックメッセージDTO（エラー時のみ）
     */
    CheckMessageDto execute(String orderCode,
                            Integer orderVersionNo,
                            String memo,
                            HTypeCouponLimitTargetType couponLimitTargetType,
                            String administratorName);
}
