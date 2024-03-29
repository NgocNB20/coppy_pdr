/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.aop.gmo;

import com.gmo_pg.g_pay.client.input.SecureTranInput;
import com.gmo_pg.g_pay.client.output.AlterTranOutput;
import com.gmo_pg.g_pay.client.output.ChangeTranOutput;
import com.gmo_pg.g_pay.client.output.DeleteCardOutput;
import com.gmo_pg.g_pay.client.output.DeleteMemberOutput;
import com.gmo_pg.g_pay.client.output.EntryExecTranCvsOutput;
import com.gmo_pg.g_pay.client.output.EntryExecTranPayEasyOutput;
import com.gmo_pg.g_pay.client.output.EntryTranOutput;
import com.gmo_pg.g_pay.client.output.ErrHolder;
import com.gmo_pg.g_pay.client.output.ExecTranOutput;
import com.gmo_pg.g_pay.client.output.ExecTranPayEasyOutput;
import com.gmo_pg.g_pay.client.output.SaveCardOutput;
import com.gmo_pg.g_pay.client.output.SaveMemberOutput;
import com.gmo_pg.g_pay.client.output.SearchCardOutput;
import com.gmo_pg.g_pay.client.output.SearchMemberOutput;
import com.gmo_pg.g_pay.client.output.SearchTradeOutput;
import com.gmo_pg.g_pay.client.output.SecureTranOutput;
import com.gmo_pg.g_pay.client.output.TradedCardOutput;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeJobCode;
import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.MulPayBillDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.MulPayShopDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.MulPaySiteDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CommunicateResult;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmAlterTranInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmChangeTranInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmDeleteCardInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmDeleteMemberInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmEntryExecTranCvsInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmEntryExecTranPayEasyInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmEntryTranInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmExecTranInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmSaveCardInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmSaveMemberInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmSearchCardInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmSearchMemberInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmTradedCardInput;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayShopEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPaySiteEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.MulPayBillLogic;
import jp.co.hankyuhanshin.itec.hitmall.util.common.EnumTypeUtil;
import jp.co.hankyuhanshin.itec.hmbase.util.common.CopyUtil;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.DateUtility;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 受注インターセプター<br/>
 * PaymentClientをコンテナ管理します<br/>
 *
 * @author yt23807
 */
@Order(2)
@Aspect
@Component
public class PaymentClientInterceptor {

    /**
     * ログ出力
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentClientInterceptor.class);

    /**
     * 決済方法　クレジット
     */
    protected static final String PAYTYPE_CREDIT = "0";
    /**
     * 決済方法　モバイルSuica
     */
    protected static final String PAYTYPE_SUICA = "1";
    /**
     * 決済方法　MobileEdy
     */
    protected static final String PAYTYPE_EDY = "2";
    /**
     * 決済方法　コンビニ
     */
    protected static final String PAYTYPE_CONVENI = "3";
    /**
     * 決済方法　Pay-easy
     */
    protected static final String PAYTYPE_PAYEASY = "4";

    /**
     * ペイジー決済タイプ
     */
    protected static final String PAYEASY_PAYMENTTYPE = "E";

    /**
     * マルチペイメント請求ロジック
     */
    protected MulPayBillLogic mulPayBillLogic;
    /**
     * マルチペイメント用ショップ設定Dao
     */
    protected MulPayShopDao mulPayShopDao;
    /**
     * マルチペイメント用サイト設定Dao
     */
    protected MulPaySiteDao mulPaySiteDao;
    /**
     * マルチペイメント請求Dao
     */
    protected MulPayBillDao mulPayBillDao;

    /**
     * コンストラクタ
     *
     * @param mulPayBillLogic
     * @param mulPayShopDao
     * @param mulPaySiteDao
     * @param mulPayBillDao
     */
    @Autowired
    public PaymentClientInterceptor(MulPayBillLogic mulPayBillLogic,
                                    MulPayShopDao mulPayShopDao,
                                    MulPaySiteDao mulPaySiteDao,
                                    MulPayBillDao mulPayBillDao) {
        this.mulPayBillLogic = mulPayBillLogic;
        this.mulPayShopDao = mulPayShopDao;
        this.mulPaySiteDao = mulPaySiteDao;
        this.mulPayBillDao = mulPayBillDao;
    }

