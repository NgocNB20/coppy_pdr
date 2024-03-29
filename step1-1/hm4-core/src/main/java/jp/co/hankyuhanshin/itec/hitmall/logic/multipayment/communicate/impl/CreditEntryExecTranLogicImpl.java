/*
 * Project Name : HIT-MALL4
 *
 * Copyright (C) 2021 i-TEC HANKYU HANSHIN INC. All Rights Reserved.
 *
 */

package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.impl;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import com.gmo_pg.g_pay.client.output.EntryExecTranOutput;
import com.gmo_pg.g_pay.client.output.EntryTranOutput;
import com.gmo_pg.g_pay.client.output.ExecTranOutput;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCardRegistType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeJobCode;
import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.MulPaySiteDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CardDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmEntryTranInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmExecTranInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmPaymentClientInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderTempDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPaySiteEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.AbstractShopLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandGetMaxCardBrandSeqLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.CardBrandRegistLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.CreditEntryExecTranLogic;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoGetService;
import jp.co.hankyuhanshin.itec.hitmall.service.memberinfo.MemberInfoUpdateService;
import jp.co.hankyuhanshin.itec.hitmall.utility.CommunicateUtility;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yt13605
 * @author Kaneko (itec) 2012/08/09 UtilからHelperへ変更　Util使用箇所をgetComponentで取得するように変更
 */
@Component
public class CreditEntryExecTranLogicImpl extends AbstractShopLogic implements CreditEntryExecTranLogic {

    /**
     * ロガー
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CreditEntryExecTranLogicImpl.class);

    /**
     * ＰＧカード決済サービスクライアント
     */
    private final PaymentClient paymentClient;

    /**
     * マルチペイメントサイトDao
     */
    private final MulPaySiteDao mulPaySiteDao;

    /**
     * 会員取得Sevice
     */
    private final MemberInfoGetService memberInfoGetService;

    /**
     * 会員更新Sevice
     */
    private final MemberInfoUpdateService memberInfoUpdateService;

    /**
     * カードブランド情報取得ロジック
     */
    private final CardBrandGetLogic cardBrandGetLogic;

    /**
     * MAXカードブランドSEQ取得ロジック
     */
    private final CardBrandGetMaxCardBrandSeqLogic cardBrandGetMaxCardBrandSeqLogic;

    /**
     * カードブランド情報登録ロジック
     */
    private final CardBrandRegistLogic cardBrandRegistLogic;

    @Autowired
    public CreditEntryExecTranLogicImpl(PaymentClient paymentClient,
                                        MulPaySiteDao mulPaySiteDao,
                                        MemberInfoGetService memberInfoGetService,
                                        MemberInfoUpdateService memberInfoUpdateService,
                                        CardBrandGetLogic cardBrandGetLogic,
                                        CardBrandGetMaxCardBrandSeqLogic cardBrandGetMaxCardBrandSeqLogic,
                                        CardBrandRegistLogic cardBrandRegistLogic) {
        this.paymentClient = paymentClient;
        this.mulPaySiteDao = mulPaySiteDao;
        this.memberInfoGetService = memberInfoGetService;
        this.memberInfoUpdateService = memberInfoUpdateService;
        this.cardBrandGetLogic = cardBrandGetLogic;
        this.cardBrandGetMaxCardBrandSeqLogic = cardBrandGetMaxCardBrandSeqLogic;
        this.cardBrandRegistLogic = cardBrandRegistLogic;
    }

    /**
     * 実行メソッド
     *
     * @param dto            受注DTO
     * @param enable3dSecure true=有効にする。false=無効にする。
     * @return 取引登録・決済出力パラメータ
     */
    @Override
    public EntryExecTranOutput execute(ReceiveOrderDto dto, boolean enable3dSecure) {
        EntryExecTranOutput res = new EntryExecTranOutput();

        EntryTranOutput entryTranOutput = this.doEntryTran(dto, enable3dSecure);
        res.setEntryTranOutput(entryTranOutput);

        if (entryTranOutput.isErrorOccurred()) {
            return res;
        }

        ExecTranOutput execTranOutput = this.doExecTran(dto, entryTranOutput, enable3dSecure);
        res.setExecTranOutput(execTranOutput);

        return res;
    }

