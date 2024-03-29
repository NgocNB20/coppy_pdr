package jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoByMemberInfoIdOrCustumerNoGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoByShopUniqueIdGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.helper.memberinfo.MemberInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.service.common.impl.HmFrontUserDetailsServiceImpl;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.MemberInfoUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Spring-Security ログイン失敗の処理
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Component
public class HmAuthenticationFailedHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * ログ<br/>
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HmAuthenticationFailedHandler.class);

    /**
     * ログイン失敗メッセージ
     */
    private static final String LOGIN_FAIL_MSGCD = "AbstractUserDetailsAuthenticationProvider.badCredentials";

    /**
     * 会員メールアドレス 又は 顧客番号から会員情報取得に失敗<br/>
     * <code>MSG_MEMBERINFOMAIL_CUSTOMERNO_ERR</code>
     */
    private static final String MSG_MEMBERINFOMAIL_CUSTOMERNO_ERR = "PDR-0023-001-A-";

    /**
     * エラーメッセージコード：アカウントロック
     */
    public static final String MSGCD_ACCOUNT_LOCK = "ALX000105";

    /**
     * 環境定数
     */
    private final Environment environment;
    /**
     * ユーザーApi
     */
    private final MemberInfoApi memberInfoApi;

    @Autowired
    public HmAuthenticationFailedHandler(MemberInfoApi memberInfoApi, Environment environment) {
        this.memberInfoApi = memberInfoApi;
        this.environment = environment;
    }

    /**
     * ログイン失敗の処理
     *
     * @param request
     * @param response
     * @param exception
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        HmFrontUserDetailsServiceImpl userDetailsService =
                        ApplicationContextUtility.getBean(HmFrontUserDetailsServiceImpl.class);
        MemberInfoUtility memberInfoUtility = ApplicationContextUtility.getBean(MemberInfoUtility.class);
        MemberInfoHelper memberInfoHelper = ApplicationContextUtility.getBean(MemberInfoHelper.class);
        // ユニークIDで取得する 大文字小文字の区別をなくす為
        Integer shopSeq = 1001;
        String memberInfoIdOrCustomerNo = request.getParameter("memberInfoIdOrCustomerNo");
        String memberInfoPassWord = request.getParameter("memberInfoPassWord");
        if (StringUtils.isNotEmpty(memberInfoIdOrCustomerNo) && StringUtils.isNotEmpty(memberInfoPassWord)) {
            // 会員ID 又は 顧客番号から会員情報取得
            MemberInfoByMemberInfoIdOrCustumerNoGetRequest memberInfoByMemberInfoIdOrCustumerNoGetRequest =
                            new MemberInfoByMemberInfoIdOrCustumerNoGetRequest();
            memberInfoByMemberInfoIdOrCustumerNoGetRequest.setMemberInfoIdOrCustomerNo(memberInfoIdOrCustomerNo);
            MemberInfoEntityResponse memberInfoPdrEntityResponse = null;

            try {
                memberInfoPdrEntityResponse = memberInfoApi.getByMemberInfoOrCustomerNo(
                                memberInfoByMemberInfoIdOrCustumerNoGetRequest);

            } catch (HttpClientErrorException e) {
                ApplicationLogUtility applicationLogUtility =
                                ApplicationContextUtility.getBean(ApplicationLogUtility.class);
                applicationLogUtility.writeExceptionLog(new RuntimeException("ゲストから会員へのカートマージに失敗しました。", e));
            }

            if (memberInfoPdrEntityResponse == null) {
                String errorMessage = AppLevelFacesMessageUtil.getAllMessage(MSG_MEMBERINFOMAIL_CUSTOMERNO_ERR, null)
                                                              .getMessage();
                exception = new BadCredentialsException(errorMessage);
            } else {
                // if アカウントロックでない場合は、 ログイン失敗回数を増加する
                // if アカウントロック中の場合は、エラーメッセージの表示を処理する
                MemberInfoEntity memberInfoEntity = memberInfoHelper.toMemberInfoEntity(memberInfoPdrEntityResponse);
                if (memberInfoUtility.isAvailableAccountLock() && memberInfoEntity.getAccountLockTime() == null) {
                    userDetailsService.increaseLoginFailureCount(memberInfoEntity,
                                                                 memberInfoUtility.getAccountLockCount()
                                                                );
                    if (memberInfoUtility.getAccountLockCount() - 1 <= memberInfoEntity.getLoginFailureCount()) {
                        String webSiteUrl = environment.getProperty("web.site.url");
                        String errorMessage = AppLevelFacesMessageUtil.getAllMessage(MSGCD_ACCOUNT_LOCK,
                                                                                     new Object[] {memberInfoEntity.getLoginFailureCount(),
                                                                                                     webSiteUrl}
                                                                                    ).getMessage();
                        exception = new BadCredentialsException(errorMessage);
                    }
                }
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("memberInfoId", memberInfoIdOrCustomerNo);
        super.setDefaultFailureUrl("/login/?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
