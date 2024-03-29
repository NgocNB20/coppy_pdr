package jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliverySpecialChargeAreaEntity;
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
public class DeliverySpecialChargeAreaDaoTest {

    @Autowired
    DeliverySpecialChargeAreaDao deliverySpecialChargeAreaDao;

    @Test
    @Order(1)
    public void insert() {
        DeliverySpecialChargeAreaEntity entity = makeDeliverySpecialChargeAreaEntity();
        int result = deliverySpecialChargeAreaDao.insert(entity);
        Assertions.assertNotNull(deliverySpecialChargeAreaDao.getEntity(99, "5550001"));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = deliverySpecialChargeAreaDao.delete(deliverySpecialChargeAreaDao.getEntity(99, "5550001"));
        Assertions.assertNull(deliverySpecialChargeAreaDao.getEntity(99, "5550001"));
    }

    private DeliverySpecialChargeAreaEntity makeDeliverySpecialChargeAreaEntity() {
        DeliverySpecialChargeAreaEntity entity = new DeliverySpecialChargeAreaEntity();
        entity.setDeliveryMethodSeq(99);
        entity.setZipCode("5550001");
        entity.setCarriage(new BigDecimal("1"));
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
