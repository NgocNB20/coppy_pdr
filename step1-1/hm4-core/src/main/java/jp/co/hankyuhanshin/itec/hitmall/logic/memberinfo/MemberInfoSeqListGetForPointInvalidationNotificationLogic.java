/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import java.sql.Timestamp;
import java.util.List;

/**
 * 【ポイント部品】<br/>
 * ポイント有効期限告知対象の会員SEQリスト取得ロジックのインターフェースクラス。
 *
 * @author k.kizaki
 */
public interface MemberInfoSeqListGetForPointInvalidationNotificationLogic {

    /**
     * ポイント有効期限告知対象の会員SEQリストを取得する。
     * <pre>
     * 会員状態 == 入会 かつ
     * 《[ポイントの有効期限日]の最大値  = 指定日》 となる会員が対象
     * </pre>
     *
     * @param checkeDate 指定日
     * @return 会員SEQリスト
     */
    List<Integer> execute(Timestamp checkeDate);

}
