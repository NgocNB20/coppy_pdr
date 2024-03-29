/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.cart;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;

/**
 * カート情報取得クラス<br/>
 * カート商品情報を取得する<br/>
 *
 * @author ozaki
 */
public interface CartGoodsGetService {

    /**
     * カート情報取得<br/>
     * カート情報を取得する。<br/>
     *
     * @param accessUid 端末識別情報
     * @param siteType  サイト区分
     * @param memberInfoSeq 会員SEQ
     * @return カートDTO
     */
    CartDto execute(String accessUid, HTypeSiteType siteType, Integer memberInfoSeq);

    // 2023-renew No14 from here

    /**
     * カート情報取得<br/>
     * カート情報を取得する。<br/>
     *
     * @param accessUid 端末識別情報
     * @param siteType  サイト区分
     * @param memberInfoSeq 会員SEQ
     * @param orderField ソート項目
     * @param goodsSeq 商品SEQ
     * @param reserveFlag 取りおきフラグ
     * @return カートDTO
     */
    CartDto execute(String accessUid,
                    HTypeSiteType siteType,
                    Integer memberInfoSeq,
                    String orderField,
                    Integer goodsSeq,
                    HTypeReserveDeliveryFlag reserveFlag);

    // 2023-renew No14 to here

}
