// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.logic.webapi;

import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseDto;
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
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

/**
 * PDR#015 12_WebAPI<br/>
 *
 * <pre>
 * WEB-API連携基底クラス
 * </pre>
 */
@Component
public abstract class AbstractWebApiLogic extends AbstractShopLogic {

    /**
     * JSONユーティリティ
     */
    protected final JsonUtility jsonUtility;
    /**
     * ロガー
     */
    protected Logger log;
    /**
     * 文字コード
     */
    protected String charset;
    /**
     * 接続タイムアウト時間(ミリ秒)
     */
    private int connectTimeout;
    /**
     * 読み取りタイムアウト時間(ミリ秒)
     */
    private int readTimeout;
    /**
     * リクエスト・レスポンスデータログ出力有無
     */
    private boolean isOutputLog;

    /**
     * WEB-API連携処理でエラーが発生した場合<br/>
     */
    public static final String MSG_ERR_CODE = "PDR-0015-001-A-";

    // 2023-renew No24 from here
    /**
     * WEB-API連携処理でエラーが発生した場合：取得結果をそのまま返したいケース<br/>
     */
    public static final String MSG_ERR_CODE_RETURN_MESSAGE = "PDR-2023RENEW-24-001-";
    // 2023-renew No24 to here

    /**
     * フラグ値：ON
     */
    protected static final String FLG_ON = "1";

    /**
     * HTTPステータス：200(正常)
     */
    protected static final int HTTP_STATUS_SUCCESS = 200;

    /**
     * WEB-API ステータス：0(正常)
     */
    public static final String WEB_API_STATUS_SUCCESS = "0";

    // 2023-renew No24 from here
    /**
     * WEB-API ステータス：99(WEB-API内でのエラー)
     */
    public static final String WEB_API_STATUS_ERROR = "99";
    // 2023-renew No24 to here

    /**
     * コンストラクタ
     */
    @Autowired
    public AbstractWebApiLogic(JsonUtility jsonUtility) {
        // ロガー
        log = getLogger();
        // 文字コード
        charset = PropertiesUtil.getSystemPropertiesValue("webapi.charset");
        // 接続タイムアウト時間(ミリ秒)
        connectTimeout = Integer.parseInt(PropertiesUtil.getSystemPropertiesValue("webapi.connect.timeout"));
        // 読み取りタイムアウト時間(ミリ秒)
        readTimeout = Integer.parseInt(PropertiesUtil.getSystemPropertiesValue("webapi.read.timeout"));
        // リクエスト・レスポンスデータログ出力有無
        isOutputLog = FLG_ON.equals(PropertiesUtil.getSystemPropertiesValue("webapi.log.output.flg"));

        this.jsonUtility = jsonUtility;

    }

    /**
     * WEB-APIを実行します。
     *
     * @param requestDto リクエストDTO
     * @return レスポンスDTO
     */
    protected AbstractWebApiResponseDto execute(AbstractWebApiRequestDto requestDto) {

        // WEB-API呼出し処理
        String res = connect(requestDto);

        // 処理結果が空の場合
        if (StringUtils.isEmpty(res)) {
            log.error(createLogMessage("WEB-APIの接続に失敗しました。"));
            // エラーページへ遷移
            throwMessage(MSG_ERR_CODE);
        }

        // レスポンスjsonデータを整形
        AbstractWebApiResponseDto dto = createRes(res);

        // JSON 変換エラー
        if (dto == null) {
            log.error(createLogMessage("JSONへの変換に失敗しました。"));
            // エラーページへ遷移
            throwMessage(MSG_ERR_CODE);
        }

        // WEB-API 処理結果 エラー
        if (!WEB_API_STATUS_SUCCESS.equals(dto.getResult().getStatus())) {
            log.error(createLogMessage(
                            "処理結果ステータス：" + dto.getResult().getStatus() + " メッセージ:" + dto.getResult().getMessage()));
            // エラーページへ遷移
            throwMessage(MSG_ERR_CODE);
        }

        // Dtoを返却
        return dto;

    }

