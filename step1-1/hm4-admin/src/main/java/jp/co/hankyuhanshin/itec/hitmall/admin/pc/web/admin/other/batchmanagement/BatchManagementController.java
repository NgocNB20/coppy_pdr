package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.other.batchmanagement;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.DisplayChangeGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.SearchGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.other.batchmanagement.validator.group.ExecuteGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.PageInfoHelper;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchRunType;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.dto.BatchManagementDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.dto.BatchManagementSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.service.BatchManagementGetListDetailService;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeManualBatch;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * バッチ管理画面コントローラー。<br />
 *
 * <pre>
 * 検索条件を元に検索結果を一覧表示する。
 * </pre>
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@RequestMapping("/batchmanagement")
@Controller
@SessionAttributes(value = "batchManagementModel")
@PreAuthorize("hasAnyAuthority('BATCH:4')")
public class BatchManagementController extends AbstractController {

    /**
     * バッチ検索：デフォルトページ番号
     */
    private static final String DEFAULT_BATCHSEARCH_PNUM = "1";

    /**
     * バッチ管理Helper
     */
    private final BatchManagementHelper helper;

    /**
     * バッチ管理検索一覧サービス
     */
    private final BatchManagementGetListDetailService service;

    /**
     * SpringBatchジョッブランチャー
     */
    private final JobLauncher jobLauncher;

    /**
     * 日付のフォーマット
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @Autowired
    public BatchManagementController(BatchManagementHelper helper, BatchManagementGetListDetailService service) {
        this.helper = helper;
        this.service = service;
        this.jobLauncher = ApplicationContextUtility.getApplicationContext()
                                                    .getBean("jobLauncherAsync", JobLauncher.class);
    }

    /**
     * 初期処理<br/>
     *
     * @param batchManagementModel バッチ管理画面モデル
     * @param model                Model
     * @return 検索一覧画面
     */
    @GetMapping("/")
    public String doLoad(BatchManagementModel batchManagementModel, Model model) {

        clearModel(BatchManagementModel.class, batchManagementModel, model);

        Map<String, String> batchtypesItems = EnumTypeUtil.getEnumMap(HTypeBatchName.class);
        batchtypesItems.remove(HTypeBatchName.BATCH_JOB_MONITORING.getValue());

        batchManagementModel.setBatchtypesItems(batchtypesItems);
        batchManagementModel.setTaskstatusesItems(EnumTypeUtil.getEnumMap(HTypeBatchStatus.class));
        batchManagementModel.setManualBatchItems(EnumTypeUtil.getEnumMap(HTypeManualBatch.class));

        return "other/batchmanagement/index";
    }

    /**
     * バッチタスク情報検索処理を実行します
     *
     * @param batchManagementModel BatchManagementModel
     * @param error                BindingResult
     * @param redirectAttributes   RedirectAttributes
     * @param model                Model
     * @return 検索一覧画面
     */
    @PostMapping(value = "/", params = "doSearch")
    @HEHandler(exception = AppLevelListException.class, returnView = "other/batchmanagement/index")
    public String doSearch(@Validated(SearchGroup.class) BatchManagementModel batchManagementModel,
                           BindingResult error,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        if (error.hasErrors()) {
            return "other/batchmanagement/index";
        }

        // ページ番号を初期設定
        batchManagementModel.setPageNumber(DEFAULT_BATCHSEARCH_PNUM);

        search(batchManagementModel, model);

        return "other/batchmanagement/index";
    }

    /**
     * ページング処理を行います
     *
     * @param batchManagementModel BatchManagementModel
     * @param error                BindingResult
     * @param model                Model
     * @return 検索一覧画面
     */
    @PostMapping(value = "/", params = "doDisplayChange")
    public String doDisplayChange(@Validated(DisplayChangeGroup.class) BatchManagementModel batchManagementModel,
                                  BindingResult error,
                                  Model model) {
        if (error.hasErrors()) {
            return "other/batchmanagement/index";
        }

        search(batchManagementModel, model);

        return "other/batchmanagement/index";
    }

    /**
     * バッチタスク詳細情報を検索します
     *
     * @param batchManagementModel BatchManagementModel
     * @param model                Model
     * @return レポート
     */
    @PostMapping(value = "/", params = "doReport")
    public String doReport(BatchManagementModel batchManagementModel, Model model) {
        BatchManagementReportItem batchManagementReportItem = helper.getBatchManagementReportItem(batchManagementModel);

        model.addAttribute("batchManagementReportItem", batchManagementReportItem);
        return "other/batchmanagement/report";
    }

