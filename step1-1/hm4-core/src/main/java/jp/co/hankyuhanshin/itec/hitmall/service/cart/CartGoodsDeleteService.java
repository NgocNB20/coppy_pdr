/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.cart;

import java.util.List;

/**
 * カート商品削除<br/>
 * カートより指定した商品情報を削除する。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.1 $
 */
public interface CartGoodsDeleteService {

    // SCC0004

    /**
     * カート商品削除<br/>
     * カートより指定した商品情報を削除する。<br/>
     *
     * @param cartGoodsSeqList カート商品情報SEQリスト
     * @param memberInfoSeq 会員SEQ
     * @param accessUid 端末識別情報
     */
    void execute(List<Integer> cartGoodsSeqList, Integer memberInfoSeq, String accessUid);
}
