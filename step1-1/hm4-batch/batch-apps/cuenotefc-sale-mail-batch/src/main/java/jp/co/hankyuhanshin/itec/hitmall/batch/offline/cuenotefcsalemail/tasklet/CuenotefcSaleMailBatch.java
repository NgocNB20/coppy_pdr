/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.cuenotefcsalemail.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailDeliveryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.dao.saleannounce.SaleAnnounceMailDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiAddressImportRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiAddressImportResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiDeliveryReserveRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.cuenote.api.CuenoteApiMailSetRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.saleannounce.SaleAnnounceAddImportListDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.saleannounce.SaleAnnounceMailListResultDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.saleannounce.SaleAnnounceMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiAddressImportLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiDeliveryReserveLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.cuenote.CuenoteApiMailSetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.mail.SendAdminMailLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.NoMailRequiredMemberInfoLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.loginadvisability.LoginAdvisabilityGetCanNotLoginMemberLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.saleannounce.SaleAddImportListGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.saleannounce.SaleDeliveryStatusGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.seasar.StringUtil;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.SocketTimeoutException;
import java.net.URI;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 2023-renew No41 from here

/**
 * お気に入りセール通知メール配信バッチ
 *
 * @author takashima
 */
@Component
public class CuenotefcSaleMailBatch extends AbstractBatch {

    /**
     * Logger
     */
    private final Logger LOGGER = LoggerFactory.getLogger(CuenotefcSaleMailBatch.class);

