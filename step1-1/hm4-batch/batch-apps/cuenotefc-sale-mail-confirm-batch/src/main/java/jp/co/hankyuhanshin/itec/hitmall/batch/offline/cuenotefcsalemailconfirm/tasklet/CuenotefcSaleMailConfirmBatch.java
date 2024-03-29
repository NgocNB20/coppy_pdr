/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.cuenotefcsalemailconfirm.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.constant.api.cuenote.CuenoteAPIConstant;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailDeliveryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.dao.saleannounce.SaleAnnounceMailDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiGetDeliveryResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.saleannounce.SaleAnnounceMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiAddressImportLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiDeliveryReserveLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiGetDeliveryLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.mail.SendAdminMailLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.NoMailRequiredMemberInfoLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.loginadvisability.LoginAdvisabilityGetCanNotLoginMemberLogic;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.env.Environment;

import java.net.SocketTimeoutException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// 2023-renew No41 from here

/**
 * お気に入りセール通知メール配信確認バッチ
 *
 * @author takashima
 */
public class CuenotefcSaleMailConfirmBatch extends AbstractBatch {

    /**
     * Logger
     */
    private final Logger LOGGER = LoggerFactory.getLogger(CuenotefcSaleMailConfirmBatch.class);

    /**
     * セールお知らせメールDao
     */
    protected final SaleAnnounceMailDao saleAnnounceMailDao;

    /**
     * Cuenote API アドレス帳インポートAPI logic
     */
    protected CuenoteApiAddressImportLogic cuenoteApiAddressImportLogic;

    /**
     * Cuenote API 配信情報予約API logic
     */
    protected CuenoteApiDeliveryReserveLogic cuenoteApiDeliveryReserveLogic;

    /**
     * ログイン不可の会員情報取得ロジック
     */
    protected LoginAdvisabilityGetCanNotLoginMemberLogic loginAdvisabilityGetCanNotLoginMemberLogic;

    /**
     * メール不要の会員情報取得ロジック
     */
    protected NoMailRequiredMemberInfoLogic noMailRequiredMemberInfoLogic;

    /**
     * 管理者メール送信 logic
     */
    protected SendAdminMailLogic sendAdminMailLogic;

    /**
     * Cuenote API 配信情報取得API logic
     */
    private final CuenoteApiGetDeliveryLogic cuenoteApiGetDeliveryLogic;

    /**
     * 日付関連Helper取得
     */
    protected final DateUtility dateUtility;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    /**
     * タイムアウトエラーメッセージ
     */
    public final static String API_ERROR_TIMEOUT_MESSAGE = "/API通信でタイムアウトが発生しました。";

    /**
     * タイムアウトエラーリカバリメッセージ
     */
    public final static String API_ERROR_TIMEOUT_RECOVERY_MESSAGE = "【リカバリ】時間を空けて再実行";

    /**
     * APIエラーメッセージ
     */
    public final static String API_ERROR_MESSAGE = "/API通信でAPIエラーが発生しました。";

    /**
     * APIエラーリカバリメッセージ
     */
    public final static String API_ERROR_RECOVERY_MESSAGE = "【リカバリ】システム管理者へお問合せください。";

    /**
     * 処理名
     */
    private final static String PROCESS_NAME_DELIVERY_CONFIRM = "お気に入りセール通知メール配信確認";

    /**
     * 処理結果メール詳細メッセージ
     */
    public String mailMessageDetail;

    /**
     * バッチ処理総件数
     */
    protected int batchProcessTotalCount = 0;

    /**
     * コンストラクタ
     */
    public CuenotefcSaleMailConfirmBatch(Environment environment) {
        this.exitMessageUtil = new BatchExitMessageUtil();
        this.dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        this.saleAnnounceMailDao = ApplicationContextUtility.getBean(SaleAnnounceMailDao.class);
        this.noMailRequiredMemberInfoLogic = ApplicationContextUtility.getBean(NoMailRequiredMemberInfoLogic.class);
        this.loginAdvisabilityGetCanNotLoginMemberLogic =
                        ApplicationContextUtility.getBean(LoginAdvisabilityGetCanNotLoginMemberLogic.class);
        this.cuenoteApiAddressImportLogic = ApplicationContextUtility.getBean(CuenoteApiAddressImportLogic.class);
        this.sendAdminMailLogic = ApplicationContextUtility.getBean(SendAdminMailLogic.class);
        this.cuenoteApiDeliveryReserveLogic = ApplicationContextUtility.getBean(CuenoteApiDeliveryReserveLogic.class);
        this.cuenoteApiGetDeliveryLogic = ApplicationContextUtility.getBean(CuenoteApiGetDeliveryLogic.class);
    }

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

