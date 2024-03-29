/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.service.shop.inquiry;

import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;

/**
 * 問い合わせ分類更新
 *
 * @author shibuya
 * @version $Revision: 1.1 $
 */
public interface InquiryGroupUpdateService {

    /* メッセージ SSI0010 */
    /**
     * 問い合わせ分類更新失敗エラー<br/>
     * <code>MSGCD_INQUIRYGROUP_UPDATE_FAIL</code>
     */
    public static final String MSGCD_INQUIRYGROUP_UPDATE_FAIL = "SSI001001";

    /**
     * 問い合わせ分類更新
     *
     * @param inquiryGroupEntity 問い合わせ分類
     * @return 処理件数
     */
    int execute(InquiryGroupEntity inquiryGroupEntity);

}
