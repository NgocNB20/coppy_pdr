package jp.co.hankyuhanshin.itec.hitmall.batch.offline.delvinfo.tasklet;

import com.fasterxml.jackson.core.JsonProcessingException;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.AsyncMailSendService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// PDR Migrate Customization from here

/**
 * 出荷情報取得バッチ
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
@Service
public class DelvInfoImportService {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DelvInfoImportService.class);

    /**
     * メール送信サービス（非同期送信）
     */
    private final AsyncMailSendService asyncMailSendService;

    /**
     * コンストラクタ
     */
    public DelvInfoImportService() {
        this.asyncMailSendService = ApplicationContextUtility.getBean(AsyncMailSendService.class);
    }

    /**
     * 一括メール送信
     *
     * @param mailDtoList メールDTOリスト
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public void sendMailInBulk(List<MailDto> mailDtoList)
                    throws JsonProcessingException, JobInstanceAlreadyCompleteException,
                    JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        int mailRequestCode = this.asyncMailSendService.execute(mailDtoList);

        // 一括メール送信バッチ起動
        JobLauncher jobLauncher = ApplicationContextUtility.getApplicationContext()
                                                           .getBean("jobLauncherAsync", JobLauncher.class);
        Job job = ApplicationContextUtility.getApplicationContext().getBean("BATCH_MAIL", Job.class);
        JobExecution jobExecution = jobLauncher.run(
                        job, new JobParametersBuilder().addString("requestCode", String.valueOf(mailRequestCode))
                                                       .addString("totalRecord", String.valueOf(mailDtoList.size()))
                                                       .toJobParameters());
        if (jobExecution.getExitStatus().equals(ExitStatus.STOPPED)) {
            LOGGER.info("一括メール送信バッチ起動失敗");
        } else {
            LOGGER.info("一括メール送信バッチ起動成功");
        }

    }
}
// PDR Migrate Customization to here
