/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.bundledupload;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.UploadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ZipUploadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.goods.bundledupload.validation.GoodsBundleUploadValidator;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.BatchName;
import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvReaderOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.goods.GoodsImageZipFileUploadService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CsvUtility;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvReaderUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvValidationResult;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.FileOperationUtility;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
 * 商品管理 商品一括アップロード<br/>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@RequestMapping("/goods/bundledupload")
@Controller
@SessionAttributes(value = "goodsBundledUploadModel")
@PreAuthorize("hasAnyAuthority('GOODS:8')")
public class GoodsBundledUploadController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsBundledUploadController.class);

    /**
     * Helper<br/>
     */
    private final GoodsBundledUploadHelper goodsBundledUploadIndexHelper;

    /**
     * 商品画像zip一括アップロードサービス<br/>
     */
    private final GoodsImageZipFileUploadService goodsImageZipFileUploadService;

    /**
     * 商品一括アップロード画面の動的バリデータ
     */
    private final GoodsBundleUploadValidator goodsBundleUploadValidator;

    /**
     * コンストラクタ
     *
     * @param goodsBundledUploadIndexHelper
     * @param goodsImageZipFileUploadService
     * @param goodsBundleUploadValidator
     */
    @Autowired
    public GoodsBundledUploadController(GoodsBundledUploadHelper goodsBundledUploadIndexHelper,
                                        GoodsImageZipFileUploadService goodsImageZipFileUploadService,
                                        GoodsBundleUploadValidator goodsBundleUploadValidator) {
        this.goodsBundledUploadIndexHelper = goodsBundledUploadIndexHelper;
        this.goodsImageZipFileUploadService = goodsImageZipFileUploadService;
        this.goodsBundleUploadValidator = goodsBundleUploadValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder error) {
        // 商品一括アップロード画面の動的バリデータをセット
        error.addValidators(goodsBundleUploadValidator);
    }

    /**
     * 商品一括アップロード画面表示(CSV)
     *
     * @param goodsBundledUploadModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @GetMapping(value = "/csv/")
    protected String doLoadCsv(GoodsBundledUploadModel goodsBundledUploadModel,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        // アップロードモードのデフォルト値を設定
        goodsBundledUploadModel.setUploadType("0");

        return "goods/bundledupload/csvUpload";

    }

    /**
     * 商品一括アップロード画面表示(画像)
     *
     * @param goodsBundledUploadModel
     * @param redirectAttributes
     * @param model
     * @return
     */
    @GetMapping(value = "/image/")
    protected String doLoadImage(GoodsBundledUploadModel goodsBundledUploadModel,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        // アップロード対象のデフォルト値を設定
        goodsBundledUploadModel.setZipImageTarget("0");

        return "goods/bundledupload/imageUpload";

    }

    /**
     * 商品一括アップロード処理実行<br/>
     *
     * @param goodsBundledUploadModel
     * @return 一括アップロード結果画面／自画面
     */
    @PostMapping(value = "/csv/", params = "doOnceUpload")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/goods/bundledupload/csvUploadComplete/")
    public String doOnceUpload(@Validated(UploadGroup.class) GoodsBundledUploadModel goodsBundledUploadModel,
                               BindingResult error,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        if (error.hasErrors()) {
            return "goods/bundledupload/csvUpload";
        }

        goodsBundledUploadModel.setErrorResultItems(new ArrayList<>());
        // フラグ初期化を行う
        initFlag(goodsBundledUploadModel);

        // CsvUtility取得
        CsvUtility csvUtility = ApplicationContextUtility.getBean(CsvUtility.class);

        // アップロード商品データファイルをテンプファイルとして保存
        String tmpFileName = csvUtility.getBatchTargetFileName("GOODS_RESULT");
        String putFileStr = putFile(goodsBundledUploadModel.getUploadCsvFile(), tmpFileName, redirectAttributes, model);

        if (StringUtils.isNotEmpty(putFileStr)) {
            return putFileStr;
        }

        // 非同期バッチをキックする
        doStartBatch(tmpFileName, goodsBundledUploadModel, redirectAttributes, model);

        // 正常終了時
        return "redirect:/goods/bundledupload/csvUploadComplete/";

    }

    /**
     * 商品部分アップロード処理実行<br/>
     *
     * @param goodsBundledUploadModel
     * @return 一括アップロード結果画面／自画面
     */
    @PostMapping(value = "/csv/", params = "doOncePartialUpload")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    @HEHandler(exception = AppLevelListException.class, returnView = "redirect:/goods/bundledupload/csvUploadComplete/")
    public String doOncePartialUpload(@Validated(UploadGroup.class) GoodsBundledUploadModel goodsBundledUploadModel,
                                      BindingResult error,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {

        if (error.hasErrors()) {
            return "goods/bundledupload/csvUpload";
        }

        // ヘッダ行のバリデートを回避するだけのため、doイベントはそのまま呼ぶ。
        return doOnceUpload(goodsBundledUploadModel, error, redirectAttributes, model);
    }

    /**
     * 戻り遷移(CSV)<br/>
     *
     * @return 遷移元画面
     */
    @PostMapping(value = "/csv/", params = "doReturn")
    public String doCsvReturn() {
        return "redirect:/goods/";
    }

    /**
     * 戻り遷移(Image)<br/>
     *
     * @return 遷移元画面
     */
    @PostMapping(value = "/image/", params = "doReturn")
    public String doImageReturn() {
        return "redirect:/goods/";
    }

    /**
     * Zipファイルのアップロード処理を実行します
     *
     * @param goodsBundledUploadModel
     * @param error
     * @return String
     */
    @PostMapping(value = "/image/", params = "doOnceZipUpload")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    @HEHandler(exception = AppLevelListException.class, returnView = "goods/bundledupload/imageUpload")
    public String doOnceZipUpload(@Validated(ZipUploadGroup.class) GoodsBundledUploadModel goodsBundledUploadModel,
                                  BindingResult error,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        if (error.hasErrors()) {
            return "goods/bundledupload/imageUpload";
        }

        String zipImageTarget = goodsBundledUploadModel.getZipImageTarget();
        int fileListSize = goodsImageZipFileUploadService.execute(goodsBundledUploadModel.getZipImageFile(),
                                                                  zipImageTarget
                                                                 );
        goodsBundledUploadModel.setUploadFileCount(fileListSize);

        // その他画像でない
        if (!"1".equals(zipImageTarget)) {
            // 非同期バッチをキックする
            try {
                JobLauncher jobLauncher = ApplicationContextUtility.getApplicationContext()
                                                                   .getBean("jobLauncherAsync", JobLauncher.class);
                Job job = ApplicationContextUtility.getApplicationContext()
                                                   .getBean(BatchName.BATCH_GOODSIMAGE_UPDATE, Job.class);
                String administratorId = this.getCommonInfo().getCommonInfoAdministrator().getAdministratorId();
                Integer shopSeq = this.getCommonInfo().getCommonInfoBase().getShopSeq();
                JobExecution jobExecution = jobLauncher.run(job, new JobParametersBuilder().addString("administratorId",
                                                                                                      administratorId
                                                                                                     )
                                                                                           .addString(
                                                                                                           "shopSeq",
                                                                                                           shopSeq.toString()
                                                                                                     )
                                                                                           .addLong(
                                                                                                           "time",
                                                                                                           System.currentTimeMillis()
                                                                                                   )
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

        return "redirect:/goods/bundledupload/imageUploadComplete/";
    }

    /**
     * フラグ初期化<br/>
     */
    protected void initFlag(GoodsBundledUploadModel goodsBundledUploadModel) {
        goodsBundledUploadModel.setNormalEndMsg(false);
        goodsBundledUploadModel.setValidationEndMsg(false);
        goodsBundledUploadModel.setErrorEndMeg(false);
    }

    /**
     * ファイル移動処理<br/>
     *
     * @param src 移動元ファイル
     * @param dst 移動先ファイル名
     */
    protected String putFile(final MultipartFile src,
                             final String dst,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        // ファイル操作Utility取得
        FileOperationUtility fileOperationUtility = ApplicationContextUtility.getBean(FileOperationUtility.class);
        try {
            fileOperationUtility.put(src, dst);
        } catch (IOException e) {
            LOGGER.error("例外処理が発生しました", e);
            addMessage(GoodsBundledUploadModel.MSGCD_FAIL_DELETE, redirectAttributes, model);
            return "redirect:/goods/bundledupload/csv/";
        }
        return null;
    }

    /**
     * 商品一括アップロードタスク登録処理
     *
     * @param tmpFileName
     * @param goodsBundledUploadModel
     * @param redirectAttributes
     * @param model
     */
    private void doStartBatch(String tmpFileName,
                              GoodsBundledUploadModel goodsBundledUploadModel,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        // --------------------------------------------------------------------
        // アップロードされた商品CSVの事前バリデーション
        // --------------------------------------------------------------------
        CsvUploadResult csvUploadResult = this.validateGoodsCsv(new File(tmpFileName));

        if (csvUploadResult.isInValid()) {
            goodsBundledUploadIndexHelper.toPageForCsvUploadResultDto(goodsBundledUploadModel, csvUploadResult);

            // ファイル操作Helper取得
            FileOperationUtility fileOperationUtility = ApplicationContextUtility.getBean(FileOperationUtility.class);
            try {
                // NGファイルは削除する
                fileOperationUtility.remove(tmpFileName);
                // バッチ起動をせずにそのまま終了させる
                return;
            } catch (IOException e) {
                // ファイルの削除に失敗した場合
                LOGGER.error("例外処理が発生しました", e);
                this.throwMessage(GoodsBundledUploadModel.MSGCD_FAIL_DELETE);
            }
        } else {
            goodsBundledUploadIndexHelper.toPageForCsvUploadResultDto(goodsBundledUploadModel, null);
        }

        // --------------------------------------------------------------------
        // 非同期バッチをキックする
        // --------------------------------------------------------------------
        try {
            JobLauncher jobLauncher = ApplicationContextUtility.getApplicationContext()
                                                               .getBean("jobLauncherAsync", JobLauncher.class);
            Job job = ApplicationContextUtility.getApplicationContext()
                                               .getBean(BatchName.BATCH_GOODS_ASYNCHRONOUS, Job.class);
            String administratorId = this.getCommonInfo().getCommonInfoAdministrator().getAdministratorId();
            Integer shopSeq = this.getCommonInfo().getCommonInfoBase().getShopSeq();
            JobExecution jobExecution = jobLauncher.run(job, new JobParametersBuilder().addString("administratorId",
                                                                                                  administratorId
                                                                                                 )
                                                                                       .addString(
                                                                                                       "shopSeq",
                                                                                                       shopSeq.toString()
                                                                                                 )
                                                                                       .addString(
                                                                                                       "targetFilePath",
                                                                                                       tmpFileName
                                                                                                 )
                                                                                       .addString("uploadType",
                                                                                                  goodsBundledUploadModel.getUploadType()
                                                                                                 )
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
     * アップロードされた商品CSVの事前バリデーション
     *
     * @param csvFile
     * @return CsvUploadResult
     */
    private CsvUploadResult validateGoodsCsv(File csvFile) {

        // CSV読み込みオプションを設定する
        CsvReaderOptionDto csvReaderOptionDto = new CsvReaderOptionDto();
        csvReaderOptionDto.setValidLimit(CsvUploadResult.CSV_UPLOAD_VALID_LIMIT);

        // Csvアップロード結果Dto作成
        CsvUploadResult csvUploadResult = new CsvUploadResult();
        CsvReaderUtil csvReaderUtil = new CsvReaderUtil();

        /* Csvファイルを読み込み */
        List<GoodsCsvDto> goodsCsvDtoList;
        try {
            // CSVの全行分の DTO を作成してみる
            goodsCsvDtoList = (List<GoodsCsvDto>) csvReaderUtil.readCsv(csvFile, GoodsCsvDto.class, csvUploadResult,
                                                                        csvReaderOptionDto
                                                                       );
        } catch (Exception e) {
            // CSV読み込みで有り得ない例外が発生した場合
            LOGGER.error("例外処理が発生しました", e);
            CsvValidationResult csvValidationResult = new CsvValidationResult();
            csvReaderUtil.createUnexpectedExceptionMsg(csvValidationResult);
            csvUploadResult.setCsvValidationResult(csvValidationResult);
            return csvUploadResult;
        }

        return csvUploadResult;
    }

    /**
     * 結果画面ロード<br/>
     *
     * @return 自画面
     */
    @GetMapping(value = "/csvUploadComplete/")
    protected String doLoadComplete() {

        return "goods/bundledupload/csvUploadComplete";

    }

    /**
     * 商品画像zipアップロード完了画面表示
     *
     * @return
     */
    @GetMapping(value = "/imageUploadComplete/")
    protected String doLoadZip() {

        return "goods/bundledupload/imageUploadComplete";

    }

}
