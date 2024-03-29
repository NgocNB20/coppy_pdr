/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry;

import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;

import java.util.List;

/**
 * お問い合わせ分類リスト取得サービス(管理者用)
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface InquiryGroupListGetForBackService {

    /**
     * お問い合わせ分類リスト取得サービス(管理者用)
     *
     * @return お問い合わせ分類リスト
     */
    List<InquiryGroupEntity> execute();

}
