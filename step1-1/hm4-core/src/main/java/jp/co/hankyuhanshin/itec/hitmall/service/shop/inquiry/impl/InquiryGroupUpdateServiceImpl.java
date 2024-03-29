/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.impl;

import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGroupUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquiryGroupUpdateService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 問い合わせ分類更新
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
@Service
public class InquiryGroupUpdateServiceImpl extends AbstractShopService implements InquiryGroupUpdateService {

    /**
     * 問い合わせ更新ロジック
     */
    private final InquiryGroupUpdateLogic inquiryGroupUpdateLogic;

    @Autowired
    public InquiryGroupUpdateServiceImpl(InquiryGroupUpdateLogic inquiryGroupUpdateLogic) {
        this.inquiryGroupUpdateLogic = inquiryGroupUpdateLogic;
    }

    /**
     * 問い合わせ分類更新
     *
     * @param inquiryGroupEntity 問い合わせ分類
     * @return 処理件数
     */
    @Override
    public int execute(InquiryGroupEntity inquiryGroupEntity) {
        // パラメータチェック
        this.checkParam(inquiryGroupEntity);

        // 更新処理
        int ret = inquiryGroupUpdateLogic.execute(inquiryGroupEntity);
        if (ret == 0) {
            throwMessage(MSGCD_INQUIRYGROUP_UPDATE_FAIL);
        }

        return ret;
    }

    /**
     * パラメータチェック
     *
     * @param inquiryGroupEntity 問い合わせ分類
     */
    public void checkParam(InquiryGroupEntity inquiryGroupEntity) {
        ArgumentCheckUtil.assertGreaterThanZero("shopSeq", inquiryGroupEntity.getShopSeq());
        ArgumentCheckUtil.assertGreaterThanZero("inquiryGroupSeq", inquiryGroupEntity.getInquiryGroupSeq());
        ArgumentCheckUtil.assertNotEmpty("inquiryGroupName", inquiryGroupEntity.getInquiryGroupName());
        ArgumentCheckUtil.assertNotNull("openStatus", inquiryGroupEntity.getOpenStatus());
    }

}
