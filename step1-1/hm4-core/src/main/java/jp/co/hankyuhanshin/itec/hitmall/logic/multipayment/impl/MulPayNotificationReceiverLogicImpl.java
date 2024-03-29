/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.impl;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAmazonPaymentConfirmStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeJobCode;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeProcessedFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.MulPayBillDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.MulPayResultDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.MulPayShopDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayResultEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayShopEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.MulPayNotificationReceiverLogic;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ConversionUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * マルチペイメント決済結果通知受付サーブレットロジック<br/>
 *
 * @author na65101 STS Nakamura 2020/03/11 #4181 GMO経由AmazonPay対応
 */
@Component
public class MulPayNotificationReceiverLogicImpl implements MulPayNotificationReceiverLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MulPayNotificationReceiverLogicImpl.class);

    /**
     * 受信方法登録値
     */
    protected static final String INS_RECEIVE_MODE = "PASSIVE";

    /**
     * 入金通知
     */
    protected static final String PAYSUCCESS = "PAYSUCCESS";

    /**
     * リクエストパラメータ名配列（共通項目）
     */
    protected static String[] requestParamsCommon =
                    {"ShopID", "ShopPass", "AccessID", "AccessPass", "OrderID", "Status", "Amount", "Tax", "TranDate",
                                    "ErrCode", "ErrInfo", "PayType"};

    /**
     * リクエストパラメータ名配列 ※AmazonPay以外
     */
    protected static String[] requestParamsExceptAmazonpay = {"Currency"};

    /**
     * リクエストパラメータ名配列 ※AmazonPay(GMO経由)
     */
    protected static String[] requestParamsForAmazonpay = {"JobCd", "CancelAmount", "CancelTax"};

    /**
     * リクエストパラメータ 決済方法インデックス
     */
    protected static final int REQUEST_PARAM_INDEX_PAYTYPE = 11;

    /**
     * ConversionUtility
     */
    private final ConversionUtility conversionUtility;

    /**
     * DateUtility
     */
    private final DateUtility dateUtility;

    /**
     * MulPayShopDao
     */
    private final MulPayShopDao mulPayShopDao;

    /**
     * MulPayBillDao
     */
    private final MulPayBillDao mulPayBillDao;

    /**
     * MulPayResultDao
     */
    private final MulPayResultDao mulPayResultDao;

    @Autowired
    public MulPayNotificationReceiverLogicImpl(ConversionUtility conversionUtility,
                                               DateUtility dateUtility,
                                               MulPayShopDao mulPayShopDao,
                                               MulPayBillDao mulPayBillDao,
                                               MulPayResultDao mulPayResultDao) {
        this.conversionUtility = conversionUtility;
        this.dateUtility = dateUtility;
        this.mulPayShopDao = mulPayShopDao;
        this.mulPayBillDao = mulPayBillDao;
        this.mulPayResultDao = mulPayResultDao;
    }

    /**
     * 実行処理<br/>
     * <p>
     * 1.受信情報の妥当性チェック<br/>
     * 2.マルチペイメント請求データ取得<br/>
     * 3.受信データをマルチペイメント決済結果テーブルへの登録処理
     * </p>
     *
     * @param req リクエスト
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void execute(HttpServletRequest req) {
        // 1.受信情報の妥当性チェック
        if (!checkRequiredParameter(req)) {
            return;
        }

        // 1-2.DB（マルチペイメント用ショップ設定テーブル）存在チェック
        MulPayShopEntity mulPayShopEntity = getMulPayShopEntity(req.getParameter("ShopID"));
        if (mulPayShopEntity == null) {
            return;
        }

        // 2.マルチペイメント請求データ取得
        MulPayBillEntity mulPayBillEntity =
                        getMulPayBillEntity(req.getParameter("OrderID"), req.getParameter("AccessID"));
        if (mulPayBillEntity == null) {
            return;
        }
        // 3.受信データをマルチペイメント決済結果テーブルへの登録処理
        insertMulPayResult(req, mulPayShopEntity.getShopSeq(), mulPayBillEntity);
    }

    /**
     * 必須パラメータチェック
     * <pre>
     * 各決済方法別の必須項目がリクエストパラメータで送られてきていることを確認する。
     * </pre>
     *
     * @param req リクエストパラメータ
     * @return true or false
     */
    protected boolean checkRequiredParameter(HttpServletRequest req) {
        // 共通項目
        if (!checkParameter(req, requestParamsCommon)) {
            LOGGER.error("受信情報妥当性エラー　必須項目がありません");
            return false;
        }

        String payType = req.getParameter(requestParamsCommon[REQUEST_PARAM_INDEX_PAYTYPE]);
        if (HTypeSettlementMethodType.AMAZON_PAYMENT.getValue().equals(payType)) {
            // AmazonPay
            if (!checkParameter(req, requestParamsForAmazonpay)) {
                LOGGER.error("受信情報妥当性エラー　AmazonPay(GMO経由)：必須項目がありません。");
                return false;
            }
        } else {
            // AmazonPay以外
            if (!checkParameter(req, requestParamsExceptAmazonpay)) {
                LOGGER.error("受信情報妥当性エラー　必須項目がありません");
                return false;
            }
        }
        return true;
    }

    /**
     * 汎用パラメータチェック
     * <pre>
     * リクエストパラメータが送られてきていることを確認する。
     * いずれか一つのパラメータが存在しなければ falseを返す。
     * </pre>
     *
     * @param req           リクエスト
     * @param requestParams リクエストパラメータ
     * @return true or false
     */
    protected boolean checkParameter(HttpServletRequest req, String[] requestParams) {
        for (String param : requestParams) {
            if (req.getParameter(param) == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 最新マルチペイメント請求エンティティ取得処理
     *
     * @param orderId  オーダーID
     * @param accessId 取引ID
     * @return マルチペイメント請求エンティティ
     */
    protected MulPayBillEntity getMulPayBillEntity(String orderId, String accessId) {
        try {
            // マルチペイメント請求データ取得処理
            MulPayBillEntity mulPayBillEntity = mulPayBillDao.getLatestEntityByOrderIdAndAccessId(orderId, accessId);
            if (mulPayBillEntity == null) {
                LOGGER.error("マルチペイメント請求データが取得できませんでした" + "　【オーダーID】:" + orderId + "　【取引ID】:" + accessId);
            }
            return mulPayBillEntity;
        } catch (Exception e) {
            LOGGER.error("マルチペイメント請求テーブル参照中にエラーが発生しました" + "　【オーダーID】:" + orderId + "　【取引ID】:" + accessId, e);
            throw e;
        }
    }

    /**
     * マルチペイメント用ショップ設定エンティティ取得処理
     *
     * @param shopId ショップID
     * @return マルチペイメント用ショップ設定エンティティ
     */
    protected MulPayShopEntity getMulPayShopEntity(String shopId) {
        try {
            // マルチペイメント用ショップ設定テーブル検索処理
            MulPayShopEntity mulPayShopEntity = mulPayShopDao.getEntityByShopId(shopId);
            if (mulPayShopEntity == null) {
                LOGGER.error("受信情報妥当性エラー　存在しないショップIDです" + "　【ショップID】:" + shopId);
                return null;
            }
            return mulPayShopEntity;
        } catch (Exception e) {
            LOGGER.error("マルチペイメント用ショップ設定テーブル参照中にエラーが発生しました" + "　【ショップID】:" + shopId, e);
            throw e;
        }
    }

    /**
     * マルチペイメント決済結果テーブルへのデータ登録処理
     *
     * @param req              リクエスト
     * @param shopSeq          ショップSEQ
     * @param mulPayBillEntity マルペイ請求エンティティ
     */
    protected void insertMulPayResult(HttpServletRequest req, Integer shopSeq, MulPayBillEntity mulPayBillEntity) {
        String orderID = req.getParameter("OrderID");
        String accessID = req.getParameter("AccessID");
        MulPayResultEntity mulPayResultEntity = creatMulPayResultEntity(req, shopSeq, mulPayBillEntity);

        // 存在チェック処理
        try {
            int dataCount = mulPayResultDao.checkSameNotificationRecord(mulPayResultEntity.getOrderId(),
                                                                        mulPayResultEntity.getStatus(),
                                                                        conversionUtility.toInteger(
                                                                                        mulPayResultEntity.getAmount()),
                                                                        mulPayResultEntity.getErrCode(),
                                                                        mulPayResultEntity.getErrInfo()
                                                                       );
            if (dataCount > 0) {
                LOGGER.warn("受信したマルチペイメント決済通知情報は既に登録済みです" + "　【オーダーID】:" + orderID + "　【取引ID】:" + accessID);
                return;
            }
        } catch (Exception e) {
            LOGGER.error("マルチペイメント決済結果テーブル参照中にエラーが発生しました" + "　【オーダーID】:" + orderID + "　【取引ID】:" + accessID, e);
            throw e;
        }

        // データ登録処理
        try {
            mulPayResultDao.insert(mulPayResultEntity);
        } catch (Exception e) {
            LOGGER.error("マルチペイメント決済通知情報の登録に失敗しました" + "　【オーダーID】:" + orderID + "　【取引ID】:" + accessID, e);
            throw e;
        }

        try {
            // マルペイ請求のアマペイ入金確認ステータスを更新
            // AmazonPay（GMO経由）決済で、マルペイ決済結果に実売上or減額or全キャンセルのデータが登録された場合は、
            // マルペイ請求のAmazonPayment入金確認ステータスを 2 にupdateする
            if (HTypeSettlementMethodType.AMAZON_PAYMENT.getValue().equals(mulPayBillEntity.getPayType())) {
                updateMulPayBillAmazonPay(mulPayBillEntity, mulPayResultEntity);
            }
        } catch (Exception e) {
            LOGGER.error("マルチペイメント請求の更新に失敗しました" + "　【オーダーID】:" + orderID + "　【取引ID】:" + accessID, e);
            throw e;
        }
    }

    /**
     * AmazonPay(GMO経由)決済用マルチペイメント請求更新<br/>
     * GMO管理画面から実売上処理されたとき、マルペイ請求.AmazonPayment入金確認ステータスを 2 に更新する<br/>
     *
     * @param mulPayBillEntity   マルチペイメント請求エンティティ
     * @param mulPayResultEntity マルチペイメント決済結果エンティティ
     */
    protected void updateMulPayBillAmazonPay(MulPayBillEntity mulPayBillEntity, MulPayResultEntity mulPayResultEntity) {
        if (StringUtils.isEmpty(mulPayResultEntity.getErrCode())) {
            if (HTypeJobCode.SALES.getValue().equals(mulPayResultEntity.getStatus()) || HTypeJobCode.RETURN.getValue()
                                                                                                           .equals(mulPayResultEntity
                                                                                                                                   .getStatus())) {
                // 現状態が実売上・返品の場合、AmazonPay入金確認ステータスを 2に更新する
                mulPayBillEntity.setAmazonPaymentConfirmStatus(HTypeAmazonPaymentConfirmStatus.PAYMENT_CONFIRMED);
                mulPayBillEntity.setUpdateTime(dateUtility.getCurrentTime());
                mulPayBillDao.update(mulPayBillEntity);
            }
        } else {
            // errCodeが設定されていた場合、AmazonPay入金確認ステータスを9に更新する
            mulPayBillEntity.setAmazonPaymentConfirmStatus(HTypeAmazonPaymentConfirmStatus.ERROR_OCCURED);
            mulPayBillEntity.setUpdateTime(dateUtility.getCurrentTime());
            mulPayBillDao.update(mulPayBillEntity);
        }
    }

    /**
     * マルチペイメント決済結果エンティティ生成処理
     *
     * @param req              リクエスト
     * @param shopSeq          ショップSEQ
     * @param mulPayBillEntity マルペイ請求エンティティ
     * @return マルチペイメント決済結果エンティティ
     */
    protected MulPayResultEntity creatMulPayResultEntity(HttpServletRequest req,
                                                         Integer shopSeq,
                                                         MulPayBillEntity mulPayBillEntity) {
        MulPayResultEntity mulPayResultEntity = ApplicationContextUtility.getBean(MulPayResultEntity.class);
        mulPayResultEntity.setReceiveMode(INS_RECEIVE_MODE);
        if (PAYSUCCESS.equals(req.getParameter("Status"))) {
            mulPayResultEntity.setProcessedFlag(HTypeProcessedFlag.PROCESSING_REQUIRED);
        } else {
            mulPayResultEntity.setProcessedFlag(HTypeProcessedFlag.PROCESSED);
        }
        if (StringUtils.isNotEmpty(req.getParameter("ErrCode")) && HTypeSettlementMethodType.AMAZON_PAYMENT.getValue()
                                                                                                           .equals(mulPayBillEntity.getPayType())) {
            mulPayResultEntity.setProcessedFlag(HTypeProcessedFlag.BILL_SETTLEMENT_ERROR);
        }
        mulPayResultEntity.setShopSeq(shopSeq);
        mulPayResultEntity.setOrderSeq(mulPayBillEntity.getOrderSeq());
        mulPayResultEntity.setOrderId(convBlankToNull(req.getParameter("OrderID")));
        mulPayResultEntity.setStatus(convBlankToNull(req.getParameter("Status")));
        mulPayResultEntity.setJobCd(convBlankToNull(req.getParameter("JobCd")));
        mulPayResultEntity.setAmount(conversionUtility.toBigDecimal(req.getParameter("Amount")));
        mulPayResultEntity.setTax(conversionUtility.toBigDecimal(req.getParameter("Tax")));
        mulPayResultEntity.setCurrency(convBlankToNull(req.getParameter("Currency")));
        mulPayResultEntity.setForward(convBlankToNull(req.getParameter("Forward")));
        mulPayResultEntity.setMethod(convBlankToNull(req.getParameter("Method")));
        mulPayResultEntity.setPayTimes(conversionUtility.toInteger(req.getParameter("PayTimes")));
        mulPayResultEntity.setTranId(convBlankToNull(req.getParameter("TranId")));
        mulPayResultEntity.setApprove(convBlankToNull(req.getParameter("Approve")));
        mulPayResultEntity.setTranDate(convBlankToNull(req.getParameter("TranDate")));
        mulPayResultEntity.setErrCode(convBlankToNull(req.getParameter("ErrCode")));
        mulPayResultEntity.setErrInfo(convBlankToNull(req.getParameter("ErrInfo")));
        mulPayResultEntity.setPayType(convBlankToNull(req.getParameter("PayType")));
        mulPayResultEntity.setCvsCode(convBlankToNull(req.getParameter("CvsCode")));
        mulPayResultEntity.setCvsConfNo(convBlankToNull(req.getParameter("CvsConfNo")));
        mulPayResultEntity.setCvsReceiptNo(convBlankToNull(req.getParameter("CvsReceiptNo")));
        mulPayResultEntity.setEdyReceiptNo(convBlankToNull(req.getParameter("EdyReceiptNo")));
        mulPayResultEntity.setEdyOrderNo(convBlankToNull(req.getParameter("EdyOrderNo")));
        mulPayResultEntity.setCustId(convBlankToNull(req.getParameter("CustId")));
        mulPayResultEntity.setBkCode(convBlankToNull(req.getParameter("BkCode")));
        mulPayResultEntity.setConfNo(convBlankToNull(req.getParameter("ConfNo")));
        mulPayResultEntity.setPaymentTerm(convBlankToNull(req.getParameter("PaymentTerm")));
        mulPayResultEntity.setEncryptReceiptNo(convBlankToNull(req.getParameter("EncryptReceiptNo")));
        mulPayResultEntity.setFinishDate(convBlankToNull(req.getParameter("FinishDate")));
        mulPayResultEntity.setReceiptDate(convBlankToNull(req.getParameter("ReceiptDate")));
        mulPayResultEntity.setRegistTime(dateUtility.getCurrentTime());
        mulPayResultEntity.setUpdateTime(dateUtility.getCurrentTime());
        mulPayResultEntity.setAmazonOrderReferenceID(mulPayBillEntity.getAmazonOrderReferenceID());
        mulPayResultEntity.setCancelAmount(conversionUtility.toBigDecimal(req.getParameter("CancelAmount")));
        mulPayResultEntity.setCancelTax(conversionUtility.toBigDecimal(req.getParameter("CancelTax")));

        mulPayResultEntity.setErrCode(convBlankToNull(req.getParameter("ErrCode")));
        mulPayResultEntity.setErrInfo(convBlankToNull(req.getParameter("ErrInfo")));

        return mulPayResultEntity;
    }

    /**
     * 文字列編集処理（空文字→Null）
     *
     * @param value 変換対象文字列
     * @return 変換後文字列
     */
    protected String convBlankToNull(String value) {
        return StringUtils.defaultIfEmpty(value, null);
    }

}
