/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.cuenotefcsale.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailDeliveryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
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
import org.springframework.core.env.Environment;

import java.net.SocketTimeoutException;
import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

// 2023-renew No41 from here

/**
 * CuenoteFCセールお知らせメール配信バッチ
 *
 * @author takashima
 */
public class CuenoteFCSaleBatch extends AbstractBatch {

    /**
     * Logger
     */
    private final Logger LOGGER = LoggerFactory.getLogger(CuenoteFCSaleBatch.class);

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
    public final static String CUENOTE_MAIL_SET_TITLE_HEAD = "セールお知らせメール_";

    /**
     * 処理名(セールお知らせメール)
     */
    public final static String PROCESS_NAME_DELIVERY_RESERVE = "セールお知らせメール";

    /**
     * タイムアウトエラーメッセージ
     */
    public final static String API_ERROR_TIMEOUT_MESSAGE = "/WEB-API通信でタイムアウトが発生しました。";

    /**
     * タイムアウトエラーリカバリメッセージ
     */
    public final static String API_ERROR_TIMEOUT_RECOVERY_MESSAGE = "【リカバリ】時間を空けて再実行";

    /**
     * APIエラーメッセージ
     */
    public final static String API_ERROR_MESSAGE = "/WEB-API通信でAPIエラーが発生しました。";

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
     * メールスキップ件数
     */
    Integer sendMailSkipCount = 0;

    /**
     * バッチ処理総件数
     */
    protected int batchProcessTotalCount = 0;

    /**
     * 商品コードスキップリスト
     */
    List<String> skipGoodsCodeList = new ArrayList<>();

    /**
     *  顧客番号スキップリスト
     */
    List<Integer> skipCustomerNoList = new ArrayList<>();

    /**
     * エラー詳細リスト
     */
    List<String> errDetailsList = new ArrayList<>();

