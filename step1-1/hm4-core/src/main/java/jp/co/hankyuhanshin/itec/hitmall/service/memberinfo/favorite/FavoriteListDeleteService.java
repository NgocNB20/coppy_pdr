/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.favorite;

/**
 * お気に入り情報リスト削除サービス<br/>
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
public interface FavoriteListDeleteService {

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
     * <p>
     * ログイン会員のお気に入り情報を削除する。<br/>
     *
     * @param goodsCode 商品コード
     * @return 削除件数
     */
    int execute(Integer memberInfoSeq, String goodsCode);

    /**
     * サービス実行<br/>
     * <p>
     * ログイン会員のお気に入り情報を削除する。<br/>
     *
     * @param goodsCodes 商品コード配列
     * @return 削除件数
     */
    int execute(Integer memberInfoSeq, String[] goodsCodes);

}
