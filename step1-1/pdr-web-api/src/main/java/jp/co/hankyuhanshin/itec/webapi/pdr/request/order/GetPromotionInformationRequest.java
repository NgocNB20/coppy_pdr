/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.webapi.pdr.request.order;

import java.util.List;

// PDR#15 20161115 k-katoh 新規作成

/**
 * PDR#15 WEB-API連携 プロモーションAPIのリクエストモデル<br/>
 *
 * @author k-katoh
 */
public class GetPromotionInformationRequest {

    /** 明細内容 */
    private List<String> detailInfo;

    /**
     * @return the detailInfo
     */
    public List<String> getDetailInfo() {
        return detailInfo;
    }

    /**
     * @param detailInfo the detailInfo to set
     */
    public void setDetailInfo(List<String> detailInfo) {
        this.detailInfo = detailInfo;
    }
}
