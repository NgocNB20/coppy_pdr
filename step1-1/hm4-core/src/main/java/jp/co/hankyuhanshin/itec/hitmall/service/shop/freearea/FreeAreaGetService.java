/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.freearea;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;

/**
 * フリーエリア情報取得サービス<br/>
 *
 * @author natume
 * @version $Revision: 1.1 $
 */
public interface FreeAreaGetService {

    // SSF0001

    /**
     * フリーエリア情報取得処理<br/>
     *
     * @param freeAreaKey フリーエリアキー
     * @return フリーエリアエンティティ
     */
    FreeAreaEntity execute(String freeAreaKey);

    /**
     * フリーエリア情報取得処理<br/>
     *
     * @param freeAreaSeq フリーエリアSEQ
     * @return フリーエリアエンティティ
     */
    FreeAreaEntity execute(Integer freeAreaSeq);

    /**
     * フリーエリア情報取得処理<br/>
     *
     * @param freeAreaKey   フリーエリアキー
     * @param memberInfoSeq 会員SEQ
     * @return フリーエリアエンティティ
     */
    FreeAreaEntity execute(String freeAreaKey, Integer memberInfoSeq);

}
