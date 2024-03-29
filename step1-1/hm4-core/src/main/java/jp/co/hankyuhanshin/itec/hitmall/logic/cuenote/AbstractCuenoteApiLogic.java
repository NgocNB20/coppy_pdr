/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.cuenote;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;

import jp.co.hankyuhanshin.itec.hmbase.util.seasar.Base64Util;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationLogUtility;
import net.arnx.jsonic.JSON;



import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.commons.httpclient.HttpURL;
import org.apache.commons.httpclient.HttpsURL;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Cuenote API<br/>
 * Cuenote APIの基底クラス
 *
 * @author st75001
 *
 */
@Component
public abstract class AbstractCuenoteApiLogic extends AbstractShopLogic {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCuenoteApiLogic.class);

    /** Cuenote API 認証ユーザ */
    protected String authUser = PropertiesUtil.getSystemPropertiesValue("cuenote.api.auth.user");

    /** Cuenote API 認証パスワード */
    protected String authPass = PropertiesUtil.getSystemPropertiesValue("cuenote.api.auth.pass");

    /** Cuenote API 接続URL */
    protected String urlCuenoteApi = PropertiesUtil.getSystemPropertiesValue("cuenote.api.url");

    /** Cuenote API メール文書セット複製パス */
    protected String pathMailSet = PropertiesUtil.getSystemPropertiesValue("cuenote.api.path.mailSet");

    /** Cuenote API 配信情報予約パス */
    protected String pathDeliveryReserve = PropertiesUtil.getSystemPropertiesValue("cuenote.api.path.deliveryReserve");

    /** Cuenote API 配信情報取得パス */
    protected String pathGetDelivery = PropertiesUtil.getSystemPropertiesValue("cuenote.api.path.getDelivery");

    /** コネクションタイムアウト(秒) */
    protected int connectionTimeout = Integer.parseInt(PropertiesUtil.getSystemPropertiesValue("cuenote.api.connection.timeout"));

    /** ソケットタイムアウト(秒) */
    protected int socketTimeout = Integer.parseInt(PropertiesUtil.getSystemPropertiesValue("cuenote.api.read.timeout"));

    /** リトライ回数 */
    protected int retryCnt = Integer.parseInt(PropertiesUtil.getSystemPropertiesValue("cuenote.api.connect.retry.max"));

    /** HTTPS */
    public static final String HTTPS = "https";

    /** HTTPステータスコード */
    protected int httpStatusCode;

    /** HTTPレスポンスヘッダ */
    protected Header[] headers;

    /** HTTPリクエストヘッダ_X-mode */
    protected String X_MODE = "X-Mode";

    /** HTTPリクエストヘッダ_host */
    public static final String HEADER_HOST = "shop.pdr.co.jp";

    /**
     * 接続URLを返却
     * @return 「HTTPステータスコード 」
     */
    protected abstract String getUri();

    /**
     * 成功時HTTPステータスコード を返却
     * @return 「HTTPステータスコード 」
     */
    protected abstract int getSuccessHttpStatusCode();

    /**
     * ログ出力用のプレフィックスを返します。
     * @return 「API名/APIを利用する機能/」
     */
    protected abstract String getLogPrefix();

    /**
     * ポストデータ作成
     *
     * @return ポストデータ
     */
    protected String createPostData() {
        return null;
    }

    /**
     * API実行
     *
     * @param uri uri
     * @param <T> レスポンス情報格納Dtoクラス
     * @param responseDtoClass レスポンス情報格納Dtoクラス
     * @param xMode POSTリクエスト（X-Mode）
     * @throws Exception 例外
     * @return レスポンス情報格納Dto
     */
    public <T> T execute(String uri, Class<T> responseDtoClass, String xMode) throws Exception {

        // APIレスポンス結果
        T responseDto;

        try {

            // API実行
            responseDto = callApi(uri, responseDtoClass, xMode);
            LOGGER.info("ステータスコード：" + httpStatusCode);
            if (getSuccessHttpStatusCode() == httpStatusCode) {
                LOGGER.info(getLogPrefix() + "正常終了");
                return responseDto;
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(getLogPrefix() + "想定外エラー", e.fillInStackTrace());
            throw new Exception(getLogPrefix() + "想定外エラー", e);

        }

        LOGGER.error(getLogPrefix() + "異常終了");
        return null;
    }

    /**
     * Authorizationリクエストヘッダを返却する<br/>
     *
     * @return Authorizationリクエストヘッダ
     */
    protected String getHeaderAuthorization() {

        // 認証ユーザ、認証パスワードのいずれかが未設定の場合
        if (StringUtils.isEmpty(authUser) || StringUtils.isEmpty(authPass)) {
            return null;
        }

        return "Basic " + Base64Util.encode((authUser + ":" + authPass).getBytes());
    }

    /**
     * Acceptリクエストヘッダを返却する<br/>
     *
     * @return Acceptリクエストヘッダ
     */
    protected String getHeaderAceept() {
        return "application/json";
    }

    /**
     * Content-Typeを返却する<br/>
     *
     * @return Content-Type
     */
    protected String getContentType() {
        return "application/json; charset=UTF-8";
    }

    /**
     * API呼び出し
     *
     * @param <T> レスポンス情報格納Dto
     * @param uri uri
     * @param responseDtoClass レスポンス情報格納Dtoクラス
     * @param xMode POSTリクエスト（X-Mode）
     * @return dtoClass レスポンスDtoクラス
     * @throws Exception 例外
     */
    protected <T> T callApi(String uri, Class<T> responseDtoClass, String xMode) throws Exception {

        String responseBody;

        // POSTデータ作成
        String postData = createPostData();

        // POSTデータがNULLの場合、GETリクエスト、NULL以外の場合POSTリクエスト
        if (postData == null) {
            // GETリクエスト
            responseBody = httpGet(uri, null, getHeaderAuthorization(), getContentType(), StandardCharsets.UTF_8);
        } else {
            // POSTリクエスト
            responseBody = httpPost(uri, getHeaderAceept(), getHeaderAuthorization(), postData, getContentType(), StandardCharsets.UTF_8, xMode);
        }

        return convertJsonDto(responseBody, responseDtoClass);

    }

    /**
     * JSONからレスポンスDtoクラスに変換
     *
     * @param <T> JSON情報格納クラス
     * @param responseBody レスポンスボディ
     * @param responseDtoClass レスポンス情報格納Dtoクラス
     * @return dtoClass レスポンスDtoクラス
     * @throws Exception 例外
     */
    protected <T> T convertJsonDto(String responseBody, Class<T> responseDtoClass) throws Exception {

        if (StringUtils.isEmpty(responseBody)) {
            return null;
        }

        return JSON.decode(new StringReader(responseBody), responseDtoClass);
    }

    /**
     * HTTP GETリクエストを実行し、レスポンスボディを返却する
     *
     * @param uri uri
     * @param headerAccept Acceptリクエストヘッダ
     * @param headerAuthorization Authorizationリクエストヘッダ
     * @param conentType ConentType
     * @param charset レスポンス文字コード
     * @return String レスポンスボディ
     * @throws Exception 例外
     */
    protected String httpGet(String uri, String headerAccept, String headerAuthorization, String conentType, Charset charset) throws Exception {

        String responseBody = null;
        // 接続マネージャーを作成
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        for (int i = 0; i <= retryCnt; i++) {

            CloseableHttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connectionManager).build();

            try {

                HttpGet getRequest = new HttpGet(uri);

                // Acceptリクエストヘッダ
                if (StringUtils.isNotEmpty(headerAccept)) {
                    getRequest.setHeader(HttpHeaders.ACCEPT, headerAccept);
                }

                // Authorizationリクエストヘッダ
                if (StringUtils.isNotEmpty(headerAuthorization)) {
                    getRequest.setHeader(HttpHeaders.AUTHORIZATION, headerAuthorization);
                }

                // Conent-Type
                if (StringUtils.isNotEmpty(conentType)) {
                    getRequest.setHeader(HttpHeaders.CONTENT_TYPE, conentType);
                }

                // 接続
                LOGGER.info(getLogPrefix() + "リクエスト(URI):" + getRequest.getURI());

                // 処理開始時間
                final long accepted = System.currentTimeMillis();
                HttpResponse response = httpClient.execute(getRequest);
                // 処理にかかった時間
                final long elapsed = System.currentTimeMillis() - accepted;

                // アプリケーションログに出力
                output(uri, elapsed);

                httpStatusCode = response.getStatusLine().getStatusCode();

                if (getSuccessHttpStatusCode() == httpStatusCode) {
                    // レスポンスヘッダ取得
                    headers = response.getAllHeaders();
                    // レスポンスボディ取得
                    InputStream iStream = response.getEntity().getContent();
                    responseBody = convertInputStreamToString(iStream, charset);
                    LOGGER.info(getLogPrefix() + "レスポンス：" + responseBody);
                }else{
                    InputStream iStream = response.getEntity().getContent();
                    LOGGER.info(getLogPrefix() + "エラーレスポンス：" + convertInputStreamToString(iStream, charset));
                }

                break;

            } catch (SocketTimeoutException e) {
                LOGGER.error(getLogPrefix() + "タイムアウト発生(SocketTimeoutException)", e);

            } catch (ConnectTimeoutException e) {
                LOGGER.error(getLogPrefix() + "タイムアウト発生(ConnectTimeoutException)", e);

            } finally {
                // 接続終了
                connectionManager.shutdown();
            }

        }

        return responseBody;

    }

    /**
     * HTTP POSTリクエストを実行し、レスポンスボディを返却する
     *
     * @param uri uri
     * @param headerAccept Acceptリクエストヘッダ
     * @param headerAuthorization Authorizationリクエストヘッダ
     * @param postData ポストデータ
     * @param entityConentType Conent-Type
     * @param charset レスポンス文字コード
     * @return String レスポンスボディ
     * @throws Exception 例外
     */
    protected String httpPost(String uri, String headerAccept, String headerAuthorization, String postData, String entityConentType, Charset charset, String headerXMode) throws Exception {

        String responseBody = null;
        // 接続マネージャーを作成
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        for (int i = 0; i <= retryCnt; i++) {
            CloseableHttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connectionManager).build();

            try {

                HttpPost postRequest = new HttpPost(uri);

                // Acceptリクエストヘッダ
                if (StringUtils.isNotEmpty(headerAccept)) {
                    postRequest.setHeader(HttpHeaders.ACCEPT, headerAccept);
                }

                // Authorizationリクエストヘッダ
                if (StringUtils.isNotEmpty(headerAuthorization)) {
                    postRequest.setHeader(HttpHeaders.AUTHORIZATION, headerAuthorization);
                }

                // Conent-Type
                StringEntity entity = new StringEntity(postData, charset.toString());
                if (entityConentType != null) {
                    postRequest.setHeader(HttpHeaders.CONTENT_TYPE, entityConentType);
                    entity.setContentType(entityConentType);
                }

                // Hostリクエストヘッダ
                postRequest.setHeader(HttpHeaders.HOST, HEADER_HOST);

                // X-Modeリクエストヘッダ
                if (StringUtils.isNotEmpty(headerXMode)) {
                    postRequest.setHeader(X_MODE, headerXMode);
                }

                postRequest.setEntity(entity);

                // 接続
                LOGGER.info(getLogPrefix() + "リクエスト(URI):" + postRequest.getURI());
                LOGGER.info(getLogPrefix() + "リクエスト(パラメータ):" + EntityUtils.toString(postRequest.getEntity()));

                // 処理開始時間
                final long accepted = System.currentTimeMillis();

                HttpResponse response = httpClient.execute(postRequest);

                // 処理にかかった時間
                final long elapsed = System.currentTimeMillis() - accepted;

                // アプリケーションログに出力
                output(uri, elapsed);

                httpStatusCode = response.getStatusLine().getStatusCode();
                // レスポンスヘッダ取得
                headers = response.getAllHeaders();

                LOGGER.info(getLogPrefix() + "レスポンスコード：" + httpStatusCode);
                LOGGER.info(getLogPrefix() + "レスポンスヘッダ：" + headers);

                if (getSuccessHttpStatusCode() == httpStatusCode) {
                    // レスポンスボディ取得
                    InputStream iStream = response.getEntity().getContent();
                    responseBody = convertInputStreamToString(iStream, charset);
                    LOGGER.info(getLogPrefix() + "レスポンス：" + responseBody);
                }else{
                    InputStream iStream = response.getEntity().getContent();
                    LOGGER.info(getLogPrefix() + "エラーレスポンス：" + convertInputStreamToString(iStream, charset));
                }

                break;
            } catch (SocketTimeoutException e) {
                LOGGER.error(getLogPrefix() + "タイムアウト発生(SocketTimeoutException)", e);

            } catch (ConnectTimeoutException e) {
                LOGGER.error(getLogPrefix() + "タイムアウト発生(ConnectTimeoutException)", e);

            } finally {
                // 接続終了
                connectionManager.shutdown();
            }
        }

        return responseBody;

    }

    /**
     * HTTP接続パラメータ作成(タイムアウト設定)<br/>
     *
     * @param uri uri
     * @return HTTP接続パラメータ
     */
    protected CloseableHttpClient getHttpClient(URI uri) {

        boolean isHttps = uri.toASCIIString().startsWith(HTTPS);

        CloseableHttpClient httpClient;

        // HTTPSのときのみ自己証明書対応を行う
        if (isHttps) {
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("TLSv1.2");
            } catch (NoSuchAlgorithmException e1) {
                LOGGER.error(e1.getMessage());
            }

            try {
                sslContext.init(null, new TrustManager[] { new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        LOGGER.debug("getAcceptedIssuers =============");
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        LOGGER.debug("checkClientTrusted =============");
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        LOGGER.debug("checkServerTrusted =============");
                    }
                } }, new SecureRandom());

            } catch (KeyManagementException e1) {
                LOGGER.error(e1.getMessage());
            }

            try {
                // SSLContextを作成
                sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, null, new SecureRandom());

                // SSL接続用のSocketFactoryを作成
                SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
                        sslContext,
                        NoopHostnameVerifier.INSTANCE
                );

                // カスタムのリクエスト設定を作成
                RequestConfig requestConfig = RequestConfig.custom()
                        .build();

                // HttpClientBuilderを使用してHttpClientを構築
                httpClient = HttpClients.custom()
                        .setSSLSocketFactory(sslSocketFactory)
                        .setDefaultRequestConfig(requestConfig)
                        .build();
            } catch (NoSuchAlgorithmException | KeyManagementException e1) {
                LOGGER.error(e1.getMessage());
            }

            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,NoopHostnameVerifier.INSTANCE);

            // スキームのレジストリを作成して登録
            RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
            registryBuilder.register("https", sslSocketFactory);

            // ConnectionSocketFactoryを登録
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", sslSocketFactory)
                            .build()
            );

            // HttpClientを構築
            httpClient = HttpClientBuilder.create().setConnectionManager(connectionManager).build();
        } else {
            // HTTPクライアント
            httpClient = HttpClients.custom().build();
        }

        // タイムアウト値設定、オートリダイレクト禁止
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectionTimeout)
                .setSocketTimeout(socketTimeout).setRedirectsEnabled(false).build();

        httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();

        return httpClient;
    }

    /**
     * ストリームを文字列に変換
     *
     * @param iStream 変換対象
     * @param charset 文字コード
     * @return 文字列
     * @throws IOException 変換失敗
     */
    protected String convertInputStreamToString(InputStream iStream, Charset charset) throws IOException {

        // レスポンスの読み込み
        BufferedReader reader = new BufferedReader(new InputStreamReader(iStream, charset));

        String line;
        StringBuilder sb = new StringBuilder();
        // レスポンス結果を文字列追加
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

    /**
     *
     * ポートを返却する<br/>
     *
     * @param uri uri
     * @param isHttps httpsかどうか？ httpsの場合true
     * @return port番号
     */
    protected int getPort(URI uri, boolean isHttps) {
        int port;
        if (uri.getPort() < 0) {
            try {
                port = Integer.parseInt(uri.getRawAuthority().replaceAll(".*:([0-9]+)?", "$1"));
            } catch (Exception e) {
                port = 0;
            }
            if (port == 0) {
                if (isHttps) {
                    port = HttpsURL.DEFAULT_PORT;
                } else {
                    port = HttpURL.DEFAULT_PORT;
                }
            }
        } else {
            port = uri.getPort();
        }
        return port;
    }

    /**
     * アプリケーションログ出力
     * @param uri uri
     * @param elapsed 処理にかかった時間
     */
    protected void output(String uri, final long elapsed) {

        // 数値フォーマット
        DecimalFormat decimalFormat = new DecimalFormat("000.000");

        // メッセージ作成
        StringBuilder sb = new StringBuilder();
        sb.append("CuenoteSmsAPI");
        if (elapsed >= 5000) {
            sb.append("5秒以上 ");
        } else if (elapsed >= 3000) {
            sb.append("3秒以上 ");
        } else if (elapsed >= 2000) {
            sb.append("2秒以上 ");
        } else if (elapsed >= 1000) {
            sb.append("1秒以上 ");
        } else {
            sb.append("1秒未満 ");
        }
        sb.append("処理速度：");
        sb.append(decimalFormat.format((elapsed / 1000d)));
        sb.append("sec");

        // ログ出力
        ApplicationLogUtility applicationLogUtility = ApplicationContextUtility.getBean(ApplicationLogUtility.class);
        applicationLogUtility.writeFreeLog("TRACE_LOG", uri, sb.toString(), null);
    }

    /**
     *
     * レスポンスヘッダを返却する<br/>
     *
     * @param key key
     * @return レスポンスヘッダ
     */
    protected String getResponseHeader(String key) {

        if (headers == null || StringUtils.isEmpty(key)) {
            return null;
        }

        for (Header header : headers) {
            if (header.getName().equalsIgnoreCase(key)) {
                return header.getValue();
            }
        }

        return null;

    }
}
