/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

/**
 * 会員メールアドレス重複チェック<br/>
 *
 * @author kaneda
 */
public interface MemberInfoMailDuplicationCheckLogic {

    /**
     * メールアドレス重複チェック<br/>
     * 引数として渡されたメールアドレスが、
     * 会員マスタに登録済みでないかチェックする<br/>
     * <p>
     * 注文フローにて呼び出されることを想定<br/>
     *
     * @param orderMail ご注文主メールアドレス
     */
    void execute(String orderMail, Integer memberInfoSeq);
}