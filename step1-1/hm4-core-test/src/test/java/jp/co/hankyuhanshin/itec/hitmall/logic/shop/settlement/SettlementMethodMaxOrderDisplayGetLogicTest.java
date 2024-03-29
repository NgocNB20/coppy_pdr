package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.settlement.SettlementMethodDao;

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
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SettlementMethodMaxOrderDisplayGetLogicTest {

    @Autowired
    SettlementMethodMaxOrderDisplayGetLogic settlementMethodMaxOrderDisplayGetLogic;

    @MockBean
    SettlementMethodDao settlementMethodDao;

    @Test
    @Order(1)
    public void execute() {

        doReturn(1).when(settlementMethodDao).getMaxOrderDisplay(any(Integer.class));

        int temp = settlementMethodMaxOrderDisplayGetLogic.execute(1);

        verify(settlementMethodDao, times(1)).getMaxOrderDisplay(any(Integer.class));
        Assertions.assertEquals(1, temp);

    }
}
