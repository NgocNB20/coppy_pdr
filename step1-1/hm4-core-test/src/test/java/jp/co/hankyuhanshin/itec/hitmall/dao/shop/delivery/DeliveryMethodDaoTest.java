package jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShortfallDisplayFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
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
public class DeliveryMethodDaoTest {

    @Autowired
    DeliveryMethodDao deliveryMethodDao;

    @Test
    @Order(1)
    public void insert() {
        DeliveryMethodEntity entity = makeDeliveryMethodEntity();
        int result = deliveryMethodDao.insert(entity);
        Assertions.assertNotNull(deliveryMethodDao.getEntity(99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = deliveryMethodDao.delete(deliveryMethodDao.getEntity(99));
        Assertions.assertNull(deliveryMethodDao.getEntity(99));
    }

    private DeliveryMethodEntity makeDeliveryMethodEntity() {
        DeliveryMethodEntity entity = new DeliveryMethodEntity();
        entity.setDeliveryMethodSeq(99);
        entity.setShopSeq(99);
        entity.setDeliveryMethodName("1");
        entity.setDeliveryMethodDisplayNamePC("1");
        entity.setDeliveryMethodDisplayNameMB("1");
        entity.setOpenStatusPC(HTypeOpenDeleteStatus.NO_OPEN);
        entity.setOpenStatusMB(HTypeOpenDeleteStatus.NO_OPEN);
        entity.setDeliveryNotePC("1");
        entity.setDeliveryNoteMB("1");
        entity.setDeliveryMethodType(HTypeDeliveryMethodType.AMOUNT);
        entity.setEqualsCarriage(new BigDecimal("0"));
        entity.setLargeAmountDiscountPrice(new BigDecimal("0"));
        entity.setLargeAmountDiscountCarriage(new BigDecimal("0"));
        entity.setShortfallDisplayFlag(HTypeShortfallDisplayFlag.OFF);
        entity.setDeliveryLeadTime(99);
        entity.setDeliveryChaseURL("1");
        entity.setDeliveryChaseURLDisplayPeriod(new BigDecimal("0"));
        entity.setPossibleSelectDays(99);
        entity.setReceiverTimeZone1("1");
        entity.setReceiverTimeZone2("1");
        entity.setReceiverTimeZone3("1");
        entity.setReceiverTimeZone4("1");
        entity.setReceiverTimeZone5("1");
        entity.setReceiverTimeZone6("1");
        entity.setReceiverTimeZone7("1");
        entity.setReceiverTimeZone8("1");
        entity.setReceiverTimeZone9("1");
        entity.setReceiverTimeZone10("1");
        entity.setOrderDisplay(99);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
