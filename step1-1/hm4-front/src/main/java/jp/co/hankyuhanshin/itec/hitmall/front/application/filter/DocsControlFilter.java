// 2023-renew No22 from here
package jp.co.hankyuhanshin.itec.hitmall.front.application.filter;

import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl.HmAdminUserDetails;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl.HmFrontUserDetails;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextImpl;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * アップロードファイルを表示するフィルタ
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public class DocsControlFilter implements Filter {

    /**
     * ログインURI
     */
    private static final String LOGIN_URI = "login.uri";
    /**
     * 一時的なパスの URI
     */
    private static final String TMP_URI_CONF_DOCUMENT = "tmp.uri.conf.document";
    /**
     * 実際のパス URI
     */
    private static final String REAL_URI_CONF_DOCUMENT = "real.uri.conf.document";

    /**
     * 春のセキュリティコンテキスト
     */
    private static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

    /**
     * Docsフィルター
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
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();

        // セッションを取得する
        HttpSession session = request.getSession(false);
        if (session == null) {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
        }

        // URIを確認してください
        String tmpUriConfDocument = PropertiesUtil.getSystemPropertiesValue(TMP_URI_CONF_DOCUMENT);
        String realUriConfDocument = PropertiesUtil.getSystemPropertiesValue(REAL_URI_CONF_DOCUMENT);

        // 登録画面のファイル
        if (uri.contains(tmpUriConfDocument)) {
            String sessionId = session.getId() == null ? "" : session.getId();
            if (StringUtils.isEmpty(sessionId)) {
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
            }
            doFilter(request, servletResponse, filterChain, uri, tmpUriConfDocument, sessionId);
            return;
        }

        // 情報確認画面のファイル
        if (uri.contains(realUriConfDocument)) {
            SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute(SPRING_SECURITY_CONTEXT);
            // ログインです
            if (securityContext != null) {
                String memberInfoSeq = null;
                if (securityContext.getAuthentication().getPrincipal() instanceof HmFrontUserDetails) {
                    memberInfoSeq = ((HmFrontUserDetails) securityContext.getAuthentication()
                                                                         .getPrincipal()).getMemberInfoEntity()
                                                                                         .getMemberInfoSeq()
                                                                                         .toString();
                } else if (securityContext.getAuthentication().getPrincipal() instanceof HmAdminUserDetails) {
                    memberInfoSeq = ((HmAdminUserDetails) securityContext.getAuthentication()
                                                                         .getPrincipal()).getMemberInfoEntity()
                                                                                         .getMemberInfoSeq()
                                                                                         .toString();
                }

                if (memberInfoSeq == null) {
                    ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
                } else {
                    doFilter(request, servletResponse, filterChain, uri, realUriConfDocument, memberInfoSeq);
                    return;
                }
            } else {
                // まだログインしていません
                ((HttpServletResponse) servletResponse).sendRedirect(
                                PropertiesUtil.getSystemPropertiesValue(LOGIN_URI));
                return;
            }
        }
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
     * 表示ファイルへのパスを置き換えます
     */
    private void doFilter(HttpServletRequest request,
                          ServletResponse servletResponse,
                          FilterChain filterChain,
                          String uri,
                          String path,
                          String key) {
        try {
            filterChain.doFilter(new HttpServletRequestWrapper(request) {
                @Override
                public String getRequestURI() {
                    return uri.replace(path, path + "/" + key + "/");
                }
            }, servletResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
// 2023-renew No22 to here
