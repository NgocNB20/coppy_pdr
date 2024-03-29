package jp.co.hankyuhanshin.itec.hitmall.dao.shop.settlement;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
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

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SettlementMethodPriceCommissionDaoTest {

    @Autowired
    SettlementMethodPriceCommissionDao settlementMethodPriceCommissionDao;

    @Test
    @Order(1)
    public void insert() {
        SettlementMethodPriceCommissionEntity entity = makeSettlementMethodPriceCommissionEntity();
        int result = settlementMethodPriceCommissionDao.insert(entity);
        Assertions.assertNotNull(settlementMethodPriceCommissionDao.getEntity(99, new BigDecimal("1")));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = settlementMethodPriceCommissionDao.delete(
                        settlementMethodPriceCommissionDao.getEntity(99, new BigDecimal("1")));
        Assertions.assertNull(settlementMethodPriceCommissionDao.getEntity(99, new BigDecimal("1")));
    }

    private SettlementMethodPriceCommissionEntity makeSettlementMethodPriceCommissionEntity() {
        SettlementMethodPriceCommissionEntity entity = new SettlementMethodPriceCommissionEntity();
        entity.setSettlementMethodSeq(99);
        entity.setMaxPrice(new BigDecimal("1"));
        entity.setCommission(new BigDecimal("0"));
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
