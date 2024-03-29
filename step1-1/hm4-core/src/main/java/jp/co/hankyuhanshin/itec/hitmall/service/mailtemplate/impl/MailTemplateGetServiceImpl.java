/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.mailtemplate.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.mailtemplate.MailTemplateGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MailTemplateGetServiceの実装
 *
 * @author tm27400
 * @version $Revision: 1.3 $
 */
@Service
public class MailTemplateGetServiceImpl extends AbstractShopService implements MailTemplateGetService {

    /**
     * テンプレートを取得するロジック
     */
    private final MailTemplateGetLogic mailTemplateGetLogic;

    @Autowired
    public MailTemplateGetServiceImpl(MailTemplateGetLogic mailTemplateGetLogic) {
        this.mailTemplateGetLogic = mailTemplateGetLogic;
    }

    /**
     * 実行
     *
     * @param tempType tempType
     * @param tempSeq  tempSeq
     * @return 結果
     */
    @Override
    public MailTemplateEntity execute(HTypeMailTemplateType tempType, Integer tempSeq) {

        Integer shopSeq = 1001;

        if (tempSeq != null) {
            return this.mailTemplateGetLogic.execute(shopSeq, tempSeq);
        } else {
            return this.mailTemplateGetLogic.execute(shopSeq, tempType);
        }

    }

}
