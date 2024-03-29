/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.confirmmail;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeConfirmMailType;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.confirmmail.ConfirmMailEntity;

/**
 * 確認メール情報取得<br/>
 *
 * @author natume
 */
public interface ConfirmMailGetLogic {

    /**
     * 確認メール情報取得<br/>
     *
     * @param password メールパスワード
     * @return 確認メールエンティティ
     */
    ConfirmMailEntity execute(String password);

    /**
     * 確認メール情報取得<br/>
     *
     * @param password        メールパスワード
     * @param confirmMailType 確認メール種別
     * @return 確認メールエンティティ
     */
    ConfirmMailEntity execute(String password, HTypeConfirmMailType confirmMailType);
}
