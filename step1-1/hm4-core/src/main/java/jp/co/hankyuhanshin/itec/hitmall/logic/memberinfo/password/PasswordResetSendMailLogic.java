// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.password;

/**
 * PDR#024 メールアドレスでリマインダー<br/>
 *
 * <pre>
 * パスワード再設定メール送信ロジッククラス
 * 引数の型を変更したため、ロジッククラスに新規作成
 * PasswordResetSendMailServiceのソースを一部流用
 * 上記クラスは、今回の処理では使用しないよう修正
 * </pre>
 *
 * @author satoh
 * @version $Revision:$
 */

public interface PasswordResetSendMailLogic {

    /**
     * 会員情報取得失敗エラー<br/>
     * 会員メールアドレスが存在しない場合のエラー<br/>
     * <code>MSGCD_MEMBERINFOMAILADDRESS_FAIL</code>
     */
    public static final String MSGCD_MEMBERINFOMAILADDRESS_FAIL = "PDR-0024-001-A-";

    /**
     * 会員情報不一致エラー<br/>
     * 会員メールアドレスと会員TELが一致しない場合のエラー<br/>
     * <code>MSGCD_MEMBERINFO_NOT_EQUAL_FAIL</code>
     */
    public static final String MSGCD_MEMBERINFO_NOT_EQUAL_FAIL = "PDR-0024-002-A-";

    /**
     * 確認メール情報登録失敗エラー<br/>
     * <code>MSGCD_CONFIRMMAIL_REGIST_FAIL</code>
     */
    public static final String MSGCD_CONFIRMMAIL_REGIST_FAIL = "SMP000202";

    /**
     * 会員情報を取得する<br />
     *
     * @param memberInfoMail 会員メールアドレス
     * @param memberInfoTel  会員TEL
     * @return メール送信結果
     */
    boolean execute(String memberInfoMail, String memberInfoTel);
}
// PDR Migrate Customization to here
