/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.inquiry.InquiryDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryUpdateLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 問い合わせ更新ロジック
 *
 * @author shibuya
 * @author Kaneko (itec) 2012/08/21 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class InquiryUpdateLogicImpl extends AbstractShopLogic implements InquiryUpdateLogic {

    /**
     * 問い合わせDao
     */
    private final InquiryDao inquiryDao;

    @Autowired
    public InquiryUpdateLogicImpl(InquiryDao inquiryDao) {
        this.inquiryDao = inquiryDao;
    }

    /**
     * 問い合わせ更新ロジック
     *
     * @param inquiryEntity 問い合わせエンティティ
     * @return 処理件数
     */
    @Override
    public int execute(InquiryEntity inquiryEntity) {

        // パラメータチェック
        this.checkParam(inquiryEntity);

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 更新日に現在日付をセット
        inquiryEntity.setUpdateTime(dateUtility.getCurrentTime());

        // 更新
        return inquiryDao.update(inquiryEntity);
    }

    /**
     * パラメータチェック
     *
     * @param inquiryEntity 問い合わせエンティティ
     */
    protected void checkParam(InquiryEntity inquiryEntity) {
        ArgumentCheckUtil.assertNotNull("inquiryEntity", inquiryEntity);
        ArgumentCheckUtil.assertGreaterThanZero("inquirySeq", inquiryEntity.getInquirySeq());
        ArgumentCheckUtil.assertGreaterThanZero("shopSeq", inquiryEntity.getShopSeq());
        ArgumentCheckUtil.assertNotEmpty("inquiryCode", inquiryEntity.getInquiryCode());
        ArgumentCheckUtil.assertNotEmpty("lastName", inquiryEntity.getInquiryLastName());
        ArgumentCheckUtil.assertNotNull("inquiryTime", inquiryEntity.getInquiryTime());
        ArgumentCheckUtil.assertNotNull("inquiryStatus", inquiryEntity.getInquiryStatus());
        ArgumentCheckUtil.assertGreaterThanZero("inquiryGroupSeq", inquiryEntity.getInquiryGroupSeq());
        // ArgumentCheckUtil.assertGreaterThanZero("administratorSeq",
        // inquiryEntity.getAdministratorSeq());
    }

}
