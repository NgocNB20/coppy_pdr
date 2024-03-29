/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.application.filter;

import jp.co.hankyuhanshin.itec.hitmall.api.shop.AccessApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.AccessRegistRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.CampaignEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.param.CampaignGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.user.UsersApi;
import jp.co.hankyuhanshin.itec.hitmall.api.user.param.AccessUidResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfoBase;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl.CommonInfoBaseImpl;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAccessType;
import jp.co.hankyuhanshin.itec.hitmall.front.helper.crypto.CryptoHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 共通処理Filter<br/>
 * フロントではSpringSecurityのFilter前に起動<br/>
 * バックではSpringSecurityのFilter後に起動
 *
 * @author natume
 * @author Kaneko (itec) 2012/08/08 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
public class CommonProcessFilter implements Filter {

    /**
     * 共通処理
     * <ul>
     *  <li>HotDeploy対応</li>
     *  <li>共通処理サービスの実行</li>
     * </ul>
     *
     * @param servletRequest  servletRequest リクエスト
     * @param servletResponse servletResponse レスポンス
     * @param filterChain     filterChain フィルター
     * @throws IOException      IOException 例外
     * @throws ServletException ServletException 例外
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
                    throws IOException, ServletException {

        // CommonInfoを取得
        // ※初回アクセス時は、このタイミングで取得と同時に、Session登録を行う
        CommonInfo commonInfo = ApplicationContextUtility.getBean(CommonInfo.class);

        if (registCampaign((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, commonInfo)) {
            return;
        }

        // フロントの場合のみ、店舗公開チェック
        CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
        if (commonInfoUtility.isSiteFront(commonInfo)) {
            // 店舗情報が非公開の場合は、メンテナンス画面へ遷移
            if (commonInfo.getCommonInfoShop().isCloseFlag()) {
                try {
                    ((HttpServletResponse) servletResponse).sendRedirect(
                                    PropertiesUtil.getSystemPropertiesValue("maintenance.url"));
                    return;

                } catch (IOException ie) {
                    throw new RuntimeException(ie);
                }
            }
        }

        // 次の処理
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 初期化処理
     *
     * @param arg0 設定
     * @throws ServletException 発生した例外
     */
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // 特になし
    }

    /**
     * 破棄処理
     */
    @Override
    public void destroy() {
        // 特になし
    }

    /**
     * キャンペーンコードの登録<br/>
     *
     * @param request    リクエスト
     * @param response   レスポンス
     * @param commonInfo 共通情報
     * @return 処理続行フラグ
     * @throws IOException 例外
     */
    private boolean registCampaign(HttpServletRequest request, HttpServletResponse response, CommonInfo commonInfo)
                    throws IOException {

        // キャンペーンURL取得
        String campaignUrl = getCampaignUrl();

        /* キャンペーン処理 */
        // キャンペーンURIに変換し、対象のキャンペーンURLでない場合終了
        String campaignUri = campaignUrl.replaceAll("https?://.*?/", "/");
        if (request.getRequestURI().indexOf(campaignUri) == -1) {
            return false;
        }

        // キャンペーンパラメータkey取得
        String campaignParamKey = getCampaignParamKey();

        // デフォルトリダイレクトページの取得
        String defaultRedirectUrl = getDefaultRedirectUrl();

        // キャンペーンパラメータKey
        String campaignCode = request.getParameter(campaignParamKey);
        if (campaignCode == null) {
            // 条件付きセッション破棄
            sessionDestroyed(request);

            sendRedirect(response, defaultRedirectUrl);
            return true;
        }

        // リダイレクトURL取得
        String redirectUrl = getRedirectUrl(request, commonInfo, defaultRedirectUrl, campaignCode);

        // 条件付きセッション破棄
        sessionDestroyed(request);

        // キャンペーンデフォルトリダイレクト指定画面へ遷移 ※キャンペーンページは、表示させない
        request.setAttribute("fromCampaign", "true");
        sendRedirect(response, redirectUrl);
        return true;
    }

    /**
     * キャンペーンURL取得<br/>
     *
     * @return キャンペーンURL
     */
    private String getCampaignUrl() {
        String campaignUrl = PropertiesUtil.getSystemPropertiesValue("campaign.url.pc");

        if (StringUtil.isEmpty(campaignUrl)) {
            RuntimeException re =
                            new RuntimeException("The setting is incomplete. system.properties [campaign.url] fail");
            ApplicationLogUtility applicationLogUtility =
                            ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            applicationLogUtility.writeExceptionLog(re);
            throw re;
        }

        return campaignUrl;
    }

    /**
     * キャンペーンパラメータkey取得<br/>
     *
     * @return キャンペーンパラメータkey
     */
    protected String getCampaignParamKey() {
        String campaignParamKey = PropertiesUtil.getSystemPropertiesValue("campaign.param.key");
        if (StringUtil.isEmpty(campaignParamKey)) {
            RuntimeException re = new RuntimeException(
                            "The setting is incomplete. system.properties [campaign.param.key] fail");
            ApplicationLogUtility applicationLogUtility =
                            ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            applicationLogUtility.writeExceptionLog(re);
            throw re;
        }
        return campaignParamKey;
    }

    /**
     * デフォルトリダイレクトURLの取得<br/>
     *
     * @return デフォルトリダイレクトURL
     */
    protected String getDefaultRedirectUrl() {
        String defaultRedirectUrl = PropertiesUtil.getSystemPropertiesValue("campaign.default.redirect.url.pc");

        if (StringUtil.isEmpty(defaultRedirectUrl)) {
            RuntimeException re = new RuntimeException(
                            "The setting is incomplete. system.properties [campaign.default.redirect.url] fail");
            ApplicationLogUtility applicationLogUtility =
                            ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            applicationLogUtility.writeExceptionLog(re);
            throw re;
        }
        return defaultRedirectUrl;
    }

    /**
     * 端末識別番号のセット
     *
     * @param request リクエスト
     * @return 端末識別番号
     */
    protected String getAccessUid(HttpServletRequest request) {

        // クッキーから取得
        String accessUid = null;
        String encryptedAccessUid = null;
        Cookie cookie = getCookie(request, "AUI");
        if (cookie != null) {
            try {
                // 暗号化された端末識別番号
                encryptedAccessUid = URLDecoder.decode(cookie.getValue(), UTF_8);

                // 復号
                CryptoHelper cryptoHelper = ApplicationContextUtility.getBean(CryptoHelper.class);
                accessUid = cryptoHelper.decryptAccessUid(encryptedAccessUid);
            } catch (Exception e) {
                RuntimeException re = new RuntimeException("Cookie decrypt fail accessUid:" + encryptedAccessUid, e);
                ApplicationLogUtility applicationLogUtility =
                                ApplicationContextUtility.getBean(ApplicationLogUtility.class);
                applicationLogUtility.writeExceptionLog(re);
            }
        }

        // ない場合作成
        if (accessUid == null) {
            accessUid = createAccessUid();
        }
        return accessUid;
    }

    /**
     * クッキー取得<br/>
     *
     * @param request    リクエスト
     * @param cookieName クッキー名
     * @return Cookie クッキー情報
     */
    protected Cookie getCookie(HttpServletRequest request, String cookieName) {
        // 現行HM HttpUtilityから移植
        // クッキーから取得
        Cookie[] cookieArray = request.getCookies();
        if (cookieArray != null) {
            for (Cookie cookie : cookieArray) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 端末識別番号の作成<br/>
     *
     * @return 端末識別番号
     */
    protected String createAccessUid() {

        // 端末識別番号作成
        UsersApi usersApi = ApplicationContextUtility.getBean(UsersApi.class);

        AccessUidResponse accessUidResponse = null;
        try {
            accessUidResponse = usersApi.getAccessUid();

        } catch (HttpClientErrorException e) {
            ApplicationLogUtility applicationLogUtility =
                            ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            applicationLogUtility.writeExceptionLog(new RuntimeException("ゲストから会員へのカートマージに失敗しました。", e));
        }

        return accessUidResponse.getAccessUid();
    }

    /**
     * キャンペーンページad.htmlは、訪問件数としてカウントしない<br/>
     * その為、リクエスト時にセッションがない状態で来た場合は、現セッションを破棄し、<br/>
     * キャンペーンのリダイレクト先で訪問件数のカウントとセッションの再作成を行う。<br/>
     * バックトップの訪問数の対応（セッション数）AccessCountFilter
     *
     * @param request リクエスト
     */
    protected void sessionDestroyed(HttpServletRequest request) {

        // リクエスト時のセッション状態を取得し、セッションがなければ破棄する
        Boolean prevSessionState = (Boolean) request.getAttribute("prevSessionState");
        if (prevSessionState != null && !prevSessionState.booleanValue()) {
            request.getSession().invalidate();
        }
    }

    /**
     * リダイレクト指定画面へ遷移<br/>
     *
     * @param response    レスポンス
     * @param redirectUrl リダイレクトURL
     * @throws IOException 例外
     */
    protected void sendRedirect(HttpServletResponse response, String redirectUrl) throws IOException {
        response.sendRedirect(redirectUrl);
    }

    /**
     * URLに端末識別番号をつける<br/>
     *
     * @param url       対象のURL
     * @param accessUid 端末識別番号(クエリ状態)
     * @return 端末識別番号付きのURL
     */
    protected String getAddAccessUid(String url, String accessUid) {

        // 携帯の場合で、端末識別番号が取得できている場合
        String tempUrl = url;
        if (accessUid != null) {
            // URLにつける
            if (url.indexOf("?") == -1) {
                tempUrl = tempUrl + "?" + accessUid;
            } else {
                tempUrl = tempUrl + "&" + accessUid;
            }
        }
        return tempUrl;
    }

    /**
     * リダイレクトURL取得<br/>
     *
     * @param request            リクエスト
     * @param commonInfo         共通情報
     * @param defaultRedirectUrl デフォルトリダイレクトURL
     * @param campaignCode       キャンペーンコード
     * @return リダイレクトURL
     */
    protected String getRedirectUrl(HttpServletRequest request,
                                    CommonInfo commonInfo,
                                    String defaultRedirectUrl,
                                    String campaignCode) {
        String redirectUrl = null;
        // 有効なキャンペーン情報取得
        CampaignEntityResponse campaignEntityResponse = getCampaignEntity(commonInfo, campaignCode);
        if (campaignEntityResponse != null) {
            // リダイレクトURL設定
            redirectUrl = setRedirectUrl(request, commonInfo, campaignCode, campaignEntityResponse);
        }
        if (redirectUrl == null) {
            redirectUrl = defaultRedirectUrl;
        }
        return redirectUrl;
    }

    /**
     * 有効なキャンペーン情報取得<br/>
     *
     * @param commonInfo   共通情報
     * @param campaignCode キャンペーンコード
     * @return キャンペーン情報
     */
    protected CampaignEntityResponse getCampaignEntity(CommonInfo commonInfo, String campaignCode) {
        ShopApi shopApi = ApplicationContextUtility.getBean(ShopApi.class);
        CampaignGetRequest request = new CampaignGetRequest();
        request.setCampaignCode(campaignCode);
        CampaignEntityResponse campaignEntityResponse = null;
        try {
            campaignEntityResponse = shopApi.getCampaign(request);

        } catch (HttpClientErrorException e) {
            ApplicationLogUtility applicationLogUtility =
                            ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            applicationLogUtility.writeExceptionLog(new RuntimeException("ゲストから会員へのカートマージに失敗しました。", e));
        }

        return campaignEntityResponse;
    }

    /**
     * リダイレクトURL設定<br/>
     *
     * @param request                リクエスト
     * @param commonInfo             共通情報
     * @param campaignCode           キャンペーンコード
     * @param campaignEntityResponse キャンペーンエンティティ
     * @return リダイレクトURL
     */
    protected String setRedirectUrl(HttpServletRequest request,
                                    CommonInfo commonInfo,
                                    String campaignCode,
                                    CampaignEntityResponse campaignEntityResponse) {
        String redirectUrl;
        // Sessionを取得
        HttpSession session = request.getSession();
        // Sessionキーを生成
        String key = this.createCampaignCodeSessionKey(commonInfo.getCommonInfoBase().getShopSeq());
        // キャンペーンコードをセット
        ((CommonInfoBaseImpl) commonInfo.getCommonInfoBase()).setCampaignCode(campaignCode);
        // Sessionに保存
        session.setAttribute(key, campaignCode);

        // アクセス情報の登録
        registAccessInfo(commonInfo, campaignCode, session, key);

        // キャンペーン画面へ遷移
        redirectUrl = campaignEntityResponse.getRedirectUrl();
        return redirectUrl;
    }

    /**
     * キャンペーンコード保存用セッションキー作成<br/>
     *
     * @param shopSeq ショップSEQ
     * @return key キャンペーンコード保存用セッションキー
     */
    protected String createCampaignCodeSessionKey(Integer shopSeq) {
        // Sessionキーを生成
        StringBuilder key = new StringBuilder("CAMPAIGN");
        key.append(shopSeq);
        return key.toString();
    }

    /**
     * アクセス情報の登録<br/>
     *
     * @param commonInfo   共通情報
     * @param campaignCode キャンペーンコード
     * @param session      セッション
     * @param key          Sessionキー
     */
    protected void registAccessInfo(CommonInfo commonInfo, String campaignCode, HttpSession session, String key) {
        try {
            CommonInfoBase commonInfoBase = commonInfo.getCommonInfoBase();
            AccessRegistRequest accessRegistRequest = new AccessRegistRequest();
            accessRegistRequest.setAccessType(HTypeAccessType.CAMPAIGN_ACCESS_COUNT.getValue());
            accessRegistRequest.setGoodsGroupSeq(null);
            accessRegistRequest.setAccessCount(1);
            accessRegistRequest.setAccessUid(commonInfoBase.getAccessUid());
            accessRegistRequest.setCampainCode(campaignCode);
            AccessApi accessApi = ApplicationContextUtility.getBean(AccessApi.class);
            try {
                accessApi.registAccess(accessRegistRequest);

            } catch (HttpClientErrorException e) {
                ApplicationLogUtility applicationLogUtility =
                                ApplicationContextUtility.getBean(ApplicationLogUtility.class);
                applicationLogUtility.writeExceptionLog(new RuntimeException("ゲストから会員へのカートマージに失敗しました。", e));
            }
        } catch (Exception e) {
            RuntimeException re = new RuntimeException("campaign regist fail campaignCode:" + campaignCode, e);
            ApplicationLogUtility applicationLogUtility =
                            ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            applicationLogUtility.writeExceptionLog(re);
            // キャンペーンコードを破棄
            session.removeAttribute(key);
        }
    }
}
