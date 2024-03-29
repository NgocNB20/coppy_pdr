/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.memberinfo;

/**
 * 会員メールアドレス変更メール送信サービス<br/>
 *
 * @author natume
 * @version $Revision: 1.2 $
 */
public interface MemberInfoMailUpdateSendMailService {

    /**
     * 確認メール情報登録失敗エラー<br/>
     * <code>MSGCD_CONFIRMMAILENTITYDTO_REGIST_FAIL</code>
     */
    String MSGCD_CONFIRMMAILENTITYDTO_REGIST_FAIL = "SMM001201";

    /**
     * 会員メールアドレス変更メール送信処理<br/>
     *
     * @param memberInfoSeq 会員SEQ
     * @param mail          メールアドレス
     * @return メール送信結果
     */
    boolean execute(Integer memberInfoSeq, String mail);
}
