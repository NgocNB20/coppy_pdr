package jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodTypeCarriageEntity;
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
public class DeliveryMethodTypeCarriageDaoTest {

    @Autowired
    DeliveryMethodTypeCarriageDao deliveryMethodTypeCarriageDao;

    @Test
    @Order(1)
    public void insert() {
        DeliveryMethodTypeCarriageEntity entity = makeDeliveryMethodTypeCarriageEntity();
        int result = deliveryMethodTypeCarriageDao.insert(entity);
        Assertions.assertNotNull(deliveryMethodTypeCarriageDao.getEntity(99, "1", new BigDecimal("99")));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = deliveryMethodTypeCarriageDao.delete(
                        deliveryMethodTypeCarriageDao.getEntity(99, "1", new BigDecimal("99")));
        Assertions.assertNull(deliveryMethodTypeCarriageDao.getEntity(99, "1", new BigDecimal("99")));
    }

    private DeliveryMethodTypeCarriageEntity makeDeliveryMethodTypeCarriageEntity() {
        DeliveryMethodTypeCarriageEntity entity = new DeliveryMethodTypeCarriageEntity();
        entity.setDeliveryMethodSeq(99);
        entity.setPrefectureType(HTypePrefectureType.HOKKAIDO);
        entity.setMaxPrice(new BigDecimal("99"));
        entity.setCarriage(new BigDecimal("99"));
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
