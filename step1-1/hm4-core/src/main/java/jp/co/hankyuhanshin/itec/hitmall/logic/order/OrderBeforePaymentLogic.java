/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderBeforePaymentDto;

import java.util.List;

/**
 * 前回支払方法取得ロジッククラス
 *
 * @author ota-s5
 */
// 2023-renew No14 from here
public interface OrderBeforePaymentLogic {

    /**
     * WEB-API連携 前回支払方法取得 を用いて前回利用した支払方法を取得する。
     * なお、基幹側で取得した決済が選択不可（前回決済なし、APIエラー、ECで利用不可の決済、請求なし決済）の場合、HM側DBから再度取得する。
     *
     * @param customerNo 顧客番号
     * @param settlementMethodTypeList 決済タイプリスト（画面選択可能な決済のみを設定）
     * @return 前回支払方法取得Dtoクラス
     */
    OrderBeforePaymentDto execute(Integer customerNo, List<String> settlementMethodTypeList);

}
// 2023-renew No14 to here
