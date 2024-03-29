/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;

/**
 * 会員メールアドレス変更サービス(本登録)<br/>
 *
 * @author natume
 */
public interface MemberInfoMailUpdateService {

    /* errorcode */

    /**
     * 会員情報更新エラー<br/>
     * <code>MSGCD_MEMBERINFO_UPDATE_ERROR</code>
     */
    String MSGCD_MEMBERINFO_UPDATE_ERROR = "SMM001301";

    // 2023-renew No0 from here
    /**
     * 確認メール削除失敗エラー<br/>
     * <code></code>
     */
    String MSGCD_CONFIRMMAIL_DELETE_ERROR = "PDR-2023RENEW-0-001-";
    // 2023-renew No0 to here

    /**
     * 会員メールアドレス変更処理<br/>
     *
     * @param memberInfoEntity    会員エンティティ
     * @param confirmMailPassword 確認メールパスワード
     */
    void execute(MemberInfoEntity memberInfoEntity, String confirmMailPassword);
}