    /**
     * 決済処理インターセプター<br/>
     *
     * @param invocation 対象となるTranメソッド
     * @return 出力パラメータ
     * @throws Throwable 例外
     */
    @Around("execution(* com.gmo_pg.g_pay.client.PaymentClient.*(..))")
    public Object invoke(ProceedingJoinPoint invocation) throws Throwable {

        Object obj = null;

        // 共通情報取得
        Integer shopSeq = 1001;

        // 対象となるメソッドを取得
        String methodName = invocation.getSignature().getName();

        // メソッドに応じて、インターセプター内の処理を振り分ける。
        if (methodName.equals("doEntryTran")) {
            obj = interceptDoEntryTran(invocation, shopSeq);
        } else if (methodName.equals("doExecTran")) {
            obj = interceptDoExecTran(invocation, shopSeq);
        } else if (methodName.equals("doSecureTran")) {
            obj = interceptDoSecureTran(invocation);
        } else if (methodName.equals("doAlterTran")) {
            obj = interceptDoAlterTran(invocation, shopSeq);
        } else if (methodName.equals("doChangeTran")) {
            obj = interceptDoChangeTran(invocation, shopSeq);
        } else if (methodName.equals("doEntryExecTranCvs")) {
            obj = interceptDoEntryExecTranCvs(invocation, shopSeq);
        } else if (methodName.equals("doEntryExecTranPayEasy")) {
            obj = interceptDoEntryExecTranPayEasy(invocation, shopSeq);
        } else if (methodName.equals("doSearchTrade")) {
            obj = interceptDoSearchTrade(invocation, shopSeq);
        } else if (methodName.equals("doSearchMember")) {
            obj = interceptDoSearchMember(invocation, shopSeq);
        } else if (methodName.equals("doSaveMember")) {
            obj = interceptDoSaveMember(invocation, shopSeq);
        } else if (methodName.equals("doDeleteMember")) {
            obj = interceptDoDeleteMember(invocation, shopSeq);
        } else if (methodName.equals("doTradedCard")) {
            obj = interceptDoTradedCard(invocation, shopSeq);
        } else if (methodName.equals("doSearchCard")) {
            obj = interceptDoSearchCard(invocation, shopSeq);
        } else if (methodName.equals("doSaveCard")) {
            obj = interceptDoSaveCard(invocation, shopSeq);
        } else if (methodName.equals("doDeleteCard")) {
            obj = interceptDoDeleteCard(invocation, shopSeq);
        } else {
            obj = interceptDoOtherPaymentMethod(invocation, shopSeq);
        }

        return obj;
    }

    /**
     * クレジット決済-取引登録インターセプター<br/>
     *
     * @param inv     実行メソッド
     * @param shopSeq ショップSEQ
     * @return メソッド実行戻り値
     * @throws Throwable 例外
     */
    protected Object interceptDoEntryTran(ProceedingJoinPoint inv, Integer shopSeq) throws Throwable {
        try {
            LOGGER.info("interceptDoEntryTran:START");

            /*
             * 前処理
             */
            // 引数を取得します。（EntryTranInput)
            Object[] obj = inv.getArgs();
            HmEntryTranInput input = (HmEntryTranInput) obj[0];
            // マルチペイショップ設定情報を取得します。
            MulPayShopEntity mulPayShopEntity = mulPayShopDao.getEntityByShopSeq(shopSeq);
            if (isNull(input.getShopId())) {
                input.setShopId(mulPayShopEntity.getShopId());
            }
            if (input.getShopPass() == null) {
                input.setShopPass(mulPayShopEntity.getShopPass());
            }

            input.setOrderId(mulPayBillLogic.getNextOrderId(shopSeq, input.getOrderSeq(), input.getOrderId()));
            input.setTdTenantName(mulPayShopEntity.getTdTenantName());

            /*
             * 本処理
             */
            EntryTranOutput outputParam = (EntryTranOutput) inv.proceed();

            /*
             * 後処理
             */
            MulPayBillEntity mulPayBillEntity = copyOutputProp(input, outputParam);
            mulPayBillEntity.setPayType(PAYTYPE_CREDIT);
            mulPayBillEntity.setTranType("EntryTran");
            // エラー内容を取得
            if (outputParam.isErrorOccurred()) {
                setErrorOutput(mulPayBillEntity, outputParam.getErrList());
            }
            // マルチペイメント請求TBL登録
            int result = mulPayBillDao.insert(mulPayBillEntity);
            if (result == 0) {
                LOGGER.warn("マルチペイメント請求TBLへの登録件数0件");
            }

            return outputParam;

        } finally {
            LOGGER.info("interceptDoEntryTran:END");
        }
    }

