/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.stockdataimport.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailDeliveryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReStockStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTopStockAnnounceFlg;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.restock.ReStockAnnounceDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.restock.ReStockAnnounceGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.restock.ReStockAnnounceMailDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.restock.ReStockMemberUpdateDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReStockResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetRestockRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.restock.ReStockAnnounceEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.restock.ReStockAnnounceGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.restock.ReStockAnnounceMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.NoMailRequiredMemberInfoLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.loginadvisability.LoginAdvisabilityGetCanNotLoginMemberLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetReStockLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hitmall.utility.GoodsUtility;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 入荷情報取得バッチクラス
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public class StockDataImportBatch extends AbstractBatch {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StockDataImportBatch.class);

    /**
     * 処理結果
     */
    protected String batchProcessResult;

    /**
     * 入荷お知らせDao
     */
    ReStockAnnounceDao reStockAnnounceDao;

    /**
     * 入荷お知らせ商品Dao
     */
    ReStockAnnounceGoodsDao reStockAnnounceGoodsDao;

    /**
     * 入荷お知らせメールDao
     */
    ReStockAnnounceMailDao reStockAnnounceMailDao;

    /**
     * 会員情報取得ロジック
     */
    MemberInfoGetLogic memberInfoGetLogic;

    /**
     * 会員情報更新ロジック
     */
    MemberInfoUpdateLogic memberInfoUpdateLogic;

    /**
     * メール不要の会員情報取得ロジック
     */
    NoMailRequiredMemberInfoLogic noMailRequiredMemberInfoLogic;

    /**
     * ログイン不可の会員情報取得ロジック
     */
    LoginAdvisabilityGetCanNotLoginMemberLogic loginAdvisabilityGetCanNotLoginMemberLogic;

    /**
     * 商品Dao
     */
    GoodsDao goodsDao;

    /**
     * 商品グループDao
     */
    GoodsGroupDao goodsGroupDao;

    /**
     * 商品入荷情報取得WEB-API連携ロジック
     */
    private final WebApiGetReStockLogic webApiGetReStockLogic;

    /**
     * 管理者用メール送信設定
     */
    private final InstantMailSetting mailSetting;

    /**
     * メール送信サービス
     */
    private final MailSendService mailSendService;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    // 入荷情報更新件数
    Integer reStockUpdCnt = 0;

    /**
     * 処理結果正常終了メッセージ
     */
    private static final String UPDATE_RESULT_SUCCESS = "正常終了";

    /**
     * 処理結果異常終了メッセージ
     */
    private static final String UPDATE_RESULT_ERROR = "異常終了";

    /**
     * 処理結果正常終了メッセージ
     */
    private static final String SYSTEM_NAME = "【ピーディーアール　オンラインショップ】";

    /**
     * タイムアウトエラーメッセージ
     */
    public final static String API_ERROR_TIMEOUT_MESSAGE = "/WEB-API通信でタイムアウトが発生しました。";

    /**
     * タイムアウトエラーリカバリメッセージ
     */
    public final static String API_ERROR_TIMEOUT_RECOVERY_MESSAGE = "【リカバリ】時間を空けて再実行";

    /**
     * APIエラーリカバリメッセージ
     */
    public final static String API_ERROR_RECOVERY_MESSAGE = "【リカバリ】エラー原因解消後、 入荷情報取得バッチを再実行してください。";

    /**
     * 処理結果メール詳細メッセージ
     */
    public boolean mailMessageDetail;

    /**
     * Creates a new StockDataImportBatch object.
     */
    public StockDataImportBatch(Environment environment) {
        super();
        this.reStockAnnounceDao = ApplicationContextUtility.getBean(ReStockAnnounceDao.class);
        this.reStockAnnounceGoodsDao = ApplicationContextUtility.getBean(ReStockAnnounceGoodsDao.class);
        this.reStockAnnounceMailDao = ApplicationContextUtility.getBean(ReStockAnnounceMailDao.class);
        this.memberInfoGetLogic = ApplicationContextUtility.getBean(MemberInfoGetLogic.class);
        this.memberInfoUpdateLogic = ApplicationContextUtility.getBean(MemberInfoUpdateLogic.class);
        this.noMailRequiredMemberInfoLogic = ApplicationContextUtility.getBean(NoMailRequiredMemberInfoLogic.class);
        this.loginAdvisabilityGetCanNotLoginMemberLogic =
                        ApplicationContextUtility.getBean(LoginAdvisabilityGetCanNotLoginMemberLogic.class);
        this.goodsDao = ApplicationContextUtility.getBean(GoodsDao.class);
        this.goodsGroupDao = ApplicationContextUtility.getBean(GoodsGroupDao.class);
        this.webApiGetReStockLogic = ApplicationContextUtility.getBean(WebApiGetReStockLogic.class);
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.exitMessageUtil = new BatchExitMessageUtil();

        this.mailSetting = new InstantMailSetting(environment.getProperty("mail.setting.stock.data.import.smtp.server"),
                                                  environment.getProperty("mail.setting.stock.data.import.mail.from"),
                                                  null, null, Collections.singletonList(
                        environment.getProperty("mail.setting.stock.data.import.mail.receivers"))
        );
    }

    /**
     * 商品グループ在庫表示追加・変更処理を実行します
     *
     * @return RepeatStatus
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // コンテキスト取得
        ExecutionContext executionContext =
                        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();

        // バッチのジョッブ情報取得
        JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobParameters();
        String administratorId = jobParameters.getString("administratorId");
        String shopSeq = "1001";
        if (!StringUtils.isEmpty(shopSeq)) {
            super.setShopSeq(Integer.valueOf(shopSeq));
        }

        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        Timestamp currentTime = dateUtility.getCurrentTime();
        WebApiGetReStockResponseDto resDto = null;
        try {

            // 入荷お知らせ情報から商品リストを取得
            List<String> goodsCodeList = reStockAnnounceDao.getGoodsCodeList();
            // データが無い場合は処理終了
            if (goodsCodeList.isEmpty()) {
                // 処理結果成功メール送信
                batchProcessResult = UPDATE_RESULT_SUCCESS;

                StringBuilder mailReport = new StringBuilder();
                mailReport.append("更新対象が有りません。");
                // メール送信メソッド
                sendResultMail(mailReport.toString());

                this.report(mailReport.toString());
                executionContext.put(
                                BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                                new BatchExitMessageDto(String.valueOf(reStockUpdCnt),
                                                                        this.getReportString().toString()
                                                )));

                return RepeatStatus.FINISHED;
            }

            String goodsCodeParam = String.join("|", goodsCodeList);

            // 入荷情報取得
            // 商品入荷情報取得リクエストDto
            WebApiGetRestockRequestDto reqDto = ApplicationContextUtility.getBean(WebApiGetRestockRequestDto.class);
            // 商品入荷情報取得API用引数をセット
            reqDto.setGoodsCode(goodsCodeParam);
            // 商品入荷情報取得WEB-API実行
            resDto = (WebApiGetReStockResponseDto) webApiGetReStockLogic.execute(reqDto);

            // 連携失敗
            if (resDto == null) {
                throw new RuntimeException("商品入荷情報取得連携に失敗しました。");
            }
            // 取得結果が不正なら処理終了
            if (resDto.getInfo() == null || !"0".equals(resDto.getResult().getStatus())) {
                throw new Exception("商品入荷情報の取得に失敗しました。 ステータスコード：" + resDto.getResult().getStatus());
            }

            // 商品入荷情報取得処理終了
            LOGGER.debug("商品入荷情報取得WEB-APIの実行が正常終了しました。");

            // 入荷情報更新
            // ※在庫数＞0のものを入荷済み、在庫数<=0のものを未入荷に変更
            List<ReStockMemberUpdateDto> reStockMemberUpdateDtoList = new ArrayList<>();

            // 会員更新用入荷お知らせメールリスト
            for (WebApiGetReStockResponseDetailDto info : resDto.getInfo()) {
                GoodsEntity goodsEntity = goodsDao.getGoodsByCode(getShopSeq(), info.getGoodsCode());
                List<ReStockAnnounceEntity> reStockAnnounceEntityList =
                                reStockAnnounceDao.getGoodsEntityList(goodsEntity.getGoodsSeq());

                HTypeReStockStatus goodsReStockStatus = null;
                for (int i = 0; i < reStockAnnounceEntityList.size(); i++) {
                    ReStockAnnounceEntity reStockAnnounceEntity = reStockAnnounceEntityList.get(i);
                    if (i == 0) {
                        ReStockAnnounceGoodsEntity reStockAnnounceGoodsEntity =
                                        reStockAnnounceGoodsDao.getEntity(reStockAnnounceEntity.getGoodsSeq());
                        goodsReStockStatus = handleFirstReStockAnnounceEntity(info, reStockAnnounceGoodsEntity,
                                                                              reStockAnnounceEntity, goodsEntity,
                                                                              currentTime, reStockMemberUpdateDtoList
                                                                             );
                    } else {
                        handleNonFirstReStockAnnounceEntity(info, reStockAnnounceEntity, goodsEntity,
                                                            goodsReStockStatus, reStockMemberUpdateDtoList
                                                           );
                    }
                }
            }

            // 入荷通知件数
            Integer reStockNoticeCnt = 0;
            // 入荷通知登録件数
            Integer reStockNoticeInsCnt = 0;
            // 入荷通知削除件数
            Integer reStockNoticeDelCnt = 0;
            // 対象会員件数
            Integer reStockNoticeCustomerCnt = 0;

            // 入荷お知らせメール配信対象の会員を取得
            Set<Integer> uniqueMemberInfoSeqList = new HashSet<>();
            for (ReStockMemberUpdateDto reStockMemberUpdateDto : reStockMemberUpdateDtoList) {
                uniqueMemberInfoSeqList.add(reStockMemberUpdateDto.getMemberInfoSeq());
            }

            // メール不要の会員、ログイン不可の会員を取得
            List<Integer> memberInfoSeqList = new ArrayList<>(uniqueMemberInfoSeqList);
            List<Integer> skipNoMailRequiredMemberInfoSeqList =
                            noMailRequiredMemberInfoLogic.getNoMailRequiredMemberInfoSeqLogic(memberInfoSeqList);
            List<Integer> skipCantLoginMemberInfoSeqList =
                            loginAdvisabilityGetCanNotLoginMemberLogic.getLoginAdvisabilityGetCanNotLoginMemberInfoSeqLogic(
                                            memberInfoSeqList);

            for (Integer uniqueMemberInfoSeq : uniqueMemberInfoSeqList) {
                boolean updMemberFlg = false;
                HTypeTopStockAnnounceFlg topStockAnnounceFlg = HTypeTopStockAnnounceFlg.OFF;
                for (ReStockMemberUpdateDto reStockMemberUpdateDto : reStockMemberUpdateDtoList) {
                    if (uniqueMemberInfoSeq.equals(reStockMemberUpdateDto.getMemberInfoSeq())) {
                        GoodsEntity goodsEntity = goodsDao.getEntity(reStockMemberUpdateDto.getGoodsSeq());
                        GoodsGroupEntity goodsGroupEntity = goodsGroupDao.getEntity(goodsEntity.getGoodsGroupSeq());
                        // 商品公開＆販売中
                        if (chkGoodsOpenStatus(goodsGroupEntity) && chkGoodsSaleStatus(goodsEntity)) {
                            updMemberFlg = true;
                            // 1件でも入荷がある場合フラグを立てる
                            if (HTypeReStockStatus.RESTOCK.equals(reStockMemberUpdateDto.getReStockStatus())) {
                                topStockAnnounceFlg = HTypeTopStockAnnounceFlg.ON;
                            }
                            // メール希望会員
                            if (!sendMailPermit(uniqueMemberInfoSeq, skipNoMailRequiredMemberInfoSeqList)
                                && !memberLoginAdvisability(uniqueMemberInfoSeq, skipCantLoginMemberInfoSeqList)) {
                                // 配信情報登録
                                try {
                                    insertReStockAnnounceMail(reStockMemberUpdateDto, currentTime);
                                    if (HTypeReStockStatus.RESTOCK.equals(reStockMemberUpdateDto.getReStockStatus())) {
                                        reStockNoticeCustomerCnt++;
                                    }
                                } catch (Exception e) {
                                    LOGGER.error("例外処理が発生しました", e);
                                    LOGGER.info("[" + StockDataImportBatch.class.getName() + "]" + new Timestamp(
                                                    System.currentTimeMillis()) + " rollbackが終了しました。処理を終了します。");
                                    // エラーがあった場合は管理者にメール送信
                                    sendAdministratorErrorMail(e.getClass().getName());
                                    executionContext.put(BatchExitMessageUtil.exitMsg,
                                                         exitMessageUtil.convertObjectToJson(
                                                                         new BatchExitMessageDto(String.valueOf(0),
                                                                                                 this.getReportString()
                                                                                                     .toString()
                                                                         ))
                                                        );
                                    return RepeatStatus.FINISHED;
                                }
                            }
                        }
                    }
                }

                // 会員情報更新
                if (updMemberFlg) {
                    MemberInfoEntity memberInfoEntity = memberInfoGetLogic.getEntityForUpdate(uniqueMemberInfoSeq);
                    memberInfoEntity.setTopStockAnnounceFlg(topStockAnnounceFlg);
                    memberInfoEntity.setStockAnnounceWatchFlg(HTypeStockAnnounceWatchFlg.UNREAD);
                    try {
                        memberInfoUpdateLogic.execute(memberInfoEntity);
                        reStockNoticeCnt++;
                        if (HTypeTopStockAnnounceFlg.ON.equals(topStockAnnounceFlg)) {
                            reStockNoticeInsCnt++;
                        } else {
                            reStockNoticeDelCnt++;
                        }
                    } catch (Exception e) {
                        LOGGER.error("例外処理が発生しました", e);
                        LOGGER.info("[" + StockDataImportBatch.class.getName() + "]" + new Timestamp(
                                        System.currentTimeMillis()) + " rollbackが終了しました。処理を終了します。");
                        // エラーがあった場合は管理者にメール送信
                        sendAdministratorErrorMail(e.getClass().getName());
                        executionContext.put(
                                        BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                                        new BatchExitMessageDto(String.valueOf(0),
                                                                                this.getReportString().toString()
                                                        )));
                        return RepeatStatus.FINISHED;
                    }
                }
            }

            String reStockUpdMsg = "入荷情報更新件数：" + reStockUpdCnt + "件";
            String reStockNoticeMsg = "\n\n\n入荷通知更新：" + reStockNoticeCnt + "件";
            String reStockNoticeInsMsg = "\n - 入荷通知登録件数：" + reStockNoticeInsCnt + "件";
            String reStockNoticeDelMsg = "\n - 入荷通知削除件数：" + reStockNoticeDelCnt + "件";
            String reStockNoticeCustomerMsg = "\n\n対象会員件数：" + reStockNoticeCustomerCnt + "件";

            StringBuilder mailReport = new StringBuilder();
            mailReport.append(reStockUpdMsg);
            mailReport.append(reStockNoticeMsg);
            mailReport.append(reStockNoticeInsMsg);
            mailReport.append(reStockNoticeDelMsg);
            mailReport.append(reStockNoticeCustomerMsg);

            // 処理結果成功メール送信
            batchProcessResult = UPDATE_RESULT_SUCCESS;
            // メール送信メソッド
            sendResultMail(mailReport.toString());

            this.report(mailReport.toString());
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(String.valueOf(reStockUpdCnt),
                                                                    this.getReportString().toString()
                                            )));

            LOGGER.debug("入荷情報取込バッチ - 正常に終了しました。");
        } catch (RuntimeException e) {
            String exceptionName = "商品入荷情報取得" + API_ERROR_TIMEOUT_MESSAGE;
            mailMessageDetail = createFailedMailDetail(exceptionName, API_ERROR_TIMEOUT_RECOVERY_MESSAGE);
            // メール送信
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(String.valueOf(0),
                                                                    this.getReportString().toString()
                                            )));
            throw e;
        } catch (Exception e) {
            String exceptionName = "商品セール情報取得" + "/処理結果ステータス：{" + resDto.getResult().getStatus() + "}メッセージ：{"
                                   + resDto.getResult().getMessage() + "}";
            mailMessageDetail = createFailedMailDetail(exceptionName, API_ERROR_RECOVERY_MESSAGE);
            // メール送信
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(String.valueOf(0),
                                                                    this.getReportString().toString()
                                            )));
            throw e;
        }
        return RepeatStatus.FINISHED;
    }

    private HTypeReStockStatus handleFirstReStockAnnounceEntity(WebApiGetReStockResponseDetailDto info,
                                                                ReStockAnnounceGoodsEntity reStockAnnounceGoodsEntity,
                                                                ReStockAnnounceEntity reStockAnnounceEntity,
                                                                GoodsEntity goodsEntity,
                                                                Timestamp currentTime,
                                                                List<ReStockMemberUpdateDto> reStockMemberUpdateDtoList) {
        HTypeReStockStatus goodsReStockStatus = null;
        if (Integer.parseInt(info.getStockQuantity().toString()) > 0) {
            if (reStockAnnounceGoodsEntity != null) {
                goodsReStockStatus = reStockAnnounceGoodsEntity.getReStockStatus();
                if (!HTypeReStockStatus.RESTOCK.equals(goodsReStockStatus)) {
                    updateReStockAnnounceGoods(reStockAnnounceGoodsEntity, currentTime, HTypeReStockStatus.RESTOCK);
                    reStockMemberUpdateDtoList.add(createUpdateMemberInfo(reStockAnnounceEntity, goodsEntity,
                                                                          HTypeReStockStatus.RESTOCK
                                                                         ));
                    reStockUpdCnt++;
                }
            } else {
                insertReStockAnnounceGoods(reStockAnnounceEntity, currentTime, HTypeReStockStatus.RESTOCK);
                reStockMemberUpdateDtoList.add(
                                createUpdateMemberInfo(reStockAnnounceEntity, goodsEntity, HTypeReStockStatus.RESTOCK));
                reStockUpdCnt++;
            }
        } else {
            if (reStockAnnounceGoodsEntity != null) {
                goodsReStockStatus = reStockAnnounceGoodsEntity.getReStockStatus();
                if (!HTypeReStockStatus.NO_RESTOCK.equals(goodsReStockStatus)) {
                    updateReStockAnnounceGoods(reStockAnnounceGoodsEntity, currentTime, HTypeReStockStatus.NO_RESTOCK);
                    reStockMemberUpdateDtoList.add(createUpdateMemberInfo(reStockAnnounceEntity, goodsEntity,
                                                                          HTypeReStockStatus.NO_RESTOCK
                                                                         ));
                    reStockUpdCnt++;
                }
            } else {
                insertReStockAnnounceGoods(reStockAnnounceEntity, currentTime, HTypeReStockStatus.NO_RESTOCK);
                reStockMemberUpdateDtoList.add(createUpdateMemberInfo(reStockAnnounceEntity, goodsEntity,
                                                                      HTypeReStockStatus.NO_RESTOCK
                                                                     ));
                reStockUpdCnt++;
            }
        }
        return goodsReStockStatus;
    }

    private void handleNonFirstReStockAnnounceEntity(WebApiGetReStockResponseDetailDto info,
                                                     ReStockAnnounceEntity reStockAnnounceEntity,
                                                     GoodsEntity goodsEntity,
                                                     HTypeReStockStatus goodsReStockStatus,
                                                     List<ReStockMemberUpdateDto> reStockMemberUpdateDtoList) {
        if (Integer.parseInt(info.getStockQuantity().toString()) > 0) {
            if (!HTypeReStockStatus.RESTOCK.equals(goodsReStockStatus)) {
                reStockMemberUpdateDtoList.add(
                                createUpdateMemberInfo(reStockAnnounceEntity, goodsEntity, HTypeReStockStatus.RESTOCK));
                reStockUpdCnt++;
            }
        } else {
            if (!HTypeReStockStatus.NO_RESTOCK.equals(goodsReStockStatus)) {
                reStockMemberUpdateDtoList.add(createUpdateMemberInfo(reStockAnnounceEntity, goodsEntity,
                                                                      HTypeReStockStatus.NO_RESTOCK
                                                                     ));
                reStockUpdCnt++;
            }
        }
    }

    /**
     * 入荷お知らせ商品情報更新
     *
     * @param reStockAnnounceGoodsEntity 入荷お知らせ商品Entity
     * @param currentTime バッチ開始時間
     * @param reStockStatus 入荷状態
     */
    protected void updateReStockAnnounceGoods(ReStockAnnounceGoodsEntity reStockAnnounceGoodsEntity,
                                              Timestamp currentTime,
                                              HTypeReStockStatus reStockStatus) {
        reStockAnnounceGoodsEntity.setReStockStatus(reStockStatus);
        if (HTypeReStockStatus.RESTOCK.equals(reStockStatus)) {
            reStockAnnounceGoodsEntity.setReStockTime(currentTime);
        } else {
            reStockAnnounceGoodsEntity.setReStockTime(null);
        }
        reStockAnnounceGoodsEntity.setUpdateTime(currentTime);
        reStockAnnounceGoodsDao.update(reStockAnnounceGoodsEntity);
    }

    /**
     * 入荷お知らせ商品情報登録
     *
     * @param reStockAnnounceEntity 入荷お知らせEntity
     * @param currentTime バッチ開始時間
     * @param reStockStatus 入荷状態
     */
    protected void insertReStockAnnounceGoods(ReStockAnnounceEntity reStockAnnounceEntity,
                                              Timestamp currentTime,
                                              HTypeReStockStatus reStockStatus) {
        ReStockAnnounceGoodsEntity reStockAnnounceGoodsEntity = new ReStockAnnounceGoodsEntity();
        reStockAnnounceGoodsEntity.setGoodsSeq(reStockAnnounceEntity.getGoodsSeq());
        reStockAnnounceGoodsEntity.setReStockStatus(reStockStatus);
        if (HTypeReStockStatus.RESTOCK.equals(reStockStatus)) {
            reStockAnnounceGoodsEntity.setReStockTime(currentTime);
        } else {
            reStockAnnounceGoodsEntity.setReStockTime(null);
        }
        reStockAnnounceGoodsEntity.setRegistTime(currentTime);
        reStockAnnounceGoodsEntity.setUpdateTime(currentTime);
        reStockAnnounceGoodsDao.insert(reStockAnnounceGoodsEntity);
    }

    /**
     * 入荷お知らせメール情報登録
     *
     * @param reStockMemberUpdateDto 入荷結果会員情報更新DTO
     * @param currentTime バッチ開始時間
     */
    protected void insertReStockAnnounceMail(ReStockMemberUpdateDto reStockMemberUpdateDto, Timestamp currentTime) {
        // 配信情報登録
        ReStockAnnounceMailEntity reStockAnnounceMailNewEntity =
                        reStockAnnounceMailDao.getNewEntity(reStockMemberUpdateDto.getGoodsSeq(),
                                                            reStockMemberUpdateDto.getMemberInfoSeq()
                                                           );
        Integer versionNo;
        if (reStockAnnounceMailNewEntity != null) {
            if (HTypeMailDeliveryStatus.UNDELIVERED.equals(reStockAnnounceMailNewEntity.getDeliveryStatus())) {
                // 最新が未配信の場合、登録なし
                return;
            }
            versionNo = reStockAnnounceMailNewEntity.getVersionNo() + 1;
        } else {
            versionNo = 1;
        }
        ReStockAnnounceMailEntity reStockAnnounceMailEntity = new ReStockAnnounceMailEntity();
        reStockAnnounceMailEntity.setGoodsSeq(reStockMemberUpdateDto.getGoodsSeq());
        reStockAnnounceMailEntity.setMemberInfoSeq(reStockMemberUpdateDto.getMemberInfoSeq());
        reStockAnnounceMailEntity.setVersionNo(versionNo);
        reStockAnnounceMailEntity.setDeliveryId(null);
        reStockAnnounceMailEntity.setDeliveryStatus(HTypeMailDeliveryStatus.UNDELIVERED);
        reStockAnnounceMailEntity.setDeliveryTime(null);
        reStockAnnounceMailEntity.setRegistTime(currentTime);
        reStockAnnounceMailEntity.setUpdateTime(currentTime);
        reStockAnnounceMailDao.insert(reStockAnnounceMailEntity);
    }

    /**
     * 会員情報更新データ作成
     *
     * @param reStockAnnounceEntity 入荷お知らせEntity
     * @param goodsEntity 商品Entity
     * @param reStockStatus 入荷状態
     * @return 入荷結果会員情報更新DTO
     */
    protected ReStockMemberUpdateDto createUpdateMemberInfo(ReStockAnnounceEntity reStockAnnounceEntity,
                                                            GoodsEntity goodsEntity,
                                                            HTypeReStockStatus reStockStatus) {

        ReStockMemberUpdateDto reStockMemberUpdateDto = ApplicationContextUtility.getBean(ReStockMemberUpdateDto.class);
        reStockMemberUpdateDto.setMemberInfoSeq(reStockAnnounceEntity.getMemberInfoSeq());
        reStockMemberUpdateDto.setGoodsSeq(reStockAnnounceEntity.getGoodsSeq());
        reStockMemberUpdateDto.setGoodsCode(goodsEntity.getGoodsCode());
        reStockMemberUpdateDto.setReStockStatus(reStockStatus);
        return reStockMemberUpdateDto;
    }

    /**
     * 商品公開判定
     *
     * @param goodsGroupEntity 商品グループEntity
     * @return true：公開 false：非公開
     */
    protected boolean chkGoodsOpenStatus(GoodsGroupEntity goodsGroupEntity) {
        // 商品系Helper取得
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);
        return goodsUtility.isGoodsOpen(goodsGroupEntity.getGoodsOpenStatusPC(), goodsGroupEntity.getOpenStartTimePC(),
                                        goodsGroupEntity.getOpenEndTimePC()
                                       );
    }

    /**
     * 商品販売判定
     *
     * @param goodsEntity 商品Entity
     * @return true：販売 false：非販売
     */
    protected boolean chkGoodsSaleStatus(GoodsEntity goodsEntity) {
        // 商品系Helper取得
        GoodsUtility goodsUtility = ApplicationContextUtility.getBean(GoodsUtility.class);
        return goodsUtility.isGoodsSales(goodsEntity.getSaleStatusPC(), goodsEntity.getSaleStartTimePC(),
                                         goodsEntity.getSaleEndTimePC()
                                        );
    }

    /**
     * 会員メール希望判定
     *
     * @param memberInfoSeq 会員SEQ
     * @param skipNoMailRequiredCustomerNoList メール不要会員リスト
     * @return true：希望しない false：希望する
     */
    protected boolean sendMailPermit(Integer memberInfoSeq, List<Integer> skipNoMailRequiredCustomerNoList) {
        // 含まれている場合メール不要
        return skipNoMailRequiredCustomerNoList.contains(memberInfoSeq);
    }

    /**
     * ログイン可否判定
     *
     * @param memberInfoSeq 会員SEQ
     * @param skipCantLoginCustomerNoList オンラインログイン不可の会員リスト
     * @return ログイン可否 true：ログイン不可 false：ログイン可
     */
    protected boolean memberLoginAdvisability(Integer memberInfoSeq, List<Integer> skipCantLoginCustomerNoList) {

        // 含まれている場合ログイン不可
        return skipCantLoginCustomerNoList.contains(memberInfoSeq);
    }

    /**
     * 処理結果メールを送信する
     *
     * @param detail 処理結果
     */
    protected void sendResultMail(String detail) {

        LOGGER.debug("処理結果メールの送信処理を開始します。");
        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);
            Map<String, Object> mailContentsMap = new HashMap<>();

            // メール本文の設定
            Map<String, String> valueMap = new HashMap<>();
            // バッチ名
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_STOCK_DATA_IMPORT.getLabel());
            // 処理結果
            valueMap.put("RESULT", "処理結果：" + batchProcessResult);
            // 処理結果詳細
            valueMap.put("DETAIL", detail);

            mailContentsMap.put("admin", valueMap);
            // メール送信実施
            mailDto.setMailTemplateType(HTypeMailTemplateType.BATCH_STOCK_DATA_IMPORT_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject(SYSTEM_NAME + HTypeBatchName.BATCH_STOCK_DATA_IMPORT.getLabel() + "報告");
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);
            LOGGER.debug("処理結果メールを送信しました。");
        } catch (Exception e) {
            LOGGER.error("処理結果メールの送信に失敗しました。", e);
        }

        LOGGER.debug("処理結果メールの送信処理を終了しました。");
    }

    /**
     * 管理者向けエラー通知メールを送信する。
     *
     * @param exceptionInfo エラー詳細
     * @return 成否
     */
    protected boolean sendAdministratorErrorMail(final String exceptionInfo) {
        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            final Map<String, String> valueMap = new HashMap<>();

            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_STOCK_DATA_IMPORT.getLabel());
            StringBuilder result = new StringBuilder();
            result.append("処理結果：");
            result.append(UPDATE_RESULT_ERROR);
            result.append("\n\n予期せぬエラーが発生したため異常終了しています。");
            result.append("\n[ Exception内容 ]");
            result.append("\n");
            result.append(exceptionInfo);
            valueMap.put("RESULT", result.toString());

            mailContentsMap.put("error", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.BATCH_STOCK_DATA_IMPORT_ERROR_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject(SYSTEM_NAME + HTypeBatchName.BATCH_STOCK_DATA_IMPORT.getLabel() + "報告[*]");
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);

            LOGGER.info("管理者へエラー通知メールを送信しました。");

            return true;

        } catch (Exception e) {
            LOGGER.warn("管理者へのエラー通知メール送信に失敗しました。", e);

            return false;
        }
    }

    /**
     * 処理結果異常終了内容生成
     *
     * @param exceptionName 発生したException
     * @param recoveryMethod リカバリー方法
     */
    public boolean createFailedMailDetail(String exceptionName, String recoveryMethod) {
        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            final Map<String, String> valueMap = new HashMap<>();

            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_STOCK_DATA_IMPORT.getLabel());
            StringBuilder result = new StringBuilder();
            result.append("処理結果：");
            result.append(UPDATE_RESULT_ERROR);
            result.append("\n\n予期せぬエラーが発生したため異常終了しています。");
            result.append("\n[ Exception内容 ]");
            result.append("\n" + exceptionName);
            result.append("\n\n【リカバリ方法】");
            result.append("\n[" + recoveryMethod + "]");
            valueMap.put("RESULT", result.toString());

            mailContentsMap.put("error", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.BATCH_STOCK_DATA_IMPORT_API_ERROR_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject(SYSTEM_NAME + HTypeBatchName.BATCH_STOCK_DATA_IMPORT.getLabel() + "報告[*]");
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);

            LOGGER.info("管理者へエラー通知メールを送信しました。");

            return true;

        } catch (Exception e) {
            LOGGER.warn("管理者へのエラー通知メール送信に失敗しました。", e);

            return false;
        }

    }
}
