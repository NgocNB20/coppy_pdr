package jp.co.hankyuhanshin.itec.hitmall.batch.offline.stockdisplay.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.stockdisplay.dao.StockStatusDisplayBatchDao;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.InstantMailSetting;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
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
import java.util.Map;

/**
 * 商品グループ在庫状態更新バッチクラス
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public class StockStatusDisplayUpdateBatch extends AbstractBatch {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StockStatusDisplayUpdateBatch.class);

    /**
     * 商品グループ在庫表示Dao
     */
    private final StockStatusDisplayBatchDao stockStatusDisplayBatchDao;

    /**
     * 管理者用メール送信設定
     */
    private final InstantMailSetting mailSetting;

    /**
     * メール送信サービス
     */
    private final MailSendService mailSendService;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    /**
     * Creates a new StockStatusDisplayUpdateBatch object.
     */
    public StockStatusDisplayUpdateBatch(Environment environment) {
        super();
        this.stockStatusDisplayBatchDao = ApplicationContextUtility.getBean(StockStatusDisplayBatchDao.class);
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.exitMessageUtil = new BatchExitMessageUtil();

        this.mailSetting = new InstantMailSetting(
                        environment.getProperty("mail.setting.stock.status.smtp.server"),
                        environment.getProperty("mail.setting.stock.status.mail.from"), null, null,
                        Collections.singletonList(environment.getProperty("mail.setting.stock.status.mail.receivers"))
        );
    }

    /**
     * 商品グループ在庫表示追加・変更処理を実行します
     *
     * @return RepeatStatus
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
        if (!StringUtils.isEmpty(shopSeq)) {
            super.setShopSeq(Integer.valueOf(shopSeq));
        }

        try {
            DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

            // 商品グループ在庫状態表示一括登録。
            // ・未登録の商品を登録します。
            int insertCnt = stockStatusDisplayBatchDao.insertNotExists(dateUtility.getCurrentTime());

            LOGGER.debug("[" + StockStatusDisplayBatchDao.class.getName()
                         + ".insertNotExists()]商品グループ在庫状態表示一括登録- 正常に終了しました。");

            // 商品グループ在庫状態表示一括更新。
            // ・全ての商品グループの在庫状態PC,MBを更新します。
            int updateCnt = stockStatusDisplayBatchDao.updateAll(dateUtility.getCurrentTime());

            LOGGER.debug("[" + StockStatusDisplayBatchDao.class.getName()
                         + ".updateAll()]商品グループ在庫状態表示一括更新- 正常に終了しました。");

            this.report("登録件数[" + insertCnt + "]更新件数[" + updateCnt + "]で処理が終了しました。");
            executionContext.put(BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                            new BatchExitMessageDto(String.valueOf(updateCnt), this.getReportString().toString())));
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            this.report(new Timestamp(System.currentTimeMillis()) + " 予期せぬエラーが発生しました。処理を中断し終了します。");
            LOGGER.info("[" + StockStatusDisplayUpdateBatch.class.getName() + "]" + new Timestamp(
                            System.currentTimeMillis()) + " rollbackが終了しました。処理を終了します。");
            // エラーがあった場合は管理者にメール送信
            sendAdministratorErrorMail(e.getClass().getName());
            executionContext.put(BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                            new BatchExitMessageDto(String.valueOf(0), this.getReportString().toString())));
            throw e;
        }
        return RepeatStatus.FINISHED;
    }

    /**
     * 管理者向けエラー通知メールを送信する。
     *
     * @param execptionInfo エラー詳細
     * @return 成否
     */
    protected boolean sendAdministratorErrorMail(final String execptionInfo) {
        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            final Map<String, String> valueMap = new HashMap<>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            valueMap.put("SYSTEM", systemName);
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_STOCKSTATUSDISPLAY_UPDATE.getLabel());
            valueMap.put("RESULT", "処理中に" + execptionInfo + "が発生しました。");

            mailContentsMap.put("error", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.STOCK_STATUS_ADMINISTRATOR_ERROR_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject(
                            "【" + systemName + "】" + HTypeBatchName.BATCH_STOCKSTATUSDISPLAY_UPDATE.getLabel() + "報告");
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);

            LOGGER.info("管理者へエラー通知メールを送信しました。");

            return true;

        } catch (Exception e) {
            LOGGER.warn("管理者へのエラー通知メール送信に失敗しました。", e);

            return false;
        }
    }

}