    /**
     * クレジット-決済実行インターセプター<br/>
     *
     * @param inv     実行メソッド
     * @param shopSeq ショップSEQ
     * @return メソッド実行戻り値
     * @throws Throwable 例外
     */
    protected Object interceptDoExecTran(ProceedingJoinPoint inv, Integer shopSeq) throws Throwable {
        try {
            LOGGER.info("interceptDoExecTran:START");

            /*
             * 前処理
             */
            // 引数を取得します。（ExecTranInput)
            Object[] obj = inv.getArgs();
            HmExecTranInput inputParam = (HmExecTranInput) obj[0];
            // MulPaySiteを取得
            MulPaySiteEntity mulPaySiteEntity = mulPaySiteDao.getEntity();
            CopyUtil.copyPropertiesIfDestIsNull(mulPaySiteEntity, inputParam, null);

            // 現在のショップに対応するMulPayShopを取得
            MulPayShopEntity mulPayShopEntity = mulPayShopDao.getEntityByShopSeq(shopSeq);
            CopyUtil.copyPropertiesIfDestIsNull(mulPayShopEntity, inputParam, null);

            // MulPayBillエンティティの取得
            String bufOrderId = null;
            MulPayBillEntity mulPayBillEntity = mulPayBillDao.getLatestEntityByAccessInfo(inputParam.getAccessId(),
                                                                                          inputParam.getAccessPass()
                                                                                         );
            bufOrderId = mulPayBillEntity.getOrderId();
            inputParam.setOrderId(bufOrderId);

            /*
             * メソッド実行
             */
            ExecTranOutput outputParam = (ExecTranOutput) inv.proceed();

            /*
             * 後処理
             */
            MulPayBillEntity mulPayBill = copyOutputProp(inputParam, outputParam);
            mulPayBill.setPayType(PAYTYPE_CREDIT);
            mulPayBill.setTranType("ExecTran");
            // エラー内容を取得
            if (outputParam.isErrorOccurred()) {
                setErrorOutput(mulPayBill, outputParam.getErrList());
            }
            // マルチペイメント請求TBL登録
            int result = mulPayBillDao.insert(mulPayBill);
            if (result == 0) {
                LOGGER.warn("マルチペイメント請求TBLへの登録件数0件");
            }

            return outputParam;

        } finally {
            LOGGER.info("interceptDoExecTran:END");
        }
    }

    /**
     * クレジット決済認証後決済実行インターセプター<br/>
     *
     * @param inv 実行メソッド
     * @return メソッド実行戻り値
     * @throws Throwable 例外
     */
    protected Object interceptDoSecureTran(ProceedingJoinPoint inv) throws Throwable {
        try {
            LOGGER.info("interceptDoSecureTran:START");

            /*
             * メソッド実行
             */
            SecureTranOutput outputParam = (SecureTranOutput) inv.proceed();

            /*
             * 後処理
             */
            // orderIdの取得チェック
            if (outputParam.getOrderId() == null) {
                // nullの場合は、マルチペイメント請求TBLには登録しない。
                LOGGER.warn("出力パラメータのオーダーＩＤがnullの為、マルチペイメント請求TBLへの登録無し");
                return outputParam;
            }

            // MulPayBillエンティティの取得
            MulPayBillEntity oldMulPayBillEntity = mulPayBillDao.getLatestEntityByOrderId(outputParam.getOrderId());

            // 引数を取得します。（SecureTranInput)
            Object[] obj = inv.getArgs();
            SecureTranInput inputParam = null;
            if (obj.length == 1) {
                inputParam = (SecureTranInput) obj[0];
            } else if (obj.length == 2) {
                inputParam = new SecureTranInput();
                inputParam.setPaRes((String) obj[0]);
                inputParam.setMd((String) obj[1]);
            }

            MulPayBillEntity mulPayBillEntity = copyOutputProp(inputParam, outputParam);
            mulPayBillEntity.setPayType(PAYTYPE_CREDIT);
            mulPayBillEntity.setTranType("SecureTran");

            CopyUtil.copyPropertiesIfDestIsNull(oldMulPayBillEntity, mulPayBillEntity, null);

            mulPayBillEntity.setMulPayBillSeq(null);

            // エラー内容を取得
            if (outputParam.isErrorOccurred()) {
                setErrorOutput(mulPayBillEntity, outputParam.getErrList());
            }
            // マルチペイメント請求TBL登録
            int result = mulPayBillDao.insert(mulPayBillEntity);
            if (result == 0) {
                LOGGER.warn("マルチペイメント請求TBLへの登録件数0件");
            }

            return outputParam;

        } finally {
            LOGGER.info("interceptDoSecureTran:END");
        }
    }

    /**
     * クレジット決済-決済変更インターセプター<br/>
     *
     * @param inv     実行メソッド
     * @param shopSeq ショップSEQ
     * @return メソッド実行戻り値
     * @throws Throwable 例外
     */
    protected Object interceptDoAlterTran(ProceedingJoinPoint inv, Integer shopSeq) throws Throwable {
        try {
            LOGGER.info("interceptDoAlterTran:START");

            /*
             * 前処理
             */
            // 引数を取得します。（AlterTranInput)
            Object[] obj = inv.getArgs();
            HmAlterTranInput inputParam = (HmAlterTranInput) obj[0];

            MulPayShopEntity mulPayShopEntity = mulPayShopDao.getEntityByShopSeq(shopSeq);
            if (inputParam.getShopId() == null) {
                inputParam.setShopId(mulPayShopEntity.getShopId());
            }
            if (inputParam.getShopPass() == null) {
                inputParam.setShopPass(mulPayShopEntity.getShopPass());
            }

            MulPayBillEntity mulPayBillEntity = mulPayBillDao.getLatestEntityByAccessInfo(inputParam.getAccessId(),
                                                                                          inputParam.getAccessPass()
                                                                                         );
            String bufOrderId = null;
            bufOrderId = mulPayBillEntity.getOrderId();

            /*
             * メソッド実行
             */
            AlterTranOutput outputParam = (AlterTranOutput) inv.proceed();

            // 処理済みの通信結果を保持
            CommunicateResult communicateResult = ApplicationContextUtility.getBean(CommunicateResult.class);
            boolean successFlag = !outputParam.isErrorOccurred();
            HTypeJobCode jobcode = EnumTypeUtil.getEnumFromValue(HTypeJobCode.class, inputParam.getJobCd());
            if (HTypeJobCode.SALES == jobcode) {
                communicateResult.addSaleFixTran(bufOrderId, successFlag);
            } else {
                communicateResult.addCancelTran(bufOrderId, successFlag);
            }

            /*
             * 後処理
             */
            MulPayBillEntity mulPayBill = copyOutputProp(inputParam, outputParam);
            mulPayBill.setPayType(PAYTYPE_CREDIT);
            mulPayBill.setTranType("AlterTran");
            mulPayBill.setOrderId(bufOrderId);
            // エラー内容を取得
            if (outputParam.isErrorOccurred()) {
                setErrorOutput(mulPayBill, outputParam.getErrList());
            }

            // マルチペイメント請求TBL登録
            int result = mulPayBillDao.insert(mulPayBill);
            if (result == 0) {
                LOGGER.warn("マルチペイメント請求TBLへの登録件数0件");
            }

            return outputParam;
        } finally {
            LOGGER.info("interceptDoAlterTran:END");
        }
    }

