package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import com.gmo_pg.g_pay.client.output.EntryExecTranOutput;
import com.gmo_pg.g_pay.client.output.EntryTranOutput;
import com.gmo_pg.g_pay.client.output.ExecTranOutput;
import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCancelFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCarrierType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryCycle;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeviceType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEffectiveFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailRequired;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMemberRank;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderAgeType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderSex;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePointActivateFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePointType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRepeatPurchaseType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSalesFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSend;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodCommissionType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodPriceCommissionFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeWaitingFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.cart.CartDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmEntryTranInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmExecTranInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderTempDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.questionnaire.QuestionnaireReplyDisplayDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.settlement.SettlementDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.questionnaire.QuestionnaireEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreditEntryExecTranLogicTest {

    @Autowired
    CreditEntryExecTranLogic creditEntryExecTranLogic;

    @MockBean
    PaymentClient paymentClient;

    @Test
    @Order(1)
    public void execute() throws PaymentException {

        // 想定値設定
        EntryTranOutput entryTranOutput = new EntryTranOutput();
        entryTranOutput.setAccessId("");
        entryTranOutput.setAccessPass("");
        entryTranOutput.setCsvResponse("");

        ExecTranOutput execTranOutput = new ExecTranOutput();

        // モック設定
        doReturn(entryTranOutput).when(paymentClient).doEntryTran(any(HmEntryTranInput.class));
        doReturn(execTranOutput).when(paymentClient).doExecTran(any(HmExecTranInput.class));

        // 試験実行
        ReceiveOrderDto dto = new ReceiveOrderDto();
        OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();
        orderSummaryEntity.setUsePoint(new BigDecimal("0"));
        orderSummaryEntity.setOrderSeq(0);
        orderSummaryEntity.setOrderVersionNo(0);
        orderSummaryEntity.setOrderCode("");
        orderSummaryEntity.setOrderType(HTypeOrderType.NORMAL);
        orderSummaryEntity.setOrderTime(new Timestamp(new java.util.Date().getTime()));
        orderSummaryEntity.setSalesTime(new Timestamp(new java.util.Date().getTime()));
        orderSummaryEntity.setCancelTime(new Timestamp(new java.util.Date().getTime()));
        orderSummaryEntity.setSalesFlag(HTypeSalesFlag.OFF);
        orderSummaryEntity.setCancelFlag(HTypeCancelFlag.OFF);
        orderSummaryEntity.setWaitingFlag(HTypeWaitingFlag.OFF);
        orderSummaryEntity.setOrderStatus(HTypeOrderStatus.PAYMENT_CONFIRMING);
        orderSummaryEntity.setBeforeDiscountOrderPrice(new BigDecimal("0"));
        orderSummaryEntity.setOrderPrice(new BigDecimal("0"));
        orderSummaryEntity.setReceiptPriceTotal(new BigDecimal("0"));
        orderSummaryEntity.setOrderSiteType(HTypeSiteType.FRONT_PC);
        orderSummaryEntity.setOrderDeviceType(HTypeDeviceType.PC);
        orderSummaryEntity.setCarrierType(HTypeCarrierType.PC);
        orderSummaryEntity.setPeriodicOrderSeq(0);
        orderSummaryEntity.setSettlementMethodSeq(0);
        orderSummaryEntity.setTaxSeq(0);
        orderSummaryEntity.setMemberInfoSeq(0);
        orderSummaryEntity.setMemberRank(HTypeMemberRank.GUEST);
        orderSummaryEntity.setPrefectureType(HTypePrefectureType.HOKKAIDO);
        orderSummaryEntity.setOrderSex(HTypeOrderSex.UNKNOWN);
        orderSummaryEntity.setOrderAgeType(HTypeOrderAgeType.AGE_0TO9);
        orderSummaryEntity.setRepeatPurchaseType(HTypeRepeatPurchaseType.GUEST);
        orderSummaryEntity.setSettlementMailRequired(HTypeMailRequired.NO_NEED);
        orderSummaryEntity.setReminderSentFlag(HTypeSend.UNSENT);
        orderSummaryEntity.setExpiredSentFlag(HTypeSend.UNSENT);
        orderSummaryEntity.setPointActivateFlag(HTypePointActivateFlag.OFF);
        orderSummaryEntity.setUserAgent("");
        orderSummaryEntity.setFreeAreaKey("");
        orderSummaryEntity.setOrderPointAddBasePrice(new BigDecimal("0"));
        orderSummaryEntity.setOrderPointAddRate(new BigDecimal("0"));
        orderSummaryEntity.setShopSeq(0);
        orderSummaryEntity.setVersionNo(0);
        orderSummaryEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        orderSummaryEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        orderSummaryEntity.setOrderGoodsVersionNo(0);
        orderSummaryEntity.setPointSeq(0);
        orderSummaryEntity.setPointVersionNo(0);
        orderSummaryEntity.setCouponDiscountPrice(new BigDecimal("0"));
        orderSummaryEntity.setPointDiscountPrice(new BigDecimal("0"));
        orderSummaryEntity.setPointType(HTypePointType.TEMPORARY);
        orderSummaryEntity.setPoint(new BigDecimal("0"));
        orderSummaryEntity.setTotalAcquisitionPoint(new BigDecimal("0"));
        orderSummaryEntity.setSettlementMethodName("");
        orderSummaryEntity.setSettlementMethodDisplayNamePC("");
        orderSummaryEntity.setSettlementMethodDisplayNameMB("");
        orderSummaryEntity.setReceiverTimeZone("");
        orderSummaryEntity.setOrderName("");
        orderSummaryEntity.setOrderKana("");
        orderSummaryEntity.setOrderTel("");
        orderSummaryEntity.setOrderContactTel("");
        orderSummaryEntity.setOrderMail("");
        orderSummaryEntity.setReceiverName("");
        orderSummaryEntity.setReceiverKana("");
        orderSummaryEntity.setReceiverTel("");
        orderSummaryEntity.setOrderConsecutiveNo(0);
        orderSummaryEntity.setDeliveryCode("");
        orderSummaryEntity.setShipmentStatus("");
        orderSummaryEntity.setDeliveryNote("");
        orderSummaryEntity.setReceiptTime(new Timestamp(new java.util.Date().getTime()));
        orderSummaryEntity.setEmergencyFlag(HTypeEmergencyFlag.OFF);
        orderSummaryEntity.setPaymentStatus("");
        orderSummaryEntity.setDeliveryMethodName("");
        orderSummaryEntity.setOrderStatusForSearchResult("");

        dto.setOrderSummaryEntity(orderSummaryEntity);

        SettlementMethodEntity settlementMethodEntity = new SettlementMethodEntity();
        settlementMethodEntity.setSettlementMethodSeq(0);
        settlementMethodEntity.setShopSeq(0);
        settlementMethodEntity.setSettlementMethodName("");
        settlementMethodEntity.setSettlementMethodDisplayNamePC("");
        settlementMethodEntity.setSettlementMethodDisplayNameMB("");
        settlementMethodEntity.setOpenStatusPC(HTypeOpenDeleteStatus.NO_OPEN);
        settlementMethodEntity.setOpenStatusMB(HTypeOpenDeleteStatus.NO_OPEN);
        settlementMethodEntity.setSettlementNotePC("");
        settlementMethodEntity.setSettlementNoteMB("");
        settlementMethodEntity.setSettlementMethodType(HTypeSettlementMethodType.DISCOUNT);
        settlementMethodEntity.setSettlementMethodCommissionType(HTypeSettlementMethodCommissionType.FLAT_YEN);
        settlementMethodEntity.setBillType(HTypeBillType.PRE_CLAIM);
        settlementMethodEntity.setDeliveryMethodSeq(0);
        settlementMethodEntity.setEqualsCommission(new BigDecimal("0"));
        settlementMethodEntity.setSettlementMethodPriceCommissionFlag(HTypeSettlementMethodPriceCommissionFlag.FLAT);
        settlementMethodEntity.setLargeAmountDiscountPrice(new BigDecimal("0"));
        settlementMethodEntity.setLargeAmountDiscountCommission(new BigDecimal("0"));
        settlementMethodEntity.setOrderDisplay(0);
        settlementMethodEntity.setMaxPurchasedPrice(new BigDecimal("0"));
        settlementMethodEntity.setPaymentTimeLimitDayCount(0);
        settlementMethodEntity.setMinPurchasedPrice(new BigDecimal("0"));
        settlementMethodEntity.setCancelTimeLimitDayCount(0);
        settlementMethodEntity.setSettlementMailRequired(HTypeMailRequired.NO_NEED);
        settlementMethodEntity.setEnableCardNoHolding(HTypeEffectiveFlag.INVALID);
        settlementMethodEntity.setEnableSecurityCode(HTypeEffectiveFlag.INVALID);
        settlementMethodEntity.setEnable3dSecure(HTypeEffectiveFlag.INVALID);
        settlementMethodEntity.setEnableInstallment(HTypeEffectiveFlag.INVALID);
        settlementMethodEntity.setEnableBonusSinglePayment(HTypeEffectiveFlag.INVALID);
        settlementMethodEntity.setEnableBonusInstallment(HTypeEffectiveFlag.INVALID);
        settlementMethodEntity.setEnableRevolving(HTypeEffectiveFlag.INVALID);
        settlementMethodEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        settlementMethodEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        dto.setSettlementMethodEntity(settlementMethodEntity);

        OrderTempDto orderTempDto = new OrderTempDto();
        orderTempDto.setExpire("");
        orderTempDto.setSecurityCode("");
        orderTempDto.setMethod("");
        orderTempDto.setPayTimes(0);
        orderTempDto.setConvenience("");
        orderTempDto.setConveniName("");
        orderTempDto.setConveniSeq(0);
        orderTempDto.setAcsUrl("");
        orderTempDto.setPaReq("");
        orderTempDto.setPaymentMemberId("");
        orderTempDto.setUseRegistCardFlg(false);
        orderTempDto.setSaveFlg(false);
        orderTempDto.setRegistCredit(false);
        orderTempDto.setCartDto(new CartDto());
        orderTempDto.setCanUseCouponSettlementFlg(false);
        orderTempDto.setCanUseAllPointSettlementFlg(false);
        orderTempDto.setToken("");
        orderTempDto.setQuestionnaireReadFlag(false);
        orderTempDto.setQuestionnaireEntity(new QuestionnaireEntity());
        orderTempDto.setQuestionnaireReplyDisplayDtoList(new ArrayList<QuestionnaireReplyDisplayDto>());
        orderTempDto.setPreCreditInformationFlag(false);

        dto.setOrderTempDto(orderTempDto);

        EntryExecTranOutput actual = creditEntryExecTranLogic.execute(dto, false);

        // 戻り値及び呼び出し検証
        verify(paymentClient, times(1)).doEntryTran(any(HmEntryTranInput.class));
        verify(paymentClient, times(1)).doExecTran(any(HmExecTranInput.class));
        Assertions.assertEquals(execTranOutput, actual.getExecTranOutput());
    }
}
