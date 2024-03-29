package jp.co.hankyuhanshin.itec.hitmall.dao.shop.coupon;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponIndexEntity;
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
public class CouponIndexDaoTest {

    @Autowired
    CouponIndexDao couponIndexDao;

    @Test
    @Order(1)
    public void insert() {
        CouponIndexEntity entity = makeCouponIndexEntity();
        int result = couponIndexDao.insert(entity);
        Assertions.assertNotNull(couponIndexDao.getEntity(99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = couponIndexDao.delete(couponIndexDao.getEntity(99));
        Assertions.assertNull(couponIndexDao.getEntity(99));
    }

    private CouponIndexEntity makeCouponIndexEntity() {
        CouponIndexEntity entity = new CouponIndexEntity();
        entity.setCouponSeq(99);
        entity.setCouponVersionNo(99);
        entity.setShopSeq(99);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
