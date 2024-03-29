/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateBodyGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.impl.CreditSearchTradeLogicImpl;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * メールテンプレートを取得するロジック実装クラス<br />
 *
 * @author kamei
 */
// 2023-renew general-mail from here
@Component
public class MailTemplateBodyGetLogicImpl extends AbstractShopLogic implements MailTemplateBodyGetLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditSearchTradeLogicImpl.class);

    private final Environment environment;

    @Autowired
    public MailTemplateBodyGetLogicImpl(Environment environment) {
        this.environment = environment;
    }

    /**
     * パラメータチェック
     *
     * @param hTypeMailTemplateType メールテンプレートタイプ
     */
    protected void checkParameter(HTypeMailTemplateType hTypeMailTemplateType) {
        ArgumentCheckUtil.assertNotNull("mailTemplateType", hTypeMailTemplateType);
    }

    /**
     * 実行
     *
     * @param hTypeMailTemplateType
     * @return 結果
     */
    @Override
    public String execute(HTypeMailTemplateType hTypeMailTemplateType) {

        String mailTemplateName = this.environment.getProperty(hTypeMailTemplateType.name());
        String stringMailBody = "";

        ClassPathResource fileResource = new ClassPathResource("mailtemplate/" + mailTemplateName + ".html");
        try {
            InputStream htmlFileInputStream = fileResource.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(htmlFileInputStream));
            while (bufferedReader.ready()) {
                String s = bufferedReader.readLine();
                stringMailBody = stringMailBody + s;
            }
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
        }

        return stringMailBody;
    }
}
// 2023-renew general-mail to here
