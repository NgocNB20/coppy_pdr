/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cart;

import java.util.List;

/**
 * カート商品リスト削除<br/>
 * カート商品SEQリストを元に、カート商品情報を削除します。<br/>
 *
 * @author ozaki
 * @version $Revision: 1.2 $
 */
public interface CartGoodsListDeleteLogic {

    // LCC0004

    /**
     * カート商品リスト削除<br/>
     * カート商品SEQリストを元に、カート商品情報を削除します。<br/>
     *
     * @param cartGoodsSeqList カート商品SEQリスト
     * @param accessUid        端末識別情報
     * @param memberInfoSeq    会員SEQ
     * @return 削除件数
     */
    int execute(List<Integer> cartGoodsSeqList, String accessUid, Integer memberInfoSeq);
}
