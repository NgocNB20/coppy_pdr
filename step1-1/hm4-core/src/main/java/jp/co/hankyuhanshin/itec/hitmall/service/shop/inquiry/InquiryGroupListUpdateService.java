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
 * 問い合わせ分類リスト更新
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface InquiryGroupListUpdateService {

    /**
     * 問い合わせ分類リスト更新
     *
     * @param inquiryGroupEntityList 問い合わせ分類リスト
     * @return 処理件数
     */
    int execute(List<InquiryGroupEntity> inquiryGroupEntityList);

}
