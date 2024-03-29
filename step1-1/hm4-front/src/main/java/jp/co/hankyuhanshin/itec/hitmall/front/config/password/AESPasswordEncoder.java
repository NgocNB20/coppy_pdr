package jp.co.hankyuhanshin.itec.hitmall.front.config.password;

import jp.co.hankyuhanshin.itec.hitmall.front.base.util.AppLevelFacesMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.helper.crypto.AESHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * AESパスワード暗号化
 * @author thang
 */
public class AESPasswordEncoder implements PasswordEncoder {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(AESPasswordEncoder.class);

    /** 会員パスワードがnullまたは、復号に失敗 */
    public static final String MSGCD_PASSWORD_DECRYPT_FAIL = "SMM001104";

    /** パスワードの暗号化キーをシステムプロパティから取得するためのキー */
    private final String passwordEncryptKey;

    /**
     * ESパスワード暗号化
     * @param passwordEncryptKey パスワード暗号化のキー
     */
    public AESPasswordEncoder(String passwordEncryptKey) {
        this.passwordEncryptKey = passwordEncryptKey;
    }

    /**
     * パスワード暗号化
     *
     * @param rawPassword 生のパスワード
     * @return 暗号化したパスワード
     */
    @Override
    public String encode(CharSequence rawPassword) {
        String keyString = PropertiesUtil.getSystemPropertiesValue(passwordEncryptKey);
        return ApplicationContextUtility.getBean(AESHelper.class).encrypt(rawPassword.toString(), keyString);
    }

    /**
     * 入力パスワードと暗号化したパスワードを比較する
     *
     * @param rawPassword 生のパスワード
     * @param encodedPassword 暗号化したパスワード
     * @return true：一致する、false：一致しない
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        } else if (encodedPassword != null && encodedPassword.length() != 0) {
            String keyString = PropertiesUtil.getSystemPropertiesValue(passwordEncryptKey);
            String decryptPassword =
                            ApplicationContextUtility.getBean(AESHelper.class).decrypt(encodedPassword, keyString);
            // パスワードの情報が登録されていない、または、パスワードの復号に失敗
            if (decryptPassword == null) {
                String errorMessage =
                                AppLevelFacesMessageUtil.getAllMessage(MSGCD_PASSWORD_DECRYPT_FAIL, null).getMessage();
                throw new IllegalArgumentException(errorMessage);
            }
            return decryptPassword.equals(rawPassword.toString());
        } else {
            LOGGER.warn("Empty encoded password");
            return false;
        }
    }
}
