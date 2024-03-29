package jp.co.hankyuhanshin.itec.hitmall.dao.order.bill;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeBillStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeEmergencyFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGmoReleaseFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;
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
public class OrderBillDaoTest {

    @Autowired
    OrderBillDao orderBillDao;

    @Test
    @Order(1)
    public void insert() {
        OrderBillEntity entity = makeOrderBillEntity();
        int result = orderBillDao.insert(entity);
        Assertions.assertNotNull(orderBillDao.getEntityWithCardbrand(99, 99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = orderBillDao.delete(orderBillDao.getEntityWithCardbrand(99, 99));
        Assertions.assertNull(orderBillDao.getEntityWithCardbrand(99, 99));
    }

    private OrderBillEntity makeOrderBillEntity() {
        OrderBillEntity entity = new OrderBillEntity();
        entity.setPaymentTimeLimitDate(new Timestamp(new java.util.Date().getTime()));
        entity.setCancelableDate(new Timestamp(new java.util.Date().getTime()));
        entity.setOrderSeq(99);
        entity.setOrderBillVersionNo(99);
        entity.setBillStatus(HTypeBillStatus.BILL_CLAIM);
        entity.setBillTime(new Timestamp(new java.util.Date().getTime()));
        entity.setBillPrice(new BigDecimal("1"));
        entity.setSettlementMethodSeq(0);
        entity.setCreditCompanyCode("1");
        entity.setCreditCompany("1");
        entity.setSettlementCommission(new BigDecimal("1"));
        entity.setEmergencyFlag(HTypeEmergencyFlag.ON);
        entity.setGmoReleaseFlag(HTypeGmoReleaseFlag.RELEASE);
        entity.setShopSeq(0);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        entity.setCardBrandDisplayPC("1");
        entity.setCardBrandDisplayMB("1");
        return entity;
    }
}