    /**
     * クレジット決済-金額変更インターセプター<br/>
     *
     * @param inv     実行メソッド
     * @param shopSeq ショップSEQ
     * @return メソッド実行戻り値
     * @throws Throwable 例外
     */
    protected Object interceptDoChangeTran(ProceedingJoinPoint inv, Integer shopSeq) throws Throwable {
        try {
            LOGGER.info("interceptDoChangeTran:START");
            /*
             * 前処理
             */
            // 引数を取得します。（ChangeTranInput)
            Object[] obj = inv.getArgs();
            HmChangeTranInput inputParam = (HmChangeTranInput) obj[0];

            MulPayShopEntity mulPayShopEntity = mulPayShopDao.getEntityByShopSeq(shopSeq);
            if (inputParam.getShopId() == null) {
                inputParam.setShopId(mulPayShopEntity.getShopId());
            }
            if (inputParam.getShopPass() == null) {
                inputParam.setShopPass(mulPayShopEntity.getShopPass());
            }
            MulPayBillEntity mulPayBillEntity = mulPayBillDao.getLatestEntityByAccessInfo(inputParam.getAccessId(),
                                                                                          inputParam.getAccessPass()
                                                                                         );
            String bufOrderId = null;
            bufOrderId = mulPayBillEntity.getOrderId();

            /*
             * メソッド実行
             */
            ChangeTranOutput outputParam = (ChangeTranOutput) inv.proceed();

            // 処理済みの通信結果を保持
            CommunicateResult communicateResult = ApplicationContextUtility.getBean(CommunicateResult.class);
            boolean successFlag = !outputParam.isErrorOccurred();
            communicateResult.addChangeTran(bufOrderId, successFlag);

            /*
             * 後処理
             */
            MulPayBillEntity mulPayBill = copyOutputProp(inputParam, outputParam);
            mulPayBill.setPayType(PAYTYPE_CREDIT);
            mulPayBill.setTranType("ChangeTran");
            mulPayBill.setOrderId(bufOrderId);
            // エラー内容を取得
            if (outputParam.isErrorOccurred()) {
                setErrorOutput(mulPayBill, outputParam.getErrList());
            }

            // マルチペイメント請求TBL登録
            int result = mulPayBillDao.insert(mulPayBill);
            if (result == 0) {
                LOGGER.warn("マルチペイメント請求TBLへの登録件数0件");
            }

            return outputParam;
        } finally {
            LOGGER.info("interceptDoChangeTran:END");
        }
    }

    /**
     * コンビニ決済-登録・決済実行インターセプター<br/>
     *
     * @param inv     実行メソッド
     * @param shopSeq ショップSEQ
     * @return メソッド実行戻り値
     * @throws Throwable 例外
     */
    protected Object interceptDoEntryExecTranCvs(ProceedingJoinPoint inv, Integer shopSeq) throws Throwable {
        try {
            LOGGER.info("interceptDoChangeTran:START");

            /*
             * 前処理
             */
            // 引数を取得します。（EntryExecTranCvsInput)
            Object[] obj = inv.getArgs();
            HmEntryExecTranCvsInput inputParam = (HmEntryExecTranCvsInput) obj[0];

            // MulPayShopエンティティの取得
            MulPayShopEntity mulPayShopEntity = mulPayShopDao.getEntityByShopSeq(shopSeq);
            // ショップ設定情報を入力パラメータへコピー
            CopyUtil.copyPropertiesIfDestIsNull(mulPayShopEntity, inputParam, null);

            // orderId取得
            inputParam.setOrderId(
                            mulPayBillLogic.getNextOrderId(shopSeq, inputParam.getOrderSeq(), inputParam.getOrderId()));
            obj[0] = inputParam;

            /*
             * メソッド実行
             */
            EntryExecTranCvsOutput outputParam = (EntryExecTranCvsOutput) inv.proceed();

            /*
             * 後処理
             */
            MulPayBillEntity mulPayBillEntity = copyOutputProp(inputParam, outputParam);
            mulPayBillEntity.setPayType(PAYTYPE_CONVENI);
            mulPayBillEntity.setTranType("EntryExecTranCvs");

            // エラー内容を取得
            if (outputParam.isEntryErrorOccurred()) {
                setErrorOutput(mulPayBillEntity, outputParam.getEntryErrList());
            } else if (outputParam.isExecErrorOccurred()) {
                setErrorOutput(mulPayBillEntity, outputParam.getExecErrList());
            }
            // マルチペイメント請求TBL登録
            int result = mulPayBillDao.insert(mulPayBillEntity);
            if (result == 0) {
                LOGGER.warn("マルチペイメント請求TBLへの登録件数0件");
            }
            return outputParam;

        } finally {
            LOGGER.info("interceptDoChangeTran:END");
        }
    }

