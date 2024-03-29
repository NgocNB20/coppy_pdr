// 2023-renew No42 from here
package jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.shop.digitalcatalog;

import jp.co.hankyuhanshin.itec.hitmall.admin.pc.web.admin.common.validation.group.ZipUploadGroup;
import jp.co.hankyuhanshin.itec.hitmall.admin.web.AbstractController;
import jp.co.hankyuhanshin.itec.hitmall.annotation.exception.HEHandler;
import jp.co.hankyuhanshin.itec.hitmall.constant.BatchName;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.digitalcatalog.DigitalCatalogZipFileUploadService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.UnzipUtility;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * デジタルカタログアップロード<br/>
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@RequestMapping("/digitalcatalog/")
@Controller
@SessionAttributes(value = "digitalCatalogUploadModel")
@PreAuthorize("hasAnyAuthority('SHOP:8')")
public class DigitalCatalogUploadController extends AbstractController {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DigitalCatalogUploadController.class);

    /**
     * デジタルカタロzip一括アップロードServiceインターフェース
     */
    private final DigitalCatalogZipFileUploadService catalogZipFileUploadService;

    /**
     * テンプファイルディレクトリパス
     */
    private static final String TMP_FILE_PATH = "real.tmp.path";

    /**
     * デジタルカタログアップロード画面から
     */
    public static final String FLASH_FROM_DIGITAL_CATALOG_UPLOAD = "fromDigitalCatalogUpload";

    /**
     * ビュー
     */
    public static final String VIEW = "view";

    /**
     * コンストラクタ
     *
     * @param catalogZipFileUploadService デジタルカタロzip一括アップロードServiceインターフェース
     */
    @Autowired
    public DigitalCatalogUploadController(DigitalCatalogZipFileUploadService catalogZipFileUploadService) {
        this.catalogZipFileUploadService = catalogZipFileUploadService;
    }

    /**
     * デジタルカタログアップロード面表示
     *
     * @param digitalCatalogUploadModel デジタルカタログアップロードモデル
     * @return
     */
    @GetMapping(value = "/digitalCatalogUpload/")
    protected String doLoad(DigitalCatalogUploadModel digitalCatalogUploadModel) {
        return "digitalcatalog/digitalCatalogUpload";
    }

    /**
     * Zipファイルのアップロード処理を実行します
     *
     * @param digitalCatalogUploadModel デジタルカタログアップロードモデル
     * @param error                     エラー
     * @param redirectAttributes        リダイレクトアトリビュート
     * @param model                     モデル
     * @return String
     */
    @PostMapping(value = "/digitalCatalogUpload/", params = "doOnceZipUpload")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    @HEHandler(exception = AppLevelListException.class, returnView = "digitalcatalog/digitalCatalogUpload")
    public String doOnceZipUpload(@Validated(ZipUploadGroup.class) DigitalCatalogUploadModel digitalCatalogUploadModel,
                                  BindingResult error,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        if (error.hasErrors()) {
            return "digitalcatalog/digitalCatalogUpload";
        }

        String tmpCatalogZipFilePath = getTargetFileName(digitalCatalogUploadModel.getZipDigitalCatalogFile());

        catalogZipFileUploadService.execute(
                        digitalCatalogUploadModel.getZipDigitalCatalogFile(), tmpCatalogZipFilePath);

        // 処理受付画面表示用に処理対象ファイル数をカウント
        try {
            UnzipUtility unzipUtility = ApplicationContextUtility.getBean(UnzipUtility.class);
            digitalCatalogUploadModel.setUploadFileCount(unzipUtility.countFileOnZipFile(tmpCatalogZipFilePath));
        } catch (IOException e) {
            LOGGER.warn("デジタルカタログzipファイルの中身の件数カウントに失敗しました", e);
            digitalCatalogUploadModel.setUploadFileCount(0);
        }

        try {
            // 非同期バッチをキックする
            JobLauncher jobLauncher = ApplicationContextUtility.getApplicationContext()
                                                               .getBean("jobLauncherAsync", JobLauncher.class);
            Job job = ApplicationContextUtility.getApplicationContext()
                                               .getBean(BatchName.BATCH_DIGITALCATALOG_IMPORT, Job.class);
            String administratorId = this.getCommonInfo().getCommonInfoAdministrator().getAdministratorId();
            Integer shopSeq = this.getCommonInfo().getCommonInfoBase().getShopSeq();
            JobExecution jobExecution = jobLauncher.run(job, new JobParametersBuilder().addString("administratorId",
                                                                                                  administratorId
                                                                                                 )
                                                                                       .addString("shopSeq",
                                                                                                  shopSeq.toString()
                                                                                                 )
                                                                                       .addString("tmpCatalogZipFilePath",
                                                                                                  tmpCatalogZipFilePath
                                                                                                  != null ?
                                                                                                                  tmpCatalogZipFilePath :
                                                                                                                  ""
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

        redirectAttributes.addFlashAttribute(FLASH_FROM_DIGITAL_CATALOG_UPLOAD, VIEW);

        return "redirect:/digitalcatalog/digitalCatalogUploadComplete/";
    }

    /**
     * デジタルカタログアップロードzipアップロード完了画面表示
     *
     * @param model モデル
     * @return 自画面
     */
    @GetMapping(value = "/digitalCatalogUploadComplete/")
    protected String doLoadZip(Model model, SessionStatus sessionStatus) {

        // デジタルカタログアップロード画面以外から遷移した場合エラー
        if (!model.containsAttribute(FLASH_FROM_DIGITAL_CATALOG_UPLOAD)) {
            throwMessage("SMM000201");
        }
        sessionStatus.setComplete();
        return "digitalcatalog/digitalCatalogUploadComplete";
    }

    /**
     * バッチへ渡す
     *
     * @param zipDigitalCatalogFile zipファイルのアップロード
     * @return パスを含むファイル名
     */
    private String getTargetFileName(MultipartFile zipDigitalCatalogFile) {

        String zipFileUploadName = zipDigitalCatalogFile.getOriginalFilename();

        if (StringUtils.isEmpty(zipFileUploadName)) {
            return null;
        }

        final String stamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        final String path = PropertiesUtil.getSystemPropertiesValue(TMP_FILE_PATH);

        return path + "/" + zipFileUploadName.substring(0, zipFileUploadName.length() - 4) + "_catalog" + stamp
               + ".zip";
    }
}
// 2023-renew No42 to here
