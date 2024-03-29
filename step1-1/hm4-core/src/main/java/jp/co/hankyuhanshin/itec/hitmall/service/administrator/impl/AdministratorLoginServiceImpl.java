/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.administrator.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAdministratorStatus;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupDetailEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.administrator.AdministratorLoginService;
import jp.co.hankyuhanshin.itec.hitmall.utility.AdministratorUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理者ログインサービス実装<br/>
 *
 * @author natume
 * @author tomo (itec) HM3.2 管理者権限対応（サービス＆ロジック統合及び DTO 改修含む)
 */
@Service
public class AdministratorLoginServiceImpl extends AbstractShopService implements AdministratorLoginService {

    /**
     * 管理者情報取得ロジック<br/>
     */
    private final AdminLogic adminLogic;

    /**
     * 変換ユーティリティー
     */
    private final ConversionUtility conversionUtility;

    /**
     * 管理者Utility
     */
    private final AdministratorUtility administratorUtility;

    @Autowired
    public AdministratorLoginServiceImpl(AdminLogic adminLogic,
                                         ConversionUtility conversionUtility,
                                         AdministratorUtility administratorUtility) {
        this.adminLogic = adminLogic;
        this.conversionUtility = conversionUtility;
        this.administratorUtility = administratorUtility;
    }

    /**
     * 管理者ログイン処理<br/>
     *
     * @param administratorId       管理者ID
     * @param administratorPassWord 管理者パスワード
     * @return 管理者エンティティ
     */
    @Override
    public AdministratorEntity execute(String administratorId, String administratorPassWord) {

        // パラメータチェック
        Integer shopSeq = 1001;
        ArgumentCheckUtil.assertNotEmpty("administratorId", administratorId);
        ArgumentCheckUtil.assertNotEmpty("administratorPassWord", administratorPassWord);

        // 管理者情報取得
        AdministratorEntity administratorEntity = adminLogic.getAdministrator(shopSeq, administratorId);
        if (administratorEntity == null) {
            throwMessage(MSGCD_ADMINISTRATORENTITYDTO_NULL);
        }

        // SHA-256ハッシュ値計算用文字列生成
        String sha256HashText = administratorUtility.createSHA256HashValue(administratorEntity.getShopSeq(),
                                                                           administratorEntity.getAdministratorSeq(),
                                                                           administratorPassWord
                                                                          );

        // 停止中の場合 エラー
        if (administratorEntity.getAdministratorStatus().equals(HTypeAdministratorStatus.STOP)) {
            throwMessage(MSGCD_STATUS_STOP);
        }

        return administratorEntity;
    }

    /**
     * ログインユーザの権限チェック<br/>
     *
     * @param entity 運営者権限グループエンティティ
     * @return true=権限あり、false=権限なし
     */
    @Override
    public boolean adminAuthCheck(AdminAuthGroupEntity entity) {

        // 代理ログイン可能な権限を取得
        String propProxyLoginAuthType = PropertiesUtil.getSystemPropertiesValue("proxy.login.auth.type");

        // 代理ログイン可能な権限が設定されていない場合は、権限チェックは行わない
        if (StringUtil.isEmpty(propProxyLoginAuthType)) {
            return true;
        }

        String[] arrayProxyLoginAuthType = propProxyLoginAuthType.split(",");

        // 権限グループと権限レベルをMapで保持
        Map<String, Integer> proxyLoginAuthTypeMap = new HashMap<>();
        for (String proxyLoginAuthType : arrayProxyLoginAuthType) {
            String[] arrayAuthTypeLevel = proxyLoginAuthType.split(":");
            proxyLoginAuthTypeMap.put(arrayAuthTypeLevel[0], conversionUtility.toInteger(arrayAuthTypeLevel[1]));
        }

        for (AdminAuthGroupDetailEntity detail : entity.getAdminAuthGroupDetailList()) {

            String authTypeCode = detail.getAuthTypeCode();
            Integer authLevel = detail.getAuthLevel();

            // 代理ログイン可能な権限グループに属している場合は権限レベルをチェック
            if (proxyLoginAuthTypeMap.containsKey(authTypeCode)) {
                // 設定されている権限レベルを満たしているかチェック
                if (proxyLoginAuthTypeMap.get(authTypeCode).compareTo(authLevel) > 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
