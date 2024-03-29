package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import com.gmo_pg.g_pay.client.input.EntryExecTranCvsInput;
import com.gmo_pg.g_pay.client.output.EntryExecTranCvsOutput;
import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.OrderTempDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConveniEntryExecTranLogicTest {

    @Autowired
    ConveniEntryExecTranLogic conveniEntryExecTranLogic;

    @MockBean
    PaymentClient paymentClient;

    @Test
    @Order(1)
    public void execute() throws PaymentException {

        // 想定値設定
        EntryExecTranCvsOutput result = new EntryExecTranCvsOutput();
        result.setAccessId("1");

        // モック設定
        doReturn(result).when(paymentClient).doEntryExecTranCvs(any(EntryExecTranCvsInput.class));

        // 試験実行
        ReceiveOrderDto dto = new ReceiveOrderDto();
        OrderPersonEntity orderPersonEntity = new OrderPersonEntity();
        orderPersonEntity.setOrderFirstName("野田");
        orderPersonEntity.setOrderLastName("阪神");
        orderPersonEntity.setOrderFirstKana("のだ");
        orderPersonEntity.setOrderLastKana("はんしん");
        orderPersonEntity.setOrderTel("1");

        OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();
        orderSummaryEntity.setOrderSeq(1);
        orderSummaryEntity.setOrderVersionNo(1);
        orderSummaryEntity.setOrderCode("1");
        orderSummaryEntity.setOrderPrice(new BigDecimal(1));

        SettlementMethodEntity settlementMethodEntity = new SettlementMethodEntity();
        settlementMethodEntity.setPaymentTimeLimitDayCount(1);

        OrderTempDto orderTempDto = new OrderTempDto();
        orderTempDto.setConvenience("1");

        dto.setOrderPersonEntity(orderPersonEntity);
        dto.setOrderSummaryEntity(orderSummaryEntity);
        dto.setSettlementMethodEntity(settlementMethodEntity);
        dto.setOrderTempDto(orderTempDto);

        EntryExecTranCvsOutput actual = conveniEntryExecTranLogic.execute(dto);

        // 戻り値及び呼び出し検証
        verify(paymentClient, times(1)).doEntryExecTranCvs(any(EntryExecTranCvsInput.class));
        Assertions.assertEquals(result.getAccessId(), actual.getAccessId());
    }
}
