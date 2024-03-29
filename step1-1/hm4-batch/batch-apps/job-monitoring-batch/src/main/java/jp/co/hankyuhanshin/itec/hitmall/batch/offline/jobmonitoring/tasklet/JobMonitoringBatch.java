// PDR Migrate Customization from here
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.jobmonitoring.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.dao.BatchJobExecutionDao;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.dto.BatchManagementDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.InstantMailSetting;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.NetworkUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.env.Environment;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * ジョブ監視バッチ
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public class JobMonitoringBatch extends AbstractBatch {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JobMonitoringBatch.class);

    /**
     * batchJobExecutionDao
     */
    private final BatchJobExecutionDao batchJobExecutionDao;

    /**
     * dicon 設定： 管理者用メール送信設定
     */
    protected InstantMailSetting mailSetting;

    /**
     * 今回判定日時
     */
    protected Timestamp currentTime;

    /**
     * アラート対象経過時間（分）
     */
    protected Integer progressTime;

    /**
     * レポートメッセージ
     */
    protected String reportMsg;

    /**
     * メール送信サービス
     */
    private final MailSendService mailSendService;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    /**
     * コンストラクタ
     */
    public JobMonitoringBatch(Environment environment) {
        this.exitMessageUtil = new BatchExitMessageUtil();
        this.batchJobExecutionDao = ApplicationContextUtility.getBean(BatchJobExecutionDao.class);
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.mailSetting = new InstantMailSetting(environment.getProperty("mail.setting.job.monitoring.smtp.server"),
                                                  environment.getProperty("mail.setting.job.monitoring.mail.from"),
                                                  null, null, Collections.singletonList(
                        environment.getProperty("mail.setting.job.monitoring.mail.receivers"))
        );
        this.progressTime = environment.getProperty("progress.time", Integer.class);
        super.setShopSeq(environment.getProperty("shopseq", Integer.class));
    }

    /**
     * ジョブ監視を実行します
     *
     * @return RepeatStatus 処理結果
     * @throws Exception 例外
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        // コンテキスト取得
        ExecutionContext executionContext =
                        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();

        // バッチのジョッブ情報取得
        JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobParameters();
        String shopSeq = jobParameters.getString("shopSeq");
        if (!StringUtils.isEmpty(shopSeq)) {
            super.setShopSeq(Integer.valueOf(shopSeq));
        }

        // レポートメッセージの初期化
        reportMsg = "";

        try {
            // 処理実施日時
            DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
            currentTime = dateUtility.getCurrentTime();

            // アラート対象経過時間（分）のチェック
            if (progressTime == null) {
                reportMsg = "アラート対象経過時間が設定されておりません。";
                LOGGER.error(reportMsg);
                // エラーメールを送信して終了
                throw new Exception(reportMsg);
            }

            // アラート対象のジョブリストの取得
            List<BatchManagementDetailDto> batchManagementDetailDtoList =
                            batchJobExecutionDao.getAlertBatchTaskList(progressTime, currentTime);

            // レポート内容に表示する内容を設定
            reportMsg = createReportMsg(batchManagementDetailDtoList);

            // 実行履歴を記入
            report(reportMsg);
            LOGGER.info(reportMsg);

            // 処理完了メール送信
            if (!batchManagementDetailDtoList.isEmpty()) {
                sendAdministratorMail();
            }

        } catch (Exception error) {
            // 予期せぬ事態で処理が中断した場合
            LOGGER.error("例外処理が発生しました", error);

            // エラーメール送信
            String exceptionName = error.getClass().getName();
            sendAdministratorErrorMail(exceptionName);
            throw error;
        }

        // バッチ正常終了
        executionContext.put(
                        BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                        new BatchExitMessageDto(null, this.getReportString().toString())));
        return RepeatStatus.FINISHED;
    }

    /**
     * レポートに出力する文言を作成します。<br />
     * 　@param batchManagementDetailDtoList batchManagementDetailDtoList
     *
     * @return 出力メッセージ
     */
    protected String createReportMsg(List<BatchManagementDetailDto> batchManagementDetailDtoList) {

        StringBuilder sb = new StringBuilder();

        // レポートに出力する文言を作成します。
        for (BatchManagementDetailDto batchManagementDetailDto : batchManagementDetailDtoList) {

            sb.append("タスク識別ID：")
              .append(batchManagementDetailDto.getJobExecutionId())
              .append(" ")
              .append("バッチ名：")
              .append(Objects.requireNonNull(EnumTypeUtil.getEnumFromValue(HTypeBatchName.class,
                                                                           batchManagementDetailDto.getBatchName()
                                                                          )).getLabel())
              .append(" ")
              .append("受付時刻：")
              .append(batchManagementDetailDto.getCreateTime())
              .append(" ")
              .append("状態：")
              .append(Objects.requireNonNull(EnumTypeUtil.getEnumFromValue(HTypeBatchStatus.class,
                                                                           batchManagementDetailDto.getStatus()
                                                                          )).getLabel())
              .append("\r\n");

        }

        return sb.toString();
    }

    /**
     * 管理者向けエラーメールを送信する
     *
     * @param exceptionName 例外エラー名
     * @return 送信成功:true、送信失敗：false
     */
    protected boolean sendAdministratorErrorMail(final String exceptionName) {

        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            final Map<String, String> valueMap = new HashMap<>();

            NetworkUtility networkUtility = ApplicationContextUtility.getBean(NetworkUtility.class);
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            valueMap.put("SYSTEM", systemName);
            valueMap.put("HOST", networkUtility.getLocalHostName() + "(" + networkUtility.getLocalHostAddress() + ")");
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_JOB_MONITORING.getLabel());
            valueMap.put("SHOP_SEQ", getShopSeq().toString());

            String resultMsg = "";
            if (exceptionName != null) {
                resultMsg = "処理中に" + exceptionName + "が発生しました。\r\n\r\n";
            }

            valueMap.put("RESULT", resultMsg + reportMsg);

            if (LOGGER.isDebugEnabled()) {
                valueMap.put("DEBUG", "1");
            } else {
                valueMap.put("DEBUG", "0");
            }

            mailContentsMap.put("error", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.JOB_MONITORING_ERROR_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_JOB_MONITORING.getLabel() + "バッチ報告[*]");
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
     * @return true:成功、false:失敗
     */
    protected boolean sendAdministratorMail() {

        try {

            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);
            Map<String, Object> mailContentsMap = new HashMap<>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            NetworkUtility networkUtility = ApplicationContextUtility.getBean(NetworkUtility.class);
            final Map<String, String> valueMap = new HashMap<>();
            valueMap.put("SYSTEM", systemName);
            valueMap.put("HOST", networkUtility.getLocalHostName() + "(" + networkUtility.getLocalHostAddress() + ")");
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_JOB_MONITORING.getLabel());
            valueMap.put("SHOP_SEQ", getShopSeq().toString());
            valueMap.put("RESULT", reportMsg);

            if (LOGGER.isDebugEnabled()) {
                valueMap.put("DEBUG", "1");
            } else {
                valueMap.put("DEBUG", "0");
            }

            mailContentsMap.put("admin", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.JOB_MONITORING_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_JOB_MONITORING.getLabel() + "バッチ報告[*]");
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);

            LOGGER.info("管理者へ通知メールを送信しました。");
            return true;

        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);
            return false;
        }
    }
}
// PDR Migrate Customization to here
