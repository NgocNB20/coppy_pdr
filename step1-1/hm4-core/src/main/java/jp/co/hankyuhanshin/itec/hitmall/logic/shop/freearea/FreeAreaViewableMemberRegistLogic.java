/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.freearea;

import java.util.List;

/**
 * フリーエリア表示対象会員情報登録ロジック<br/>
 *
 * @author Yu
 */
public interface FreeAreaViewableMemberRegistLogic {
    /**
     * フリーエリア表示対象会員情報登録処理<br/>
     *
     * @param freeAreaSeq       フリーエリアSEQ
     * @param memberInfoSeqList 対象会員リスト
     * @return 処理件数
     */
    int execute(Integer freeAreaSeq, List<Integer> memberInfoSeqList);

}
