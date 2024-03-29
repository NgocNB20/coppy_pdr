/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.favorite;

/**
 * お気に入り情報リスト削除ロジック<br/>
 *
 * @author ueshima
 * @version $Revision: 1.3 $
 */
public interface FavoriteListDeleteLogic {

    /**
     * お気に入り情報リスト削除処理<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsSeqs     商品SEQ配列
     * @return 削除件数
     */
    int execute(Integer memberInfoSeq, Integer[] goodsSeqs);

    /**
     * お気に入り情報リスト削除処理<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @return 削除件数
     */
    int execute(Integer memberInfoSeq);

    /**
     * お気に入り情報リスト削除処理<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param goodsCodes    商品コード配列
     * @return 削除件数
     */
    int execute(Integer memberInfoSeq, String[] goodsCodes);

}
