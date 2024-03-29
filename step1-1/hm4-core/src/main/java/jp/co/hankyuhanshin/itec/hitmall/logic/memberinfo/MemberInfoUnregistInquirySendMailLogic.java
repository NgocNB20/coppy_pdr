// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

/**
 * PDR#011 08_データ連携（顧客情報）<br/>
 *
 * <pre>
 * 未登録会員照会発生メール ロジック
 * </pre>
 *
 * @author satoh
 * @version $Revision:$
 */

public interface MemberInfoUnregistInquirySendMailLogic {

    /** 管理者宛メール送信 有無 設定キー */
    public static final String KEY_SEND_MAIL_FLG = "admin.send.mail.flg";

    /** プロパティファイル名 */
    public static final String PROP_FILE_NAME = "/memberUnregistInquiryAlert.properties";

    /** メール名 */
    public static final String MAIL_NAME = "未登録会員照会発生通知";

    /** フラグ値：ON */
    public static final String FLG_ON = "1";

    /**
     * 未登録会員照会発生メールを送信します。<br/>
     *
     * <pre>
     * プロパティファイルでメールの送信有に設定されていた場合は
     * 管理者宛にメールの送信を行います。
     * </pre>
     *
     * @param customerNo    顧客番号
     * @param memberInfoTel 会員TEL
     */
    void execute(String customerNo, String memberInfoTel);
}
// PDR Migrate Customization to here
