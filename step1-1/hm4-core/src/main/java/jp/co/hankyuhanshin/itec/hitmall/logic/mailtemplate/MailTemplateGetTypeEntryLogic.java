/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate;

import jp.co.hankyuhanshin.itec.hitmall.application.mailtemplate.MailTemplateTypeEntry;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;

/**
 * メールテンプレート種別に対応する MailTemplateTypeEntry を取得する。
 *
 * @author tm27400
 * @version $Revision: 1.2 $
 */
public interface MailTemplateGetTypeEntryLogic {

    // LIM0008

    /**
     * メールテンプレート種別に対応する MailTemplateTypeEntry を取得する。
     *
     * @param type メールテンプレート種別
     * @return MailTemplateTypeEntry
     */
    MailTemplateTypeEntry execute(HTypeMailTemplateType type);

}
