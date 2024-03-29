package jp.co.hankyuhanshin.itec.hitmall.logic.ukapi;

import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.AbstractUkApiResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.JsonUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * UK-API連携 基底Logic
 * @author tt32117
 */
@Component
public abstract class AbstractUkApiLogic extends AbstractShopLogic {
    /** JSONユーティリティ */
    public JsonUtility jsonUtility;
    /** ロガー */
    protected Logger log;
    /** 文字コード */
    protected String charset;
    /** 接続タイムアウト時間(ミリ秒) */
    private int connectTimeout;
    /** 読み取りタイムアウト時間(ミリ秒) */
    private int readTimeout;
    /** リクエスト・レスポンスデータログ出力有無 */
    private boolean isOutputLog;

    /**
     * UK-API連携処理でエラーが発生した場合<br/>
     * <code>MSG_ERR_CODE</code>
     */
    public static final String MSG_ERR_CODE = "PDR-0015-001-A-";

    /** フラグ値：ON */
    protected static final String FLG_ON = "1";

    /** HTTPステータス：200(正常) */
    protected static final int HTTP_STATUS_SUCCESS = 200;

    /** UK-API ステータス：0(正常) */
    public static final String WEB_API_STATUS_SUCCESS = "0";

    /** コンストラクタ */
    @Autowired
    public AbstractUkApiLogic(JsonUtility jsonUtility) {
        // ロガー
        log = getLogger();
        // 文字コード
        charset = PropertiesUtil.getSystemPropertiesValue("ukapi.charset");
        // 接続タイムアウト時間(ミリ秒)
        connectTimeout = Integer.parseInt(PropertiesUtil.getSystemPropertiesValue("ukapi.connect.timeout"));
        // 読み取りタイムアウト時間(ミリ秒)
        readTimeout = Integer.parseInt(PropertiesUtil.getSystemPropertiesValue("ukapi.read.timeout"));
        // リクエスト・レスポンスデータログ出力有無
        isOutputLog = FLG_ON.equals(PropertiesUtil.getSystemPropertiesValue("ukapi.log.output.flg"));

        this.jsonUtility = jsonUtility;
    }