    /**
     * 取引登録実行メソッド
     *
     * @param dto            受注DTO
     * @param enable3dSecure true=有効にする。false=無効にする。
     * @return 取引登録出力パラメータ
     */
    public EntryTranOutput doEntryTran(ReceiveOrderDto dto, boolean enable3dSecure) {
        HmEntryTranInput input = this.getEntryTranInput(dto, enable3dSecure);

        EntryTranOutput output = null;

        try {
            output = paymentClient.doEntryTran(input);
        } catch (PaymentException e) {
            LOGGER.error("例外処理が発生しました", e);
            this.throwMessage(MSGCD_PAYMENT_COM_FAIL, null, e);
        }
        return output;
    }

    /**
     * 取引登録入力パラメータの取得
     *
     * @param dto            受注DTO
     * @param enable3dSecure true=有効にする。false=無効にする。
     * @return 取引登録入力パラメータ
     */
    protected HmEntryTranInput getEntryTranInput(ReceiveOrderDto dto, boolean enable3dSecure) {
        HmEntryTranInput input = new HmEntryTranInput();

        OrderSummaryEntity orderSummaryEntity = dto.getOrderSummaryEntity();

        SettlementMethodEntity settlementMethodEntity = dto.getSettlementMethodEntity();

        HTypeJobCode jobcode;
        if (HTypeBillType.PRE_CLAIM == settlementMethodEntity.getBillType()) {
            jobcode = HTypeJobCode.CAPTURE;
        } else {
            jobcode = HTypeJobCode.AUTH;
        }
        input.setJobCd(jobcode.getValue());

        input.setOrderSeq(orderSummaryEntity.getOrderSeq());
        input.setOrderVersionNo(orderSummaryEntity.getOrderVersionNo());
        input.setOrderId(orderSummaryEntity.getOrderCode());
        input.setAmount(orderSummaryEntity.getOrderPrice().intValue());

        if (enable3dSecure) {
            input.setTdFlag(settlementMethodEntity.getEnable3dSecure().getValue());
        }

        return input;
    }

    /**
     * 決済実行メソッド
     *
     * @param dto             受注DTO
     * @param entryTranOutput 取引登録出力パラメータ
     * @param enable3dSecure  true=有効にする。false=無効にする。
     * @return 決済実行出力パラメータ
     */
    public ExecTranOutput doExecTran(ReceiveOrderDto dto, EntryTranOutput entryTranOutput, boolean enable3dSecure) {
        HmExecTranInput input = this.getExecTranInput(dto, entryTranOutput);

        if (enable3dSecure) {
            this.set3dSecureParam(input);
        }

        ExecTranOutput output = null;

        try {
            output = paymentClient.doExecTran(input);
        } catch (PaymentException e) {
            LOGGER.error("例外処理が発生しました", e);
            this.throwMessage(MSGCD_PAYMENT_COM_FAIL, null, e);
        }

        if (!output.isErrorOccurred()) {
            if (dto.getOrderTempDto().isUseRegistCardFlg()) {
                updateMemberInfo(
                                dto.getOrderSummaryEntity().getMemberInfoSeq(),
                                dto.getOrderTempDto().getPaymentMemberId()
                                );
            }
        }

        return output;
    }

    /**
     * 会員情報を更新する<br/>
     *
     * @param memberInfoSeq   会員SEQ
     * @param paymentMemberId 決済代行会員ID
     */
    protected void updateMemberInfo(Integer memberInfoSeq, String paymentMemberId) {
        // 会員情報取得
        MemberInfoEntity memberInfoEntity = memberInfoGetService.execute(memberInfoSeq);

        // 会員のカード登録種別が既に認証済みであれば処理しない
        if (HTypeCardRegistType.AUTHENTICATED.equals(memberInfoEntity.getPaymentCardRegistType())) {
            return;
        }
        // クレジットカード保持種別を更新する
        memberInfoEntity.setPaymentCardRegistType(HTypeCardRegistType.AUTHENTICATED);
        memberInfoEntity.setPaymentMemberId(paymentMemberId);

        memberInfoUpdateService.execute(memberInfoEntity);
    }