    /**
     * WEB-API連携を行い、JSON形式のレスポンスデータを取得する
     *
     * @param requestDto リクエストDTO
     * @return レスポンスデータ(json形式)
     */
    protected String connect(AbstractWebApiRequestDto requestDto) {

        // 接続情報定義
        HttpURLConnection urlConn = null;
        PrintStream prSt = null;
        BufferedReader br = null;

        String jsonStr = jsonUtility.encode(requestDto);

        if (StringUtils.isEmpty(jsonStr)) {
            log.error(createLogMessage("リクエストデータが設定されていません。"));
            return null;
        }

        // 接続先URL
        String urlString = getUrl();
        if (isOutputLog) {
            // リクエストデータをログ出力
            log.info(createLogMessage("接続先URL：" + urlString));
            log.info(createLogMessage("リクエストデータ：" + jsonStr));
        }
        // 接続処理
        try {

            URL url = new URL(urlString);
            urlConn = (HttpURLConnection) url.openConnection();
            // SSLの場合
            if (urlConn instanceof HttpsURLConnection) {
                urlConn = setSSLConnection((HttpsURLConnection) urlConn);
            }

            // 指定URL接続設定
            urlConn.setRequestMethod("POST");
            urlConn.setDoOutput(true);
            urlConn.setInstanceFollowRedirects(false);
            urlConn.setRequestProperty("Accept-Language", "ja");
            urlConn.setRequestProperty("Content-Type", "application/json;charset=" + charset);
            urlConn.setReadTimeout(readTimeout);
            urlConn.setConnectTimeout(connectTimeout);

            prSt = new PrintStream(urlConn.getOutputStream());

            prSt.print(jsonStr);

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
            log.error(createLogMessage("WEB-API通信でタイムアウトが発生しました。：" + e.getMessage()), e);
            return null;
        } catch (IOException e) {
            log.error(createLogMessage("WEB-APIへの接続に失敗しました。：" + e.getMessage()), e);
            return null;
        } catch (Exception e) {
            log.error(createLogMessage("システムエラーが発生しました。：" + e.getMessage()), e);
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    log.error("例外処理が発生しました", e);
                }
            }
            if (prSt != null) {
                try {
                    prSt.close();
                } catch (Exception e) {
                    log.error("例外処理が発生しました", e);
                }
            }

            if (urlConn != null) {
                try {
                    urlConn.disconnect();
                } catch (Exception e) {
                    log.error("例外処理が発生しました", e);
                }
            }
        }
    }

    /**
     * ログ出力用メッセージを作成する
     * <pre>
     * 先頭にWEB-API連携名称＋「/」を付与して返却する
     * </pre>
     *
     * @param message メッセージ
     * @return ログ出力用メッセージ
     */
    protected String createLogMessage(String message) {

        // WEB-API連携名称を付与したメッセージを返却
        return getWebApiName() + "/" + message;
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
     * WEB-API連携名称を取得する
     *
     * @return WEB-API連携名称
     */
    protected abstract String getWebApiName();

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
     * @return WEB-API連携取得結果クラス
     */
    protected abstract AbstractWebApiResponseDto createRes(String res);

    /**
     * List<String>からパイプ区切りの文字列を作成します。
     *
     * @param list リスト
     * @return パイプ区切りの文字列
     */
    public static String createStrPipeByList(List<String> list) {
        StringBuilder result = new StringBuilder();
        for (String str : list) {
            if (result.length() > 0) {
                result.append("|");
            }
            result.append(str);
        }

        return result.toString();
    }

    /**
     * パイプ区切りの文字列からList<String>を作成します。
     *
     * @param strPipe パイプ区切りの文字列
     * @return リスト
     */
    public static List<String> creatListByStrPipe(String strPipe) {

        if (StringUtils.isEmpty(strPipe)) {
            return null;
        }

        String[] array = strPipe.split("\\|");

        if (array == null || array.length == 0) {
            return null;
        }

        return Arrays.asList(array);
    }
}
// PDR Migrate Customization to here
