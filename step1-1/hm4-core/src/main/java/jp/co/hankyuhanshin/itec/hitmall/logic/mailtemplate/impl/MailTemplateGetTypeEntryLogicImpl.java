/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.impl;

import jp.co.hankyuhanshin.itec.hitmall.application.mailtemplate.MailTemplateTypeEntry;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateGetTypeEntryLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * メールテンプレート種別に対応する MailTemplateTypeEntry を取得する。
 *
 * @author tm27400
 * @version $Revision: 1.2 $
 */
@Component
public class MailTemplateGetTypeEntryLogicImpl extends AbstractShopLogic implements MailTemplateGetTypeEntryLogic {

    // LIM0009

    /**
     * メールテンプレート種別に対応する MailTemplateTypeEntry を取得する。
     *
     * @param type メールテンプレート種別
     * @return MailTemplateTypeEntry
     */
    @Override
    public MailTemplateTypeEntry execute(HTypeMailTemplateType type) {

        ArgumentCheckUtil.assertNotNull("mailTemplateType", type);

        List<MailTemplateTypeEntry> mailTemplateTypeList =
                        (List<MailTemplateTypeEntry>) ApplicationContextUtility.getApplicationContext()
                                                                               .getBean("mailTemplateTypeEntryList");
        for (MailTemplateTypeEntry entry : mailTemplateTypeList) {

            if (entry == null || entry.getTemplateType() == null) {
                continue;
            }

            if (entry.getTemplateType() == type) {
                return entry;
            }
        }

        return null;
    }
}
