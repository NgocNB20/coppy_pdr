package jp.co.hankyuhanshin.itec.hitmall.batch.online.goods.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDisplayStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitImageFlag;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsImageDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupImageDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvDownloadOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsImageOpenstatusDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsImageProcErrorCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsImageProcSuccessCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupImageOpenstatusDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.AttachFileDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.DiffUtil;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品画像更新バッチ
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public class GoodsImageUpdateBatch extends AbstractBatch {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsImageUpdateBatch.class);

    /**
     * 管理者用メール送信設定
     */
    private final InstantMailSetting mailSetting;

    /**
     * メール送信サービス
     */
    private final MailSendService mailSendService;

    /**
     * 処理日時（ファイル出力日時）
     */
    private Timestamp outputTime;

    /************
     * ディレクトリ
     ************/
    /**
     * 画像ディレクトリ
     */
    private String imageDir;

    /**
     * 画像追加ディレクトリ
     */
    private String inputDir;

    /**
     * エラーディレクトリ
     */
    private String errorDir;

    /**
     * バックアップディレクトリ
     */
    private String backupDir;

    /**
     * 作業ディレクトリ
     */
    private String workDir;

    /************
     * 集計
     ************/
    /**
     * 処理結果出力用リスト 商品画像処理CSV
     */
    private List<GoodsImageProcSuccessCsvDto> procSuccessCsvDtoList;

    /**
     * 処理結果出力用リスト 商品画像処理エラーCSV
     */
    private List<GoodsImageProcErrorCsvDto> procErrorCsvDtoList;

    /**
     * 集計用件数リスト 商品管理番号 合計件数
     */
    private Map<String, String> cntGoodsGroupSumMap;

    /**
     * 集計用件数リスト 商品管理番号 商品管理番号が存在しない
     */
    private Map<String, String> cntGoodsGroupNotExistMap;

    /**
     * 集計用件数リスト 商品管理番号 画像ファイル名が不適切
     */
    private Map<String, String> cntGoodsGroupFileNameNotMatchMap;

    /**
     * 集計用件数リスト 商品管理番号 商品が削除されている
     */
    private Map<String, String> cntGoodsGroupOpenStateDelMap;

    /**
     * 集計用件数リスト 商品管理番号 商品画像 追加
     */
    private Map<String, String> cntGoodsGroupUpdateMap;

    /**
     * 集計用件数リスト 商品管理番号 商品画像 削除
     */
    private Map<String, String> cntGoodsGroupRegistMap;

    /**
     * 集計用件数リスト 商品管理番号 商品画像 更新
     */
    private Map<String, String> cntGoodsGroupDeleteMap;

    /**
     * 集計用件数リスト 商品管理番号 規格コードが存在しない
     */
    private Map<String, String> cntUnitImageCodeNotExistMap;

    /**
     * 商品グループ画像
     * 集計用件数 画像 合計件数
     */
    private int cntGoodsGroupImageSum;

    /**
     * 集計用件数 画像 商品管理番号が存在しない
     */
    private int cntGoodsGroupImageNotExist;

    /**
     * 集計用件数 画像 画像ファイル名が不適切
     */
    private int cntGoodsGroupImageFileNameNotMatch;

    /**
     * 集計用件数 画像 商品が削除されている
     */
    private int cntGoodsGroupImageOpenStateDel;

    /**
     * 集計用件数 画像 商品画像 追加
     */
    private int cntGoodsGroupImageRegist;

    /**
     * 集計用件数 画像 商品画像 更新
     */
    private int cntGoodsGroupImageUpdate;

    /**
     * 集計用件数 画像 商品画像 削除
     */
    private int cntGoodsGroupImageDelete;

    /**
     * 集計用件数 画像 商品画像 追加
     */
    private int cntUnitImageRegist;

    /**
     * 集計用件数 画像 商品画像 更新
     */
    private int cntUnitImageUpdate;

    /**
     * 集計用件数 画像 商品画像 削除
     */
    private int cntUnitImageDelete;

    /**
     * 集計用件数 画像 合計件数
     */
    private int cntUnitImageSum;

    /**
     * 集計用件数 画像 商品が削除されている
     */
    private int cntUnitImageOpenStateDel;

    /**
     * 集計用件数 画像 規格コードが存在しない
     */
    private int cntUnitImageCodeNotExist;

    /**
     * 集計用件数 画像 画像ファイル名が不適切
     */
    protected int cntUnitImageFileNameNotMatch;

    /**
     * 集計用件数 画像 商品管理番号が存在しない
     */
    private int cntUnitImageNotExist;

    /**
     * 画像ファイル画像ディレクトリ移動時エラーメッセージリスト
     */
    private List<String> toImageDirCopyErrorMsgList;

    /**
     * 画像ファイル画像削除エラーメッセージリスト
     */
    private List<String> toImageDirDeleteErrorMsgList;

    /**
     * 画像ファイルエラーディレクトリ移動時エラーメッセージリスト
     */
    private List<String> toErrorDirCopyErrorMsgList;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    /**
     * 削除処理の処理件数<br />
     * 商品グループ画像テーブル及び商品規格画像テーブルを全件処理する為、数回に分けて取得する。<br />
     * リミット件数分のレコードが、メモリに保持される。<br />
     */
    private static final int DELETE_TARGET_LIMIT = 100000;

    /**
     * 最大商品グループ画像数
     */
    private int goodsGroupImageMaxCnt;

    /**
     * 商品画像格納先URI
     */
    private String imageDirUri;

    /**
     * 説明文字列
     */
    private static final String EXPLAIN_NOTEXIST = "商品管理番号が存在しません";

    /**
     * 説明文字列
     */
    private static final String EXPLAIN_STATE_DEL = "商品が削除されています";

    /**
     * 説明文字列
     */
    private static final String EXPLAIN_FILENAME_ERROR = "画像ファイル名が不適切です";

    /**
     * 説明文字列
     */
    private static final String EXPLAIN_UNITIMAGECODE_NOTEXIST = "規格コードが存在しません";

    // 共通
    /**
     * 詳細文字列
     */
    private static final String DETAILE_EXTENSION_ERROR = "jpeg ファイルではありません。({0})";

    /**
     * 詳細文字列
     */
    private static final String DETAILE_GOODSGROUPCODE_ERROR = "フォルダ名の商品管理番号とファイル名の商品管理番号が不一致です。({0})";

    /**
     * 詳細文字列
     */
    private static final String DETAILE_FILENAME_SPLIT_ERROR = "画像ファイルのファイル命名規則と不一致です。({0})";

    // 商品グループ画像
    /**
     * 詳細文字列
     */
    private static final String DETAILE_VERSIONNO_DETAILEIMAGE_RANGE_ERROR =
                    "種類が「_pds」,「_pdm」,「_pdl」,「_mdl」の場合、商品グループ画像表示順は0埋め2桁で「1～{0}」の範囲で設定してください。({1})";

    /**
     * 詳細文字列
     */
    private static final String DETAILE_UNITIMAGECODE_DELETE = "該当の規格は削除されております。";

    /**
     * 画像種別内連番 正規表現(数値二桁)
     */
    private static final String MATCH_VERSION_NO = "^[0-9]{2}$";

    /**
     * 処理種別文字列
     */
    private static final String MODE_UPDATE = "更新";

    /**
     * 処理種別文字列
     */
    private static final String MODE_INSERT = "追加";

    /**
     * 処理種別文字列
     */
    private static final String MODE_DELETE = "削除";

    /**
     * 規格画像フラグ
     */
    protected static final boolean UNITIMAGE_FLAG = true;

    /**
     * 商品グループ画像フラグ
     */
    protected static final boolean GOODSGROUPIMAGE_FLAG = false;

    /**
     * 結果CSV　商品画像処理CSVファイル名
     */
    private String csvFileNameProcSuccess;

    /**
     * 結果CSV　商品画像処理エラーCSVファイル名
     */
    private String csvFileNameProcError;

    /**
     * 結果CSV　商品画像処理CSVファイル名の接中辞
     */
    private static final String CSV_FILENAME_PROC_SUCCESS_INFIX = "goodsImageProc_";

    /**
     * 結果CSV　商品画像処理エラーCSVファイル名の接中辞
     */
    private static final String CSV_FILENAME_PROC_ERROR_INFIX = "goodsImageProcError_";

    /**
     * 結果CSV　拡張子
     */
    private static final String FILE_NAME_EXTENSION_CSV = ".csv";

    /**
     * 画像ファイルセパレーター
     */
    private static final String FILE_NAME_SEPARATOR = "_";

    /**
     * 規格画像ファイルセパレーター
     */
    private static final String UNIT_FILE_NAME_SEPARATOR = "_u_";

    /**
     * 画像ファイル拡張子(.jpg)
     */
    private static final String FILE_NAME_EXTENSION_JPG = ".jpg";

    /**
     * 画像ファイル拡張子(.jpeg)
     */
    private static final String FILE_NAME_EXTENSION_JPEG = ".jpeg";

    /**
     * 画像ファイル拡張子(.jpe)
     */
    private static final String FILE_NAME_EXTENSION_JPE = ".jpe";

    /**
     * 画像ファイル拡張子(.jfif)
     */
    private static final String FILE_NAME_EXTENSION_JFIF = ".jfif";

    /**
     * 画像ファイル拡張子(.jfi)
     */
    private static final String FILE_NAME_EXTENSION_JFI = ".jfi";

    /**
     * 画像ファイル拡張子(.jif)
     */
    private static final String FILE_NAME_EXTENSION_JIF = ".jif";

    /**
     * 画像ファイル拡張子リスト
     */
    private static final List<String> EXTENSION_LIST;

    static {
        EXTENSION_LIST = List.of(FILE_NAME_EXTENSION_JPG, FILE_NAME_EXTENSION_JPEG, FILE_NAME_EXTENSION_JPE,
                                 FILE_NAME_EXTENSION_JFIF, FILE_NAME_EXTENSION_JFI, FILE_NAME_EXTENSION_JIF
                                );
    }

    /**
     * ファイルマジックナンバー
     */
    private static final byte[] MAGIC_NUMBER1 = {(byte) 0xFF, (byte) 0xD8};

    /**
     * 改行(キャリッジリターン)
     */
    private static final String LINE_FEED_CR = "\r\n";

    /**
     * 区切り文字(スラッシュ)
     */
    private static final String DELIMITER_SLASH = "/";

    /************
     * Dao
     ************/
    /**
     * 商品グループDao
     */
    private final GoodsGroupDao goodsGroupDao;

    /**
     * 商品グループ画像Dao(ext)
     */
    private final GoodsGroupImageDao goodsGroupimageDao;

    // 2023-renew No76 from here
    /**
     * 商品Dao
     */
    private final GoodsDao goodsDao;
    // 2023-renew No76 to here

    /**
     * 商品画像Dao(ext)
     */
    private final GoodsImageDao goodsImageDao;

    /**
     * コンストラクタ<br/>
     */
    public GoodsImageUpdateBatch(Environment environment) {
        // falseでバッチ起動時にBatchTaskテーブルに対応するレコードを作成する
        this.goodsGroupDao = ApplicationContextUtility.getBean(GoodsGroupDao.class);
        // 2023-renew No76 from here
        this.goodsDao = ApplicationContextUtility.getBean(GoodsDao.class);
        // 2023-renew No76 to here
        this.goodsImageDao = ApplicationContextUtility.getBean(GoodsImageDao.class);
        this.goodsGroupimageDao = ApplicationContextUtility.getBean(GoodsGroupImageDao.class);
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.exitMessageUtil = new BatchExitMessageUtil();
        this.mailSetting = new InstantMailSetting(environment.getProperty("mail.setting.goodsimage.upload.smtp.server"),
                                                  environment.getProperty("mail.setting.goodsimage.upload.mail.from"),
                                                  null, null, Collections.singletonList(
                        environment.getProperty("mail.setting.goodsimage.upload.mail.receivers"))
        );
        super.setShopSeq(environment.getProperty("shopseq", Integer.class));
    }

    /**
     * データの追加、更新を行う<br/>
     *
     * @return RepeatStatus
     * @throws Exception 例外
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

        // 初期設定
        String msg = init();
        if (msg.length() != 0) {
            LOGGER.info("設定ファイルエラー" + LINE_FEED_CR + msg);
            report(msg);
            sendAdministratorErrorMail("設定エラー", msg);
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(null, this.getReportString().toString())));
            throw new Exception("設定ファイルエラー");
        }

        try {
            // 商品画像の削除処理を行います。
            deleteGoodsGroupImage();

            // 商品規格画像の削除処理を行います。
            deleteGoodsImage();

            // 登録更新処理を行います。
            registUpdate();

            // 結果csvを生成します。
            outputResultCsv();

            // 商品画像ファイルを移動します。
            moveDirImageFile();

            // レポート内容に表示する内容を設定
            report(createReportMsg());

            // 処理完了メール送信
            sendAdministratorMail();

        } catch (Exception error) {
            // 予期せぬ事態で処理が中断した場合
            LOGGER.error("例外処理が発生しました", error);
            // エラーメール送信
            sendAdministratorErrorMail(error.getClass().getName(), null);
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(null, this.getReportString().toString())));
            throw error;
        }

        executionContext.put(
                        BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                        new BatchExitMessageDto(null, this.getReportString().toString())));
        return RepeatStatus.FINISHED;
    }

    /**
     * 初期設定<br />
     *
     * @return 設定結果
     */
    protected String init() {

        StringBuilder sb = new StringBuilder();

        // 集計用リストの初期化
        procSuccessCsvDtoList = new ArrayList<>();
        procErrorCsvDtoList = new ArrayList<>();

        // 集計用件数の初期化(商品管理番号毎)
        cntGoodsGroupSumMap = new HashMap<>();
        cntGoodsGroupNotExistMap = new HashMap<>();
        cntGoodsGroupFileNameNotMatchMap = new HashMap<>();
        cntGoodsGroupOpenStateDelMap = new HashMap<>();
        cntGoodsGroupUpdateMap = new HashMap<>();
        cntGoodsGroupRegistMap = new HashMap<>();
        cntGoodsGroupDeleteMap = new HashMap<>();
        cntUnitImageCodeNotExistMap = new HashMap<>();

        // 集計用件数の初期化(画像毎)
        // 商品グループ画像
        cntGoodsGroupImageSum = 0;
        cntGoodsGroupImageNotExist = 0;
        cntGoodsGroupImageFileNameNotMatch = 0;
        cntGoodsGroupImageOpenStateDel = 0;
        cntGoodsGroupImageRegist = 0;
        cntGoodsGroupImageUpdate = 0;
        cntGoodsGroupImageDelete = 0;

        // 規格画像
        cntUnitImageSum = 0;
        cntUnitImageRegist = 0;
        cntUnitImageUpdate = 0;
        cntUnitImageDelete = 0;
        cntUnitImageOpenStateDel = 0;
        cntUnitImageCodeNotExist = 0;
        cntUnitImageFileNameNotMatch = 0;
        cntUnitImageNotExist = 0;

        // 画像コピーエラーリスト
        toImageDirCopyErrorMsgList = new ArrayList<>();
        toImageDirDeleteErrorMsgList = new ArrayList<>();
        toErrorDirCopyErrorMsgList = new ArrayList<>();

        // 商品画像格納先URIの取得
        imageDirUri = PropertiesUtil.getSystemPropertiesValue("goodsimage.directory.uri");

        // 出力日時
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        outputTime = dateUtility.getCurrentTime();
        String datetime = new SimpleDateFormat(DateUtility.YMD + DateUtility.HMS_NON_COLON).format(outputTime);

        // ディレクトリ設定を読込み
        imageDir = PropertiesUtil.getSystemPropertiesValue("real.images.path.goods");
        inputDir = PropertiesUtil.getSystemPropertiesValue("goodsimage.input.directory");
        errorDir = PropertiesUtil.getSystemPropertiesValue("goodsimage.error.directory");
        workDir = PropertiesUtil.getSystemPropertiesValue("goodsimage.csv.work.directory");
        backupDir = PropertiesUtil.getSystemPropertiesValue("goodsimage.csv.backup.directory");

        if (!new File(imageDir).exists()) {
            sb.append("[").append(imageDir).append("]が存在しません。").append(LINE_FEED_CR);
        }
        if (!new File(inputDir).exists()) {
            sb.append("[").append(inputDir).append("]が存在しません。").append(LINE_FEED_CR);
        }
        if (!new File(errorDir).exists()) {
            sb.append("[").append(errorDir).append("]が存在しません。").append(LINE_FEED_CR);
        }
        if (!new File(workDir).exists()) {
            sb.append("[").append(workDir).append("]が存在しません。").append(LINE_FEED_CR);
        }
        if (!new File(backupDir).exists()) {
            sb.append("[").append(backupDir).append("]が存在しません。").append(LINE_FEED_CR);
        }
        errorDir = errorDir + DELIMITER_SLASH + datetime;

        // 各CSVに付与するプレフィックス
        String csvPrefix = PropertiesUtil.getSystemPropertiesValue("shopid") + FILE_NAME_SEPARATOR;
        csvFileNameProcSuccess = csvPrefix + CSV_FILENAME_PROC_SUCCESS_INFIX;
        csvFileNameProcError = csvPrefix + CSV_FILENAME_PROC_ERROR_INFIX;

        // 最大商品グループ画像数
        String cnt = PropertiesUtil.getSystemPropertiesValue("goodsimage.max.count");
        if (StringUtils.isEmpty(cnt)) {
            sb.append("[goodsgroupimage.max.count]が設定されていません。").append(LINE_FEED_CR);
        }
        goodsGroupImageMaxCnt = Integer.parseInt(cnt);

        return sb.toString();
    }

    /**
     * 商品グループ画像削除処理<br />
     * 処理対象となる画像を取得します。<br />
     * 取得した画像に対して、処理方法を決定します。<br />
     * <p>
     * ・商品グループ画像テーブルに画像が登録されている場合<br />
     * 商品グループ画像テーブルの画像を削除します。<br />
     * 「⑤商品画像 削除」で集計します。<br />
     */
    protected void deleteGoodsGroupImage() {
        LOGGER.info("▼商品グループ画像削除処理開始");
        int offset = 0;
        // メモリを考慮し、limit件数毎に処理を行う。
        for (; ; ) {
            // 全商品の商品グループ画像を取得します。
            List<GoodsGroupImageOpenstatusDto> list =
                            goodsGroupimageDao.getGoodsGroupImageOpenstatusList(offset, DELETE_TARGET_LIMIT);
            if (list == null || list.size() == 0 || list.isEmpty()) {
                break;
            }
            // 取得した画像分処理を行います。
            for (GoodsGroupImageOpenstatusDto imageDto : list) {
                File imageFile = new File(imageDir + DELIMITER_SLASH + imageDto.getImageFileName());
                if (imageFile.exists()) {
                    // g_images配下にファイルが存在している場合。処理なし
                    continue;
                }
                // 削除処理（物理削除）
                if (deleteGoodsGroupImageExecute(imageDto)) {
                    // 商品グループの公開状態が、削除以外の場合。「⑤商品画像 削除」で集計します。
                    addCntDelete(imageDto.getGoodsGroupCode(), imageFile, null, null, GOODSGROUPIMAGE_FLAG);
                }
            }
            offset = offset + DELETE_TARGET_LIMIT;
        }
        LOGGER.info("▲商品グループ画像削除処理終了");
    }

    /**
     * 商品規格画像削除処理<br />
     * 処理対象となる画像を取得します。<br />
     * 取得した画像に対して、処理方法を決定します。<br />
     * <p>
     * ・商品規格画像テーブルに画像が登録されている場合<br />
     * 商品規格画像テーブルの画像を削除します。<br />
     * 「⑤商品画像 削除」で集計します。<br />
     */
    protected void deleteGoodsImage() {
        LOGGER.info("▼商品規格画像削除処理開始");
        int offset = 0;
        // メモリを考慮し、limit件数毎に処理を行う。
        for (; ; ) {
            // 全商品の商品規格画像を取得します。
            List<GoodsImageOpenstatusDto> list = goodsImageDao.getGoodsImageOpenstatusList(offset, DELETE_TARGET_LIMIT);
            if (list == null || list.size() == 0 || list.isEmpty()) {
                break;
            }
            // 取得した画像分処理を行います。
            for (GoodsImageOpenstatusDto imageDto : list) {

                File imageFile = new File(imageDir + DELIMITER_SLASH + imageDto.getImageFileName());
                if (imageFile.exists()) {
                    // g_images配下にファイルが存在している場合。処理なし
                    continue;
                }
                // 削除処理（物理削除）
                if (deleteGoodsImageExecute(imageDto)) {
                    // 商品グループの公開状態が、削除以外の場合。「⑤商品画像 削除」で集計します。
                    addCntDelete(imageDto.getGoodsGroupCode(), imageFile, null, null, UNITIMAGE_FLAG);
                }
            }
            offset = offset + DELETE_TARGET_LIMIT;
        }
        LOGGER.info("▲商品規格画像削除処理終了");
    }

    /**
     * 商品グループ画像・商品規格画像登録処理<br />
     * 処理対象となる画像ファイルを取得します。<br />
     * 取得した画像に対して、処理方法を決定します。<br />
     * <p>
     * ・商品グループが取得できない場合<br />
     * 「①商品管理番号が存在しない」で集計を行います。<br />
     * <p>
     * ・商品グループの公開状態が削除の場合<br />
     * 「②商品が削除されている」で集計します。<br />
     * <p>
     * ・商品グループ画像テーブルもしくは商品規格画像テーブルに既に画像が登録されている場合<br />
     * 「③商品画像 更新」で集計します。<br />
     * <p>
     * ・商品グループ画像テーブルもしくは商品規格画像テーブルに画像が登録されていない場合<br />
     * 「④商品画像 登録」で集計します。<br />
     *
     * @throws Exception exception
     */
    protected void registUpdate() throws Exception {
        LOGGER.info("▼登録更新処理開始");

        // 画像登録ディレクトリから、処理対象となる商品管理番号ディレクトリを取得します。
        File[] goodsGroupDirs = getInputGoodsGroupCodeDir();

        // 商品管理番号ディレクトリが存在しない場合。
        if (goodsGroupDirs == null || goodsGroupDirs.length == 0) {
            return;
        }

        // 取得した商品管理番号分処理を行います。
        for (File goodsGroupDir : goodsGroupDirs) {

            // 商品管理番号ディレクトリから、処理対象となる画像ファイルを取得します。
            File[] imageFiles = getInputImageFile(goodsGroupDir);
            if (imageFiles == null || imageFiles.length == 0) {
                // 画像ファイルが存在しない場合。
                continue;
            }
            // 商品グループを取得します。
            GoodsGroupEntity goodsGroupEntity = getGoodsGroupEntity(goodsGroupDir.getName());
            List<GoodsGroupImageEntity> goodsGroupImageEntityList = new ArrayList<>();
            List<GoodsImageOpenstatusDto> goodsImageOpenStatusDtoList = new ArrayList<>();
            if (goodsGroupEntity != null) {
                // 商品グループ画像情報リストを取得します。
                goodsGroupImageEntityList = goodsGroupimageDao.getGoodsGroupImageListByGoodsGroupSeq(
                                goodsGroupEntity.getGoodsGroupSeq());

                // 商品規格画像情報リストを取得します。
                goodsImageOpenStatusDtoList =
                                goodsImageDao.getGoodsImageOpenStatusListByCode(goodsGroupEntity.getGoodsGroupCode());
            }

            // list of fileName without extension
            List<String> fileNameList = new ArrayList<>();
            // 取得した画像ファイル分処理を行います。
            for (File imageFile : imageFiles) {

                // 商品グループ画像ファイルか規格画像ファイルか判定する
                boolean isUnitImage = judgeUnitImageKind(imageFile);

                // 商品グループが取得できない場合。「①商品管理番号が存在しない」で集計を行います。
                if (goodsGroupEntity == null) {
                    addCntNotExist(goodsGroupDir.getName(), imageFile, isUnitImage);
                    continue;
                }

                // If same files with different extension present then skip the
                // processing.
                String fileNameWithoutExt = imageFile.getName().split("\\.")[0];
                if (fileNameList.contains(fileNameWithoutExt)) {
                    String fileName = goodsGroupDir.getName().concat(DELIMITER_SLASH).concat(imageFile.getName());
                    moveErrorDir(goodsGroupEntity.getGoodsGroupCode(), fileName);
                    continue;
                }

                fileNameList.add(fileNameWithoutExt);

                // 商品グループの公開状態が、削除の場合。「②商品が削除されている」で集計します。
                if (HTypeOpenDeleteStatus.DELETED.equals(goodsGroupEntity.getGoodsOpenStatusPC())) {
                    String fileName = goodsGroupDir.getName().concat(DELIMITER_SLASH).concat(imageFile.getName());
                    String goodsCode = null;
                    if (isUnitImage) {
                        goodsCode = getGoodsCode(imageFile);
                    }
                    addCntOpenStateDel(goodsGroupEntity.getGoodsGroupCode(), goodsCode, fileName, null, isUnitImage);
                    continue;
                }

                if (isUnitImage) {
                    // 規格画像登録更新処理
                    registUpdateUnitImage(goodsGroupEntity, goodsGroupDir, imageFile, goodsImageOpenStatusDtoList);
                } else {
                    // 商品グループ画像登録更新処理
                    registUpdateGoodsGroupImage(goodsGroupEntity, goodsGroupDir, imageFile, goodsGroupImageEntityList);
                }
            }
        }
        LOGGER.info("▲登録更新処理終了");
    }

    /**
     * 画像ファイル名から規格画像か否か判定する<br />
     *
     * @param imageFile 画像ファイル
     * @return 判定結果 true..規格画像ファイル false..商品グループ画像ファイル
     */
    protected boolean judgeUnitImageKind(File imageFile) {
        // ハイフンで分割し、分割後のサイズが4なら規格画像
        // 商品グループ画像 :商品管理番号_表示順_種類.jpg（例：CBM6189_1_ptn.jpg）
        // 規格画像 :商品管理番号_規格画像コード_規格画像グループNO_種類.jpg（例：CBM6189_R318_1_ptn.jpg）
        String[] fileNameArray = imageFile.getName().split(UNIT_FILE_NAME_SEPARATOR);
        return 2 == fileNameArray.length;
    }

    /**
     * 処理結果CSVを出力します。<br />
     * 　・商品画像処理CSV<br />
     * 　・商品画像処理エラーCSV<br />
     *
     * @throws IOException csvファイル生成エラー
     */
    protected void outputResultCsv() throws IOException {
        LOGGER.info("▼処理結果CSV出力処理開始");

        String datetime = new SimpleDateFormat(DateUtility.YMD + DateUtility.HMS_NON_COLON).format(outputTime);

        csvFileNameProcSuccess = csvFileNameProcSuccess + datetime + FILE_NAME_EXTENSION_CSV;
        csvFileNameProcError = csvFileNameProcError + datetime + FILE_NAME_EXTENSION_CSV;

        // 商品管理番号/規格コード/画像ファイル名の昇順でソート
        procErrorCsvDtoList.sort((ent1, ent2) -> {
            if (ent1.getGoodsGroupCode().equals(ent2.getGoodsGroupCode())) {
                String goodsCode1 = ent1.getGoodsCode();
                String goodsCode2 = ent2.getGoodsCode();
                if (goodsCode1 == null && goodsCode2 == null || (goodsCode1 != null && goodsCode2 != null
                                                                 && goodsCode1.equals(goodsCode2))) {
                    return ent1.getGoodsGroupCode().compareTo(ent2.getGoodsGroupCode());
                } else if (goodsCode1 == null) {
                    return 1;
                } else if (goodsCode2 == null) {
                    return -1;
                } else {
                    return goodsCode1.compareTo(goodsCode2);
                }
            } else {
                return ent1.getGoodsGroupCode().compareTo(ent2.getGoodsGroupCode());
            }
        });

        outputCsvExecute(GoodsImageProcSuccessCsvDto.class, procSuccessCsvDtoList, csvFileNameProcSuccess);
        LOGGER.info("結果CSV 商品画像処理CSVを出力しました");
        outputCsvExecute(GoodsImageProcErrorCsvDto.class, procErrorCsvDtoList, csvFileNameProcError);
        LOGGER.info("結果CSV 商品画像処理エラーCSVを出力しました");

        LOGGER.info("▲処理結果CSV出力処理終了");
    }

    /**
     * 画像追加ディレクトリの画像ファイルをg_imagesに移動します。<bre />
     *
     * @throws Exception 例外
     */
    protected void moveDirImageFile() throws Exception {

        // 登録できなかった画像をerrorに移動します。
        LOGGER.info("▼error移動処理開始");
        for (GoodsImageProcErrorCsvDto errorDto : procErrorCsvDtoList) {

            File file = new File(inputDir + DELIMITER_SLASH + errorDto.getGoodsGroupCode());

            try {
                if (file.exists()) {
                    // 追加用ディレクトリの画像ファイルをerrorディレクトリに移動します。
                    if (moveErrorDir(errorDto.getGoodsGroupCode(), errorDto.getImageFileName())) {
                        LOGGER.info("error移動処理成功 " + file.getParent() + DELIMITER_SLASH + errorDto.getImageFileName());
                    } else {
                        LOGGER.info("error移動処理失敗 " + file.getParent() + DELIMITER_SLASH + errorDto.getImageFileName());
                        toErrorDirCopyErrorMsgList.add(
                                        inputDir + DELIMITER_SLASH + errorDto.getGoodsGroupCode() + DELIMITER_SLASH
                                        + errorDto.getImageFileName());
                    }
                }
            } catch (Exception e) {
                // ログ出力し、次の画像の処理を進める。
                LOGGER.error("error移動処理中に例外が発生しました。[" + file.getAbsolutePath() + "]", e);
                toErrorDirCopyErrorMsgList.add(
                                inputDir + DELIMITER_SLASH + errorDto.getGoodsGroupCode() + DELIMITER_SLASH
                                + errorDto.getImageFileName());
            }
        }
        LOGGER.info("▲error移動処理終了");
        // 登録完了した画像を、g_imagesにファイルをコピーします。
        // 全て正常にコピーできた場合、追加用ディレクトリから削除します。
        LOGGER.info("▼正常移動処理開始");
        for (GoodsImageProcSuccessCsvDto regDto : procSuccessCsvDtoList) {
            if (MODE_DELETE.equals(regDto.getMode())) {
                // 処理区分：削除の場合は、ファイルの移動処理は不要
                continue;
            }

            File file = new File(regDto.getImageUrl()
                                       .replaceAll(imageDirUri + DELIMITER_SLASH, inputDir + DELIMITER_SLASH));

            // 削除フラグ
            boolean inputDelFlg = true;

            if (file.exists() && file.isFile()) {

                // g_imagesに移動します。
                String gImageGoodsGroupPath = imageDir + DELIMITER_SLASH + regDto.getGoodsGroupCode() + DELIMITER_SLASH;
                try {
                    // 移動用ディレクトリ作成
                    if (!new File(gImageGoodsGroupPath).exists()) {
                        new File(gImageGoodsGroupPath).mkdirs();
                    }

                    // 追加用ディレクトリの画像ファイルをg_imagesに移動します。
                    // 拡張子違いのファイルを削除
                    cleanUpFile(gImageGoodsGroupPath + file.getName());
                    if (copyFile(file.getAbsolutePath(), gImageGoodsGroupPath + file.getName(), false)) {
                        LOGGER.debug("g_images移動処理成功 " + gImageGoodsGroupPath + file.getName());
                    } else {
                        LOGGER.info("g_images移動処理失敗 " + gImageGoodsGroupPath + file.getName());
                        toImageDirCopyErrorMsgList.add(
                                        inputDir + DELIMITER_SLASH + regDto.getGoodsGroupCode() + DELIMITER_SLASH
                                        + file.getName());
                        inputDelFlg = false;
                    }

                } catch (Exception e) {
                    // ログ出力し、次の画像の処理を進める。
                    LOGGER.error("g_images移動処理中に例外が発生しました。[" + gImageGoodsGroupPath + file.getName() + "]", e);
                    toImageDirCopyErrorMsgList.add(
                                    inputDir + DELIMITER_SLASH + regDto.getGoodsGroupCode() + DELIMITER_SLASH
                                    + file.getName());
                    inputDelFlg = false;
                }
            }

            if (inputDelFlg) {
                // 画像追加用ディレクトリの削除を行います。
                FileOperationUtility fileOperationUtility =
                                ApplicationContextUtility.getBean(FileOperationUtility.class);
                try {
                    // ファイルの削除
                    String filePath = inputDir + DELIMITER_SLASH + regDto.getGoodsGroupCode() + DELIMITER_SLASH
                                      + file.getName();
                    fileOperationUtility.remove(filePath);
                } catch (IOException e) {
                    LOGGER.info("ファイルの削除に失敗しました。（" + inputDir + DELIMITER_SLASH + regDto.getGoodsGroupCode()
                                + DELIMITER_SLASH + "）", e);
                    toImageDirDeleteErrorMsgList.add(
                                    inputDir + DELIMITER_SLASH + regDto.getGoodsGroupCode() + DELIMITER_SLASH
                                    + file.getName());
                }
                String[] fileList = file.getParentFile().list();
                int fileListLength = 0;
                if (fileList != null) {
                    fileListLength = fileList.length;
                }

                if (fileListLength == 0) {
                    try {
                        // 最後の画像ファイルだった場合、親ディレクトリも削除
                        fileOperationUtility.removeDir(
                                        inputDir + DELIMITER_SLASH + regDto.getGoodsGroupCode() + DELIMITER_SLASH);
                    } catch (IOException e) {
                        LOGGER.info("ディレクトリの削除に失敗しました。（" + inputDir + DELIMITER_SLASH + regDto.getGoodsGroupCode()
                                    + DELIMITER_SLASH + "）", e);
                    }
                }

            }
        }
        LOGGER.info("▲正常移動処理終了");
    }

    /**
     * 商品グループ画像削除処理を行います。<br />
     *
     * @param imageDto 商品グループ画像Dto
     * @return true:成功　false:失敗
     */
    protected boolean deleteGoodsGroupImageExecute(GoodsGroupImageOpenstatusDto imageDto) {

        GoodsGroupImageEntity goodsGroupImageEntity = ApplicationContextUtility.getBean(GoodsGroupImageEntity.class);
        goodsGroupImageEntity.setGoodsGroupSeq(imageDto.getGoodsGroupSeq());
        goodsGroupImageEntity.setImageTypeVersionNo(imageDto.getImageTypeVersionNo());

        int cnt = goodsGroupimageDao.delete(goodsGroupImageEntity);

        if (cnt == 0) {
            return false;
        }
        return true;
    }

    /**
     * 商品規格画像削除処理を行います。<br />
     *
     * @param imageDto 商品グループ画像Dto
     * @return true:成功　false:失敗
     */
    protected boolean deleteGoodsImageExecute(GoodsImageOpenstatusDto imageDto) {
        int cnt = goodsImageDao.deleteEntity(imageDto.getGoodsGroupSeq(), imageDto.getGoodsSeq());

        if (cnt == 0) {
            return false;
        }
        // 2023-renew No76 from here
        // 規格画像有無更新
        goodsDao.updateUnitImageFlag(HTypeUnitImageFlag.OFF, imageDto.getGoodsSeq());
        // 2023-renew No76 to here
        return true;
    }

    /**
     * 集計「⑤商品画像 削除」<br />
     *
     * @param goodsGroupCode 商品管理番号
     * @param imageFile      画像ファイル
     * @param groupImage     商品グループ画像エンティティ
     */
    protected void addCntDelete(String goodsGroupCode,
                                File imageFile,
                                GoodsGroupImageEntity groupImage,
                                GoodsImageEntity unitImage,
                                boolean imageKind) {
        LOGGER.debug("⑤商品画像 削除 - " + goodsGroupCode + " - " + imageFile.getName());

        // 画像URLリストに追加します。
        addProcSuccessCsvDtoList(goodsGroupCode, imageFile, groupImage, unitImage, MODE_DELETE);

        // cnt追加します。
        cntGoodsGroupDeleteMap.put(goodsGroupCode, goodsGroupCode);
        cntGoodsGroupSumMap.put(goodsGroupCode, goodsGroupCode);

        if (imageKind) {
            cntUnitImageDelete++;
            cntUnitImageSum++;
        } else {
            cntGoodsGroupImageDelete++;
            cntGoodsGroupImageSum++;
        }
    }

    /**
     * レポートに出力する文言を生成します。<br />
     *
     * @return 出力メッセージ
     */
    protected String createReportMsg() {
        StringBuilder sb = new StringBuilder();

        // 成功件数算出
        int goodsGroupImageSuccessCnt = cntGoodsGroupImageRegist + cntGoodsGroupImageDelete + cntGoodsGroupImageUpdate;
        int unitImageSuccessCnt = cntUnitImageRegist + cntUnitImageDelete + cntUnitImageUpdate;
        // 失敗件数算出
        int goodsGroupImageErrorCnt = cntGoodsGroupImageNotExist + cntGoodsGroupImageFileNameNotMatch
                                      + cntGoodsGroupImageOpenStateDel;
        int unitImageErrorCnt = cntUnitImageNotExist + cntUnitImageCodeNotExist + cntUnitImageFileNameNotMatch
                                + cntUnitImageOpenStateDel;

        // レポート内容作成
        sb.append("-----").append(LINE_FEED_CR);
        sb.append("■総処理件数").append(LINE_FEED_CR);
        sb.append("商品管理番号：").append(cntGoodsGroupSumMap.size()).append(" 件").append(LINE_FEED_CR);
        sb.append("規格画像：").append(cntUnitImageSum).append(" 件").append(LINE_FEED_CR);
        sb.append("グループ画像：").append(cntGoodsGroupImageSum).append(" 件").append(LINE_FEED_CR).append(LINE_FEED_CR);
        sb.append("■成功件数").append(LINE_FEED_CR);
        sb.append("規格画像：").append(unitImageSuccessCnt).append(" 件").append(LINE_FEED_CR);
        sb.append("グループ画像：").append(goodsGroupImageSuccessCnt).append(" 件").append(LINE_FEED_CR).append(LINE_FEED_CR);
        sb.append("■失敗件数").append(LINE_FEED_CR);
        sb.append("規格画像：").append(unitImageErrorCnt).append(" 件").append(LINE_FEED_CR);
        sb.append("グループ画像：").append(goodsGroupImageErrorCnt).append(" 件").append(LINE_FEED_CR);
        sb.append("-----").append(LINE_FEED_CR);

        // 処理件数0件の場合、メール送信しないので出力しない
        int count = cntGoodsGroupSumMap.size() + cntGoodsGroupImageSum;
        if (count != 0) {
            sb.append("詳細はメールにてご確認ください。").append(LINE_FEED_CR);
        }

        if (toImageDirCopyErrorMsgList.size() != 0 || toErrorDirCopyErrorMsgList.size() != 0) {
            sb.append("※移動処理に失敗した画像があります。").append(LINE_FEED_CR);
        }

        if (toImageDirDeleteErrorMsgList.size() != 0) {
            sb.append("※削除処理に失敗した画像があります。").append(LINE_FEED_CR);
        }

        return sb.toString();
    }

    /**
     * 商品画像処理CSVリストにレコードを追加します。<br />
     *
     * @param goodsGroupCode 商品管理番号
     * @param imageFile      画像ファイル
     * @param groupImage     商品グループ画像エンティティ
     * @param goodsImage     商品画像エンティティ
     * @param mode           処理種別
     */
    protected void addProcSuccessCsvDtoList(String goodsGroupCode,
                                            File imageFile,
                                            GoodsGroupImageEntity groupImage,
                                            GoodsImageEntity goodsImage,
                                            String mode) {
        GoodsImageProcSuccessCsvDto csvDto = ApplicationContextUtility.getBean(GoodsImageProcSuccessCsvDto.class);
        csvDto.setGoodsGroupCode(goodsGroupCode);
        csvDto.setImageUrl(imageDirUri + DELIMITER_SLASH + goodsGroupCode + DELIMITER_SLASH + imageFile.getName());
        csvDto.setMode(mode);
        csvDto.setGoodsGroupImageEntity(groupImage);
        procSuccessCsvDtoList.add(csvDto);
    }

    /**
     * 追加用ディレクトリを参照し、商品管番号ディレクトリを返します。<br />
     * 「error」「tmp」は、特殊ディレクトリとする為、排除します。<br />
     *
     * @return 商品管理番号ディレクトリの配列
     */
    protected File[] getInputGoodsGroupCodeDir() {
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.equals("error") || name.equals("tmp")) {
                    return false;
                }
                return true;
            }
        };
        File[] items = new File(inputDir).listFiles(filter);
        return items;
    }

    /**
     * 引数の商品管理番号ディレクトリを参照し、商品画像ファイルを返します。<br />
     *
     * @param goodeGroupDir 商品管理番号ディレクトリ
     * @return 画像ファイルの配列
     * @throws Exception exception
     */
    protected File[] getInputImageFile(File goodeGroupDir) throws Exception {
        // フィルタ作成
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.equals("Thumbs.db")) {
                    try {
                        FileOperationUtility fileOperationUtility =
                                        ApplicationContextUtility.getBean(FileOperationUtility.class);
                        fileOperationUtility.remove(
                                        inputDir + DELIMITER_SLASH + dir.getName() + DELIMITER_SLASH + "Thumbs.db");
                    } catch (Exception e) {
                        LOGGER.info("ファイル削除に失敗しました。（" + inputDir + DELIMITER_SLASH + dir.getName() + DELIMITER_SLASH
                                    + "Thumbs.db" + "）", e);
                        LOGGER.error("ERROR : getInputImageFile", e);
                    }
                    return false;
                } else {
                    return true;
                }
            }
        };
        File[] items = goodeGroupDir.listFiles(filter);
        return items;
    }

    /**
     * 引数の商品管理番号の、商品グループエンティティを返します。<br />
     *
     * @param goodsGroupCode 商品管理番号
     * @return 商品グループエンティティ
     */
    protected GoodsGroupEntity getGoodsGroupEntity(String goodsGroupCode) {
        List<String> goodsGroupCodeList = new ArrayList<>();
        goodsGroupCodeList.add(goodsGroupCode);
        List<GoodsGroupEntity> goodsGroupEntityList =
                        goodsGroupDao.getGoodsGroupByCodeList(this.getShopSeq(), goodsGroupCodeList);
        if (goodsGroupEntityList == null || goodsGroupEntityList.isEmpty()) {
            return null;
        }
        return goodsGroupEntityList.get(0);
    }

    /**
     * 集計「①商品管理番号が存在しない」<br />
     *
     * @param goodsGroupCode 商品管理番号
     * @param imageFile      画像ファイル
     * @param imageKind      true..規格画像 false..商品グループ画像
     */
    protected void addCntNotExist(String goodsGroupCode, File imageFile, boolean imageKind) {
        LOGGER.debug("①商品管理番号が存在しない - " + goodsGroupCode + " - " + imageFile.getName());

        // ファイル名を作成する
        String imageFileName = goodsGroupCode.concat(DELIMITER_SLASH).concat(imageFile.getName());

        String goodsCode = null;
        if (imageKind) {
            goodsCode = getGoodsCode(imageFile);
        }
        // 削除リストに追加します。
        addProcErrorCsvDtoList(goodsGroupCode, goodsCode, EXPLAIN_NOTEXIST, imageFileName, null);

        // cnt追加します。
        cntGoodsGroupNotExistMap.put(goodsGroupCode, goodsGroupCode);
        cntGoodsGroupSumMap.put(goodsGroupCode, goodsGroupCode);

        if (imageKind) {
            cntUnitImageNotExist++;
            cntUnitImageSum++;
        } else {
            cntGoodsGroupImageNotExist++;
            cntGoodsGroupImageSum++;
        }
    }

    /**
     * 削除リストにレコードを追加します。<br />
     *
     * @param goodsGroupCode 商品管理番号
     * @param goodsCode      商品番号
     * @param explain        説明
     * @param imageFileName  画像ファイル名
     * @param details        詳細
     */
    protected void addProcErrorCsvDtoList(String goodsGroupCode,
                                          String goodsCode,
                                          String explain,
                                          String imageFileName,
                                          String details) {
        GoodsImageProcErrorCsvDto csvDto = ApplicationContextUtility.getBean(GoodsImageProcErrorCsvDto.class);

        // 商品管理番号、規格コード、画像ファイル名が一致している場合は追加処理は行わない
        for (GoodsImageProcErrorCsvDto errorCsvDto : procErrorCsvDtoList) {
            if (errorCsvDto.getGoodsGroupCode().equals(goodsGroupCode) && errorCsvDto.getImageFileName()
                                                                                     .equals(imageFileName)) {
                return;
            }
        }
        csvDto.setGoodsGroupCode(goodsGroupCode);
        csvDto.setGoodsCode(goodsCode);
        csvDto.setExplain(explain);
        csvDto.setImageFileName(imageFileName);
        csvDto.setDetails(details);

        procErrorCsvDtoList.add(csvDto);
    }

    /**
     * 集計リストCSVを出力します。<br />
     *
     * @param dtoClass Dtoクラス
     * @param dtoList  集計リスト
     * @param fileName csvファイル名
     */
    protected void outputCsvExecute(Class<?> dtoClass, List<?> dtoList, String fileName) {

        // CSVファイルパス
        String workFilePath = workDir + DELIMITER_SLASH + fileName;

        // CSVダウンロードオプションを設定する
        CsvDownloadOptionDto csvDownloadOptionDto = CsvDownloadOptionDto.DEFAULT_DOWNLOAD_OPTION;
        csvDownloadOptionDto.setOutputHeader(true);

        // 出力ファイルの設定
        CsvExtractUtility csvExtractUtility =
                        new CsvExtractUtility(dtoClass, csvDownloadOptionDto, null, workFilePath, dtoList);

        try {
            csvExtractUtility.outCsv();
        } catch (IOException e) {
            LOGGER.error("集計リストCSVの出力に失敗しました。", e);
        }
    }

    /**
     * 指定ファイルをErrorディレクトリにコピーします。<br />
     *
     * @param goodsGroupCode 商品管理番号
     * @param imageFileName  商品画像ファイル名
     * @return true:成功 false:失敗
     * @throws Exception コピーエラー
     */
    protected boolean moveErrorDir(String goodsGroupCode, String imageFileName) throws Exception {

        FileOperationUtility fileOperationUtility = ApplicationContextUtility.getBean(FileOperationUtility.class);

        try {
            String errorPath = errorDir + DELIMITER_SLASH + goodsGroupCode + DELIMITER_SLASH;

            // 移動用ディレクトリ作成
            if (!new File(errorPath).exists()) {
                new File(errorPath).mkdirs();
            }

            fileOperationUtility.put(inputDir + DELIMITER_SLASH + imageFileName, errorPath, true);

        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            return false;
        }
        File dir = new File(inputDir + DELIMITER_SLASH + goodsGroupCode);
        File[] fileList = dir.listFiles();
        int fileListLength = 0;
        if (fileList != null) {
            fileListLength = fileList.length;
        }
        if (dir.exists() && fileListLength == 0) {
            // 移動元ディレクトリが空になった場合、移動元ディレクトリを削除する
            try {
                fileOperationUtility.remove(dir);
            } catch (IOException e) {
                LOGGER.info("ディレクトリの削除に失敗しました。（" + dir + "）", e);
            }
        }

        return true;
    }

    /**
     * 指定ファイルをコピーします。<br />
     *
     * @param fromPath  コピー元
     * @param toPath    コピー先
     * @param removeSrc true：コピー元削除 false:コピー先削除
     * @return true:成功 false:失敗
     * @throws Exception コピーエラー
     */
    protected boolean copyFile(String fromPath, String toPath, boolean removeSrc) throws Exception {
        try {
            FileOperationUtility fileOperationUtility = ApplicationContextUtility.getBean(FileOperationUtility.class);
            fileOperationUtility.put(fromPath, toPath, removeSrc);
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            return false;
        }
        return true;
    }

    /**
     * 集計「②商品が削除されている」<br />
     *
     * @param goodsGroupCode 商品管理番号
     * @param goodsCode      商品番号
     * @param fileName       画像ファイル名
     * @param details        詳細
     * @param imageKind      true..規格画像 false..商品グループ画像
     */
    protected void addCntOpenStateDel(String goodsGroupCode,
                                      String goodsCode,
                                      String fileName,
                                      String details,
                                      boolean imageKind) {
        LOGGER.debug("②商品が削除されている - " + goodsGroupCode + " - " + fileName);

        // 削除リストに追加します。
        addProcErrorCsvDtoList(goodsGroupCode, goodsCode, EXPLAIN_STATE_DEL, fileName, details);

        // cnt追加します。
        cntGoodsGroupOpenStateDelMap.put(goodsGroupCode, goodsGroupCode);
        cntGoodsGroupSumMap.put(goodsGroupCode, goodsGroupCode);

        if (imageKind) {
            cntUnitImageOpenStateDel++;
            cntUnitImageSum++;
        } else {
            cntGoodsGroupImageOpenStateDel++;
            cntGoodsGroupImageSum++;
        }
    }

    /**
     * 集計「④商品画像 追加」<br />
     *
     * @param goodsGroupCode 商品管理番号
     * @param imageFile      画像ファイル
     * @param groupImage     商品グループ画像エンティティ
     * @param goodsImage     商品規格画像エンティティ
     * @param imageKind      true..規格画像 false..商品グループ画像
     */
    protected void addCntRegist(String goodsGroupCode,
                                File imageFile,
                                GoodsGroupImageEntity groupImage,
                                GoodsImageEntity goodsImage,
                                boolean imageKind) {
        LOGGER.debug("④商品画像 追加 - " + goodsGroupCode + " - " + imageFile.getName());

        // 画像URLリストに追加します。
        addProcSuccessCsvDtoList(goodsGroupCode, imageFile, groupImage, goodsImage, MODE_INSERT);

        // cnt追加します。
        cntGoodsGroupRegistMap.put(goodsGroupCode, goodsGroupCode);
        cntGoodsGroupSumMap.put(goodsGroupCode, goodsGroupCode);

        if (imageKind) {
            cntUnitImageRegist++;
            cntUnitImageSum++;
        } else {
            cntGoodsGroupImageRegist++;
            cntGoodsGroupImageSum++;
        }
    }

    /**
     * 集計「画像ファイル名が不適切です」<br />
     *
     * @param goodsGroupCode 商品管理番号
     * @param goodsCode      商品番号
     * @param fileName       画像ファイル名
     * @param details        詳細
     * @param imageKind      true..規格画像 false..商品グループ画像
     */
    protected void addFileNameNotMatch(String goodsGroupCode,
                                       String goodsCode,
                                       String fileName,
                                       String details,
                                       boolean imageKind) {
        LOGGER.info("画像ファイル名が不適切です - " + goodsGroupCode + " - " + fileName);

        // 削除リストに追加します。
        addProcErrorCsvDtoList(goodsGroupCode, goodsCode, EXPLAIN_FILENAME_ERROR, fileName, details);

        // cnt追加します。
        cntGoodsGroupSumMap.put(goodsGroupCode, goodsGroupCode);
        cntGoodsGroupFileNameNotMatchMap.put(goodsGroupCode, goodsGroupCode);

        if (imageKind) {
            cntUnitImageSum++;
            cntUnitImageFileNameNotMatch++;
        } else {
            cntGoodsGroupImageSum++;
            cntGoodsGroupImageFileNameNotMatch++;
        }
    }

    /**
     * 商品グループ画像の登録更新処理を行う
     *
     * @param goodsGroupEntity    商品グループエンティティ
     * @param goodsGroupDir       商品管理ディレクトリ
     * @param imageFile           画像ファイル
     * @param goodsGroupImageList 商品グループ画像リスト
     */
    protected void registUpdateGoodsGroupImage(GoodsGroupEntity goodsGroupEntity,
                                               File goodsGroupDir,
                                               File imageFile,
                                               List<GoodsGroupImageEntity> goodsGroupImageList) {
        // ファイル名を作成する
        String imageFileName = goodsGroupDir.getName().concat(DELIMITER_SLASH).concat(imageFile.getName());

        // ファイル名の妥当性チェックを行い、登録用に商品グループ画像エンティティを作成する
        GoodsGroupImageEntity registGoodsGroupImage =
                        createGoodsGroupImageEntity(goodsGroupEntity, imageFile, goodsGroupDir, goodsGroupImageList);
        if (registGoodsGroupImage == null) {
            // ファイル名が不適切の為、次のファイルを処理する
            return;
        }

        boolean isFileExistFlag = false;
        for (GoodsGroupImageEntity entity : goodsGroupImageList) {
            if (isSameFileName(imageFileName, entity.getImageFileName())) {
                isFileExistFlag = true;
                // 商品グループ画像が取得できた場合。「③商品画像 更新」で集計し、DBを更新します。
                addCntUpdate(goodsGroupEntity.getGoodsGroupCode(), imageFile, registGoodsGroupImage, null,
                             GOODSGROUPIMAGE_FLAG
                            );
                goodsGroupimageDao.update(registGoodsGroupImage);
                break;
            }
        }

        if (!isFileExistFlag) {
            // 商品グループ画像が取得できなかった場合。「④商品画像 登録」で集計します。
            goodsGroupimageDao.insert(registGoodsGroupImage);
            addCntRegist(goodsGroupEntity.getGoodsGroupCode(), imageFile, registGoodsGroupImage, null,
                         GOODSGROUPIMAGE_FLAG
                        );
        }

    }

    /**
     * 商品規格画像の登録更新処理を行う
     *
     * @param goodsGroupEntity  商品グループエンティティ
     * @param goodsGroupDir     商品管理ディレクトリ
     * @param imageFile         画像ファイル
     * @param goodsImageDtoList 商品規格画像公開ステータスDto
     */
    protected void registUpdateUnitImage(GoodsGroupEntity goodsGroupEntity,
                                         File goodsGroupDir,
                                         File imageFile,
                                         List<GoodsImageOpenstatusDto> goodsImageDtoList) {
        // ファイル名を作成する
        String imageFileName = goodsGroupDir.getName().concat(DELIMITER_SLASH).concat(imageFile.getName());

        // ファイル名の妥当性チェックを行い、登録用に商品規格画像エンティティを作成する
        GoodsImageEntity registGoodsImageEntity =
                        createGoodsImageEntity(goodsGroupEntity, imageFile, goodsGroupDir, goodsImageDtoList);
        if (registGoodsImageEntity == null) {
            // ファイル名が不適切の為、次のファイルを処理する
            return;
        }

        // ファイル名より規格画像コードを取得する。
        String goodsCode = getGoodsCode(imageFile);
        boolean isUnitImageCodeExistFlag = false;
        boolean isFileExistFlag = false;
        HTypeGoodsSaleStatus status = HTypeGoodsSaleStatus.DELETED;
        for (GoodsImageOpenstatusDto dto : goodsImageDtoList) {

            // ファイルの規格画像コードと一致する規格画像コードが存在する場合フラグを立てる
            if (StringUtils.isEmpty(dto.getGoodsCode())) {
                continue;
            }
            if (dto.getGoodsCode().equals(goodsCode)) {
                isUnitImageCodeExistFlag = true;

                // 規格画像コードが一致する規格の販売状態が削除でない場合に設定する
                if (!HTypeGoodsSaleStatus.DELETED.equals(dto.getSaleStatusPc())) {
                    status = dto.getSaleStatusPc();

                    // 同じファイルが存在する場合、「③商品画像 更新」で集計し、DBを更新します。
                    if (isSameFileName(imageFileName, dto.getImageFileName())) {
                        isFileExistFlag = true;
                        addCntUpdate(goodsGroupEntity.getGoodsGroupCode(), imageFile, null, registGoodsImageEntity,
                                     UNITIMAGE_FLAG
                                    );
                        goodsImageDao.update(registGoodsImageEntity);
                        // 2023-renew No76 from here
                        // 規格画像有無更新
                        goodsDao.updateUnitImageFlag(HTypeUnitImageFlag.ON, dto.getGoodsSeq());
                        // 2023-renew No76 to here
                        break;
                    }
                }
            }
        }

        // 規格画像コードが存在しない
        if (!isUnitImageCodeExistFlag) {
            addUnitImageCodeNotExist(goodsGroupEntity.getGoodsGroupCode(), goodsCode, imageFileName, null);
            return;
        }

        // 販売状態が削除のみの場合、「②商品が削除されている」で集計します。
        if (HTypeGoodsSaleStatus.DELETED.equals(status)) {
            addCntOpenStateDel(goodsGroupEntity.getGoodsGroupCode(), goodsCode, imageFileName,
                               DETAILE_UNITIMAGECODE_DELETE, UNITIMAGE_FLAG
                              );
            return;
        }

        // 商品規格画像が取得できなかった場合。「④商品画像 登録」で集計します。
        if (!isFileExistFlag) {
            goodsImageDao.insert(registGoodsImageEntity);
            // 2023-renew No76 from here
            // 規格画像有無更新
            goodsDao.updateUnitImageFlag(HTypeUnitImageFlag.ON, registGoodsImageEntity.getGoodsSeq());
            // 2023-renew No76 to here
            addCntRegist(goodsGroupEntity.getGoodsGroupCode(), imageFile, null, registGoodsImageEntity, UNITIMAGE_FLAG);
        }
    }

    /**
     * 集計「規格コードが存在しない」<br />
     *
     * @param goodsGroupCode 商品管理番号
     * @param goodsCode      商品コード
     * @param fileName       画像ファイル名
     * @param details        詳細
     */
    protected void addUnitImageCodeNotExist(String goodsGroupCode, String goodsCode, String fileName, String details) {
        LOGGER.info("商品コードが存在しない - " + goodsGroupCode + " - " + fileName);

        // 削除リストに追加します。
        addProcErrorCsvDtoList(goodsGroupCode, goodsCode, EXPLAIN_UNITIMAGECODE_NOTEXIST, fileName, details);

        // cnt追加します。
        cntGoodsGroupSumMap.put(goodsGroupCode, goodsGroupCode);
        cntUnitImageSum++;
        cntUnitImageCodeNotExistMap.put(goodsGroupCode, goodsGroupCode);
        cntUnitImageCodeNotExist++;
    }

    /**
     * 規格画像ファイル名から規格コードを取得する<br />
     *
     * @param imageFile 規格画像ファイル
     * @return 規格コード
     */
    protected String getGoodsCode(File imageFile) {
        String[] fileNameArray = imageFile.getName().split(UNIT_FILE_NAME_SEPARATOR);
        return fileNameArray[1].substring(0, fileNameArray[1].lastIndexOf("."));
    }

    /**
     * 規格画像ファイル名の妥当性チェックを行い、登録用に商品規格画像エンティティを作成する<br />
     *
     * @param goodsGroupEntity  商品グループエンティティ
     * @param imageFile         画像ファイル
     * @param goodsGroupDir     商品管理ディレクトリ
     * @param goodsImageDtoList 商品規格画像公開ステータスDtoリスト
     * @return 商品規格画像エンティティ ファイル名が不正な場合はNULLを返却
     */
    protected GoodsImageEntity createGoodsImageEntity(GoodsGroupEntity goodsGroupEntity,
                                                      File imageFile,
                                                      File goodsGroupDir,
                                                      List<GoodsImageOpenstatusDto> goodsImageDtoList) {

        GoodsImageEntity goodsImageEntity = new GoodsImageEntity();

        // 画像ファイルから登録する値を生成します。
        String filename = imageFile.getName();
        String[] filenameArray = filename.split(UNIT_FILE_NAME_SEPARATOR);
        // ファイル名を作成する
        String imageFileName = goodsGroupDir.getName().concat(DELIMITER_SLASH).concat(filename);

        String goodsCodeFromFileName = getGoodsCode(imageFile);

        // jpegファイルかのチェック
        if (!confirm(imageFile)) {
            addFileNameNotMatch(goodsGroupEntity.getGoodsGroupCode(), goodsCodeFromFileName, imageFileName,
                                MessageFormat.format(DETAILE_EXTENSION_ERROR, getExtension(filename)), UNITIMAGE_FLAG
                               );
            return null;
        }

        // 商品管理番号のチェック
        if (!goodsGroupDir.getName().equals(filenameArray[0])) {
            addFileNameNotMatch(goodsGroupEntity.getGoodsGroupCode(), goodsCodeFromFileName, imageFileName,
                                MessageFormat.format(DETAILE_GOODSGROUPCODE_ERROR, filenameArray[0]), UNITIMAGE_FLAG
                               );
            return null;
        }

        // 商品情報を取得する
        HTypeDisplayStatus displayStatus = null;
        Integer goodsSeq = null;
        String goodsCode = null;
        List<String> goodsCodeList = new ArrayList<>();
        for (GoodsImageOpenstatusDto dto : goodsImageDtoList) {
            goodsCodeList.add(dto.getGoodsCode());
            if (dto.getGoodsGroupCode() != null && dto.getGoodsCode() != null && dto.getGoodsCode()
                                                                                    .equals(goodsCodeFromFileName)) {
                goodsSeq = dto.getGoodsSeq();
                goodsCode = dto.getGoodsCode();
                if (dto.getDisplayFlag() == null) {
                    if (dto.getSaleStatusPc().equals(HTypeGoodsSaleStatus.SALE)) {
                        displayStatus = HTypeDisplayStatus.DISPLAY;
                    } else if (dto.getSaleStatusPc().equals(HTypeGoodsSaleStatus.NO_SALE)) {
                        displayStatus = HTypeDisplayStatus.NO_DISPLAY;
                    }
                } else {
                    displayStatus = dto.getDisplayFlag();
                }
                break;
            }
        }

        if (!goodsCodeList.contains(goodsCodeFromFileName)) {
            addUnitImageCodeNotExist(goodsGroupEntity.getGoodsGroupCode(), goodsCode, imageFileName, null);
            return null;
        }

        // 商品番号のチェック
        if (goodsCode == null || !goodsCode.equals(goodsCodeFromFileName)) {
            addFileNameNotMatch(goodsGroupEntity.getGoodsGroupCode(), goodsCode, imageFileName,
                                MessageFormat.format(DETAILE_GOODSGROUPCODE_ERROR, goodsCodeFromFileName),
                                UNITIMAGE_FLAG
                               );
            return null;
        }

        // エンティティに値を設定する
        goodsImageEntity.setGoodsGroupSeq(goodsGroupEntity.getGoodsGroupSeq());
        goodsImageEntity.setGoodsSeq(goodsSeq);
        goodsImageEntity.setImageFileName(imageFileName);
        goodsImageEntity.setDisplayFlag(displayStatus);
        goodsImageEntity.setRegistTime(outputTime);
        goodsImageEntity.setUpdateTime(outputTime);
        goodsImageEntity.setTmpFilePath(imageFile.getPath());
        return goodsImageEntity;
    }

    /**
     * 商品グループ画像ファイル名の妥当性チェックを行い、登録用に商品規格画像エンティティを作成する<br />
     *
     * @param goodsGroupEntity                 商品グループエンティティ
     * @param imageFile                        画像ファイル
     * @param goodsGroupDir                    商品管理ディレクトリ
     * @param goodsGroupImageOpenstatusDtoList 商品グループ画像情報リスト
     * @return 商品グループ画像エンティティ ファイル名が不正な場合はNULLを返却
     */
    protected GoodsGroupImageEntity createGoodsGroupImageEntity(GoodsGroupEntity goodsGroupEntity,
                                                                File imageFile,
                                                                File goodsGroupDir,
                                                                List<GoodsGroupImageEntity> goodsGroupImageOpenstatusDtoList) {

        GoodsGroupImageEntity goodsGroupImageEntity = ApplicationContextUtility.getBean(GoodsGroupImageEntity.class);

        // 画像ファイルから登録する値を生成します。
        String filename = imageFile.getName();
        String[] filenameArray = filename.split(FILE_NAME_SEPARATOR);
        // ファイル名を作成する
        String imageFileName = goodsGroupDir.getName().concat(DELIMITER_SLASH).concat(filename);

        // ファイル命名規則チェック
        if (filenameArray.length != 2) {
            addFileNameNotMatch(goodsGroupEntity.getGoodsGroupCode(), null, imageFileName,
                                MessageFormat.format(DETAILE_FILENAME_SPLIT_ERROR, filename), GOODSGROUPIMAGE_FLAG
                               );
            return null;
        }

        // jpegファイルかのチェック
        if (!confirm(imageFile)) {
            addFileNameNotMatch(goodsGroupEntity.getGoodsGroupCode(), null, imageFileName,
                                MessageFormat.format(DETAILE_EXTENSION_ERROR, getExtension(filename)),
                                GOODSGROUPIMAGE_FLAG
                               );
            return null;
        }

        // 画像種別内連番チェック
        // 画像種別内連番の桁数チェック
        String versionString = null;
        try {
            versionString = filenameArray[1].substring(0, filenameArray[1].lastIndexOf("."));
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            addFileNameNotMatch(goodsGroupEntity.getGoodsGroupCode(), null, imageFileName,
                                MessageFormat.format(DETAILE_VERSIONNO_DETAILEIMAGE_RANGE_ERROR, goodsGroupImageMaxCnt,
                                                     versionString
                                                    ), GOODSGROUPIMAGE_FLAG
                               );
            return null;
        }

        if (!versionString.matches(MATCH_VERSION_NO)) {
            addFileNameNotMatch(goodsGroupEntity.getGoodsGroupCode(), null, imageFileName,
                                MessageFormat.format(DETAILE_VERSIONNO_DETAILEIMAGE_RANGE_ERROR, goodsGroupImageMaxCnt,
                                                     versionString
                                                    ), GOODSGROUPIMAGE_FLAG
                               );
            return null;
        }
        // 画像種別内連番の範囲チェック
        int versionNo = Integer.parseInt(versionString);
        // 商品グループ画像の詳細画像の場合、１～最大値
        if (versionNo < 1 || versionNo > goodsGroupImageMaxCnt) {
            addFileNameNotMatch(goodsGroupEntity.getGoodsGroupCode(), null, imageFileName,
                                MessageFormat.format(DETAILE_VERSIONNO_DETAILEIMAGE_RANGE_ERROR, goodsGroupImageMaxCnt,
                                                     versionString
                                                    ), GOODSGROUPIMAGE_FLAG
                               );
            return null;
        }

        // 商品管理番号のチェック
        if (!goodsGroupDir.getName().equals(filenameArray[0])) {
            addFileNameNotMatch(goodsGroupEntity.getGoodsGroupCode(), null, imageFileName,
                                MessageFormat.format(DETAILE_GOODSGROUPCODE_ERROR, filenameArray[0]),
                                GOODSGROUPIMAGE_FLAG
                               );
            return null;
        }

        // エンティティに値を設定する
        goodsGroupImageEntity.setGoodsGroupSeq(goodsGroupEntity.getGoodsGroupSeq());
        goodsGroupImageEntity.setImageTypeVersionNo(versionNo);
        goodsGroupImageEntity.setImageFileName(imageFileName);
        goodsGroupImageEntity.setRegistTime(outputTime);
        goodsGroupImageEntity.setUpdateTime(outputTime);
        return goodsGroupImageEntity;
    }

    /**
     * ファイル名が同一視できるか確認する<br/>
     * jpgとjpegの違いを吸収する<br/>
     *
     * @param src1 ファイル名1
     * @param src2 ファイル名2
     * @return true 同一視可能 / false 同一視不可能
     */
    protected boolean isSameFileName(String src1, String src2) {
        if (StringUtils.isEmpty(src1)) {
            return false;
        }
        if (StringUtils.isEmpty(src2)) {
            return false;
        }
        if (src1.equals(src2)) {
            return true;
        }
        for (String ext : EXTENSION_LIST) {
            // 拡張子をjpegに寄せてチェックする
            if (src1.toLowerCase()
                    .replaceAll(ext + "$", FILE_NAME_EXTENSION_JPEG)
                    .equals(src2.toLowerCase().replaceAll(ext + "$", FILE_NAME_EXTENSION_JPEG))) {
                return true;
            }
        }
        return false;
    }

    /**
     * ファイルパスの拡張子違いのファイルを削除する<br/>
     * ※ファイルが存在しない場合の例外は握りつぶす
     * ※拡張子が小文字の場合のみ対応
     * ※拡張子がJpEgなど大文字小文字が混在している場合には対応していない
     *
     * @param path 対象ファイルパス
     */
    protected void cleanUpFile(String path) {
        if (StringUtils.isEmpty(path)) {
            return;
        }
        // パスの拡張子を取得
        int i = path.lastIndexOf('.');
        String extension = "";
        if (i > 0) {
            extension = path.substring(i);
        }
        FileOperationUtility fileOperationUtility = ApplicationContextUtility.getBean(FileOperationUtility.class);
        for (String ext : EXTENSION_LIST) {
            String targetPath = path.replace(extension, ext);
            try {
                fileOperationUtility.remove(targetPath);
            } catch (IOException ie) {
                // ログだけ吐いて握りつぶす
                LOGGER.error("例外処理が発生しました", ie);
            }
        }
    }

    /**
     * 集計「③商品画像 更新」<br />
     *
     * @param goodsGroupCode 商品管理番号
     * @param imageFile      画像ファイル
     * @param groupImage     商品グループ画像エンティティ
     * @param goodsImage     商品規格画像エンティティ
     * @param imageKind      true..規格画像 false..商品グループ画像
     */
    protected void addCntUpdate(String goodsGroupCode,
                                File imageFile,
                                GoodsGroupImageEntity groupImage,
                                GoodsImageEntity goodsImage,
                                boolean imageKind) {
        LOGGER.debug("③商品画像 更新 - " + goodsGroupCode + " - " + imageFile.getName());

        // 画像URLリストに追加します。
        addProcSuccessCsvDtoList(goodsGroupCode, imageFile, groupImage, goodsImage, MODE_UPDATE);

        // cnt追加します。
        cntGoodsGroupUpdateMap.put(goodsGroupCode, goodsGroupCode);
        cntGoodsGroupSumMap.put(goodsGroupCode, goodsGroupCode);

        if (imageKind) {
            cntUnitImageUpdate++;
            cntUnitImageSum++;
        } else {
            cntGoodsGroupImageUpdate++;
            cntGoodsGroupImageSum++;
        }
    }

    /**
     * 画像ファイルから拡張子を取得する<br />
     *
     * @param filename 画像ファイル
     * @return 拡張子
     */
    protected String getExtension(String filename) {
        String extension = "";
        int indexPeriod = filename.lastIndexOf(".");
        if (indexPeriod != -1) {
            extension = filename.substring(indexPeriod);
        }
        return extension;
    }

    /**
     * 画像ファイルから画像種類を取得する<br />
     *
     * @param filename 画像ファイル
     * @return 画像種類
     */
    protected String getImageKind(String filename) {
        String imageKind = "";
        int indexPeriod = filename.lastIndexOf(".");
        int indexHyphen = filename.lastIndexOf("_");
        if (indexPeriod != -1 && indexHyphen != -1) {
            imageKind = filename.substring(indexHyphen, indexPeriod);
        }
        return imageKind;
    }

    /**
     * 完全に処理が失敗した旨の管理者向けメールを送信する。
     *
     * @param exceptionName 例外名
     * @param msg           エラー結果メッセージ
     * @return true:成功、false:失敗
     */
    protected boolean sendAdministratorErrorMail(final String exceptionName, String msg) {

        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            final Map<String, String> valueMap = new HashMap<>();
            valueMap.put("SYSTEM", systemName);
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_GOODSIMAGE_UPDATE.getLabel());
            valueMap.put("SHOP_SEQ", super.getShopSeq().toString());
            if (exceptionName != null) {
                String resultMsg = "処理中に" + exceptionName + "が発生しました。" + LINE_FEED_CR + LINE_FEED_CR;
                valueMap.put("RESULT", resultMsg);
            } else {
                String resultMsg = "キャンセル要求があったため処理は中断されました。" + LINE_FEED_CR + LINE_FEED_CR;
                valueMap.put("RESULT", resultMsg);
            }

            if (msg != null) {
                valueMap.put("LIST", msg);
            } else {
                valueMap.put("LIST", "");
            }

            if (LOGGER.isDebugEnabled()) {
                valueMap.put("DEBUG", "1");
            } else {
                valueMap.put("DEBUG", "0");
            }

            mailContentsMap.put("error", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.GOODSIMAGE_UPDATE_ERROR_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_GOODSIMAGE_UPDATE.getLabel() + "報告");
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);

            LOGGER.info("管理者へ通知メールを送信しました。");

            return true;

        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);

            return false;
        }
    }

    /**
     * 管理者向けメールを送信する
     *
     * @return true:成功、false:失敗
     */
    protected boolean sendAdministratorMail() {
        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            final Map<String, String> valueMap = new HashMap<>();
            valueMap.put("SYSTEM", systemName);
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_GOODSIMAGE_UPDATE.getLabel());
            valueMap.put("SHOP_SEQ", super.getShopSeq().toString());
            valueMap.put("GOODS_IMAGE_PATH", imageDir);

            // 処理結果件数 商品管理番号毎
            valueMap.put("CNT_GOODSGROUP_SUM", Integer.toString(cntGoodsGroupSumMap.size()));
            valueMap.put("CNT_GOODSGROUP_NOTEXIST", Integer.toString(cntGoodsGroupNotExistMap.size()));
            valueMap.put("CNT_UNITIMAGECODE_NOTEXIST", Integer.toString(cntUnitImageCodeNotExistMap.size()));
            valueMap.put("CNT_GOODSGROUP_FILENAME_NOTMATCH", Integer.toString(cntGoodsGroupFileNameNotMatchMap.size()));
            valueMap.put("CNT_GOODSGROUP_OPENSTATEDEL", Integer.toString(cntGoodsGroupOpenStateDelMap.size()));
            valueMap.put("CNT_GOODSGROUP_REGIST", Integer.toString(cntGoodsGroupRegistMap.size()));
            valueMap.put("CNT_GOODSGROUP_DELETE", Integer.toString(cntGoodsGroupDeleteMap.size()));
            valueMap.put("CNT_GOODSGROUP_UPDATE", Integer.toString(cntGoodsGroupUpdateMap.size()));

            // 処理結果件数 画像毎
            // 商品グループ画像
            int goodsGroupImageSuccessCnt =
                            cntGoodsGroupImageRegist + cntGoodsGroupImageDelete + cntGoodsGroupImageUpdate;
            int goodsGroupImageErrorCnt = cntGoodsGroupImageNotExist + cntGoodsGroupImageFileNameNotMatch
                                          + cntGoodsGroupImageOpenStateDel;
            valueMap.put("CNT_GOODSGROUPIMAGE_SUM", Integer.toString(cntGoodsGroupImageSum));
            valueMap.put("CNT_GOODSGROUPIMAGE_SUCCESS_SUM", Integer.toString(goodsGroupImageSuccessCnt));
            valueMap.put("CNT_GOODSGROUPIMAGE_ERROR_SUM", Integer.toString(goodsGroupImageErrorCnt));
            valueMap.put("CNT_GOODSGROUPIMAGE_NOTEXIST", Integer.toString(cntGoodsGroupImageNotExist));
            valueMap.put("CNT_GOODSGROUPIMAGE_FILENAME_NOTMATCH", Integer.toString(cntGoodsGroupImageFileNameNotMatch));
            valueMap.put("CNT_GOODSGROUPIMAGE_OPENSTATEDEL", Integer.toString(cntGoodsGroupImageOpenStateDel));
            valueMap.put("CNT_GOODSGROUPIMAGE_REGIST", Integer.toString(cntGoodsGroupImageRegist));
            valueMap.put("CNT_GOODSGROUPIMAGE_DELETE", Integer.toString(cntGoodsGroupImageDelete));
            valueMap.put("CNT_GOODSGROUPIMAGE_UPDATE", Integer.toString(cntGoodsGroupImageUpdate));

            // 規格画像
            int unitImageSuccessCnt = cntUnitImageRegist + cntUnitImageDelete + cntUnitImageUpdate;
            int unitImageErrorCnt = cntUnitImageNotExist + cntUnitImageCodeNotExist + cntUnitImageFileNameNotMatch
                                    + cntUnitImageOpenStateDel;
            valueMap.put("CNT_UNITIMAGE_SUM", Integer.toString(cntUnitImageSum));
            valueMap.put("CNT_UNITIMAGE_SUCCESS_SUM", Integer.toString(unitImageSuccessCnt));
            valueMap.put("CNT_UNITIMAGE_ERROR_SUM", Integer.toString(unitImageErrorCnt));
            valueMap.put("CNT_UNITIMAGE_NOTEXIST", Integer.toString(cntUnitImageNotExist));
            valueMap.put("CNT_UNITIMAGE_UNITIMAGECODE_NOTEXIST", Integer.toString(cntUnitImageCodeNotExist));
            valueMap.put("CNT_UNITIMAGE_FILENAME_NOTMATCH", Integer.toString(cntUnitImageFileNameNotMatch));
            valueMap.put("CNT_UNITIMAGE_OPENSTATEDEL", Integer.toString(cntUnitImageOpenStateDel));
            valueMap.put("CNT_UNITIMAGE_REGIST", Integer.toString(cntUnitImageRegist));
            valueMap.put("CNT_UNITIMAGE_DELETE", Integer.toString(cntUnitImageDelete));
            valueMap.put("CNT_UNITIMAGE_UPDATE", Integer.toString(cntUnitImageUpdate));

            StringBuilder sb = new StringBuilder();
            // 画像移動に失敗がある場合
            if (toImageDirCopyErrorMsgList.size() != 0 || toErrorDirCopyErrorMsgList.size() != 0
                || toImageDirDeleteErrorMsgList.size() != 0) {
                valueMap.put("WARNING_FLG", "[*]");
                valueMap.put("WARNING_INFO", "移動または削除に失敗した画像があります。" + LINE_FEED_CR + "詳しくはサーバログを参照してください");
                sb.append(LINE_FEED_CR).append("---");
                if (toImageDirCopyErrorMsgList.size() != 0 || toErrorDirCopyErrorMsgList.size() != 0) {
                    sb.append(LINE_FEED_CR).append("※以下の画像は、移動処理に失敗しました。").append(LINE_FEED_CR);
                    // 画像ディレクトリ移動時エラーメッセージ出力
                    if (toImageDirCopyErrorMsgList.size() != 0) {
                        sb.append(LINE_FEED_CR)
                          .append("■移動先 : [")
                          .append(imageDir)
                          .append("]")
                          .append(LINE_FEED_CR)
                          .append(LINE_FEED_CR);
                        for (String msg : toImageDirCopyErrorMsgList) {
                            sb.append("　・").append(msg).append(LINE_FEED_CR);
                        }
                    }
                    // エラーディレクトリ移動時エラーメッセージ出力
                    if (toErrorDirCopyErrorMsgList.size() != 0) {
                        sb.append(LINE_FEED_CR)
                          .append("■移動先 : [")
                          .append(errorDir)
                          .append("]")
                          .append(LINE_FEED_CR)
                          .append(LINE_FEED_CR);
                        for (String msg : toErrorDirCopyErrorMsgList) {
                            sb.append("　・").append(msg).append(LINE_FEED_CR);
                        }
                    }
                }
                if (toImageDirDeleteErrorMsgList.size() != 0) {
                    sb.append(LINE_FEED_CR).append("※以下の画像は、削除処理に失敗しました。").append(LINE_FEED_CR);
                    for (String msg : toImageDirDeleteErrorMsgList) {
                        sb.append("　・").append(msg).append(LINE_FEED_CR);
                    }
                }
            } else {
                valueMap.put("WARNING_FLG", "");
                valueMap.put("WARNING_INFO", "");
            }

            // 画像コピー結果
            valueMap.put("LIST", sb.toString());

            // 本文
            mailContentsMap.put("admin", valueMap);

            // 件名
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_GOODSIMAGE_UPDATE.getLabel() + "報告"
                               + valueMap.get("WARNING_FLG"));

            mailDto.setMailTemplateType(HTypeMailTemplateType.GOODSIMAGE_UPDATE_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());

            mailDto.setMailContentsMap(mailContentsMap);

            // 添付ファイル
            LOGGER.info("結果ファイルを添付");
            mailDto.setAttachFileFlag(true);
            List<AttachFileDto> attachFileList = new ArrayList();

            // 商品画像処理CSV
            byte[] attachmentUrl = this.getCsvContent(workDir + DELIMITER_SLASH + csvFileNameProcSuccess);
            AttachFileDto attachmentUrlDto = new AttachFileDto();
            attachmentUrlDto.setAttachFileName(csvFileNameProcSuccess);
            attachmentUrlDto.setAttachFileByte(attachmentUrl);
            attachFileList.add(attachmentUrlDto);

            // 商品画像処理エラーCSV
            byte[] attachmentDel = this.getCsvContent(workDir + DELIMITER_SLASH + csvFileNameProcError);
            AttachFileDto attachmentDelDto = new AttachFileDto();
            attachmentDelDto.setAttachFileName(csvFileNameProcError);
            attachmentDelDto.setAttachFileByte(attachmentDel);
            attachFileList.add(attachmentDelDto);

            // 添付ファイル一覧を設定
            mailDto.setAttachFileList(attachFileList);

            LOGGER.info("CSVファイルが添付されたメールを送信します。");
            LOGGER.info("送信先：" + this.mailSetting.getNotificationReceivers());

            this.mailSendService.execute(mailDto);
            LOGGER.info("管理者へ通知メールを送信しました。");

            // csvファイルをバックアップディレクトリに移動させます。
            backupCsv(workDir + DELIMITER_SLASH + csvFileNameProcSuccess,
                      backupDir + DELIMITER_SLASH + csvFileNameProcSuccess, true
                     );
            backupCsv(workDir + DELIMITER_SLASH + csvFileNameProcError,
                      backupDir + DELIMITER_SLASH + csvFileNameProcError, true
                     );

            return true;

        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);

            return false;
        }
    }

    /**
     * 添付用CSV作成
     *
     * @param csvFilePath 添付ファイルパス
     * @return CSVファイルのバイト配列
     * @throws Exception 例外
     */
    protected byte[] getCsvContent(String csvFilePath) throws Exception {
        InputStream in = null;
        ByteArrayOutputStream baos = null;

        try {
            in = new FileInputStream(csvFilePath);

            baos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int j;
            while ((j = in.read(b)) != -1) {
                baos.write(b, 0, j);
            }

        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error("ERROR : getCsvContent", e);
                }
            }
        }
        return baos.toByteArray();
    }

    /**
     * jpeg ファイルかを検証する
     *
     * @param file ファイル
     * @return 検証結果
     */
    protected boolean confirm(File file) {

        try {
            // マジックナンバー検証
            if (!confirmMagicNumber(file, MAGIC_NUMBER1)) {
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);
            return false;
        }

        // 拡張子検証
        if (confirmExtension(file)) {
            return true;
        }

        return false;
    }

    /**
     * CSVをバックアップディレクトリに移動させます。<br />
     *
     * @param fromPath  コピー元
     * @param toPath    コピー先
     * @param removeSrc true：コピー元削除 false:コピー先削除
     * @throws Exception コピーエラー
     */
    protected void backupCsv(String fromPath, String toPath, boolean removeSrc) throws Exception {
        File fromFile = new File(fromPath);
        File toFile = new File(toPath);
        FileOperationUtility fileOperationUtility = ApplicationContextUtility.getBean(FileOperationUtility.class);
        fileOperationUtility.put(fromFile, toFile, removeSrc);
    }

    /**
     * 拡張子の検証
     *
     * @param file ファイル
     * @return 検証結果
     */
    protected boolean confirmExtension(File file) {
        for (String extension : EXTENSION_LIST) {
            if (file.getName().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * マジックナンバーの検証
     *
     * @param file        ファイル
     * @param magicNumber あるべきマジックナンバー
     * @return 検証結果
     * @throws Exception 発生した例外
     */
    protected boolean confirmMagicNumber(File file, final byte[] magicNumber) throws Exception {

        // マジックナンバーすら確認できない場合
        if (file.length() < magicNumber.length) {
            return false;
        }

        InputStream inStream = null;

        try {
            inStream = new FileInputStream(file);
            byte[] fileMagicNumber = new byte[magicNumber.length];
            inStream.read(fileMagicNumber);

            final List<String> comp = DiffUtil.diff(magicNumber, fileMagicNumber);
            return comp.isEmpty();

        } finally {
            if (inStream != null) {
                inStream.close();
            }
        }

    }
}
