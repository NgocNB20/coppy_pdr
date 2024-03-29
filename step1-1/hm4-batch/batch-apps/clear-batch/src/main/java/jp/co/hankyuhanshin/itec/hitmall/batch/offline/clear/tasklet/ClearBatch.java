/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.file.TemporaryFileClearLogic;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.file.TemporaryFileZipLogic;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.record.AdminConfirmMailRecordClearLogic;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.record.ConfirmMailRecordClearLogic;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.record.FootPrintClearLogic;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.record.GoodsCartClearLogic;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.record.PreviewAccessControlRecordClearLogic;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.recordcountreport.AccessInfoGetRecordCountLogic;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.clear.logic.recordcountreport.AccessSearchKeywordGetRecordCountLogic;
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
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.env.Environment;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * クリアバッチ
 * 作成日：2021/04/01
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public class ClearBatch extends AbstractBatch {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClearBatch.class);

    /**
     * トランザクションマネージャ
     */
    private final PlatformTransactionManager transactionManager;

    /**
     * トランザクション定義
     */
    private final DefaultTransactionDefinition tranDefinition;

    /**
     * テンポラリファイルクリアロジック
     */
    private final TemporaryFileClearLogic temporaryFileClearLogic;

    /**
     * 確認メールクリアロジック
     */
    private final ConfirmMailRecordClearLogic confirmMailRecordClearLogic;

    /**
     * カートクリアロジック
     */
    private final GoodsCartClearLogic goodsCartClearLogic;

    /**
     * あしあとクリアロジック
     */
    private final FootPrintClearLogic footPrintClearLogic;

    /**
     * アクセス情報通知ロジック
     */
    private final AccessInfoGetRecordCountLogic accessInfoGetRecordCountLogic;

    /**
     * 検索キーワード通知クリアロジック
     */
    private final AccessSearchKeywordGetRecordCountLogic accessSearchKeywordGetRecordCountLogic;

    /**
     * テンポラリファイル圧縮ロジック
     */
    private final TemporaryFileZipLogic temporaryFileZipLogic;

    /**
     * 運営者用確認メールクリアロジック
     */
    public AdminConfirmMailRecordClearLogic adminConfirmMailRecordClearLogic;

    /**
     * テンポラリファイルクリア結果Map
     */
    private Map<String, String> resultTempFileClearMap;

    /**
     * テンポラリファイルクリア結果Map ※受注CSV用
     */
    private Map<String, String> resultTempFileClearMapForOrderCSV;

    /**
     * レコードクリア結果Map
     */
    private Map<String, Map<String, String>> resultRecordClearMap;

    /**
     * レコード件数通知取得結果Map
     */
    private Map<String, Map<String, String>> resultRecordCountReportMap;

    /**
     * 管理者用メール送信設定
     */
    private final InstantMailSetting mailSetting;

    /**
     * クリア対象ディレクトリ
     */
    private final String[] clearTemporaryDirectoryPath;

    /**
     * クリア対象ディレクトリ ※受注CSV用
     */
    private final String[] clearTemporaryDirectoryPathForOrderCSV;

    /**
     * クリアファイル指定日数
     */
    private final Integer specifiedDaysOfTemporaryFile;

    /**
     * dicon 設定：　クリアファイル指定日数 ※受注CSV用
     */
    private final Integer specifiedDaysOfTemporaryFileForOrderCSV;

    /**
     * 確認メールTBLレコードクリア指定日数
     */
    private final Integer specifiedDaysOfComfirmMail;

    /**
     * カート情報TBLレコードクリア指定日数
     */
    private final Integer specifiedDaysOfCartGoods;

    /**
     * あしあと情報TBLレコードクリア指定日数
     */
    private final Integer specifiedDaysOfFootPrint;

    /**
     * アクセス情報TBLレコード通知指定日数
     */
    private final Integer specifiedDaysOfAccessInfo;

    /**
     * アクセス検索TBLレコード通知指定日数
     */
    private final Integer specifiedDaysOfAccessSearchKeywords;

    /**
     * バッチログファイルパス
     */
    private final String[] batchLogPath;

    /**
     * 圧縮後バッチログファイル出力ファイルパス
     */
    private final String batchLogOutputPath;

    /**
     * バッチログファイル圧縮指定日数（日次）
     */
    private final Integer dailyTerm;

    /**
     * バッチログファイル圧縮指定日数（月次）
     */
    private final Integer monthlyTerm;

    /**
     * バッチログファイル圧縮対象指定月（月次）
     */
    private final Integer targetMonthlyTerm;

    /**
     * プレビューアクセスクリアロジック
     */
    private final PreviewAccessControlRecordClearLogic previewAccessControlRecodeClearLogic;

    /**
     * メール送信サービス
     */
    private final MailSendService mailSendService;

    /**
     * コンストラクタ。
     */
    public ClearBatch(Environment environment) {

        // トランザクション手動管理
        this.transactionManager = ApplicationContextUtility.getBean(PlatformTransactionManager.class);
        tranDefinition = new DefaultTransactionDefinition();
        tranDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        this.temporaryFileClearLogic = ApplicationContextUtility.getBean(TemporaryFileClearLogic.class);
        this.confirmMailRecordClearLogic = ApplicationContextUtility.getBean(ConfirmMailRecordClearLogic.class);
        this.goodsCartClearLogic = ApplicationContextUtility.getBean(GoodsCartClearLogic.class);
        this.footPrintClearLogic = ApplicationContextUtility.getBean(FootPrintClearLogic.class);
        this.accessInfoGetRecordCountLogic = ApplicationContextUtility.getBean(AccessInfoGetRecordCountLogic.class);
        this.accessSearchKeywordGetRecordCountLogic =
                        ApplicationContextUtility.getBean(AccessSearchKeywordGetRecordCountLogic.class);
        this.temporaryFileZipLogic = ApplicationContextUtility.getBean(TemporaryFileZipLogic.class);
        this.adminConfirmMailRecordClearLogic =
                        ApplicationContextUtility.getBean(AdminConfirmMailRecordClearLogic.class);
        this.previewAccessControlRecodeClearLogic =
                        ApplicationContextUtility.getBean(PreviewAccessControlRecordClearLogic.class);
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);

        // 管理者用メール送信設定
        this.mailSetting = new InstantMailSetting(environment.getProperty("mail.setting.clear.smtp.server"),
                                                  environment.getProperty("mail.setting.clear.mail.from"), null, null,
                                                  Collections.singletonList(environment.getProperty(
                                                                  "mail.setting.clear.mail.receivers"))
        );

        // クリア対象ディレクトリ
        this.clearTemporaryDirectoryPath = new String[] {environment.getProperty("batch.file.path"),
                        environment.getProperty("goodsimage.error.directory"),
                        environment.getProperty("goodsimage.csv.backup.directory"),
                        environment.getProperty("sitemapxmloutput.output.backupdir")};
        // クリア対象ディレクトリ ※受注CSV用
        this.clearTemporaryDirectoryPathForOrderCSV =
                        new String[] {environment.getProperty("orderCsvAsynchronous.file.path")};

        // クリアファイル指定日数
        this.specifiedDaysOfTemporaryFile = environment.getProperty("specified.days.of.temporary.file", Integer.class);
        // クリアファイル指定日数 ※受注CSV用
        this.specifiedDaysOfTemporaryFileForOrderCSV =
                        environment.getProperty("specified.days.of.temporary.file.for.ordercsv", Integer.class);
        // 確認メールTBLレコードクリア指定日数
        this.specifiedDaysOfComfirmMail = environment.getProperty("specified.days.of.comfirm.mail", Integer.class);
        // カート情報TBLレコードクリア指定日数
        this.specifiedDaysOfCartGoods = environment.getProperty("specified.days.of.cart.goods", Integer.class);
        // あしあと情報TBLレコードクリア指定日数 */
        this.specifiedDaysOfFootPrint = environment.getProperty("specified.days.of.foot.print", Integer.class);

        // アクセス情報TBLレコード通知指定日数
        this.specifiedDaysOfAccessInfo = environment.getProperty("specified.days.of.access.info", Integer.class);
        // アクセス検索TBLレコード通知指定日数
        this.specifiedDaysOfAccessSearchKeywords =
                        environment.getProperty("specified.days.of.access.search.keywords", Integer.class);

        // バッチログファイルパス
        this.batchLogPath = new String[] {environment.getProperty("logback.FileAppender.output.dir"),
                        environment.getProperty("zip.target.batch.log.dir")};
        // 圧縮後バッチログファイル出力ファイルパス
        this.batchLogOutputPath = environment.getProperty("zip.batch.log.output.dir");

        // バッチログファイル圧縮指定日数（日次）
        this.dailyTerm = environment.getProperty("daily.term", Integer.class);
        // バッチログファイル圧縮指定日数（月次）
        this.monthlyTerm = environment.getProperty("monthly.term", Integer.class);
        // バッチログファイル圧縮対象指定月（月次）
        this.targetMonthlyTerm = environment.getProperty("target.monthly.term", Integer.class);
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

        LOGGER.info("クリアバッチ処理を開始します。");

        JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobParameters();

        String administratorId = jobParameters.getString("administratorId");
        String shopSeq = jobParameters.getString("shopSeq");

        if (!StringUtils.isEmpty(shopSeq)) {
            super.setShopSeq(Integer.valueOf(shopSeq));
        } else {
            // 異常終了メールを送信します。
            sendAdministratorErrorMail("ショップSEQが設定されていません。");
            LOGGER.info("ショップSEQが設定されていません。");
            return RepeatStatus.FINISHED; // 異常終了
        }

        try {
            // ファイル削除メソッド実行
            execClearFile();

            // レコード削除メソッド実行
            execClearRecords();

            // バッチログファイル圧縮（日次）
            temporaryFileZipLogic.execute(
                            TemporaryFileZipLogic.ZIP_MODE_YMD, batchLogPath, dailyTerm, batchLogOutputPath,
                            targetMonthlyTerm
                                         );

            // バッチログファイル圧縮（月次）
            if (monthlyTerm > 28) {
                LOGGER.warn("指定された日数（" + monthlyTerm + "日）は、有効な日数ではありません。");
            } else {
                DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
                Timestamp baseTime = dateUtility.getCurrentTime();
                SimpleDateFormat formatter = new SimpleDateFormat("d");
                if ((formatter.format(baseTime)).equals(monthlyTerm.toString())) {
                    // specifiedDaysは、使用しないので「－１」設定
                    // 圧縮後のログ出力先ディレクトリが指定されている場合は、圧縮対象ディレクトリに圧縮後のログ出力先ディレクトリを指定する
                    if (batchLogOutputPath.isEmpty()) {
                        temporaryFileZipLogic.execute(
                                        TemporaryFileZipLogic.ZIP_MODE_YM, batchLogPath, -1, batchLogOutputPath,
                                        targetMonthlyTerm
                                                     );
                    } else {
                        temporaryFileZipLogic.execute(
                                        TemporaryFileZipLogic.ZIP_MODE_YM, new String[] {batchLogOutputPath}, -1,
                                        batchLogOutputPath, targetMonthlyTerm
                                                     );
                    }
                }
            }

            // レコード通知用メソッドの実行
            execReportRecordsCount();

        } catch (Exception e) {
            // 異常終了メールを送信します。
            sendAdministratorErrorMail("クリア処理中に例外が発生しました。");
            LOGGER.warn("クリア処理中に例外が発生しました。", e);

            // 異常終了
            throw e;
        }
        // 後処理
        // メール送信メソッドを実行
        sendAdministratorMail();
        LOGGER.info("クリアバッチ処理を終了しました。");

        return RepeatStatus.FINISHED;
    }

    /**
     * ファイルクリア実行
     */
    protected void execClearFile() {

        LOGGER.info("▼テンポラリファイルクリア処理を開始します。");
        resultTempFileClearMap = temporaryFileClearLogic.execute(this.clearTemporaryDirectoryPath,
                                                                 this.specifiedDaysOfTemporaryFile
                                                                );
        LOGGER.info("▲テンポラリファイルクリア処理を終了しました。");

        LOGGER.info("▼テンポラリファイルクリア処理を開始します。※受注CSV用");
        resultTempFileClearMapForOrderCSV = temporaryFileClearLogic.execute(this.clearTemporaryDirectoryPathForOrderCSV,
                                                                            this.specifiedDaysOfTemporaryFileForOrderCSV
                                                                           );
        LOGGER.info("▲テンポラリファイルクリア処理を終了しました。※受注CSV用");
    }

    /**
     * レコードクリア実行
     *
     * @throws Exception 実行例外
     */
    protected void execClearRecords() throws Exception {

        TransactionStatus status = null;
        resultRecordClearMap = new HashMap<>();
        try {
            // 確認メールクリア実行
            LOGGER.info("▼確認メールTBLのレコードクリア処理を開始します。");
            // トラン開始
            status = transactionManager.getTransaction(tranDefinition);
            final Map<String, String> confirmMailResultMap =
                            confirmMailRecordClearLogic.execute(this.getShopSeq(), this.specifiedDaysOfComfirmMail);
            // トラン終了
            exitTransaction(confirmMailResultMap, status);
            LOGGER.info("▲確認メールTBLのレコードクリア処理を終了しました。");

            // カートクリア実行
            LOGGER.info("▼カートTBLのレコードクリア処理を開始します。");
            // トラン開始
            status = transactionManager.getTransaction(tranDefinition);
            final Map<String, String> goodsCartResultMap =
                            goodsCartClearLogic.execute(this.getShopSeq(), this.specifiedDaysOfCartGoods);
            // トラン終了
            exitTransaction(goodsCartResultMap, status);
            LOGGER.info("▲カートTBLのレコードクリア処理を終了しました。");

            // あしあとクリア実行
            LOGGER.info("▼あしあとTBLのレコードクリア処理を開始します。");
            // トラン開始
            status = transactionManager.getTransaction(tranDefinition);
            final Map<String, String> footPrintResultMap =
                            footPrintClearLogic.execute(this.getShopSeq(), this.specifiedDaysOfFootPrint);
            // トラン終了
            exitTransaction(footPrintResultMap, status);
            LOGGER.info("▲あしあとTBLのレコードクリア処理を終了しました。");

            // 各TBL結果ごとに、纏めてMapへ格納。
            resultRecordClearMap.put("confirmMail", confirmMailResultMap);
            resultRecordClearMap.put("goodsCart", goodsCartResultMap);
            resultRecordClearMap.put("footPrint", footPrintResultMap);

        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            if (status != null && !status.isCompleted()) {
                transactionManager.rollback(status);
            }
            throw e;
        } finally {
            if (status != null && !status.isCompleted()) {
                transactionManager.commit(status);
            }
        }
    }

    /**
     * レコードカウント取得実行
     *
     * @throws Exception 実行例外
     */
    protected void execReportRecordsCount() throws Exception {

        resultRecordCountReportMap = new HashMap<>();

        // アクセス情報レコード件数取得実行
        LOGGER.info("▼アクセス情報通知処理を開始します。");
        final Map<String, String> accessInfoResultMap =
                        accessInfoGetRecordCountLogic.execute(this.getShopSeq(), this.specifiedDaysOfAccessInfo);
        LOGGER.info("▲アクセス情報通知処理を終了しました。");

        // 検索キーワード情報レコード件数取得実行
        LOGGER.info("▼検索キーワードTBLのレコードクリア処理を開始します。");
        final Map<String, String> accessSearchKeywordResultMap =
                        accessSearchKeywordGetRecordCountLogic.execute(this.getShopSeq(),
                                                                       this.specifiedDaysOfAccessSearchKeywords
                                                                      );
        LOGGER.info("▲検索キーワードTBLのレコードクリア処理を終了しました。");

        // 各TBL結果ごとに、纏めてMapへ格納。
        resultRecordCountReportMap.put("accessInfo", accessInfoResultMap);
        resultRecordCountReportMap.put("accessSearchKeyword", accessSearchKeywordResultMap);

    }

    /**
     * トランザクション終了
     *
     * @param result     結果Map
     * @param tranStatus トランザクションステータス
     * @throws Exception 実行例外
     */
    protected void exitTransaction(final Map<String, String> result, TransactionStatus tranStatus) throws Exception {

        if (result.get("STATUS").equals("0") && Integer.parseInt(result.get("RECORD_CLEAR_COUNT")) > 0) {
            transactionManager.commit(tranStatus);
            LOGGER.info("コミットしました。");
        } else if (result.get("STATUS").equals("0") && Integer.parseInt(result.get("RECORD_CLEAR_COUNT")) <= 0) {
            transactionManager.rollback(tranStatus);
            LOGGER.info("対象データがないため、ロールバックしました。");
        } else if (result.get("STATUS").equals("1")) {
            transactionManager.rollback(tranStatus);
            LOGGER.info("ロールバックしました。");
        } else {
            transactionManager.rollback(tranStatus);
            LOGGER.info("ロールバックしました。");
        }
    }

    /**
     * 管理者向け処理完了メール（正常終了メール）を送信する。
     */
    protected void sendAdministratorMail() {
        LOGGER.info("管理者向けメール送信処理を開始します。");

        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            final Map<String, String> valueMap = new HashMap<>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            // プレースホルダーへ結果セット
            valueMap.put("SYSTEM", systemName);
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_CLEAR.getLabel());
            valueMap.put("SHOP_SEQ", this.getShopSeq().toString());
            valueMap.put("FILE_CLEAR_STATUS", this.resultTempFileClearMap.get("STATUS_MESSAGE"));
            int fileClearCount =
                            Integer.parseInt(this.resultTempFileClearMap.get("FILE_CLEAR_COUNT")) + Integer.parseInt(
                                            this.resultTempFileClearMapForOrderCSV.get("FILE_CLEAR_COUNT"));
            valueMap.put("FILE_CLEAR_COUNT", String.valueOf(fileClearCount));

            valueMap.put(
                            "CONFIRMMAIL_RECORD_CLEAR_STATUS",
                            this.resultRecordClearMap.get("confirmMail").get("STATUS_MESSAGE")
                        );
            valueMap.put(
                            "CONFIRMMAIL_RECORD_CLEAR_COUNT",
                            this.resultRecordClearMap.get("confirmMail").get("RECORD_CLEAR_COUNT")
                        );
            valueMap.put(
                            "GOODSCART_RECORD_CLEAR_STATUS",
                            this.resultRecordClearMap.get("goodsCart").get("STATUS_MESSAGE")
                        );
            valueMap.put(
                            "GOODSCART_RECORD_CLEAR_COUNT",
                            this.resultRecordClearMap.get("goodsCart").get("RECORD_CLEAR_COUNT")
                        );
            valueMap.put(
                            "FOOTPRINT_RECORD_CLEAR_STATUS",
                            this.resultRecordClearMap.get("footPrint").get("STATUS_MESSAGE")
                        );
            valueMap.put(
                            "FOOTPRINT_RECORD_CLEAR_COUNT",
                            this.resultRecordClearMap.get("footPrint").get("RECORD_CLEAR_COUNT")
                        );
            valueMap.put(
                            "ACCESSINFO_RECORD_REPORT_STATUS",
                            this.resultRecordCountReportMap.get("accessInfo").get("STATUS_MESSAGE")
                        );
            valueMap.put(
                            "ACCESSINFO_ALL_RECORD_COUNT",
                            this.resultRecordCountReportMap.get("accessInfo").get("RECORD_COUNT")
                        );
            valueMap.put(
                            "ACCESSINFO_RECORD_COUNT",
                            this.resultRecordCountReportMap.get("accessInfo").get("SPECIFIEDDAYS_RECORD_COUNT")
                        );
            valueMap.put(
                            "ACCESSSEARCHKEYWORD_RECORD_REPORT_STATUS",
                            this.resultRecordCountReportMap.get("accessSearchKeyword").get("STATUS_MESSAGE")
                        );
            valueMap.put(
                            "ACCESSSEARCHKEYWORD_ALL_RECORD_COUNT",
                            this.resultRecordCountReportMap.get("accessSearchKeyword").get("RECORD_COUNT")
                        );
            valueMap.put(
                            "ACCESSSEARCHKEYWORD_RECORD_COUNT",
                            this.resultRecordCountReportMap.get("accessSearchKeyword").get("SPECIFIEDDAYS_RECORD_COUNT")
                        );
            if (LOGGER.isDebugEnabled()) {
                valueMap.put("DEBUG", "1");
            } else {
                valueMap.put("DEBUG", "0");
            }

            mailContentsMap.put("admin", valueMap);

            // 削除失敗ファイルの一覧
            List<String> deleteFailFiles = new ArrayList<>();
            for (String key : this.resultTempFileClearMap.keySet()) {
                if (key.startsWith("DELETE_FAIL_FILE_")) {
                    deleteFailFiles.add(this.resultTempFileClearMap.get(key));
                }
            }
            for (String key : this.resultTempFileClearMapForOrderCSV.keySet()) {
                if (key.startsWith("DELETE_FAIL_FILE_")) {
                    deleteFailFiles.add(this.resultTempFileClearMapForOrderCSV.get(key));
                }
            }
            mailContentsMap.put("deleteFailFiles", deleteFailFiles);

            mailDto.setMailTemplateType(HTypeMailTemplateType.CLEAR_RESULT_ADMINISTRATOR_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_CLEAR.getLabel() + "報告");
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);
            LOGGER.info("管理者へ通知メールを送信しました。");
        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);
        }

        LOGGER.info("管理者向けメール送信処理を終了しました。");
    }

    /**
     * 管理者向け処理完了メール（異常終了メール）を送信する。
     *
     * @param result 結果レポート文字列
     * @return 成否
     */
    protected boolean sendAdministratorErrorMail(String result) {
        LOGGER.info("管理者向けクリアバッチ異常メール送信処理を開始します。");

        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            final Map<String, String> valueMap = new HashMap<>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            // プレースホルダーへ結果セット
            valueMap.put("SYSTEM", systemName);
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_CLEAR.getLabel());
            valueMap.put("RESULT", result);

            if (LOGGER.isDebugEnabled()) {
                valueMap.put("DEBUG", "1");
            } else {
                valueMap.put("DEBUG", "0");
            }

            mailContentsMap.put("error", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.CLEAR_RESULT_ADMINISTRATOR_ERROR_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_CLEAR.getLabel() + "報告");
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);
            LOGGER.info("管理者へ通知メールを送信しました。");
        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);
            return false;
        }

        return true;

    }

}
