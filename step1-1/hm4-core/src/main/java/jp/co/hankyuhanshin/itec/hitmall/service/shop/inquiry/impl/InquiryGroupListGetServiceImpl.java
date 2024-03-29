/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquiryGroupSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGroupListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.AbstractShopService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry.InquiryGroupListGetService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 問い合わせ分類リスト取得サービス
 *
 * @author kimura
 * @version $Revision: 1.2 $
 */
@Service
public class InquiryGroupListGetServiceImpl extends AbstractShopService implements InquiryGroupListGetService {

    /**
     * お問い合わせ分類リスト取得処理ロジッククラス
     */
    private final InquiryGroupListGetLogic inquiryGroupListGetLogic;

    @Autowired
    public InquiryGroupListGetServiceImpl(InquiryGroupListGetLogic inquiryGroupListGetLogic) {
        this.inquiryGroupListGetLogic = inquiryGroupListGetLogic;
    }

    /**
     * サービス実行
     *
     * @param なし
     * @return お問い合わせ情報リスト
     */
    @Override
    public List<InquiryGroupEntity> execute() {

        // ショップSEQ ： nullの場合 エラーとして処理を終了する
        ArgumentCheckUtil.assertNotNull("inquiryShopSeq", 1001);

        // 問い合わせ分類情報DAO用検索条件DTO作成
        InquiryGroupSearchForDaoConditionDto inquiryGroupSearchForDaoConditionDto =
                        getComponent(InquiryGroupSearchForDaoConditionDto.class);
        inquiryGroupSearchForDaoConditionDto.setShopSeq(1001);
        inquiryGroupSearchForDaoConditionDto.setOpenStatus(HTypeOpenDeleteStatus.OPEN);

        // 問い合わせ分類情報リスト取得サービス実行
        return inquiryGroupListGetLogic.execute(inquiryGroupSearchForDaoConditionDto);

    }

}
