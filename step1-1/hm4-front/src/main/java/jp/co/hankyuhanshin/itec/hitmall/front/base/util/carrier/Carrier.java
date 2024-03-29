/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.base.util.carrier;

import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.EnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * キャリアの列挙。
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Getter
@AllArgsConstructor
public enum Carrier implements EnumType {

    /**
     * CRAWLER
     */
    CRAWLER("CRAWLER", "0"),

    /**
     * NTT docomo
     */
    DOCOMO("DOCOMO", "1"),

    /**
     * SoftBank mobile
     */
    SOFTBANK("SOFTBANK", "2"),

    /**
     * Disney
     */
    DISNEY("DISNEY", "3"),

    /**
     * au by KDDI
     */
    AU("AU", "4"),

    /**
     * Emobile
     */
    EMOBILE("EMOBILE", "5"),

    /**
     * Willcom
     */
    WILLCOM("WILLCOM", "6"),

    /**
     * URL
     * 絵文字用
     * エージェント、メールアドレスのキャリア判定で
     * 返ってくることはないキャリア
     */
    URL("URL", "7"),

    /**
     * EMBEDED
     * 絵文字用
     * エージェント、メールアドレスのキャリア判定で
     * 返ってくることはないキャリア
     */
    EMBEDED("EMBEDED", "8"),

    /**
     * UNKNOWN
     */
    UNKNOWN("UNKNOWN", "9");

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Carrier.class);

    /**
     * 標準のユーザエージェント設定ファイル
     */
    private static final String DEFAULT_CARRIER_AGENT =
                    Carrier.class.getName().replaceAll("(^|\\.)", "/").replaceAll("[^\\/]*$", "")
                    + "CarrierAgent.properties";

    /**
     * アプリケーションで再定義されたユーザエージェント設定ファイル
     */
    private static final String CUSTOMIZED_CARRIER_AGENT = "/config/hitmall/carrier/CarrierAgent.properties";

    /**
     * エージェント正規表現
     */
    private static final Map<String, String[]> AGENT_REGEX_MAP;

    static {
        AGENT_REGEX_MAP = loadProperties(CUSTOMIZED_CARRIER_AGENT, DEFAULT_CARRIER_AGENT);
    }

    /**
     * ラベル
     */
    private String label;
    /**
     * 区分値
     */
    private String value;

    /**
     * エージェントからキャリアを判定する
     *
     * @param agent HTTPエージェントヘッダ
     * @return キャリア
     */
    public static Carrier agent(String agent) {

        if (agent == null) {
            return UNKNOWN;
        }

        for (Carrier ca : Carrier.values()) {
            String[] regexes = AGENT_REGEX_MAP.get(ca.name());
            if (regexes == null) {
                continue;
            }

            for (String regex : regexes) {
                if (agent.matches(regex)) {
                    return ca;
                }
            }
        }

        return UNKNOWN;
    }

    /**
     * 設定ファイルを読み込む。
     *
     * @param customizedPropertiesFile カスタマイズした設定ファイル（優先）
     * @param defaultPropertiesFile    デフォルトの設定ファイル
     * @return 読み込んだ情報のマップ
     */
    private static Map<String, String[]> loadProperties(String customizedPropertiesFile, String defaultPropertiesFile) {

        // アプリケーションレベルで再設定されている場合は再設定されたファイルを使用
        InputStream inStream = Carrier.class.getResourceAsStream(customizedPropertiesFile);

        if (inStream == null) {
            // アプリケーションレベルで再設定されていない場合は、標準の設定ファイルを使用
            inStream = Carrier.class.getResourceAsStream(defaultPropertiesFile);
        }
        if (inStream == null) {
            LOGGER.warn("デフォルトのケータイキャリア設定ファイルが見つかりません。" + defaultPropertiesFile + "ファイルが必要です。");
            return new HashMap<>();
        }

        final Properties prop = new Properties();

        try {
            prop.load(new InputStreamReader(inStream, StandardCharsets.UTF_8));
        } catch (final Exception e) {
            LOGGER.warn("ケータイキャリア設定ファイルを読み込めませんでした。ファイル" + defaultPropertiesFile);
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (final IOException e) {
                    LOGGER.warn("設定ファイルクローズ処理例外。", e);
                }
            }
        }

        Map<String, String[]> map = new LinkedHashMap<>();

        for (final Object obj : prop.keySet()) {

            String key = ((String) obj);
            String val = prop.getProperty(key);
            if (key.indexOf('_') != -1) {
                key = key.substring(0, key.indexOf('_'));
            }

            key = key.toUpperCase();
            String[] vals = map.get(key);

            if (vals == null) {
                map.put(key, new String[] {val});
            } else {
                List<String> list = new ArrayList<>(Arrays.asList(vals));
                list.add(val);
                vals = list.toArray(new String[] {});
                map.put(key, vals);
            }
        }

        return Collections.unmodifiableMap(map);
    }
}
