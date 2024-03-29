package jp.co.hankyuhanshin.itec.hitmall.front.service.common.impl;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginAdvisabilityEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginAdvisabilityGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginFailureCountResetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.LoginFailureWithLockTimeUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoByMemberInfoIdOrCustumerNoGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoByShopUniqueIdGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ResultCountResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.UpdateLoginFailureUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl.HmFrontUserDetails;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.helper.memberinfo.MemberInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.MemberInfoUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.Normalizer;

/**
 * Spring-Security Front認証サービスクラス
 * DB認証用カスタマイズ
 * ※ログイン失敗メッセージはSpring-Security標準メッセージをプロパティファイルにて上書
 *
 * @author kaneda
 */
@Service
public class HmFrontUserDetailsServiceImpl extends AbstractShopService implements UserDetailsService {

    /**
     * ログ<br/>
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HmFrontUserDetailsServiceImpl.class);
    /**
     * 会員業務Utility
     */
    private final MemberInfoUtility memberInfoUtility;

    /**
     * 環境定数
     */
    private final Environment environment;

    /**
     * 日付関連Utilityクラス
     */
    private final DateUtility dateUtility;

    /**
     * メールアドレスの正規表現
     */
    private static final String MAIL_ADDRESS_REGEX =
                    "^[a-zA-Z0-9!#\\$%&'\\*\\+\\-/=\\?\\^_`\\{\\|\\}~\\.]+@(([-a-z0-9]+\\.)*[a-z]+|\\[\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\])";

    /**
     * エラーメッセージコード：アカウントロック
     */
    public static final String MSGCD_ACCOUNT_LOCK = "ALX000105";

    /**
     * ログイン失敗メッセージ
     */
    private static final String LOGIN_FAIL_MSGCD = "AbstractUserDetailsAuthenticationProvider.badCredentials";

    // PDR Migrate Customization from here

    /**
     * 特殊文字チェックバリデータのアノテーション。
     */
    private static final String SPECIAL_CHARACTER_REGEX =
                    "[^\\x00\\x01\\x02\\x03\\x04\\x05\\x06\\x07\\x08\\x0b\\x0c\\x0e\\x0f\\x10\\x11\\x12\\x13\\x14\\x15\\x16\\x17\\x18\\x19\\x1a\\x1b\\x1c\\x1d\\x1e\\x1f\\x7f\\x80\\x81\\x82\\x83\\x84\\x85\\x86\\x87\\x88\\x89\\x8a\\x8b\\x8c\\x8d\\x8e\\x8f\\x90\\x91\\x92\\x93\\x94\\x95\\x96\\x97\\x98\\x99\\x9a\\x9b\\x9c\\x9d\\x9e\\x9f]*";

    /**
     * 会員メールアドレス 又は 顧客番号から会員情報取得に失敗<br/>
     * <code>MSG_MEMBERINFOMAIL_CUSTOMERNO_ERR</code>
     */
    private static final String MSG_MEMBERINFOMAIL_CUSTOMERNO_ERR = "PDR-0023-001-A-";

    /**
     * 会員情報からログイン可否判定の取得に失敗<br/>
     * <code>MSG_MEMBERINFO_LOGINADVISABLITY_SYS_ERR</code>
     */
    private static final String MSG_MEMBERINFO_LOGINADVISABLITY_SYS_ERR = "PDR-0437-001-A-";

    /**
     * 入力されていない場合
     */
    public static final String MSG_SPECIAL_CHARACTER_VALIDATOR = "{HSpecialCharacterValidator.INVALID_detail}";

    /**
     * 指定文字数よりも大きい場合
     */
    public static final String MAXIMUM_MESSAGE_ID =
                    "jp.co.hankyuhanshin.itec.hmbase.validator.HLengthValidator.MAXIMUM_detail";

    /**
     * 項目名
     */
    public static final String FIELD_NAME = "会員ID(お客様番号またはご登録メールアドレス）";
    // PDR Migrate Customization to here

    /**
     * ユーザーApi
     */
    private final MemberInfoApi memberInfoApi;

    /**
     * コンストラクタ
     *
     * @param memberInfoUtility 会員業務ヘルパークラス
     * @param dateUtility       日付関連Utilityクラス
     * @param environment       environment
     */
    @Autowired
    public HmFrontUserDetailsServiceImpl(MemberInfoApi memberInfoApi,
                                         MemberInfoUtility memberInfoUtility,
                                         DateUtility dateUtility,
                                         Environment environment) {
        this.memberInfoUtility = memberInfoUtility;
        this.dateUtility = dateUtility;
        this.environment = environment;
        this.memberInfoApi = memberInfoApi;
    }

