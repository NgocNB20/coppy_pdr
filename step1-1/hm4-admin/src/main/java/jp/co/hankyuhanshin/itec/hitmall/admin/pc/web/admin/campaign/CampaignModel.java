package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.campaign;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.campaign.CampaignSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hmbase.constant.RegularExpressionsConstants;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * 広告検索
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 *
 */
@Data
public class CampaignModel extends AbstractModel {
    /**
     * 正規表現エラー
     */
    public static final String MSGCD_REGULAR_EXPRESSION_ERR = "{AAX000101W}";

    /**
     * ページ番号
     */
    private String pageNumber;

    /**
     * 最大表示件数
     */
    private int limit;

    /**
     * ソート項目
     */
    private String orderField;

    /**
     * ソート条件
     */
    private boolean orderAsc;

    /**
     * 検索結果総件数
     */
    private int totalCount;

    /** 検索条件 */
    private CampaignSearchForDaoConditionDto conditionDto;

    /** 検索結果一覧 */
    private List<CampaignModelItem> resultItems;

    /** 検索条件キャンペーンコード */
    @Pattern(regexp = RegularExpressionsConstants.CAMPAIGN_CODE_REGEX, message = MSGCD_REGULAR_EXPRESSION_ERR)
    private String campaignCodeCondition;

    /** 検索条件キャンペーン名 */
    @HVSpecialCharacter(allowPunctuation = true)
    private String campaignNameCondition;

    /** 検索条件備考 */
    @HVSpecialCharacter(allowPunctuation = true)
    private String noteCondition;

    /** 連番 */
    private Integer resultNo;

    /** キャンペーンコード */
    private String campaignCode;

    /** キャンペーン名 */
    private String campaignName;

    /** 備考 */
    private String note;

    /** 広告コードソート */
    private String campaignCodeSort;

    /** 広告名ソート */
    private String campaignNameSort;

    /** メモソート */
    private String noteSort;

    /**
     *  CSVダウンロード件数限界値
     */
    private Integer csvLimit;

    /**
     * 検索結果表示判定<br/>
     *
     * @return true=検索結果がnull以外(0件リスト含む), false=検索結果がnull
     */
    public boolean isResult() {
        return getResultItems() != null;
    }
}
