package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.settlement.SettlementMethodPriceCommissionDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.settlement.SettlementMethodPriceCommissionEntity;

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
public class SettlementMethodPriceCommissionRegistLogicTest {

    @Autowired
    SettlementMethodPriceCommissionRegistLogic settlementMethodPriceCommissionRegistLogic;

    @MockBean
    SettlementMethodPriceCommissionDao settlementMethodPriceCommissionDao;

    @Test
    @Order(1)
    public void execute() {
        SettlementMethodPriceCommissionEntity entity = new SettlementMethodPriceCommissionEntity();
        entity.setSettlementMethodSeq(1);

        doReturn(1).when(settlementMethodPriceCommissionDao)
                   .insert((SettlementMethodPriceCommissionEntity) any(Object.class));

        int temp = settlementMethodPriceCommissionRegistLogic.execute(entity);

        verify(settlementMethodPriceCommissionDao, times(1)).insert(
                        (SettlementMethodPriceCommissionEntity) any(Object.class));
        Assertions.assertEquals(1, temp);
    }
}
