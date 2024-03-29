package jp.co.hankyuhanshin.itec.hitmall.dao.order.index;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeCouponLimitTargetType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailRequired;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeProcessType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSend;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeWaitingFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;
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
public class OrderIndexDaoTest {

    @Autowired
    OrderIndexDao orderIndexDao;

    @Test
    @Order(1)
    public void insert() {
        OrderIndexEntity entity = makeOrderIndexEntity();
        int result = orderIndexDao.insert(entity);
        Assertions.assertNotNull(orderIndexDao.getEntity(99, 99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = orderIndexDao.delete(orderIndexDao.getEntity(99, 99));
        Assertions.assertNull(orderIndexDao.getEntity(99, 99));
    }

    private OrderIndexEntity makeOrderIndexEntity() {
        OrderIndexEntity entity = new OrderIndexEntity();
        entity.setOrderSeq(99);
        entity.setOrderVersionNo(99);
        entity.setProcessTime(new Timestamp(new java.util.Date().getTime()));
        entity.setOrderPersonVersionNo(99);
        entity.setOrderDeliveryVersionNo(99);
        entity.setOrderSettlementVersionNo(99);
        entity.setOrderGoodsVersionNo(99);
        entity.setOrderAdditionalChargeVersionNo(99);
        entity.setOrderBillVersionNo(99);
        entity.setOrderReceiptOfMoneyVersionNo(99);
        entity.setOrderMemoVersionNo(99);
        entity.setProcessPersonName("1");
        entity.setProcessType(HTypeProcessType.CANCELLATION);
        entity.setWaitingFlag(HTypeWaitingFlag.OFF);
        entity.setShopSeq(99);
        entity.setSettlementMailRequired(HTypeMailRequired.REQUIRED);
        entity.setReminderSentFlag(HTypeSend.SENT);
        entity.setExpiredSentFlag(HTypeSend.SENT);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        entity.setCouponSeq(99);
        entity.setCouponVersionNo(99);
        entity.setCouponLimitTargetType(HTypeCouponLimitTargetType.OFF);
        entity.setPointSeq(99);
        entity.setPointVersionNo(99);
        entity.setOrderWaitingMemo("1");
        return entity;
    }
}
