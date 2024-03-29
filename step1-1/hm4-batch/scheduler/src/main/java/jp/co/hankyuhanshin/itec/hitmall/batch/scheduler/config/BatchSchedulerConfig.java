package jp.co.hankyuhanshin.itec.hitmall.batch.scheduler.config;

import jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchRunType;
import jp.co.hankyuhanshin.itec.hitmall.constant.BatchName;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
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
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 機能概要：バッチスケジューラ
 * 作成日：2021/01/12
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@Configuration
@EnableScheduling
@DependsOn({"applicationContextUtility"})
public class BatchSchedulerConfig {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(BatchSchedulerConfig.class);

    /**
     * 日時のフォーマッター
     */
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * CommonInfo
     */
    private final CommonInfo commonInfo;

    /**
     * SpringBatchジョッブランチャー
     */
    private final JobLauncher jobLauncher;

    @Autowired
    public BatchSchedulerConfig() {
        this.commonInfo = ApplicationContextUtility.getBeanByName("commonInfoBatch", CommonInfo.class);
        this.jobLauncher = ApplicationContextUtility.getApplicationContext()
                                                    .getBean("jobLauncherAsync", JobLauncher.class);
    }

    /**
     * TOP集計
     */
    @Scheduled(cron = "${cron.topTotalingBatch}", zone = "Asia/Tokyo")
    public void executeTopTotalingBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_TOP_TOTALING + " " + formatter.format(new Date()));
        launch(BatchName.BATCH_TOP_TOTALING);
    }

    /**
     * 不要タスク処理
     */
    @Scheduled(cron = "${cron.taskCleanBatch}", zone = "Asia/Tokyo")
    public void executeTaskCleanBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_TASK_CLEAN + " " + formatter.format(new Date()));
        launch(BatchName.BATCH_TASK_CLEAN);
    }

    /**
     * 不要ファイル処理
     */
    @Scheduled(cron = "${cron.clearBatch}", zone = "Asia/Tokyo")
    public void executeClearBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_CLEAR + " " + formatter.format(new Date()));
        launch(BatchName.BATCH_CLEAR);
    }

    /**
     * 郵便番号更新
     */
    @Scheduled(cron = "${cron.zipCodeUpdateBatch}", zone = "Asia/Tokyo")
    public void executeZipCodeUpdateBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_ZIP_CODE + " " + formatter.format(new Date()));
        launch(BatchName.BATCH_ZIP_CODE);
    }

    /**
     * 事業所郵便番号更新
     */
    @Scheduled(cron = "${cron.officeZipCodeUpdateBatch}", zone = "Asia/Tokyo")
    public void executeOfficeZipCodeUpdateBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_OFFICE_ZIP_CODE + " " + formatter.format(new Date()));
        launch(BatchName.BATCH_OFFICE_ZIP_CODE);
    }

    /**
     * 商品在庫表示状態更新
     */
    @Scheduled(cron = "${cron.stockStatusDisplayUpdateBatch}", zone = "Asia/Tokyo")
    public void executeStockStatusDisplayUpdateBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_STOCKSTATUSDISPLAY_UPDATE + " " + formatter.format(
                        new Date()));
        launch(BatchName.BATCH_STOCKSTATUSDISPLAY_UPDATE);
    }

    /**
     * サイトマップXML出力バッチ
     */
    @Scheduled(cron = "${cron.siteMapXmlOutputBatch}", zone = "Asia/Tokyo")
    public void executeSiteMapXmlOutputBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_SITEMAPXML_OUTPUT + " " + formatter.format(new Date()));
        launch(BatchName.BATCH_SITEMAPXML_OUTPUT);
    }

    /**
     * アンケート回答集計
     */
    @Scheduled(cron = "${cron.questionnaireTotalingBatch}", zone = "Asia/Tokyo")
    public void executeQuestionnaireTotalingBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_QUESTIONNAIRE_TOTALING + " " + formatter.format(new Date()));
        launch(BatchName.BATCH_QUESTIONNAIRE_TOTALING);
    }

    // PDR Migrate Customization from here

    /**
     * 会員情報更新
     */
    @Scheduled(cron = "${cron.memberInfoUpdateBatch}", zone = "Asia/Tokyo")
    public void executeMemberInfoUpdateBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_MEMBER_INFO_UPDATE + " " + formatter.format(new Date()));
        launch(BatchName.BATCH_MEMBER_INFO_UPDATE);
    }

    /**
     * 商品価格更新
     */
    @Scheduled(cron = "${cron.goodsPriceUpdateBatch}", zone = "Asia/Tokyo")
    public void executeGoodsPriceUpdateBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_GOODS_PRICE_UPDATE + " " + formatter.format(new Date()));
        launch(BatchName.BATCH_GOODS_PRICE_UPDATE);
    }

    /**
     * 出荷情報取込
     */
    @Scheduled(cron = "${cron.delvInfoImportBatch}", zone = "Asia/Tokyo")
    public void executeDelvInfoInportBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_DELV_PRICE_UPDATE + " " + formatter.format(new Date()));
        launch(BatchName.BATCH_DELV_PRICE_UPDATE);
    }

    /**
     * ジョブ監視バッチ
     */
    @Scheduled(cron = "${cron.jobMonitoringBatch}", zone = "Asia/Tokyo")
    public void executeJobMonitoringBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_JOB_MONITORING + " " + formatter.format(new Date()));
        launch(BatchName.BATCH_JOB_MONITORING);
    }
    // PDR Migrate Customization to here
    // 2023-renew No3 from here
    /**
     * 商品フィード出力
     */
    @Scheduled(cron = "${cron.goodsFeedOutputBatch}", zone = "Asia/Tokyo")
    public void executeGoodsFeedOutputBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_GOODS_FEED_OUTPUT + " " + formatter.format(new Date()));
        launch(BatchName.BATCH_GOODS_FEED_OUTPUT);
    }

    /**
     * コンテンツフィード出力
     */
    @Scheduled(cron = "${cron.contentsFeedOutputBatch}", zone = "Asia/Tokyo")
    public void executeContentsFeedOutputBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_CONTENTS_FEED_OUTPUT + " " + formatter.format(new Date()));
        launch(BatchName.BATCH_CONTENTS_FEED_OUTPUT);
    }
    // 2023-renew No3 to here
    // 2023-renew No21 from here
    /**
     * 一緒によく購入される商品更新バッチ
     */
    @Scheduled(cron = "${cron.goodsPurchasedTogetherUpdateBatch}", zone = "Asia/Tokyo")
    public void executeGoodsPurchasedTogetherUpdateBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_GOODS_PURCHASED_TOGETHER_UPDATE + " " + formatter.format(new Date()));
        launch(BatchName.BATCH_GOODS_PURCHASED_TOGETHER_UPDATE);
    }
    // 2023-renew No21 to here

    // 2023-renew No65 from here
    /**
     * 入荷情報取得バッチ
     */
    @Scheduled(cron = "${cron.stockDataImportBatch}", zone = "Asia/Tokyo")
    public void executeStockDataImportBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_STOCK_DATA_IMPORT + " " + formatter.format(new Date()));
        launch(BatchName.BATCH_STOCK_DATA_IMPORT);
    }
    // 2023-renew No65 to here

    // 2023-renew No41 from here

    /**
     * お気に入りセール通知メール配信バッチ
     */
    @Scheduled(cron = "${cron.cuenotefcSaleMailBatch}", zone = "Asia/Tokyo")
    public void executeCuenotefcSaleMailBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_CUENOTEFC_SALE_MAIL + " " + formatter.format(new Date()));
        launch(BatchName.BATCH_CUENOTEFC_SALE_MAIL);
    }

    /**
     * お気に入りセール通知メール配信確認バッチ
     */
    @Scheduled(cron = "${cron.cuenotefcSaleMailConfirmBatch}", zone = "Asia/Tokyo")
    public void executeCuenotefcSaleMailConfirmBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_CUENOTEFC_SALE_MAIL_CONFIRM + " " + formatter.format(
                        new Date()));
        launch(BatchName.BATCH_CUENOTEFC_SALE_MAIL_CONFIRM);
    }

    /**
     * セール情報取得バッチ
     */
    @Scheduled(cron = "${cron.saleDataImportBatch}", zone = "Asia/Tokyo")
    public void executeSaleDataImportBatch() {
        logger.info("Executing batch: " + BatchName.BATCH_SALE_DATA_IMPORT + " " + formatter.format(new Date()));
        launch(BatchName.BATCH_SALE_DATA_IMPORT);
    }
    // 2023-renew No41 to here

    /**
     * バッチのジョッブを起動
     *
     * @param batchName バッチ名
     */
    private void launch(String batchName) {
        Job job = ApplicationContextUtility.getApplicationContext().getBean(batchName, Job.class);

        String administratorId = this.commonInfo.getCommonInfoAdministrator().getAdministratorId();
        String shopSeq = this.commonInfo.getCommonInfoBase().getShopSeq().toString();

        try {
            // バッチのジョッブを起動する
            JobExecution jobExecution = jobLauncher.run(
                            job, new JobParametersBuilder().addString("administratorId", administratorId)
                                                           .addString("shopSeq", shopSeq)
                                                           .addLong("timestamp", System.currentTimeMillis())
                                                           .addString("batchRunType", BatchRunType.SCHEDULE)
                                                           .toJobParameters());

            if (jobExecution.getExitStatus().equals(ExitStatus.STOPPED)) {
                logger.info("Executing batch - " + batchName + ": STOPPED " + formatter.format(new Date()));
            } else {
                logger.info("Executing batch - " + batchName + ": SUCCESS " + formatter.format(new Date()));
            }
        } catch (JobParametersInvalidException | JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException e) {
            logger.error(batchName + " @ Failed to executing ", e.getCause());
        }
    }
}
