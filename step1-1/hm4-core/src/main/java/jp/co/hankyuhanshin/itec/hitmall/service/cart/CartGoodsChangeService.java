/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.cart;

import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartGoodsDto;

import java.util.List;

/**
 * カート情報変更クラス<br/>
 * カート情報を更新する。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface CartGoodsChangeService {

    // SCC0005

    /**
     * メソッド概要<br/>
     * メソッドの説明・概要<br/>
     *
     * @param cartGoodsDtoList 変更するカート商品情報リスト
     * @param accessUid        端末識別番号
     * @param memberInfoSeq    会員SEQ
     */
    void execute(List<CartGoodsDto> cartGoodsDtoList, String accessUid, Integer memberInfoSeq);
}
