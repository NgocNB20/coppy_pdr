package jp.co.hankyuhanshin.itec.hitmall.config;

import jp.co.hankyuhanshin.itec.hitmall.config.password.AESPasswordEncoder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration

public class PasswordConfig {

    @Bean("encoderMember")

    public PasswordEncoder encoderAdmin() {
        return new AESPasswordEncoder("adminPassEncryptKey");
    }
}
