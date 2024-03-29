package jp.co.hankyuhanshin.itec.hitmall.batch.offline.zip.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.PreProcessUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.throwable.NoDataException;
import jp.co.hankyuhanshin.itec.hitmall.batch.offline.zip.dto.OfficeZipCodeCsvDto;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dao.zipcode.OfficeZipCodeDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.csv.CsvReaderOptionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.zipcode.OfficeZipCodeEntity;
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
 * 機能概要：事業所郵便番号更新バッチ
 * 作成日：2021/03/30
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public class OfficeZipCodeUpdateBatch extends AbstractBatch {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OfficeZipCodeUpdateBatch.class);

    /**
     * 管理者用メール送信設定
     */
    private final InstantMailSetting mailSetting;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    /**
     * 事業所郵便番号Dao
     */
    private final OfficeZipCodeDao officeZipCodeDao;

    /**
     * メール送信サービス
     */
    private final MailSendService mailSendService;

    /**
     * 前処理共通機能
     */
    private final PreProcessUtil preProcessUtil;

    /**
     * データフルパス
     */
    private static final String DL_URL = "https://www.post.japanpost.jp/zipcode/dl/jigyosyo/zip/";

    /**
     * ダウンロード先
     */
    private final String downloadDirectory;

    /**
     * 全国一括登録フラグ
     */
    private boolean allFlg = false;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    /**
     * コンストラクタ<br/>
     */
    public OfficeZipCodeUpdateBatch(Environment environment) {
        this.dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        this.officeZipCodeDao = ApplicationContextUtility.getBean(OfficeZipCodeDao.class);
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
     * @param contribution
     * @param chunkContext
     * @return
     * @throws Exception
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // コンテキスト取得
        ExecutionContext executionContext =
                        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
        // 処理変数初期化
        int totalCount = 0;
        int count = 0;
        String msg = null;

        // バッチのジョッブ情報取得
        JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobParameters();
        String administratorId = jobParameters.getString("administratorId");
        String batchRunType = jobParameters.getString("batchRunType");
        String shopSeq = jobParameters.getString("shopSeq");
        if (!StringUtils.isEmpty(shopSeq)) {
            super.setShopSeq(Integer.valueOf(shopSeq));
        }

        // 処理対象ファイル
        String filePath;

        // 処理対象年
        String yy;

        // 処理対象月
        String mm;

        // 処理対象日
        String dd;

        // 初回登録
        if (officeZipCodeDao.getFirstEntity() == null) {
            // 全データ登録フラグ設定
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
            String registData = "jigyosyo_" + yy + mm + dd + ".csv";

            // ダウンロードファイル名
            String zipFile = "jigyosyo.zip";

            // 処理対象ファイル
            filePath = preProcessUtil.zipCodeRegistPreProcess(registData, zipFile, downloadDirectory, DL_URL);

        } else {
            // 全データ登録フラグ設定
            allFlg = false;

            // 更新対象年月の設定
            LocalDate localDate = LocalDate.now().minusMonths(1);

            // 処理対象年
            yy = preProcessUtil.getYearString(localDate);

            // 処理対象月
            mm = preProcessUtil.getMonthString(localDate);

            // 更新データ
            String updateData = "jupdate" + yy + mm;

            // ダウンロードファイル(新規追加)名
            String addZipFile = "jadd" + yy + mm + ".zip";

            // ダウンロードファイル(廃止)名
            String delZipFile = "jdel" + yy + mm + ".zip";

            // 処理対象ファイル
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

            // 事業所の個別郵便番号CSVDtoを取得
            List<OfficeZipCodeCsvDto> officeZipCodeCsvDtoList;
            CsvReaderUtil csvReaderUtil = new CsvReaderUtil();

            // CSV読み込みオプションを設定する
            CsvReaderOptionDto csvReaderOptionDto = new CsvReaderOptionDto();
            csvReaderOptionDto.setValidLimit(CsvUploadResult.CSV_UPLOAD_VALID_LIMIT);
            csvReaderOptionDto.setInputHeader(false);

            officeZipCodeCsvDtoList = (List<OfficeZipCodeCsvDto>) csvReaderUtil.readCsv(file, OfficeZipCodeCsvDto.class,
                                                                                        csvUploadResult,
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
                                                    new BatchExitMessageDto(String.valueOf(0),
                                                                            "引数 yy(年)、mm(月) が不正です。"
                                                    )));
                    throw new Exception("引数 yy(年)、mm(月) が不正です。");
                }
            }

            // ファイルが取得できた場合はtotalCountが「0」以下になることはない想定
            totalCount = officeZipCodeCsvDtoList.size();

            // CSVファイルにデータが存在しなかった場合
            if (totalCount == 0) {
                throw new NoDataException();
            }

            for (OfficeZipCodeCsvDto csvDto : officeZipCodeCsvDtoList) {
                count++;

                // 登録/更新処理
                insertOrUpdate(csvDto);

                if ((count % 50) == 0) {
                    // 進捗状況をログに出力する
                    LOGGER.info(String.format("%1$,d / %2$,d 件完了", count, totalCount));
                }
            }

            // 全データ登録完了
            if (allFlg) {
                msg = "事業所郵便番号の全最新データ登録が完了しました。";
            } else {
                msg = yy + " 年 " + mm + " 月の事業所郵便番号更新の反映が完了しました。";
            }

            report(msg);
            sendAdministratorMail(msg, true);

            // 正常終了時にはバッチが自動的にコミットを行うので、明示的にコミットを行っていない。
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(String.valueOf(count),
                                                                    this.getReportString().toString()
                                            )));
            return RepeatStatus.FINISHED;

        } catch (IOException e) {
            // CSVファイルの桁数、型がテーブルと一致しない場合に発生
            if (allFlg) {
                msg = "事業所郵便番号の全最新データ登録を行おうとしましたが、" + count + "行目に不正なデータが含まれていたため処理は無効になりました。";
            } else {
                msg = yy + " 年 " + mm + " 月の事業所郵便番号の更新を行おうとしましたが、" + count + " 行目に不正なデータが含まれていたため処理は無効になりました。";
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
                msg = "事業所郵便番号の全最新データ登録を行おうとしましたが、対象のファイルにデータが存在しないため、処理は無効になりました。\n";
                msg += "対象のファイル、ダウンロードサイトをご確認ください。\n";
                msg += "(引数 : all=[" + allFlg + "])";
            } else {
                msg = yy + " 年 " + mm + " 月の事業所郵便番号の更新を行おうとしましたが、対象のファイルにデータが存在しないため、処理は無効になりました。\n";
                msg += "対象のファイル、ダウンロードサイトをご確認ください。\n";
                msg += "(引数 : yy=[" + yy + "] mm=[" + mm + "] all=[" + allFlg + "])";
            }

            LOGGER.error(e.getClass().toString(), e);
            LOGGER.error(msg);
            report(msg);
            sendAdministratorMail(msg, false);
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(String.valueOf(0),
                                                                    this.getReportString().toString()
                                            )));
            throw e;
        } catch (Exception e) {
            if (allFlg) {
                msg = "事業所郵便番号の全最新データ登録を行おうとしましたが、処理中に " + e.getClass().getName() + " が発生したため処理は無効になりました。";
                msg += "(引数 : all=[" + allFlg + "])";
            } else {
                msg = "事業所郵便番号の月次更新を行おうとしましたが、処理中に " + e.getClass().getName() + " が発生したため処理は無効になりました。";
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
                                            new BatchExitMessageDto(String.valueOf(count),
                                                                    this.getReportString().toString()
                                            )));
            throw e;
        }
    }

    /**
     * 事業所郵便番号データを登録／更新する<br/>
     * 事業所郵便番号マスタにデータが存在しない場合は登録処理を<br/>
     * データが存在する場合は更新処理を行う。<br/>
     *
     * @param csvDto 事業所郵便番号データ
     */
    protected void insertOrUpdate(OfficeZipCodeCsvDto csvDto) {
        // プライマリーキーでマスタを検索
        // 存在しなければ新規登録、存在すれば更新処理を行う。
        OfficeZipCodeEntity entity = convertToEntity(csvDto);
        String officeCode = entity.getOfficeCode();
        String officeKana = entity.getOfficeKana();
        String officeName = entity.getOfficeName();
        String prefectureName = entity.getPrefectureName();
        String cityName = entity.getCityName();
        String townName = entity.getTownName();
        String numbers = entity.getNumbers();
        String zipCode = entity.getZipCode();
        String oldZipCode = entity.getOldZipCode();
        OfficeZipCodeEntity searchResult =
                        officeZipCodeDao.getEntity(officeCode, officeKana, officeName, prefectureName, cityName,
                                                   townName, numbers, zipCode, oldZipCode
                                                  );

        // 現在日時
        Timestamp currentTime = dateUtility.getCurrentTime();

        if (searchResult == null) {
            // ＤＢ上に存在しない住所なので登録
            entity.setRegistTime(currentTime);
            entity.setUpdateTime(currentTime);
            officeZipCodeDao.insert(entity);
        } else {
            // ＤＢ上に存在する住所なので更新
            entity.setRegistTime(searchResult.getRegistTime());
            entity.setUpdateTime(currentTime);
            officeZipCodeDao.update(entity);
        }
    }

    /**
     * 事業所郵便番号データを事業所郵便番号Entityに変換<br/>
     *
     * @param csvDto 事業所郵便番号データ
     * @return 事業所郵便番号Entity
     */
    protected OfficeZipCodeEntity convertToEntity(OfficeZipCodeCsvDto csvDto) {
        OfficeZipCodeEntity entity = ApplicationContextUtility.getBean(OfficeZipCodeEntity.class);
        entity.setOfficeCode(csvDto.getOfficeCode());
        entity.setOfficeKana(csvDto.getOfficeKana());
        entity.setOfficeName(csvDto.getOfficeName());
        entity.setPrefectureName(csvDto.getPrefectureName());
        entity.setCityName(csvDto.getCityName());
        entity.setTownName(csvDto.getTownName());
        entity.setNumbers(csvDto.getNumbers());
        entity.setZipCode(csvDto.getZipCode());
        entity.setOldZipCode(csvDto.getOldZipCode());
        entity.setHandlingBranchName(csvDto.getHandlingBranchName());
        entity.setIndividualType(csvDto.getIndividualType());
        entity.setAnyZipFlag(csvDto.getAnyZipFlag());
        entity.setUpdateCode(csvDto.getUpdateCode());
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
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_OFFICE_ZIP_CODE.getLabel());
            valueMap.put("RESULT", msg);

            if (result) {
                mailContentsMap.put("admin", valueMap);
                mailDto.setMailTemplateType(HTypeMailTemplateType.ZIPCODE_ADMINISTRATOR_MAIL);
            } else {
                mailContentsMap.put("error", valueMap);
                mailDto.setMailTemplateType(HTypeMailTemplateType.ZIPCODE_ADMINISTRATOR_ERROR_MAIL);
            }

            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_OFFICE_ZIP_CODE.getLabel() + "報告");
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);

            LOGGER.info("管理者へ通知メールを送信しました。");
        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);
        }
    }
}
