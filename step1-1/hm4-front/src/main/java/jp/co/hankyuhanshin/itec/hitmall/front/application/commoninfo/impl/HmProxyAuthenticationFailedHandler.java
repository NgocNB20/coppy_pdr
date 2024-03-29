package jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.AppLevelFacesMessageUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring-Security Proxy ログイン失敗の処理
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class HmProxyAuthenticationFailedHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * USERNAME_OR_PASSWORD_ERROR_MESSAGE
     */
    private static final String USERNAME_OR_PASSWORD_ERROR_MESSAGE = "PKG-3786-004-A-E";

    /**
     * ADMINISTRATOR_NOT_HAVE_ACCESS_MESSAGE
     */
    private static final String ADMINISTRATOR_NOT_HAVE_ACCESS_MESSAGE = "PKG-3786-003-A-E";

    /**
     * ログイン失敗の処理
     *
     * @param request   リクエスト
     * @param response  レスポンス
     * @param exception 例外
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        Integer memberInfoSeq = null;

        if (request.getSession().getAttribute("proxyMemberInfoSeq") != null) {
            memberInfoSeq = Integer.valueOf(request.getSession().getAttribute("proxyMemberInfoSeq").toString());
        }

        if (exception instanceof BadCredentialsException) {
            String errorMessage;
            if (exception.getMessage().equals(ADMINISTRATOR_NOT_HAVE_ACCESS_MESSAGE)) {
                errorMessage = AppLevelFacesMessageUtil.getAllMessage(ADMINISTRATOR_NOT_HAVE_ACCESS_MESSAGE, null)
                                                       .getMessage();
            } else {
                errorMessage = AppLevelFacesMessageUtil.getAllMessage(USERNAME_OR_PASSWORD_ERROR_MESSAGE, null)
                                                       .getMessage();
            }
            exception = new BadCredentialsException(errorMessage);
        }

        super.setDefaultFailureUrl("/login/proxy/?memberInfoSeq=" + memberInfoSeq + "&from=" + "member" + "&error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
