/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */
package jp.co.hankyuhanshin.itec.hitmall.batch.offline.saledataimport.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoopId;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailDeliveryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFavoriteSaleStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleAnnounceWatchFlg;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTopSaleAnnounceFlg;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.GoodsGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.sale.SaleGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.favorite.FavoriteDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.saleannounce.SaleAnnounceMailDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.sale.SaleMemberUpdateDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSaleResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSaleResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSaleRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.coop.CoopDateHistoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.sale.SaleGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.favorite.FavoriteEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.saleannounce.SaleAnnounceMailEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.coop.CoopDateHistoryGetUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.MemberInfoUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.NoMailRequiredMemberInfoLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo.loginadvisability.LoginAdvisabilityGetCanNotLoginMemberLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetSaleLogic;
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

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * セール情報取得バッチクラス
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public class SaleDataImportBatch extends AbstractBatch {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleDataImportBatch.class);

    /**
     * 処理結果
     */
    protected String batchProcessResult;

    /**
     * 基幹連携日時履歴取得・更新ロジック
     */
    private final CoopDateHistoryGetUpdateLogic coopDateHistoryGetUpdateLogic;

    /**
     * お気に入りDao
     */
    FavoriteDao favoriteDao;

    /**
     * セール商品Dao
     */
    SaleGoodsDao saleGoodsDao;

    /**
     * セールお知らせメールDao
     */
    SaleAnnounceMailDao saleAnnounceMailDao;

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
     * 商品セール情報取得WEB-API連携ロジック
     */
    private final WebApiGetSaleLogic webApiGetSaleLogic;

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

    // セール情報更新件数
    Integer saleUpdCnt = 0;

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
    public final static String API_ERROR_RECOVERY_MESSAGE = "【リカバリ】エラー原因解消後、 セール情報取得バッチを再実行してください。";

    /**
     * 処理結果メール詳細メッセージ
     */
    public boolean mailMessageDetail;

    /**
     * 処理名(セール情報取得バッチ)
     */
    public final static String PROCESS_NAME_SALE_DATA_IMPORT = "セール情報取得";

    /**
     * Creates a new SaleDataImportBatch object.
     */
    public SaleDataImportBatch(Environment environment) {
        super();
        this.coopDateHistoryGetUpdateLogic = ApplicationContextUtility.getBean(CoopDateHistoryGetUpdateLogic.class);
        this.favoriteDao = ApplicationContextUtility.getBean(FavoriteDao.class);
        this.saleGoodsDao = ApplicationContextUtility.getBean(SaleGoodsDao.class);
        this.saleAnnounceMailDao = ApplicationContextUtility.getBean(SaleAnnounceMailDao.class);
        this.memberInfoGetLogic = ApplicationContextUtility.getBean(MemberInfoGetLogic.class);
        this.memberInfoUpdateLogic = ApplicationContextUtility.getBean(MemberInfoUpdateLogic.class);
        this.noMailRequiredMemberInfoLogic = ApplicationContextUtility.getBean(NoMailRequiredMemberInfoLogic.class);
        this.loginAdvisabilityGetCanNotLoginMemberLogic =
                        ApplicationContextUtility.getBean(LoginAdvisabilityGetCanNotLoginMemberLogic.class);
        this.goodsDao = ApplicationContextUtility.getBean(GoodsDao.class);
        this.goodsGroupDao = ApplicationContextUtility.getBean(GoodsGroupDao.class);
        this.webApiGetSaleLogic = ApplicationContextUtility.getBean(WebApiGetSaleLogic.class);
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.exitMessageUtil = new BatchExitMessageUtil();

        this.mailSetting = new InstantMailSetting(environment.getProperty("mail.setting.sale.data.import.smtp.server"),
                                                  environment.getProperty("mail.setting.sale.data.import.mail.from"),
                                                  null, null, Collections.singletonList(
                        environment.getProperty("mail.setting.sale.data.import.mail.receivers"))
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

        // セール通知件数
        Integer saleNoticeCnt = 0;
        // セール通知登録件数
        Integer saleNoticeInsCnt = 0;
        // セール通知削除件数
        Integer saleNoticeDelCnt = 0;
        // 対象会員件数
        Integer saleNoticeCustomerCnt = 0;
        // 商品コードスキップリスト
        List<String> skipGoodsCodeList = new ArrayList<>();
        String exceptionName;

        WebApiGetSaleResponseDto resDto = null;
        try {

            // 基幹連携日時履歴テーブルから前回実行日時を取得
            CoopDateHistoryEntity coopDateHistoryEntity =
                            coopDateHistoryGetUpdateLogic.execute(HTypeCoopId.GOODS_SALE_GET_COOP.getValue());

            // セール情報取得
            // 商品セール情報取得リクエストDto
            WebApiGetSaleRequestDto reqDto = ApplicationContextUtility.getBean(WebApiGetSaleRequestDto.class);
            // 商品セール情報取得API用引数をセット
            reqDto.setDesignationDate(coopDateHistoryEntity.getLastCoopDate());
            // 商品セール情報取得WEB-API実行
            resDto = (WebApiGetSaleResponseDto) webApiGetSaleLogic.execute(reqDto);

            // 連携失敗
            if (resDto == null) {
                throw new RuntimeException("商品セール情報取得連携に失敗しました。");
            }

            if (resDto.getInfo() != null) {
                // 取得結果が不正なら処理終了
                if (!"0".equals(resDto.getResult().getStatus())) {
                    throw new Exception("商品セール情報の取得に失敗しました。 ステータスコード：" + resDto.getResult().getStatus());
                }

                // 商品セール情報取得処理終了
                LOGGER.debug("商品セール情報取得WEB-APIの実行が正常終了しました。");

                List<SaleGoodsEntity> updSaleGoodsEntityList = new ArrayList<>();
                List<GoodsEntity> goodsEntityList = new ArrayList<>();

                for (WebApiGetSaleResponseDetailDto info : resDto.getInfo()) {
                    GoodsEntity goodsEntity = goodsDao.getGoodsByCode(getShopSeq(), info.getGoodsCode());
                    // 商品番号が存在しない場合、対象商品番号を控えて次商品へ
                    if (goodsEntity == null) {
                        skipGoodsCodeList.add(info.getGoodsCode());
                        continue;
                    }
                    goodsEntityList.add(goodsEntity);
                    Integer goodsSeq = goodsEntity.getGoodsSeq();
                    // セール情報の更新(基幹から返却されるセール情報は原則１商品１件のため、制御無しとして後勝ち更新)
                    SaleGoodsEntity saleGoodsEntity = saleGoodsDao.getEntity(goodsSeq);
                    SaleGoodsEntity tmpSaleGoodsEntity;
                    if (saleGoodsEntity != null) {

                        // セール情報が前回から変わっていない場合スキップ
                        if (saleGoodsEntity.getSaleCd().equals(info.getSaleCode())) {
                            continue;
                        }

                        if (HTypeFavoriteSaleStatus.SALE.getValue().equals(info.getSaleStatus())) {
                            tmpSaleGoodsEntity = updateSaleGoods(saleGoodsEntity, info, currentTime,
                                                                 HTypeFavoriteSaleStatus.SALE
                                                                );
                            if (tmpSaleGoodsEntity == null) {
                                throw new SQLException();
                            }
                        } else {
                            tmpSaleGoodsEntity = updateSaleGoods(saleGoodsEntity, info, currentTime,
                                                                 HTypeFavoriteSaleStatus.NO_SALE
                                                                );
                            if (tmpSaleGoodsEntity == null) {
                                throw new SQLException();
                            }
                        }
                    } else {
                        if (HTypeFavoriteSaleStatus.SALE.getValue().equals(info.getSaleStatus())) {
                            tmpSaleGoodsEntity =
                                            insertSaleGoods(goodsSeq, info, currentTime, HTypeFavoriteSaleStatus.SALE);
                            if (tmpSaleGoodsEntity == null) {
                                throw new SQLException();
                            }
                        } else {
                            tmpSaleGoodsEntity = insertSaleGoods(goodsSeq, info, currentTime,
                                                                 HTypeFavoriteSaleStatus.NO_SALE
                                                                );
                            if (tmpSaleGoodsEntity == null) {
                                throw new SQLException();
                            }
                        }
                    }
                    saleUpdCnt++;
                    updSaleGoodsEntityList.add(tmpSaleGoodsEntity);
                }

                Set<Integer> updGoodsSeqList = new HashSet<>();
                for (SaleGoodsEntity saleGoodsEntityList : updSaleGoodsEntityList) {
                    updGoodsSeqList.add(saleGoodsEntityList.getGoodsSeq());
                }

                // セールお知らせメール配信対象の会員を取得
                List<FavoriteEntity> favoriteEntityList =
                                favoriteDao.getFavoriteEntityListByGoodsSeqList(new ArrayList<>(updGoodsSeqList));
                List<SaleMemberUpdateDto> saleMemberUpdateDtoList = new ArrayList<>();
                Set<Integer> uniqueMemberInfoSeqList = new HashSet<>();
                for (FavoriteEntity favoriteEntity : favoriteEntityList) {
                    uniqueMemberInfoSeqList.add(favoriteEntity.getMemberInfoSeq());
                    // 会員更新用のリストを作成
                    for (SaleGoodsEntity saleGoodsEntity : updSaleGoodsEntityList) {
                        if (favoriteEntity.getGoodsSeq().equals(saleGoodsEntity.getGoodsSeq())) {
                            saleMemberUpdateDtoList.add(
                                            createUpdateMemberInfo(favoriteEntity, saleGoodsEntity.getSaleStatus()));
                        }
                    }
                }

                // メール不要の会員、ログイン不可の会員を取得
                List<Integer> memberInfoSeqList = new ArrayList<>(uniqueMemberInfoSeqList);
                List<Integer> skipNoMailRequiredMemberInfoSeqList =
                                noMailRequiredMemberInfoLogic.getNoMailRequiredMemberInfoSeqLogic(memberInfoSeqList);
                List<Integer> skipCantLoginMemberInfoSeqList =
                                loginAdvisabilityGetCanNotLoginMemberLogic.getLoginAdvisabilityGetCanNotLoginMemberInfoSeqLogic(
                                                memberInfoSeqList);

                for (Integer uniqueMemberInfoseq : uniqueMemberInfoSeqList) {
                    boolean updMemberFlg = false;
                    HTypeTopSaleAnnounceFlg topSaleAnnounceFlg = HTypeTopSaleAnnounceFlg.OFF;
                    for (SaleMemberUpdateDto saleMemberUpdateDto : saleMemberUpdateDtoList) {
                        if (uniqueMemberInfoseq.equals(saleMemberUpdateDto.getMemberInfoSeq())) {
                            GoodsEntity goodsEntity = goodsEntityList.stream()
                                                                     .filter(tmpGoodsEntity -> saleMemberUpdateDto.getGoodsSeq()
                                                                                                                  .equals(tmpGoodsEntity.getGoodsSeq()))
                                                                     .collect(Collectors.toList())
                                                                     .get(0);
                            GoodsGroupEntity goodsGroupEntity = goodsGroupDao.getEntity(goodsEntity.getGoodsGroupSeq());
                            // 商品公開＆販売中
                            if (chkGoodsOpenStatus(goodsGroupEntity) && chkGoodsSaleStatus(goodsEntity)) {
                                updMemberFlg = true;
                                // 1件でもセールがある場合フラグを立てる
                                if (HTypeFavoriteSaleStatus.SALE.equals(saleMemberUpdateDto.getSaleStatus())) {
                                    topSaleAnnounceFlg = HTypeTopSaleAnnounceFlg.ON;

                                    // メール希望会員
                                    if (!sendMailPermit(uniqueMemberInfoseq, skipNoMailRequiredMemberInfoSeqList)
                                        && !memberLoginAdvisability(
                                                    uniqueMemberInfoseq, skipCantLoginMemberInfoSeqList)) {
                                        try {
                                            // 配信情報登録
                                            updateSaleAnnounceMail(saleMemberUpdateDto, currentTime);
                                            saleNoticeCustomerCnt++;
                                        } catch (Exception e) {
                                            LOGGER.error("例外処理が発生しました", e);
                                            LOGGER.info("[" + SaleDataImportBatch.class.getName() + "]" + new Timestamp(
                                                            System.currentTimeMillis()) + " rollbackが終了しました。処理を終了します。");
                                            // エラーがあった場合は管理者にメール送信
                                            sendAdministratorErrorMail(e.getClass().getName());
                                            executionContext.put(BatchExitMessageUtil.exitMsg,
                                                                 exitMessageUtil.convertObjectToJson(
                                                                                 new BatchExitMessageDto(
                                                                                                 String.valueOf(0),
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
                    }

                    // 会員情報更新
                    if (updMemberFlg) {
                        MemberInfoEntity memberInfoEntity = memberInfoGetLogic.getEntityForUpdate(uniqueMemberInfoseq);
                        memberInfoEntity.setTopSaleAnnounceFlg(topSaleAnnounceFlg);
                        memberInfoEntity.setSaleAnnounceWatchFlg(HTypeSaleAnnounceWatchFlg.UNREAD);
                        try {
                            memberInfoUpdateLogic.execute(memberInfoEntity);
                        } catch (Exception e) {
                            LOGGER.error("例外処理が発生しました", e);
                            LOGGER.info("[" + SaleDataImportBatch.class.getName() + "]" + new Timestamp(
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
                        saleNoticeCnt++;
                        if (HTypeTopSaleAnnounceFlg.ON.equals(topSaleAnnounceFlg)) {
                            saleNoticeInsCnt++;
                        } else {
                            saleNoticeDelCnt++;
                        }
                    }
                }
            }
            // 基幹連携日時履歴更新
            coopDateHistoryEntity.setLastCoopDate(currentTime);
            coopDateHistoryEntity.setUpdateTime(currentTime);
            try {
                coopDateHistoryGetUpdateLogic.execute(coopDateHistoryEntity);
            } catch (Exception e) {
                LOGGER.error("例外処理が発生しました", e);
                LOGGER.info("[" + SaleDataImportBatch.class.getName() + "]" + new Timestamp(System.currentTimeMillis())
                            + " rollbackが終了しました。処理を終了します。");
                // エラーがあった場合は管理者にメール送信
                sendAdministratorErrorMail(e.getClass().getName());
                executionContext.put(
                                BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                                new BatchExitMessageDto(String.valueOf(0),
                                                                        this.getReportString().toString()
                                                )));
                return RepeatStatus.FINISHED;
            }

            String saleUpdMsg = "セール情報更新件数：" + saleUpdCnt + "件";
            StringBuilder skipMsg = new StringBuilder("\n\nスキップ件数：" + skipGoodsCodeList.size() + "件");
            for (String goodsCode : skipGoodsCodeList) {
                skipMsg.append("\n - 商品コード：").append(goodsCode);
            }
            StringBuilder skipGoodsCodeListMsg = null;
            for (String goodsCode : skipGoodsCodeList) {
                if (skipGoodsCodeListMsg == null) {
                    skipGoodsCodeListMsg = new StringBuilder("\n\n▼エラー内容\n商品コード：" + goodsCode + "が存在しないためスキップします");
                } else {
                    skipGoodsCodeListMsg.append("\n商品コード：").append(goodsCode).append("が存在しないためスキップします");
                }
            }
            String saleNoticeMsg = "\n\n\nセール通知更新：" + saleNoticeCnt + "件";
            String saleNoticeInsMsg = "\n - セール通知登録件数：" + saleNoticeInsCnt + "件";
            String saleNoticeDelMsg = "\n - セール通知削除件数：" + saleNoticeDelCnt + "件";
            String saleNoticeCustomerMsg = "\n\n対象会員件数：" + saleNoticeCustomerCnt + "件";

            StringBuilder mailReport = new StringBuilder();
            mailReport.append(saleUpdMsg);
            mailReport.append(skipMsg);
            if (skipGoodsCodeListMsg != null) {
                mailReport.append(skipGoodsCodeListMsg);
            }
            mailReport.append(saleNoticeMsg);
            mailReport.append(saleNoticeInsMsg);
            mailReport.append(saleNoticeDelMsg);
            mailReport.append(saleNoticeCustomerMsg);

            // 処理結果成功メール送信
            batchProcessResult = UPDATE_RESULT_SUCCESS;
            // メール送信メソッド
            sendResultMail(mailReport.toString());

            this.report(mailReport.toString());
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(String.valueOf(saleUpdCnt),
                                                                    this.getReportString().toString()
                                            )));

            LOGGER.debug("セール情報取得バッチ - 正常に終了しました。");
        } catch (RuntimeException e) {
            exceptionName = "商品セール情報取得" + API_ERROR_TIMEOUT_MESSAGE;
            mailMessageDetail = createFailedMailDetail(exceptionName, PROCESS_NAME_SALE_DATA_IMPORT,
                                                       API_ERROR_TIMEOUT_RECOVERY_MESSAGE
                                                      );
            // メール送信
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(String.valueOf(0),
                                                                    this.getReportString().toString()
                                            )));
            throw e;
        } catch (SQLException e) {
            LOGGER.error("例外処理が発生しました", e);
            LOGGER.info("[" + SaleDataImportBatch.class.getName() + "]" + new Timestamp(System.currentTimeMillis())
                        + " rollbackが終了しました。処理を終了します。");
            // エラーがあった場合は管理者にメール送信
            sendAdministratorErrorMail(e.getClass().getName());
            executionContext.put(
                            BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                                            new BatchExitMessageDto(String.valueOf(0),
                                                                    this.getReportString().toString()
                                            )));
            throw e;
        } catch (Exception e) {
            exceptionName = "商品セール情報取得" + "/処理結果ステータス：{" + resDto.getResult().getStatus() + "}メッセージ：{"
                            + resDto.getResult().getMessage() + "}";
            mailMessageDetail = createFailedMailDetail(exceptionName, PROCESS_NAME_SALE_DATA_IMPORT,
                                                       API_ERROR_RECOVERY_MESSAGE
                                                      );
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

    /**
     * セール商品情報更新
     *
     * @param saleGoodsEntity セール商品Entity
     * @param info WEB-API連携取得結果DTOクラス
     * @param currentTime バッチ開始時間
     * @param saleStatus セール状態
     */
    protected SaleGoodsEntity updateSaleGoods(SaleGoodsEntity saleGoodsEntity,
                                              WebApiGetSaleResponseDetailDto info,
                                              Timestamp currentTime,
                                              HTypeFavoriteSaleStatus saleStatus) {
        saleGoodsEntity.setSaleStatus(saleStatus);
        saleGoodsEntity.setPreSaleCd(saleGoodsEntity.getSaleCd());
        saleGoodsEntity.setSaleCd(info.getSaleCode());
        saleGoodsEntity.setSaleFrom(info.getSaleFrom());
        saleGoodsEntity.setSaleTo(info.getSaleTo());
        saleGoodsEntity.setUpdateTime(currentTime);
        saleGoodsDao.update(saleGoodsEntity);
        return saleGoodsEntity;
    }

    /**
     * セール商品情報登録
     * @param goodsSeq 商品SEQ
     * @param info WEB-API連携取得結果DTOクラス
     * @param currentTime バッチ開始時間
     * @param saleStatus セール状態
     */
    protected SaleGoodsEntity insertSaleGoods(Integer goodsSeq,
                                              WebApiGetSaleResponseDetailDto info,
                                              Timestamp currentTime,
                                              HTypeFavoriteSaleStatus saleStatus) {
        SaleGoodsEntity saleGoodsEntity = new SaleGoodsEntity();
        saleGoodsEntity.setGoodsSeq(goodsSeq);
        saleGoodsEntity.setSaleStatus(saleStatus);
        saleGoodsEntity.setPreSaleCd(null);
        saleGoodsEntity.setSaleCd(info.getSaleCode());
        saleGoodsEntity.setSaleFrom(info.getSaleFrom());
        saleGoodsEntity.setSaleTo(info.getSaleTo());
        saleGoodsEntity.setRegistTime(currentTime);
        saleGoodsEntity.setUpdateTime(currentTime);
        saleGoodsDao.insert(saleGoodsEntity);
        return saleGoodsEntity;
    }

    /**
     * セールお知らせメール情報更新
     *
     * @param saleMemberUpdateDto セール結果会員情報更新DTO
     * @param currentTime バッチ開始時間
     */
    protected void updateSaleAnnounceMail(SaleMemberUpdateDto saleMemberUpdateDto, Timestamp currentTime)
                    throws Exception {
        // メール配信情報登録
        SaleAnnounceMailEntity saleAnnounceMailEntity = saleAnnounceMailDao.getEntity(saleMemberUpdateDto.getGoodsSeq(),
                                                                                      saleMemberUpdateDto.getMemberInfoSeq()
                                                                                     );
        if (saleAnnounceMailEntity == null) {
            saleAnnounceMailEntity = new SaleAnnounceMailEntity();
            saleAnnounceMailEntity.setGoodsSeq(saleMemberUpdateDto.getGoodsSeq());
            saleAnnounceMailEntity.setMemberInfoSeq(saleMemberUpdateDto.getMemberInfoSeq());
            saleAnnounceMailEntity.setDeliveryId(null);
            saleAnnounceMailEntity.setDeliveryStatus(HTypeMailDeliveryStatus.UNDELIVERED);
            saleAnnounceMailEntity.setDeliveryTime(null);
            saleAnnounceMailEntity.setRegistTime(currentTime);
            saleAnnounceMailEntity.setUpdateTime(currentTime);
            saleAnnounceMailDao.insert(saleAnnounceMailEntity);
        } else {
            saleAnnounceMailEntity.setDeliveryId(null);
            saleAnnounceMailEntity.setDeliveryStatus(HTypeMailDeliveryStatus.UNDELIVERED);
            saleAnnounceMailEntity.setDeliveryTime(null);
            saleAnnounceMailEntity.setUpdateTime(currentTime);
            saleAnnounceMailDao.update(saleAnnounceMailEntity);
        }
    }

    /**
     * 会員情報更新データ作成
     *
     * @param favoriteEntity お気に入りEntity
     * @param saleStatus セール状態
     * @return セール結果会員情報更新DTO
     */
    protected SaleMemberUpdateDto createUpdateMemberInfo(FavoriteEntity favoriteEntity,
                                                         HTypeFavoriteSaleStatus saleStatus) {

        SaleMemberUpdateDto saleMemberUpdateDto = ApplicationContextUtility.getBean(SaleMemberUpdateDto.class);
        saleMemberUpdateDto.setMemberInfoSeq(favoriteEntity.getMemberInfoSeq());
        saleMemberUpdateDto.setGoodsSeq(favoriteEntity.getGoodsSeq());
        saleMemberUpdateDto.setSaleStatus(saleStatus);
        return saleMemberUpdateDto;
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
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_SALE_DATA_IMPORT.getLabel());
            // 処理結果
            valueMap.put("RESULT", "処理結果：" + batchProcessResult);
            // 処理結果詳細
            valueMap.put("DETAIL", detail);

            mailContentsMap.put("admin", valueMap);
            // メール送信実施
            mailDto.setMailTemplateType(HTypeMailTemplateType.BATCH_SALE_DATA_IMPORT_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject(SYSTEM_NAME + HTypeBatchName.BATCH_SALE_DATA_IMPORT.getLabel() + "報告");
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

            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_SALE_DATA_IMPORT.getLabel());
            StringBuilder result = new StringBuilder();
            result.append("処理結果：");
            result.append(UPDATE_RESULT_ERROR);
            result.append("\n\n予期せぬエラーが発生したため異常終了しています。");
            result.append("\n[ Exception内容 ]");
            result.append("\n");
            result.append(exceptionInfo);
            valueMap.put("RESULT", result.toString());

            mailContentsMap.put("error", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.BATCH_SALE_DATA_IMPORT_ERROR_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject(SYSTEM_NAME + HTypeBatchName.BATCH_SALE_DATA_IMPORT.getLabel() + "報告[*]");
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
     * @param processName 処理名
     * @param recoveryMethod リカバリー方法
     */
    public boolean createFailedMailDetail(String exceptionName, String processName, String recoveryMethod) {
        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            final Map<String, String> valueMap = new HashMap<>();

            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_SALE_DATA_IMPORT.getLabel());
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

            mailDto.setMailTemplateType(HTypeMailTemplateType.BATCH_SALE_DATA_IMPORT_API_ERROR_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject(SYSTEM_NAME + HTypeBatchName.BATCH_SALE_DATA_IMPORT.getLabel() + "報告[*]");
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