    /**
     * UK-API連携を行い、JSON形式のレスポンスデータを取得する
     *
     * @param requestDto リクエストDTO
     * @return レスポンスデータ(json形式)
     */
    protected String connect(Map<String, Object> requestDto) {

        // 接続情報定義
        HttpURLConnection urlConn = null;
        BufferedReader br = null;

        // クエリストリングを作成する
        final String query = buildParamString(requestDto, charset);

        if (StringUtils.isEmpty(query)) {
            log.error(createLogMessage("リクエストデータが設定されていません。"));
            return null;
        }

        // 接続先URL
        String urlString = getUrl();

        if (isOutputLog) {
            // リクエストデータをログ出力
            log.info(createLogMessage("接続先URL：" + urlString));
            log.info(createLogMessage("リクエストデータ：" + query));
        }
        // 接続処理
        try {

            URL url = null;
            if (query == null) {
                url = new URL(urlString);
            } else {
                url = new URL(urlString + "?" + query);
            }
            urlConn = (HttpURLConnection) url.openConnection();
            // SSLの場合
            if (urlConn instanceof HttpsURLConnection) {
                urlConn = setSSLConnection((HttpsURLConnection) urlConn);
            }

            // 指定URL接続設定
            urlConn.setRequestMethod("GET");
            urlConn.setDoOutput(true);
            urlConn.setInstanceFollowRedirects(false);
            urlConn.setRequestProperty("Accept-Language", "ja");
            urlConn.setRequestProperty("Content-Type", "application/json; charset=" + charset);
            urlConn.setReadTimeout(readTimeout);
            urlConn.setConnectTimeout(connectTimeout);

            // 通信結果が成功[200]の場合
            int responseCode = urlConn.getResponseCode();
            if (responseCode == HTTP_STATUS_SUCCESS) {
                // 通信結果取得
                br = new BufferedReader((new InputStreamReader(urlConn.getInputStream(), charset)));

                String bufStr = IOUtils.toString(br);

                if (isOutputLog) {
                    // リクエストデータをログ出力
                    log.info(createLogMessage("レスポンスデータ：" + bufStr));
                }

                return bufStr;
            }
            // 通信結果が失敗([200]以外)の場合
            else {
                log.error(createLogMessage("通信失敗(HTTPステータスコード)：" + responseCode));

                return null;
            }

        } catch (SocketTimeoutException e) {
            log.error(createLogMessage("UK-API通信でタイムアウトが発生しました。：" + e.getMessage()), e);
            return null;
        } catch (IOException e) {
            log.error(createLogMessage("UK-APIへの接続に失敗しました。：" + e.getMessage()), e);
            return null;
        } catch (Exception e) {
            log.error(createLogMessage("システムエラーが発生しました。：" + e.getMessage()), e);
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }

            if (urlConn != null) {
                try {
                    urlConn.disconnect();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * パラメータの追加<br/>
     *
     * @param name パラメータ
     * @param val 設定値（String）
     * @param map 要請パラメータ クエリストリングMAP
     */
    protected void addParam(String name, String val, Map<String, Object> map) {
        if (val != null) {
            map.put(name, val);
        }
    }

    /**
     * 値マップから GET / 通常POST で使用する値文字列を生成する。
     *
     * @param valueMap 値マップ
     * @param charset URLエンコード処理に使用する文字コード
     * @return URL に付加するクエリストリング
     */
    public String buildParamString(final Map<String, ?> valueMap, final String charset) {

        if (valueMap == null || valueMap.isEmpty()) {
            return null;
        }

        final StringBuilder query = new StringBuilder();

        for (final String key : valueMap.keySet()) {

            final Object value = valueMap.get(key);

            if (value instanceof Object[]) {
                for (Object part : (Object[]) value) {
                    append(query, key, part, charset);
                }
            } else if (value instanceof List) {
                for (Object part : (List<?>) value) {
                    append(query, key, part, charset);
                }
            } else if (value instanceof Set) {
                for (Object part : (Set<?>) value) {
                    append(query, key, part, charset);
                }
            } else {
                append(query, key, value, charset);
            }

        }

        return query.toString();
    }

    /**
     * 値文字列に文字列を追加する
     *
     * @param query 対象の値文字列
     * @param key キー
     * @param value 値
     * @param charset 文字セット
     */
    public void append(final StringBuilder query, final String key, final Object value, final String charset) {
        if (query.length() != 0) {
            query.append("&");
        }

        try {
            String valueString = "";
            if (value != null) {
                valueString = value.toString();
            }
            query.append(URLEncoder.encode(key, charset) + "=" + URLEncoder.encode(valueString, charset));
        } catch (final UnsupportedEncodingException e) {
            // No exception will be caught here.
        }
    }

    /**
     * ログ出力用メッセージを作成する
     * <pre>
     * 先頭にUK-API連携名称＋「/」を付与して返却する
     * </pre>
     *
     * @param message メッセージ
     * @return ログ出力用メッセージ
     */
    protected String createLogMessage(String message) {

        // UK-API連携名称を付与したメッセージを返却
        return getUkApiName() + "/" + message;
    }

    /**
     * SSL通信設定<br/>
     *
     * @param connection 送信データ
     * @return 送信データ（SSL設定）
     * @throws Exception 通信エラー
     */
    protected HttpsURLConnection setSSLConnection(HttpsURLConnection connection) throws Exception {
        if (connection instanceof HttpsURLConnection) {
            // SSLサーバー認証／クライアント認証を実施しない
            TrustManager[] tm = {new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, tm, new SecureRandom());
            ((HttpsURLConnection) connection).setSSLSocketFactory(sslContext.getSocketFactory());
            // ホスト名の検証を実施しない
            connection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        }

        return connection;
    }

    /**
     * ロガークラスを取得する
     *
     * @return ロガークラス
     */
    protected abstract Logger getLogger();

    /**
     * UK-API連携名称を取得する
     *
     * @return UK-API連携名称
     */
    protected abstract String getUkApiName();

    /**
     * 接続先URLを取得する
     *
     * @return 接続先URL
     */
    protected abstract String getUrl();

    /**
     * レスポンスデータを整形します。
     *
     * @param res レスポンスjsonデータ
     * @return UK-API連携取得結果クラス
     */
    protected abstract AbstractUkApiResponseDto createRes(String res);
}
