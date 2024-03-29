/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.config.api;

import jp.co.hankyuhanshin.itec.hitmall.api.cart.CartApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.CategoryApi;
import jp.co.hankyuhanshin.itec.hitmall.api.goods.GoodsApi;
import jp.co.hankyuhanshin.itec.hitmall.api.memberinfo.MemberInfoApi;
import jp.co.hankyuhanshin.itec.hitmall.api.order.OrderApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.AccessApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ShopApi;
import jp.co.hankyuhanshin.itec.hitmall.api.shop.ZipcodeApi;
import jp.co.hankyuhanshin.itec.hitmall.api.ukapi.UkapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.user.UsersApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;

/**
 * APIClientのDI登録クラス<br/>
 *
 * @author kimura
 */
@Configuration
public class ApiClientConfiguration {

    @Value("${base-path-front-site-services}")
    private String basePathFrontSiteServices;

    private RestTemplate restTemplate = ApplicationContextUtility.getBean(RestTemplate.class);

    @Bean
    @Primary
    @SessionScope
    public GoodsApi goodsApi() {
        jp.co.hankyuhanshin.itec.hitmall.api.ApiClient apiClient =
                        new jp.co.hankyuhanshin.itec.hitmall.api.ApiClient(restTemplate);
        GoodsApi goodsApi = new GoodsApi(apiClient);
        goodsApi.getApiClient().setBasePath(basePathFrontSiteServices);
        return goodsApi;
    }

    @Bean
    @Primary
    @SessionScope
    public ZipcodeApi zipcodeApi() {
        jp.co.hankyuhanshin.itec.hitmall.api.ApiClient apiClient =
                        new jp.co.hankyuhanshin.itec.hitmall.api.ApiClient(restTemplate);
        ZipcodeApi zipcodeApi = new ZipcodeApi(apiClient);
        zipcodeApi.getApiClient().setBasePath(basePathFrontSiteServices);
        return zipcodeApi;
    }

    @Bean
    @Primary
    @SessionScope
    public MemberInfoApi memberInfoApi() throws Exception {
        jp.co.hankyuhanshin.itec.hitmall.api.ApiClient apiClient =
                        new jp.co.hankyuhanshin.itec.hitmall.api.ApiClient(restTemplate);
        MemberInfoApi memberInfoApi = new MemberInfoApi(apiClient);
        memberInfoApi.getApiClient().setBasePath(basePathFrontSiteServices);
        return memberInfoApi;
    }

    @Bean
    @Primary
    @SessionScope
    public CategoryApi categoryApi() {
        jp.co.hankyuhanshin.itec.hitmall.api.ApiClient apiClient =
                        new jp.co.hankyuhanshin.itec.hitmall.api.ApiClient(restTemplate);
        CategoryApi categoryApi = new CategoryApi(apiClient);
        categoryApi.getApiClient().setBasePath(basePathFrontSiteServices);
        return categoryApi;
    }

    @Bean
    @Primary
    @SessionScope
    public ShopApi shopApi() {
        jp.co.hankyuhanshin.itec.hitmall.api.ApiClient apiClient =
                        new jp.co.hankyuhanshin.itec.hitmall.api.ApiClient(restTemplate);
        ShopApi shopApi = new ShopApi(apiClient);
        shopApi.getApiClient().setBasePath(basePathFrontSiteServices);
        return shopApi;
    }

    @Bean
    @Primary
    @SessionScope
    public UsersApi usersApi() {
        jp.co.hankyuhanshin.itec.hitmall.api.ApiClient apiClient =
                        new jp.co.hankyuhanshin.itec.hitmall.api.ApiClient(restTemplate);
        UsersApi usersApi = new UsersApi(apiClient);
        usersApi.getApiClient().setBasePath(basePathFrontSiteServices);
        return usersApi;
    }

    @Bean
    @Primary
    @SessionScope
    public WebapiApi webapiApi() {
        jp.co.hankyuhanshin.itec.hitmall.api.ApiClient apiClient =
                        new jp.co.hankyuhanshin.itec.hitmall.api.ApiClient(restTemplate);
        WebapiApi webapiApi = new WebapiApi(apiClient);
        webapiApi.getApiClient().setBasePath(basePathFrontSiteServices);
        return webapiApi;
    }

    @Bean
    @Primary
    @SessionScope
    public UkapiApi ukapiApi() {
        jp.co.hankyuhanshin.itec.hitmall.api.ApiClient apiClient =
                        new jp.co.hankyuhanshin.itec.hitmall.api.ApiClient(restTemplate);
        UkapiApi ukapiApi = new UkapiApi(apiClient);
        ukapiApi.getApiClient().setBasePath(basePathFrontSiteServices);
        return ukapiApi;
    }

    @Bean
    @Primary
    @SessionScope
    public AccessApi accessApi() {
        jp.co.hankyuhanshin.itec.hitmall.api.ApiClient apiClient =
                        new jp.co.hankyuhanshin.itec.hitmall.api.ApiClient(restTemplate);
        AccessApi accessApi = new AccessApi(apiClient);
        accessApi.getApiClient().setBasePath(basePathFrontSiteServices);
        return accessApi;
    }

    @Bean
    @Primary
    @SessionScope
    public CartApi cartApi() {
        jp.co.hankyuhanshin.itec.hitmall.api.ApiClient apiClient =
                        new jp.co.hankyuhanshin.itec.hitmall.api.ApiClient(restTemplate);
        CartApi cartApi = new CartApi(apiClient);
        cartApi.getApiClient().setBasePath(basePathFrontSiteServices);
        return cartApi;
    }

    @Bean
    @Primary
    @SessionScope
    public OrderApi orderApi() {
        jp.co.hankyuhanshin.itec.hitmall.api.ApiClient apiClient =
                        new jp.co.hankyuhanshin.itec.hitmall.api.ApiClient(restTemplate);
        OrderApi orderApi = new OrderApi(apiClient);
        orderApi.getApiClient().setBasePath(basePathFrontSiteServices);
        return orderApi;
    }

}