    /**
     * PayEasy決済-登録・決済実行インターセプター<br/>
     *
     * @param inv     実行メソッド
     * @param shopSeq ショップSEQ
     * @return メソッド実行戻り値
     * @throws Throwable 例外
     */
    protected Object interceptDoEntryExecTranPayEasy(ProceedingJoinPoint inv, Integer shopSeq) throws Throwable {
        try {
            LOGGER.info("interceptDoEntryExecTranPayEasy:START");
            /*
             * 前処理
             */
            // 引数を取得します。（EntryExecTranPayEasyInput)
            Object[] obj = inv.getArgs();
            HmEntryExecTranPayEasyInput inputParam = (HmEntryExecTranPayEasyInput) obj[0];

            // MulPayShopエンティティの取得→inputParamへセット
            MulPayShopEntity mulPayShopEntity = mulPayShopDao.getEntityByShopSeq(shopSeq);
            // ショップ設定情報を入力パラメータへコピー
            CopyUtil.copyPropertiesIfDestIsNull(mulPayShopEntity, inputParam, null);

            // orderId取得
            inputParam.setOrderId(
                            mulPayBillLogic.getNextOrderId(shopSeq, inputParam.getOrderSeq(), inputParam.getOrderId()));
            obj[0] = inputParam;

            inputParam.setPaymentType(PAYEASY_PAYMENTTYPE);

            /*
             * メソッド実行
             */
            EntryExecTranPayEasyOutput outputParam = (EntryExecTranPayEasyOutput) inv.proceed();

            /*
             * 後処理
             */
            MulPayBillEntity mulPayBillEntity = copyOutputProp(inputParam, outputParam);
            mulPayBillEntity.setPayType(PAYTYPE_PAYEASY);
            mulPayBillEntity.setTranType("EntryExecTranPayEasy");

            // ペイジー戻り値検査
            if (StringUtils.isEmpty(mulPayBillEntity.getPaymentURL())) {
                List<ErrHolder> eList = new ArrayList<>();
                ErrHolder eh = new ErrHolder();
                eh.setErrCode("paymentURLが返却されていません");
                eh.setErrInfo("PAYMENTURL");
                eList.add(eh);
                ExecTranPayEasyOutput eo = new ExecTranPayEasyOutput();
                eo.setErrList(eList);
                outputParam.setExecTranOutput(eo);
                setErrorOutput(mulPayBillEntity, eList);
            }

            // エラー内容を取得
            if (outputParam.isEntryErrorOccurred()) {
                setErrorOutput(mulPayBillEntity, outputParam.getEntryErrList());
            } else if (outputParam.isExecErrorOccurred()) {
                setErrorOutput(mulPayBillEntity, outputParam.getExecErrList());
            }
            // マルチペイメント請求TBL登録
            int result = mulPayBillDao.insert(mulPayBillEntity);
            if (result == 0) {
                LOGGER.warn("マルチペイメント請求TBLへの登録件数0件");
            }
            return outputParam;
        } finally {
            LOGGER.info("interceptDoEntryExecTranPayEasy:END");
        }
    }

    /**
     * クレジットの状況確認用 SearchTrade 用インタセプト処理。<br />
     * MulPayBillへの保存要求がある場合に保存処理を行う
     *
     * @param inv     実行メソッド
     * @param shopSeq ショップSEQ
     * @return メソッド実行戻り値
     * @throws Throwable 例外
     */
    protected Object interceptDoSearchTrade(ProceedingJoinPoint inv, Integer shopSeq) throws Throwable {

        // メソッドを実行します。
        SearchTradeOutput outputDto = (SearchTradeOutput) this.interceptDoOtherPaymentMethod(inv, shopSeq);

        // エラーが含まれる場合はそのまま返す
        if (outputDto == null || outputDto.isErrorOccurred()) {
            return outputDto;
        }

        return outputDto;
    }

