/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.campaign.report;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRDateGreaterEqual;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.dto.access.AccessInfoSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hmbase.constant.RegularExpressionsConstants;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * 広告効果測定
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@Data
@HVRDateGreaterEqual(target = "campaignDateTo", comparison = "campaignDateFrom")
public class CampaignReportModel extends AbstractModel {

    /** 正規表現エラー:ASC000412W */
    public static final String MSGCD_REGULAR_EXPRESSION_ERR = "{ASC000412W}";

    /** 検索条件 */
    public AccessInfoSearchForDaoConditionDto conditionDto;

    /** 広告効果リスト */
    public List<CampaignReportModelItem> resultItems;

    /** 広告コード */
    private String campaignCodeSort;

    /** 広告名 */
    private String campaignNameSort;

    /** メモ */
    @HVSpecialCharacter(allowPunctuation = true)
    private String noteSort;

    /** 連番 */
    private Integer resultNo;

    /** キャンペーンコード */
    @Pattern(regexp = RegularExpressionsConstants.CAMPAIGN_CODE_REGEX, message = MSGCD_REGULAR_EXPRESSION_ERR)
    private String campaignCode;

    /** キャンペーン名 */
    @HVSpecialCharacter(allowPunctuation = true)
    private String campaignName;

    /** 備考 */
    @HVSpecialCharacter(allowPunctuation = true)
    private String note;

    /** クリック数 */
    @HCNumber
    private Integer clickCount;

    /** 注文完了数 */
    @HCNumber
    private Integer orderCount;

    /** 注文完了比率 */
    private String orderCountRatio;

    /** 会員登録数 */
    @HCNumber
    private Integer memberRegistCount;

    /** 会員登録比率 */
    private String memberRegistCountRatio;

    /** 会員退会数 */
    @HCNumber
    private Integer memberDeleteCount;

    /** 会員退会比率 */
    private String memberDeleteCountRatio;

    /** 広告コスト */
    private String advertiseCost;

    /** 受注金額 */
    private String orderPrice;

    /** コスト比率 */
    private String advertiseCostRatio;

    /** 期間(From) */
    @HVDate
    @HCDate
    private String campaignDateFrom;

    /** 期間(To) */
    @HVDate
    @HCDate
    private String campaignDateTo;

    /** CSVダウンロード件数限界値 */
    private Integer csvLimit;

    /**
     * ページ番号<br/>
     */
    private String pageNumber;

    /**
     * 最大表示件数<br/>
     */
    private int limit;

    /**
     * ソート項目<br/>
     */
    private String orderField;

    /**
     * ソート条件<br/>
     */
    private boolean orderAsc;

    /**
     * 検索結果総件数<br/>
     */
    private int totalCount;

    /************************************
     **  条件判定
     ************************************/
    /**
     * 検索結果表示判定<br/>
     *
     * @return true=検索結果がnull以外(0件リスト含む), false=検索結果がnull
     */
    public boolean isResult() {
        return getResultItems() != null;
    }

}
