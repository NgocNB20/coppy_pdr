/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.application.listener;

import jp.co.hankyuhanshin.itec.hitmall.api.cart.CartApi;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CartGoodsMergeUpdateRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.WebapiApi;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetCouponListRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.webapi.param.WebApiGetCouponListResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.pc.web.front.member.coupon.CouponHelper;
import jp.co.hankyuhanshin.itec.hitmall.front.utility.CommonInfoUtility;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.web.client.HttpClientErrorException;

/**
 * 認証用イベントリスナ
 *
 * @author kimura
 */
public class AuthenticationEventListeners {

    /**
     * ログイン処理がすべて成功したことを通知するためのイベントクラス<br/>
     * ゲストのカートを更新
     *
     * @param event
     */
    @EventListener
    public void interactiveAuthenticationSuccessEvent(InteractiveAuthenticationSuccessEvent event) {

        CommonInfo commonInfo = ApplicationContextUtility.getBean(CommonInfo.class);
        // SpringSecutiryの会員情報から取得
        Integer memberInfoSeq = commonInfo.getCommonInfoUser().getMemberInfoSeq();

        if (memberInfoSeq != null) {
            // カート更新処理
            CartGoodsMergeUpdateRequest cartGoodsMergeUpdateRequest = new CartGoodsMergeUpdateRequest();
            cartGoodsMergeUpdateRequest.setAccessUid(commonInfo.getCommonInfoBase().getAccessUid());
            cartGoodsMergeUpdateRequest.setChangeMemberInfoSeq(memberInfoSeq);
            try {
                CartApi cartApi = ApplicationContextUtility.getBean(CartApi.class);
                cartApi.merge(cartGoodsMergeUpdateRequest);
            } catch (HttpClientErrorException e) {
                ApplicationLogUtility applicationLogUtility =
                                ApplicationContextUtility.getBean(ApplicationLogUtility.class);
                applicationLogUtility.writeExceptionLog(new RuntimeException("ゲストから会員へのカートマージに失敗しました。", e));
            }

            // 2023-renew No24 from here
            // セッションのクーポン数を更新
            CommonInfoUtility commonInfoUtility = ApplicationContextUtility.getBean(CommonInfoUtility.class);
            WebApiGetCouponListRequest webApiGetCouponListRequest = new WebApiGetCouponListRequest();
            webApiGetCouponListRequest.setCustomerNo(commonInfoUtility.getCustomerNo(commonInfo));
            WebApiGetCouponListResponse webApiGetCouponListResponse = null;
            try {
                WebapiApi webapiApi = ApplicationContextUtility.getBean(WebapiApi.class);
                webApiGetCouponListResponse = webapiApi.getCouponList(webApiGetCouponListRequest);
            } catch (HttpClientErrorException e) {
                ApplicationLogUtility applicationLogUtility =
                                ApplicationContextUtility.getBean(ApplicationLogUtility.class);
                applicationLogUtility.writeExceptionLog(new RuntimeException("クーポン数の更新に失敗しました。", e));
            }
            CouponHelper couponHelper = ApplicationContextUtility.getBean(CouponHelper.class);
            commonInfoUtility.setCouponCount(
                            commonInfo, couponHelper.toWebApiGetCouponListResponseDto(webApiGetCouponListResponse));
            // 2023-renew No24 to here
        }
    }

}
