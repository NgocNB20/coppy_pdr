/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.front.aop;

import jp.co.hankyuhanshin.itec.hitmall.api.cart.CartApi;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CommonInfoCartGetRequest;
import jp.co.hankyuhanshin.itec.hitmall.api.cart.param.CommonInfoCartResponse;
import jp.co.hankyuhanshin.itec.hitmall.front.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hitmall.front.base.utility.ApplicationLogUtility;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

/**
 * コントローラー系アスペクトクラス
 *
 * @author kn23834
 * @version $Revision: 1.0 $
 */
@Aspect
@Component
public class FrontControllerAspect {

    /**
     * カート合計数量を計算除外対象(部分一致)
     */
    private static final String[] EXCLUSION_CART_SUMCOUNT =
                    {"org.springframework.boot", "jp.co.hankyuhanshin.itec.hitmall.front.web"};

    /**
     * カートApi
     */
    private CartApi cartApi;

    @Autowired
    public FrontControllerAspect(CartApi cartApi) {
        this.cartApi = cartApi;
    }

    /**
     * コントローラー開始ログ出力メソッド<br/>
     * 指定したアノテーションが付与されているメソッドの前に呼び出される
     *
     * @Param joinPoint 実行ポイント
     */
    @Before("@annotation(org.springframework.web.bind.annotation.GetMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.PostMapping) || "
            + "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerStartLog(JoinPoint joinPoint) {

        // アプリケーションログ出力Helper取得
        ApplicationLogUtility applicationLogUtility = ApplicationContextUtility.getBean(ApplicationLogUtility.class);
        // 対象メソッドのメソッド名を取得
        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
        // アクションログを出力
        applicationLogUtility.writeActionLog(methodName);

    }

    /**
     * カート情報を計算してセッションに格納するメソッド<br/>
     *
     * @Param joinPoint 実行ポイント
     */
    @After("@annotation(org.springframework.web.bind.annotation.GetMapping) || "
           + "@annotation(org.springframework.web.bind.annotation.PostMapping) || "
           + "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void sessionCartCommonInfo(JoinPoint joinPoint) {

        // カート合計数量計算対象外
        for (String exclusion : EXCLUSION_CART_SUMCOUNT) {
            if (joinPoint.getSignature().toString().contains(exclusion)) {
                return;
            }
        }

        // CommonInfo取得
        CommonInfo commonInfo = ApplicationContextUtility.getBean(CommonInfo.class);

        // PDR Migrate Customization from here
        // PDR案件では、カート数量・金額が未設定時のみここで設定する（ログイン直後等）
        // ※カート画面遷移時（CartController#getCartDto）にAPIレスポンスからの再計算を行うので、その値を初期化しないようにする
        if (commonInfo.getCommonInfoBase().getCartGoodsSumCount() == null || BigDecimal.ZERO.equals(
                        commonInfo.getCommonInfoBase().getCartGoodsSumCount())) {
            // カート情報作成
            CommonInfoCartGetRequest request = new CommonInfoCartGetRequest();
            request.setMemberInfoSeq(commonInfo.getCommonInfoUser().getMemberInfoSeq());
            request.setAccessUid(commonInfo.getCommonInfoBase().getAccessUid());
            CommonInfoCartResponse commonInfoCartResponse = null;
            try {
                commonInfoCartResponse = cartApi.getCartCommonInfo(request);
            } catch (HttpClientErrorException e) {
                ApplicationLogUtility applicationLogUtility =
                                ApplicationContextUtility.getBean(ApplicationLogUtility.class);
                applicationLogUtility.writeExceptionLog(new RuntimeException("ゲストから会員へのカートマージに失敗しました。", e));
            }

            if (commonInfoCartResponse != null) {
                // カート合計数量をセット
                commonInfo.getCommonInfoBase().setCartGoodsSumCount(commonInfoCartResponse.getCartGoodsSumCount());
                // カート合計金額をセット
                commonInfo.getCommonInfoBase().setCartGoodsSumPrice(commonInfoCartResponse.getCartGoodsSumPrice());
            }
        }
        // PDR Migrate Customization to here
    }

}
