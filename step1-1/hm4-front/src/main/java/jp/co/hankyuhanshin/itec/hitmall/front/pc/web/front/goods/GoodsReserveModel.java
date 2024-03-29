/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.goods;

import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.common.reserve.AbstractReserveModel;
import lombok.Data;

/**
 * 商品_セールde予約画面Model
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
// 2023-renew No14 from here
public class GoodsReserveModel extends AbstractReserveModel {

    /**
     * 商品グループコード（リクエストパラメータ保持用）
     */
    private String ggcd;

    /**
     * カテゴリーID（リクエストパラメータ保持用）
     */
    private String cid;

    /**
     * 当画面の商品がセールde予約として既にカートINされているかどうか
     */
    private boolean existCart;

}
// 2023-renew No14 to here
