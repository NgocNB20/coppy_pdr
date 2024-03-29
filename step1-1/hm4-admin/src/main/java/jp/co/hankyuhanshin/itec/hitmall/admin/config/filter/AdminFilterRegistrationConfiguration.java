package jp.co.hankyuhanshin.itec.hitmall.admin.config.filter;

import org.springframework.context.annotation.Configuration;

/**
 * フィルター 設定クラス
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Configuration
public class AdminFilterRegistrationConfiguration {

    /**
     * FrontSecurityConfigurationにて、SpringSecurityのfilterChain前に実行するように設定<br/>
     * ログフィルターの定義
     * @return FilterRegistrationBean<LogFilter>
     */
    // @Bean
    // public FilterRegistrationBean<LogFilter> logFilter() {
    // // ログフィルターを登録
    // FilterRegistrationBean<LogFilter> bean = new
    // FilterRegistrationBean<LogFilter>(new LogFilter());
    // // 全てのリクエストに対してログフィルターを有効化
    // bean.addUrlPatterns("*");
    // // ログフィルターの実行順序を4に設定
    // bean.setOrder(4);
    // return bean;
    // }

}