        //更新対象を取得
        List<SaleAnnounceMailEntity> saleAnnounceMailEntityList =
                        saleAnnounceMailDao.getSaleAnnounceMailDeliveryedList();
        if (saleAnnounceMailEntityList.size() == 0) {
            LOGGER.info("配信中のメールが存在しませんでした。");

            CuenoteApiGetDeliveryResponseDto cuenoteApiGetDeliveryResponseDto = null;
            HTypeMailDeliveryStatus deliveryStatus = null;

            // 処理結果詳細生成
            // メールメッセージ初期化
            mailMessageDetail =
                            sendAdminMailLogic.createSuccessDeliveryConfirmMailDetail(cuenoteApiGetDeliveryResponseDto,
                                                                                      deliveryStatus
                                                                                     );

            // メール送信
            sendAdminMailLogic.execute(mailMessageDetail, PROCESS_NAME_DELIVERY_CONFIRM,
                                       HTypeMailTemplateType.CUENOTEFC_SALE_MAIL_CONFIRM, false
                                      );

            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(String.valueOf(batchProcessTotalCount),
                                                                    this.getReportString().toString()
                                            )));
            return RepeatStatus.FINISHED;
        }

        CuenoteApiGetDeliveryResponseDto cuenoteApiGetDeliveryResponseDto = new CuenoteApiGetDeliveryResponseDto();

        // 配信中の配信ＩＤは原則一件のみ
        String deliveryId = saleAnnounceMailEntityList.get(0).getDeliveryId();
        String exceptionName;
        try {
            // 配信情報取得API実行
            cuenoteApiGetDeliveryResponseDto = cuenoteApiGetDeliveryLogic.execute(deliveryId);

            // エラーの場合
            if (cuenoteApiGetDeliveryResponseDto == null) {
                LOGGER.error("APIエラー");
                Timestamp currentTime = dateUtility.getCurrentTime();
                for (SaleAnnounceMailEntity saleAnnounceMailEntity : saleAnnounceMailEntityList) {
                    saleAnnounceMailEntity.setDeliveryStatus(HTypeMailDeliveryStatus.SYSTEM_ERROR);
                    saleAnnounceMailEntity.setUpdateTime(currentTime);
                    saleAnnounceMailDao.update(saleAnnounceMailEntity);
                }
                throw new Exception();
            }

        } catch (SocketTimeoutException | ConnectTimeoutException e) {
            LOGGER.error("タイムアウト例外処理が発生しました", e);

            exceptionName = "配信情報取得" + API_ERROR_TIMEOUT_MESSAGE;
            mailMessageDetail = sendAdminMailLogic.createFailedMailDetail(exceptionName, PROCESS_NAME_DELIVERY_CONFIRM,
                                                                          API_ERROR_TIMEOUT_RECOVERY_MESSAGE
                                                                         );
            // メール送信
            sendAdminMailLogic.execute(mailMessageDetail, PROCESS_NAME_DELIVERY_CONFIRM,
                                       HTypeMailTemplateType.CUENOTEFC_SALE_ERROR_MAIL_CONFIRM, true
                                      );
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(String.valueOf(batchProcessTotalCount),
                                                                    this.getReportString().toString()
                                            )));
            return RepeatStatus.FINISHED;

        } catch (Exception e) {
            LOGGER.error("例外処理が発生しました", e);

            exceptionName = "配信情報取得" + API_ERROR_MESSAGE;
            mailMessageDetail = sendAdminMailLogic.createFailedMailDetail(exceptionName, PROCESS_NAME_DELIVERY_CONFIRM,
                                                                          API_ERROR_RECOVERY_MESSAGE
                                                                         );

            // メール送信
            sendAdminMailLogic.execute(mailMessageDetail, PROCESS_NAME_DELIVERY_CONFIRM,
                                       HTypeMailTemplateType.CUENOTEFC_SALE_ERROR_MAIL_CONFIRM, true
                                      );

            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(String.valueOf(batchProcessTotalCount),
                                                                    this.getReportString().toString()
                                            )));
            return RepeatStatus.FINISHED;
        }
        HTypeMailDeliveryStatus deliveryStatus =
                        deliveryStatusReflection(cuenoteApiGetDeliveryResponseDto, saleAnnounceMailEntityList);

        // 処理結果詳細生成
        // メールメッセージ初期化
        mailMessageDetail = sendAdminMailLogic.createSuccessDeliveryConfirmMailDetail(cuenoteApiGetDeliveryResponseDto,
                                                                                      deliveryStatus
                                                                                     );
        // メール送信
        sendAdminMailLogic.execute(mailMessageDetail, PROCESS_NAME_DELIVERY_CONFIRM,
                                   HTypeMailTemplateType.CUENOTEFC_SALE_MAIL_CONFIRM, false
                                  );

        executionContext.put(BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                        new BatchExitMessageDto(String.valueOf(batchProcessTotalCount),
                                                this.getReportString().toString()
                        )));
        return RepeatStatus.FINISHED;
    }

    /**
     * 配信状況反映
     *
     * @param cuenoteApiGetDeliveryResponseDto 配信情報取得APIレスポンスDTO
     * @param saleAnnounceMailEntityList セールお知らせメール更新対象
     * @return 配信状況
     */
    protected HTypeMailDeliveryStatus deliveryStatusReflection(CuenoteApiGetDeliveryResponseDto cuenoteApiGetDeliveryResponseDto,
                                                               List<SaleAnnounceMailEntity> saleAnnounceMailEntityList) {
        // 配信ステータスのチェック
        HTypeMailDeliveryStatus deliveryStatus = null;
        Timestamp currentTime = dateUtility.getCurrentTime();
        // ステータスが配信中かをチェック
        switch (cuenoteApiGetDeliveryResponseDto.getMtaStatus()) {
            case CuenoteAPIConstant.MTA_STATUS_WAIT:
            case CuenoteAPIConstant.MTA_STATUS_PREPARE:
            case CuenoteAPIConstant.MTA_STATUS_PREPARING:
            case CuenoteAPIConstant.MTA_STATUS_DELIVERING:
                deliveryStatus = HTypeMailDeliveryStatus.DELIVERING;
                break;
        }

        // 失敗件数があればステータスを配信失敗とする。
        if (deliveryStatus == null) {
            if (cuenoteApiGetDeliveryResponseDto.getStatFailure() + cuenoteApiGetDeliveryResponseDto.getStatDeferral()
                == 0) {
                deliveryStatus = HTypeMailDeliveryStatus.DELIVERED;
            } else {
                deliveryStatus = HTypeMailDeliveryStatus.FAILED;
            }
        }

        Timestamp deliveryEndTime = null;
        if (HTypeMailDeliveryStatus.DELIVERED.equals(deliveryStatus)) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            OffsetDateTime offsetDateTime =
                            OffsetDateTime.parse(cuenoteApiGetDeliveryResponseDto.deliveryEndTime, formatter);
            deliveryEndTime = Timestamp.from(offsetDateTime.toInstant());
        }

        // セールお知らせメールを更新
        for (SaleAnnounceMailEntity saleAnnounceMailEntity : saleAnnounceMailEntityList) {
            saleAnnounceMailEntity.setDeliveryStatus(deliveryStatus);
            if (HTypeMailDeliveryStatus.DELIVERED.equals(deliveryStatus)) {
                saleAnnounceMailEntity.setDeliveryTime(deliveryEndTime);
            }
            saleAnnounceMailEntity.setUpdateTime(currentTime);
            saleAnnounceMailDao.update(saleAnnounceMailEntity);
        }

        return deliveryStatus;
    }
}
// 2023-renew No41 to here
