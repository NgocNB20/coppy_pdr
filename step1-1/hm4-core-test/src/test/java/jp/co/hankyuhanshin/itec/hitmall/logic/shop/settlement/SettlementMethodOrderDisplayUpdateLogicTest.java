package jp.co.hankyuhanshin.itec.hitmall.logic.shop.settlement;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SettlementMethodOrderDisplayUpdateLogicTest {

    @Autowired
    SettlementMethodOrderDisplayUpdateLogic settlementMethodOrderDisplayUpdateLogic;

    @MockBean
    SettlementMethodDao settlementMethodDao;

    @Test
    @Order(1)
    public void execute() {
        List<SettlementMethodEntity> settlementMethodList = new ArrayList<SettlementMethodEntity>();
        SettlementMethodEntity entity = new SettlementMethodEntity();
        entity.setSettlementMethodName("abc");
        entity.setShopSeq(1);
        settlementMethodList.add(entity);

        doNothing().when(settlementMethodDao).updateLockTableShareModeNowait();

        int temp = settlementMethodOrderDisplayUpdateLogic.execute(settlementMethodList, 1);

        verify(settlementMethodDao, times(1)).updateLockTableShareModeNowait();
        //Assertions.assertEquals(1, temp);

    }

}
