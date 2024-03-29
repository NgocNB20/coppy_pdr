// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.restockannounce;

/**
 * 入荷お知ら入り情報リスト削除ロジック<br/>
 *
 * @author hung.leviet
 * @version $Revision: 1.3 $
 */
public interface RestockAnnounceListDeleteLogic {

    /**
     * 入荷お知ら入り情報リスト削除処理<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsSeqs     商品SEQ配列
     * @return 削除件数
     */
    int execute(Integer memberInfoSeq, Integer[] goodsSeqs);

    /**
     * 入荷お知ら入り情報リスト削除処理<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @return 削除件数
     */
    int execute(Integer memberInfoSeq);

    /**
     * 入荷お知ら入り情報リスト削除処理<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsCodes    商品コード配列
     * @return 削除件数
     */
    int execute(Integer memberInfoSeq, String[] goodsCodes);

}
// 2023-renew No65 to here