    /**
     * 会員照会用 SearchMember 用インタセプト処理。<br />
     *
     * @param inv     実行メソッド
     * @param shopSeq ショップSEQ
     * @return メソッド実行戻り値
     * @throws Throwable 例外
     */
    protected Object interceptDoSearchMember(ProceedingJoinPoint inv, Integer shopSeq) throws Throwable {

        try {
            LOGGER.info("interceptDoSearchMember:START");

            /*
             * 前処理
             */
            // 引数を取得します。（ExecTranInput)
            Object[] obj = inv.getArgs();
            HmSearchMemberInput inputParam = (HmSearchMemberInput) obj[0];
            // MulPaySiteを取得
            MulPaySiteEntity mulPaySiteEntity = mulPaySiteDao.getEntity();
            CopyUtil.copyPropertiesIfDestIsNull(mulPaySiteEntity, inputParam, null);

            inputParam.setSitePass(mulPaySiteEntity.getSitePassword());

            obj[0] = inputParam;

            /*
             * メソッド実行
             */
            SearchMemberOutput outputParam = (SearchMemberOutput) inv.proceed();

            return outputParam;

        } finally {
            LOGGER.info("interceptDoSearchMember:END");
        }
    }

    /**
     * 会員登録用 SaveMember 用インタセプト処理。<br />
     *
     * @param inv     実行メソッド
     * @param shopSeq ショップSEQ
     * @return メソッド実行戻り値
     * @throws Throwable 例外
     */
    protected Object interceptDoSaveMember(ProceedingJoinPoint inv, Integer shopSeq) throws Throwable {

        try {
            LOGGER.info("interceptDoSaveMember:START");

            /*
             * 前処理
             */
            // 引数を取得します。（ExecTranInput)
            Object[] obj = inv.getArgs();
            HmSaveMemberInput inputParam = (HmSaveMemberInput) obj[0];
            // MulPaySiteを取得
            MulPaySiteEntity mulPaySiteEntity = mulPaySiteDao.getEntity();
            CopyUtil.copyPropertiesIfDestIsNull(mulPaySiteEntity, inputParam, null);

            inputParam.setSitePass(mulPaySiteEntity.getSitePassword());

            obj[0] = inputParam;

            /*
             * メソッド実行
             */
            SaveMemberOutput outputParam = (SaveMemberOutput) inv.proceed();

            return outputParam;

        } finally {
            LOGGER.info("interceptDoSaveMember:END");
        }
    }

    /**
     * 会員削除用 doDeleteMember 用インタセプト処理
     *
     * @param inv     実行メソッド
     * @param shopSeq ショップSEQ
     * @return メソッド実行戻り値
     * @throws Throwable 例外
     */
    protected Object interceptDoDeleteMember(ProceedingJoinPoint inv, Integer shopSeq) throws Throwable {
        try {
            LOGGER.info("interceptDoDeleteMember:START");

            // --- 前処理

            // 引数を取得します。（ExecTranInput)
            Object[] obj = inv.getArgs();
            HmDeleteMemberInput inputParam = (HmDeleteMemberInput) obj[0];
            // MulPaySiteを取得
            MulPaySiteEntity mulPaySiteEntity = mulPaySiteDao.getEntity();
            CopyUtil.copyPropertiesIfDestIsNull(mulPaySiteEntity, inputParam, null);

            inputParam.setSitePass(mulPaySiteEntity.getSitePassword());

            // MulPayShopエンティティの取得→inputParamへセット
            MulPayShopEntity mulPayShopEntity = mulPayShopDao.getEntityByShopSeq(shopSeq);
            // ショップ設定情報を入力パラメータへコピー
            CopyUtil.copyPropertiesIfDestIsNull(mulPayShopEntity, inputParam, null);

            obj[0] = inputParam;

            // メソッド実行
            DeleteMemberOutput outputParam = (DeleteMemberOutput) inv.proceed();

            return outputParam;

        } finally {
            LOGGER.info("interceptDoDeleteMember:END");
        }
    }

    /**
     * 決済後カード登録用 doTradedCard 用インタセプト処理
     *
     * @param inv     実行メソッド
     * @param shopSeq ショップSEQ
     * @return メソッド実行戻り値
     * @throws Throwable 例外
     */
    protected Object interceptDoTradedCard(ProceedingJoinPoint inv, Integer shopSeq) throws Throwable {
        try {
            LOGGER.info("interceptDoTradedCard:START");

            // --- 前処理

            // 引数を取得します。（ExecTranInput)
            Object[] obj = inv.getArgs();
            HmTradedCardInput inputParam = (HmTradedCardInput) obj[0];
            // MulPaySiteを取得
            MulPaySiteEntity mulPaySiteEntity = mulPaySiteDao.getEntity();
            CopyUtil.copyPropertiesIfDestIsNull(mulPaySiteEntity, inputParam, null);

            inputParam.setSitePass(mulPaySiteEntity.getSitePassword());

            // MulPayShopエンティティの取得→inputParamへセット
            MulPayShopEntity mulPayShopEntity = mulPayShopDao.getEntityByShopSeq(shopSeq);
            // ショップ設定情報を入力パラメータへコピー
            CopyUtil.copyPropertiesIfDestIsNull(mulPayShopEntity, inputParam, null);

            obj[0] = inputParam;

            // メソッド実行
            TradedCardOutput outputParam = (TradedCardOutput) inv.proceed();

            return outputParam;
        } finally {
            LOGGER.info("interceptDoTradedCard:END");
        }
    }

