/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.taskclean.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.dao.batch.BatchJobDao;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.env.Environment;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * タスククリーンバッチ
 * 作成日：2021/04/14
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public class TaskCleanBatch extends AbstractBatch {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskCleanBatch.class);

    /**
     * バッチジョブDAO
     */
    private final BatchJobDao batchJobDao;

    /**
     * 掲載期限
     */
    private final Integer retentionDate;

    /**
     * コンストラクタ。
     */
    public TaskCleanBatch(Environment environment) {
        this.batchJobDao = ApplicationContextUtility.getBean(BatchJobDao.class);
        this.retentionDate = environment.getProperty("retention.date", Integer.class);
    }

    /**
     * バッチ主処理
     *
     * @param contribution
     * @param chunkContext
     * @return
     * @throws Exception
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // 掲載期限の算出を行う
        Date expiredDateTime = DateUtils.addDays(new Date(), retentionDate);

        // 処理対象ファイル一覧を取得する
        List<String> targetFileListForDelete = this.batchJobDao.getTargetFileListForDelete(expiredDateTime);

        // 処理対象ファイルがあればそのファイルを削除する
        for (String targetFilePath : targetFileListForDelete) {
            final File deleteFile = new File(targetFilePath);
            boolean failure = true;

            if (!deleteFile.exists()) {
                LOGGER.warn("処理対象ファイルは存在しません。 " + targetFilePath);
                failure = false;
            } else if (deleteFile.exists() && deleteFile.isFile() && deleteFile.canWrite()) {
                failure = !deleteFile.delete();
            }

            if (failure) {
                LOGGER.warn("削除対象のファイルを削除できません。削除できなかったファイル：" + targetFilePath);
                continue;
            }
        }

        // Spring-Batchの実行履歴を削除する
        int deleteCount = 0;
        int totalCount = 0;
        try {
            // BATCH_STEP_EXECUTION_CONTEXTテーブルの削除
            deleteCount = this.batchJobDao.deleteStepExecutionContext(expiredDateTime);
            LOGGER.info("BATCH_STEP_EXECUTION_CONTEXTテーブルから" + deleteCount + " レコードを削除しました。");
            totalCount += deleteCount;

            // BATCH_STEP_EXECUTIONテーブルの削除
            deleteCount = this.batchJobDao.deleteStepExecution(expiredDateTime);
            LOGGER.info("BATCH_STEP_EXECUTIONテーブルから" + deleteCount + " レコードを削除しました。");
            totalCount += deleteCount;

            // BATCH_JOB_EXECUTION_CONTEXTテーブルの削除
            deleteCount = this.batchJobDao.deleteJobExecutionContext(expiredDateTime);
            LOGGER.info("BATCH_JOB_EXECUTION_CONTEXTテーブルから" + deleteCount + " レコードを削除しました。");
            totalCount += deleteCount;

            // BATCH_JOB_EXECUTION_PARAMS_EXTENSIONテーブルの削除
            deleteCount = this.batchJobDao.deleteJobExecutionParamsExtension(expiredDateTime);
            LOGGER.info("BATCH_JOB_EXECUTION_PARAMS_EXTENSIONテーブルから" + deleteCount + " レコードを削除しました。");
            totalCount += deleteCount;

            // BATCH_JOB_EXECUTION_PARAMSテーブルの削除
            deleteCount = this.batchJobDao.deleteJobExecutionParams(expiredDateTime);
            LOGGER.info("BATCH_JOB_EXECUTION_PARAMSテーブルから" + deleteCount + " レコードを削除しました。");
            totalCount += deleteCount;

            // BATCH_JOB_EXECUTIONテーブルの削除
            deleteCount = this.batchJobDao.deleteJobExecution(expiredDateTime);
            LOGGER.info("BATCH_JOB_EXECUTIONテーブルから" + deleteCount + " レコードを削除しました。");
            totalCount += deleteCount;

            // BATCH_JOB_INSTANCEテーブルの削除
            deleteCount = this.batchJobDao.deleteJobInstance();
            LOGGER.info("BATCH_JOB_INSTANCEテーブルから" + deleteCount + " レコードを削除しました。");
            totalCount += deleteCount;
        } catch (Exception e) {
            LOGGER.warn("タスクを削除できませんでした。", e);
            // ロールバックする
            throw e;
        }

        LOGGER.info(totalCount + " 件の掲載期限超過タスクを削除しました。");
        return RepeatStatus.FINISHED;
    }

}
