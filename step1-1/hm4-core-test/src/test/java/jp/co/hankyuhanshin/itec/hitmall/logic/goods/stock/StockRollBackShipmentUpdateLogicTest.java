package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
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

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockRollBackShipmentUpdateLogicTest {

    @Autowired
    StockRollBackShipmentUpdateLogic stockRollBackShipmentUpdateLogic;

    @Test
    @Order(1)
    public void execute() {
        int result = stockRollBackShipmentUpdateLogic.execute(999, 999, 999);
        Assertions.assertEquals(0, result);
    }
}
