/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.mailtemplate;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;

/**
 * メールテンプレートを取得するサービス
 *
 * @author tm27400
 * @version $Revision: 1.2 $
 */
public interface MailTemplateGetService {

    /**
     * 実行
     *
     * @param tempType tempType
     * @param tempSeq  tempSeq
     * @return 結果
     */
    MailTemplateEntity execute(HTypeMailTemplateType tempType, Integer tempSeq);

}
