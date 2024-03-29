/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.service.common.impl;

import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.AdminAuthGroupDetailtResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberInfoEntityResponse;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.MemberinfoBySeqAndStatusGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ProxyAdminLoginRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.param.ProxyAdminLoginResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl.HmAdminUserDetails;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeAdministratorStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypeMemberInfoStatus;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.constant.type.HTypePasswordSHA256EncryptedFlag;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.administrator.AdminAuthGroupDetailEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.administrator.AdminAuthGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.front.helper.memberinfo.MemberInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.util.common.EnumTypeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring-Security Admin Proxy 認証サービスクラス
 * DB認証用カスタマイズ
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Service
public class HmProxyAdminUserDetailsServiceImpl implements UserDetailsService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HmProxyAdminUserDetailsServiceImpl.class);

    private static final String USERNAME_OR_PASSWORD_ERROR_MESSAGE = "PKG-3786-004-A-E";

    /**
     * ログイン失敗メッセージ
     */
    private static final String LOGIN_FAIL_MSGCD = "AbstractUserDetailsAuthenticationProvider.badCredentials";

    /**
     * ユーザーApi
     */
    private final MemberInfoApi memberInfoApi;

    /**
     * コンストラクタ
     *
     * @param memberInfoApi コンストラクタ
     */
    @Autowired
    public HmProxyAdminUserDetailsServiceImpl(MemberInfoApi memberInfoApi) {
        this.memberInfoApi = memberInfoApi;
    }

    @Override
    public UserDetails loadUserByUsername(String administratorId) throws AuthenticationException {

        // 入力チェックのエラーメッセージ
        String errorMessage;

        // DB負荷を減らすためチェックを行う
        try {
            // 半角変換
            administratorId = Normalizer.normalize(administratorId, Normalizer.Form.NFKC);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            e.printStackTrace();
            errorMessage = AppLevelFacesMessageUtil.getAllMessage("LMC002011E", null).getMessage();
            throw new BadCredentialsException(errorMessage);
        }

        // 必須チェック
        // ユーザID
        if (StringUtils.isEmpty(administratorId)) {
            errorMessage = AppLevelFacesMessageUtil.getAllMessage("SMM002002W", new Object[] {"管理者ID"}).getMessage();
            throw new BadCredentialsException(errorMessage);
        }
        // パスワード
        HttpServletRequest request =
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String password = request.getParameter("administratorPassWord");
        if (StringUtils.isEmpty(password)) {
            errorMessage = AppLevelFacesMessageUtil.getAllMessage("SMM002002W", new Object[] {"パスワード"}).getMessage();
            throw new BadCredentialsException(errorMessage);
        }

        // 文字数チェック
        if (administratorId.length() > 20) {
            errorMessage = AppLevelFacesMessageUtil.getAllMessage("USERNAME.LENGTH.E", new Object[] {"ユーザID", "20"})
                                                   .getMessage();
            throw new BadCredentialsException(errorMessage);
        }

        // 管理者情報取得
        AdministratorEntity administratorEntity = new AdministratorEntity();
        List<String> authorityList = new ArrayList<>();
        ProxyAdminLoginRequest proxyAdminLoginRequest = new ProxyAdminLoginRequest();
        proxyAdminLoginRequest.setAdminId(administratorId);

        ProxyAdminLoginResponse proxyAdminLoginResponse = null;
        try {
            proxyAdminLoginResponse = memberInfoApi.getProxyAdmin(proxyAdminLoginRequest);
        } catch (HttpClientErrorException e) {
            ApplicationLogUtility applicationLogUtility =
                            ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            applicationLogUtility.writeExceptionLog(new RuntimeException("代理ログインで管理者情報取得失敗", e));
        }

        if (proxyAdminLoginResponse == null) {
            errorMessage = AppLevelFacesMessageUtil.getAllMessage(USERNAME_OR_PASSWORD_ERROR_MESSAGE, null)
                                                   .getMessage();
            throw new BadCredentialsException(errorMessage);
        }

        administratorEntity = toAdministratorEntity(proxyAdminLoginResponse);
        // ユーザIDが存在する場合に、対象ユーザーの権限リストを取得する
        authorityList = proxyAdminLoginResponse.getAuthorityList();

        // 会員情報取得
        String proxyMemberInfoSeq = request.getSession().getAttribute("proxyMemberInfoSeq").toString();
        MemberinfoBySeqAndStatusGetRequest memberinfoBySeqAndStatusGetRequest =
                        new MemberinfoBySeqAndStatusGetRequest();
        memberinfoBySeqAndStatusGetRequest.setMemberInfoSeq(Integer.valueOf(proxyMemberInfoSeq));
        memberinfoBySeqAndStatusGetRequest.setStatus(HTypeMemberInfoStatus.ADMISSION.getValue());
        MemberInfoEntityResponse memberInfoEntityResponse = null;
        try {
            memberInfoEntityResponse = memberInfoApi.getByMemberInfoSeqAndStatus(memberinfoBySeqAndStatusGetRequest);
        } catch (HttpClientErrorException e) {
            ApplicationLogUtility applicationLogUtility =
                            ApplicationContextUtility.getBean(ApplicationLogUtility.class);
            applicationLogUtility.writeExceptionLog(new RuntimeException("代理ログインで会員情報取得失敗", e));
        }

        MemberInfoHelper memberInfoHelper = ApplicationContextUtility.getBean(MemberInfoHelper.class);
        MemberInfoEntity memberInfoEntity = memberInfoHelper.toMemberInfoEntity(memberInfoEntityResponse);
        return new HmAdminUserDetails(
                        memberInfoEntity, true, administratorEntity, authorityList.toArray(new String[0]));
    }

    /**
     * 管理者クラスエンティティに変換
     *
     * @param proxyAdminLoginResponse 代理のログインレスポンス
     * @return 管理者クラスエンティティ
     */
    private AdministratorEntity toAdministratorEntity(ProxyAdminLoginResponse proxyAdminLoginResponse) {

        ConversionUtility conversionUtility = ApplicationContextUtility.getBean(ConversionUtility.class);

        AdministratorEntity entity = new AdministratorEntity();
        entity.setAdministratorSeq(proxyAdminLoginResponse.getAdministratorSeq());
        entity.setAdministratorStatus(EnumTypeUtil.getEnumFromValue(HTypeAdministratorStatus.class,
                                                                    proxyAdminLoginResponse.getAdministratorStatus()
                                                                   ));
        entity.setAdministratorId(proxyAdminLoginResponse.getAdministratorId());
        entity.setAdministratorPassword(proxyAdminLoginResponse.getAdministratorPassword());
        entity.setMail(proxyAdminLoginResponse.getMail());
        entity.setAdministratorLastName(proxyAdminLoginResponse.getAdministratorLastName());
        entity.setAdministratorFirstName(proxyAdminLoginResponse.getAdministratorFirstName());
        entity.setAdministratorLastKana(proxyAdminLoginResponse.getAdministratorLastKana());
        entity.setAdministratorFirstKana(proxyAdminLoginResponse.getAdministratorFirstKana());
        entity.setUseStartDate(conversionUtility.toTimeStamp(proxyAdminLoginResponse.getUseStartDate()));
        entity.setUseEndDate(conversionUtility.toTimeStamp(proxyAdminLoginResponse.getUseEndDate()));
        entity.setAdminAuthGroupSeq(proxyAdminLoginResponse.getAdminAuthGroupSeq());
        entity.setRegistTime(conversionUtility.toTimeStamp(proxyAdminLoginResponse.getRegistTime()));
        entity.setUpdateTime(conversionUtility.toTimeStamp(proxyAdminLoginResponse.getUpdateTime()));
        entity.setPasswordChangeTime(conversionUtility.toTimeStamp(proxyAdminLoginResponse.getPasswordChangeTime()));
        entity.setPasswordExpiryDate(conversionUtility.toTimeStamp(proxyAdminLoginResponse.getPasswordExpiryDate()));
        entity.setLoginFailureCount(proxyAdminLoginResponse.getLoginFailureCount());
        entity.setAccountLockTime(conversionUtility.toTimeStamp(proxyAdminLoginResponse.getAccountLockTime()));
        entity.setPasswordNeedChangeFlag(EnumTypeUtil.getEnumFromValue(HTypePasswordNeedChangeFlag.class,
                                                                       proxyAdminLoginResponse.getPasswordNeedChangeFlag()
                                                                      ));
        entity.setPasswordSHA256EncryptedFlag(EnumTypeUtil.getEnumFromValue(HTypePasswordSHA256EncryptedFlag.class,
                                                                            proxyAdminLoginResponse.getPasswordSHA256EncryptedFlag()
                                                                           ));
        if (proxyAdminLoginResponse.getAdminAuthGroup() != null) {
            AdminAuthGroupEntity adminAuthGroupEntity = new AdminAuthGroupEntity();
            adminAuthGroupEntity.setAdminAuthGroupSeq(
                            proxyAdminLoginResponse.getAdminAuthGroup().getAdminAuthGroupSeq());
            adminAuthGroupEntity.setAuthGroupDisplayName(
                            proxyAdminLoginResponse.getAdminAuthGroup().getAuthGroupDisplayName());
            adminAuthGroupEntity.setRegistTime(
                            conversionUtility.toTimeStamp(proxyAdminLoginResponse.getAdminAuthGroup().getRegistTime()));
            adminAuthGroupEntity.setUpdateTime(
                            conversionUtility.toTimeStamp(proxyAdminLoginResponse.getAdminAuthGroup().getUpdateTime()));
            adminAuthGroupEntity.setUnmodifiableGroup(
                            proxyAdminLoginResponse.getAdminAuthGroup().getUnmodifiableGroup());

            if (proxyAdminLoginResponse.getAdminAuthGroup().getAdminAuthGroupDetailList() != null) {
                List<AdminAuthGroupDetailEntity> adminAuthGroupDetailList = new ArrayList<>();
                for (AdminAuthGroupDetailtResponse adminAuthGroupDetailResponse : proxyAdminLoginResponse.getAdminAuthGroup()
                                                                                                         .getAdminAuthGroupDetailList()) {
                    AdminAuthGroupDetailEntity adminAuthGroupDetailEntity = new AdminAuthGroupDetailEntity();
                    adminAuthGroupDetailEntity.setAdminAuthGroupSeq(
                                    adminAuthGroupDetailResponse.getAdminAuthGroupSeq());
                    adminAuthGroupDetailEntity.setAuthTypeCode(adminAuthGroupDetailResponse.getAuthTypeCode());
                    adminAuthGroupDetailEntity.setAuthLevel(adminAuthGroupDetailResponse.getAuthLevel());
                    adminAuthGroupDetailEntity.setRegistTime(
                                    conversionUtility.toTimeStamp(adminAuthGroupDetailResponse.getRegistTime()));
                    adminAuthGroupDetailEntity.setUpdateTime(
                                    conversionUtility.toTimeStamp(adminAuthGroupDetailResponse.getUpdateTime()));
                }
                adminAuthGroupEntity.setAdminAuthGroupDetailList(adminAuthGroupDetailList);
            }
            entity.setAdminAuthGroup(adminAuthGroupEntity);
        }
        return entity;
    }

}