    /**
     * 決済実行入力パラメータ取得
     *
     * @param dto             受注DTO
     * @param entryTranOutput 取引登録出力パラメータ
     * @return 決済実行入力パラメータ
     */
    protected HmExecTranInput getExecTranInput(ReceiveOrderDto dto, EntryTranOutput entryTranOutput) {
        HmExecTranInput input = new HmExecTranInput();

        OrderSummaryEntity orderSummaryEntity = dto.getOrderSummaryEntity();
        OrderTempDto orderTempDto = dto.getOrderTempDto();

        input.setAccessId(entryTranOutput.getAccessId());
        input.setAccessPass(entryTranOutput.getAccessPass());

        input.setOrderSeq(orderSummaryEntity.getOrderSeq());
        input.setOrderVersionNo(orderSummaryEntity.getOrderVersionNo());
        input.setOrderId(orderSummaryEntity.getOrderCode());

        HTypeJobCode jobcode;
        SettlementMethodEntity settlementMethodEntity = dto.getSettlementMethodEntity();
        if (HTypeBillType.PRE_CLAIM == settlementMethodEntity.getBillType()) {
            jobcode = HTypeJobCode.CAPTURE;
        } else {
            jobcode = HTypeJobCode.AUTH;
        }
        input.setJobCd(jobcode.getValue());
        input.setMethod(orderTempDto.getMethod());
        input.setPayTimes(orderTempDto.getPayTimes());
        input.setAmount(orderSummaryEntity.getOrderPrice().intValue());
        // HM361では下記コメントアウト箇所は未記述
        // input.setCardNo(orderTempDto.cardNo);
        // input.setExpire(orderTempDto.getExpire());
        // 新規カードの場合セキュリティコードは渡ってこない
        // input.setSecurityCode(orderTempDto.getSecurityCode());
        input.setToken(orderTempDto.getToken());

        if (dto.getOrderTempDto().isRegistCredit() && dto.getOrderTempDto().isUseRegistCardFlg()) {
            // 登録済みカードを利用する場合
            MulPaySiteEntity mulPaySiteEntity = mulPaySiteDao.getEntity();
            input.setSiteId(mulPaySiteEntity.getSiteId());
            input.setSitePass(mulPaySiteEntity.getSitePassword());
            input.setMemberId(dto.getOrderTempDto().getPaymentMemberId());
            input.setSeqMode(HmPaymentClientInput.SEQ_MODE_LOGICAL);
            input.setCardSeq(HmPaymentClientInput.CARD_SEQ);
            input.setSecurityCode(orderTempDto.getSecurityCode());
            input.setToken(null);
        }

        return input;
    }

    /**
     * 3Dｾｷｭｱ対応有効時のパラメータを設定
     *
     * @param input 決済実行入力パラメータ
     */
    protected void set3dSecureParam(HmExecTranInput input) {
        HttpServletRequest request =
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String deviceCategory = "0";

        /* 3Dｾｷｭｱ対応 */
        input.setHttpAccept(request.getHeader(HTTP_ACCEPT));
        input.setHttpUserAgent(request.getHeader(HTTP_USER_AGENT));
        input.setDeviceCategory(deviceCategory);
    }

    /**
     * 取引登録・決済出力用エラーチェック
     *
     * @param output 取引登録・決済出力パラメータ
     * @return エラーなし：null、エラーあり：List<CheckMessageDto>
     */
    @Override
    public List<CheckMessageDto> checkOutput(EntryExecTranOutput output) {

        // 通信Helper取得
        CommunicateUtility communicateUtility = ApplicationContextUtility.getBean(CommunicateUtility.class);

        if (output.isEntryErrorOccurred()) {
            return communicateUtility.checkOutput(output.getEntryTranOutput());
        } else if (output.isExecErrorOccurred()) {
            return communicateUtility.checkOutput(output.getExecTranOutput());
        }
        return null;
    }

