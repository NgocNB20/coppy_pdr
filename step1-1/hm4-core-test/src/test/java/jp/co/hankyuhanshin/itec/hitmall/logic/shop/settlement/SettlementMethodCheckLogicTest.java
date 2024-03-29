package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSettlementMethodType;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.settlement.SettlementMethodDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodEntity;
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
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SettlementMethodCheckLogicTest {

    @Autowired
    SettlementMethodCheckLogic settlementMethodCheckLogic;

    @MockBean
    SettlementMethodDao settlementMethodDao;

    @Test
    @Order(1)
    public void execute() {
        HTypeSettlementMethodType settlementMethodType = HTypeSettlementMethodType.BANK_TRANSFER;
        HTypeOpenDeleteStatus openStatusPC = HTypeOpenDeleteStatus.NO_OPEN;
        HTypeOpenDeleteStatus openStatusMB = HTypeOpenDeleteStatus.NO_OPEN;

        SettlementMethodEntity et = new SettlementMethodEntity();
        et.setDeliveryMethodSeq(1);
        et.setSettlementMethodSeq(1);
        et.setSettlementMethodName("abc");
        et.setSettlementMethodType(settlementMethodType);
        et.setOpenStatusMB(openStatusMB);
        et.setOpenStatusPC(openStatusPC);

        doReturn(et).when(settlementMethodDao).getEntity(any(Integer.class));

        settlementMethodCheckLogic.execute(et);

        verify(settlementMethodDao, times(1)).getEntity(any(Integer.class));

    }
}
