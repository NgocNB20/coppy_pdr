package jp.co.hankyuhanshin.itec.hitmall.batch.offline.zip.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.PreProcessUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.throwable.NoDataException;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.zip.dto.ZipCodeCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dao.zipcode.ZipCodeDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvReaderOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.zipcode.ZipCodeEntity;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvReaderUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.InstantMailSetting;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 機能概要：郵便番号更新バッチ
 * 作成日：2021/03/30
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public class ZipCodeUpdateBatch extends AbstractBatch {

    /**
     * Log
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZipCodeUpdateBatch.class);

    /**
     * 管理者用メール送信設定
     */
    private final InstantMailSetting mailSetting;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    /**
     * 郵便番号Dao
     */
    private final ZipCodeDao zipCodeDao;

    /**
     * メール送信サービス
     */
    private final MailSendService mailSendService;

    /**
     * 前処理共通機能
     */
    private final PreProcessUtil preProcessUtil;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    /**
     * データフルパス
     */
    private static final String DL_URL = "https://www.post.japanpost.jp/zipcode/dl/kogaki/zip/";

    /**
     * ダウンロード先
     */
    private final String downloadDirectory;

    /**
     * 全国一括登録フラグ
     */
    private boolean allFlg;

    /**
     * コンストラクタ<br/>
     */
    public ZipCodeUpdateBatch(Environment environment) {
        this.dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        this.zipCodeDao = ApplicationContextUtility.getBean(ZipCodeDao.class);
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.preProcessUtil = new PreProcessUtil();
        this.exitMessageUtil = new BatchExitMessageUtil();
        this.mailSetting = new InstantMailSetting(environment.getProperty("mail.setting.zipcode.smtp.server"),
                                                  environment.getProperty("mail.setting.zipcode.mail.from"), null, null,
                                                  Collections.singletonList(environment.getProperty(
                                                                  "mail.setting.zipcode.mail.receivers"))
        );
        this.downloadDirectory = environment.getProperty("batch.file.path");
    }

    /**
     * 郵便番号データの追加、更新を行う<br/>
     *
     * @return RepeatStatus
     * @throws Exception 例外
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        // コンテキスト取得
        ExecutionContext executionContext =
                        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();

        // 変数初期化
        int totalCount = 0;
        int count = 0;
        String msg = null;

        //  // バッチのジョッブ情報取得
        JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobParameters();
        String administratorId = jobParameters.getString("administratorId");
        String batchRunType = jobParameters.getString("batchRunType");
        String shopSeq = jobParameters.getString("shopSeq");
        if (!StringUtils.isEmpty(shopSeq)) {
            super.setShopSeq(Integer.valueOf(shopSeq));
        }

        // 処理対象年
        String yy;

        // 処理対象月
        String mm;

        // 処理対象日
        String dd;

        // 処理対象ファイル
        String filePath;

        // 初回登録
        if (zipCodeDao.getFirstEntity() == null) {
            // 全国一括登録フラグ設定
            allFlg = true;

            // 更新対象年月の設定
            LocalDate localDate = LocalDate.now();

            // 処理対象年
            yy = preProcessUtil.getYearString(localDate);

            // 処理対象月
            mm = preProcessUtil.getMonthString(localDate);

            // 処理対象日
            dd = preProcessUtil.getDayString(localDate);

            // 登録データ
            String registData = "ken_all_" + yy + mm + dd + ".csv";

            // ダウンロードファイル名
            String zipFile = "ken_all.zip";

            // 登録ファイルだ取得
            filePath = preProcessUtil.zipCodeRegistPreProcess(registData, zipFile, downloadDirectory, DL_URL);

        } else {
            // 全国一括登録フラグ設定
            allFlg = false;

            // 更新対象年月の設定
            LocalDate localDate = LocalDate.now().minusMonths(1);

            // 処理対象年
            yy = preProcessUtil.getYearString(localDate);

            // 処理対象月
            mm = preProcessUtil.getMonthString(localDate);

            // 更新データ
            String updateData = "update_" + yy + mm;

            // ダウンロードファイル(新規追加)名
            String addZipFile = "add_" + yy + mm + ".zip";

            // ダウンロードファイル(廃止)名
            String delZipFile = "del_" + yy + mm + ".zip";

            // 更新ファイルだ取得
            filePath = preProcessUtil.zipCodeUpdatePreProcess(updateData, addZipFile, delZipFile, downloadDirectory,
                                                              DL_URL
                                                             );
        }

        try {
            if (StringUtils.isEmpty(filePath)) {
                throw new NoDataException();
            }
            File file = new File(filePath);

            // Csvアップロード結果Dto作成
            CsvUploadResult csvUploadResult = new CsvUploadResult();

            // 出荷登録CSVDtoを取得
            List<ZipCodeCsvDto> zipCodeCsvDtoList;
            CsvReaderUtil csvReaderUtil = new CsvReaderUtil();

            // CSV読み込みオプションを設定する
            CsvReaderOptionDto csvReaderOptionDto = new CsvReaderOptionDto();
            csvReaderOptionDto.setValidLimit(CsvUploadResult.CSV_UPLOAD_VALID_LIMIT);
            csvReaderOptionDto.setInputHeader(false);

            zipCodeCsvDtoList = (List<ZipCodeCsvDto>) csvReaderUtil.readCsv(file, ZipCodeCsvDto.class, csvUploadResult,
                                                                            csvReaderOptionDto
                                                                           );
            // 処理開始前に引数を確認
            if (!allFlg) {
                // 更新モード
                if (yy == null || yy.isEmpty() || mm == null || mm.isEmpty()) {
                    // 更新モードの場合は引数に年月が必要なのにない
                    LOGGER.error("引数 yy(年)、mm(月) が不正です。");
                    executionContext.put(
                                    BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                                    new BatchExitMessageDto(String.valueOf(totalCount),
                                                                            "引数 yy(年)、mm(月) が不正です。"
                                                    )));
                    throw new Exception("引数 yy(年)、mm(月) が不正です。");
                }
            }

            // ファイルが取得できた場合はtotalCountが「0」以下になることはない想定
            totalCount = zipCodeCsvDtoList.size();

            // CSVファイルにデータが存在しなかった場合
            if (totalCount == 0) {
                throw new NoDataException();
            }

            for (ZipCodeCsvDto csvDto : zipCodeCsvDtoList) {
                count++;

                // 登録/更新処理
                insertOrUpdate(csvDto);
            }

            // 全データ登録完了
            if (allFlg) {
                msg = "郵便番号の全国一括登録が完了しました。";
            } else {
                msg = yy + " 年 " + mm + " 月の郵便番号更新の反映が完了しました。";
            }

            // レポート処理
            report(msg);
            sendAdministratorMail(msg, true);
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(String.valueOf(count),
                                                                    this.getReportString().toString()
                                            )));

            // 正常終了時にはバッチが自動的にコミットを行うので、明示的にコミットを行っていない。
            return RepeatStatus.FINISHED;

        } catch (IOException e) {
            // CSVファイルの桁数、型がテーブルと一致しない場合に発生
            if (allFlg) {
                msg = "郵便番号の全国一括登録を行おうとしましたが、" + count + "行目に不正なデータが含まれていたため処理は無効になりました。";
            } else {
                msg = yy + " 年 " + mm + " 月の郵便番号の更新を行おうとしましたが、" + count + " 行目に不正なデータが含まれていたため処理は無効になりました。";
            }
            LOGGER.error(msg, e);
            report(msg);
            sendAdministratorMail(msg, false);
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(String.valueOf(count),
                                                                    this.getReportString().toString()
                                            )));
            throw new IOException(msg);

        } catch (NoDataException e) {
            // 更新データが「0」件の場合
            if (allFlg) {
                msg = "郵便番号の全国一括登録を行おうとしましたが、対象のファイルにデータが存在しないため、処理は無効になりました。\n";
                msg += "対象のファイル、ダウンロードサイトをご確認ください。\n";
                msg += "(引数 : all=[" + allFlg + "])";
            } else {
                msg = "郵便番号の月次更新を行おうとしましたが、対象のファイルにデータが存在しないため、処理は無効になりました。\n";
                msg += "対象のファイル、ダウンロードサイトをご確認ください。\n";
                msg += "(引数 : yy=[" + yy + "] mm=[" + mm + "] all=[" + allFlg + "])";
            }

            LOGGER.error(e.getClass().toString(), e);
            LOGGER.error(msg);
            report(msg);
            sendAdministratorMail(msg, false);
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(String.valueOf(count),
                                                                    this.getReportString().toString()
                                            )));
            throw e;

        } catch (Exception e) {
            if (allFlg) {
                msg = "郵便番号の全国一括登録を行おうとしましたが、処理中に " + e.getClass().getName() + " が発生したため処理は無効になりました。";
                msg += "(引数 : all=[" + allFlg + "])";
            } else {
                msg = "郵便番号の月次更新を行おうとしましたが、処理中に " + e.getClass().getName() + " が発生したため処理は無効になりました。";
                msg += "(引数 : yy=[" + yy + "] mm=[" + mm + "] all=[" + allFlg + "])";
            }
            if (count > 0) {
                // 読み込みが開始していれば、行数も出力する
                LOGGER.error(String.format("%1$,d行目の処理中に想定外の例外が発生しました。", count));
            }
            LOGGER.error(msg, e);
            sendAdministratorMail(msg, false);
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(String.valueOf(count), msg)));
            throw e;
        }

    }

    /**
     * 郵便番号データを登録／更新する<br/>
     * 郵便番号マスタにデータが存在しない場合は登録処理を<br/>
     * データが存在する場合は更新処理を行う。<br/>
     *
     * @param csvDto 郵便番号データ
     */
    protected void insertOrUpdate(ZipCodeCsvDto csvDto) {
        // プライマリーキーでマスタを検索
        // 存在しなければ新規登録、存在すれば更新処理を行う。
        ZipCodeEntity entity = convertToEntity(csvDto);
        String localCode = entity.getLocalCode();
        String oldZipCode = entity.getOldZipCode();
        String zipCode = entity.getZipCode();
        String prefectureName = entity.getPrefectureName();
        String cityName = entity.getCityName();
        String townName = entity.getTownName();
        ZipCodeEntity searchResult =
                        zipCodeDao.getEntity(localCode, oldZipCode, zipCode, prefectureName, cityName, townName);

        // 現在日時
        Timestamp currentTime = dateUtility.getCurrentTime();

        if (searchResult == null) {
            // ＤＢ上に存在しない住所なので登録
            entity.setRegistTime(currentTime);
            entity.setUpdateTime(currentTime);
            zipCodeDao.insert(entity);
        } else {
            // ＤＢ上に存在する住所なので更新
            entity.setRegistTime(searchResult.getRegistTime());
            entity.setUpdateTime(currentTime);
            zipCodeDao.update(entity);
        }
    }

    /**
     * 郵便番号データを郵便番号Entityに変換<br/>
     *
     * @param csvDto 郵便番号データ
     * @return 郵便番号Entity
     */
    protected ZipCodeEntity convertToEntity(ZipCodeCsvDto csvDto) {
        ZipCodeEntity entity = ApplicationContextUtility.getBean(ZipCodeEntity.class);
        entity.setLocalCode(csvDto.getLocalCode());
        entity.setOldZipCode(csvDto.getOldZipCode());
        entity.setZipCode(csvDto.getZipCode());
        entity.setPrefectureKana(csvDto.getPrefectureKana());
        entity.setCityKana(csvDto.getCityKana());
        entity.setTownKana(csvDto.getTownKana());
        entity.setPrefectureName(csvDto.getPrefectureName());
        entity.setCityName(csvDto.getCityName());
        entity.setTownName(csvDto.getTownName());
        entity.setAnyZipFlag(csvDto.getAnyZipFlag());
        entity.setNumberFlag1(csvDto.getNumberFlag1());
        entity.setNumberFlag2(csvDto.getNumberFlag2());
        entity.setNumberFlag3(csvDto.getNumberFlag3());
        entity.setUpdateVisibleType(csvDto.getUpdateVisibleType());
        entity.setUpdateNoteType(csvDto.getUpdateNoteType());

        return entity;
    }

    /**
     * 管理者向け処理完了メールを送信する。
     *
     * @param msg    完了メッセージ
     * @param result 正常終了時：true　異常終了時：false
     */
    protected void sendAdministratorMail(String msg, boolean result) {
        LOGGER.info("管理者向けメール送信処理を開始します。");

        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            final Map<String, String> valueMap = new HashMap<>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            // プレースホルダーへ結果セット
            valueMap.put("SYSTEM", systemName);
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_ZIP_CODE.getLabel());
            valueMap.put("RESULT", msg);

            if (result) {
                mailDto.setMailTemplateType(HTypeMailTemplateType.ZIPCODE_ADMINISTRATOR_MAIL);
                mailContentsMap.put("admin", valueMap);
            } else {
                mailDto.setMailTemplateType(HTypeMailTemplateType.ZIPCODE_ADMINISTRATOR_ERROR_MAIL);
                mailContentsMap.put("error", valueMap);
            }

            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_ZIP_CODE.getLabel() + "報告");
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);

            LOGGER.info("管理者へ通知メールを送信しました。");
        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);
        }
    }
}
