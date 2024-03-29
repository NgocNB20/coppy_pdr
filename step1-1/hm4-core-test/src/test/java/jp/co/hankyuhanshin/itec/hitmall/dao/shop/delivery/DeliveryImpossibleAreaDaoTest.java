package jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryImpossibleAreaEntity;
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
public class DeliveryImpossibleAreaDaoTest {

    @Autowired
    DeliveryImpossibleAreaDao deliveryImpossibleAreaDao;

    @Test
    @Order(1)
    public void insert() {
        DeliveryImpossibleAreaEntity entity = makeDeliveryImpossibleAreaEntity();
        int result = deliveryImpossibleAreaDao.insert(entity);
        Assertions.assertNotNull(deliveryImpossibleAreaDao.getEntity(99, "5550001"));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = deliveryImpossibleAreaDao.delete(deliveryImpossibleAreaDao.getEntity(99, "5550001"));
        Assertions.assertNull(deliveryImpossibleAreaDao.getEntity(99, "5550001"));
    }

    private DeliveryImpossibleAreaEntity makeDeliveryImpossibleAreaEntity() {
        DeliveryImpossibleAreaEntity entity = new DeliveryImpossibleAreaEntity();
        entity.setDeliveryMethodSeq(99);
        entity.setZipCode("5550001");
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
