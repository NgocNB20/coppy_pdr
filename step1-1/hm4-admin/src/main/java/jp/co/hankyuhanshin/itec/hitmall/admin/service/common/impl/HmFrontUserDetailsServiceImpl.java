/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.admin.service.common.impl;

import jp.co.hankyuhanshin.itec.hitmall.admin.application.commoninfo.impl.HmFrontUserDetails;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.loginadvisability.LoginAdvisabilityEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.loginadvisability.LoginAdvisabilityGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.utility.MemberInfoUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
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
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HmFrontUserDetailsServiceImpl.class);

    /**
     * 会員情報取得ロジック
     */
    private final MemberInfoGetLogic memberInfoGetLogic;

    /**
     * 会員情報更新ロジック
     */
    private final MemberInfoUpdateLogic memberInfoUpdateLogic;

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
     * 顧客番号でのログイン
     */
    private final LoginAdvisabilityGetLogic loginAdvisabilityGetLogic;

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
     * コンストラクタ
     *
     * @param memberInfoGetLogic        会員情報取得ロジック
     * @param memberInfoUpdateLogic     会員情報更新
     * @param memberInfoUtility         会員業務ヘルパークラス
     * @param dateUtility               日付関連Utilityクラス
     * @param environment               environment
     * @param loginAdvisabilityGetLogic 顧客番号でのログイン
     */
    @Autowired
    public HmFrontUserDetailsServiceImpl(MemberInfoGetLogic memberInfoGetLogic,
                                         MemberInfoUpdateLogic memberInfoUpdateLogic,
                                         MemberInfoUtility memberInfoUtility,
                                         DateUtility dateUtility,
                                         Environment environment,
                                         LoginAdvisabilityGetLogic loginAdvisabilityGetLogic) {
        this.memberInfoGetLogic = memberInfoGetLogic;
        this.memberInfoUpdateLogic = memberInfoUpdateLogic;
        this.memberInfoUtility = memberInfoUtility;
        this.dateUtility = dateUtility;
        this.environment = environment;
        this.loginAdvisabilityGetLogic = loginAdvisabilityGetLogic;
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
        MemberInfoEntity memberInfoPdrEntity =
                        memberInfoGetLogic.getMemberInfoEntityByMemberInfoIdOrCustomerNo(memberInfoIdOrCustomerNo);

        if (memberInfoPdrEntity == null) {
            // 会員情報取得に失敗した場合エラー
            errorMessage = AppLevelFacesMessageUtil.getAllMessage(MSG_MEMBERINFOMAIL_CUSTOMERNO_ERR, null).getMessage();
            throw new BadCredentialsException(errorMessage);
        }

        // ログイン可否判定
        LoginAdvisabilityEntity loginAdvisabilityEntity =
                        loginAdvisabilityGetLogic.getLoginAdvisabilityPdrEntityByMemberInfo(memberInfoPdrEntity);

        if (loginAdvisabilityEntity == null) {
            // 会員情報取得に失敗した場合エラー
            errorMessage = AppLevelFacesMessageUtil.getAllMessage(MSG_MEMBERINFO_LOGINADVISABLITY_SYS_ERR, null)
                                                   .getMessage();
            throw new BadCredentialsException(errorMessage);
        }

        if (loginAdvisabilityEntity.getLoginAdvisabilitytype().equals("0")) {
            // ログイン可否判定でログイン不可だった場合エラー
            errorMessage = AppLevelFacesMessageUtil.getAllMessage(MSG_MEMBERINFOMAIL_CUSTOMERNO_ERR, null).getMessage();
            throw new BadCredentialsException(errorMessage);
        }

        // ユニークIDで取得する 大文字小文字の区別をなくす為
        Integer shopSeq = 1001;
        String shopUniqueId = memberInfoUtility.createShopUniqueId(shopSeq, memberInfoPdrEntity.getMemberInfoId());
        // PDR Migrate Customization to here

        // 会員情報取得
        MemberInfoEntity memberInfoEntity = memberInfoGetLogic.execute(shopUniqueId);
        if (memberInfoEntity == null) {
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
            return memberInfoUpdateLogic.updateLoginFailureCount(
                            memberInfoEntity.getMemberInfoSeq(), dateUtility.getCurrentTime());
        }

        return memberInfoUpdateLogic.updateLoginFailureCount(memberInfoEntity.getMemberInfoSeq());
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
        return memberInfoUpdateLogic.resetLoginFailureCount(memberInfoSeq);
    }

}
