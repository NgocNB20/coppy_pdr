/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.impl;

import jp.co.hankyuhanshin.itec.hitmall.dao.inquiry.InquiryGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.shop.inquiry.InquiryGroupGetLogic;
import jp.co.hankyuhanshin.itec.hmbase.util.common.ArgumentCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * お問い合わせ分類取得
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
@Component
public class InquiryGroupGetLogicImpl extends AbstractShopLogic implements InquiryGroupGetLogic {

    /**
     * 問い合わせ分類リスト用valueカラム名
     */
    protected static final String VALUE_COLNAME = "inquirygroupseq";
    /**
     * 問い合わせ分類リスト用ラベルカラム名
     */
    protected static final String LABEL_COLNAME = "inquirygroupname";

    /**
     * お問い合わせ分類Dao
     */
    private final InquiryGroupDao inquiryGroupDao;

    @Autowired
    public InquiryGroupGetLogicImpl(InquiryGroupDao inquiryGroupDao) {
        this.inquiryGroupDao = inquiryGroupDao;
    }

    /**
     * お問い合わせ分類取得
     *
     * @param inquiryGroupSeq お問い合わせ分類SEQ
     * @param shopSeq         ショップSEQ
     * @return お問い合わせ分類
     */
    @Override
    public InquiryGroupEntity execute(Integer inquiryGroupSeq, Integer shopSeq) {

        // パラメータチェック
        ArgumentCheckUtil.assertGreaterThanZero("iconSeq", inquiryGroupSeq);

        // 取得
        return inquiryGroupDao.getEntityByShopSeq(inquiryGroupSeq, shopSeq);
    }

    /**
     * 問い合わせ分類リスト取得ロジック(ショップSEQのみ指定)
     *
     * @param shopSeq ショップSEQ
     * @return お問い合わせ分類エンティティリスト
     */
    @Override
    public Map<String, String> getItemMapList(Integer shopSeq) {

        // 取得
        List<Map<String, Object>> inquiryGroupMapList = inquiryGroupDao.getItemMapList(shopSeq);

        Map<String, String> map = new LinkedHashMap<String, String>();
        if (map != null) {
            for (Map<String, ?> inquiryGroupMap : inquiryGroupMapList) {
                map.put(inquiryGroupMap.get(VALUE_COLNAME).toString(), inquiryGroupMap.get(LABEL_COLNAME).toString());
            }
        }

        return map;
    }
}
