package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import com.gmo_pg.g_pay.client.output.SearchMemberOutput;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmSaveMemberInput;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.memo.OrderMemoEntity;
import jp.co.hankyuhanshin.itec.hitmall.constant.PointUseType;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderReceiptOfMoneyEntity;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderInfoMasterDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderOtherDataDto;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInvoiceAttachmentFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;

import java.util.ArrayList;

import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;

import java.math.BigDecimal;

import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.member.SimultaneousOrderExclusionEntity;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderTempDto;

import com.gmo_pg.g_pay.client.output.SaveMemberOutput;
import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.utility.MulPayUtility;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SaveMemberLogicTest {

    @Autowired
    SaveMemberLogic saveMemberLogic;

    @MockBean
    MulPayUtility mulPayUtility;

    @MockBean
    SearchMemberLogic searchMemberLogic;

    @MockBean
    PaymentClient paymentClient;

    @Test
    @Order(1)
    public void execute() throws PaymentException {

        // 想定値設定
        SearchMemberOutput searchMemberOutput = new SearchMemberOutput();
        searchMemberOutput.setMemberList(new ArrayList<>());

        SaveMemberOutput result = new SaveMemberOutput();

        // モック設定
        doReturn("test").when(mulPayUtility).createPaymentMemberId(any());
        doReturn(searchMemberOutput).when(searchMemberLogic).execute(any(String.class));
        doReturn(result).when(paymentClient).doSaveMember(any(HmSaveMemberInput.class));

        // 試験実行
        ReceiveOrderDto dto = new ReceiveOrderDto();
        dto.setUsePoint(new BigDecimal("0"));
        dto.setOrderSummaryEntity(new OrderSummaryEntity());
        dto.setOrderIndexEntity(new OrderIndexEntity());
        dto.setOrderPersonEntity(new OrderPersonEntity());
        dto.setOrderDeliveryDto(new OrderDeliveryDto());
        dto.setOrderSettlementEntity(new OrderSettlementEntity());
        dto.setOrderNextSettlementEntity(new OrderSettlementEntity());
        dto.setOrderAdditionalChargeEntityList(new ArrayList<OrderAdditionalChargeEntity>());
        dto.setOrderBillEntity(new OrderBillEntity());
        dto.setOrderReceiptOfMoneyEntityList(new ArrayList<OrderReceiptOfMoneyEntity>());
        dto.setOrderMemoEntity(new OrderMemoEntity());
        dto.setSettlementMethodEntity(new SettlementMethodEntity());
        dto.setNextSettlementMethodEntity(new SettlementMethodEntity());
        dto.setMulPayBillEntity(new MulPayBillEntity());
        dto.setMasterDto(new OrderInfoMasterDto());
        dto.setSimultaneousOrderExclusionEntity(new SimultaneousOrderExclusionEntity());
        dto.setCoupon(new CouponEntity());
        dto.setApplyCouponFlg(false);
        dto.setOrderTempDto(new OrderTempDto());
        dto.setOrderOtherDataDto(new OrderOtherDataDto());
        dto.setAuthoryLimitDate(new Timestamp(new java.util.Date().getTime()));
        dto.setReAuthoryFlag(false);
        dto.setRecoveryAuthoryFlag(false);
        dto.setPointDiscountPrice(new BigDecimal("0"));
        dto.setPointUseType(PointUseType.NONE);
        dto.setActivatePoint(new BigDecimal("0"));
        dto.setAcquisitionPoint(new BigDecimal("0"));
        dto.setCouponCode("");
        dto.setOriginalCommission(new BigDecimal("0"));
        dto.setOriginalCarriage(new BigDecimal("0"));
        dto.setInvoiceAttachmentSetFlag(HTypeInvoiceAttachmentFlag.OFF);
        dto.setNextDeliveryDateTime(new Timestamp(new java.util.Date().getTime()));
        dto.setNextCarriage(new BigDecimal("0"));
        dto.setPeriodicOrderFlag(false);
        dto.setOrderWaitingMemo("");

        SaveMemberOutput actual = saveMemberLogic.execute(dto);

        // 戻り値及び呼び出し検証
        verify(mulPayUtility, times(1)).createPaymentMemberId(any());
        verify(searchMemberLogic, times(1)).execute(any(String.class));
        verify(paymentClient, times(1)).doSaveMember(any(HmSaveMemberInput.class));
        Assertions.assertEquals(result, actual);
    }
}
