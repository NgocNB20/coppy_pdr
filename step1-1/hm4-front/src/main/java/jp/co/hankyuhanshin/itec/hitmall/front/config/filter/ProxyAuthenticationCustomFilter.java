package jp.co.hankyuhanshin.itec.hitmall.front.config.filter;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * フィルター 設定クラス
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public class ProxyAuthenticationCustomFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * obtainUsername SpringSecurity
     *
     * @param request HttpServletRequest
     * @return string
     */
    @Override
    public String obtainUsername(HttpServletRequest request) {

        return request.getParameter("administratorId");
    }

    /**
     * obtainPassword SpringSecurity
     *
     * @param request HttpServletRequest
     * @return string
     */
    @Override
    public String obtainPassword(HttpServletRequest request) {
        String paramValue = request.getParameter("proxyMemberInfoSeq");
        request.getSession().setAttribute("proxyMemberInfoSeq", paramValue);

        return request.getParameter("administratorPassWord");
    }

}