    protected static final GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);

    /**
     * セールお知らせメールDao
     */
    protected final SaleAnnounceMailDao saleAnnounceMailDao;

    /**
     * 配信内容取得サービス
     */
    private final SaleDeliveryStatusGetLogic saleDeliveryStatusGetLogic;

    /**
     * 配信情報取得ロジック
     */
    private final SaleAddImportListGetLogic saleAddImportListGetLogic;

    /**
     * Cuenote API アドレス帳インポートAPI logic
     */
    protected CuenoteApiAddressImportLogic cuenoteApiAddressImportLogic;

    /**
     * Cuenote API メール文書セット複製 logic
     */
    protected CuenoteApiMailSetLogic cuenoteApiMailSetLogic;

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
     * 日付関連Helper取得
     */
    protected final DateUtility dateUtility;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    /**
     * メール文書複製タイトル
     */
    public final static String CUENOTE_MAIL_SET_TITLE_HEAD = "お気に入りセール通知_";

    /**
     * 処理名(お気に入りセール通知メール配信バッチ)
     */
    public final static String PROCESS_NAME_DELIVERY_RESERVE = "お気に入りセール通知メール配信";

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
     * 処理結果メール詳細メッセージ
     */
    public String mailMessageDetail;

    /**
     * メール配信件数
     */
    Integer sendMailRequestCount = 0;

    /**
     * 配信予約件数
     */
    Integer sendMailMemberCount = 0;

    /**
     * バッチ処理総件数
     */
    protected int batchProcessTotalCount = 0;

    /**
     * 商品コード除外リスト
     */
    List<String> skipGoodsCodeList = new ArrayList<>();

    /**
     *  顧客番号除外リスト
     */
    List<Integer> skipCustomerNoList = new ArrayList<>();

    /**
     * エラー詳細リスト
     */
    List<String> errDetailsList = new ArrayList<>();

    /**
     * 配信情報予約API実行時間
     */
    @Value("${cuenote.api.goodsReceived.send.mail.time}")
    public String mailReserveTime;

    /**
     * コンストラクタ
     */
    public CuenotefcSaleMailBatch(Environment environment) {
        this.exitMessageUtil = new BatchExitMessageUtil();
        this.saleDeliveryStatusGetLogic = ApplicationContextUtility.getBean(SaleDeliveryStatusGetLogic.class);
        this.saleAddImportListGetLogic = ApplicationContextUtility.getBean(SaleAddImportListGetLogic.class);
        this.dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        this.saleAnnounceMailDao = ApplicationContextUtility.getBean(SaleAnnounceMailDao.class);
        this.noMailRequiredMemberInfoLogic = ApplicationContextUtility.getBean(NoMailRequiredMemberInfoLogic.class);
        this.loginAdvisabilityGetCanNotLoginMemberLogic =
                        ApplicationContextUtility.getBean(LoginAdvisabilityGetCanNotLoginMemberLogic.class);
        this.cuenoteApiAddressImportLogic = ApplicationContextUtility.getBean(CuenoteApiAddressImportLogic.class);
        this.sendAdminMailLogic = ApplicationContextUtility.getBean(SendAdminMailLogic.class);
        this.cuenoteApiDeliveryReserveLogic = ApplicationContextUtility.getBean(CuenoteApiDeliveryReserveLogic.class);
        this.cuenoteApiMailSetLogic = ApplicationContextUtility.getBean(CuenoteApiMailSetLogic.class);

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

        try {

            // 更新対象(セールお知らせメールTBL)
            List<SaleAnnounceMailEntity> saleAnnounceMailEntityList = new ArrayList<>();
            List<String> keyList = new ArrayList<>();

            // メール配信情報取得
            // セールお知らせメールTBL.deliverystatus=1（配信中）のものが1件でも存在する場合、処理終了
            List<SaleAnnounceMailListResultDto> tmpsaleAnnounceMailEntityList = saleDeliveryStatusGetLogic.execute();
            for (SaleAnnounceMailListResultDto saleAnnounceMailListResultDto : tmpsaleAnnounceMailEntityList) {
                if (HTypeMailDeliveryStatus.DELIVERING.equals(saleAnnounceMailListResultDto.getDeliveryStatus())) {
                    LOGGER.info("配信中のメールが存在する為、処理を終了しました。");

                    //管理者へ正常終了のメール送信

                    // 処理結果詳細生成
                    // メールメッセージ初期化
                    mailMessageDetail = sendAdminMailLogic.createSuccessDeliveryReserveMailDetail(sendMailRequestCount,
                                                                                                  sendMailMemberCount,
                                                                                                  skipGoodsCodeList,
                                                                                                  skipCustomerNoList,
                                                                                                  errDetailsList
                                                                                                 );
                    // メール送信
                    sendAdminMailLogic.execute(mailMessageDetail, PROCESS_NAME_DELIVERY_RESERVE,
                                               HTypeMailTemplateType.CUENOTEFC_SALE_MAIL, false
                                              );

                    executionContext.put(
                                    BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                                    new BatchExitMessageDto(String.valueOf(batchProcessTotalCount),
                                                                            this.getReportString().toString()
                                                    )));

                    return RepeatStatus.FINISHED;
                }

                SaleAnnounceMailEntity tmpSaleAnnounceMailEntity = new SaleAnnounceMailEntity();
                tmpSaleAnnounceMailEntity.setMemberInfoSeq(saleAnnounceMailListResultDto.getMemberInfoSeq());
                tmpSaleAnnounceMailEntity.setGoodsSeq(saleAnnounceMailListResultDto.getGoodsSeq());
                tmpSaleAnnounceMailEntity.setDeliveryId(saleAnnounceMailListResultDto.getDeliveryId());
                tmpSaleAnnounceMailEntity.setDeliveryStatus(saleAnnounceMailListResultDto.getDeliveryStatus());
                tmpSaleAnnounceMailEntity.setDeliveryTime(saleAnnounceMailListResultDto.getDeliveryTime());
                tmpSaleAnnounceMailEntity.setRegistTime(saleAnnounceMailListResultDto.getRegistTime());
                tmpSaleAnnounceMailEntity.setUpdateTime(saleAnnounceMailListResultDto.getUpdateTime());

                saleAnnounceMailEntityList.add(tmpSaleAnnounceMailEntity);
                keyList.add(saleAnnounceMailListResultDto.getKey());
            }
            List<SaleAnnounceAddImportListDto> adImportReqDtoList = new ArrayList<>();
            List<String> tmpKey = new ArrayList<>();

            int cnt = 0;
            for (String key : keyList) {
                tmpKey.add(key);
                if ((cnt > 0 && cnt % 20000 == 0) || cnt == keyList.size() - 1) {
                    // メール配信対象を取得
                    List<SaleAnnounceAddImportListDto> tmpAdImportReqDtoList =
                                    saleAddImportListGetLogic.execute(tmpKey);
                    adImportReqDtoList.addAll(tmpAdImportReqDtoList);
                    tmpKey = new ArrayList<>();
                }
                cnt++;
            }

            // cuenote配信予約
            cuenoteMailReserve(adImportReqDtoList, saleAnnounceMailEntityList);

        } catch (Exception error) {
            LOGGER.error("例外処理が発生しました", error);
            throw error;
        }
        executionContext.put(BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                        new BatchExitMessageDto(String.valueOf(batchProcessTotalCount),
                                                this.getReportString().toString()
                        )));
        return RepeatStatus.FINISHED;
    }

    /**
     * cuenote配信予約
     * @param adImportReqDtoList セールお知らせメールアドレス帳登録リスト
     * @param saleAnnounceMailEntityList セールお知らせメールEntityリスト
     */
    protected void cuenoteMailReserve(List<SaleAnnounceAddImportListDto> adImportReqDtoList,
                                      List<SaleAnnounceMailEntity> saleAnnounceMailEntityList) {

        Timestamp currentTime = dateUtility.getCurrentTime();
        List<SaleAnnounceMailEntity> saleAnnounceMailUpdateEntity = new ArrayList<>();

        // Cuenote API アドレス帳インポートAPIリクエストデータ作成
        List<CuenoteApiAddressImportRequestDto> cuenoteApiAddressImportRequestDtoList =
                        createCuenoteApiAddressImportRequest(adImportReqDtoList, currentTime,
                                                             saleAnnounceMailEntityList, saleAnnounceMailUpdateEntity
                                                            );

        if (cuenoteApiAddressImportRequestDtoList.size() > 0) {

            String exceptionName;
            try {
                // アドレス帳インポートAPI呼出
                CuenoteApiAddressImportResponseDto cuenoteApiAddressImportResponseDto =
                                cuenoteApiAddressImportLogic.execute(cuenoteApiAddressImportRequestDtoList,
                                                                     PropertiesUtil.getSystemPropertiesValue(
                                                                                     "cuenote.api.path.saleAddressImport")
                                                                    );
                // エラーの場合
                if (cuenoteApiAddressImportResponseDto == null) {
                    LOGGER.error("APIエラー");
                    throw new Exception();
                }
            } catch (SocketTimeoutException | ConnectTimeoutException e) {
                LOGGER.error("タイムアウト例外処理が発生しました", e);
                // セールお知らせメール更新
                systemErrorUpdate(saleAnnounceMailUpdateEntity);

                exceptionName = "アドレス帳レコードインポート" + API_ERROR_TIMEOUT_MESSAGE;
                mailMessageDetail =
                                sendAdminMailLogic.createFailedMailDetail(exceptionName, PROCESS_NAME_DELIVERY_RESERVE,
                                                                          API_ERROR_TIMEOUT_RECOVERY_MESSAGE
                                                                         );
                // メール送信
                sendAdminMailLogic.execute(mailMessageDetail, PROCESS_NAME_DELIVERY_RESERVE,
                                           HTypeMailTemplateType.CUENOTEFC_SALE_ERROR_MAIL, true
                                          );
                return;
            } catch (Exception e) {
                LOGGER.error("例外処理が発生しました", e);
                // セールお知らせメール更新
                systemErrorUpdate(saleAnnounceMailUpdateEntity);

                exceptionName = "アドレス帳レコードインポート" + API_ERROR_MESSAGE;
                mailMessageDetail =
                                sendAdminMailLogic.createFailedMailDetail(exceptionName, PROCESS_NAME_DELIVERY_RESERVE,
                                                                          API_ERROR_RECOVERY_MESSAGE
                                                                         );
                // メール送信
                sendAdminMailLogic.execute(mailMessageDetail, PROCESS_NAME_DELIVERY_RESERVE,
                                           HTypeMailTemplateType.CUENOTEFC_SALE_ERROR_MAIL, true
                                          );
                return;
            }

            // メール文書複製API呼出
            String mailSetLocation = null;
            try {
                mailSetLocation = cuenoteApiMailSetLogic.execute(createCuenoteApiMailSetRequest());
                // エラーの場合
                if (StringUtil.isEmpty(mailSetLocation)) {
                    LOGGER.error("APIエラー");
                    throw new Exception();
                }
            } catch (SocketTimeoutException | ConnectTimeoutException e) {
                LOGGER.error("タイムアウト例外処理が発生しました", e);
                // セールお知らせメール更新
                systemErrorUpdate(saleAnnounceMailUpdateEntity);

                exceptionName = "メール文書複製" + API_ERROR_TIMEOUT_MESSAGE;
                mailMessageDetail =
                                sendAdminMailLogic.createFailedMailDetail(exceptionName, PROCESS_NAME_DELIVERY_RESERVE,
                                                                          API_ERROR_TIMEOUT_RECOVERY_MESSAGE
                                                                         );
                // メール送信
                sendAdminMailLogic.execute(mailMessageDetail, PROCESS_NAME_DELIVERY_RESERVE,
                                           HTypeMailTemplateType.CUENOTEFC_SALE_ERROR_MAIL, true
                                          );
                return;
            } catch (Exception e) {
                LOGGER.error("例外処理が発生しました", e);
                // セールお知らせメール更新
                systemErrorUpdate(saleAnnounceMailUpdateEntity);

                exceptionName = "メール文書複製" + API_ERROR_MESSAGE;
                mailMessageDetail =
                                sendAdminMailLogic.createFailedMailDetail(exceptionName, PROCESS_NAME_DELIVERY_RESERVE,
                                                                          API_ERROR_RECOVERY_MESSAGE
                                                                         );

                // メール送信
                sendAdminMailLogic.execute(mailMessageDetail, PROCESS_NAME_DELIVERY_RESERVE,
                                           HTypeMailTemplateType.CUENOTEFC_SALE_ERROR_MAIL, true
                                          );
                return;
            }

            // 配信情報予約API呼出
            String deliveryReserveLocation = null;
            try {
                deliveryReserveLocation = cuenoteApiDeliveryReserveLogic.execute(
                                createCuenoteApiDeliveryReserveRequest(mailSetLocation));

                // エラーの場合
                if (StringUtil.isEmpty(deliveryReserveLocation)) {
                    LOGGER.error("APIエラー");
                    throw new Exception();
                }
            } catch (SocketTimeoutException | ConnectTimeoutException e) {
                LOGGER.error("タイムアウト例外処理が発生しました", e);
                // セールお知らせメール更新
                systemErrorUpdate(saleAnnounceMailUpdateEntity);

                exceptionName = "配信情報予約" + API_ERROR_TIMEOUT_MESSAGE;
                mailMessageDetail =
                                sendAdminMailLogic.createFailedMailDetail(exceptionName, PROCESS_NAME_DELIVERY_RESERVE,
                                                                          API_ERROR_TIMEOUT_RECOVERY_MESSAGE
                                                                         );
                // メール送信
                sendAdminMailLogic.execute(mailMessageDetail, PROCESS_NAME_DELIVERY_RESERVE,
                                           HTypeMailTemplateType.CUENOTEFC_SALE_ERROR_MAIL, true
                                          );
                return;
            } catch (Exception e) {
                LOGGER.error("例外処理が発生しました", e);
                // セールお知らせメール更新
                systemErrorUpdate(saleAnnounceMailUpdateEntity);

                exceptionName = "配信情報予約" + API_ERROR_MESSAGE;
                mailMessageDetail =
                                sendAdminMailLogic.createFailedMailDetail(exceptionName, PROCESS_NAME_DELIVERY_RESERVE,
                                                                          API_ERROR_RECOVERY_MESSAGE
                                                                         );

                // メール送信
                sendAdminMailLogic.execute(mailMessageDetail, PROCESS_NAME_DELIVERY_RESERVE,
                                           HTypeMailTemplateType.CUENOTEFC_SALE_ERROR_MAIL, true
                                          );
                return;
            }

            URI deliveryReserveUri = null;
            try {
                deliveryReserveUri = new URI(deliveryReserveLocation);
            } catch (Exception e) {
                // URIを利用する場合はtry,catchが必要な為囲む
                LOGGER.error("セールお知らせメール更新時に例外処理が発生しました", e);
                return;
            }
            String deliveryReservePath = deliveryReserveUri.getPath();
            String[] deliveryReserveParts = deliveryReservePath.split("/");
            String deliveryId = deliveryReserveParts[deliveryReserveParts.length - 1];

            // セールお知らせメール更新
            for (SaleAnnounceMailEntity saleAnnounceMailEntity : saleAnnounceMailUpdateEntity) {
                saleAnnounceMailEntity.setDeliveryId(deliveryId);
                saleAnnounceMailEntity.setDeliveryStatus(HTypeMailDeliveryStatus.DELIVERING);
                saleAnnounceMailEntity.setUpdateTime(currentTime);
                saleAnnounceMailDao.update(saleAnnounceMailEntity);
            }
        }

        // 処理結果詳細生成
        // メールメッセージ初期化
        mailMessageDetail = sendAdminMailLogic.createSuccessDeliveryReserveMailDetail(sendMailRequestCount,
                                                                                      sendMailMemberCount,
                                                                                      skipGoodsCodeList,
                                                                                      skipCustomerNoList, errDetailsList
                                                                                     );
        // メール送信
        sendAdminMailLogic.execute(mailMessageDetail, PROCESS_NAME_DELIVERY_RESERVE,
                                   HTypeMailTemplateType.CUENOTEFC_SALE_MAIL, false
                                  );
    }

    /**
     * アドレス帳レコードインポートリクエストデータ作成
     *
     * @param adImportReqDtoList セールお知らせメールアドレス帳登録Dtoリスト
     * @param currentTime 当日日付
     * @param saleAnnounceMailEntityList セールお知らせメールEntityリスト
     * @return Cuenote API アドレス帳インポートAPIリクエストDTOリスト
     */
    protected List<CuenoteApiAddressImportRequestDto> createCuenoteApiAddressImportRequest(List<SaleAnnounceAddImportListDto> adImportReqDtoList,
                                                                                           Timestamp currentTime,
                                                                                           List<SaleAnnounceMailEntity> saleAnnounceMailEntityList,
                                                                                           List<SaleAnnounceMailEntity> saleAnnounceMailUpdateEntity) {

        List<CuenoteApiAddressImportRequestDto> cuenoteApiAddressImportRequestDtoList = new ArrayList<>();
        // リクエストデータ作成
        ArrayList<String> goodsHtml = new ArrayList<>();

        // 会員情報リスト作成
        Set<Integer> uniqueCustomerNoList = new HashSet<>();
        for (SaleAnnounceAddImportListDto adImportReqDto : adImportReqDtoList) {
            if (adImportReqDto.getCustomerNo() != null) {
                uniqueCustomerNoList.add(adImportReqDto.getCustomerNo());
            }
        }
        List<Integer> customerNoList = new ArrayList<>(uniqueCustomerNoList);

        // メール不要の会員を取得
        List<Integer> skipNoMailRequiredCustomerNoList =
                        noMailRequiredMemberInfoLogic.getNoMailRequiredMemberInfoLogic(customerNoList);
        // オンラインログイン不可の会員を取得
        List<Integer> skipCantLoginCustomerNoList =
                        loginAdvisabilityGetCanNotLoginMemberLogic.getLoginAdvisabilityGetCanNotLoginMemberLogic(
                                        customerNoList);
        int updateCuount = 0;
        for (int i = 0; i < adImportReqDtoList.size(); i++) {

            // 次のデータを取得
            SaleAnnounceAddImportListDto saleAnnounceAddImportListDto = adImportReqDtoList.get(i);
            SaleAnnounceAddImportListDto nextData;
            if (i + 1 < adImportReqDtoList.size()) {
                nextData = adImportReqDtoList.get(i + 1);
            } else {
                nextData = null;
            }

            String customerNo = null;
            if (saleAnnounceAddImportListDto.getCustomerNo() != null) {
                customerNo = saleAnnounceAddImportListDto.getCustomerNo().toString();
            }
            String nextCustomerNo = null;
            if (nextData != null) {
                if (nextData.getCustomerNo() != null) {
                    nextCustomerNo = nextData.getCustomerNo().toString();
                }
            } else {
                // 次のデータがない場合は無効な顧客IDを設定
                nextCustomerNo = "-1";
            }
            // 会員が切り替わるタイミングでカウントアップ
            if (!(customerNo == null || customerNo.equals(nextCustomerNo))) {
                // 配信予約件数カウントアップ
                sendMailMemberCount = sendMailMemberCount + 1;
            }

            if (customerNo == null || !chkSaleSendMailSkipGoods(saleAnnounceAddImportListDto)) {
                // メール配信ステータスを更新
                updateReStockMailExclusion(saleAnnounceAddImportListDto, currentTime, saleAnnounceMailEntityList);

            } else {

                if (updateCuount < 10) {
                    // 商品情報をセット
                    goodsHtml.add(setHtml(saleAnnounceAddImportListDto));

                    //メール配信ステータスを更新
                    SaleAnnounceMailEntity saleAnnounceMailEntity =
                                    saleAnnounceMailDao.getEntity(saleAnnounceAddImportListDto.getGoodsSeq(),
                                                                  saleAnnounceAddImportListDto.getMemberInfoSeq()
                                                                 );
                    // 更新対象に追加
                    saleAnnounceMailUpdateEntity.add(saleAnnounceMailEntity);
                }
                updateCuount++;
            }

            // 会員が切り替わる直前のタイミングで処理を実行
            if (customerNo == null || customerNo.equals(nextCustomerNo)) {
                continue;
            } else {
                if (!goodsHtml.isEmpty()) {
                    // メール配信除外チェック
                    if (!chkSaleSendMailSkipMember(saleAnnounceAddImportListDto, skipNoMailRequiredCustomerNoList,
                                                   skipCantLoginCustomerNoList
                                                  )) {
                        // メール配信ステータスを更新
                        updateReStockMailExclusionMember(
                                        saleAnnounceAddImportListDto, currentTime, saleAnnounceMailUpdateEntity);
                        // 商品情報を初期化
                        goodsHtml = new ArrayList<>();
                        updateCuount = 0;
                        continue;
                    }

                    // メール配信予約成功件数カウントアップ
                    sendMailRequestCount = sendMailRequestCount + 1;

                    String goodsInfo = createAddImportHtml(goodsHtml);
                    String csvEscapedValue = "\"" + goodsInfo.replace("\"", "\"\"") + "\"";
                    // 文字数チェック
                    if (csvEscapedValue.length() >= PropertiesUtil.getSystemPropertiesValueToInt(
                                    "cuenote.api.limit.body.length")) {
                    }

                    // APIのリクエストデータ作成
                    CuenoteApiAddressImportRequestDto adImportReqDto = new CuenoteApiAddressImportRequestDto();
                    adImportReqDto.setEmail(saleAnnounceAddImportListDto.getMemberInfoMail());
                    adImportReqDto.setOfficeName(saleAnnounceAddImportListDto.getMemberInfoLastName());
                    adImportReqDto.setGoodsInfo(csvEscapedValue);
                    cuenoteApiAddressImportRequestDtoList.add(adImportReqDto);

                    // 商品情報を初期化
                    goodsHtml = new ArrayList<>();
                    updateCuount = 0;
                }
            }
        }
        return cuenoteApiAddressImportRequestDtoList;
    }

    /**
     * 送信除外更新
     * @param saleAnnounceAddImportListDto セールお知らせメールアドレス帳登録Dto
     * @param currentTime 当日日付
     * @param saleAnnounceMailUpdateEntity セールお知らせメールEntityリスト
     */
    protected void updateReStockMailExclusion(SaleAnnounceAddImportListDto saleAnnounceAddImportListDto,
                                              Timestamp currentTime,
                                              List<SaleAnnounceMailEntity> saleAnnounceMailUpdateEntity) {

        // メール配信ステータスを更新
        SaleAnnounceMailEntity saleAnnounceMailEntity =
                        saleAnnounceMailDao.getEntity(saleAnnounceAddImportListDto.getGoodsSeq(),
                                                      saleAnnounceAddImportListDto.getMemberInfoSeq()
                                                     );
        // 更新対象から削除
        saleAnnounceMailUpdateEntity.remove(saleAnnounceMailEntity);
        saleAnnounceMailEntity.setDeliveryStatus(HTypeMailDeliveryStatus.EXCLUSION);
        saleAnnounceMailEntity.setUpdateTime(currentTime);
        saleAnnounceMailDao.update(saleAnnounceMailEntity);

    }

    /**
     * 送信除外更新(会員)
     * @param saleAddImportListDto セールお知らせメールアドレス帳登録Dto
     * @param currentTime 当日日付
     * @param saleAnnounceMailUpdateEntity セールお知らせメールEntityリスト
     */
    protected void updateReStockMailExclusionMember(SaleAnnounceAddImportListDto saleAddImportListDto,
                                                    Timestamp currentTime,
                                                    List<SaleAnnounceMailEntity> saleAnnounceMailUpdateEntity) {
        // メール配信ステータスを更新
        List<SaleAnnounceMailEntity> saleAnnounceMailEntityList =
                        saleAnnounceMailDao.getSaleAnnounceMailEntityMemberInfoSeqListForNotDelivery(
                                        saleAddImportListDto.getMemberInfoSeq());
        // 更新対象から全て削除
        for (SaleAnnounceMailEntity saleAnnounceMailEntity : saleAnnounceMailEntityList) {
            saleAnnounceMailUpdateEntity.remove(saleAnnounceMailEntity);
            saleAnnounceMailEntity.setDeliveryStatus(HTypeMailDeliveryStatus.EXCLUSION);
            saleAnnounceMailEntity.setUpdateTime(currentTime);
            saleAnnounceMailDao.update(saleAnnounceMailEntity);
        }
    }

    /**
     * メール文書複製APIリクエストデータ作成
     *
     * @return Cuenote API メール文書複製APIリクエストDTO
     */
    protected CuenoteApiMailSetRequestDto createCuenoteApiMailSetRequest() {
        CuenoteApiMailSetRequestDto cuenoteApiMailSetRequestDto = new CuenoteApiMailSetRequestDto();
        cuenoteApiMailSetRequestDto.setOriginalMailId(
                        PropertiesUtil.getSystemPropertiesValue("cuenote.api.template.sale"));
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        cuenoteApiMailSetRequestDto.setTitle(CUENOTE_MAIL_SET_TITLE_HEAD + now.format(formatter));

        return cuenoteApiMailSetRequestDto;
    }

    /**
     * 配信情報予約APIリクエストデータ作成
     * @param mailSetLocation メール文書複製APIの戻り値
     *
     * @return Cuenote API 配信情報予約APIリクエストDTO
     */
    protected CuenoteApiDeliveryReserveRequestDto createCuenoteApiDeliveryReserveRequest(String mailSetLocation) {
        CuenoteApiDeliveryReserveRequestDto cuenoteApiDeliveryReserveRequestDto =
                        new CuenoteApiDeliveryReserveRequestDto();

        URI uri = null;
        try {
            uri = new URI(mailSetLocation);
        } catch (Exception e) {
            // URIを利用する場合はtry,catchが必要な為囲む
            LOGGER.error("セールお知らせメール更新時に例外処理が発生しました", e);
        }
        String path = uri.getPath();
        String[] parts = path.split("/");
        Integer setMailId = Integer.parseInt(parts[parts.length - 1]);

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String currentDateFormatted = currentDate.format(formatter);

        String dateTimeString = currentDateFormatted + " " + mailReserveTime;
        formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

        // 現在日の10時と現在日時を比較し、現在日の10時を過ぎている場合は、翌日の10時に配信予約する
        LocalDateTime fiveMinutesLater = LocalDateTime.now();
        if (fiveMinutesLater.isAfter(dateTime)) {
            fiveMinutesLater = dateTime.plus(1, ChronoUnit.DAYS);
        } else {
            fiveMinutesLater = dateTime;
        }
        DateTimeFormatter formatterZ = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+09:00");
        cuenoteApiDeliveryReserveRequestDto.setMailId(setMailId);
        cuenoteApiDeliveryReserveRequestDto.setAdBookId(
                        PropertiesUtil.getSystemPropertiesValue("cuenote.api.addressId.sale"));
        cuenoteApiDeliveryReserveRequestDto.setDeliveryTime(fiveMinutesLater.format(formatterZ));

        return cuenoteApiDeliveryReserveRequestDto;
    }

    /**
     * セールお知らせメール送信判定（商品）
     *
     * @param saleAnnounceAddImportListDto セールお知らせメールアドレス帳登録Dto
     * @return true：送信要 false：送信不要
     */
    protected boolean chkSaleSendMailSkipGoods(SaleAnnounceAddImportListDto saleAnnounceAddImportListDto) {
        boolean ret = true;

        if (!skipGoodsCodeList.contains(saleAnnounceAddImportListDto.getGoodsCode())) {
            // 商品販売判定
            if (!chkGoodsSaleStatus(saleAnnounceAddImportListDto)) {
                ret = false;
                skipGoodsCodeList.add(saleAnnounceAddImportListDto.getGoodsCode());
                errDetailsList.add("商品コード：" + saleAnnounceAddImportListDto.getGoodsCode() + "は非販売のため除外しました。");
            }

            // 商品公開判定
            if (!chkGoodsOpenStatus(saleAnnounceAddImportListDto)) {
                ret = false;
                skipGoodsCodeList.add(saleAnnounceAddImportListDto.getGoodsCode());
                errDetailsList.add("商品コード：" + saleAnnounceAddImportListDto.getGoodsCode() + "は非公開のため除外しました。");
            }
        } else {
            ret = false;
        }

        // お気に入り未登録会員
        if (memberNotSale(saleAnnounceAddImportListDto)) {
            ret = false;
            errDetailsList.add("顧客番号：" + saleAnnounceAddImportListDto.getCustomerNo() + "は、" + "商品コード："
                               + saleAnnounceAddImportListDto.getGoodsCode() + "のお気に入りを解除しため除外しました。");
        }

        return ret;
    }

    /**
     * セールお知らせメール送信判定(会員)
     *
     * @param saleAnnounceAddImportListDto セールお知らせメールアドレス帳登録Dto
     * @param skipNoMailRequiredCustomerNoList メール不要会員リスト
     * @param skipCantLoginCustomerNoList オンラインログイン不可の会員リスト
     * @return true：送信要 false：送信不要
     */
    protected boolean chkSaleSendMailSkipMember(SaleAnnounceAddImportListDto saleAnnounceAddImportListDto,
                                                List<Integer> skipNoMailRequiredCustomerNoList,
                                                List<Integer> skipCantLoginCustomerNoList) {
        boolean ret = true;

        // 会員のメール希望判定
        if (sendMailPermit(saleAnnounceAddImportListDto.getCustomerNo(), skipNoMailRequiredCustomerNoList)) {
            ret = false;
            skipCustomerNoList.add(saleAnnounceAddImportListDto.getCustomerNo());
            errDetailsList.add("顧客番号：" + saleAnnounceAddImportListDto.getCustomerNo() + "はメールによるおトク情報を希望しないため除外しました。");
        }

        // ログイン可否判定
        if (memberLoginAdvisability(saleAnnounceAddImportListDto.getCustomerNo(), skipCantLoginCustomerNoList)) {
            ret = false;
            skipCustomerNoList.add(saleAnnounceAddImportListDto.getCustomerNo());
            errDetailsList.add("顧客番号：" + saleAnnounceAddImportListDto.getCustomerNo() + "はログイン不可のため除外しました。");
        }

        return ret;
    }

    /**
     * 商品販売判定
     *
     * @param saleAnnounceAddImportListDto セールお知らせメールアドレス帳登録Dto
     * @return true：販売 false：非販売
     */
    protected boolean chkGoodsSaleStatus(SaleAnnounceAddImportListDto saleAnnounceAddImportListDto) {
        // 商品系Helper取得
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);
        return goodsUtility.isGoodsSales(saleAnnounceAddImportListDto.getSaleStatusPc(),
                                         saleAnnounceAddImportListDto.getSaleStartTimePc(),
                                         saleAnnounceAddImportListDto.getSaleEndTimePc()
                                        );
    }

    /**
     * 商品公開判定
     *
     * @param saleAnnounceAddImportListDto セールお知らせメールアドレス帳登録Dto
     * @return true：公開 false：非公開
     */
    protected boolean chkGoodsOpenStatus(SaleAnnounceAddImportListDto saleAnnounceAddImportListDto) {
        // 商品系Helper取得
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);
        return goodsUtility.isGoodsOpen(saleAnnounceAddImportListDto.getGoodsOpenStatusPc(),
                                        saleAnnounceAddImportListDto.getOpenStartTimePc(),
                                        saleAnnounceAddImportListDto.getOpenEndTimePc()
                                       );
    }

    /**
     * セールお知らせ未登録会員
     *
     * @param saleAnnounceAddImportListDto セールお知らせメールアドレス帳登録Dto
     * @return true：セールお知らせ未登録 false：セールお知らせ登録済
     */
    protected boolean memberNotSale(SaleAnnounceAddImportListDto saleAnnounceAddImportListDto) {
        // nullの場合、入荷お知らせ未登録
        return saleAnnounceAddImportListDto.getSaleGoodsSeq() == null;
    }

    /**
     * 会員メール希望判定
     *
     * @param customerNo 顧客番号
     * @param skipNoMailRequiredCustomerNoList メール不要会員リスト
     * @return true：希望しない false：希望する
     */
    protected boolean sendMailPermit(Integer customerNo, List<Integer> skipNoMailRequiredCustomerNoList) {
        // 含まれている場合メール不要
        return skipNoMailRequiredCustomerNoList.contains(customerNo);
    }

    /**
     * ログイン可否判定
     *
     * @param customerNo 顧客番号
     * @param skipCantLoginCustomerNoList オンラインログイン不可の会員リスト
     * @return ログイン可否 true：ログイン不可 false：ログイン可
     */
    protected boolean memberLoginAdvisability(Integer customerNo, List<Integer> skipCantLoginCustomerNoList) {

        // 含まれている場合ログイン不可
        return skipCantLoginCustomerNoList.contains(customerNo);
    }

    /**
     * セールお知らせ商品のアドレス帳に送信するhtmlを作成<br/>
     *
     * @param saleAnnounceAddImportListDto セールお知らせメールアドレス帳登録Dto
     * @return html
     */
    public String setHtml(SaleAnnounceAddImportListDto saleAnnounceAddImportListDto) {
        StringBuilder sb = new StringBuilder();
        String webSiteUrl = PropertiesUtil.getSystemPropertiesValue("secure.connect.url");
        String spaceIcon = webSiteUrl + "/d_images/icon/space.gif";
        String goodsUrl = webSiteUrl + "/goods/index.html?gcd=" + saleAnnounceAddImportListDto.getGoodsCode();
        String goodsIconUrl = webSiteUrl + "/d_images/icon";
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        sb.append("              <table class=\"bk\" align=\"center\" valign=\"top\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;max-width:600px;margin:0;border:0\" width=\"600\">")
          .append("                <tbody>")
          .append("                  <tr>")
          .append("                    <td class=\"bk-inner\" style=\"font-size: 0px;\">")
          .append("                      <div class=\"col col12\" style=\"width: 600px; display: inline-block; vertical-align: top;\">")
          .append("                        <table class=\"sp\" valign=\"top\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed\">")
          .append("                          <tbody>")
          .append("                            <tr>")
          .append("                              <td class=\"td\" width=\"15\" height=\"30\" style=\"vertical-align:bottom;font-size:0;width:15px;height:30px\"><img width=\"15\" height=\"30\" style=\"vertical-align:bottom;font-size:0;width:15px;height:30px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                            </tr>")
          .append("                          </tbody>")
          .append("                        </table>")
          .append("                      </div>")
          .append("                    </td>")
          .append("                  </tr>")
          .append("                </tbody>")
          .append("              </table>")
          .append("              <table class=\"bk\" align=\"center\" valign=\"top\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;max-width:600px;margin:0;border:0\" width=\"600\">")
          .append("                <tbody>")
          .append("                  <tr>")
          .append("                    <td class=\"bk-inner\" style=\"font-size: 0px;\">")
          .append("                      <div class=\"col col12\" style=\"width: 600px; display: inline-block; vertical-align: top;\">")
          .append("                        <table class=\"sp\" valign=\"top\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed\">")
          .append("                          <tbody>")
          .append("                            <tr>")
          .append("                              <td class=\"td\" width=\"15\" height=\"30\" style=\"vertical-align:bottom;font-size:0;width:15px;height:30px\"><img width=\"15\" height=\"30\" style=\"vertical-align:bottom;font-size:0;width:15px;height:30px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                              <td class=\"in\" width=\"*\" height=\"*\"><div class=\"cn hr\" style=\"text-align: -webkit-center; line-height: 0;\">")
          .append("                                <table class=\"hr-table\" align=\"center\" width=\"100\" style=\"width:100%;font-size:0;line-height:0\">")
          .append("                                    <tbody><tr><td style=\"border-top: 1px solid #e4e4e4;\">&nbsp;</td></tr></tbody>")
          .append("                                </table></div>")
          .append("                              </td>")
          .append("                              <td class=\"td\" width=\"15\" height=\"30\" style=\"vertical-align:bottom;font-size:0;width:15px;height:30px\"><img width=\"15\" height=\"30\" style=\"vertical-align:bottom;font-size:0;width:15px;height:30px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                            </tr>")
          .append("                          </tbody>")
          .append("                        </table>")
          .append("                      </div>")
          .append("                    </td>")
          .append("                  </tr>")
          .append("                </tbody>")
          .append("              </table>")
          .append("              <table class=\"bk\" align=\"center\" valign=\"top\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:600px;max-width:600px;margin:0;border:0\" width=\"600\">")
          .append("                <tbody>")
          .append("                  <tr>")
          .append("                    <td class=\"col col5\" style=\"width: 210px;  vertical-align: top;\">")
          .append("                      <table class=\"sp\" valign=\"top\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed\">")
          .append("                        <tbody>")
          .append("                          <tr>")
          .append("                            <td class=\"td\" height=\"*\" width=\"15\" style=\"vertical-align:bottom;font-size:0;width:15px\"><img height=\"1\" width=\"15\" style=\"vertical-align:bottom;font-size:0;width:15px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                            <td class=\"in\" width=\"*\" height=\"*\">")
          .append("                              <div>")
          .append("                                <table class=\"sp\" valign=\"top\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed\">")
          .append("                                  <tbody>")
          .append("                                    <tr>")
          .append("                                      <td class=\"in\" width=\"*\" height=\"*\">");
        String goodsImageUrl = webSiteUrl + goodsUtility.getFrontGoodsImagePath(
                        saleAnnounceAddImportListDto.getImageFileName());
        sb.append("                                        <div class=\"cn image\" style=\"text-align:center;text-align:-webkit-center\"><a target=\"_blank\" href=")
          .append(goodsUrl)
          .append("\"><img class=\"img\" width=\"180\" style=\"display:inline-block;vertical-align:top;max-width:100%;max-width:calc(100% - 0);height:auto;mso-border-alt:none;width:auto\" src=\"")
          .append(goodsImageUrl)
          .append("\"></a></div>");
        sb.append("                                      </td>")
          .append("                                    </tr>")
          .append("                                  </tbody>")
          .append("                                </table>")
          .append("                              </div>")
          .append("                            </td>")
          .append("                            <td class=\"td\" height=\"*\" width=\"15\" style=\"vertical-align:bottom;font-size:0;width:15px\"><img height=\"1\" width=\"15\" style=\"vertical-align:bottom;font-size:0;width:15px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                          </tr>")
          .append("                        </tbody>")
          .append("                      </table>")
          .append("                    </td>")
          .append("                    <td class=\"col col7\" style=\"width: 390px; vertical-align: top;\">")
          .append("                      <table class=\"sp\" valign=\"top\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed\">")
          .append("                        <tbody>")
          .append("                          <tr>")
          .append("                            <td class=\"td\" height=\"*\" width=\"15\" style=\"vertical-align:bottom;font-size:0;width:15px\"><img height=\"1\" width=\"15\" style=\"vertical-align:bottom;font-size:0;width:15px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                            <td class=\"in\" width=\"*\" height=\"*\">")
          .append("                              <div>")
          .append("                                <table class=\"sp\" valign=\"top\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed\">")
          .append("                                  <tbody>")
          .append("                                    <tr>")
          .append("                                      <td class=\"in\" width=\"*\" height=\"*\">")
          .append("                                        <div class=\"cn\" style=\"text-align: left; font-size: 0px; line-height: 1;\">");
        if (HTypeReserveIconFlag.ON.equals(saleAnnounceAddImportListDto.getReserveIconFlag())) {
            sb.append("                                          <span style=\"display: inline-block; font-size: 0px; line-height: 1;\"><img style=\"display:inline-block\" src=\"")
              .append(goodsIconUrl)
              .append("/icon-reserve.gif\" width=\"145\" height=\"29\"><img style=\"display:inline-block;width:5px;height:24px\" src=\"")
              .append(spaceIcon)
              .append("\" width=\"5\" height=\"29\"></span>");
        }
        if (HTypeNewIconFlag.ON.equals(saleAnnounceAddImportListDto.getNewIconFlag())) {
            sb.append("                                          <span style=\"display: inline-block; font-size: 0px; line-height: 1;\">")
              .append("<img style=\"display:inline-block\" src=\"")
              .append(goodsIconUrl)
              .append("/icon-new.gif\" width=\"70\" height=\"29\"><img style=\"display:inline-block;width:5px;height:24px\" src=\"")
              .append(spaceIcon)
              .append("\" width=\"5\" height=\"29\">")
              .append("</span>");
        }
        if (HTypeSaleIconFlag.ON.equals(saleAnnounceAddImportListDto.getSaleIconFlag())) {
            sb.append("                                          <span style=\"display: inline-block; font-size: 0px; line-height: 1;\"><img style=\"display:inline-block\" src=\"")
              .append(goodsIconUrl)
              .append("/icon-sale.gif\" width=\"70\" height=\"29\"><img style=\"display:inline-block;width:5px;height:24px\" src=\"")
              .append(spaceIcon)
              .append("\" width=\"5\" height=\"29\"></span>");
        }
        if (HTypeOutletIconFlag.ON.equals(saleAnnounceAddImportListDto.getOutletIconFlag())) {
            sb.append("                                          <span style=\"display: inline-block; font-size: 0px; line-height: 1;\"><img style=\"display:inline-block\" src=\"")
              .append(goodsIconUrl)
              .append("/icon-outlet.gif\" width=\"70\" height=\"29\"><img style=\"display:inline-block;width:5px;height:24px\" src=\"")
              .append(spaceIcon)
              .append("\" width=\"5\" height=\"29\"></span>");
        }
        sb.append("                                        </div>")
          .append("                                      </td>")
          .append("                                    </tr>")
          .append("                                    <tr>")
          .append("                                      <td class=\"in\" width=\"*\" height=\"*\">")
          .append("                                        <div class=\"cn text\" style=\"text-align: left; word-break: break-all; font-family: &quot;Hiragino Sans&quot;, &quot;メイリオ&quot;, Meiryo, Osaka, &quot;ＭＳ Ｐゴシック&quot;, &quot;MS PGothic&quot;, &quot;Hiragino Kaku Gothic ProN&quot;, sans-serif; font-size: 15px; line-height: 1.5; width: 360px;\">")
          .append("                                          <p style=\"margin: 0px; font-size: 15px;\">")
          .append(saleAnnounceAddImportListDto.getGoodsCode())
          .append("</p>")
          .append("                                          <p style=\"margin: 0px; font-size: 16px;\"><b>")
          .append(saleAnnounceAddImportListDto.getGoodsName())
          .append("</b></p>")
          .append("                                        </div>")
          .append("                                      </td>")
          .append("                                    </tr>")
          .append("                                    <tr>")
          .append("                                      <td class=\"td\" height=\"5\" width=\"*\" style=\"vertical-align:bottom;font-size:0;height:5px\"><img height=\"5\" width=\"1\" style=\"vertical-align:bottom;font-size:0;height:5px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                                    </tr>")
          .append("                                    <tr>")
          .append("                                      <td class=\"in\" width=\"*\" height=\"*\">")
          .append("                                        <div class=\"cn text\" style=\"text-align: left; word-break: break-all; font-family: &quot;Hiragino Sans&quot;, &quot;メイリオ&quot;, Meiryo, Osaka, &quot;ＭＳ Ｐゴシック&quot;, &quot;MS PGothic&quot;, &quot;Hiragino Kaku Gothic ProN&quot;, sans-serif; font-size: 15px; line-height: 1.5; width: 360px;\">")
          .append("                                          <p style=\"margin: 0px; font-size: 15px;\">");
        if (StringUtil.isNotEmpty(saleAnnounceAddImportListDto.getUnitTitle1())) {
            sb.append(saleAnnounceAddImportListDto.getUnitTitle1())
              .append("：")
              .append(saleAnnounceAddImportListDto.getUnitValue1())
              .append("<br>");
        }
        if (StringUtil.isNotEmpty(saleAnnounceAddImportListDto.getUnitTitle2())) {
            sb.append(saleAnnounceAddImportListDto.getUnitTitle2())
              .append("：")
              .append(saleAnnounceAddImportListDto.getUnitValue2());
        }
        sb.append("</p>")
          .append("                                        </div>")
          .append("                                      </td>")
          .append("                                    </tr>")
          .append("                                    <tr>")
          .append("                                      <td class=\"td\" height=\"8\" width=\"*\" style=\"vertical-align:bottom;font-size:0;height:8px\"><img height=\"8\" width=\"1\" style=\"vertical-align:bottom;font-size:0;height:8px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                                    </tr>")
          .append("                                    <tr>")
          .append("                                      <td class=\"in\" width=\"*\" height=\"*\">")
          .append("                                        <div class=\"cn text\" style=\"text-align: left; word-break: break-all; font-family: &quot;Hiragino Sans&quot;, &quot;メイリオ&quot;, Meiryo, Osaka, &quot;ＭＳ Ｐゴシック&quot;, &quot;MS PGothic&quot;, &quot;Hiragino Kaku Gothic ProN&quot;, sans-serif; font-size: 15px; line-height: 1.5; width: 360px;\">")
          .append("                                          <p class=\"sale\" style=\"margin: 0px; font-size: 16px; color: #ff0000;\">");
        if (StringUtil.isNotEmpty(saleAnnounceAddImportListDto.getGoodsPreDiscountPrice())) {
            sb.append("                                            <span style=\"font-size:14px\">")
              .append(saleAnnounceAddImportListDto.getGoodsPreDiscountPrice())
              .append("</span><br>");
        }

        if (HTypeSaleIconFlag.ON.equals(saleAnnounceAddImportListDto.getSaleIconFlag())) {
            if (saleAnnounceAddImportListDto.getPreDiscountPriceLow()
                                            .compareTo(saleAnnounceAddImportListDto.getPreDiscountPriceHight()) == 0) {
                sb.append("                                            <span style=\"font-size:14px\">セール：</span><b>")
                  .append(decimalFormat.format(saleAnnounceAddImportListDto.getPreDiscountPriceLow()))
                  .append("</b>&nbsp;<span style=\"font-size:14px\">円</span>");
            } else {
                sb.append("                                            <span style=\"font-size:14px\">セール：</span><b>")
                  .append(decimalFormat.format(saleAnnounceAddImportListDto.getPreDiscountPriceLow()))
                  .append("</b>&nbsp;円&nbsp;～&nbsp;<b>")
                  .append(decimalFormat.format(saleAnnounceAddImportListDto.getPreDiscountPriceHight()))
                  .append("</b>&nbsp;<span style=\"font-size:14px\">円</span>");
            }
        }
        sb.append("                                          </p>");
        if (saleAnnounceAddImportListDto.getGoodsPriceInTaxLow()
                                        .compareTo(saleAnnounceAddImportListDto.getGoodsPriceInTaxHight()) == 0) {
            sb.append("                                          <p style=\"margin: 0px; font-size: 16px;\"><b>")
              .append(decimalFormat.format(saleAnnounceAddImportListDto.getGoodsPriceInTaxLow()))
              .append("</b>&nbsp;<span style=\"font-size:14px\">円</span></p>");
        } else {
            sb.append("                                          <p class=\"regular\" style=\"margin: 3px 0px 0px 0px; font-size: 14px;\">通常価格：<span>")
              .append(decimalFormat.format(saleAnnounceAddImportListDto.getGoodsPriceInTaxLow()))
              .append("</span>円&nbsp;～&nbsp;<span>")
              .append(decimalFormat.format(saleAnnounceAddImportListDto.getGoodsPriceInTaxHight()))
              .append("</span>円</p>");
        }
        sb.append("                                        </div>")
          .append("                                      </td>")
          .append("                                    </tr>")
          .append("                                    <tr>")
          .append("                                      <td class=\"td\" height=\"10\" width=\"*\" style=\"vertical-align:bottom;font-size:0;height:10px\"><img height=\"10\" width=\"1\" style=\"vertical-align:bottom;font-size:0;height:10px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                                    </tr>")
          .append("                                    <tr>")
          .append("                                      <td class=\"in\" width=\"*\" height=\"*\">")
          .append("                                        <div>")
          .append("                                          <table valign=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">")
          .append("                                            <tbody>")
          .append("                                              <tr>")
          .append("                                                <td class=\"in\" width=\"*\" height=\"*\" align=\"center\" style=\"background-color:#f36b30; border-radius:18px;\">")
          .append("                                                  <a href=")
          .append(goodsUrl)
          .append("\" target=\"_blank\" style=\"font-size: 14px; color: #ffffff; text-decoration: none; background-color: #f36b30; display: block; min-width: 80px; font-family:&quot;Hiragino Sans&quot;, &quot;メイリオ&quot;, Meiryo, Osaka, &quot;ＭＳ Ｐゴシック&quot;, &quot;MS PGothic&quot;, &quot;Hiragino Kaku Gothic ProN&quot;, sans-serif; font-weight: bold; word-break: break-word; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding: 7px 32px; border: 1px solid #f36b30; line-height:21px; mso-line-height-rule: exactly;\">")
          .append("                                                    商品はこちら")
          .append("                                                </a>")
          .append("                                                </td>")
          .append("                                              </tr>")
          .append("                                            </tbody>")
          .append("                                          </table>")
          .append("                                        </div>")
          .append("                                      </td>")
          .append("                                    </tr>")
          .append("                                    <tr>")
          .append("                                      <td class=\"td\" height=\"10\" width=\"*\" style=\"vertical-align:bottom;font-size:0;height:10px\"><img height=\"10\" width=\"1\" style=\"vertical-align:bottom;font-size:0;height:10px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                                    </tr>")
          .append("                                  </tbody>")
          .append("                                </table>")
          .append("                              </div>")
          .append("                            </td>")
          .append("                            <td class=\"td\" height=\"*\" width=\"15\" style=\"vertical-align:bottom;font-size:0;width:15px\"><img height=\"1\" width=\"15\" style=\"vertical-align:bottom;font-size:0;width:15px\" src=\"")
          .append(spaceIcon)
          .append("\"></td>")
          .append("                          </tr>")
          .append("                        </tbody>")
          .append("                      </table>")
          .append("                    </td>")
          .append("                  </tr>")
          .append("                </tbody>")
          .append("              </table>");
        return sb.toString();
    }

    /**
     * APIエラー時にセールお知らせメールTBLをシステムエラーに更新する
     *
     * @param saleAnnounceMailUpdateEntity　セールお知らせメール
     * @return true：希望しない false：希望する
     */
    protected void systemErrorUpdate(List<SaleAnnounceMailEntity> saleAnnounceMailUpdateEntity) {
        Timestamp currentTime = dateUtility.getCurrentTime();
        for (SaleAnnounceMailEntity saleAnnounceMailEntity : saleAnnounceMailUpdateEntity) {
            saleAnnounceMailEntity.setDeliveryStatus(HTypeMailDeliveryStatus.SYSTEM_ERROR);
            saleAnnounceMailEntity.setUpdateTime(currentTime);
            saleAnnounceMailDao.update(saleAnnounceMailEntity);
        }
    }

    /**
     * アドレス帳インポートAPIに使用するhtmlを作成<br/>
     *
     * @param goodsHtml 商品情報
     * @return html
     */
    public String createAddImportHtml(List<String> goodsHtml) {
        StringBuilder htmlBuilder = new StringBuilder();
        // 商品情報をセット
        for (String goods : goodsHtml) {
            htmlBuilder.append(goods);
        }
        return htmlBuilder.toString();

    }
}
// 2023-renew No41 to here
