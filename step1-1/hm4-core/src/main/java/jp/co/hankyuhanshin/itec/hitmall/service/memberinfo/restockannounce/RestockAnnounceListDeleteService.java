// 2023-renew No65 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.restockannounce;

/**
 * 入荷お知らせ情報リスト削除サービス<br/>
 *
 * @author hung.leviet
 * @version $Revision: 1.3 $
 */
public interface RestockAnnounceListDeleteService {

    /**
     * サービス実行<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsSeqs     商品SEQ配列
     * @return 削除件数
     */
    int execute(Integer memberInfoSeq, Integer[] goodsSeqs);

    /**
     * サービス実行<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsSeq      商品SEQ
     * @return 削除件数
     */
    int execute(Integer memberInfoSeq, Integer goodsSeq);

    /**
     * サービス実行<br/>
     * @param goodsCode 商品コード
     * @return 削除件数
     */
    int execute(Integer memberInfoSeq, String goodsCode);

    /**
     * サービス実行<br/>
     * @param goodsCodes 商品コード配列
     * @return 削除件数
     */
    int execute(Integer memberInfoSeq, String[] goodsCodes);

}
// 2023-renew No65 to here
