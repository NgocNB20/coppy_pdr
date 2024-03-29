/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.base.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * スマートフォン対応で使用するための関数群。
 * 使用するには init() 関数を前もって呼んでおく必要がある。
 *
 * @author Tomo (itec) 2011/03/23 スマートフォン対応
 * @author Kaneko (itec) 2012/08/10 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class SmartPhoneUtility {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(SmartPhoneUtility.class);

    /** 対象ユーザエージェントキーワード */
    private final String[] keywords;

    /** 対象ユーザエージェントキーワード（スマートフォン） */
    private final String[] keywordsDeviceSp;

    /** 対象ユーザエージェントキーワード（タブレット） */
    private final String[] keywordsDeviceTab;

    /**
     * リソースを取得し初期化する処理
     * 初期化処理
     * SmartPhoneFilter から初期化される。
     */
    public SmartPhoneUtility(Environment environment) {

        //
        // スマートフォン版HTMLに差し替える対象の User-Agent キーワード
        //
        keywords = readUserAgentKeywords(environment, "userAgent_keywords");

        for (String keyword : keywords) {
            LOGGER.info("スマートフォン判定User-Agentキーワード:" + keyword);
        }

        // デバイス判定スマートフォンの User-Agent キーワード
        keywordsDeviceSp = readUserAgentKeywords(environment, "device_sp_userAgent_keywords");
        for (String keyword : keywordsDeviceSp) {
            LOGGER.info("スマートフォンデバイス判定User-Agentキーワード:" + keyword);
        }

        // デバイス判定タブレットの User-Agent キーワード
        keywordsDeviceTab = readUserAgentKeywords(environment, "device_tab_userAgent_keywords");
        for (String keyword : keywordsDeviceTab) {
            LOGGER.info("タブレットデバイス判定User-Agentキーワード:" + keyword);
        }

    }

    /**
     * User-Agentキーワードのプロパティ読み込み
     *
     * @param propKey プロパティキー
     * @return User-Agentキーワード
     */
    protected static String[] readUserAgentKeywords(Environment environment, String propKey) {
        String userAgentKeywords = environment.getProperty(propKey);
        if (userAgentKeywords != null && !userAgentKeywords.trim().isEmpty()) {
            return userAgentKeywords.toUpperCase().split(" *, *");
        }
        return new String[0];
    }

    /**
     * スマートフォンのUser-Agentであるかを判定
     *
     * @param userAgent User-Agent
     * @return true = スマートフォン
     */
    public boolean isDeviceTypeSp(String userAgent) {
        return isTargetUserAgent(keywordsDeviceSp, userAgent);
    }

    /**
     * タブレットのUser-Agentであるかを判定
     *
     * @param userAgent User-Agent
     * @return true = タブレット
     */
    public boolean isDeviceTypeTab(String userAgent) {
        return isTargetUserAgent(keywordsDeviceTab, userAgent);
    }

    /**
     * User-Agentにキーワードが含まれている場合に true を返す
     *
     * @param keywords キーワード
     * @param userAgent User-Agent
     * @return true = キーワードが含まれる
     */
    protected boolean isTargetUserAgent(String[] keywords, String userAgent) {
        if (keywords == null) {
            return false;
        }

        String upperCaseUserAgent = userAgent.toUpperCase();
        for (String keyword : keywords) {
            boolean equiv = true;
            for (String part : keyword.split("\\+")) {
                if (!upperCaseUserAgent.contains(part)) {
                    equiv = false;
                    break;
                }
            }
            if (equiv) {
                return true;
            }
        }
        return false;
    }

}
