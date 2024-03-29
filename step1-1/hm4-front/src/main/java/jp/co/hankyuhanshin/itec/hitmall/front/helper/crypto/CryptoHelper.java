/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.helper.crypto;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

/**
 * 暗号化/復号ヘルパークラス
 *
 * @author matsumoto
 */
@Component
public class CryptoHelper {
    /**
     * 端末識別番号の暗号化キーをシステムプロパティから取得するためのキー
     */
    protected static final String ACCESS_UID_ENCRYPT_KEY = "accessUidEncryptKey";
    // PDR Migrate Customization from here
    /**
     * 会員パスワードの暗号化キーをシステムプロパティから取得するためのキー
     */
    protected static final String MEMBER_PASSWORD_ENCRYPT_KEY = "memberPassEncryptKey";

    public String encryptMemberPassword(String password) {
        String keyString = PropertiesUtil.getSystemPropertiesValue(MEMBER_PASSWORD_ENCRYPT_KEY);
        return ApplicationContextUtility.getBean(AESHelper.class).encrypt(password, keyString);
    }
    // PDR Migrate Customization to here

    /**
     * 端末識別番号の暗号化
     *
     * @param accessUid 端末識別番号
     * @return 端末識別番号を暗号化した文字列
     */
    public String encryptAccessUid(String accessUid) {
        String keyString = PropertiesUtil.getSystemPropertiesValue(ACCESS_UID_ENCRYPT_KEY);
        return ApplicationContextUtility.getBean(AESHelper.class).encrypt(accessUid, keyString);
    }

    /**
     * 暗号化された端末識別番号の復号
     *
     * @param encryptedAccessUid 暗号化された端末識別番号の文字列
     * @return 復号した端末識別番号
     */
    public String decryptAccessUid(String encryptedAccessUid) {
        String keyString = PropertiesUtil.getSystemPropertiesValue(ACCESS_UID_ENCRYPT_KEY);
        return ApplicationContextUtility.getBean(AESHelper.class).decrypt(encryptedAccessUid, keyString);
    }
}
