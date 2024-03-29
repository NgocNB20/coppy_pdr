package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.totaling.searchkeywordaccording;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractModel;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.converter.HCNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVDate;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVItems;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVNumber;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRDateGreaterEqual;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVRNumberGreaterEqual;
import jp.co.hankyuhanshin.itec.hitmall.annotation.validator.HVSpecialCharacter;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalDto;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 検索キーワード集計ページクラス。<br />
 *
 * <pre>
 * 検索キーワード集計画面表示項目。
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Data
@Component
@Scope("prototype")
@HVRDateGreaterEqual(target = "processDateTo", comparison = "processDateFrom")
@HVRNumberGreaterEqual(target = "searchResultCountTo", comparison = "searchResultCountFrom")
public class SearchKeywordAccordingModel extends AbstractModel {

    /**
     * 検索条件 期間－From
     */
    @NotEmpty
    @HVDate
    @HCDate
    private String processDateFrom;

    /**
     * 検索条件 期間－To
     */
    @NotEmpty
    @HVDate
    @HCDate
    private String processDateTo;

    /**
     * 検索条件 受付場所
     */
    @NotEmpty
    @HVItems(target = HTypeSiteType.class)
    private String[] orderSiteTypeList;

    /**
     * ラベル 受付場所
     */
    private Map<String, String> orderSiteTypeListItems;

    /**
     * 検索条件 キーワード
     */
    @HVSpecialCharacter(allowPunctuation = true)
    private String keyword;

    /**
     * 検索条件 検索回数－From
     */
    @HVNumber
    @Digits(integer = 10, fraction = 0)
    @HCNumber
    private String searchResultCountFrom;

    /**
     * 検索条件 検索回数－To
     */
    @HVNumber
    @Digits(integer = 10, fraction = 0)
    @HCNumber
    private String searchResultCountTo;

    /**
     * 検索条件 ショップSEQ
     */
    private Integer shopSeq;

    /**
     * ソート条件
     */
    private String orderByCondition;

    /**
     * 検索結果 リスト
     */
    private List<AccessSearchKeywordTotalDto> resultDataItems;

    /**
     * 検索結果 オブジェクト
     */
    private AccessSearchKeywordTotalDto resultData;

    /**
     * 検索結果 行番号
     */
    private int resultDataIndex;

    /**
     * 検索条件 キーワード
     */
    private String searchKeyword;

    /**
     * 検索結果 検索回数
     */
    @HCNumber
    private Integer searchCount;

    /**
     * 検索結果 検索結果数
     */
    private BigDecimal searchResultCount;

    /**
     * 検索結果 合計行フラグ
     */
    private boolean totalFlag = false;

    /**
     * 行のClass
     */
    private String lineClass;

    /**
     * Csv出力限界値<br/>
     * ※デフォルト-1=無制限とする
     */
    private Integer csvLimit;

    /**
     * @return the resultDataIndex
     */
    public int getResultDataIndex() {
        return resultDataIndex + 1;
    }

    /* 画面表示判定 */

    /**
     * 検索結果表示判定<br/>
     *
     * @return true=検索結果がnull以外(0件リスト含む), false=検索結果がnull
     */
    public boolean isResult() {
        return getResultDataItems() != null;
    }
}
