package jp.co.hankyuhanshin.itec.hitmall.front.config.filter;

import org.springframework.context.annotation.Configuration;

/**
 * フィルター 設定クラス
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Configuration
public class FrontFilterRegistrationConfiguration {

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
    // // ログフィルターの実行順序を設定（HIT-MALL Ver3踏襲。web.xmlのfilter-mapping記述順を適用）
    // bean.setOrder(7);
    // return bean;
    // }

    /**
     * FrontSecurityConfigurationにて、SpringSecurityのfilterChain前に実行するように設定<br/>
     * 共通処理フィルターの定義
     * @return FilterRegistrationBean<LogFilter>
     */
    // @Bean
    // public FilterRegistrationBean<CommonProcessFilter> commonProcessFilter()
    // {
    // // ログフィルターを登録
    // FilterRegistrationBean<CommonProcessFilter> bean = new
    // FilterRegistrationBean<CommonProcessFilter>(new CommonProcessFilter());
    // // 全てのリクエストに対してログフィルターを有効化
    // bean.addUrlPatterns("*");
    // // ログフィルターの実行順序を設定（HIT-MALL Ver3踏襲。web.xmlのfilter-mapping記述順を適用）
    // bean.setOrder(15);
    // return bean;
    // }
}
