package jp.co.hankyuhanshin.itec.hitmall.batch.online.mail.tasklet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.batch.BatchJobEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.mail.MailBatchInputSelectLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.ExInternetAddress;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * メール送信バッチ
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public class MailBatch extends AbstractBatch {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MailBatch.class);

    /**
     * 正規表現：改行文字
     */
    private static final String REGEXP_CRLF = "(\\r|\\n)";

    /**
     * デフォルトメールエンコード
     */
    private static final String DEFAULT_ENCODE = "UTF-8";

    /**
     * 作業用内部プロパティ：送信に失敗したメール数
     */
    private int failureCount;

    /**
     * トータルレコード
     */
    private Integer totalRecord;

    /**
     * 処理したレコード
     */
    private Integer processedRecord;

    /**
     * エンコード
     */
    private final String encode;

    /**
     * メール送信入力情報取得
     */
    private final MailBatchInputSelectLogic mailBatchInputSelectLogic;

    /**
     * メール送信サービス（同期送信）
     */
    private final MailSendService mailSendService;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    /**
     * コンストラクタ。
     */
    public MailBatch() {
        super();

        this.mailBatchInputSelectLogic = ApplicationContextUtility.getBean(MailBatchInputSelectLogic.class);
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.exitMessageUtil = new BatchExitMessageUtil();

        // デフォルト値
        this.encode = DEFAULT_ENCODE;

        // 初期値設定
        this.processedRecord = 0;
        this.totalRecord = 0;
        this.failureCount = 0;
    }

    /**
     * 一括メール送信バッチ
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
        String requestCode = jobParameters.getString("requestCode");
        String totalRecordString = jobParameters.getString("totalRecord");

        if (!StringUtils.isEmpty(requestCode) && !StringUtils.isEmpty(totalRecordString)) {
            this.totalRecord = Integer.valueOf(totalRecordString);

            Stream<BatchJobEntity> mailDtoListOutput = mailBatchInputSelectLogic.execute(Integer.valueOf(requestCode));

            Iterator<BatchJobEntity> mailDtoIterator = mailDtoListOutput.iterator();

            while (mailDtoIterator.hasNext()) {
                BatchJobEntity entity = mailDtoIterator.next();

                try {
                    // メールDTO取得
                    MailDto mailDto = convertJsonToMailDto(entity.getRequestData());
                    mailAddressAnalysis(mailDto);
                    this.mailSendService.execute(mailDto);

                    // 処理済件数を更新
                    this.processedRecord++;
                } catch (Exception e) {
                    // 処理済件数を更新
                    this.processedRecord++;
                    this.failureCount++;
                    LOGGER.warn(this.processedRecord + "件目の送信先メールアドレスに不備があります。このアドレスにはメールは送信されません。", e);
                }
            }
        } else {
            if (StringUtils.isEmpty(requestCode)) {
                this.report("メール要求コードが取得できませんでした。");
            }
            if (StringUtils.isEmpty(totalRecordString)) {
                this.report("メール送信全件数が取得できませんでした。");
            }
            executionContext.put(BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                            new BatchExitMessageDto(this.totalRecord.toString(), this.getReportString().toString())));
            throw new Exception(this.getReportString().toString());
        }

        this.report("メール送信処理が終了しました。");
        this.report("処理対象件数 " + this.totalRecord + "件 送信失敗 " + this.failureCount + "件");

        // 進捗の最終状態を保存
        LOGGER.info(this.getReportString().toString().replaceAll(REGEXP_CRLF, ""));
        executionContext.put(BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                        new BatchExitMessageDto(this.totalRecord.toString(), this.getReportString().toString())));
        return RepeatStatus.FINISHED;
    }

    /**
     * 送信時にスローされたオブジェクトからレポート文字列を編集する。
     *
     * @param reason キャッチした例外
     */
    protected void createBadEndReport(final Throwable reason) {
        if (reason instanceof JsonProcessingException) {
            this.report("データベースからJsonをメールDtoに変換できませんでした。");
        } else if (reason instanceof Exception) {
            this.report("メールアドレスの設定に不備があります。");
        }
        this.report(this.totalRecord + "件中 " + this.processedRecord + "件送信されました。");
    }

    /**
     * JsonからMailDtoに変換<br/>
     *
     * @param jsonString jsonString
     * @return MailDto
     */
    private MailDto convertJsonToMailDto(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, MailDto.class);
    }

    /**
     * メールアドレスを解析<br/>
     *
     * @param mailDto メールDTO
     * @throws AddressException
     */
    private void mailAddressAnalysis(MailDto mailDto) throws AddressException {

        // メールアドレスの設定に不備
        if (StringUtils.isEmpty(mailDto.getFrom()) || (((mailDto.getToList() == null) || (mailDto.getToList().size()
                                                                                          == 0)))) {
            throw new AddressException("メールアドレスの設定に不備があります。");
        }

        // 不正なメールアドレスが設定されているかどうか確認
        Address fromAddress = new ExInternetAddress(mailDto.getFrom(), encode);
        for (String to : mailDto.getToList()) {
            Address toAddress = new ExInternetAddress(to, encode);
        }
    }
}
