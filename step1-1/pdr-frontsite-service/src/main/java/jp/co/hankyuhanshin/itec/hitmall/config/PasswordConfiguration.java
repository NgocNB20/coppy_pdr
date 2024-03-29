package jp.co.hankyuhanshin.itec.hitmall.config;

import jp.co.hankyuhanshin.itec.hitmall.config.password.AESPasswordEncoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfiguration {

    /**
     * 会員パスワードの暗号化キーをシステムプロパティから取得するためのキー
     * */
    private static final String MEMBER_PASSWORD_ENCRYPT_KEY = "memberPassEncryptKey";

    /**
     * 管理者パスワードの暗号化キーをシステムプロパティから取得するためのキー
     * */
    private static final String ADMIN_PASSWORD_ENCRYPT_KEY = "adminPassEncryptKey";

    @Bean
    @Qualifier("encoderMember")
    public PasswordEncoder encoderMember() {
        return new AESPasswordEncoder(MEMBER_PASSWORD_ENCRYPT_KEY);
    }

    /**
     * 管理者パスワードエンコーダー Bean
     * ※設定値あり
     */
    @Bean
    @Qualifier("encoderAdmin")
    public PasswordEncoder encoderAdmin() {
        return new AESPasswordEncoder(ADMIN_PASSWORD_ENCRYPT_KEY);
    }
}
