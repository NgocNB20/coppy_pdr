/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.confirmmail;

import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.confirmmail.ConfirmMailEntity;

/**
 * 確認メール情報登録<br/>
 *
 * @author natume
 */
public interface ConfirmMailRegistLogic {

    /**
     * メールパスワードの作成に失敗エラー<br/>
     * <code>MSGCD_MEMBERINFOENTITYDTO_NULL</code>
     */
    public static final String MSGCD_MAKE_CONFIRMMAILPASSWORD_FAIL = "LIM000501";

    /**
     * 確認メール情報登録処理<br/>
     *
     * @param confirmMailEntity 確認メールエンティティ
     * @return 登録件数
     */
    int execute(ConfirmMailEntity confirmMailEntity);
}
