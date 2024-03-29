/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.cart;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsRegistCheckDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * カート商品追加サービス
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
public interface CartGoodsAddService {

    /**
     * カート情報に商品を追加する。
     *
     * @param goodsCode 商品コード
     * @param count     数量
     * @param accessUid 端末識別情報
     * @param memberInfoSeq 会員SEQ
     * @param siteType      サイト区分
     * @param cartGoodsRegistCheckDto カート投入チェックDto
     * @return エラー商品リスト
     */
    List<CheckMessageDto> execute(String goodsCode,
                                  BigDecimal count,
                                  String accessUid,
                                  Integer memberInfoSeq,
                                  HTypeSiteType siteType,
                                  // 2023-renew No14 from here
                                  CartGoodsRegistCheckDto cartGoodsRegistCheckDto
                                  // 2023-renew No14 to here
                                 );

}
