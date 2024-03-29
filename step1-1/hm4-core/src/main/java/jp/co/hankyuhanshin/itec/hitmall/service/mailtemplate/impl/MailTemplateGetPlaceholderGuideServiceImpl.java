/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.mailtemplate.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateGetValueMapLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.mailtemplate.MailTemplateGetPlaceholderGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * MailTemplateGetPlaceholderGuideServiceの実装
 *
 * @author tm27400
 * @version $Revision: 1.5 $
 */
@Service
public class MailTemplateGetPlaceholderGuideServiceImpl extends AbstractShopService
                implements MailTemplateGetPlaceholderGuideService {

    /**
     * ダミー値マップを取得するロジック
     */
    private final MailTemplateGetValueMapLogic mailTemplateGetValueMapLogic;

    @Autowired
    public MailTemplateGetPlaceholderGuideServiceImpl(MailTemplateGetValueMapLogic mailTemplateGetValueMapLogic) {
        this.mailTemplateGetValueMapLogic = mailTemplateGetValueMapLogic;
    }

    /**
     * 実行
     *
     * @param tempType tempType
     * @return 結果
     */
    @Override
    public String execute(HTypeMailTemplateType tempType) {
        Map<String, ?> valueMap = this.mailTemplateGetValueMapLogic.execute(tempType);

        Set<String> valueSet = new LinkedHashSet<>();
        for (String key : valueMap.keySet()) {

            // 末尾が数字で終わる場合
            if (key.matches(".+_[0-9]+$")) {
                // 配列要素番号を取り除いたプレースホルダ名を登録
                valueSet.add(key.replaceAll("_[0-9]+$", ""));
                // 配列であることを知らせるプレースホルダ名を登録
                valueSet.add(key.replaceAll("_[0-9]+$", "_1～"));
                continue;
            }
            valueSet.add(key);

        }

        StringBuilder builder = new StringBuilder();
        for (String value : valueSet) {
            builder.append("\"" + value + "\", ");
        }

        String guide = builder.toString().replaceAll(", $", "");

        return guide;
    }
}
