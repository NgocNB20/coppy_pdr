//  Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.order;

/**
 * PDR#011 08_データ連携（顧客情報）<br/>
 *
 * <pre>
 * 住所録登録失敗通知メール ロジック
 * </pre>
 * @author satoh
 *
 */
public interface OrderRegistFailureAddressBookSendMailLogic {

    /** 管理者宛メール送信 有無 設定キー */
    public static final String KEY_SEND_MAIL_FLG = "admin.send.mail.flg";

    /** プロパティファイル名 */
    public static final String PROP_FILE_NAME = "/OrderRegistFailureAddressBookAlert.properties";

    /** メール名 */
    public static final String MAIL_NAME = "住所録登録失敗通知";

    /** フラグ値：ON */
    public static final String FLG_ON = "1";

    /**
     * 住所録の登録に失敗した場合のメールを送信します。<br/>
     *
     * <pre>
     * プロパティファイルでメールの送信有に設定されていた場合は
     * 管理者宛にメールの送信を行います。
     * </pre>
     * @param customerNo 顧客番号
     */
    void execute(Integer customerNo);
}
//  Customization to here
