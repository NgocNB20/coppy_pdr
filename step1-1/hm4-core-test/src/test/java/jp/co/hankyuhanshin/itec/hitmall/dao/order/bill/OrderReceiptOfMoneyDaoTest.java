package jp.co.hankyuhanshin.itec.hitmall.dao.order.bill;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderReceiptOfMoneyEntity;
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
public class OrderReceiptOfMoneyDaoTest {

    @Autowired
    OrderReceiptOfMoneyDao orderReceiptOfMoneyDao;

    @Test
    @Order(1)
    public void insert() {
        OrderReceiptOfMoneyEntity entity = makeOrderReceiptOfMoneyEntity();
        int result = orderReceiptOfMoneyDao.insert(entity);
        Assertions.assertNotNull(orderReceiptOfMoneyDao.getEntity(99, 99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = orderReceiptOfMoneyDao.delete(orderReceiptOfMoneyDao.getEntity(99, 99));
        Assertions.assertNull(orderReceiptOfMoneyDao.getEntity(99, 99));
    }

    private OrderReceiptOfMoneyEntity makeOrderReceiptOfMoneyEntity() {
        OrderReceiptOfMoneyEntity entity = new OrderReceiptOfMoneyEntity();
        entity.setOrderSeq(99);
        entity.setOrderReceiptOfMoneyVersionNo(99);
        entity.setReceiptTime(new Timestamp(new java.util.Date().getTime()));
        entity.setReceiptYmd("1");
        entity.setReceiptYm("1");
        entity.setReceiptPrice(new BigDecimal("1"));
        entity.setReceiptPriceTotal(new BigDecimal("1"));
        entity.setReceiptMethodSeq(99);
        entity.setOrderTime(new Timestamp(new java.util.Date().getTime()));
        entity.setSalesTime(new Timestamp(new java.util.Date().getTime()));
        entity.setOrderSiteType(HTypeSiteType.FRONT_SP);
        entity.setSettlementMethodSeq(99);
        entity.setShopSeq(99);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
