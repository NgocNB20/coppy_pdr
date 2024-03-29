package jp.co.hankyuhanshin.itec.hitmall.batch.config;

import jp.co.hankyuhanshin.itec.hitmall.config.password.AESPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BatchSecutiryConfiguration {

    /**
     * 会員パスワードの暗号化キーをシステムプロパティから取得するためのキー
     * */
    private static final String MEMBER_PASSWORD_ENCRYPT_KEY = "memberPassEncryptKey";

    /**
     * 会員パスワードエンコーダー Bean
     * ※設定値あり
     */
    @Bean
    public PasswordEncoder encoderMember() {
        return new AESPasswordEncoder(MEMBER_PASSWORD_ENCRYPT_KEY);
    }

}
