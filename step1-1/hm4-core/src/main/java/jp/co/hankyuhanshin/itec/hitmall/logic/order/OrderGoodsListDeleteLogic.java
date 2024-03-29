/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

/**
 * 受注商品リスト削除ロジック<br/>
 *
 * @author YAMAGUCHI
 * @version $Revision: 1.2 $
 */
public interface OrderGoodsListDeleteLogic {

    /**
     * 実行メソッド<br/>
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @return 削除件数
     */
    int execute(Integer orderSeq, Integer orderGoodsVersionNo);

    /**
     * 実行メソッド<br/>
     *
     * @param orderSeq            受注SEQ
     * @param orderGoodsVersionNo 受注商品連番
     * @param orderConsecutiveNo  注文連番
     * @return 削除件数
     */
    int execute(Integer orderSeq, Integer orderGoodsVersionNo, Integer orderConsecutiveNo);
}
