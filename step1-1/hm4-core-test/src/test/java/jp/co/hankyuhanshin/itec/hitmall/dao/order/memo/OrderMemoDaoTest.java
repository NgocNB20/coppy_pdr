package jp.co.hankyuhanshin.itec.hitmall.dao.order.memo;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.memo.OrderMemoEntity;
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
public class OrderMemoDaoTest {

    @Autowired
    OrderMemoDao orderMemoDao;

    @Test
    @Order(1)
    public void insert() {
        OrderMemoEntity entity = makeOrderMemoEntity();
        int result = orderMemoDao.insert(entity);
        Assertions.assertNotNull(orderMemoDao.getEntity(99, 99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = orderMemoDao.delete(orderMemoDao.getEntity(99, 99));
        Assertions.assertNull(orderMemoDao.getEntity(99, 99));
    }

    private OrderMemoEntity makeOrderMemoEntity() {
        OrderMemoEntity entity = new OrderMemoEntity();
        entity.setOrderSeq(99);
        entity.setOrderMemoVersionNo(99);
        entity.setMemo("1");
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
