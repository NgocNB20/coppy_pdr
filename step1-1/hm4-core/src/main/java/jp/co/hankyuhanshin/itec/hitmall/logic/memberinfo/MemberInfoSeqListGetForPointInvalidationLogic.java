/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import java.util.List;

/**
 * 【ポイント部品】<br/>
 * 無効化条件を満たす会員SEQリスト取得ロジックのインターフェースクラス。
 *
 * @author k.harada
 */
public interface MemberInfoSeqListGetForPointInvalidationLogic {

    /**
     * 期限切れポイント無効化バッチの無効化条件を満たす会員SEQリストを取得する。
     *
     * @return 無効化条件を満たす会員SEQリスト
     */
    List<Integer> execute();

}
