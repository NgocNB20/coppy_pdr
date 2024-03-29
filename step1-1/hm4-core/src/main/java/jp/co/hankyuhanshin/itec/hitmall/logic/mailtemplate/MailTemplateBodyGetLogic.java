/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;

/**
 * メールテンプレートを取得するロジック
 *
 * @author kamei
 */
// 2023-renew general-mail from here
public interface MailTemplateBodyGetLogic {

    /**
     * 実行
     *
     * @param hTypeMailTemplateType
     * @return 結果
     */
    String execute(HTypeMailTemplateType hTypeMailTemplateType);

}
// 2023-renew general-mail to here
