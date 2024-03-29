/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.utility;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hmbase.util.carrier.Carrier;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.SmartPhoneUtility;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * アクセスデバイスの解析用Utility
 *
 * @author kk32102
 */
@Component
public class AccessDeviceUtility {

    /**
     * User-Agent を取得
     *
     * @param request リクエスト
     * @return User-Agent
     */
    public String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    /**
     * デバイス種別を返却
     *
     * @param request リクエスト
     * @return デバイス種別
     */
    public HTypeDeviceType getDeviceType(HttpServletRequest request) {
        String userAgent = getUserAgent(request);
        return getDeviceType(userAgent);
    }

    /**
     * デバイス種別を返却
     *
     * @param userAgent User-Agent
     * @return デバイス種別
     */
    public HTypeDeviceType getDeviceType(String userAgent) {

        // スマートフォン、タブレット判定
        SmartPhoneUtility smartPhoneUtility = ApplicationContextUtility.getBean(SmartPhoneUtility.class);
        if (smartPhoneUtility.isDeviceTypeSp(userAgent)) {
            return HTypeDeviceType.SP;
        } else if (smartPhoneUtility.isDeviceTypeTab(userAgent)) {
            return HTypeDeviceType.TAB;
        }

        // フィーチャーフォン判定
        Carrier carrier = Carrier.agent(userAgent);
        if (Carrier.UNKNOWN != carrier && Carrier.CRAWLER != carrier) {
            return HTypeDeviceType.MB;
        }

        return HTypeDeviceType.PC;
    }

}
