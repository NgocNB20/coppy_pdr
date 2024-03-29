/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGroupRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquiryGroupRegistService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 問い合わせ分類登録
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
@Service
public class InquiryGroupRegistServiceImpl extends AbstractShopService implements InquiryGroupRegistService {

    /**
     * 問い合わせ分類登録ロジック
     */
    private final InquiryGroupRegistLogic inquiryGroupRegistLogic;

    @Autowired
    public InquiryGroupRegistServiceImpl(InquiryGroupRegistLogic inquiryGroupRegistLogic) {
        this.inquiryGroupRegistLogic = inquiryGroupRegistLogic;
    }

    /**
     * 問い合わせ分類登録
     *
     * @param inquiryGroupEntity 問い合わせ分類
     * @return 処理件数
     */
    @Override
    public int execute(InquiryGroupEntity inquiryGroupEntity) {

        // パラメータチェック
        this.checkParam(inquiryGroupEntity);
        // 共通情報チェック
        Integer shopSeq = 1001;

        // 共通情報セット
        inquiryGroupEntity.setShopSeq(shopSeq);

        // 登録処理
        int ret = inquiryGroupRegistLogic.execute(inquiryGroupEntity);
        if (ret == 0) {
            throwMessage(MSGCD_INQUIRYGROUP_REGIST_FAIL);
        }

        return ret;
    }

    /**
     * パラメータチェック
     *
     * @param inquiryGroupEntity 問い合わせ分類
     */
    public void checkParam(InquiryGroupEntity inquiryGroupEntity) {
        ArgumentCheckUtil.assertNotEmpty("inquiryGroupName", inquiryGroupEntity.getInquiryGroupName());
        ArgumentCheckUtil.assertNotNull("openStatus", inquiryGroupEntity.getOpenStatus());
    }

}