    /**
     * カード照会用 doSearchCard 用インタセプト処理
     *
     * @param inv     実行メソッド
     * @param shopSeq ショップSEQ
     * @return メソッド実行戻り値
     * @throws Throwable 例外
     */
    protected Object interceptDoSearchCard(ProceedingJoinPoint inv, Integer shopSeq) throws Throwable {
        try {
            LOGGER.info("interceptDoSearchCard:START");

            // --- 前処理

            // 引数を取得します。（ExecTranInput)
            Object[] obj = inv.getArgs();
            HmSearchCardInput inputParam = (HmSearchCardInput) obj[0];
            // MulPaySiteを取得
            MulPaySiteEntity mulPaySiteEntity = mulPaySiteDao.getEntity();
            CopyUtil.copyPropertiesIfDestIsNull(mulPaySiteEntity, inputParam, null);

            inputParam.setSitePass(mulPaySiteEntity.getSitePassword());

            // MulPayShopエンティティの取得→inputParamへセット
            MulPayShopEntity mulPayShopEntity = mulPayShopDao.getEntityByShopSeq(shopSeq);
            // ショップ設定情報を入力パラメータへコピー
            CopyUtil.copyPropertiesIfDestIsNull(mulPayShopEntity, inputParam, null);

            obj[0] = inputParam;

            // メソッド実行
            SearchCardOutput outputParam = (SearchCardOutput) inv.proceed();

            return outputParam;
        } finally {
            LOGGER.info("interceptDoSearchCard:END");
        }
    }

    /**
     * カード登録用 doSaveCard 用インタセプト処理
     *
     * @param inv     実行メソッド
     * @param shopSeq ショップSEQ
     * @return メソッド実行戻り値
     * @throws Throwable 例外
     */
    protected Object interceptDoSaveCard(ProceedingJoinPoint inv, Integer shopSeq) throws Throwable {
        try {
            LOGGER.info("interceptDoSaveCard:START");

            // --- 前処理

            // 引数を取得します。（ExecTranInput)
            Object[] obj = inv.getArgs();
            HmSaveCardInput inputParam = (HmSaveCardInput) obj[0];
            // MulPaySiteを取得
            MulPaySiteEntity mulPaySiteEntity = mulPaySiteDao.getEntity();
            CopyUtil.copyPropertiesIfDestIsNull(mulPaySiteEntity, inputParam, null);

            inputParam.setSitePass(mulPaySiteEntity.getSitePassword());

            // MulPayShopエンティティの取得→inputParamへセット
            MulPayShopEntity mulPayShopEntity = mulPayShopDao.getEntityByShopSeq(shopSeq);
            // ショップ設定情報を入力パラメータへコピー
            CopyUtil.copyPropertiesIfDestIsNull(mulPayShopEntity, inputParam, null);

            obj[0] = inputParam;

            // メソッド実行
            SaveCardOutput outputParam = (SaveCardOutput) inv.proceed();

            return outputParam;
        } finally {
            LOGGER.info("interceptDoSaveCard:END");
        }
    }

    /**
     * カード削除用 doDeleteCard 用インタセプト処理
     *
     * @param inv     実行メソッド
     * @param shopSeq ショップSEQ
     * @return メソッド実行戻り値
     * @throws Throwable 例外
     */
    protected Object interceptDoDeleteCard(ProceedingJoinPoint inv, Integer shopSeq) throws Throwable {
        try {
            LOGGER.info("interceptDoDeleteCard:START");

            // --- 前処理

            // 引数を取得します。（ExecTranInput)
            Object[] obj = inv.getArgs();
            HmDeleteCardInput inputParam = (HmDeleteCardInput) obj[0];
            // MulPaySiteを取得
            MulPaySiteEntity mulPaySiteEntity = mulPaySiteDao.getEntity();
            CopyUtil.copyPropertiesIfDestIsNull(mulPaySiteEntity, inputParam, null);

            inputParam.setSitePass(mulPaySiteEntity.getSitePassword());

            // MulPayShopエンティティの取得→inputParamへセット
            MulPayShopEntity mulPayShopEntity = mulPayShopDao.getEntityByShopSeq(shopSeq);
            // ショップ設定情報を入力パラメータへコピー
            CopyUtil.copyPropertiesIfDestIsNull(mulPayShopEntity, inputParam, null);

            obj[0] = inputParam;

            // メソッド実行
            DeleteCardOutput outputParam = (DeleteCardOutput) inv.proceed();

            return outputParam;
        } finally {
            LOGGER.info("interceptDoDeleteCard:END");
        }
    }

