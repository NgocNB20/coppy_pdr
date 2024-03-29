/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.impl;

import jp.co.hankyuhanshin.itec.hitmall.application.mailtemplate.MailTemplateTypeEntry;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.helper.mailtemplate.OrderTransformHelper;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateGetTypeEntryLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateGetValueMapLogic;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * valueObject から指定テンプレート用プレースホルダ valueMap に変換する。
 *
 * @author tm27400
 * @version $Revision: 1.2 $
 */
@Component
public class MailTemplateGetValueMapLogicImpl extends AbstractShopLogic implements MailTemplateGetValueMapLogic {

    // LIM0010

    /**
     * テンプレート種別登録を取得するためのロジック
     */
    private final MailTemplateGetTypeEntryLogic mailTemplateGetTypeEntryLogic;

    @Autowired
    public MailTemplateGetValueMapLogicImpl(MailTemplateGetTypeEntryLogic mailTemplateGetTypeEntryLogic) {
        this.mailTemplateGetTypeEntryLogic = mailTemplateGetTypeEntryLogic;
    }

    /**
     * valueObject から指定テンプレート用プレースホルダ valueMap に変換する。
     *
     * @param mailTemplateType mailTemplateType
     * @param valueObjects     valueObjects
     * @return valueMap
     */
    @Override
    public Map<String, ?> execute(HTypeMailTemplateType mailTemplateType, Object... valueObjects) {
        MailTemplateTypeEntry entry = this.mailTemplateGetTypeEntryLogic.execute(mailTemplateType);

        // エントリされていない場合
        if (entry == null) {
            super.addErrorMessage("LIM001001", new Object[] {mailTemplateType});
            super.throwMessage();
        }

        Map<String, ?> valueMap = null;

        if (valueObjects != null && valueObjects.length != 0) {
            valueMap = ApplicationContextUtility.getBean(OrderTransformHelper.class).toValueMap(valueObjects);
        } else {
            valueMap = ApplicationContextUtility.getBean(OrderTransformHelper.class).getDummyPlaceholderMap();
        }

        return valueMap;
    }

}