    /**
     * バッチタスク詳細情報を検索します
     *
     * @param batchManagementModel BatchManagementModel
     * @param model                Model
     * @return レポート
     */
    @PostMapping(value = "/doReportAjax")
    @ResponseBody
    public ResponseEntity<?> doReportAjax(BatchManagementModel batchManagementModel, Model model) {
        BatchManagementReportItem batchManagementReportItem = helper.getBatchManagementReportItem(batchManagementModel);

        batchManagementReportItem.setAccepttimeStr(convertTime(batchManagementReportItem.getAccepttime()));
        batchManagementReportItem.setTerminatetimeStr(convertTime(batchManagementReportItem.getTerminatetime()));

        return ResponseEntity.ok(batchManagementReportItem);
    }

    /**
     * バッチの手動起動処理を実行します
     */
    @PreAuthorize("hasAnyAuthority('BATCH:8')")
    @PostMapping(value = "/", params = "doExecute")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    @HEHandler(exception = AppLevelListException.class, returnView = "other/batchmanagement/index")
    public String doExecute(@Validated(ExecuteGroup.class) BatchManagementModel batchManagementModel,
                            BindingResult error,
                            RedirectAttributes redirectAttributes,
                            Model model)
                    throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException,
                    JobInstanceAlreadyCompleteException {

        if (error.hasErrors()) {
            return "other/batchmanagement/index";
        }

        if (batchManagementModel.getManualBatch() == null) {
            throwMessage("AGS000105", new Object[] {"手動起動バッチ名"});
        } else {
            // バッチ名からバッチのジョッブを取得する
            Job job = ApplicationContextUtility.getApplicationContext()
                                               .getBean(batchManagementModel.getManualBatch(), Job.class);
            String administratorId = this.getCommonInfo().getCommonInfoAdministrator().getAdministratorId();
            String shopSeq = this.getCommonInfo().getCommonInfoBase().getShopSeq().toString();

            // バッチのジョッブを起動する
            JobExecution jobExecution = jobLauncher.run(
                            job, new JobParametersBuilder().addString("administratorId", administratorId)
                                                           .addString("shopSeq", shopSeq)
                                                           .addLong("timestamp", System.currentTimeMillis())
                                                           .addString("batchRunType", BatchRunType.MANUAL)
                                                           .toJobParameters());

            if (jobExecution.getExitStatus().equals(ExitStatus.STOPPED)) {
                // 個別のPageのmessage属性をメッセージとして出力するやり方は標準的ではないが、このつくりとなった背景が不明のため
                addMessage("AEB000101", new String[] {"バッチの手動起動"}, redirectAttributes, model);
            } else {
                // 個別のPageのmessage属性をメッセージとして出力するやり方は標準的ではないが、このつくりとなった背景が不明のため
                addMessage("AEB000102", new String[] {"バッチの手動起動"}, redirectAttributes, model);
            }
        }

        // 検索結果再初期化
        batchManagementModel.setResultItems(null);

        return "other/batchmanagement/index";
    }

    /**
     * 検索処理
     *
     * @param batchManagementModel BatchManagementModel
     * @param model                Model
     */
    private void search(BatchManagementModel batchManagementModel, Model model) {
        BatchManagementSearchConditionDto searchConditionDto =
                        helper.convertToBatchManagementSearchConditionDto(batchManagementModel);

        // ページング検索セットアップ
        PageInfoHelper pageInfoHelper = ApplicationContextUtility.getBean(PageInfoHelper.class);
        pageInfoHelper.setupPageInfo(
                        searchConditionDto, batchManagementModel.getPageNumber(), batchManagementModel.getLimit());

        List<BatchManagementDetailDto> dtoList = service.execute(searchConditionDto);

        List<BatchManagementReportItem> resultItems = new ArrayList<>();
        for (BatchManagementDetailDto detailDto : dtoList) {
            BatchManagementReportItem reportItem = helper.convertToBatchManagementReportItem(detailDto);
            resultItems.add(reportItem);
        }

        batchManagementModel.setResultItems(resultItems);

        // ページャーセットアップ
        pageInfoHelper.setupViewPager(searchConditionDto, batchManagementModel);
    }

    /**
     * 日付のフォーマット
     *
     * @param ts
     * @return String formattedDate
     */
    private String convertTime(Timestamp ts) {
        if (ts == null) {
            return null;
        }
        return ts.toLocalDateTime().format(FORMATTER);
    }
}

