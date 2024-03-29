package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.totaling.searchkeywordaccording;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.totaling.AccessSearchKeywordTotalSearchForConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.service.totaling.searchkeywordaccording.SearchKeyWordAccordingTotalingCsvOutputService;
import jp.co.hankyuhanshin.itec.hitmall.service.totaling.searchkeywordaccording.SearchKeyWordAccordingTotalingService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.exception.FileDownloadException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * 検索キーワード集計画面用コントロール
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/searchkeywordaccording")
@Controller
@SessionAttributes(value = "searchKeywordAccordingModel")
@PreAuthorize("hasAnyAuthority('REPORT:4')")
public class SearchKeywordAccordingController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchKeywordAccordingController.class);

    /**
     * デフォルト：ページ番号
     */
    private static final String DEFAULT_PNUM = "1";
    /**
     * デフォルト：表示件数
     */
    private static final String DEFAULT_LIMIT = "100";

    /**
     * 検索キーワード集計サービス
     */
    private final SearchKeyWordAccordingTotalingService searchKeyWordAccordingTotalingService;

    /**
     * 検索キーワード集計CSV出力サービス
     */
    public SearchKeyWordAccordingTotalingCsvOutputService searchKeyWordAccordingTotalingCsvOutputService;

    /**
     * 検索キーワード集計Helper
     */
    private final SearchKeywordAccordingHelper searchKeywordAccordingHelper;

    @Autowired
    public SearchKeywordAccordingController(SearchKeyWordAccordingTotalingService searchKeyWordAccordingTotalingService,
                                            SearchKeyWordAccordingTotalingCsvOutputService searchKeyWordAccordingTotalingCsvOutputService,
                                            SearchKeywordAccordingHelper searchKeywordAccordingHelper) {
        this.searchKeyWordAccordingTotalingService = searchKeyWordAccordingTotalingService;
        this.searchKeyWordAccordingTotalingCsvOutputService = searchKeyWordAccordingTotalingCsvOutputService;
        this.searchKeywordAccordingHelper = searchKeywordAccordingHelper;
    }

    /**
     * 初期表示処理を行います
     */
    @GetMapping({"/", "/index.html"})
    public String doLoadIndex(Model model) {

        SearchKeywordAccordingModel searchModel = ApplicationContextUtility.getBean(SearchKeywordAccordingModel.class);
        String[] orderSiteTypeList = new String[] {"0", "1", "2"};

        searchModel.setOrderSiteTypeList(orderSiteTypeList);
        searchModel.setOrderSiteTypeListItems(EnumTypeUtil.getEnumMap(HTypeSiteType.class));

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        // 今月の初日、末日を取得し、集計期間にセット
        Timestamp currentDate = dateUtility.getCurrentDate();
        Timestamp startDate = dateUtility.getStartOfMonth(0, currentDate);
        Timestamp endDate = dateUtility.getEndOfMonth(0, currentDate);

        searchModel.setProcessDateFrom(dateUtility.formatYmdWithSlash(startDate));
        searchModel.setProcessDateTo(dateUtility.formatYmdWithSlash(endDate));

        model.addAttribute("searchKeywordAccordingModel", searchModel);

        return "totaling/index";
    }

    /**
     * 検索キーワード集計処理を実行します
     *
     * @param searchKeywordAccordingModel
     * @param error
     * @param pnum
     * @param limit
     * @param redirectAttributes
     * @param model
     * @return Class&lt;IndexPage&gt;
     */
    @PostMapping(value = "/", params = "doReportOutput")
    @HEHandler(exception = AppLevelListException.class, returnView = "totaling/index")
    public String doReportOutput(@Validated SearchKeywordAccordingModel searchKeywordAccordingModel,
                                 BindingResult error,
                                 @RequestParam(required = false, defaultValue = DEFAULT_PNUM) String pnum,
                                 @RequestParam(required = false, defaultValue = DEFAULT_LIMIT) int limit,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        if (error.hasErrors()) {
            return "totaling/index";
        }

        // ブラウザバック遷移時の対応処理
        //        this.processForBrowseBack();

        // PageをDtoに変換
        AccessSearchKeywordTotalSearchForConditionDto conditionDto =
                        ApplicationContextUtility.getBean(AccessSearchKeywordTotalSearchForConditionDto.class);
        searchKeywordAccordingHelper.convertToAccessSearchKeywordTotalSearchForConditionDtoForSearch(
                        searchKeywordAccordingModel, conditionDto);

        // PageInfoHelper取得
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(conditionDto, pnum, limit);

        // 集計処理を実施
        List<AccessSearchKeywordTotalDto> resultDataItem = searchKeyWordAccordingTotalingService.execute(conditionDto);

        if ((resultDataItem != null) && (resultDataItem.size() > 0)) {
            /* 検索結果総件数が表示件数設定値より多い場合はメッセージを設定し結果件数を表示件数にトリム */
            if (resultDataItem.size() < conditionDto.getTotalCount()) {
                // メッセージを設定
                addMessage("ARX000101", new Object[] {limit}, redirectAttributes, model);
            }
        }

        // 検索結果リストを設定
        searchKeywordAccordingModel.setResultDataItems(resultDataItem);
        model.addAttribute("searchKeywordAccordingModel", searchKeywordAccordingModel);

        return "totaling/index";
    }

    /**
     * 検索キーワード集計CSV出力処理を実行します
     *
     * @param searchKeywordAccordingModel
     * @return
     */
    @PreAuthorize("hasAnyAuthority('REPORT:8')")
    @PostMapping(value = "/", params = "doCsvOutput")
    @HEHandler(exception = AppLevelListException.class, returnView = "totaling/index")
    @HEHandler(exception = FileDownloadException.class, returnView = "totaling/index")
    public void doCsvOutput(@Validated SearchKeywordAccordingModel searchKeywordAccordingModel,
                            BindingResult error,
                            HttpServletResponse response,
                            Model model) {

        if (error.hasErrors()) {
            throw new FileDownloadException(model);
        }

        // 検索条件作成
        AccessSearchKeywordTotalSearchForConditionDto conditionDto =
                        ApplicationContextUtility.getBean(AccessSearchKeywordTotalSearchForConditionDto.class);
        searchKeywordAccordingHelper.convertToAccessSearchKeywordTotalSearchForConditionDtoForSearch(
                        searchKeywordAccordingModel, conditionDto);

        try {
            searchKeyWordAccordingTotalingCsvOutputService.execute(conditionDto, response);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            throw new FileDownloadException(model);
        }

    }
}
