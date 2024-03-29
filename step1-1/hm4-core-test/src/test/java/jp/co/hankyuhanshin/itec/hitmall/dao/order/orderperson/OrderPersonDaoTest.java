package jp.co.hankyuhanshin.itec.hitmall.dao.order.orderperson;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAddressType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderAgeType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOrderSex;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.orderperson.OrderPersonEntity;
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
public class OrderPersonDaoTest {

    @Autowired
    OrderPersonDao orderPersonDao;

    @Test
    @Order(1)
    public void insert() {
        OrderPersonEntity entity = makeOrderPersonEntity();
        int result = orderPersonDao.insert(entity);
        Assertions.assertNotNull(orderPersonDao.getEntity(99, 99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = orderPersonDao.delete(orderPersonDao.getEntity(99, 99));
        Assertions.assertNull(orderPersonDao.getEntity(99, 99));
    }

    private OrderPersonEntity makeOrderPersonEntity() {
        OrderPersonEntity entity = new OrderPersonEntity();
        entity.setOrderSeq(99);
        entity.setOrderPersonVersionNo(99);
        entity.setMemberInfoSeq(99);
        entity.setOrderLastName("1");
        entity.setOrderFirstName("1");
        entity.setOrderLastKana("1");
        entity.setOrderFirstKana("1");
        entity.setOrderZipCode("1");
        entity.setPrefectureType(HTypePrefectureType.YAMAGATA);
        entity.setOrderPrefecture("1");
        entity.setOrderAddress1("1");
        entity.setOrderAddress2("1");
        entity.setOrderAddress3("1");
        entity.setOrderTel("1");
        entity.setOrderContactTel("1");
        entity.setOrderMail("1");
        entity.setOrderBirthday(new Timestamp(new java.util.Date().getTime()));
        entity.setOrderAge(99);
        entity.setOrderAgeType(HTypeOrderAgeType.AGE_0TO9);
        entity.setOrderSex(HTypeOrderSex.MALE);
        entity.setAddressType(HTypeAddressType.MB);
        entity.setShopSeq(99);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
