/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.cuenoteapi.pdr.common;

import javax.servlet.ServletContext;
import java.util.Properties;

/**
 * PDR#15 CUENOTE-API連携 プロパティファイル共通ファイル<br/>
 *
 * @author st75001
 */
public class PropManeger {

    /**
     * コンストラクタ<br/>
     */
    private PropManeger() {

    }

    /**
     * messages.propertiesからメッセージを取得します。
     *
     * @param context コンテキスト
     * @param key キー
     * @return メッセージ
     */
    public static String getMessage(ServletContext context, String key) {

        Properties pro = (Properties) context.getAttribute("msg");

        return (String) pro.get(key);
    }

    /**
     * conf.propertiesから設定値を取得します。
     *
     * @param context コンテキスト
     * @param key キー
     * @return 設定値
     */
    public static String getConf(ServletContext context, String key) {

        Properties pro = (Properties) context.getAttribute("conf");

        return (String) pro.get(key);
    }
}
