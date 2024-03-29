/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.inquiry.InquiryGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.inquiry.InquiryGroupSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGroupListGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 問い合わせ分類リスト取得ロジック
 *
 * @author kimura
 * @version $Revision: 1.4 $
 */
@Component
public class InquiryGroupListGetLogicImpl extends AbstractShopLogic implements InquiryGroupListGetLogic {

    /**
     * お問い合わせ分類情報取得Dao
     */
    private final InquiryGroupDao inquiryGroupDao;

    @Autowired
    public InquiryGroupListGetLogicImpl(InquiryGroupDao inquiryGroupDao) {
        this.inquiryGroupDao = inquiryGroupDao;
    }

    /**
     * ロジック実行<br/>
     *
     * @param inquiryGroupSearchForDaoConditionDto 問い合わせ分類Dao用検索条件DTO
     * @return お問い合わせ分類エンティティリスト
     */
    @Override
    public List<InquiryGroupEntity> execute(InquiryGroupSearchForDaoConditionDto inquiryGroupSearchForDaoConditionDto) {

        // 問い合わせ分類エンティティＤＴＯ ： NULLの場合 エラーとして処理を終了する
        ArgumentCheckUtil.assertNotNull("問い合わせ分類情報が取得できません", inquiryGroupSearchForDaoConditionDto);
        // 問い合わせ分類エンティティＤＴＯ ： NULLの場合 エラーとして処理を終了する
        ArgumentCheckUtil.assertNotNull("ショップ情報が取得できません", inquiryGroupSearchForDaoConditionDto.getShopSeq());
        ArgumentCheckUtil.assertNotNull("公開情報が取得できません", inquiryGroupSearchForDaoConditionDto.getOpenStatus());

        return inquiryGroupDao.getInquiryGroupList(inquiryGroupSearchForDaoConditionDto);
    }

    /**
     * 問い合わせ分類リスト取得ロジック(ショップSEQのみ指定)
     *
     * @param shopSeq ショップSEQ
     * @return お問い合わせ分類エンティティリスト
     */
    @Override
    public List<InquiryGroupEntity> execute(Integer shopSeq) {

        // 問題なければ検索
        return inquiryGroupDao.getInquiryGroupEntityList(shopSeq);
    }

}
