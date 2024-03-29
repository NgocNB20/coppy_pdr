/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.goodsfeedoutput.tasklet;

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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.category.CategoryDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvDownloadOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkGoodsFeedCategoryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.ukapi.UkGoodsFeedTsvDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDetailsGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupTableLockLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.InstantMailSetting;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

/**
 * 商品フィード出力バッチ
 * @author tt32117
 */
public class GoodsFeedOutputBatch extends AbstractBatch {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsFeedOutputBatch.class);

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
     * 商品グループDao
     */
    private final GoodsGroupDao goodsGroupDao;

    /**
     * カテゴリDao
     */
    private final CategoryDao categoryDao;

    /**
     * 商品グループレコードロックLogic
     */
    private final GoodsGroupTableLockLogic goodsGroupTableLockLogic;

    /**
     * トランザクションマネージャ
     */
    private final PlatformTransactionManager transactionManager;

    /**
     * トランザクション定義
     */
    private final DefaultTransactionDefinition tranDefinition;

    /**
     * 商品グループ詳細取得Logic
     */
    private final GoodsGroupDetailsGetByCodeLogic goodsGroupDetailsGetByCodeLogic;

    /**
     * 商品グループ更新Logic
     */
    private final GoodsGroupUpdateLogic goodsGroupUpdateLogic;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    /**
     * カテゴリMap
     */
    private final Map<Integer, UkGoodsFeedCategoryDto> categoryDtoMap;

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
    protected List<UkGoodsFeedTsvDto> resultList;

    /**
     * 更新用商品グループList
     */
    protected Set<String> updateGoodsGroupList;

    /**
     * 更新用商品グループMap
     */
    protected Map<String, String> updateGoodsGroupMap;

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
    public GoodsFeedOutputBatch(Environment environment) {
        this.goodsGroupDao = ApplicationContextUtility.getBean(GoodsGroupDao.class);
        this.categoryDao = ApplicationContextUtility.getBean(CategoryDao.class);
        this.goodsGroupTableLockLogic = ApplicationContextUtility.getBean(GoodsGroupTableLockLogic.class);
        // トランザクション手動管理
        this.transactionManager = ApplicationContextUtility.getBean(PlatformTransactionManager.class);
        tranDefinition = new DefaultTransactionDefinition();
        tranDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        this.goodsGroupDetailsGetByCodeLogic = ApplicationContextUtility.getBean(GoodsGroupDetailsGetByCodeLogic.class);
        this.goodsGroupUpdateLogic = ApplicationContextUtility.getBean(GoodsGroupUpdateLogic.class);
        this.dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        this.categoryDtoMap = new HashMap<>();
        this.resultList = new ArrayList<>();
        this.updateGoodsGroupList = new HashSet<>();
        this.updateGoodsGroupMap = new HashMap<>();
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.exitMessageUtil = new BatchExitMessageUtil();
        this.mailSetting = new InstantMailSetting(environment.getProperty("mail.setting.goodsfeed.output.smtp.server"),
                                                  environment.getProperty("mail.setting.goodsfeed.output.mail.from"),
                                                  null, null, Collections.singletonList(
                        environment.getProperty("mail.setting.goodsfeed.output.mail.receivers"))
        );
    }

    /**
     * 商品フィード出力
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

            // ③商品表示名更新
            updateGoodsInfo();

            // ④tsv編集
            editData();

            // ⑤tsv連携
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
        this.dataDir = PropertiesUtil.getSystemPropertiesValue("goodsfeed.output.datadir");
        this.workDir = PropertiesUtil.getSystemPropertiesValue("goodsfeed.output.workdir");
        this.backupDir = PropertiesUtil.getSystemPropertiesValue("goodsfeed.output.backupdir");

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
        this.tsvFileName = PropertiesUtil.getSystemPropertiesValue("goodsfeed.output.tsv.file.id") + dateFormat
                           + TSV_SUFFIX;
        this.txtFileName = PropertiesUtil.getSystemPropertiesValue("goodsfeed.output.txt.file.id") + dateFormat
                           + TXT_SUFFIX;
        this.gzipFileName = tsvFileName + GZ_SUFFIX;

        if (isScpCommunicate()) {
            // 接続先情報を設定
            this.remoteHostName = PropertiesUtil.getSystemPropertiesValue("goodsfeed.output.send.remotehostname");
            this.port = Integer.parseInt(PropertiesUtil.getSystemPropertiesValue("goodsfeed.output.send.port"));
            this.userid = PropertiesUtil.getSystemPropertiesValue("goodsfeed.output.send.user");
            this.rsaPath = PropertiesUtil.getSystemPropertiesValue("goodsfeed.output.send.rsakey");

        } else {
            LOGGER.info("SCP通信フラグが「0:通信しない」のため、SCP通信は行いません。");
        }
    }

    public void lock() {
        goodsGroupTableLockLogic.executeWait();
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
        String isCommunicate = PropertiesUtil.getSystemPropertiesValue("goodsfeed.output.scp.connect");
        return SCP_CONNECT_FLG.equals(isCommunicate);
    }

    /**
     * データを抽出する
     */
    protected void getData() {
        // 公開中かつ心意気でない商品を取得（削除商品も除外）
        this.resultList = goodsGroupDao.getUkGoodsFeedTsvList(currentTime);
        this.batchProcessTotalCount = resultList.size();

        // 連携用カテゴリ情報を作成する（^Eで親カテゴリを連結）
        List<UkGoodsFeedCategoryDto> categoryList = categoryDao.getUkGoodsFeedCategory(CTRLE);
        for (UkGoodsFeedCategoryDto dto : categoryList) {
            this.categoryDtoMap.put(dto.getCategorySeq(), dto);
        }
    }

    protected void updateGoodsInfo() {
        this.resultList.forEach(e -> {

            if (this.updateGoodsGroupList.contains(e.getGoodsGroupCode())) {
                e.setGoodsGroupName(this.updateGoodsGroupMap.get(e.getGoodsGroupCode()));
                return;
            }
            this.updateGoodsGroupList.add(e.getGoodsGroupCode());

            TransactionStatus status = null;

            try {

                status = transactionManager.getTransaction(tranDefinition);
                // テーブルロック
                // 更新に失敗しないようテーブルロックはWAIT。
                // 管理画面での処理はNOWAITかつ、1件ずつコミットするのでデッドロックにはならない。
                lock();
                GoodsGroupEntity entity =
                                goodsGroupDetailsGetByCodeLogic.getGoodsGroup(getShopSeq(), e.getGoodsGroupCode(), null,
                                                                              HTypeSiteType.BACK, null
                                                                             );
                // 編集してセット
                Timestamp openStartTime1 = entity.getGoodsGroupName1OpenStartTime();
                Timestamp openStartTime2 = entity.getGoodsGroupName2OpenStartTime();

                if (openStartTime1 != null && openStartTime2 != null) {
                    if (currentTime.after(openStartTime1) && currentTime.after(openStartTime2)) {
                        if (openStartTime1.equals(openStartTime2) || openStartTime1.after(openStartTime2)) {
                            // 同じ日時であれば公開開始日時１が優先される
                            setGoodsGroupName(entity, e, entity.getGoodsGroupName1());
                        } else {
                            setGoodsGroupName(entity, e, entity.getGoodsGroupName2());
                        }
                    } else if (currentTime.after(openStartTime1)) {
                        setGoodsGroupName(entity, e, entity.getGoodsGroupName1());
                    } else if (currentTime.after(openStartTime2)) {
                        setGoodsGroupName(entity, e, entity.getGoodsGroupName2());
                    } else {
                        // 両方未来日のケースなし
                    }
                } else if (openStartTime1 != null && currentTime.after(openStartTime1)) {
                    setGoodsGroupName(entity, e, entity.getGoodsGroupName1());
                } else if (openStartTime2 != null && currentTime.after(openStartTime2)) {
                    setGoodsGroupName(entity, e, entity.getGoodsGroupName2());
                } else {
                    // 両方nullのケースなし
                }
                goodsGroupUpdateLogic.execute(entity);
                transactionManager.commit(status);

            } catch (Exception exception) {
                LOGGER.info("商品表示名更新：NG（商品グループコード：" + e.getGoodsGroupCode() + "）");
                LOGGER.info(exception.getMessage());
                transactionManager.rollback(status);
            }
        });
        LOGGER.info("商品表示名更新：OK（" + updateGoodsGroupList.size() + "件）");
    }

    /**
     * 商品表示名をセットする
     * @param entity 商品グループEntity
     * @param e 商品フィードTsvDto
     * @param goodsGroupName 商品表示名
     */
    protected void setGoodsGroupName(GoodsGroupEntity entity, UkGoodsFeedTsvDto e, String goodsGroupName) {
        entity.setGoodsGroupName(goodsGroupName);
        this.updateGoodsGroupMap.put(e.getGoodsGroupCode(), goodsGroupName);
        e.setGoodsGroupName(goodsGroupName);
    }

    /**
     * データ編集
     */
    protected void editData() {
        this.resultList.forEach(e -> {
            // 文字列を含むものに関して変換処理を行う
            e.setGoodsGroupName(getConvertData(e.getGoodsGroupName()));
            e.setGoodsPreDiscountPrice(getConvertData(e.getGoodsPreDiscountPrice()));
            e.setCategoryNameList(getConvertData(e.getCategoryNameList()));
            e.setGoodsNote1(getConvertData(e.getGoodsNote1()));
            e.setGoodsNote1Sub(getConvertData(e.getGoodsNote1Sub()));
            e.setSearchKeyword(getConvertData(e.getSearchKeyword()));
            e.setUnitTitle1(getConvertData(e.getUnitTitle1()));
            e.setUnitTitle2(getConvertData(e.getUnitTitle2()));
            e.setUnitValue1(getConvertData(e.getUnitValue1()));
            e.setUnitValue2(getConvertData(e.getUnitValue2()));
            // カテゴリ情報
            if (e.getCategorySeqList() != null) {
                setCategoryInfo(e);
            }
            // 検索キーワード
            if (e.getSearchKeyword() != null) {
                setSearchKeywordInfo(e);
            }
            // 商品概要
            Timestamp openStartTime1 = e.getGoodsNote1OpenStartTime();
            Timestamp openStartTime2 = e.getGoodsNote1SubOpenStartTime();

            if (openStartTime1 != null && openStartTime2 != null) {
                if (currentTime.after(openStartTime1) && currentTime.after(openStartTime2)) {
                    if (openStartTime1.equals(openStartTime2) || openStartTime1.after(openStartTime2)) {
                        // 同じ日時であれば公開開始日時１が優先される
                        e.setItemOverview(e.getGoodsNote1());
                    } else {
                        e.setItemOverview(e.getGoodsNote1Sub());
                    }
                } else if (currentTime.after(openStartTime1)) {
                    e.setItemOverview(e.getGoodsNote1());
                } else if (currentTime.after(openStartTime2)) {
                    e.setItemOverview(e.getGoodsNote1Sub());
                } else {
                    // 両方未来日のケースはあるが、値は連携しない
                }
            } else if (openStartTime1 != null && currentTime.after(openStartTime1)) {
                e.setItemOverview(e.getGoodsNote1());
            } else if (openStartTime2 != null && currentTime.after(openStartTime2)) {
                e.setItemOverview(e.getGoodsNote1Sub());
            } else {
                // 両方nullまたは未来日の場合は、値がないので連携なし
            }

            // 医薬品フラグ（薬品区分から変換）
            e.setDrugFlag(e.getGoodsClassType().getDrugFlag());
            // 新着日付
            // タイムゾーン変換すると時間が変わってしまうのでZを付与する形で連携する。
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            e.setNewDate(e.getTmpNewDate().toLocalDateTime().format(formatter) + "Z");
            // 値引後商品価格（NULLで保持されているのでNULLの場合は値引前商品価格で置換）
            if (StringUtils.isEmpty(e.getPreDiscountPriceLow())) {
                e.setPreDiscountPriceLow(e.getGoodsPriceInTaxLow());
            }
        });
    }

    /**
     * ①両端の空白文字を取り除く
     * ②タブ文字を半角スペースに変換する
     * @param orgString 変換前文字列
     */
    protected String getConvertData(String orgString) {
        if (StringUtils.isEmpty(orgString)) {
            return orgString;
        }
        return orgString.replaceAll("^[\\s|　]+", "").replaceAll("[\\s|　]+$", "").replaceAll("\\t", " ");
    }

    /**
     * カテゴリ情報を^Aで連携する
     * @param e 商品フィードTsvDto
     */
    protected void setCategoryInfo(UkGoodsFeedTsvDto e) {
        String[] categorySeqArray = e.getCategorySeqList().split("/");
        for (String categorySeq : categorySeqArray) {
            if (StringUtil.isEmpty(categorySeq)) {
                continue;
            }
            String categoryIdList = this.categoryDtoMap.get(Integer.valueOf(categorySeq)).getCategoryIdList();
            String categoryNameList = this.categoryDtoMap.get(Integer.valueOf(categorySeq)).getCategoryNameList();
            String categoryOrderList = this.categoryDtoMap.get(Integer.valueOf(categorySeq)).getCategoryOrderList();

            // カテゴリIDリスト
            if (e.getCategoryIdList() == null) {
                e.setCategoryIdList(categoryIdList);
            } else {
                e.setCategoryIdList(e.getCategoryIdList() + CTRLA + categoryIdList);
            }
            // カテゴリ名リスト
            if (e.getCategoryNameList() == null) {
                e.setCategoryNameList(categoryNameList);
            } else {
                e.setCategoryNameList(e.getCategoryNameList() + CTRLA + categoryNameList);
            }
            // カテゴリ順リスト
            if (e.getCategoryOrderList() == null) {
                e.setCategoryOrderList(categoryOrderList);
            } else {
                e.setCategoryOrderList(e.getCategoryOrderList() + CTRLA + categoryOrderList);
            }
        }
    }

    /**
     * 検索キーワードを^Aで連結する
     * @param e 商品フィードTsvDto
     */
    protected void setSearchKeywordInfo(UkGoodsFeedTsvDto e) {
        String[] searchKeywordArray = e.getSearchKeyword().split("/");
        for (String searchKeyword : searchKeywordArray) {
            if (StringUtil.isEmpty(searchKeyword)) {
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
                        new CsvExtractUtility(UkGoodsFeedTsvDto.class, optionDto, null, workDir + tsvFileName,
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
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_GOODS_FEED_OUTPUT.getLabel());
            valueMap.put("RESULT", result);

            mailContentsMap.put("admin", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.GOODS_FEED_OUTPUT_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_GOODS_FEED_OUTPUT.getLabel() + "報告");
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
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_GOODS_FEED_OUTPUT.getLabel());
            valueMap.put("RESULT", result);

            mailContentsMap.put("error", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.GOODS_FEED_OUTPUT_ERROR_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_GOODS_FEED_OUTPUT.getLabel() + "報告[*]");
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);

            LOGGER.info("管理者へ通知メールを送信しました。");
            this.report(new Timestamp(System.currentTimeMillis()) + " 例外が発生しました。ロールバックし、処理を終了します。");

        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);

        }
    }

}