    /**
     * ユーザ認証処理
     *
     * @param memberInfoIdOrCustomerNo 会員ID 又は 顧客番号
     */
    @Override
    public UserDetails loadUserByUsername(String memberInfoIdOrCustomerNo) throws AuthenticationException {

        // 入力チェックのエラーメッセージ
        String errorMessage;

        // DB負荷を減らすためチェックを行う
        try {
            // 半角変換
            memberInfoIdOrCustomerNo = Normalizer.normalize(memberInfoIdOrCustomerNo, Normalizer.Form.NFKC);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            e.printStackTrace();
            errorMessage = AppLevelFacesMessageUtil.getAllMessage("LMC002011E", null).getMessage();
            throw new BadCredentialsException(errorMessage);
        }

        // 必須チェック
        // 会員ID 又は 顧客番号
        if (StringUtils.isEmpty(memberInfoIdOrCustomerNo)) {
            errorMessage = AppLevelFacesMessageUtil.getAllMessage("SMM002002W", new Object[] {FIELD_NAME}).getMessage();
            throw new BadCredentialsException(errorMessage);
        }

        // ログイン後のユーザー情報・メールアドレスなどを変更した場合は、以下のチェックを対象外とする
        HttpServletRequest request =
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String memberInfoPassWord = request.getParameter("memberInfoPassWord");
        if (StringUtils.isEmpty(memberInfoPassWord)) {
            errorMessage = AppLevelFacesMessageUtil.getAllMessage("SMM002002W", new Object[] {"パスワード"}).getMessage();
            throw new BadCredentialsException(errorMessage);
        }

        // PDR Migrate Customization from here
        if (!memberInfoIdOrCustomerNo.matches(SPECIAL_CHARACTER_REGEX)) {
            errorMessage = AppLevelFacesMessageUtil.getAllMessage(MSG_SPECIAL_CHARACTER_VALIDATOR, null).getMessage();
            throw new BadCredentialsException(errorMessage);
        }

        // 文字数チェック
        if (memberInfoIdOrCustomerNo.length() > 160) {
            errorMessage = AppLevelFacesMessageUtil.getAllMessage(MAXIMUM_MESSAGE_ID, new Object[] {FIELD_NAME, "160"})
                                                   .getMessage();
            throw new BadCredentialsException(errorMessage);
        }

        // PDR Migrate Customization from here
        // 会員ID 又は 顧客番号から会員情報取得
        MemberInfoByMemberInfoIdOrCustumerNoGetRequest memberInfoByMemberInfoIdOrCustumerNoGetRequest =
                        new MemberInfoByMemberInfoIdOrCustumerNoGetRequest();
        memberInfoByMemberInfoIdOrCustumerNoGetRequest.setMemberInfoIdOrCustomerNo(memberInfoIdOrCustomerNo);
        MemberInfoEntityResponse memberInfoPdrEntityResponse = null;

        try {
            memberInfoPdrEntityResponse =
                            memberInfoApi.getByMemberInfoOrCustomerNo(memberInfoByMemberInfoIdOrCustumerNoGetRequest);
        } catch (HttpClientErrorException e) {
            ApplicationLogUtility applicationLogUtility =
                            ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            applicationLogUtility.writeExceptionLog(new RuntimeException("ゲストから会員へのカートマージに失敗しました。", e));
        }

        if (memberInfoPdrEntityResponse == null) {
            // 会員情報取得に失敗した場合エラー
            errorMessage = AppLevelFacesMessageUtil.getAllMessage(MSG_MEMBERINFOMAIL_CUSTOMERNO_ERR, null).getMessage();
            throw new BadCredentialsException(errorMessage);
        }

        MemberInfoHelper memberInfoHelper = ApplicationContextUtility.getBean(MemberInfoHelper.class);
        MemberInfoEntity memberInfoPdrEntity = memberInfoHelper.toMemberInfoEntity(memberInfoPdrEntityResponse);

        // ログイン可否判定
        LoginAdvisabilityGetRequest loginAdvisabilityGetRequest = new LoginAdvisabilityGetRequest();
        if (memberInfoPdrEntity.getMemberInfoStatus() != null) {
            loginAdvisabilityGetRequest.setMemberInfoStatus(memberInfoPdrEntity.getMemberInfoStatus().getValue());
        }
        if (memberInfoPdrEntity.getApproveStatus() != null) {
            loginAdvisabilityGetRequest.setApproveStatus(memberInfoPdrEntity.getApproveStatus().getValue());
        }
        if (memberInfoPdrEntity.getOnlineLoginAdvisability() != null) {
            loginAdvisabilityGetRequest.setOnlineloginAdvisability(
                            memberInfoPdrEntity.getOnlineLoginAdvisability().getValue());
        }
        if (memberInfoPdrEntity.getMemberListType() != null) {
            loginAdvisabilityGetRequest.setMemberListType(memberInfoPdrEntity.getMemberListType().getValue());
        }
        if (memberInfoPdrEntity.getAccountingType() != null) {
            loginAdvisabilityGetRequest.setAccountingType(memberInfoPdrEntity.getAccountingType().getValue());
        }

        LoginAdvisabilityEntityResponse loginAdvisabilityEntityResponse = null;
        try {
            loginAdvisabilityEntityResponse = memberInfoApi.getByLoginAdvisability(loginAdvisabilityGetRequest);
        } catch (HttpClientErrorException e) {
            ApplicationLogUtility applicationLogUtility =
                            ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            applicationLogUtility.writeExceptionLog(new RuntimeException("ゲストから会員へのカートマージに失敗しました。", e));
        }

        if (loginAdvisabilityEntityResponse == null) {
            // 会員情報取得に失敗した場合エラー
            errorMessage = AppLevelFacesMessageUtil.getAllMessage(MSG_MEMBERINFO_LOGINADVISABLITY_SYS_ERR, null)
                                                   .getMessage();
            throw new BadCredentialsException(errorMessage);
        }

        if (loginAdvisabilityEntityResponse.getLoginAdvisabilitytype().equals("0")) {
            // ログイン可否判定でログイン不可だった場合エラー
            errorMessage = AppLevelFacesMessageUtil.getAllMessage(MSG_MEMBERINFOMAIL_CUSTOMERNO_ERR, null).getMessage();
            throw new BadCredentialsException(errorMessage);
        }

        // ユニークIDで取得する 大文字小文字の区別をなくす為
        Integer shopSeq = 1001;
        String shopUniqueId = memberInfoUtility.createShopUniqueId(shopSeq, memberInfoPdrEntity.getMemberInfoId());
        // PDR Migrate Customization to here

        // 会員情報取得
        MemberInfoByShopUniqueIdGetRequest memberInfoByShopUniqueIdGetRequest =
                        new MemberInfoByShopUniqueIdGetRequest();
        memberInfoByShopUniqueIdGetRequest.setShopUniqueId(shopUniqueId);
        MemberInfoEntityResponse memberInfoEntityResponse = null;
        try {
            memberInfoEntityResponse = memberInfoApi.getByShopUniqueId(memberInfoByShopUniqueIdGetRequest);
        } catch (HttpClientErrorException e) {
            ApplicationLogUtility applicationLogUtility =
                            ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            applicationLogUtility.writeExceptionLog(new RuntimeException("ゲストから会員へのカートマージに失敗しました。", e));
        }
        MemberInfoEntity memberInfoEntity = memberInfoHelper.toMemberInfoEntity(memberInfoEntityResponse);
        if (memberInfoEntityResponse == null) {
            errorMessage = AppLevelFacesMessageUtil.getAllMessage(MSG_MEMBERINFOMAIL_CUSTOMERNO_ERR, null).getMessage();
            throw new BadCredentialsException(errorMessage);
        } else {
            if (memberInfoUtility.isAvailableAccountLock() && memberInfoEntity.getAccountLockTime() != null) {
                String webSiteUrl = environment.getProperty("web.site.url");
                errorMessage = AppLevelFacesMessageUtil.getAllMessage(MSGCD_ACCOUNT_LOCK,
                                                                      new Object[] {memberInfoEntity.getLoginFailureCount(),
                                                                                      webSiteUrl}
                                                                     )
                                                       .getMessage();
                throw new BadCredentialsException(errorMessage);
            }
        }

        return new HmFrontUserDetails(memberInfoEntity);

    }

