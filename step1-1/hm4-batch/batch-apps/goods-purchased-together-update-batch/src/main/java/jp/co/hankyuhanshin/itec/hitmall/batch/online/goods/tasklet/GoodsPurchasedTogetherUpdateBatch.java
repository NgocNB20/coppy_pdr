// 2023-renew No21 from here
/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 */

package jp.co.hankyuhanshin.itec.hitmall.batch.online.goods.tasklet;

import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.batch.common.BatchExitMessageUtil;
import jp.co.hankyuhanshin.itec.hitmall.batch.core.AbstractBatch;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBatchName;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRegisterMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dto.mail.MailDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsTogetherBuyGroupEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup.GoodsGroupTogetherBuyGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryTogetherBuyGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一緒によく購入される商品更新バッチ
 *
 * @author Doan Thang (VTI Japan Co., Ltd.)
 */
public class GoodsPurchasedTogetherUpdateBatch extends AbstractBatch {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsPurchasedTogetherUpdateBatch.class);

    /**
     * メール送信サービス（同期送信）
     */
    private final MailSendService mailSendService;

    /**
     * dicon 設定： メール送信設定
     */
    private final InstantMailSetting mailSetting;

    /**
     * 一緒によく購入される商品更新サービス
     */
    private final GoodsPurchasedTogetherUpdateService goodsPurchasedTogetherUpdateService;

    /**
     * 商品グループDAO
     */
    private final GoodsGroupTogetherBuyGetLogic goodsGroupTogetherBuyGetLogic;

    /**
     * よく一緒に購入される商品クラスリスト取得
     */
    private final OrderSummaryTogetherBuyGetLogic orderSummaryTogetherBuyGetLogic;

    /**
     * 日付関連Utility
     */
    private final DateUtility dateUtility;

    /**
     * バッチ終了メッセージ共通処理
     */
    private final BatchExitMessageUtil exitMessageUtil;

    /**
     * 一緒によく購入された商品バッチ用開始位置
     */
    private final int ORDER_DISPLAY_START = 21;

    /**
     * 一緒によく購入された商品バッチ用終了位置
     */
    private final int ORDER_DISPLAY_END = 41;

    /**
     * コンストラクタ。
     */
    public GoodsPurchasedTogetherUpdateBatch(Environment environment) {
        this.mailSendService = ApplicationContextUtility.getBean(MailSendService.class);
        this.goodsPurchasedTogetherUpdateService =
                        ApplicationContextUtility.getBean(GoodsPurchasedTogetherUpdateService.class);
        this.goodsGroupTogetherBuyGetLogic = ApplicationContextUtility.getBean(GoodsGroupTogetherBuyGetLogic.class);
        this.orderSummaryTogetherBuyGetLogic = ApplicationContextUtility.getBean(OrderSummaryTogetherBuyGetLogic.class);
        this.dateUtility = ApplicationContextUtility.getBean(DateUtility.class);
        this.exitMessageUtil = new BatchExitMessageUtil();

        this.mailSetting = new InstantMailSetting(
                        environment.getProperty("mail.setting.goodsPurchasedTogether.update.smtp.server"),
                        environment.getProperty("mail.setting.goodsPurchasedTogether.update.mail.from"), null, null,
                        Collections.singletonList(environment.getProperty(
                                        "mail.setting.goodsPurchasedTogether.update.mail.receivers"))
        );
    }

    /**
     * 一緒によく購入される商品更新バッチ
     *
     * @param contribution contribution
     * @param chunkContext chunkContext
     * @return exitCode
     * @throws Exception ハンドリングされなかった例外全般
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
            // ① 一緒によく購入される商品TBLデータ削除
            goodsPurchasedTogetherUpdateService.goodsTogetherBuyGroupDelete(HTypeRegisterMethodType.BATCH.getValue());

            // ② 過去半年間で購入された商品リスト取得
            List<Integer> goodsGroupSeqList = goodsGroupTogetherBuyGetLogic.execute();

            if (goodsGroupSeqList != null) {
                for (Integer goodsGroupSeq : goodsGroupSeqList) {
                    // ③ 一緒によく購入された商品集計
                    List<GoodsTogetherBuyGroupEntity> goodsTogetherBuyDtoList =
                                    orderSummaryTogetherBuyGetLogic.execute(goodsGroupSeq);

                    // ④一緒によく購入された商品TBL登録
                    int orderDisplay = ORDER_DISPLAY_START;
                    for (GoodsTogetherBuyGroupEntity goodsTogetherBuyGroupEntity : goodsTogetherBuyDtoList) {
                        // 現在日時
                        Timestamp currentTime = dateUtility.getCurrentTime();
                        goodsTogetherBuyGroupEntity.setGoodsGroupSeq(goodsGroupSeq);
                        goodsTogetherBuyGroupEntity.setOrderDisplay(orderDisplay);
                        goodsTogetherBuyGroupEntity.setRegistMethod(HTypeRegisterMethodType.BATCH);
                        goodsTogetherBuyGroupEntity.setRegistTime(currentTime);
                        goodsTogetherBuyGroupEntity.setUpdateTime(currentTime);

                        goodsPurchasedTogetherUpdateService.goodsTogetherBuyGroupRegist(goodsTogetherBuyGroupEntity);
                        orderDisplay++;
                        if (orderDisplay >= ORDER_DISPLAY_END) {
                            break;
                        }
                    }
                }
            }

            // 処理結果メールを管理者に送信する。
            sendAdministratorMail(goodsGroupSeqList != null ? goodsGroupSeqList.size() : 0);
        } catch (Exception e) {
            // 予期せぬ事態で処理が中断した場合
            LOGGER.error("例外処理が発生しました", e);

            // エラーメール送信
            String exceptionName = e.getClass().getName();
            sendAdministratorErrorMail(exceptionName);
            executionContext.put(BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                            new BatchExitMessageDto(null, this.getReportString().toString())));
            throw e;
        }

        executionContext.put(BatchExitMessageUtil.exitMsg, exitMessageUtil.convertObjectToJson(
                        new BatchExitMessageDto(null, this.getReportString().toString())));
        return RepeatStatus.FINISHED;
    }

    /**
     * 管理者向けメールを送信する
     *
     * @param goodsPurchased 更新対象商品数
     * @return true:成功、false:失敗
     */
    protected boolean sendAdministratorMail(Integer goodsPurchased) {

        try {

            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);
            Map<String, Object> mailContentsMap = new HashMap<>();

            // システム名を取得
            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            final Map<String, String> valueMap = new HashMap<>();
            valueMap.put("SYSTEM", systemName);
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_GOODS_PURCHASED_TOGETHER_UPDATE.getLabel());
            valueMap.put("SHOP_SEQ", getShopSeq().toString());
            valueMap.put("RESULT", goodsPurchased.toString());

            if (LOGGER.isDebugEnabled()) {
                valueMap.put("DEBUG", "1");
            } else {
                valueMap.put("DEBUG", "0");
            }

            mailContentsMap.put("admin", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.BATCH_GOODS_PURCHASED_TOGETHER_UPDATE_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_GOODS_PURCHASED_TOGETHER_UPDATE.getLabel()
                               + "正常終了");
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
     * 管理者向けエラーメールを送信する
     *
     * @param exceptionName 例外エラー名
     * @return 送信成功:true、送信失敗：false
     */
    protected boolean sendAdministratorErrorMail(final String exceptionName) {

        try {
            MailDto mailDto = ApplicationContextUtility.getBean(MailDto.class);

            Map<String, Object> mailContentsMap = new HashMap<>();
            final Map<String, String> valueMap = new HashMap<>();

            String systemName = PropertiesUtil.getSystemPropertiesValue("system.name");

            valueMap.put("SYSTEM", systemName);
            valueMap.put("BATCH_NAME", HTypeBatchName.BATCH_GOODS_PURCHASED_TOGETHER_UPDATE.getLabel());
            valueMap.put("SHOP_SEQ", getShopSeq().toString());
            valueMap.put("RESULT", exceptionName);

            if (LOGGER.isDebugEnabled()) {
                valueMap.put("DEBUG", "1");
            } else {
                valueMap.put("DEBUG", "0");
            }

            mailContentsMap.put("error", valueMap);

            mailDto.setMailTemplateType(HTypeMailTemplateType.BATCH_GOODS_PURCHASED_TOGETHER_UPDATE_ERROR_MAIL);
            mailDto.setFrom(this.mailSetting.getMailFrom());
            mailDto.setToList(this.mailSetting.getNotificationReceivers());
            mailDto.setSubject("【" + systemName + "】" + HTypeBatchName.BATCH_GOODS_PURCHASED_TOGETHER_UPDATE.getLabel()
                               + "報告[*]");
            mailDto.setMailContentsMap(mailContentsMap);

            this.mailSendService.execute(mailDto);

            LOGGER.info("管理者へ通知メールを送信しました。");
            return true;

        } catch (Exception e) {
            LOGGER.warn("管理者への通知メール送信に失敗しました。", e);
            return false;
        }
    }
}
// 2023-renew No21 to here
