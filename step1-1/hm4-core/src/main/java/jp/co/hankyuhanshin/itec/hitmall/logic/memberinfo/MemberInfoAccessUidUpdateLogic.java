/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

/**
 * 端末識別番号更新ロジック<br/>
 * ※携帯端末で利用
 *
 * @author natume
 * @version $Revision: 1.1 $
 */
public interface MemberInfoAccessUidUpdateLogic {

    /**
     * 端末識別番号の更新処理を行う<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param accessUid     携帯識別番号
     * @return 更新件数
     */
    int execute(Integer memberInfoSeq, String accessUid);
}
