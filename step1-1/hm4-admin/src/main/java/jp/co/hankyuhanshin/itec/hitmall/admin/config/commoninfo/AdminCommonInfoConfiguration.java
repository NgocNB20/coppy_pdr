/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.config.commoninfo;

import jp.co.hankyuhanshin.itec.hitmall.admin.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.admin.login.common.CommonProcessAdminLogic;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CommonInfo 設定クラス
 *
 * @author yt23807
 * @version $Revision: 1.0 $
 */
@Configuration
public class AdminCommonInfoConfiguration {

    /**
     * CommonInfo Bean定義
     * Sessionスコープ
     *
     * @return
     */
    @Bean
    @Scope("session")
    @Primary
    public CommonInfo commonInfo() {
        // requestとresponseを取得
        ServletRequestAttributes requestAttributes =
                        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();

        // 共通処理Logicを生成
        CommonProcessAdminLogic commonProcessAdminLogic =
                        ApplicationContextUtility.getBean(CommonProcessAdminLogic.class);
        return commonProcessAdminLogic.execute(request, response);
    }
}
