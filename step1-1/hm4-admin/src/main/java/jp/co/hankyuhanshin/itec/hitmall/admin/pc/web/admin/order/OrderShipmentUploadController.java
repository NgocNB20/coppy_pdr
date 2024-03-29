/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.order;

import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.BatchName;
import jp.co.hankyuhanshin.itec.hitmall.service.order.ShipmentUploadService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvValidationResult;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.InvalidDetail;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.FileOperationUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 出荷アップロード画面コントロール
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/order/shipmentUpload")
@Controller
@SessionAttributes(value = "orderShipmentUploadModel")
@PreAuthorize("hasAnyAuthority('ORDER:8')")
public class OrderShipmentUploadController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderShipmentUploadController.class);

    /**
     * 出荷アップロードHELPER
     */
    private OrderShipmentUploadHelper orderShipmentUploadHelper;

    /**
     * 出荷ファイルアップロードサービス
     */
    private ShipmentUploadService shipmentUploadService;

    /**
     * コンストラクター
     *
     * @param orderShipmentUploadHelper
     * @param shipmentUploadService
     */
    @Autowired
    public OrderShipmentUploadController(OrderShipmentUploadHelper orderShipmentUploadHelper,
                                         ShipmentUploadService shipmentUploadService) {
        this.orderShipmentUploadHelper = orderShipmentUploadHelper;
        this.shipmentUploadService = shipmentUploadService;
    }

    /**
     * 出荷データアップロード画面表示
     *
     * @return
     */
    @GetMapping(value = "/")
    public String doLoadUpload(OrderShipmentUploadModel orderShipmentUploadModel) {

        return "order/shipmentUpload";
    }

    /**
     * 出荷ファイルアップロード処理
     *
     * @param orderShipmentUploadModel
     * @param model
     * @return 遷移先ページ
     */
    @PostMapping(value = "/", params = "doOnceFileUpload")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/order/shipmentUpload/complete/")
    public String doOnceFileUpload(@Validated OrderShipmentUploadModel orderShipmentUploadModel,
                                   BindingResult error,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        if (error.hasErrors()) {
            return "order/shipmentUpload";
        }

        MultipartFile uploaded = orderShipmentUploadModel.getRegistUploadFile();

        // ----------------------------------------------------------------
        // HIT-MALL3では、uploadedファイルがnullであるかのチェックがあったが、
        // 新HIT-MALLの新規作成された @HVMultipartFile を付与することで、
        // フォームのバインディングの際に自動的にチェックを行うはず。
        // ----------------------------------------------------------------
        // ファイルがアップロードされていない場合
        if (uploaded == null) {
            return null;
        }

        // CSVHelper取得
        CsvUtility csvUtility = ApplicationContextUtility.getBean(CsvUtility.class);

        // ファイル操作Helper取得
        FileOperationUtility fileOperationUtility = ApplicationContextUtility.getBean(FileOperationUtility.class);

        // アップロードされたファイルを指定の場所へ移動させる
        String tmpFileName = csvUtility.getBatchTargetFileName("SHIPMENT_REGIST");
        try {
            fileOperationUtility.put(uploaded, tmpFileName);
        } catch (IOException e) {
            // 元ファイルの削除に失敗した場合
            LOGGER.error("例外処理が発生しました", e);
            this.throwMessage(OrderShipmentUploadModel.MSGCD_FAIL_DELETE);
        }
        orderShipmentUploadModel.setSavedTempraryFile(new File(tmpFileName));

        // ----------------------------------------------------------------
        // アップロードされたCSVファイルの検証
        // ----------------------------------------------------------------
        List<Integer> recordCount = new ArrayList<>();
        int limit = CsvUploadResult.CSV_UPLOAD_VALID_LIMIT;
        CsvValidationResult result =
                        this.shipmentUploadService.execute(orderShipmentUploadModel.getSavedTempraryFile(), recordCount,
                                                           limit
                                                          );

        List<OrderShipmentUploadModelItem> resultItems = new ArrayList<>();
        orderShipmentUploadModel.setResultItems(resultItems);

        if (result.isValid()) {

            orderShipmentUploadModel.setRegistCount(recordCount.get(0));
            // 非同期バッチをキックする
            this.doStartBatch(redirectAttributes, model, tmpFileName);
        } else {
            orderShipmentUploadModel.setRegistCount(0);

            for (InvalidDetail detail : result.getInvalidDetailList()) {
                OrderShipmentUploadModelItem item =
                                ApplicationContextUtility.getBean(OrderShipmentUploadModelItem.class);
                item.setRowNumber(detail.getRow());
                item.setColumnLabel(detail.getColumnLabel());
                item.setErrContent(detail.getMessage());

                resultItems.add(item);
            }
            try {
                // NGファイルは削除する
                fileOperationUtility.remove(orderShipmentUploadModel.getSavedTempraryFile());
            } catch (IOException e) {
                // ファイルの削除に失敗した場合
                LOGGER.error("例外処理が発生しました", e);
                this.throwMessage(OrderShipmentUploadModel.MSGCD_FAIL_DELETE);
            }
        }

        return "redirect:/order/shipmentUpload/complete/";
    }

    /**
     * 出荷アップロードタスク登録処理
     */
    private void doStartBatch(RedirectAttributes redirectAttributes, Model model, String tmpFileName) {
        // 非同期バッチをキックする
        try {
            JobLauncher jobLauncher = ApplicationContextUtility.getApplicationContext()
                                                               .getBean("jobLauncherAsync", JobLauncher.class);
            Job job = ApplicationContextUtility.getApplicationContext()
                                               .getBean(BatchName.BATCH_SHIPMENT_REGIST, Job.class);
            String administratorId = this.getCommonInfo().getCommonInfoAdministrator().getAdministratorId();
            Integer shopSeq = this.getCommonInfo().getCommonInfoBase().getShopSeq();
            JobExecution jobExecution = jobLauncher.run(
                            job, new JobParametersBuilder().addString("administratorId", administratorId)
                                                           .addString("shopSeq", shopSeq.toString())
                                                           .addString("targetFilePath", tmpFileName)
                                                           .toJobParameters());

            if (jobExecution.getExitStatus().equals(ExitStatus.STOPPED)) {
                // 異常の場合
                addMessage("AEB000101", new Object[] {"バッチの起動"}, redirectAttributes, model);
            } else {
                // 正常の場合
                addMessage("AEB000102", new Object[] {"バッチの起動"}, redirectAttributes, model);
            }
        } catch (JobParametersInvalidException | JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage("AEB000101", new Object[] {"バッチの起動"}, redirectAttributes, model);
        }
    }

    /**
     * 戻る<br/>
     *
     * @return 受注検索画面
     */
    @PostMapping(value = "/", params = "doReturn")
    public String doReturn() {
        return "redirect:/order/";
    }

    /**
     * 出荷データアップロード終了画面表示
     *
     * @return 出荷データアップロード終了画面
     */
    @GetMapping(value = "/complete/")
    public String doLoadComplete() {

        return "order/shipmentUploadComplete";
    }
}