    /**
     * マルチペイメントAPI用インターセプター（特定API以外）<br/>
     *
     * @param inv     実行メソッド
     * @param shopSeq ショップSEQ
     * @return メソッド実行戻り値
     * @throws Throwable 例外
     */
    protected Object interceptDoOtherPaymentMethod(ProceedingJoinPoint inv, Integer shopSeq) throws Throwable {

        try {
            LOGGER.info("interceptDoOtherPaymentMethod:START");

            // メソッドパラメータから、引数取得
            Object obj = inv.getArgs()[0];

            // サイト設定、ショップ設定情報をinputParamへコピーする
            MulPayShopEntity mulPayShopEntity = mulPayShopDao.getEntityByShopSeq(shopSeq);
            MulPaySiteEntity mulPaySiteEntity = mulPaySiteDao.getEntity();

            if (isInputParmName(obj.getClass().getName())) {

                CopyUtil.copyPropertiesIfDestIsNull(mulPayShopEntity, obj, null);
                CopyUtil.copyPropertiesIfDestIsNull(mulPaySiteEntity, obj, null);
                Class<?> clazz = obj.getClass();

                Method getMethod = null;
                Method setMethod = null;

                // orderIdメソッドを取り出す。
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.getName().equals("getOrderId")) {
                        getMethod = method;
                        setMethod = clazz.getMethod("setOrderId", method.getReturnType());
                        Object objOrderId = getMethod.invoke(obj);

                        if (objOrderId != null) {
                            String orderCode = (String) objOrderId;
                            String orderId = orderCode;

                            if (!orderCode.contains("-")) {
                                // 最新のorderIdを取得する。
                                MulPayBillEntity mulPayBillEntity = mulPayBillLogic.getMulPayBillByOrderCode(orderCode);
                                if (mulPayBillEntity != null) {
                                    orderId = mulPayBillEntity.getOrderId();
                                }
                            }

                            setMethod.invoke(obj, orderId);
                        }

                        break;
                    }
                }
            }

            // メソッドを実行します。
            Object retObj = inv.proceed();

            return retObj;
        } finally {
            LOGGER.info("interceptDoOtherPaymentMethod:END");
        }

    }

    /**
     * 引数オブジェクトが対象のものであるか判定<br/>
     * オブジェクト名が○○○Inputである場合は処理対象とする<br/>
     *
     * @param inputName 入力パラメータオブジェクト名
     * @return true:処理対象、false：非処理対象
     */
    protected boolean isInputParmName(String inputName) {

        if (inputName.endsWith("Input")) {
            return true;
        }
        return false;
    }

    /**
     * パラメータ移送と時刻セット（クレジット決済用）<br/>
     * 入力パラメータ、出力パラメータを登録用エンティティにデータ移送する。<br/>
     *
     * @param inputObject  入力パラメータ
     * @param outputObject 出力パラメータ
     * @return マルチペイメント請求エンティティ
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    protected MulPayBillEntity copyOutputProp(Object inputObject, Object outputObject)
                    throws IllegalAccessException, InvocationTargetException {

        // 日付関連Helper取得
        DateUtility dateUtility = ApplicationContextUtility.getBean(DateUtility.class);

        MulPayBillEntity mulPayBillEntity = ApplicationContextUtility.getBean(MulPayBillEntity.class);
        // プロパティ移送
        BeanUtils.copyProperties(mulPayBillEntity, inputObject);
        BeanUtils.copyProperties(mulPayBillEntity, outputObject);
        // 現在時刻のセット
        mulPayBillEntity.setRegistTime(dateUtility.getCurrentTime());
        mulPayBillEntity.setUpdateTime(dateUtility.getCurrentTime());

        return mulPayBillEntity;

    }

    /**
     * エラー情報設定メソッド<br/>
     * レスポンスからエラー情報を取得し、エンティティに設定します。<br/>
     *
     * @param mulPayBillEntity マルチペイメント請求エンティティ
     * @param errorList        エラーリスト
     * @return マルチペイメント請求エンティティ
     */
    protected MulPayBillEntity setErrorOutput(MulPayBillEntity mulPayBillEntity, List<?> errorList) {

        StringBuilder strBuffErrInfo = new StringBuilder();
        StringBuilder strBuffErrCode = new StringBuilder();

        int index = 0;
        @SuppressWarnings("unchecked")
        List<ErrHolder> errorList2 = (List<ErrHolder>) errorList;
        for (ErrHolder holder : errorList2) {
            if (index > 0) {
                strBuffErrInfo.append("|");
                strBuffErrCode.append("|");
            }
            strBuffErrInfo.append(holder.getErrInfo());
            strBuffErrCode.append(holder.getErrCode());
            index++;
        }

        mulPayBillEntity.setErrInfo(strBuffErrInfo.toString());
        mulPayBillEntity.setErrCode(strBuffErrCode.toString());

        return mulPayBillEntity;

    }

    /**
     * 文字列型のNULLチェック<br/>
     *
     * @param str チェック対象文字列
     * @return true:NULL false:NOT NULL
     */
    protected boolean isNull(String str) {
        if (str == null) {
            return true;
        }
        return false;
    }
}
