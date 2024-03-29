/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;

/**
 * フリーエリア情報取得ロジック<br/>
 *
 * @author natume
 * @version $Revision: 1.2 $
 */
public interface FreeAreaGetLogic {

    // LSF0001

    /**
     * フリーエリア情報取得処理<br/>
     *
     * @param shopSeq     ショップSEQ
     * @param freeAreaKey フリーエリアキー
     * @return フリーエリアエンティティ
     */
    FreeAreaEntity execute(Integer shopSeq, String freeAreaKey);

    /**
     * フリーエリア情報取得処理
     *
     * @param shopSeq     ショップSEQ
     * @param freeAreaSeq フリーエリアSEQ
     * @return フリーエリアエンティティ
     */
    FreeAreaEntity execute(Integer shopSeq, Integer freeAreaSeq);

    /**
     * フリーエリア情報取得処理<br/>
     *
     * @param shopSeq       ショップSEQ
     * @param freeAreaKey   フリーエリアキー
     * @param memberInfoSeq 会員SEQ
     * @return フリーエリアエンティティ
     */
    FreeAreaEntity execute(Integer shopSeq, String freeAreaKey, Integer memberInfoSeq);
}
