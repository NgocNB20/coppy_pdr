package jp.co.hankyuhanshin.itec.hitmall.dao.shop.settlement;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
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

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SettlementMethodDaoTest {

    @Autowired
    SettlementMethodDao settlementMethodDao;

    @Test
    @Order(1)
    public void getEntity() {
        Assertions.assertNotNull(settlementMethodDao.getEntity(1001));
    }

    @Test
    @Order(2)
    public void getOpenSettlementMethodListBySeqList() {
        List<Integer> settlementMethodSeqList = new ArrayList<>();
        settlementMethodSeqList.add(1001);
        settlementMethodSeqList.add(5000);
        List<SettlementMethodEntity> entityList =
                        settlementMethodDao.getOpenSettlementMethodListBySeqList(1001, settlementMethodSeqList);
        Assertions.assertEquals(2, entityList.size());
    }
}
