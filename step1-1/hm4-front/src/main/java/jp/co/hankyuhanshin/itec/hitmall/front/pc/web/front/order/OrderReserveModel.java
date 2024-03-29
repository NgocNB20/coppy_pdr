/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.order;

import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.AbstractReserveModel;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.ReserveItem;
import lombok.Data;

/**
 * 注文_セールde予約画面Model
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
// 2023-renew No14 from here
public class OrderReserveModel extends AbstractReserveModel {

    /**
     * セールde予約情報Item（編集前：「お届け内容の変更へ戻る」用）
     */
    private ReserveItem beforeReserveItem;

}
// 2023-renew No14 to here
