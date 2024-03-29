package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmDeleteCardInput;
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

import com.gmo_pg.g_pay.client.output.DeleteCardOutput;
import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
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
public class DeleteCardLogicTest {

    @Autowired
    DeleteCardLogic deleteCardLogic;

    @MockBean
    PaymentClient paymentClient;

    @Test
    @Order(1)
    public void execute() throws PaymentException {

        // 想定値設定
        DeleteCardOutput result = new DeleteCardOutput();

        // モック設定
        doReturn(result).when(paymentClient).doDeleteCard(any(HmDeleteCardInput.class));

        // 試験実行
        ReceiveOrderDto receiveOrderDto = new ReceiveOrderDto();
        receiveOrderDto.setUsePoint(new BigDecimal("0"));
        receiveOrderDto.setOrderSummaryEntity(new OrderSummaryEntity());
        receiveOrderDto.setOrderIndexEntity(new OrderIndexEntity());
        receiveOrderDto.setOrderPersonEntity(new OrderPersonEntity());
        receiveOrderDto.setOrderDeliveryDto(new OrderDeliveryDto());
        receiveOrderDto.setOrderSettlementEntity(new OrderSettlementEntity());
        receiveOrderDto.setOrderNextSettlementEntity(new OrderSettlementEntity());
        receiveOrderDto.setOrderAdditionalChargeEntityList(new ArrayList<OrderAdditionalChargeEntity>());
        receiveOrderDto.setOrderBillEntity(new OrderBillEntity());
        receiveOrderDto.setOrderReceiptOfMoneyEntityList(new ArrayList<OrderReceiptOfMoneyEntity>());
        receiveOrderDto.setOrderMemoEntity(new OrderMemoEntity());
        receiveOrderDto.setSettlementMethodEntity(new SettlementMethodEntity());
        receiveOrderDto.setNextSettlementMethodEntity(new SettlementMethodEntity());
        receiveOrderDto.setMulPayBillEntity(new MulPayBillEntity());
        receiveOrderDto.setMasterDto(new OrderInfoMasterDto());
        receiveOrderDto.setSimultaneousOrderExclusionEntity(new SimultaneousOrderExclusionEntity());
        receiveOrderDto.setCoupon(new CouponEntity());
        receiveOrderDto.setApplyCouponFlg(false);
        receiveOrderDto.setOrderTempDto(new OrderTempDto());
        receiveOrderDto.setOrderOtherDataDto(new OrderOtherDataDto());
        receiveOrderDto.setAuthoryLimitDate(new Timestamp(new java.util.Date().getTime()));
        receiveOrderDto.setReAuthoryFlag(false);
        receiveOrderDto.setRecoveryAuthoryFlag(false);
        receiveOrderDto.setPointDiscountPrice(new BigDecimal("0"));
        receiveOrderDto.setPointUseType(PointUseType.NONE);
        receiveOrderDto.setActivatePoint(new BigDecimal("0"));
        receiveOrderDto.setAcquisitionPoint(new BigDecimal("0"));
        receiveOrderDto.setCouponCode("");
        receiveOrderDto.setOriginalCommission(new BigDecimal("0"));
        receiveOrderDto.setOriginalCarriage(new BigDecimal("0"));
        receiveOrderDto.setInvoiceAttachmentSetFlag(HTypeInvoiceAttachmentFlag.OFF);
        receiveOrderDto.setNextDeliveryDateTime(new Timestamp(new java.util.Date().getTime()));
        receiveOrderDto.setNextCarriage(new BigDecimal("0"));
        receiveOrderDto.setPeriodicOrderFlag(false);
        receiveOrderDto.setOrderWaitingMemo("");

        DeleteCardOutput actual = deleteCardLogic.execute(receiveOrderDto);

        // 戻り値及び呼び出し検証
        verify(paymentClient, times(1)).doDeleteCard(any(HmDeleteCardInput.class));
        Assertions.assertEquals(result, actual);
    }
}
