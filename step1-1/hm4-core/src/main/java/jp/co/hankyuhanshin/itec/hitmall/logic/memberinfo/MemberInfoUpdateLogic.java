/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;

import java.sql.Timestamp;

/**
 * 会員情報更新
 *
 * @author natume
 */
public interface MemberInfoUpdateLogic {

    /**
     * 会員情報更新処理
     *
     * @param memberInfoEntity 会員エンティティ
     * @return 更新件数
     */
    int execute(MemberInfoEntity memberInfoEntity);

    /**
     * 更新日時指定付き会員情報更新処理
     *
     * @param memberInfoEntity 会員エンティティ
     * @param currentTime      現在日時
     * @return 更新件数
     */
    int execute(MemberInfoEntity memberInfoEntity, Timestamp currentTime);

    /**
     * 会員ログイン情報更新処理
     *
     * @param memberInfoSeq 会員SEQ
     * @param userAgent     ユーザーエージェント
     * @param deviceType    デバイス種別
     * @return 更新件数
     */
    int execute(Integer memberInfoSeq, String userAgent, HTypeDeviceType deviceType);

    /**
     * ログイン失敗回数を0にする<br/>
     * アカウントロック日時をnullにする<br/>
     * ログイン成功時、パスワードリセット時に呼び出される想定
     *
     * @param memberInfoSeq 会員SEQ
     * @return 更新件数
     */
    int resetLoginFailureCount(Integer memberInfoSeq);

    /**
     * ログイン失敗回数をインクリメントする<br/>
     * accountLockTime が渡されていれば、アカウントロック日時を更新する
     *
     * @param memberInfoSeq 会員SEQ
     * @param accountLockTime アカウントロック日時
     * @return 更新件数
     */
    int updateLoginFailureCount(Integer memberInfoSeq, Timestamp accountLockTime);

    /**
     * ログイン失敗回数をインクリメントする<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @return 更新件数
     */
    int updateLoginFailureCount(Integer memberInfoSeq);
}
