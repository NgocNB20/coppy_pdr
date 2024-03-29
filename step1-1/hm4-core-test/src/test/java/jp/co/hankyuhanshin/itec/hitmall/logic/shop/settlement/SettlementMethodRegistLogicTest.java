package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.settlement.SettlementMethodDao;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SettlementMethodRegistLogicTest {

    @Autowired
    SettlementMethodRegistLogic settlementMethodRegistLogic;

    @MockBean
    SettlementMethodDao settlementMethodDao;

    @Test
    @Order(1)
    public void execute() {
        SettlementMethodEntity entity = new SettlementMethodEntity();
        entity.setSettlementMethodSeq(1);

        doReturn(1).when(settlementMethodDao).insert((SettlementMethodEntity) any(Object.class));

        int temp = settlementMethodRegistLogic.execute(entity);

        verify(settlementMethodDao, times(1)).insert((SettlementMethodEntity) any(Object.class));
        Assertions.assertEquals(1, temp);
    }
}
