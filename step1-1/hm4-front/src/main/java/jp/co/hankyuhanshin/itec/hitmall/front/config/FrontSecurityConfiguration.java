/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.front.config;

import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl.HmAuthenticationFailedHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl.HmProxyAuthenticationFailedHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.impl.HmProxyAuthenticationSuccessHandler;
import jp.co.hankyuhanshin.itec.hitmall.front.application.filter.CacheControlFilter;
import jp.co.hankyuhanshin.itec.hitmall.front.application.filter.CommonProcessFilter;
import jp.co.hankyuhanshin.itec.hitmall.front.application.filter.DocsControlFilter;
import jp.co.hankyuhanshin.itec.hitmall.front.application.filter.ExceptionHandlerFilter;
import jp.co.hankyuhanshin.itec.hitmall.front.application.filter.LogFilter;
import jp.co.hankyuhanshin.itec.hitmall.front.base.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hitmall.front.config.filter.ProxyAuthenticationCustomFilter;
import jp.co.hankyuhanshin.itec.hitmall.front.config.password.AESPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

/**
 * Spring-Security　セキュリティ設定Configクラス
 *
 * @author kaneda
 */
@Configuration
@EnableWebSecurity
public class FrontSecurityConfiguration {

    /**
     * Frontアカウント情報取得サービス
     */
    private final UserDetailsService userDetailsServiceFront;

    /**
     * Spring-Security Admin Proxy 認証サービスクラス
     */
    private final UserDetailsService userDetailsServiceAdmin;

    /**
     * Frontログイン成功ハンドラ
     */
    private final AuthenticationSuccessHandler successHandler;

    /**
     * Spring-Security ログイン失敗の処理
     */
    private final HmAuthenticationFailedHandler failureHandler;

    /**
     * Spring-Security Proxy ログイン失敗の処理
     */
    private final HmProxyAuthenticationFailedHandler proxyFailedHandler;

    /**
     * Spring-Security Proxy ログイン成功ハンドラ
     */
    private final HmProxyAuthenticationSuccessHandler proxySuccessHandler;

    /**
     * ３D本人認証結果受け取り用URL
     */
    public static final String PROPERTY_CODE_TD_RESULT_RECEIVE_URL = "credit.td.result.receive.relative.url";

    /**
     * 会員パスワードの暗号化キーをシステムプロパティから取得するためのキー
     * */
    private static final String MEMBER_PASSWORD_ENCRYPT_KEY = "memberPassEncryptKey";

    /**
     * 管理者パスワードの暗号化キーをシステムプロパティから取得するためのキー
     * */
    private static final String ADMIN_PASSWORD_ENCRYPT_KEY = "adminPassEncryptKey";

    /**
     * コンストラクタ
     *
     * @param userDetailsServiceFront Spring-Security Front認証サービスクラス
     * @param userDetailsServiceAdmin Spring-Security Admin Proxy 認証サービスクラス
     * @param successHandler          Spring-Security ログイン成功ハンドラ
     * @param proxySuccessHandler     Spring-Security Proxy ログイン成功ハンドラ
     * @param proxyFailedHandler      Spring-Security Proxy ログイン失敗の処理
     */
    @Autowired
    public FrontSecurityConfiguration(
                    @Qualifier("hmFrontUserDetailsServiceImpl") UserDetailsService userDetailsServiceFront,
                    @Qualifier("hmProxyAdminUserDetailsServiceImpl") UserDetailsService userDetailsServiceAdmin,
                    @Qualifier("hmAuthenticationSuccessHandler") AuthenticationSuccessHandler successHandler,
                    @Qualifier("hmProxyAuthenticationSuccessHandler")
                                    HmProxyAuthenticationSuccessHandler proxySuccessHandler,
                    HmProxyAuthenticationFailedHandler proxyFailedHandler,
                    @Qualifier("hmAuthenticationFailedHandler") HmAuthenticationFailedHandler failureHandler) {
        this.userDetailsServiceFront = userDetailsServiceFront;
        this.userDetailsServiceAdmin = userDetailsServiceAdmin;
        this.successHandler = successHandler;
        this.proxyFailedHandler = proxyFailedHandler;
        this.proxySuccessHandler = proxySuccessHandler;
        this.failureHandler = failureHandler;
    }

    /**
     * 会員パスワードエンコーダー Bean
     * ※設定値あり
     */
    @Bean
    public PasswordEncoder encoderMember() {
        return new AESPasswordEncoder(MEMBER_PASSWORD_ENCRYPT_KEY);
    }

    /**
     * 管理者パスワードエンコーダー Bean
     * ※設定値あり
     */
    @Bean
    public PasswordEncoder encoderAdmin() {
        return new AESPasswordEncoder(ADMIN_PASSWORD_ENCRYPT_KEY);
    }

