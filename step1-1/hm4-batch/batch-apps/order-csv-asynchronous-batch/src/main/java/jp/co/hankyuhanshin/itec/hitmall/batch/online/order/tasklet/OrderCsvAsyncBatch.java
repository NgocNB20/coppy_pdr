/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.online.order.tasklet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.impl.CommonInfoBatchAdministratorImpl;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvDownloadOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderCSVDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderSearchConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.batch.BatchJobEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.batch.BatchJobSelectLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderAllCsvDownloadLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderCsvDownloadLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.InstantMailSetting;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.CsvExtractUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 機能概要：受注CSV非同期バッチ<br/>
 * 作成日：2021/07/26
 *
 * @author kimura
 */
public class OrderCsvAsyncBatch extends AbstractBatch {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCsvAsyncBatch.class);

    /**
     * 管理者情報
     */
    public CommonInfoBatchAdministratorImpl commonInfoAdministrator;

    /**
     * 管理者用メール送信設定
     */
    protected InstantMailSetting mailSetting;

    /**
     * 管理者情報取得サービス
     */
    private final AdminLogic adminLogic;

    /**
     * メール送信サービス
     */
    private final MailSendService mailSendService;

    /**
     * 受注CSV出力Logic 全件出力
     */
    private final OrderAllCsvDownloadLogic orderAllCsvDownloadLogic;

    /**
     * 受注CSV出力Logic 選択出力
     */
    private final OrderCsvDownloadLogic orderCsvDownloadLogic;

    /**
     * バッチのリクエストデータ取得ロジック
     */
    private final BatchJobSelectLogic batchJobSelectLogic;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    /**
     * ファイル名
     */
    private final String FILE_NAME_PRE = "order";

    /**
     * ファイル拡張子
     */
    private final String FILE_EXTENSION = ".csv";

    /**
     * ファイルパス
     */
    private final String FILE_PATH;

    /**
     * ダウンロードURL
     */
    private final String DL_URL;

    /**
     * 保持期間
     */
    private final String RETENTION_PERIOD;

    /**
     * 全件CSV出力のパラメータ数
     */
    private final int OUTPUTAll_PRM_SIZE = 1;

    /**
     * 選択CSV出力のパラメータ数
     */
    private final int OUTPUTSELECT_PRM_SIZE = 2;

    /**
     * コンストラクタ
     */
    public OrderCsvAsyncBatch(Environment environment) {
        this.adminLogic = ApplicationContextUtility.getBean(AdminLogic.class);
        this.commonInfoAdministrator = ApplicationContextUtility.getBean(CommonInfoBatchAdministratorImpl.class);
        this.mailSetting =
                        new InstantMailSetting(environment.getProperty("mail.setting.orderCsvAsynchronous.smtp.server"),
                                               environment.getProperty("mail.setting.orderCsvAsynchronous.mail.from"),
                                               null, null, new ArrayList<>()
                        );
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.orderAllCsvDownloadLogic = ApplicationContextUtility.getBean(OrderAllCsvDownloadLogic.class);
        this.orderCsvDownloadLogic = ApplicationContextUtility.getBean(OrderCsvDownloadLogic.class);
        this.batchJobSelectLogic = ApplicationContextUtility.getBean(BatchJobSelectLogic.class);
        this.exitMessageUtil = new BatchExitMessageUtil();
        FILE_PATH = environment.getProperty("orderCsvAsynchronous.file.path");
        DL_URL = environment.getProperty("orderCsvAsynchronous.url");
        RETENTION_PERIOD = environment.getProperty("orderCsvAsynchronous.retention.period");
    }

    /**
     * 受注CSV非同期生成のメイン処理<br/>
     * <p>
     * 1. CSVを生成　＋　サーバーに配置<br/>
     * 2. DL用メールを送信
     *
     * @param contribution
     * @param chunkContext
     * @return RepeatStatus
     * @throws Exception
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        // コンテキスト取得
        ExecutionContext executionContext =
                        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();

        // リクエストデータのリストを取得
        String jobParam = chunkContext.getStepContext().getStepExecution().getJobParameters().getString("jobParam");
        List<BatchJobEntity> batchJobEntityList = batchJobSelectLogic.execute(Integer.valueOf(jobParam));

        // 管理者IDを取得
        String administratorId = chunkContext.getStepContext()
                                             .getStepExecution()
                                             .getJobParameters()
                                             .getString("administratorId");
        // shopSeqを取得
        String shopSeq = chunkContext.getStepContext().getStepExecution().getJobParameters().getString("shopSeq");
        // 管理者情報を取得
        AdministratorEntity administratorEntity =
                        adminLogic.getAdministrator(Integer.parseInt(shopSeq), administratorId);

        // 処理日時を生成 ファイル名、メール文面に利用
        LocalDateTime processingTime = LocalDateTime.now();

        // ファイル名を生成
        String fileName =
                        FILE_NAME_PRE + DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss").format(processingTime).toString()
                        + FILE_EXTENSION;

        /**
         * 1. CSVを生成　＋　サーバーに配置
         * ※全件取得の場合、batchJobEntityListのsizeが1つ（condition）
         * ※選択取得の場合、batchJobEntityListのsizeが2つ（orderSeqList, shopSeq）
         */
        // 件数が-1の場合、エラー
        int processingCnt = 0;

        // 全件取得
        if (batchJobEntityList.size() == OUTPUTAll_PRM_SIZE) {
            processingCnt = doOutPutAll(chunkContext, fileName, processingTime, batchJobEntityList);
        }

        // 選択取得
        else if (batchJobEntityList.size() == OUTPUTSELECT_PRM_SIZE) {
            processingCnt = doOutPutSelect(chunkContext, fileName, processingTime, batchJobEntityList);
        } else {
            // 異常
            processingCnt = -1;
        }

        /** 2. DL用メールを送信 */
        if (processingCnt != -1) {
            sendMail(fileName, processingTime, processingCnt, administratorEntity.getMail());
        } else {
            LOGGER.error("受注CSVの出力に失敗しました。受注CSVの生成は実行しておりません。");
            sendErrorMail(processingTime, administratorEntity.getMail());
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(null, "受注CSVの出力に失敗しました。受注CSVの生成は実行しておりません。")));
            throw new Exception("受注CSVの出力に失敗しました。受注CSVの生成は実行しておりません。");
        }

        executionContext.put(
                        BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                        new BatchExitMessageDto(null, processingCnt + "件の受注CSVを出力しました。")));
        return RepeatStatus.FINISHED;
    }

    /**
     * 受注CSV一括出力 + 配置<br/>
     * ※BatchJobEntityListの要素順とはCSVLogicの引数順を統一
     *
     * @param chunkContext
     * @param fileName
     * @param processingTime
     * @param batchJobEntityList
     * @return 成功時...件数 失敗時...-1
     */
    protected int doOutPutAll(ChunkContext chunkContext,
                              String fileName,
                              LocalDateTime processingTime,
                              List<BatchJobEntity> batchJobEntityList) {

        ObjectMapper objectMapper = new ObjectMapper();
        OrderSearchConditionDto condition = null;
        try {
            // pageInfo無視(例外を発生させないため。結果に影響なし)
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            // conditionを取得
            condition = objectMapper.readValue(
                            batchJobEntityList.get(OUTPUTAll_PRM_SIZE - OUTPUTAll_PRM_SIZE).getRequestData(),
                            OrderSearchConditionDto.class
                                              );
        } catch (JsonProcessingException e) {
            LOGGER.error("リクエストデータの変換に失敗しました。 : [JSON to OrderSearchConditionDto.class]", e);
            return -1;
        }

        // Stream方式でCSVダウンロードサービスから取得する
        Stream<OrderCSVDto> resultStream = orderAllCsvDownloadLogic.execute(condition);

        // CSVダウンロードオプションを設定する
        CsvDownloadOptionDto csvDownloadOptionDto = CsvDownloadOptionDto.DEFAULT_DOWNLOAD_OPTION;
        csvDownloadOptionDto.setOutputHeader(true);

        // 出力ファイルの設定
        CsvExtractUtility csvExtractUtility =
                        new CsvExtractUtility(OrderCSVDto.class, csvDownloadOptionDto, resultStream,
                                              FILE_PATH + fileName
                        );

        try {
            return csvExtractUtility.outCsv();
        } catch (IOException e) {
            LOGGER.error("受注CSVの出力に失敗しました。", e);
            return -1;
        }
    }

    /**
     * 受注CSV選択出力 + 配置<br/>
     * ※BatchJobEntityListの要素順とはCSVLogicの引数順を統一
     *
     * @param chunkContext
     * @param fileName
     * @param processingTime
     * @param batchJobEntityList
     * @return 成功時...件数 失敗時...-1
     */
    @SuppressWarnings("unchecked")
    protected int doOutPutSelect(ChunkContext chunkContext,
                                 String fileName,
                                 LocalDateTime processingTime,
                                 List<BatchJobEntity> batchJobEntityList) {

        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> orderSeqList = null;
        try {
            // orderSeqListを取得
            orderSeqList = objectMapper.readValue(batchJobEntityList.get(0).getRequestData(), List.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("リクエストデータの変換に失敗しました[JSON to List.class]", e);
            return -1;
        }

        // shopSeqを取得
        String shopSeq = batchJobEntityList.get(1).getRequestData();

        // Stream方式でCSVダウンロードサービスから取得する
        Stream<OrderCSVDto> resultStream = orderCsvDownloadLogic.execute(orderSeqList, Integer.parseInt(shopSeq));

        // CSVダウンロードオプションを設定する
        CsvDownloadOptionDto csvDownloadOptionDto = CsvDownloadOptionDto.DEFAULT_DOWNLOAD_OPTION;
        csvDownloadOptionDto.setOutputHeader(true);

        // 出力ファイルの設定
        CsvExtractUtility csvExtractUtility =
                        new CsvExtractUtility(OrderCSVDto.class, csvDownloadOptionDto, resultStream,
                                              FILE_PATH + fileName
                        );

        try {
            return csvExtractUtility.outCsv();
        } catch (IOException e) {
            LOGGER.error("受注CSVの出力に失敗しました。", e);
            return -1;
        }
    }

    /**
     * DL用メールを送信
     *
     * @param fileName
     * @param processingTime
     * @param processingCnt
     * @param mail
     */
    protected void sendMail(String fileName, LocalDateTime processingTime, int processingCnt, String mail) {

        try {
            // メールに記載する情報
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            Map<String, String> valueMap = new HashMap<>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            // プレースホルダーへ結果セット
            valueMap.put("SYSTEM", systemName);
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_ORDER_CSV_ASYNCHRONOUS.getLabel());
            valueMap.put(
                            "PROCESS_START_DATE",
                            DateTimeFormatter.ofPattern("yyyy年MM月dd日HH:mm:ss").format(processingTime).toString()
                        );
            valueMap.put("PROCESS_COUNT", Integer.valueOf(processingCnt).toString());
            valueMap.put("FILE_DL_URL", DL_URL + fileName);
            valueMap.put("PERIOD", RETENTION_PERIOD);
            valueMap.put(
                            "DELETE_DATE", DateTimeFormatter.ofPattern("yyyy年MM月dd日")
                                                            .format(processingTime.plusDays(
                                                                            Integer.valueOf(RETENTION_PERIOD)))
                                                            .toString());
            mailContentsMap.put("admin", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.ORDER_CSV_ADMINISTRATOR_MAIL);
            mailDto.setFrom(mailSetting.getMailFrom());
            mailDto.setToList(new ArrayList<String>(Arrays.asList(mail)));
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_ORDER_CSV_ASYNCHRONOUS.getLabel() + "報告");
            mailDto.setMailContentsMap(mailContentsMap);

            // メール送信処理
            Boolean result = mailSendService.execute(mailDto);
            if (result) {
                LOGGER.info("管理者へ受注CSVのDLメールを送信しました。");
            }
        } catch (Exception e) {
            LOGGER.warn("管理者への受注CSVのDLメール送信に失敗しました。", e);
        }
    }

    /**
     * 管理者宛てエラーメールを送信
     *
     * @param processingTime
     * @param mail
     */
    protected void sendErrorMail(LocalDateTime processingTime, String mail) {

        try {
            // メールに記載する情報
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            Map<String, String> valueMap = new HashMap<>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            // プレースホルダーへ結果セット
            valueMap.put("SYSTEM", systemName);
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_ORDER_CSV_ASYNCHRONOUS.getLabel());
            valueMap.put(
                            "PROCESS_START_DATE",
                            DateTimeFormatter.ofPattern("yyyy年MM月dd日HH:mm:ss").format(processingTime).toString()
                        );
            mailContentsMap.put("error", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.ORDER_CSV_ADMINISTRATOR_ERROR_MAIL);
            mailDto.setFrom(mailSetting.getMailFrom());
            mailDto.setToList(new ArrayList<String>(Arrays.asList(mail)));
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_ORDER_CSV_ASYNCHRONOUS.getLabel() + "報告");
            mailDto.setMailContentsMap(mailContentsMap);

            // メール送信処理
            Boolean result = mailSendService.execute(mailDto);
            if (result) {
                LOGGER.info("管理者へ受注CSV生成バッチのエラーメールを送信しました。");
            }
        } catch (Exception e) {
            LOGGER.warn("管理者への受注CSV生成バッチのエラーメール送信に失敗しました。", e);
        }
    }
}
