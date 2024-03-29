package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import com.gmo_pg.g_pay.client.output.AlterTranOutput;
import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmAlterTranInput;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.settlement.OrderSettlementEntity;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreditAlterTranLogicTest {

    @Autowired
    CreditAlterTranLogic creditAlterTranLogic;

    @MockBean
    PaymentClient paymentClient;

    @Test
    @Order(1)
    public void execute() throws PaymentException {

        // 想定値設定
        AlterTranOutput result = new AlterTranOutput();
        result.setAccessId("1");

        // モック設定
        doReturn(result).when(paymentClient).doAlterTran(any(HmAlterTranInput.class));

        // 試験実行
        ReceiveOrderDto dto = new ReceiveOrderDto();

        MulPayBillEntity mulPayBillEntity = new MulPayBillEntity();
        mulPayBillEntity.setJobCd("CANCEL");
        mulPayBillEntity.setOrderSeq(1);
        mulPayBillEntity.setOrderVersionNo(1);
        mulPayBillEntity.setAccessId("1");
        mulPayBillEntity.setAccessPass("1");
        mulPayBillEntity.setMethod("1");
        mulPayBillEntity.setPayTimes(1);

        OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();
        orderSummaryEntity.setOrderPrice(new BigDecimal(1));
        orderSummaryEntity.setOrderStatus(HTypeOrderStatus.PAYMENT_CONFIRMING);

        OrderSettlementEntity orderSettlementEntity = new OrderSettlementEntity();
        orderSettlementEntity.setBillType(HTypeBillType.PRE_CLAIM);

        dto.setMulPayBillEntity(mulPayBillEntity);
        dto.setOrderSummaryEntity(orderSummaryEntity);
        dto.setOrderSettlementEntity(orderSettlementEntity);

        AlterTranOutput actual = creditAlterTranLogic.doReTran(dto);

        // 戻り値及び呼び出し検証
        verify(paymentClient, times(1)).doAlterTran(any(HmAlterTranInput.class));
        Assertions.assertEquals(result.getAccessId(), actual.getAccessId());
    }
}
