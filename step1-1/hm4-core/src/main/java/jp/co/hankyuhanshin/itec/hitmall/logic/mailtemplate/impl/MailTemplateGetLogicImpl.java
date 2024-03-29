/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.mail.MailTemplateDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.mailtemplate.MailTemplateGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * メールテンプレート取得ロジック実装クラス<br />
 *
 * @author negishi
 * @version $Revision: 1.2 $
 */
@Component
public class MailTemplateGetLogicImpl extends AbstractShopLogic implements MailTemplateGetLogic {

    // LIM0008

    /**
     * メールテンプレートDao
     */
    private final MailTemplateDao mailTemplateDao;

    @Autowired
    public MailTemplateGetLogicImpl(MailTemplateDao mailTemplateDao) {
        this.mailTemplateDao = mailTemplateDao;
    }

    /**
     * パラメータチェック
     *
     * @param shopSeq          ショップSEQ
     * @param mailTemplateType メールテンプレートタイプ
     */
    protected void checkParameter(Integer shopSeq, HTypeMailTemplateType mailTemplateType) {
        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);
        ArgumentCheckUtil.assertNotNull("mailTemplateType", mailTemplateType);
    }

    /**
     * 指定されたメールテンプレート SEQ のエンティティを取得する。<br />
     *
     * @param shopSeq         ショップSEQ
     * @param mailTemplateSeq メールテンプレートSEQ
     * @return 結果
     */
    @Override
    public MailTemplateEntity execute(Integer shopSeq, Integer mailTemplateSeq) {
        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);
        ArgumentCheckUtil.assertNotNull("mailTemplateSeq", mailTemplateSeq);

        return this.mailTemplateDao.getEntityBySeq(shopSeq, mailTemplateSeq);
    }

    /**
     * 指定されたメールテンプレート種別のエンティティを取得する。<br />
     * 標準のエンティティを優先で取得するが、
     * 標準が見つからない場合は、一般のエンティティを取得する。
     *
     * @param shopSeq          ショップSEQ
     * @param mailTemplateType メールテンプレート種別
     * @return 結果
     */
    @Override
    public MailTemplateEntity execute(Integer shopSeq, HTypeMailTemplateType mailTemplateType) {
        ArgumentCheckUtil.assertNotNull("shopSeq", shopSeq);
        ArgumentCheckUtil.assertNotNull("mailTemplateType", mailTemplateType);

        return this.mailTemplateDao.getEntityByType(shopSeq, mailTemplateType);
    }
}
