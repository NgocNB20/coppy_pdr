/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.campaign.report;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 広告効果測定
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
public class CampaignReportModelItem implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** 連番 */
    public Integer resultNo;

    /** キャンペーンコード */
    public String campaignCode;

    /** キャンペーン名 */
    public String campaignName;

    /** 備考 */
    public String note;

    /** クリック数 */
    public Integer clickCount;

    /** 注文完了数 */
    public Integer orderCount;

    /** 注文完了比率 */
    public String orderCountRatio;

    /** 会員登録数 */
    public Integer memberRegistCount;

    /** 会員登録比率 */
    public String memberRegistCountRatio;

    /** 会員退会数 */
    public Integer memberDeleteCount;

    /** 会員退会比率 */
    public String memberDeleteCountRatio;

    /** 広告コスト */
    public String advertiseCost;

    /** 受注金額 */
    public String orderPrice;

    /** コスト比率 */
    public String advertiseCostRatio;
}
