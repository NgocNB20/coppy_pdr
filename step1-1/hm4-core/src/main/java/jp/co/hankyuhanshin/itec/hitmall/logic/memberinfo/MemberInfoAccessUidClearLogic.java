/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

/**
 * 端末識別番号クリアロジック<br/>
 * ※携帯端末で利用
 */
public interface MemberInfoAccessUidClearLogic {

    /**
     * 端末識別番号クリアを行う<br/>
     *
     * @param accessUid 携帯識別番号
     * @return 更新件数
     */
    int execute(String accessUid);
}