    /**
     * カード有効性チェック<br />
     *
     * @param cardDto カード登録情報
     * @return 有効性チェック実行出力パラメータ
     */
    @Override
    public EntryExecTranOutput executeCheck(CardDto cardDto) {

        EntryExecTranOutput res = new EntryExecTranOutput();

        EntryTranOutput entryTranOutput = this.doEntryTranForCheck(cardDto);
        res.setEntryTranOutput(entryTranOutput);

        if (entryTranOutput.isErrorOccurred()) {
            return res;
        }

        ExecTranOutput execTranOutput = this.doExecTranForCheck(cardDto, entryTranOutput);
        res.setExecTranOutput(execTranOutput);

        return res;
    }

    /**
     * 有効性チェック実行メソッド
     *
     * @param cardDto カード登録情報
     * @return 有効性チェック出力パラメータ
     */
    public EntryTranOutput doEntryTranForCheck(CardDto cardDto) {
        HmEntryTranInput input = this.getEntryTranInputForCheck(cardDto);

        EntryTranOutput output = null;
        try {
            output = paymentClient.doEntryTran(input);
        } catch (PaymentException e) {
            LOGGER.error("例外処理が発生しました", e);
            this.throwMessage(MSGCD_PAYMENT_COM_FAIL, null, e);
        }
        return output;
    }

    /**
     * 有効性チェック入力パラメータの取得
     *
     * @param cardDto カード登録情報
     * @return 有効性チェック入力パラメータ
     */
    protected HmEntryTranInput getEntryTranInputForCheck(CardDto cardDto) {
        HmEntryTranInput input = new HmEntryTranInput();

        input.setJobCd(HTypeJobCode.CHECK.getValue());
        input.setOrderId(cardDto.getOrderCode());
        input.setOrderSeq(cardDto.getOrderSeq());
        input.setOrderVersionNo(cardDto.getOrderVersionNo());

        return input;
    }

    /**
     * 有効性チェック実行メソッド
     *
     * @param cardDto         カード登録情報
     * @param entryTranOutput 有効性チェック出力パラメータ
     * @return 有効性チェック実行出力パラメータ
     */
    public ExecTranOutput doExecTranForCheck(CardDto cardDto, EntryTranOutput entryTranOutput) {
        HmExecTranInput input = this.getExecTranInputForCheck(cardDto, entryTranOutput);
        ExecTranOutput output = null;
        try {
            output = paymentClient.doExecTran(input);
        } catch (PaymentException e) {
            LOGGER.error("例外処理が発生しました", e);
            this.throwMessage(MSGCD_PAYMENT_COM_FAIL, null, e);
        }
        return output;
    }

    /**
     * 有効性チェック実行入力パラメータ取得
     *
     * @param cardDto         カード登録情報
     * @param entryTranOutput 有効性チェック出力パラメータ
     * @return 有効性チェック実行入力パラメータ
     */
    protected HmExecTranInput getExecTranInputForCheck(CardDto cardDto, EntryTranOutput entryTranOutput) {
        HmExecTranInput input = new HmExecTranInput();

        input.setAccessId(entryTranOutput.getAccessId());
        input.setAccessPass(entryTranOutput.getAccessPass());
        input.setJobCd(HTypeJobCode.CHECK.getValue());
        input.setOrderId(cardDto.getOrderCode());
        input.setOrderSeq(cardDto.getOrderSeq());
        input.setOrderVersionNo(cardDto.getOrderVersionNo());
        input.setCardNo(cardDto.getCardNumber());
        input.setExpire(cardDto.getExpirationDate());
        input.setToken(cardDto.getToken());

        if (cardDto.isRegistCredit() && cardDto.isUseRegistCardFlg()) {
            // 登録済みカードを利用する場合
            MulPaySiteEntity mulPaySiteEntity = mulPaySiteDao.getEntity();
            input.setSiteId(mulPaySiteEntity.getSiteId());
            input.setSitePass(mulPaySiteEntity.getSitePassword());
            input.setMemberId(cardDto.getPaymentMemberId());
            input.setSeqMode(HmPaymentClientInput.SEQ_MODE_LOGICAL);
            input.setCardSeq(HmPaymentClientInput.CARD_SEQ);
            input.setSecurityCode(cardDto.getSecurityCode());
            input.setToken(null);
        }

        return input;
    }
}