    /**
     * セッションのユーザー情報を更新
     *
     * @param memberInfoId
     */
    public void updateUserInfo(String memberInfoId) {
        UserDetails userDetails = loadUserByUsername(memberInfoId);
        Authentication oldAuthentication = SecurityContextHolder.getContext().getAuthentication();
        UsernamePasswordAuthenticationToken newAuthentication =
                        new UsernamePasswordAuthenticationToken(userDetails, oldAuthentication.getCredentials(),
                                                                userDetails.getAuthorities()
                        );
        newAuthentication.setDetails(new WebAuthenticationDetails(
                        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()));
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

    }

    /**
     * Remember-MeのCookieを取得してみる
     *
     * @param request
     * @return
     */
    public String extractRememberMeCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if ((cookies == null) || (cookies.length == 0)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * ログイン失敗回数をインクリメントする
     * パスワード照合失敗の時に、throwMessageするが
     * ログイン失敗回数は更新したいため、別トランザクションにしてロールバックされないようにする
     *
     * @param memberInfoEntity 会員エンティティ
     * @param accountLockCount アカウントロックログイン回数
     * @return true..ロックされた / false..まだロックされていない
     */
    public int increaseLoginFailureCount(MemberInfoEntity memberInfoEntity, Integer accountLockCount) {

        Integer currentCount = memberInfoEntity.getLoginFailureCount();

        if (currentCount >= accountLockCount - 1) {
            // 現在のログイン失敗回数が、設定ファイルの回数 - 1 回だった場合
            // 今の失敗でロックされるということ
            LoginFailureWithLockTimeUpdateRequest loginFailureWithLockTimeUpdateRequest =
                            new LoginFailureWithLockTimeUpdateRequest();
            loginFailureWithLockTimeUpdateRequest.setMemberInfoSeq(memberInfoEntity.getMemberInfoSeq());
            loginFailureWithLockTimeUpdateRequest.setAccountLockTime(dateUtility.getCurrentTime());

            ResultCountResponse resultCountResponse = null;
            try {
                resultCountResponse =
                                memberInfoApi.updateLoginFailureWithLocktime(loginFailureWithLockTimeUpdateRequest);
            } catch (HttpClientErrorException e) {
                ApplicationLogUtility applicationLogUtility =
                                ApplicationContextUtility.getBean(ApplicationLogUtility.class);
                applicationLogUtility.writeExceptionLog(new RuntimeException("アカウントロックに失敗しました。", e));
                return memberInfoEntity.getLoginFailureCount();
            }

            if (resultCountResponse == null || resultCountResponse.getResultCount() == null) {
                return memberInfoEntity.getLoginFailureCount();
            } else {
                return resultCountResponse.getResultCount();
            }
        }
        UpdateLoginFailureUpdateRequest updateLoginFailureUpdateRequest = new UpdateLoginFailureUpdateRequest();
        updateLoginFailureUpdateRequest.setMemberInfoSeq(memberInfoEntity.getMemberInfoSeq());
        ResultCountResponse resultCountResponse = null;
        try {
            resultCountResponse = memberInfoApi.updateLoginFailure(updateLoginFailureUpdateRequest);
        } catch (HttpClientErrorException e) {
            ApplicationLogUtility applicationLogUtility =
                            ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            applicationLogUtility.writeExceptionLog(new RuntimeException("ログイン失敗回数更新に失敗しました。", e));
        }

        if (resultCountResponse == null || resultCountResponse.getResultCount() == null) {
            return memberInfoEntity.getLoginFailureCount();
        } else {
            return resultCountResponse.getResultCount();
        }
    }

    /**
     * ログイン失敗回数を0にする
     * アカウントロック日時をnullにする
     * ログイン成功時、パスワードリセット時に呼び出される想定
     *
     * @param memberInfoSeq 会員SEQ
     * @return 更新件数
     */
    public int resetLoginFailureCount(Integer memberInfoSeq) {
        ArgumentCheckUtil.assertGreaterThanZero("memberInfoSeq", memberInfoSeq);
        LoginFailureCountResetRequest loginFailureCountResetRequest = new LoginFailureCountResetRequest();
        loginFailureCountResetRequest.setMemberInfoSeq(memberInfoSeq);

        ResultCountResponse resultCountResponse = null;
        try {
            resultCountResponse = memberInfoApi.updateResetLoginFailureCount(loginFailureCountResetRequest);
        } catch (HttpClientErrorException e) {
            ApplicationLogUtility applicationLogUtility =
                            ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            applicationLogUtility.writeExceptionLog(new RuntimeException("ゲストから会員へのカートマージに失敗しました。", e));
        }

        return resultCountResponse.getResultCount();
    }

}
