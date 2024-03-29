package jp.co.hankyuhanshin.itec.hitmall.batch.offline.shop.top.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupPopularityDao;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * トップ画面初期表示用集計バッチ
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public class TopTotalingBatch extends AbstractBatch {

    /**
     * Log
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TopTotalingBatch.class);

    /**
     * 商品グループ人気Dao
     */
    private final GoodsGroupPopularityDao goodsGroupPopularityDao;

    /**
     * 処理時間計測用 開始
     */
    private long processStartTime = 0;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    /**
     * Creates a new TopTotalingBatch object.
     */
    public TopTotalingBatch() {
        this.goodsGroupPopularityDao = ApplicationContextUtility.getBean(GoodsGroupPopularityDao.class);
        this.exitMessageUtil = new BatchExitMessageUtil();
    }

    /**
     * トップ画面初期表示用に各テーブルを集計します
     *
     * @return int
     * @throws Exception 例外
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        LOGGER.info("TOP画面集計処理を開始します。");

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

        /** properties read */
        Properties props = new Properties();
        InputStream is = TopTotalingBatch.class.getResourceAsStream("/top_totaling.properties");

        if (is == null) {
            LOGGER.error("[TopTotalingBatch.execute]:top_totaling.propertiesファイル取得に失敗しました。");
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(null,
                                                                    "[TopTotalingBatch.execute]:top_totaling.propertiesファイル取得に失敗しました。"
                                            )));
            return RepeatStatus.FINISHED;
        }

        props.load(new InputStreamReader(is, StandardCharsets.UTF_8));

        try {
            // 商品ランキング集計期間 プロパティ値取得
            String totalPeriod = props.getProperty("total_period");
            if (StringUtil.isEmpty(totalPeriod)) {
                LOGGER.error("商品ランキング集計期間を取得できませんでした。 properites : total_period");
                this.report("商品ランキング集計期間を取得できませんでした。");
                executionContext.put(
                                BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                                new BatchExitMessageDto(null, this.getReportString().toString())));
                return RepeatStatus.FINISHED;
            }

            // 商品ランキング最大取得件数 プロパティ値取得
            String strLimit = props.getProperty("limit");
            if (StringUtil.isEmpty(strLimit)) {
                LOGGER.error(" 商品ランキング最大取得件数を取得できませんでした。 properites : limit");
                this.report("商品ランキング最大取得件数を取得できませんでした。");
                executionContext.put(
                                BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                                new BatchExitMessageDto(null, this.getReportString().toString())));
                return RepeatStatus.FINISHED;
            }
            Integer limit = Integer.valueOf(strLimit);

            // 人気カウント更新
            this.startProcess();
            goodsGroupPopularityDao.updatePopularityOrder(getShopSeq(), totalPeriod);
            LOGGER.info(GoodsGroupPopularityDao.class.getSimpleName()
                        + ".updatePopularityOrder - 人気カウント更新 - 正常終了 - 処理時間：" + this.stopProcessAndGetProcessTime()
                        + "ms");

            this.report("TOP画面集計処理は正常に終了しました。");
            LOGGER.info("TOP画面集計処理は正常に終了しました。");
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(null, this.getReportString().toString())));

        } catch (Exception e) {
            this.report(new Timestamp(System.currentTimeMillis()) + " 例外が発生しました。ロールバックし、処理を終了します。");
            LOGGER.error("例外が発生しました。ロールバックし、処理を終了します。");
            LOGGER.error("エラー情報　", e);
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(null, this.getReportString().toString())));
            throw e;
        }

        return RepeatStatus.FINISHED;
    }

    /**
     * 計測用　処理開始時間をセット<br/>
     */
    private void startProcess() {
        processStartTime = System.currentTimeMillis();
    }

    /**
     * 計測用　処理時間を取得　<br/>
     * processMeasureStartを実施後にコールすることで処理時間を計測する ミリ秒<br/>
     *
     * @return 処理時間(ミリ秒)  開始時間がセットされていない場合は -1
     */
    private long stopProcessAndGetProcessTime() {
        if (processStartTime == 0) {
            return -1;
        }
        long processTime = System.currentTimeMillis() - processStartTime;
        processStartTime = 0; // 初期化

        return processTime;
    }
}
