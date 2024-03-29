package jp.co.hankyuhanshin.itec.hitmall.dao.order.additional;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;
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
public class OrderAdditionalChargeDaoTest {

    @Autowired
    OrderAdditionalChargeDao orderAdditionalChargeDao;

    @Test
    @Order(1)
    public void insert() {
        OrderAdditionalChargeEntity entity = makeOrderAdditionalChargeEntity();
        int result = orderAdditionalChargeDao.insert(entity);
        Assertions.assertNotNull(orderAdditionalChargeDao.getEntity(99, 99, 99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = orderAdditionalChargeDao.delete(orderAdditionalChargeDao.getEntity(99, 99, 99));
        Assertions.assertNull(orderAdditionalChargeDao.getEntity(99, 99, 99));
    }

    private OrderAdditionalChargeEntity makeOrderAdditionalChargeEntity() {
        OrderAdditionalChargeEntity entity = new OrderAdditionalChargeEntity();
        entity.setOrderSeq(99);
        entity.setOrderAdditionalChargeVersionNo(99);
        entity.setOrderDisplay(99);
        entity.setAdditionalDetailsName("1");
        entity.setAdditionalDetailsPrice(new BigDecimal("0"));
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