    /**
     * コンストラクタ
     */
    public CuenoteFCSaleBatch(Environment environment) {
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
                                                                                                  sendMailSkipCount,
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

            // メール配信対象を取得
            List<SaleAnnounceAddImportListDto> adImportReqDtoList = saleAddImportListGetLogic.execute(keyList);

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

                exceptionName = "配信情報予約" + API_ERROR_MESSAGE;
                mailMessageDetail =
                                sendAdminMailLogic.createFailedMailDetail(exceptionName, PROCESS_NAME_DELIVERY_RESERVE,
                                                                          API_ERROR_TIMEOUT_RECOVERY_MESSAGE
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
                                                                                      sendMailSkipCount,
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
        List<Integer> customerNoList = new ArrayList<>();
        for (SaleAnnounceAddImportListDto adImportReqDto : adImportReqDtoList) {
            if (adImportReqDto.getCustomerNo() != null) {
                customerNoList.add(adImportReqDto.getCustomerNo());
            }
        }
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

            if (customerNo == null || !chkSaleSendMailSkipGoods(saleAnnounceAddImportListDto)) {
                // スキップ件数カウントアップ
                sendMailSkipCount = sendMailSkipCount + 1;
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
                    // メール配信スキップチェック
                    if (!chkSaleSendMailSkipMember(saleAnnounceAddImportListDto, skipNoMailRequiredCustomerNoList,
                                                   skipCantLoginCustomerNoList
                                                  )) {
                        // スキップ件数カウントアップ
                        sendMailSkipCount = sendMailSkipCount + 1;
                        // メール配信ステータスを更新
                        updateReStockMailExclusion(
                                        saleAnnounceAddImportListDto, currentTime, saleAnnounceMailUpdateEntity);
                        // 商品情報を初期化
                        goodsHtml = new ArrayList<>();
                        continue;
                    }

                    // メール配信件数カウントアップ
                    sendMailRequestCount = sendMailRequestCount + 1;

                    // 商品htmlを作成する。
                    String goodsInfo = null;

                    for (String s : goodsHtml) {
                        if (StringUtil.isEmpty(goodsInfo)) {
                            goodsInfo = s;
                        } else {
                            goodsInfo = goodsInfo + "<br/>" + s;
                        }
                    }

                    // APIのリクエストデータ作成
                    CuenoteApiAddressImportRequestDto adImportReqDto = new CuenoteApiAddressImportRequestDto();
                    adImportReqDto.setEmail(saleAnnounceAddImportListDto.getMemberInfoMail());
                    adImportReqDto.setOfficeName(saleAnnounceAddImportListDto.getMemberInfoLastName());
                    adImportReqDto.setGoodsInfo(goodsInfo);
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
        LocalDateTime fiveMinutesLater = LocalDateTime.now().plus(
                        PropertiesUtil.getSystemPropertiesValueToInt("cuenote.api.goodsReceived.send.mail.extension"),
                        ChronoUnit.MINUTES
                                                                 );
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

        // 商品販売判定
        if (!chkGoodsSaleStatus(saleAnnounceAddImportListDto)) {
            ret = false;

            if (!skipGoodsCodeList.contains(saleAnnounceAddImportListDto.getGoodsCode())) {
                skipGoodsCodeList.add(saleAnnounceAddImportListDto.getGoodsCode());
                errDetailsList.add("商品番号：" + saleAnnounceAddImportListDto.getGoodsCode() + "は非販売のためスキップしました。");
            }
        }

        // 商品公開判定
        if (!chkGoodsOpenStatus(saleAnnounceAddImportListDto)) {
            ret = false;
            skipGoodsCodeList.add(saleAnnounceAddImportListDto.getGoodsCode());
            errDetailsList.add("商品番号：" + saleAnnounceAddImportListDto.getGoodsCode() + "は非公開のためスキップしました。");
        }

        // セールお知らせ未登録会員
        if (memberNotSale(saleAnnounceAddImportListDto)) {
            ret = false;
            errDetailsList.add("顧客番号：" + saleAnnounceAddImportListDto.getCustomerNo() + "は" + "商品番号："
                               + saleAnnounceAddImportListDto.getGoodsCode() + "のセールお知らせを希望しないためスキップしました。");
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
            errDetailsList.add(
                            "顧客番号：" + saleAnnounceAddImportListDto.getCustomerNo() + "はメールによるおトク情報を希望しないためスキップしました。");
        }

        // ログイン可否判定
        if (memberLoginAdvisability(saleAnnounceAddImportListDto.getCustomerNo(), skipCantLoginCustomerNoList)) {
            ret = false;
            skipCustomerNoList.add(saleAnnounceAddImportListDto.getCustomerNo());
            errDetailsList.add("顧客番号：" + saleAnnounceAddImportListDto.getCustomerNo() + "はログイン不可のためスキップしました。");
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
        sb.append(saleAnnounceAddImportListDto.getImageFileName());
        sb.append(saleAnnounceAddImportListDto.getSaleIconFlag());
        sb.append(saleAnnounceAddImportListDto.getReserveIconFlag());
        sb.append(saleAnnounceAddImportListDto.getNewIconFlag());
        sb.append(saleAnnounceAddImportListDto.getOutletIconFlag());
        sb.append(saleAnnounceAddImportListDto.getGoodsCode());
        sb.append(saleAnnounceAddImportListDto.getGoodsName());
        sb.append(saleAnnounceAddImportListDto.getUnitTitle1());
        sb.append(saleAnnounceAddImportListDto.getUnitValue1());
        sb.append(saleAnnounceAddImportListDto.getUnitTitle2());
        sb.append(saleAnnounceAddImportListDto.getUnitValue2());
        sb.append(saleAnnounceAddImportListDto.getGoodsPriceInTaxLow());
        sb.append(saleAnnounceAddImportListDto.getGoodsPriceInTaxHight());
        sb.append(saleAnnounceAddImportListDto.getPreDiscountPriceLow());
        sb.append(saleAnnounceAddImportListDto.getPreDiscountPriceHight());
        return sb.toString();
    }
}
// 2023-renew No41 to here
