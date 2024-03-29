/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.service.common.impl;

import jp.co.hankyuhanshin.itec.hitmall.admin.application.commoninfo.impl.HmAdminUserDetails;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAdministratorStatus;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminAuthGroupDetailLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hmbase.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.Normalizer;
import java.util.List;

/**
 * Spring-Security Admin認証サービスクラス
 * DB認証用カスタマイズ
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Service
public class HmAdminUserDetailsServiceImpl extends AbstractShopService implements UserDetailsService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HmAdminUserDetailsServiceImpl.class);

    /**
     * 管理者情報取得ロジック
     */
    private final AdminLogic adminLogic;

    /**
     * 権限グループ詳細ロジック
     */
    private final AdminAuthGroupDetailLogic adminAuthGroupDetailLogic;

    /**
     * ログイン失敗メッセージ
     */
    private static final String LOGIN_FAIL_MSGCD = "AbstractUserDetailsAuthenticationProvider.badCredentials";

    /**
     * コンストラクタ
     *
     * @param adminLogic
     * @param adminAuthGroupDetailLogic
     */
    @Autowired
    public HmAdminUserDetailsServiceImpl(AdminLogic adminLogic, AdminAuthGroupDetailLogic adminAuthGroupDetailLogic) {
        this.adminLogic = adminLogic;
        this.adminAuthGroupDetailLogic = adminAuthGroupDetailLogic;
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
            errorMessage = AppLevelFacesMessageUtil.getAllMessage("SMM002002W", new Object[] {"ユーザID"}).getMessage();
            throw new BadCredentialsException(errorMessage);
        }
        // パスワード
        HttpServletRequest request =
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String password = request.getParameter("password");
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

        // パラメータチェック
        Integer shopSeq = 1001;
        ArgumentCheckUtil.assertGreaterThanZero("commoninfo.shopSeq", shopSeq);

        // 管理者情報取得
        AdministratorEntity administratorEntity = adminLogic.getAdministrator(shopSeq, administratorId);
        if (administratorEntity == null || !administratorEntity.getAdministratorStatus()
                                                               .equals(HTypeAdministratorStatus.USE)) {
            errorMessage = AppLevelFacesMessageUtil.getAllMessage(LOGIN_FAIL_MSGCD, null).getMessage();
            throw new BadCredentialsException(errorMessage);
        }

        // ユーザIDが存在する場合に、対象ユーザーの権限リストを取得する
        List<String> authorityList =
                        adminAuthGroupDetailLogic.getAuthorityList(administratorEntity.getAdminAuthGroupSeq());

        return new HmAdminUserDetails(null, false, administratorEntity, authorityList.toArray(new String[0]));

    }
}
