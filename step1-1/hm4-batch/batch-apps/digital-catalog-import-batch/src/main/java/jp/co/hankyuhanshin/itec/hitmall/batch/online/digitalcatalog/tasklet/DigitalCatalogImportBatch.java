// 2023-renew No42 from here
package jp.co.hankyuhanshin.itec.hitmall.batch.online.digitalcatalog.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hitmall.service.shop.digitalcatalog.DigitalCatalogZipFileUploadService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.InstantMailSetting;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.UnzipUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.env.Environment;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * デジタルカタログ取込バッチ
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public class DigitalCatalogImportBatch extends AbstractBatch {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DigitalCatalogImportBatch.class);

    /**
     * 管理者用メール送信設定
     */
    private final InstantMailSetting mailSetting;

    /**
     * メール送信サービス
     */
    private final MailSendService mailSendService;

    /**
     * 改行(キャリッジリターン)
     */
    private static final String LINE_FEED_CR = "\r\n";

    /**
     * ファイルパス
     */
    private final String DIGITAL_CATALOG_FILE_PATH;

    /**
     * 区切り文字(スラッシュ)
     */
    private static final String DELIMITER_SLASH = "/";

    /**
     * デジタルカタロzip一括アップロードServiceインターフェース
     */
    private final DigitalCatalogZipFileUploadService catalogZipFileUploadService;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    /**
     * コンストラクタ。
     */
    public DigitalCatalogImportBatch(Environment environment) {
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.exitMessageUtil = new BatchExitMessageUtil();
        this.mailSetting = new InstantMailSetting(
                        environment.getProperty("mail.setting.digital.catalog.smtp.server"),
                        environment.getProperty("mail.setting.digital.catalog.mail.from"), null, null,
                        Collections.singletonList(
                                        environment.getProperty("mail.setting.digital.catalog.mail.receivers"))
        );
        DIGITAL_CATALOG_FILE_PATH = environment.getProperty("digital.catalog.directory");
        catalogZipFileUploadService = ApplicationContextUtility.getBean(DigitalCatalogZipFileUploadService.class);
    }

    /**
     * デジタルカタログ取込
     *
     * @param contribution contribution
     * @param chunkContext chunkContext
     * @return exitCode
     * @throws Exception ハンドリングされなかった例外全般
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // コンテキスト取得
        ExecutionContext executionContext =
                        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();

        // バッチのジョッブ情報取得
        JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobParameters();
        String administratorId = jobParameters.getString("administratorId");
        String shopSeq = jobParameters.getString("shopSeq");
        String tmpCatalogZipFilePath = jobParameters.getString("tmpCatalogZipFilePath");
        if (!StringUtils.isEmpty(shopSeq)) {
            super.setShopSeq(Integer.valueOf(shopSeq));
        }

        String errorMessage = null;
        String msg = "[" + tmpCatalogZipFilePath + "]が存在しません。";

        try {
            // 取得件数が0件の場合
            if (StringUtils.isEmpty(tmpCatalogZipFilePath) || !Files.exists(Paths.get(tmpCatalogZipFilePath))) {
                LOGGER.error("パラメーターエラー[テンポラリファイルパス]" + LINE_FEED_CR + msg);
                report(msg);
                errorMessage = "パラメーターエラー[テンポラリファイルパス]";
                throw new Exception(errorMessage);
            }

            String digitalCatalogDir = getDigitalCatalogDir(tmpCatalogZipFilePath);
            UnzipUtility unzipUtility = ApplicationContextUtility.getBean(UnzipUtility.class);
            String folderInZip = unzipUtility.getHighestLevelDirectoryName(tmpCatalogZipFilePath);
            int uploadFileCount = catalogZipFileUploadService.execute(tmpCatalogZipFilePath, digitalCatalogDir);

            if (uploadFileCount == 0) {
                LOGGER.warn("対象ファイルがありません");
                errorMessage = "対象ファイルなしエラー";
                throw new Exception(errorMessage);
            }

            // 処理完了メール送信"
            sendAdministratorMail(DIGITAL_CATALOG_FILE_PATH, String.valueOf(uploadFileCount));
        } catch (Exception error) {
            // 予期せぬ事態で処理が中断した場合
            LOGGER.error("例外処理が発生しました", error);
            // エラーメール送信
            if (Objects.isNull(errorMessage)) {
                sendAdministratorErrorMail(error.getClass().getName());
            } else {
                sendAdministratorErrorMail(errorMessage);
            }
            executionContext.put(BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                            new BatchExitMessageDto(null, this.getReportString().toString())));
            throw error;
        }
        executionContext.put(BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                        new BatchExitMessageDto(null, this.getReportString().toString())));
        return RepeatStatus.FINISHED;
    }

    /**
     * 完全に処理が失敗した旨の管理者向けメールを送信する。
     *
     * @param exceptionName 例外名
     * @return true:成功、false:失敗
     */
    protected boolean sendAdministratorErrorMail(final String exceptionName) {

        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            final Map<String, String> valueMap = new HashMap<>();
            valueMap.put("SYSTEM", systemName);
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_DIGITALCATALOG_IMPORT.getLabel());
            valueMap.put("SHOP_SEQ", super.getShopSeq().toString());
            valueMap.put("RESULT", exceptionName);

            if (LOGGER.isDebugEnabled()) {
                valueMap.put("DEBUG", "1");
            } else {
                valueMap.put("DEBUG", "0");
            }

            mailContentsMap.put("error", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.DIGITAL_CATALOG_IMPORT_ERROR_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject(
                            "【" + systemName + "】" + HTypeBatchName.BATCH_DIGITALCATALOG_IMPORT.getLabel() + "報告[*]");
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);

            LOGGER.info("管理者へ通知メールを送信しました。");

            return true;

        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);

            return false;
        }
    }

    /**
     * 管理者向けメールを送信する
     *
     * @param digitalCatalogDir デジタルカタログ取込フォルダ
     * @param uploadFileCount   アップロードしたファイル数
     * @return true:成功、false:失敗
     */
    protected boolean sendAdministratorMail(String digitalCatalogDir, String uploadFileCount) {
        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            final Map<String, String> valueMap = new HashMap<>();
            valueMap.put("SYSTEM", systemName);
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_DIGITALCATALOG_IMPORT.getLabel());
            valueMap.put("SHOP_SEQ", super.getShopSeq().toString());
            valueMap.put("UPLOAD_FILE_COUNT", uploadFileCount);
            valueMap.put("DIGITAL_CATALOG_PATH", digitalCatalogDir);
            // 本文
            mailContentsMap.put("admin", valueMap);

            // 件名
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_DIGITALCATALOG_IMPORT.getLabel() + "報告");

            mailDto.setMailTemplateType(HTypeMailTemplateType.DIGITAL_CATALOG_IMPORT_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());

            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);

            return true;

        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);

            return false;
        }
    }

    /**
     * ディレクトリ名の生成
     *
     * @param tmpDigitalCatalogDir 一時保存フォルダ
     * @return デジタルカタログ取込フォルダ
     */
    protected String getDigitalCatalogDir(String tmpDigitalCatalogDir) {

        String tmpUploadFileName =
                        tmpDigitalCatalogDir.substring(tmpDigitalCatalogDir.lastIndexOf(DELIMITER_SLASH) + 1);
        String digitalCatalogDir = DIGITAL_CATALOG_FILE_PATH + tmpUploadFileName;
        digitalCatalogDir = digitalCatalogDir.substring(0, digitalCatalogDir.length() - 29);

        return digitalCatalogDir;
    }

}
// 2023-renew No42 to here
