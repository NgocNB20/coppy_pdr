package jp.co.hankyuhanshin.itec.hitmall.batch.offline.questionnaire.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire.QuestionReplyTotalDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.questionnaire.QuestionnaireReplyTotalDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.InstantMailSetting;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.IntegerConversionUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.env.Environment;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * アンケート回答集計バッチ
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public class QuestionnaireTotalingBatch extends AbstractBatch {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionnaireTotalingBatch.class);

    /**
     * アンケート設問回答集計Dao
     */
    private static QuestionReplyTotalDao questionReplyTotalDao;

    /**
     * アンケート回答集計Dao
     */
    private final QuestionnaireReplyTotalDao questionnaireReplyTotalDao;

    /**
     * 管理者用メール送信設定
     */
    private final InstantMailSetting mailSetting;

    /**
     * メール送信サービス
     */
    private final MailSendService mailSendService;

    /**
     * コンストラクタ
     */
    public QuestionnaireTotalingBatch(Environment environment) {
        this.questionReplyTotalDao = ApplicationContextUtility.getBean(QuestionReplyTotalDao.class);
        this.questionnaireReplyTotalDao = ApplicationContextUtility.getBean(QuestionnaireReplyTotalDao.class);
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);

        this.mailSetting = new InstantMailSetting(
                        environment.getProperty("mail.setting.questionnaire.totaling.smtp.server"),
                        environment.getProperty("mail.setting.questionnaire.totaling.mail.from"), null, null,
                        Collections.singletonList(
                                        environment.getProperty("mail.setting.questionnaire.totaling.mail.receivers"))
        );
    }

    /**
     * アンケートの回答を集計します。
     *
     * @param contribution chunkContext
     * @return RepeatStatus 処理結果
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        // バッチのジョッブ情報取得
        JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobParameters();
        String shopSeq = jobParameters.getString("shopSeq");
        if (StringUtils.isNotEmpty(shopSeq)) {
            super.setShopSeq(Integer.valueOf(shopSeq));
        }

        try {
            // 日付関連Helper取得
            DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

            // 現在日時取得
            Timestamp currentTime = dateUtility.getCurrentTime();

            Integer graceDates =
                            IntegerConversionUtil.toInteger(PropertiesUtil.getSystemPropertiesValue("grace.dates"));
            if (graceDates == null) {
                LOGGER.error("プロパティ：[grace.dates] の設定値が不正です");
                throw new Exception();
            }

            Timestamp totalingCompleteTime = dateUtility.getAdditionalDate(graceDates);

            // アンケート回答集計削除
            int delQuestionnaireTotalCnt = questionnaireReplyTotalDao.deleteForQuestionnaireTotaling(currentTime,
                                                                                                     totalingCompleteTime
                                                                                                    );
            LOGGER.debug("「アンケート回答集計情報　削除件数 : " + delQuestionnaireTotalCnt + "件」");

            // アンケート設問回答集計削除
            int delQuestionTotalCnt =
                            questionReplyTotalDao.deleteForQuestionnaireTotaling(currentTime, totalingCompleteTime);
            LOGGER.debug("「アンケート設問回答集計情報　削除件数 : " + delQuestionTotalCnt + "件」");

            // アンケート回答集計登録
            int insQuestionnaireTotalCnt = questionnaireReplyTotalDao.insertForQuestionnaireTotaling(currentTime,
                                                                                                     totalingCompleteTime
                                                                                                    );
            LOGGER.debug("「アンケート回答集計情報　登録件数 : " + insQuestionnaireTotalCnt + "件」");

            // アンケート設問回答集計登録
            int insQuestionTotalCnt =
                            questionReplyTotalDao.insertForQuestionnaireTotaling(currentTime, totalingCompleteTime);
            LOGGER.debug("「アンケート設問回答集計情報　登録件数 : " + insQuestionTotalCnt + "件」");

        } catch (Exception error) {
            LOGGER.error("例外処理が発生しました", error);
            LOGGER.info("[" + QuestionnaireTotalingBatch.class.getName() + "]" + new Timestamp(
                            System.currentTimeMillis()) + " rollbackを実行します。");

            // エラーがあった場合は管理者にメール送信
            sendAdministratorErrorMail(error.getClass().getName());

            LOGGER.info("[" + QuestionnaireTotalingBatch.class + "] 処理中に予期せぬエラーが発生したため異常終了しています。");
            LOGGER.info("情報　" + error.getClass().getName());

            throw new RuntimeException();
        }

        // バッチ正常終了
        return RepeatStatus.FINISHED;
    }

    /**
     * 完全に処理が失敗した旨の管理者向けメールを送信する。
     *
     * @param exceptionName エラー内容
     * @return 成否
     */
    protected boolean sendAdministratorErrorMail(final String exceptionName) {
        try {
            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            final Map<String, String> valueMap = new HashMap<>();
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_QUESTIONNAIRE_TOTALING.getLabel());
            valueMap.put("RESULT", "処理中に" + exceptionName + "が発生しました。");
            valueMap.put("SYSTEM", systemName);

            if (LOGGER.isDebugEnabled()) {
                valueMap.put("DEBUG", "1");
            } else {
                valueMap.put("DEBUG", "0");
            }

            Map<String, Object> mailContentsMap = new HashMap<>();
            mailContentsMap.put("admin", valueMap);

            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            mailDto.setMailTemplateType(HTypeMailTemplateType.QUESTIONNAIRE_TOTALING_ERROR_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_QUESTIONNAIRE_TOTALING.getLabel() + "報告");
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
