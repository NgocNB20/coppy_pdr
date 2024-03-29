/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.contentsfeedoutput.tasklet;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.freearea.FreeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvDownloadOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkContentsFeedTsvDto;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.InstantMailSetting;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.CsvExtractUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.FileOperationUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.env.Environment;
import org.springframework.util.DigestUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/**
 * コンテンツフィード出力バッチ
 * @author tk32120
 */
public class ContentsFeedOutputBatch extends AbstractBatch {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentsFeedOutputBatch.class);

    /**
     * 作業ディレクトリ
     */
    private String workDir;

    /**
     * 連携ディレクトリ
     */
    private String dataDir;

    /**
     * バックアップディレクトリ
     */
    private String backupDir;

    /**
     * フリーエリアDao
     */
    private final FreeAreaDao freeAreaDao;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    /**
     * 処理結果メール詳細メッセージ
     */
    private String mailMessageResult;

    /**
     * 処理結果正常終了メッセージ
     */
    private static final String UPDATE_RESULT_SUCCESS = "正常終了";

    /**
     * 処理結果異常終了メッセージ
     */
    private static final String UPDATE_RESULT_FAILED = "異常終了";

    /**
     * 処理結果
     */
    protected String batchProcessResult;

    /**
     * 連携リスト
     */
    protected List<UkContentsFeedTsvDto> resultList;

    /**
     * バッチ処理総件数
     */
    protected int batchProcessTotalCount = 0;

    /**
     * TSVファイル名
     */
    private String tsvFileName = "";

    /**
     * TXTファイル名
     */
    private String txtFileName = "";

    /**
     * gzipファイル名
     */
    private String gzipFileName = "";

    /**
     * 接続用リモートホスト名
     */
    private String remoteHostName = "";

    /**
     * 接続用ポート番号
     */
    private int port;

    /**
     * 接続用ユーザーID
     */
    private String userid = "";

    /**
     * 接続用秘密鍵のパス
     */
    private String rsaPath = "";

    /**
     * SCP通信フラグ（1:通信する）
     */
    protected static final String SCP_CONNECT_FLG = "1";

    /**
     * TSVファイルサフィックス
     */
    protected static final String TSV_SUFFIX = ".tsv";

    /**
     * TXTファイルサフィックス
     */
    protected static final String TXT_SUFFIX = ".txt";

    /**
     * ファイル日付フォーマット
     */
    public static final String DATE_FORMAT = "yyyyMMddHHmmss";

    /**
     * GZファイルサフィックス
     */
    public static final String GZ_SUFFIX = ".gz";

    /**
     * 制御文字（CTRLA）
     */
    public static final String CTRLA = "\u0001";

    /**
     * 制御文字（CTRLE）
     */
    public static final String CTRLE = "\u0005";

    /**
     * バッチ処理日付
     */
    private Timestamp currentTime;

    /**
     * メール送信サービス
     */
    private final MailSendService mailSendService;

    /**
     * dicon 設定： メール送信設定
     */
    private final InstantMailSetting mailSetting;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    /**
     * コンストラクタ
     */
    public ContentsFeedOutputBatch(Environment environment) {
        this.freeAreaDao = ApplicationContextUtility.getBean(FreeAreaDao.class);
        this.dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        this.resultList = new ArrayList<>();
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.exitMessageUtil = new BatchExitMessageUtil();
        this.mailSetting =
                        new InstantMailSetting(environment.getProperty("mail.setting.contentsfeed.output.smtp.server"),
                                               environment.getProperty("mail.setting.contentsfeed.output.mail.from"),
                                               null, null, Collections.singletonList(
                                        environment.getProperty("mail.setting.contentsfeed.output.mail.receivers"))
                        );
    }

    /**
     *コンテンツフィード出力
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

        // バッチのジョブ情報取得
        JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobParameters();
        String shopSeq = jobParameters.getString("shopSeq");
        if (!StringUtils.isEmpty(shopSeq)) {
            super.setShopSeq(Integer.valueOf(shopSeq));
        }

        try {
            // ①前処理
            init();

            // ②データ抽出
            getData();

            // ③tsv編集
            editData();

            // ④tsv連携
            outputTsv();

            // ⑥後処理
            afterProcess();

        } catch (AppLevelListException appe) {
            // 処理結果エラーメール送信
            batchProcessResult = UPDATE_RESULT_FAILED;
            sendAdministratorErrorMail(appe.getClass().getName());
            throw appe;

        } catch (Exception error) {
            LOGGER.error("例外処理が発生しました", error);
            // 処理結果エラーメール送信
            batchProcessResult = UPDATE_RESULT_FAILED;
            sendAdministratorErrorMail(error.getClass().getName());
            throw error;
        }
        executionContext.put(BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                        new BatchExitMessageDto(String.valueOf(batchProcessTotalCount),
                                                this.getReportString().toString()
                        )));
        return RepeatStatus.FINISHED;

    }

    /**
     * 初期処理
     */
    protected void init() throws Exception {
        // バッチ開始時間の設定
        this.currentTime = dateUtility.getCurrentTime();
        String dateFormat = dateUtility.format(currentTime, DATE_FORMAT);

        // ディレクトリの設定
        this.dataDir = PropertiesUtil.getSystemPropertiesValue("contentsfeed.output.datadir");
        this.workDir = PropertiesUtil.getSystemPropertiesValue("contentsfeed.output.workdir");
        this.backupDir = PropertiesUtil.getSystemPropertiesValue("contentsfeed.output.backupdir");

        if (!new File(workDir).exists()) {
            LOGGER.error("作業ディレクトリが存在しません。 > " + workDir);
            throw new FileNotFoundException();
        }
        if (!new File(backupDir).exists()) {
            LOGGER.error("バックアップディレクトリが存在しません。 > " + backupDir);
            throw new FileNotFoundException();
        }
        if (!isScpCommunicate() && !new File(dataDir).exists()) {
            LOGGER.error("連携ディレクトリが存在しません。 > " + dataDir);
            throw new FileNotFoundException();
        }

        // 作業フォルダ内ファイルの削除
        clearWorkDirectory();
        LOGGER.info("初期処理 > 作業フォルダ内ファイル削除：OK");

        // ファイル名の設定
        this.tsvFileName = PropertiesUtil.getSystemPropertiesValue("contentsfeed.output.tsv.file.id") + dateFormat
                           + TSV_SUFFIX;
        this.txtFileName = PropertiesUtil.getSystemPropertiesValue("contentsfeed.output.txt.file.id") + dateFormat
                           + TXT_SUFFIX;
        this.gzipFileName = tsvFileName + GZ_SUFFIX;

        if (isScpCommunicate()) {
            // 接続先情報を設定
            this.remoteHostName = PropertiesUtil.getSystemPropertiesValue("contentsfeed.output.send.remotehostname");
            this.port = Integer.parseInt(PropertiesUtil.getSystemPropertiesValue("contentsfeed.output.send.port"));
            this.userid = PropertiesUtil.getSystemPropertiesValue("contentsfeed.output.send.user");
            this.rsaPath = PropertiesUtil.getSystemPropertiesValue("contentsfeed.output.send.rsakey");

        } else {
            LOGGER.info("SCP通信フラグが「0:通信しない」のため、SCP通信は行いません。");
        }
    }

    /**
     * 作業フォルダのクリア
     */
    public void clearWorkDirectory() {
        File workDirectory = new File(workDir);
        File[] files = workDirectory.listFiles();
        if (files == null) {
            LOGGER.warn("対象ファイルが存在しませんでした。");
            return;
        }

        for (File tempFile : files) {
            tempFile.delete();
        }
    }

    /**
     * SCP通信要否を判定
     */
    public boolean isScpCommunicate() {
        String isCommunicate = PropertiesUtil.getSystemPropertiesValue("contentsfeed.output.scp.connect");
        return SCP_CONNECT_FLG.equals(isCommunicate);
    }

    /**
     * データを抽出する
     */
    protected void getData() {
        // 公開中かつユニサーチ連携フラグが「連携する」となっているコンテンツを取得
        this.resultList = freeAreaDao.getUkContentsFeedTsvList(currentTime);
        this.batchProcessTotalCount = resultList.size();
    }

    /**
     * データ編集
     */
    protected void editData() {
        this.resultList.forEach(e -> {
            convertTab(e);
            // 検索キーワード
            if (e.getSearchKeyword() != null) {
                setSearchKeywordInfo(e);
            }
        });
    }

    /**
     * ①両端の空白文字を取り除く
     * ②タブ文字を半角スペースに変換する
     * @param e コンテンツフィードTsvDto
     */
    protected void convertTab(UkContentsFeedTsvDto e) {
        if (!StringUtils.isEmpty(e.getFreeAreaTitle())) {
            String title = e.getFreeAreaTitle().replaceAll("^[\\s|　]+", "").replaceAll("[\\s|　]+$", "");
            e.setFreeAreaTitle(title.replaceAll("\\t", " "));
        }

        if (!StringUtils.isEmpty(e.getUkTransitionUrl())) {
            String transitionUrl = e.getUkTransitionUrl().replaceAll("^[\\s|　]+", "").replaceAll("[\\s|　]+$", "");
            e.setUkTransitionUrl(transitionUrl.replaceAll("\\t", " "));
        }

        if (!StringUtils.isEmpty(e.getUkContentImageUrl())) {
            String imageUrl = e.getUkContentImageUrl().replaceAll("^[\\s|　]+", "").replaceAll("[\\s|　]+$", "");
            e.setUkContentImageUrl(imageUrl.replaceAll("\\t", " "));
        }

        if (!StringUtils.isEmpty(e.getSearchKeyword())) {
            String searchKeyword = e.getSearchKeyword().replaceAll("^[\\s|　]+", "").replaceAll("[\\s|　]+$", "");
            e.setSearchKeyword(searchKeyword.replaceAll("\\t", " "));
        }
    }

    /**
     * 検索キーワードを^Aで連結する
     * @param e コンテンツフィードTsvDto
     */
    protected void setSearchKeywordInfo(UkContentsFeedTsvDto e) {
        String[] searchKeywordArray = e.getSearchKeyword().split("/");
        for (String searchKeyword : searchKeywordArray) {
            if (StringUtils.isEmpty(searchKeyword)) {
                continue;
            }
            if (e.getUkSearchKeyword() == null) {
                e.setUkSearchKeyword(searchKeyword);
            } else {
                e.setUkSearchKeyword(e.getUkSearchKeyword() + CTRLA + searchKeyword);
            }
        }
    }

    /**
     * tsv出力
     * <pre>
     *     １）tsvの出力
     *     ２）tsvをgzipに圧縮
     *     ３）txtの出力
     *     ４）連携ディレクトリへコピー
     *     ５）バックアップディレクトリへコピー
     * </pre>
     */
    protected void outputTsv() throws Exception {
        try {
            createTsv();
            LOGGER.info("tsv出力 > tsvファイルの作成：OK");

            createGzip();
            LOGGER.info("tsv出力 > gzipへの圧縮：OK");

            createTxt();
            LOGGER.info("tsv出力 > txtファイルの作成：OK");
        } catch (Exception e) {
            LOGGER.error("ファイルの連携に失敗しました。" + e);
            throw e;
        }
    }

    /**
     * tsv出力
     * @return 処理件数
     */
    protected int createTsv() throws IOException {
        // TSVファイルのフォーマットを設定
        CsvDownloadOptionDto optionDto =
                        new CsvDownloadOptionDto('"', "\t", "\n", null, false, StandardCharsets.UTF_8, true, false,
                                                 false
                        );

        // 出力ファイルの設定
        CsvExtractUtility csvExtractUtility =
                        new CsvExtractUtility(UkContentsFeedTsvDto.class, optionDto, null, workDir + tsvFileName,
                                              this.resultList
                        );

        return csvExtractUtility.outTsv();
    }

    /**
     * gzipへ圧縮する
     * @throws IOException IOException
     */
    protected void createGzip() throws IOException {
        try (FileInputStream fis = new FileInputStream(workDir + tsvFileName);
                        FileOutputStream fos = new FileOutputStream(workDir + gzipFileName);
                        GZIPOutputStream gzipOS = new GZIPOutputStream(fos)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                gzipOS.write(buffer, 0, bytesRead);
            }
        }
    }

    /**
     * txtファイル作成
     * @throws IOException　IOException
     */
    protected void createTxt() throws IOException {
        try (FileInputStream fis = new FileInputStream(workDir + gzipFileName);
                        BufferedWriter writer = new BufferedWriter(
                                        new OutputStreamWriter(new FileOutputStream(workDir + txtFileName),
                                                               StandardCharsets.UTF_8
                                        ))) {

            // doneファイルにMD5値を入れる
            String md5Hash = DigestUtils.md5DigestAsHex(fis);
            writer.write(md5Hash + "\n");
            writer.flush();
        }
    }

    /**
     * 後処理
     * <pre>
     *     １）連携ディレクトリへ移動
     *     ２）バックアップディレクトリへ移動
     *     ３）作業フォルダ内ファイルの削除
     *     ４）処理結果メールの送信＆結果レポートの出力
     * </pre>
     */
    protected void afterProcess() throws Exception {
        // １）連携ディレクトリへ移動
        if (isScpCommunicate()) {
            sendFile(workDir + gzipFileName);
            sendFile(workDir + txtFileName);
            LOGGER.info("後処理 > 連携ディレクトリ（外部）へ移動：OK");
        } else {
            moveFile(workDir + gzipFileName, dataDir + gzipFileName, false);
            moveFile(workDir + txtFileName, dataDir + txtFileName, false);
            LOGGER.info("後処理 > 連携ディレクトリ（内部）へ移動：OK");
        }

        // ２）バックアップディレクトリへ移動
        moveFile(workDir + gzipFileName, backupDir + gzipFileName, false);
        moveFile(workDir + txtFileName, backupDir + txtFileName, false);
        LOGGER.info("後処理 > バックアップディレクトリへ移動：OK");

        // ３）作業フォルダ内ファイルの削除
        clearWorkDirectory();
        LOGGER.info("後処理 > 作業フォルダ内ファイルの削除：OK");

        // ４）処理結果メールの送信＆結果レポートの出力
        this.batchProcessResult = UPDATE_RESULT_SUCCESS;
        createMailMessageResult();
        sendAdministratorMail(mailMessageResult);
        this.report(mailMessageResult);
    }

    /**
     * SCP連携する
     * @param fromPath コピー元
     */
    protected void sendFile(String fromPath) throws JSchException, SftpException {
        JSch jsch = new JSch();
        // 秘密鍵の設定
        jsch.addIdentity(rsaPath);

        // SSHセッションの作成
        Session session = jsch.getSession(userid, remoteHostName, port);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        // ファイルを転送
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        channelSftp.put(fromPath, dataDir);
        channelSftp.disconnect();

        session.disconnect();
    }

    /**
     * ファイル移動<br />
     *
     * @param fromPath  コピー元
     * @param toPath    コピー先
     * @param removeSrc true：コピー元削除 false:削除しない
     * @throws Exception コピーエラー
     */
    protected void moveFile(String fromPath, String toPath, boolean removeSrc) throws Exception {
        try {
            File fromFile = new File(fromPath);
            File toFile = new File(toPath);
            FileOperationUtility fileOperationUtility = ApplicationContextUtility.getBean(FileOperationUtility.class);
            fileOperationUtility.put(fromFile, toFile, removeSrc);
        } catch (Exception e) {
            // 移動失敗
            LOGGER.error("ファイルの移動に失敗しました。\r\nコピー元 :" + fromPath + "\r\nコピー先 :" + toPath, e);
            throw e;
        }
    }

    /**
     * 成功メール用の本文を作成する
     */
    protected void createMailMessageResult() {
        StringBuilder resultDetail = new StringBuilder();
        // 発送メール送信件数
        resultDetail.append("処理結果：");
        resultDetail.append(batchProcessResult);
        resultDetail.append("\n処理件数：");
        resultDetail.append(batchProcessTotalCount);
        resultDetail.append("件");
        resultDetail.append("\n\n連携ファイル");
        resultDetail.append("\n　TSVファイル名：");
        resultDetail.append(gzipFileName);
        resultDetail.append("\n　TXTファイル名：");
        resultDetail.append(txtFileName);
        // 処理結果メールメッセージに格納
        this.mailMessageResult = resultDetail.toString();
    }

    /**
     * 管理者向けメールを送信する
     *
     * @param result 結果レポート文字列
     */
    protected void sendAdministratorMail(final String result) {

        LOGGER.debug("処理結果メールの送信処理を開始します。");
        try {

            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            final Map<String, String> valueMap = new HashMap<>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            valueMap.put("SYSTEM", systemName);
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_CONTENTS_FEED_OUTPUT.getLabel());
            valueMap.put("RESULT", result);

            mailContentsMap.put("admin", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.CONTENTS_FEED_OUTPUT_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_CONTENTS_FEED_OUTPUT.getLabel() + "報告");
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);
            LOGGER.info("管理者へ通知メールを送信しました。");

        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);

        }
    }

    /**
     * 管理者向けエラーメールを送信する
     *
     * @param result 結果レポート文字列
     */
    protected void sendAdministratorErrorMail(final String result) {

        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            final Map<String, String> valueMap = new HashMap<>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            valueMap.put("SYSTEM", systemName);
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_CONTENTS_FEED_OUTPUT.getLabel());
            valueMap.put("RESULT", result);

            mailContentsMap.put("error", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.CONTENTS_FEED_OUTPUT_ERROR_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_CONTENTS_FEED_OUTPUT.getLabel() + "報告[*]");
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);

            LOGGER.info("管理者へ通知メールを送信しました。");
            this.report(new Timestamp(System.currentTimeMillis()) + " 例外が発生しました。ロールバックし、処理を終了します。");

        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);

        }
    }
}
