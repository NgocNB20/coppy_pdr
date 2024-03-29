/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.online.goods.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.CommonInfo;
import jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.CommonInfoAdministrator;
import jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.impl.CommonInfoBatchAdministratorImpl;
import jp.co.hankyuhanshin.itec.hitmall.batch.application.commoninfo.impl.CommonInfoImpl;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.batch.utility.CommonInfoBatchUtility;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUploadType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.administrator.AdminLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.goods.GoodsCsvUpLoadAsynchronousService;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadError;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvUploadResult;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.CsvValidationResult;
import jp.co.hankyuhanshin.itec.hmbase.util.csvupload.InvalidDetail;
import jp.co.hankyuhanshin.itec.hmbase.util.mail.InstantMailSetting;
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
import org.springframework.core.env.Environment;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品登録非同期バッチ
 * 作成日：2021/03/23
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public class GoodsAsynchronousBatch extends AbstractBatch {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsAsynchronousBatch.class);

    /**
     * 管理者用メール送信設定
     */
    private final InstantMailSetting mailSetting;

    /**
     * 改行(キャリッジリターン)
     */
    private static final String LINE_FEED = "\r\n";

    /**
     * タブ
     */
    private static final String TAB = "\t";

    /**
     * 商品一括アップロードサービス
     */
    private final GoodsCsvUpLoadAsynchronousService goodsCsvUpLoadAsynchronousService;

    /**
     * 管理者情報取得サービス
     */
    private final AdminLogic adminLogic;

    /**
     * メール送信サービス
     */
    private final MailSendService mailSendService;

    /**
     * 管理者情報
     */
    private AdministratorEntity administratorEntity;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    /**
     * コンストラクタ
     */
    public GoodsAsynchronousBatch(Environment environment) {
        this.goodsCsvUpLoadAsynchronousService =
                        ApplicationContextUtility.getBean(GoodsCsvUpLoadAsynchronousService.class);
        this.adminLogic = ApplicationContextUtility.getBean(AdminLogic.class);
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.exitMessageUtil = new BatchExitMessageUtil();
        this.mailSetting = new InstantMailSetting(environment.getProperty("mail.setting.goodsimage.upload.smtp.server"),
                                                  environment.getProperty("mail.setting.goodsimage.upload.mail.from"),
                                                  null, null, Collections.singletonList(
                        environment.getProperty("mail.setting.goodsimage.upload.mail.receivers"))
        );
    }

    /**
     * 商品一括アップロードの処理を実行します
     *
     * @param contribution StepContribution
     * @param chunkContext ChunkContext
     * @return RepeatStatus
     * @throws Exception Exception
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
        String targetFilePath = jobParameters.getString("targetFilePath");
        String uploadType = jobParameters.getString("uploadType");
        if (!StringUtils.isEmpty(shopSeq)) {
            super.setShopSeq(Integer.valueOf(shopSeq));
        }

        // 管理者情報を取得
        administratorEntity = adminLogic.getAdministrator(Integer.parseInt(shopSeq), administratorId);

        CsvUploadResult uploadResult;
        boolean isCsvUploadError = false;
        try {

            // サービスを呼び商品登録を実行する
            uploadResult = callService(targetFilePath, uploadType, HTypeSiteType.BACK);

            // 処理完了メール送信
            sendAdministratorMail(uploadResult);

            isCsvUploadError = uploadResult.isCsvUploadError();
            if (isCsvUploadError) {
                // ロールバックさせるために例外をthrowする
                throw new Exception("バリデーションチェックまたはデータチェックでエラーが発生しました。");
            }

            report("エラーなし正常終了");
            LOGGER.info("コミットします。");

        } catch (Exception error) {

            if (isCsvUploadError) {
                // バリデーションチェックまたはデータチェックでエラーが発生した場合
                report("エラー有り終了\r\n詳細はメールにてご確認ください");
            } else {
                // 予期せぬ事態で処理が中断した場合
                LOGGER.error("例外処理が発生しました", error);
                // エラーメール送信
                sendAdministratorErrorMail(error.getClass().getName());
                report("例外発生異常終了");
            }

            LOGGER.info("ロールバックします。");
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
     * アップロードサービスを呼び出します。<br/>
     *
     * @param targetFilePath
     * @param uploadType
     * @param siteType       サイト種別
     * @return Csvアップロード結果
     */
    public CsvUploadResult callService(String targetFilePath, String uploadType, HTypeSiteType siteType) {
        // 商品一括登録処理実行
        CommonInfoBatchUtility commonInfoBatchUtility = ApplicationContextUtility.getBean(CommonInfoBatchUtility.class);
        return goodsCsvUpLoadAsynchronousService.execute(
                        new File(targetFilePath), EnumTypeUtil.getEnumFromValue(HTypeUploadType.class, uploadType),
                        commonInfoBatchUtility.getAdministratorName(getCommonInfo()), siteType
                                                        );
    }

    /**
     * 管理者向けメールを送信する<br/>
     *
     * @param uploadResult アップロード結果
     * @return true:成功、false:失敗
     */
    public boolean sendAdministratorMail(CsvUploadResult uploadResult) {

        if (uploadResult == null) {
            throw new NullPointerException();
        }

        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);
            Map<String, Object> mailContentsMap = new HashMap<>();

            mailDto.setMailTemplateType(HTypeMailTemplateType.GOODS_ASYNCHRONOUS_MAIL);

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            // メール本文作成用マップ
            final Map<String, String> valueMap = new HashMap<>();
            valueMap.put("SYSTEM", systemName);
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_GOODS_ASYNCHRONOUS.getLabel());
            valueMap.put("SHOP_SEQ", this.getShopSeq().toString());

            // メール本文の内容作成
            addMailMessage(valueMap, uploadResult);

            mailContentsMap.put("admin", valueMap);
            mailDto.setMailContentsMap(mailContentsMap);

            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_GOODS_ASYNCHRONOUS.getLabel() + "報告");

            this.mailSendService.execute(mailDto);

            LOGGER.info("管理者へ通知メールを送信しました。");

            return true;

        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);
            return false;
        }
    }

    /**
     * メール本文を作成する
     *
     * @param valueMap     メール本文作成用マップ
     * @param uploadResult CSVアップロード結果
     */
    public void addMailMessage(Map<String, String> valueMap, CsvUploadResult uploadResult) {
        // csvデータ行数(ヘッダー行を除く)
        int totalCount = uploadResult.getRecordCount() - 1;

        // エラー件数
        int errorCount = countCsvUploadError(uploadResult);

        // エラーメッセージ整形ビルダー
        StringBuilder errorMessages = new StringBuilder();

        // CSVアップロードにエラーが合った場合、メール本文にエラーメッセージを付与
        if (uploadResult.isCsvUploadError()) {

            // バリデーションチェックエラーがある場合
            if (uploadResult.isInValid()) {
                CsvValidationResult csvValidationResult = uploadResult.getCsvValidationResult();
                for (InvalidDetail detail : csvValidationResult.getInvalidDetailList()) {
                    errorMessages.append(createMessage(detail.getRow(), detail.getColumnLabel(), detail.getMessage()));
                }
            }

            // データチェックエラーがある場合
            if (uploadResult.isError()) {
                for (CsvUploadError csvUploadError : uploadResult.getCsvUploadErrorlList()) {
                    errorMessages.append(createMessage(csvUploadError.getRow(), null, csvUploadError.getMessage()));
                }
            }
        }

        // 総処理件数をメール本文に追加
        valueMap.put("CNT_GOODSGROUP_SUM", Integer.toString(totalCount));
        // 成功件数をメール本文に追加
        valueMap.put("CNT_SUCCESS_SUM", Integer.toString(uploadResult.mergeRowCount));
        // 失敗件数をメール本文に追加
        valueMap.put("CNT_ERROR_SUM", Integer.toString(errorCount));

        // エラー結果
        valueMap.put("LIST", errorMessages.toString());
    }

    /**
     * CSVアップロードのエラー件数をカウントする
     *
     * @param uploadResult CSVアップロード結果
     * @return errorCount エラー件数
     */
    public int countCsvUploadError(CsvUploadResult uploadResult) {
        int errorCount = 0;
        // アップロードCSVがバリデーションチェックか相関チェックでエラー判定を受けていた場合
        // バリデーションチェックエラーの場合
        if (uploadResult.isInValid()) {
            // バリデーションチェックエラーが起きた行数をカウント
            errorCount = uploadResult.getCsvValidationResult().getInvalidDetailList().size();
            // 相関チェックエラーの場合
        } else if (uploadResult.isError()) {
            // 相関チェックエラーの数をカウント
            errorCount = getErrorMap(uploadResult.getCsvUploadErrorlList()).size();
        }
        return errorCount;
    }

    /**
     * エラーの行数のmapを返す。<br/>
     * ※失敗件数カウント用<br/>
     *
     * @param errorList エラー詳細リスト
     * @return Map エラー行数マップ
     */
    private Map<Integer, Integer> getErrorMap(List<CsvUploadError> errorList) {
        if (errorList.size() == 0) {
            return new HashMap<>();
        }

        Map<Integer, Integer> returnMap = new HashMap<>();
        for (CsvUploadError csvUploadError : errorList) {
            returnMap.put(csvUploadError.getRow(), csvUploadError.getRow());
        }
        return returnMap;
    }

    /**
     * 管理者向けエラーメールを送信する
     *
     * @param errorResultMsg エラー結果
     * @return 送信成功:true、送信失敗：false
     */
    public boolean sendAdministratorErrorMail(final String errorResultMsg) {

        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            final Map<String, String> valueMap = new HashMap<>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            // メール本文作成用マップ
            valueMap.put("SYSTEM", systemName);
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_GOODS_ASYNCHRONOUS.getLabel());
            valueMap.put("SHOP_SEQ", this.getShopSeq().toString());

            // エラーメール本文の内容作成
            final Map<String, String> includeMailMessageMap = addErrorMailMessage(valueMap, errorResultMsg);

            mailContentsMap.put("error", includeMailMessageMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.GOODS_ASYNCHRONOUS_ERROR_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_GOODS_ASYNCHRONOUS.getLabel() + "報告");
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
     * エラーメール本文を作成する
     *
     * @param valueMap       メール本文作成用マップ
     * @param errorResultMsg エラー結果
     * @return valueMap メール本文作成用マップ
     */
    public Map<String, String> addErrorMailMessage(Map<String, String> valueMap, final String errorResultMsg) {
        String resultMsg = "処理中に" + errorResultMsg + "が発生しました。" + LINE_FEED + LINE_FEED;
        valueMap.put("RESULT", resultMsg);
        if (LOGGER.isDebugEnabled()) {
            valueMap.put("DEBUG", "1");
        } else {
            valueMap.put("DEBUG", "0");
        }

        return valueMap;
    }

    /**
     * メール本文のメッセージを生成<br/>
     *
     * @param row        行番号
     * @param columnName カラム名
     * @param message    内容
     * @return Item
     */
    public String createMessage(Integer row, String columnName, String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(row).append("行目").append(TAB);
        if (StringUtil.isNotEmpty(columnName)) {
            sb.append("項目名:").append(columnName).append(TAB);
        }
        sb.append(replaceLessGreaterThan(message)).append(LINE_FEED);
        return sb.toString();
    }

    /**
     * &lt;&gt;を置換しメッセージを返す<br/>
     *
     * @param message メッセージ
     * @return 置換メッセージ
     */
    public String replaceLessGreaterThan(String message) {

        if (StringUtil.isEmpty(message)) {
            return message;
        }

        return message.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
    }

    /**
     * 共通情報の取得
     *
     * @return CommonInfo
     */
    protected CommonInfo getCommonInfo() {
        // 共通情報の作成
        CommonInfo commonInfo = ApplicationContextUtility.getBeanByName("commonInfoBatch", CommonInfo.class);

        CommonInfoImpl commonInfoImpl = new CommonInfoImpl() {
            /** シリアルID */
            private static final long serialVersionUID = 1L;

            @Override
            public CommonInfoAdministrator getCommonInfoAdministrator() {
                // Batch用ダミー管理者
                CommonInfoBatchAdministratorImpl adminImpl = new CommonInfoBatchAdministratorImpl();

                if (administratorEntity != null) {
                    // 管理者名をバッチ登録者名に変更
                    adminImpl.setAdministratorSeq(administratorEntity.getAdministratorSeq());
                    adminImpl.setAdministratorId(administratorEntity.getAdministratorId());
                    adminImpl.setAdministratorLastName(administratorEntity.getAdministratorLastName());
                    adminImpl.setAdministratorFirstName(administratorEntity.getAdministratorFirstName());
                } else {
                    adminImpl.setAdministratorSeq(commonInfo.getCommonInfoAdministrator().getAdministratorSeq());
                    adminImpl.setAdministratorId(commonInfo.getCommonInfoAdministrator().getAdministratorId());
                    adminImpl.setAdministratorLastName(
                                    commonInfo.getCommonInfoAdministrator().getAdministratorLastName());
                    adminImpl.setAdministratorFirstName(
                                    commonInfo.getCommonInfoAdministrator().getAdministratorFirstName());
                }

                return adminImpl;
            }
        };
        commonInfoImpl.setCommonInfoBase(commonInfo.getCommonInfoBase());
        commonInfoImpl.setCommonInfoShop(commonInfo.getCommonInfoShop());

        return commonInfoImpl;
    }
}
