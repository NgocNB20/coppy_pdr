package jp.co.hankyuhanshin.itec.hitmall.dao.order.delivery;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryCycle;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInvoiceAttachmentFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReceiverDateDesignationFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeReservationDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
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
public class OrderDeliveryDaoTest {

    @Autowired
    OrderDeliveryDao orderDeliveryDao;

    @Test
    @Order(1)
    public void insert() {
        OrderDeliveryEntity entity = makeOrderDeliveryEntity();
        int result = orderDeliveryDao.insert(entity);
        Assertions.assertNotNull(orderDeliveryDao.getEntity(99, 99, 99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = orderDeliveryDao.delete(orderDeliveryDao.getEntity(99, 99, 99));
        Assertions.assertNull(orderDeliveryDao.getEntity(99, 99, 99));
    }

    private OrderDeliveryEntity makeOrderDeliveryEntity() {
        OrderDeliveryEntity entity = new OrderDeliveryEntity();
        entity.setOrderSeq(99);
        entity.setOrderDeliveryVersionNo(99);
        entity.setOrderConsecutiveNo(99);
        entity.setShipmentStatus(HTypeShipmentStatus.SHIPPED);
        entity.setReservationDeliveryFlag(HTypeReservationDeliveryFlag.ON);
        entity.setPlanDate(new Timestamp(new java.util.Date().getTime()));
        entity.setShipmentDate(new Timestamp(new java.util.Date().getTime()));
        entity.setDeliveryCode("1");
        entity.setInvoiceAttachmentFlag(HTypeInvoiceAttachmentFlag.ON);
        entity.setDeliveryMethodSeq(99);
        entity.setReceiverLastName("1");
        entity.setReceiverFirstName("1");
        entity.setReceiverLastKana("1");
        entity.setReceiverFirstKana("1");
        entity.setReceiverTel("1");
        entity.setReceiverZipCode("1");
        entity.setReceiverPrefecture("1");
        entity.setReceiverAddress1("1");
        entity.setReceiverAddress2("1");
        entity.setReceiverAddress3("1");
        entity.setReceiverDateDesignationFlag(HTypeReceiverDateDesignationFlag.ON);
        entity.setReceiverDate(new Timestamp(new java.util.Date().getTime()));
        entity.setReceiverTimeZone("1");
        entity.setDeliveryNote("1");
        entity.setOthersNote("1");
        entity.setMessage("1");
        entity.setCarriage(new BigDecimal("1"));
        entity.setShopSeq(99);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        entity.setShortestDeliveryDateToRegist(new Timestamp(new java.util.Date().getTime()));
        entity.setDeliveryCycle(HTypeDeliveryCycle.MONTH_1);
        entity.setDeliveryDay(99);
        return entity;
    }
}
