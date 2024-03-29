// PDR Migrate Customization from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.offline.goods.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCoopId;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGroupPriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGroupSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeNewIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOutletIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReserveIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleControlType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSaleIconFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceIntegrityFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalePriceMarkDispFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goodsgroup.GoodsGroupDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetPriceRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetPriceResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetPriceResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSeriesPriceRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSeriesPriceResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetSeriesPriceResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.coop.CoopDateHistoryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.coop.CoopDateHistoryGetUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsEntityGetByGoodsCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupCodeGetByGoodsGroupSeqLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDetailsGetByCodeLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupDisplayGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetPriceLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetSeriesPriceLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.goods.group.GoodsGroupRegistUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelException;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
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
import org.thymeleaf.util.ListUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * PDR#036 商品価格の基幹連携<br/>
 * 商品価格更新バッチ<br/>
 * <pre>
 * 中間サーバのAPIをコール(WebAPI)し、基幹システムで変更された商品価格情報をHIT-MALLに取り込む。
 * </pre>
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
public class GoodsPriceUpdateBatch extends AbstractBatch {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsPriceUpdateBatch.class);

    /**
     * 処理結果正常終了メッセージ
     */
    private static final String UPDATE_RESULT_SUCCESS = "正常終了";

    /**
     * 処理結果異常終了メッセージ
     */
    private static final String UPDATE_RESULT_FAILED = "異常終了";

    /**
     * 処理結果更新失敗メッセージ
     */
    private static final String UPDATE_RESULT_ERROR = "【要対応】エラーあり";

    /**
     * システムエラーメッセージ
     */
    private static final String SYSTEM_ERR_MSG = "システムエラーが発生しました。システム担当者に問い合わせてください。";

    /**
     * 必須メッセージ
     */
    private static final String ISEMPTY_MSG = "は必須項目です。";

    /**
     * 桁数オーバーメッセージ
     */
    private static final String MAXLENGTH_MSG = "の桁数が不正です。";

    /**
     * 項目不正メッセージ
     */
    private static final String ITEM_ILLEGAL_MSG = "が不正です。";

    /**
     * 処理結果
     */
    protected String batchProcessResult;

    /**
     * 処理結果メール詳細メッセージ
     */
    protected String mailMessageDetail;

    /**
     * 処理中商品コード保持
     */
    protected String checkGoodsCode = null;

    /**
     * 処理中商品グループコード保持
     */
    protected String checkGoodsGroupCode = null;

    /**
     * 処理中差異商品グループコード保持
     */
    protected String checkRemainsGoodsGroupCode = null;

    /**
     * 商品テーブル更新処理件数
     */
    protected int goodsUpdateNumber = 0;

    /**
     * 商品グループ情報更新処理件数
     */
    protected int goodsGroupUpdateNumber = 0;

    /**
     * バッチ処理総件数
     */
    protected int batchProcessTotalCount = 0;

    /**
     * 商品グループコードリスト(商品価格情報より取得)
     */
    protected List<String> goodsGroupCodeListByGoodsPrice;

    /**
     * 商品グループコードリスト(商品グループ価格情報より取得)
     */
    protected List<String> goodsGroupCodeListByGoodsSeriesPrice;

    /**
     * 商品グループ更新情報保持用マップ
     */
    Map<String, GoodsGroupEntity> goodsGroupMap;

    /**
     * 商品グループ表示更新情報保持用マップ
     */
    Map<String, GoodsGroupDisplayEntity> goodsGroupDisplayMap;

    /**
     * 更新時に存在しなかった商品コード格納用リスト
     */
    protected List<String> failedGoodsCodeList;

    /**
     * 更新時に存在しなかった商品グループコード格納用リスト
     */
    protected List<String> failedGoodsGroupCodeList;

    /**
     * 商品更新エラーチェックリスト
     */
    private final List<String> failedGoodsList = new ArrayList<>();

    /**
     * 商品グループ更新エラーチェックリスト
     */
    private final List<String> failedGoodsGroupList = new ArrayList<>();

    /**
     * 商品エンティティ取得ロジック
     */
    private final GoodsEntityGetByGoodsCodeLogic goodsEntityGetByGoodsCodeLogic;

    /**
     * 商品グループエンティティ取得ロジック
     */
    private final GoodsGroupDetailsGetByCodeLogic goodsGroupDetailsGetByCodeLogic;

    /**
     * 商品グループ表示エンティティ取得ロジック
     */
    private final GoodsGroupDisplayGetLogic goodsGroupDisplayGetLogic;

    /**
     * 商品グループコード取得ロジック
     */
    private final GoodsGroupCodeGetByGoodsGroupSeqLogic goodsGroupCodeGetByGoodsGroupSeqLogic;

    /**
     * 基幹連携日時履歴取得・更新ロジック
     */
    private final CoopDateHistoryGetUpdateLogic coopDateHistoryGetUpdateLogic;

    /**
     * 商品価格情報取得WEB-API連携ロジック
     */
    private final WebApiGetPriceLogic webApiGetPriceLogic;

    /**
     * シリーズ商品価格情報取得WEB-API連携ロジック
     */
    private final WebApiGetSeriesPriceLogic webApiGetSeriesPriceLogic;

    /**
     * 商品グループ最高値、最安値更新サービス
     */
    private final GoodsGroupRegistUpdateService goodsGroupRegistUpdateService;

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
     * 商品価格更新サービス
     */
    private final GoodsPriceUpdateService goodsPriceUpdateService;

    /**
     * コンストラクタ
     */
    public GoodsPriceUpdateBatch(Environment environment) {
        goodsGroupCodeListByGoodsPrice = new ArrayList<>();
        goodsGroupCodeListByGoodsSeriesPrice = new ArrayList<>();
        goodsGroupMap = new HashMap<>();
        goodsGroupDisplayMap = new HashMap<>();
        failedGoodsCodeList = new ArrayList<>();
        failedGoodsGroupCodeList = new ArrayList<>();
        this.goodsEntityGetByGoodsCodeLogic = ApplicationContextUtility.getBean(GoodsEntityGetByGoodsCodeLogic.class);
        this.goodsGroupDetailsGetByCodeLogic = ApplicationContextUtility.getBean(GoodsGroupDetailsGetByCodeLogic.class);
        this.goodsGroupDisplayGetLogic = ApplicationContextUtility.getBean(GoodsGroupDisplayGetLogic.class);
        this.goodsGroupCodeGetByGoodsGroupSeqLogic =
                        ApplicationContextUtility.getBean(GoodsGroupCodeGetByGoodsGroupSeqLogic.class);
        this.coopDateHistoryGetUpdateLogic = ApplicationContextUtility.getBean(CoopDateHistoryGetUpdateLogic.class);
        this.webApiGetPriceLogic = ApplicationContextUtility.getBean(WebApiGetPriceLogic.class);
        this.webApiGetSeriesPriceLogic = ApplicationContextUtility.getBean(WebApiGetSeriesPriceLogic.class);
        this.goodsPriceUpdateService = ApplicationContextUtility.getBean(GoodsPriceUpdateService.class);
        this.goodsGroupRegistUpdateService = ApplicationContextUtility.getBean(GoodsGroupRegistUpdateService.class);
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.exitMessageUtil = new BatchExitMessageUtil();

        this.mailSetting = new InstantMailSetting(environment.getProperty("mail.setting.goodsprice.update.smtp.server"),
                                                  environment.getProperty("mail.setting.goodsprice.update.mail.from"),
                                                  null, null, Collections.singletonList(
                        environment.getProperty("mail.setting.goodsprice.update.mail.receivers"))
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
        String shopSeq = jobParameters.getString("shopSeq");
        if (!StringUtils.isEmpty(shopSeq)) {
            super.setShopSeq(Integer.valueOf(shopSeq));
        }

        try {

            // 前回実行日時取得処理開始
            LOGGER.debug("前回実行日時の取得処理を開始します。");

            // 前回実行日時取得(取得失敗でエラー)
            CoopDateHistoryEntity coopDateHistoryEntity =
                            coopDateHistoryGetUpdateLogic.execute(HTypeCoopId.GOODS_CHGINFO_GET_COOP.getValue());
            // 前回実行日時
            if (coopDateHistoryEntity == null) {
                throw new RuntimeException("前回実行日時の取得に失敗しました。");
            }
            Timestamp lastCoopDate = coopDateHistoryEntity.getLastCoopDate();

            // 前回実行日時取得処理終了
            LOGGER.debug("前回実行日時の取得処理が正常終了しました。");

            // 日付関連Helper取得
            DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

            // 現在日時を取得
            Timestamp currentTime = dateUtility.getCurrentTime();

            // 商品価格情報取得処理開始
            LOGGER.debug("商品価格情報取得WEB-APIの実行を開始します。");
            // 前回連携実行日時から商品価格情報を取得
            // 商品価格リクエストDto
            WebApiGetPriceRequestDto reqDto = ApplicationContextUtility.getBean(WebApiGetPriceRequestDto.class);
            // 前回実行日時をセット
            reqDto.setDesignationDate(lastCoopDate);
            // 商品価格情報取得WEB-API実行
            WebApiGetPriceResponseDto responseDto = (WebApiGetPriceResponseDto) webApiGetPriceLogic.execute(reqDto);

            // 連携失敗
            if (responseDto == null || responseDto.getResult() == null) {
                throw new RuntimeException("商品価格情報取得連携に失敗しました。");
            }
            // 取得結果が不正なら処理終了
            if (!responseDto.getResult().getStatus().equals("0")) {
                throw new RuntimeException("価格情報の取得に失敗しました。ステータスコード：" + responseDto.getResult().getStatus());
            }
            // 商品価格情報取得処理終了
            LOGGER.debug("商品価格情報取得WEB-APIの実行が正常終了しました。");

            // 商品価格情報から商品テーブルの更新を行う
            // 更新の過程で商品グループSeqを取得しておく
            updateGoodsInfo(responseDto.getInfo());

            // シリーズ商品価格情報取得処理開始
            LOGGER.debug("シリーズ商品価格情報取得WEB-APIの実行を開始します。");
            // 前回連携実行日時からシリーズ商品価格情報を取得
            // シリーズ商品価格リクエストDto
            WebApiGetSeriesPriceRequestDto reqSeriesPriceDto =
                            ApplicationContextUtility.getBean(WebApiGetSeriesPriceRequestDto.class);
            // 前回実行日時をセット
            reqSeriesPriceDto.setDesignationDate(lastCoopDate);
            // シリーズ商品価格情報取得WEB-API実行
            WebApiGetSeriesPriceResponseDto responseSeriesPriceDto =
                            (WebApiGetSeriesPriceResponseDto) webApiGetSeriesPriceLogic.execute(reqSeriesPriceDto);

            // 連携失敗
            if (responseSeriesPriceDto == null || responseSeriesPriceDto.getResult() == null) {
                throw new RuntimeException("シリーズ商品価格情報取得連携に失敗しました。");
            }
            // 取得結果が不正なら処理終了
            if (!responseSeriesPriceDto.getResult().getStatus().equals("0")) {
                throw new RuntimeException("シリーズ商品価格情報の取得に失敗しました。ステータスコード：" + responseDto.getResult().getStatus());
            }
            // シリーズ商品価格情報取得処理終了
            LOGGER.debug("シリーズ商品価格情報取得WEB-APIの実行が正常終了しました。");

            // シリーズ商品価格情報から商品グループSeqを取得する。
            // 商品グループ情報、商品グループ表示情報の更新用情報を取得しておく
            setGoodsGroupInfo(responseSeriesPriceDto.getInfo());

            // 処理対象が存在するかチェック
            if (!ListUtils.isEmpty(goodsGroupCodeListByGoodsSeriesPrice)) {

                // 商品グループ最高値・最安値、表示単価反映ループ処理開始
                LOGGER.debug("最高値・最安値、表示単価情報反映ループ処理を開始します。");
                LOGGER.debug("処理対象件数：" + goodsGroupCodeListByGoodsSeriesPrice.size());
                // 取得した商品グループSEQで商品テーブル内の最高値と最安値を取得
                // 商品グループSEQをキーにMAPから商品グループDtoを取得
                for (String goodsGroupCode : goodsGroupCodeListByGoodsSeriesPrice) {

                    // 処理中商品グループコード保持
                    checkGoodsGroupCode = goodsGroupCode;

                    GoodsGroupDto goodsGroupDto =
                                    goodsGroupDetailsGetByCodeLogic.execute(getShopSeq(), goodsGroupCode, null,
                                                                            HTypeSiteType.BACK, null, null
                                                                           );
                    goodsGroupCodeListByGoodsPrice.remove(goodsGroupCode);

                    // 商品グループ情報をシリーズ商品価格情報で上書き
                    goodsGroupDto.setGoodsGroupEntity(goodsGroupMap.get(goodsGroupCode));
                    // 商品グループ表示情報をシリーズ商品価格情報で上書き
                    goodsGroupDto.setGoodsGroupDisplayEntity(goodsGroupDisplayMap.get(goodsGroupCode));
                    // 最高値、最安値を計算
                    goodsGroupRegistUpdateService.calculateGoodsGroupPrice(goodsGroupDto);
                    try {
                        // 商品グループ・商品グループ表示テーブル更新
                        goodsPriceUpdateService.goodsGroupUpdate(goodsGroupDto);

                    } catch (Exception error) {
                        // 更新失敗
                        LOGGER.warn(SYSTEM_ERR_MSG, error);
                        failedGoodsGroupList.add(SYSTEM_ERR_MSG);
                        continue;
                    }
                    // 更新件数インクリメント
                    goodsGroupUpdateNumber++;
                }
                // 処理中商品グループコード保持終了
                checkGoodsGroupCode = null;
                // 商品グループ最高値・最安値、表示単価反映ループ処理終了
                LOGGER.debug("シリーズ商品価格情報反映ループ処理を正常終了します。");
            }

            // もし商品価格情報から取得した商品グループSEQとシリーズ商品価格情報から
            // 取得した商品グループSEQに差異があった場合は残っているSEQで最高値、最安値更新を行う
            if (!ListUtils.isEmpty(goodsGroupCodeListByGoodsPrice)) {

                // 再度シリーズ商品価格情報反映ループ処理開始
                LOGGER.debug("商品価格情報とシリーズ商品価格情報の共通情報に差異があったため、再度最高値・最安値、表示単価情報反映ループ処理を開始します。");
                LOGGER.debug("処理対象件数：" + goodsGroupCodeListByGoodsPrice.size());

                // 商品グループDtoを取得
                for (String goodsGroupRemainsCode : goodsGroupCodeListByGoodsPrice) {

                    // 処理中差異商品グループコード保持
                    checkRemainsGoodsGroupCode = goodsGroupRemainsCode;

                    GoodsGroupDto goodsGroupRemainsDto =
                                    goodsGroupDetailsGetByCodeLogic.execute(getShopSeq(), goodsGroupRemainsCode, null,
                                                                            HTypeSiteType.BACK, null, null
                                                                           );
                    // 最高値、最安値を計算
                    goodsGroupRegistUpdateService.calculateGoodsGroupPrice(goodsGroupRemainsDto);
                    try {
                        // 商品グループ・商品グループ表示テーブル更新
                        goodsPriceUpdateService.goodsGroupUpdate(goodsGroupRemainsDto);

                    } catch (Exception error) {
                        // 更新失敗
                        LOGGER.warn(SYSTEM_ERR_MSG, error);
                        failedGoodsGroupList.add(SYSTEM_ERR_MSG);
                        continue;
                    }
                    // 更新件数インクリメント
                    goodsGroupUpdateNumber++;
                }
                // 処理中差異商品グループコード保持終了
                checkRemainsGoodsGroupCode = null;
                // 再度シリーズ商品価格情報反映ループ処理再度実行終了
                LOGGER.debug("最高値・最安値、表示単価情報反映ループ処理の再実行を正常終了します。");
            }

            // 前回実行日時更新処理開始
            LOGGER.debug("前回実行日時の更新処理を開始します。");
            // 前回実行日時更新
            coopDateHistoryEntity.setLastCoopDate(currentTime);
            coopDateHistoryGetUpdateLogic.execute(coopDateHistoryEntity);
            // 前回実行日時更新処理終了
            LOGGER.debug("前回実行日時の更新処理を終了します。");

            // 処理結果成功メール送信
            batchProcessResult = UPDATE_RESULT_SUCCESS;
            // 処理結果失敗メール送信
            if (!failedGoodsList.isEmpty() || !failedGoodsGroupList.isEmpty()) {
                batchProcessResult = UPDATE_RESULT_ERROR;
            }
            // 処理結果詳細生成
            createSuccessMailDetail();
            // メール送信メソッド
            sendResultMail(mailMessageDetail);

            // バッチ処理結果レポート登録
            this.report(mailMessageDetail);

        } catch (AppLevelListException appe) {
            // 処理結果エラーメール送信
            batchProcessResult = UPDATE_RESULT_FAILED;
            // エラーとなった対象の商品コードをログ出力
            if (checkGoodsCode != null) {
                LOGGER.error("エラー対象商品コード：" + checkGoodsCode);
            }
            // エラーとなった対象の商品グループコードをログ出力
            if (checkGoodsGroupCode != null) {
                LOGGER.error("エラー対象商品グループコード：" + checkGoodsGroupCode);
            }
            // エラーとなった対象の差異商品グループコードをログ出力
            if (checkRemainsGoodsGroupCode != null) {
                LOGGER.error("エラー対象商品グループコード(差分取込)：" + checkRemainsGoodsGroupCode);
            }
            // エラーリスト内のメッセージをログ出力する
            for (AppLevelException e : appe.getErrorList()) {
                LOGGER.error(e.getMessage(), e);
            }
            // 処理結果詳細生成
            createFailedMailDetail(appe.getClass().getName());
            // メール送信メソッド
            sendResultMail(mailMessageDetail);
            throw appe;
        } catch (Exception error) {
            LOGGER.error("例外処理が発生しました", error);
            // 処理結果エラーメール送信
            batchProcessResult = UPDATE_RESULT_FAILED;
            // エラーとなった対象の商品コードをログ出力
            if (checkGoodsCode != null) {
                LOGGER.error("エラー対象商品コード：" + checkGoodsCode);
            }
            // エラーとなった対象の商品グループコードをログ出力
            if (checkGoodsGroupCode != null) {
                LOGGER.error("エラー対象商品グループコード：" + checkGoodsGroupCode);
            }
            // エラーとなった対象の差異商品グループコードをログ出力
            if (checkRemainsGoodsGroupCode != null) {
                LOGGER.error("エラー対象商品グループコード(差分取込)：" + checkRemainsGoodsGroupCode);
            }
            // 処理結果詳細生成
            createFailedMailDetail(error.getClass().getName());
            // メール送信メソッド
            sendResultMail(mailMessageDetail);
            throw error;
        }

        executionContext.put(BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                        new BatchExitMessageDto(String.valueOf(batchProcessTotalCount),
                                                this.getReportString().toString()
                        )));
        return RepeatStatus.FINISHED;
    }

    /**
     * 商品価格情報取得WEB-APIで取得した情報で商品テーブルを更新<br/>
     * 商品価格情報取得WEB-APIで取得した情報で商品グループSeqを取得し、保持する<br/>
     *
     * @param info 商品価格情報
     */
    protected void updateGoodsInfo(List<WebApiGetPriceResponseDetailDto> info) {

        // 処理対象が存在するかチェック
        if (info != null) {
            // 商品情報更新ループ処理開始
            LOGGER.debug("商品情報ループを開始します。");
            LOGGER.debug("処理対象件数：" + info.size());
            GoodsEntity entity = ApplicationContextUtility.getBean(GoodsEntity.class);
            // 商品価格情報リストから情報取得
            for (WebApiGetPriceResponseDetailDto goodsPriceDetailDto : info) {

                // 処理中商品コード保持
                checkGoodsCode = goodsPriceDetailDto.getGoodsCode();
                // 総処理件数インクリメント
                batchProcessTotalCount++;

                // 商品エンティティ取得
                if (goodsPriceDetailDto.getGoodsCode() != null) {
                    entity = goodsEntityGetByGoodsCodeLogic.execute(getShopSeq(), goodsPriceDetailDto.getGoodsCode());
                }

                // 入力チェック
                if (goodsCheckInputItem(goodsPriceDetailDto, entity)) {
                    // 商品コードを控える
                    failedGoodsCodeList.add(goodsPriceDetailDto.getGoodsCode());
                    continue;
                }

                if (entity == null) {
                    LOGGER.warn("商品コード：" + goodsPriceDetailDto.getGoodsCode() + "が存在しないためスキップします。");
                    // 存在しなかった商品コードを控えておく
                    failedGoodsCodeList.add(goodsPriceDetailDto.getGoodsCode());
                    continue;
                }
                // 2023-renew AddNo5 from here
                // 商品情報上書き
                // 商品価格
                entity.setGoodsPrice(BigDecimal.ZERO);
                // 価格（最低）
                entity.setGoodsPriceInTaxLow(goodsPriceDetailDto.getPriceLow());
                // 価格（最高）
                entity.setGoodsPriceInTaxHight(goodsPriceDetailDto.getPriceHight());
                // 商品価格記号表示フラグ
                // 必須項目のため、記号表示しないをセット
                entity.setPriceMarkDispFlag(HTypePriceMarkDispFlag.OFF);

                // セール価格（最低）
                entity.setPreDiscountPriceLow(goodsPriceDetailDto.getSalePriceLow());
                // セール価格（最高）
                entity.setPreDiscountPriceHight(goodsPriceDetailDto.getSalePriceHight());
                // セール価格記号表示フラグ
                // 必須項目のため、記号表示しないをセット
                entity.setSalePriceMarkDispFlag(HTypeSalePriceMarkDispFlag.OFF);

                // セール価格が商品価格より大きい場合は、セール価格整合性フラグを不整合とする
                if (entity.getPreDiscountPriceLow() != null && entity.getPreDiscountPriceHight() != null) {
                    if (entity.getPreDiscountPriceLow().compareTo(entity.getGoodsPriceInTaxLow()) > 0
                        && entity.getPreDiscountPriceHight().compareTo(entity.getGoodsPriceInTaxHight()) > 0) {
                        entity.setSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MISMATCH);
                    } else {
                        entity.setSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MATCH);
                    }
                } else {
                    // セール価格がない場合は整合性フラグを整合とする
                    entity.setSalePriceIntegrityFlag(HTypeSalePriceIntegrityFlag.MATCH);
                }

                // 販売制御区分
                // 必須項目のため、取り扱い中止をセット
                HTypeSaleControlType saleControlType = EnumTypeUtil.getEnumFromValue(HTypeSaleControlType.class,
                                                                                     String.valueOf(goodsPriceDetailDto.getSaleControl())
                                                                                    );
                if (Objects.isNull(saleControlType)) {
                    saleControlType = HTypeSaleControlType.DISCONTINUED;
                }
                entity.setSaleControl(saleControlType);
                // 2023-renew AddNo5 to here

                // 商品グループコードを取得
                String targetGoodsGroupCode = goodsGroupCodeGetByGoodsGroupSeqLogic.execute(entity.getGoodsGroupSeq());
                // NULLチェック
                if (targetGoodsGroupCode != null) {
                    // 商品グループコードを保持する(保持する際、重複チェックを行う)
                    if (!goodsGroupCodeListByGoodsPrice.contains(targetGoodsGroupCode)) {
                        goodsGroupCodeListByGoodsPrice.add(targetGoodsGroupCode);
                    }
                }

                try {
                    // 商品テーブル更新
                    goodsPriceUpdateService.goodsUpdate(entity);

                } catch (Exception error) {
                    // 更新失敗
                    LOGGER.warn(SYSTEM_ERR_MSG, error);
                    failedGoodsList.add(SYSTEM_ERR_MSG);
                    continue;
                }
                // 更新件数インクリメント
                goodsUpdateNumber++;
            }
            // 処理中商品コード保持終了
            checkGoodsCode = null;
            // 商品情報更新ループ処理終了
            LOGGER.debug("商品情報ループを終了します。");
        }
    }

    /**
     * シリーズ商品価格情報取得WEB-APIで取得した情報で商品グループSeqを取得し、保持する<br/>
     * シリーズ商品価格情報取得WEB-APIで取得した情報で商品グループ情報、商品グループ表示情報を上書きする<br/>
     *
     * @param info シリーズ商品価格情報
     */
    protected void setGoodsGroupInfo(List<WebApiGetSeriesPriceResponseDetailDto> info) {

        // 処理対象が存在するかチェック
        if (info != null) {
            // 商品グループ情報更新ループ処理開始
            LOGGER.debug("商品グループ情報更新ループ処理を開始します。");
            LOGGER.debug("処理対象件数：" + info.size());
            GoodsGroupEntity goodsGroupEntity = ApplicationContextUtility.getBean(GoodsGroupEntity.class);
            GoodsGroupDisplayEntity goodsGroupDisplayEntity =
                            ApplicationContextUtility.getBean(GoodsGroupDisplayEntity.class);
            // シリーズ商品価格情報リストから情報取得
            for (WebApiGetSeriesPriceResponseDetailDto goodsGroupPriceDetailDto : info) {

                // 処理中商品グループコード保持
                checkGoodsGroupCode = goodsGroupPriceDetailDto.getGoodsGroupCode();

                // 総処理件数インクリメント
                batchProcessTotalCount++;

                // 入力チェック
                if (goodsGroupCheckInputItem(goodsGroupPriceDetailDto)) {
                    // 商品グループコードを控える
                    failedGoodsGroupCodeList.add(goodsGroupPriceDetailDto.getGoodsGroupCode());
                    continue;
                }

                // 商品グループエンティティ取得
                goodsGroupEntity = goodsGroupDetailsGetByCodeLogic.getGoodsGroup(getShopSeq(),
                                                                                 goodsGroupPriceDetailDto.getGoodsGroupCode(),
                                                                                 null, HTypeSiteType.BACK, null
                                                                                );
                if (goodsGroupEntity == null) {
                    LOGGER.warn("商品グループコード：" + goodsGroupPriceDetailDto.getGoodsGroupCode() + "が存在しないためスキップします。");
                    // 存在しなかった商品コードを控えておく
                    failedGoodsGroupCodeList.add(goodsGroupPriceDetailDto.getGoodsGroupCode());
                    continue;
                }
                // 商品グループ表示エンティティ取得
                goodsGroupDisplayEntity = goodsGroupDisplayGetLogic.execute(goodsGroupEntity.getGoodsGroupSeq());
                if (goodsGroupDisplayEntity == null) {
                    LOGGER.warn("商品グループコード：" + goodsGroupPriceDetailDto.getGoodsGroupCode() + "が存在しないためスキップします。");
                    // 存在しなかった商品コードを控えておく
                    failedGoodsGroupCodeList.add(goodsGroupPriceDetailDto.getGoodsGroupCode());
                    continue;
                }

                // 2023-renew AddNo5 from here
                // 商品グループ情報上書き
                // シリーズ価格
                goodsGroupEntity.setGroupPrice(BigDecimal.ZERO);
                // シリーズセール価格
                goodsGroupEntity.setGroupSalePrice(null);

                // シリーズ価格記号表示フラグ
                // 必須項目のためnullはあり得ない想定だが、nullの場合は記号表示しないをセット
                goodsGroupEntity.setGroupPriceMarkDispFlag(HTypeGroupPriceMarkDispFlag.OFF);

                // シリーズセール価格記号表示フラグ
                // シリーズセール価格記号表示フラグが連携されなかった場合は、記号表示しないをセット
                goodsGroupEntity.setGroupSalePriceMarkDispFlag(HTypeGroupSalePriceMarkDispFlag.OFF);
                // 2023-renew AddNo5 to here

                // シリーズセールコメント
                goodsGroupEntity.setGoodsPreDiscountPrice(goodsGroupPriceDetailDto.getSeriesSaleComment());

                // 商品グループ表示情報上書き
                // NEWアイコンフラグ
                if (goodsGroupPriceDetailDto.getNewIconFlag() != null) {
                    goodsGroupDisplayEntity.setNewIconFlag(EnumTypeUtil.getEnumFromValue(HTypeNewIconFlag.class,
                                                                                         goodsGroupPriceDetailDto.getNewIconFlag()
                                                                                                                 .toString()
                                                                                        ));
                }
                // お取りおきアイコンフラグ
                if (goodsGroupPriceDetailDto.getReserveIconFlag() != null) {
                    goodsGroupDisplayEntity.setReserveIconFlag(EnumTypeUtil.getEnumFromValue(HTypeReserveIconFlag.class,
                                                                                             goodsGroupPriceDetailDto.getReserveIconFlag()
                                                                                                                     .toString()
                                                                                            ));
                }
                // SALEアイコンフラグ
                if (goodsGroupPriceDetailDto.getSaleIconFlag() != null) {
                    goodsGroupDisplayEntity.setSaleIconFlag(EnumTypeUtil.getEnumFromValue(HTypeSaleIconFlag.class,
                                                                                          goodsGroupPriceDetailDto.getSaleIconFlag()
                                                                                                                  .toString()
                                                                                         ));
                }

                // 2023-renew No92 from here
                // アウトレットアイコンフラグ
                if (goodsGroupPriceDetailDto.getOutletIconFlag() != null) {
                    goodsGroupDisplayEntity.setOutletIconFlag(EnumTypeUtil.getEnumFromValue(HTypeOutletIconFlag.class,
                                                                                            goodsGroupPriceDetailDto.getOutletIconFlag()
                                                                                                            .toString()
                                                                                           ));
                }
                // 2023-renew No92 to here

                // 上書き情報をマップで保存
                goodsGroupMap.put(goodsGroupEntity.getGoodsGroupCode(), goodsGroupEntity);
                goodsGroupDisplayMap.put(goodsGroupEntity.getGoodsGroupCode(), goodsGroupDisplayEntity);
                // 商品グループコードを保持する(保持する際、重複チェックを行う)
                if (!goodsGroupCodeListByGoodsSeriesPrice.contains(goodsGroupEntity.getGoodsGroupCode())) {
                    goodsGroupCodeListByGoodsSeriesPrice.add(goodsGroupEntity.getGoodsGroupCode());
                }
            }
            // 処理中商品グループコード保持終了
            checkGoodsGroupCode = null;
            // 商品グループ情報更新ループ処理終了
            LOGGER.debug("商品グループ情報更新ループ処理を終了します。");
        }
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
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_GOODS_PRICE_UPDATE.getLabel());
            // 処理結果
            valueMap.put("RESULT", batchProcessResult);
            // 処理結果詳細
            valueMap.put("DETAIL", detail);

            mailContentsMap.put("admin", valueMap);
            // メール送信実施
            mailDto.setMailTemplateType(HTypeMailTemplateType.GOODS_PRICE_UPDATE_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject(HTypeBatchName.BATCH_GOODS_PRICE_UPDATE.getLabel() + batchProcessResult);
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);
            LOGGER.debug("処理結果メールを送信しました。");
        } catch (Exception e) {
            LOGGER.error("処理結果メールの送信に失敗しました。", e);
        }

        LOGGER.debug("処理結果メールの送信処理を終了しました。");
    }

    /**
     * 処理結果成功内容生成
     */
    public void createSuccessMailDetail() {

        StringBuilder resultDetail = new StringBuilder();
        // 商品情報成功更新件数
        resultDetail.append("商品情報更新件数：");
        resultDetail.append(goodsUpdateNumber);
        resultDetail.append("件");

        // スキップした商品コードがある場合のみ処理結果情報に追記
        if (!ListUtils.isEmpty(failedGoodsCodeList)) {
            resultDetail.append("\nスキップ件数:");
            resultDetail.append(failedGoodsCodeList.size());
            resultDetail.append("件");
            for (String failedCode : failedGoodsCodeList) {
                resultDetail.append("\n - 商品コード：").append(failedCode);
            }
        }

        // 商品グループ情報成功更新件数
        resultDetail.append("\n商品グループ情報更新件数：");
        resultDetail.append(goodsGroupUpdateNumber);
        resultDetail.append("件");

        // スキップした商品コードがある場合のみ処理結果情報に追記
        if (!ListUtils.isEmpty(failedGoodsGroupCodeList)) {
            resultDetail.append("\nスキップ件数:");
            resultDetail.append(failedGoodsGroupCodeList.size());
            resultDetail.append("件");
            for (String failedGroupCode : failedGoodsGroupCodeList) {
                resultDetail.append("\n - 商品グループコード：").append(failedGroupCode);
            }
        }
        resultDetail.append(createSuccessMailFailed());
        // 処理結果メールメッセージに格納
        mailMessageDetail = resultDetail.toString();
    }

    /**
     * 処理結果失敗内容生成
     *
     * @return 失敗内容
     */
    public String createSuccessMailFailed() {

        StringBuilder resultFailed = new StringBuilder();
        // エラーチェックリストがある場合のみ処理結果情報に追記
        if (failedGoodsList.size() > 0 || failedGoodsGroupList.size() > 0) {
            resultFailed.append("\n\n▼エラー内容：\n");
        }
        for (String failedGoodsCode : failedGoodsList) {
            // 既にシステムエラ-が存在した場合、メッセージが重複しないようにする
            if (SYSTEM_ERR_MSG.equals(failedGoodsCode)) {
                if (resultFailed.toString().contains(SYSTEM_ERR_MSG)) {
                    continue;
                }
            }
            resultFailed.append(failedGoodsCode).append("\n");
        }
        for (String failedGoodsGroupCode : failedGoodsGroupList) {
            // 既にシステムエラ-が存在した場合、メッセージが重複しないようにする
            if (SYSTEM_ERR_MSG.equals(failedGoodsGroupCode)) {
                if (resultFailed.toString().contains(SYSTEM_ERR_MSG)) {
                    continue;
                }
            }
            resultFailed.append(failedGoodsGroupCode).append("\n");
        }
        return resultFailed.toString();
    }

    /**
     * 処理結果異常終了内容生成
     *
     * @param exceptionName 発生したException
     */
    public void createFailedMailDetail(String exceptionName) {

        // 処理結果メールメッセージに格納
        mailMessageDetail = "以下のエラーが発生しました。" + "\n詳しくはサーバログを参照してください。" + "\n\n処理中に" + exceptionName + "が発生しました。";
    }

    /**
     * 入力チェック
     *
     * @param goodsPriceDetailDto 価格情報取得 詳細情報
     * @param entity              商品情報
     * @return エラーフラグ true:エラー有 false:エラー無
     */
    public boolean goodsCheckInputItem(WebApiGetPriceResponseDetailDto goodsPriceDetailDto, GoodsEntity entity) {

        boolean errorFlag = false;

        String goodsGroupCode = "";
        if (entity != null && entity.getGoodsGroupSeq() != null) {
            goodsGroupCode = goodsGroupCodeGetByGoodsGroupSeqLogic.execute(entity.getGoodsGroupSeq());
        }
        String goodsCode = goodsPriceDetailDto.getGoodsCode() == null ? "" : goodsPriceDetailDto.getGoodsCode();

        String checkTargetStr = "【商品グループコード：" + goodsGroupCode + "　商品コード:" + goodsCode + "】";

        // 入力チェック・桁数チェック・区分値チェック
        // 商品コードを確認
        if (goodsPriceDetailDto.getGoodsCode() == null) {
            failedGoodsList.add(checkTargetStr + "商品コード" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (goodsPriceDetailDto.getGoodsCode().length() >= 13) {
            failedGoodsList.add(checkTargetStr + "商品コード" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 2023-renew AddNo5 from here
        // 価格を確認
        if (goodsPriceDetailDto.getPriceLow() == null) {
            failedGoodsList.add(checkTargetStr + "価格(最低)" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (goodsPriceDetailDto.getPriceLow().toString().length() >= 11) {
            failedGoodsList.add(checkTargetStr + "価格(最低)" + MAXLENGTH_MSG);
            errorFlag = true;
        }
        if (goodsPriceDetailDto.getPriceHight() == null) {
            failedGoodsList.add(checkTargetStr + "価格（最高）" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (goodsPriceDetailDto.getPriceHight().toString().length() >= 11) {
            failedGoodsList.add(checkTargetStr + "価格（最高）" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // セール価格を確認
        if (goodsPriceDetailDto.getSalePriceLow() != null
            && goodsPriceDetailDto.getSalePriceLow().toString().length() >= 11) {
            failedGoodsList.add(checkTargetStr + "セール価格（最低）" + MAXLENGTH_MSG);
            errorFlag = true;
        }
        if (goodsPriceDetailDto.getSalePriceHight() != null
            && goodsPriceDetailDto.getSalePriceHight().toString().length() >= 11) {
            failedGoodsList.add(checkTargetStr + "セール価格（最高）" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 販売制御区分を確認
        if (goodsPriceDetailDto.getSaleControl() == null || EnumTypeUtil.getEnumFromValue(HTypeSaleControlType.class,
                                                                                          String.valueOf(goodsPriceDetailDto.getSaleControl())
                                                                                         ) == null) {
            failedGoodsList.add(checkTargetStr + "販売制御区分" + ITEM_ILLEGAL_MSG);
            errorFlag = true;
        }
        // 2023-renew AddNo5 to here

        // セールコメントを確認
        if (goodsPriceDetailDto.getSaleComment() != null && goodsPriceDetailDto.getSaleComment().length() >= 101) {
            failedGoodsList.add(checkTargetStr + "セールコメント" + MAXLENGTH_MSG);
            errorFlag = true;
        }
        return errorFlag;
    }

    /**
     * 入力チェック
     *
     * @param goodsGroupPriceDetailDto シリーズ商品価格情報取得 詳細情報
     * @return エラーフラグ true:エラー有 false:エラー無
     */
    public boolean goodsGroupCheckInputItem(WebApiGetSeriesPriceResponseDetailDto goodsGroupPriceDetailDto) {

        boolean errorFlag = false;

        String goodsGroupCode = goodsGroupPriceDetailDto.getGoodsGroupCode() == null ?
                        "" :
                        goodsGroupPriceDetailDto.getGoodsGroupCode();

        String checkErrorCodeStr = "【商品グループコード:" + goodsGroupCode + "】";

        // 入力チェック・桁数チェック
        // 商品グループコードを確認
        if (goodsGroupPriceDetailDto.getGoodsGroupCode() == null) {
            checkErrorCodeStr = "【商品グループコード:】";
            failedGoodsGroupList.add(checkErrorCodeStr + "商品グループコード" + ISEMPTY_MSG);
            errorFlag = true;
        } else if (goodsGroupPriceDetailDto.getGoodsGroupCode().length() >= 13) {
            failedGoodsGroupList.add(checkErrorCodeStr + "商品グループコード" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // 2023-renew AddNo5 from here
        // 2023-renew AddNo5 to here

        // シリーズセールコメントを確認
        if (goodsGroupPriceDetailDto.getSeriesSaleComment() != null
            && goodsGroupPriceDetailDto.getSeriesSaleComment().length() >= 51) {
            failedGoodsGroupList.add(checkErrorCodeStr + "シリーズセールコメント" + MAXLENGTH_MSG);
            errorFlag = true;
        }

        // NEWアイコンフラグを確認
        if (goodsGroupPriceDetailDto.getNewIconFlag() != null && goodsGroupPriceDetailDto.getNewIconFlag() != 0
            && goodsGroupPriceDetailDto.getNewIconFlag() != 1) {
            failedGoodsGroupList.add(checkErrorCodeStr + "NEWアイコンフラグ" + ITEM_ILLEGAL_MSG);
            errorFlag = true;
        }

        // お取り置きアイコンフラグを確認
        if (goodsGroupPriceDetailDto.getReserveIconFlag() != null && goodsGroupPriceDetailDto.getReserveIconFlag() != 0
            && goodsGroupPriceDetailDto.getReserveIconFlag() != 1) {
            failedGoodsGroupList.add(checkErrorCodeStr + "お取り置きアイコンフラグ" + ITEM_ILLEGAL_MSG);
            errorFlag = true;
        }

        // SALEアイコンフラグを確認
        if (goodsGroupPriceDetailDto.getSaleIconFlag() != null && goodsGroupPriceDetailDto.getSaleIconFlag() != 0
            && goodsGroupPriceDetailDto.getSaleIconFlag() != 1) {
            failedGoodsGroupList.add(checkErrorCodeStr + "SALEアイコンフラグ" + ITEM_ILLEGAL_MSG);
            errorFlag = true;
        }

        // 2023-renew No92 from here
        // アウトレットアイコンフラグを確認
        if (goodsGroupPriceDetailDto.getOutletIconFlag() != null && goodsGroupPriceDetailDto.getOutletIconFlag() != 0
            && goodsGroupPriceDetailDto.getOutletIconFlag() != 1) {
            failedGoodsGroupList.add(checkErrorCodeStr + "アウトレットアイコンフラグ" + ITEM_ILLEGAL_MSG);
            errorFlag = true;
        }
        // 2023-renew No92 to here
        return errorFlag;
    }

}
// PDR Migrate Customization to here
