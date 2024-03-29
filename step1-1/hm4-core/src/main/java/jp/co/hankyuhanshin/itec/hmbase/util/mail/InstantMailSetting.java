/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hmbase.util.mail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * InstantMailSetting で必要な設定を保持するクラス。
 *
 * @author tm27400
 * @version $Revision: 1.1 $
 */
public class InstantMailSetting implements Serializable, Cloneable {

    /**
     * シリアル
     */
    private static final long serialVersionUID = 2068984431026450848L;

    /**
     * メールサーバ
     */
    private String server;

    /**
     * メール送信者
     */
    private String mailFrom;

    /**
     * メールリソース
     */
    private String mailResource;

    /**
     * メールリソースの文字セット
     */
    private String mailResourceCharset = "UTF-8";

    /**
     * 障害通知先メールアドレス
     */
    private List<String> notificationReceivers;

    public InstantMailSetting() {
    }

    public InstantMailSetting(String server,
                              String mailFrom,
                              String mailResource,
                              String mailResourceCharset,
                              List<String> notificationReceivers) {
        if (server != null) {
            this.server = server;
        }
        if (mailFrom != null) {
            this.mailFrom = mailFrom;
        }
        if (mailResource != null) {
            this.mailResource = mailResource;
        }
        if (mailResourceCharset != null) {
            this.mailResourceCharset = mailResourceCharset;
        }
        if (notificationReceivers.size() > 0) {
            this.notificationReceivers = notificationReceivers;
        }
    }

    /**
     * メールサーバを登録する
     *
     * @param host host
     */
    public void setServer(final String host) {
        this.server = host;
    }

    /**
     * メール送信者を登録する
     *
     * @param sender sender
     */
    public void setMailFrom(final String sender) {
        this.mailFrom = sender;
    }

    /**
     * 使用するメールのリソースを登録する
     *
     * @param mailResource リソース名
     */
    public void setMailResource(final String mailResource) {
        this.mailResource = mailResource;
    }

    /**
     * 使用するメールのリソースの文字セットを登録する
     *
     * @param mailResourceCharset 文字セット名
     */
    public void setMailResourceCharset(final String mailResourceCharset) {
        this.mailResourceCharset = mailResourceCharset;
    }

    /**
     * 障害通知先メールアドレスを登録する
     *
     * @param receiver 通知先
     */
    public void addNotificationReceiver(final String receiver) {
        if (this.notificationReceivers == null) {
            this.notificationReceivers = new ArrayList<>();
        }
        this.notificationReceivers.add(receiver);
    }

    /**
     * 障害通知先メールアドレスを登録する
     *
     * @param receivers 通知先
     */
    public void setNotificationReceivers(final String... receivers) {
        this.notificationReceivers = Arrays.asList(receivers);
    }

    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @return the mailFrom
     */
    public String getMailFrom() {
        return mailFrom;
    }

    /**
     * @return the mailResource
     */
    public String getMailResource() {
        return mailResource;
    }

    /**
     * @return the mailResourceCharset
     */
    public String getMailResourceCharset() {
        return mailResourceCharset;
    }

    /**
     * @return the notificationReceivers
     */
    public List<String> getNotificationReceivers() {
        return notificationReceivers;
    }

    /**
     * 設定の複製を生成する。
     *
     * @return 複製
     * @throws CloneNotSupportedException 複製できない場合にスローされる
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        InstantMailSetting newOne = (InstantMailSetting) super.clone();
        newOne.notificationReceivers = new ArrayList<>();
        Collections.copy(newOne.notificationReceivers, this.notificationReceivers);
        return newOne;
    }

    /**
     * 設定のコピーを生成する。
     *
     * @return コピー
     */
    public InstantMailSetting copy() {
        try {
            return (InstantMailSetting) this.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