    /**
     * Cookie設定 Bean
     * ※設定値あり
     */
    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> {
            servletContext.getSessionCookieConfig().setName("hm4-session");
            servletContext.getSessionCookieConfig().setSecure(true);
        };
    }

    /**
     * 静的なresourcesはSecurityチェック対象外とする
     */
    @Bean
    protected WebSecurityCustomizer webSecurityCustomizer() {
        String[] staticResource = PropertiesUtil.getSystemPropertiesValue("hm4.static.resource").split(",");
        return web -> web.ignoring().antMatchers(staticResource);
    }

    @Configuration
    @Order(1)
    public class FrontSideSecurity extends WebSecurityConfigurerAdapter {

        /**
         * DaoAuthenticationProvider　Bean
         */
        @Bean
        public DaoAuthenticationProvider authProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsServiceFront);
            authProvider.setPasswordEncoder(encoderMember());
            return authProvider;
        }

        /**
         * ページのアクセス制限の設定
         * ※設定値あり
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.authenticationProvider(authProvider());

            // 認可に関する設定
            http.authorizeRequests()
                .antMatchers("/member/pass/", "/member/pass/index.html")
                .permitAll()
                .antMatchers("/member/**")
                .fullyAuthenticated() // 会員ページ
                .antMatchers("/order/**")
                .fullyAuthenticated() // 注文ページ
                .antMatchers("/cart/**")
                // 2023-renew No22 from here
                .fullyAuthenticated() // カートページ
                .antMatchers("/confirm-docs/**")
                // 2023-renew No22 to here
                .fullyAuthenticated() // カートページ
                // 2023-renew No42 from here
                .antMatchers("/catalog/**")
                .fullyAuthenticated()
                // 2023-renew No42 to here
                // 2023-renew No14 from here
                .antMatchers("/goods/reserve**")
                .fullyAuthenticated() // セールde予約
                // 2023-renew No14 to here
                .anyRequest()
                .permitAll(); // 他

            // ログインに関する設定
            http.formLogin().loginPage("/login/member.html") // ログインページURL
                .loginProcessingUrl("/signin") // ログイン処理URL
                .successHandler(successHandler) // ログイン成功後遷移先
                .failureHandler(failureHandler) // ログインエラー遷移先
                .usernameParameter("memberInfoIdOrCustomerNo") // ログインID
                .passwordParameter("memberInfoPassWord"); // ログインパスワード

            // アクセス拒否されたセッションの宛先
            http.exceptionHandling().accessDeniedPage("/accessdenied/");

            // ログアウトに関する設定
            http.logout().clearAuthentication(true) // 認証のクリア
                .invalidateHttpSession(true) // セッションを無効にする
                .deleteCookies("JSESSIONID") // CookieからJSESSIONIDを削除
                .logoutSuccessUrl("/");

            // 会員情報破棄はSpringSecurityにお任せ（ここでは何もしない）

            // 3Dセキュア遷移時のcsrfチェックを除外
            http.csrf()
                .ignoringAntMatchers(PropertiesUtil.getSystemPropertiesValue(PROPERTY_CODE_TD_RESULT_RECEIVE_URL));
            // csrfチェックを除外(ヘッダーからの商品検索POST)
            http.csrf().ignoringAntMatchers("/search/");

            // htmlのキャッシュを有効化（Spring-Securityによるデフォルトの制御では無効化となっている）
            http.headers()
                .cacheControl()
                .disable()
                .addHeaderWriter(new StaticHeadersWriter(HttpHeaders.CACHE_CONTROL, "no-cache"));

            // SpringSequrityのFilterChainProxyの最初にセットされる、
            // ChannelProcessingFilterの前にHMのfilterをセット
            // 「LogFilter → CommonProcessFilter → SpringSequrityのFilter」の順で起動
            http.addFilterBefore(new LogFilter(), ChannelProcessingFilter.class);
            http.addFilterBefore(new CommonProcessFilter(), ChannelProcessingFilter.class);

            // ------------------------------------------------------------------------------------------------------
            // Spring Securityで @SessionAttributes を利用するとCache-Controlヘッダが上書きされるため、
            // 上記「Cache-Control: no-cache」を設定していても、各Controllerで「@SessionAttributes」を宣言している場合は、
            // HTTPレスポンスヘッダーに「Cache-Control: no-store」が勝手に上書きされてしまう
            // 従って、以下のフィルターを追加することで、「Cache-Control: no-cache」を改めて設定する
            // ------------------------------------------------------------------------------------------------------
            http.addFilterBefore(new CacheControlFilter(), ChannelProcessingFilter.class);

            // 意外な例外ハンドリングを行う処理をセット
            http.addFilterBefore(new ExceptionHandlerFilter(), ChannelProcessingFilter.class);
            // 2023-renew No22 from here
            http.addFilterBefore(new DocsControlFilter(), ChannelProcessingFilter.class);
            // 2023-renew No22 to here
        }
    }

    @Configuration
    @Order(2)
    public class AdminSideSecurity extends WebSecurityConfigurerAdapter {

        /**
         * DaoAuthenticationProvider　Bean
         */
        @Bean
        public DaoAuthenticationProvider authProviderAdmin() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsServiceAdmin);
            authProvider.setPasswordEncoder(encoderAdmin());
            return authProvider;
        }

        /**
         * ProxyAuthenticationCustomFilter Bean
         */
        @Bean
        public ProxyAuthenticationCustomFilter proxyAuthenticationCustomFilter() throws Exception {
            ProxyAuthenticationCustomFilter filter = new ProxyAuthenticationCustomFilter();
            filter.setAuthenticationManager(authenticationManagerBean());
            filter.setFilterProcessesUrl("/login/proxy");
            filter.setAuthenticationFailureHandler(proxyFailedHandler);
            filter.setAuthenticationSuccessHandler(proxySuccessHandler);

            return filter;
        }

        /**
         * ページのアクセス制限の設定
         * ※設定値あり
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.authenticationProvider(authProviderAdmin());

            http.authorizeRequests()
                .antMatchers("/member/**")
                .fullyAuthenticated()
                .antMatchers("/order/**")
                .fullyAuthenticated() // 注文ページ
                .antMatchers("/cart/**")
                .fullyAuthenticated() // カートページ
                // 2023-renew No42 from here
                .antMatchers("/catalog/**")
                .fullyAuthenticated()
                // 2023-renew No42 to here
                // 2023-renew No14 from here
                .antMatchers("/goods/reserve**")
                .fullyAuthenticated() // セールde予約
                // 2023-renew No14 to here
                .anyRequest()
                .permitAll(); // 他

            // ログインに関する設定
            http.addFilterBefore(proxyAuthenticationCustomFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/login/proxy/")
                .loginProcessingUrl("/login/proxy");

            // アクセス拒否されたセッションの宛先
            http.exceptionHandling().accessDeniedPage("/login/proxy/");

            // セッションに関する設定
            http.sessionManagement().sessionAuthenticationErrorUrl("/login/proxy/"); // セッション有効期限切れ遷移先

            // ログアウトに関する設定
            http.logout().clearAuthentication(true) // 認証のクリア
                .invalidateHttpSession(true) // セッションを無効にする
                .deleteCookies("JSESSIONID") // CookieからJSESSIONIDを削除
                .logoutSuccessUrl("/");

            // 3Dセキュア遷移時のcsrfチェックを除外
            http.csrf()
                .ignoringAntMatchers(PropertiesUtil.getSystemPropertiesValue(PROPERTY_CODE_TD_RESULT_RECEIVE_URL));
            // csrfチェックを除外(ヘッダーからの商品検索POST)
            http.csrf().ignoringAntMatchers("/search/");

            // htmlのキャッシュを有効化（Spring-Securityによるデフォルトの制御では無効化となっている）
            http.headers()
                .cacheControl()
                .disable()
                .addHeaderWriter(new StaticHeadersWriter(HttpHeaders.CACHE_CONTROL, "no-cache"));

            // SpringSequrityのFilterChainProxyの最初にセットされる、
            // ChannelProcessingFilterの前にHMのfilterをセット
            // 「LogFilter → CommonProcessFilter → SpringSequrityのFilter」の順で起動
            http.addFilterBefore(new LogFilter(), ChannelProcessingFilter.class);
            http.addFilterBefore(new CommonProcessFilter(), ChannelProcessingFilter.class);

            // ------------------------------------------------------------------------------------------------------
            // Spring Securityで @SessionAttributes を利用するとCache-Controlヘッダが上書きされるため、
            // 上記「Cache-Control: no-cache」を設定していても、各Controllerで「@SessionAttributes」を宣言している場合は、
            // HTTPレスポンスヘッダーに「Cache-Control: no-store」が勝手に上書きされてしまう
            // 従って、以下のフィルターを追加することで、「Cache-Control: no-cache」を改めて設定する
            // ------------------------------------------------------------------------------------------------------
            http.addFilterBefore(new CacheControlFilter(), ChannelProcessingFilter.class);

            // 意外な例外ハンドリングを行う処理をセット
            http.addFilterBefore(new ExceptionHandlerFilter(), ChannelProcessingFilter.class);
            // 2023-renew No22 from here
            http.addFilterBefore(new DocsControlFilter(), ChannelProcessingFilter.class);
            // 2023-renew No22 to here
        }
    }

}
