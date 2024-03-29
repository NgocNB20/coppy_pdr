/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.impl;

import jp.co.hankyuhanshin.itec.hitmall.config.thymeleaf.CustomDialect;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.CreateMailBodyLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.impl.CreditSearchTradeLogicImpl;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Map;

/**
 * マップの値を指定されたテンプレートタイプの
 * メールテンプレートに代入して返すロジック実装クラス<br />
 *
 * @author kamei
 */
// 2023-renew general-mail from here
@Component
public class CreateMailBodyLogicImpl extends AbstractShopLogic implements CreateMailBodyLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditSearchTradeLogicImpl.class);

    private final Environment environment;
    private final ClassLoaderTemplateResolver mailTemplateResolver;
    private static final String hostUrl = "hostUrl";
    private static final String shopName = "shopName";

    @Autowired
    public CreateMailBodyLogicImpl(Environment environment, ClassLoaderTemplateResolver mailTemplateResolver) {

        this.environment = environment;
        this.mailTemplateResolver = mailTemplateResolver;

    }

    /**
     * パラメータチェック
     *
     * @param hTypeMailTemplateType
     */
    protected void checkParameter(HTypeMailTemplateType hTypeMailTemplateType) {
        ArgumentCheckUtil.assertNotNull("mailTemplateType", hTypeMailTemplateType);
    }

    /**
     * 実行
     *
     * @param mailContentsMap
     * @param hTypeMailTemplateType
     * @return 結果
     */
    @Override
    public String execute(Map<String, Object> mailContentsMap, HTypeMailTemplateType hTypeMailTemplateType) {

        // メール本文用のThymeleafコンテキスト準備
        Context context = new Context();
        for (Map.Entry<String, Object> entry : mailContentsMap.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(this.mailTemplateResolver);
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.addDialect(new CustomDialect());

        // サイトURLを設定
        context.setVariable(hostUrl, environment.getProperty("web.site.url"));
        // ショップ名を設定
        context.setVariable(shopName, environment.getProperty("shop.name"));

        // メール内容を準備
        String mailTemplateName = this.environment.getProperty(hTypeMailTemplateType.name());
        return templateEngine.process(mailTemplateName, context);
    }
}
// 2023-renew general-mail to here
