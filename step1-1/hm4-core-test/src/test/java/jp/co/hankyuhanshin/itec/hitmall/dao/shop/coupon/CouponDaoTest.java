package jp.co.hankyuhanshin.itec.hitmall.dao.shop.coupon;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
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
public class CouponDaoTest {

    @Autowired
    CouponDao couponDao;

    @Test
    @Order(1)
    public void insert() {
        CouponEntity entity = makeCouponEntity();
        int result = couponDao.insert(entity);
        Assertions.assertNotNull(couponDao.getEntity(99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = couponDao.delete(couponDao.getEntity(99));
        Assertions.assertNull(couponDao.getEntity(99));
    }

    private CouponEntity makeCouponEntity() {
        CouponEntity entity = new CouponEntity();
        entity.setCouponSeq(99);
        entity.setCouponVersionNo(99);
        entity.setShopSeq(99);
        entity.setCouponId("1");
        entity.setCouponName("1");
        entity.setCouponDisplayNamePC("1");
        entity.setCouponDisplayNameMB("1");
        entity.setCouponCode("1");
        entity.setCouponStartTime(new Timestamp(new java.util.Date().getTime()));
        entity.setCouponEndTime(new Timestamp(new java.util.Date().getTime()));
        entity.setDiscountPrice(new BigDecimal("0"));
        entity.setDiscountLowerOrderPrice(new BigDecimal("0"));
        entity.setUseCountLimit(99);
        entity.setTargetGoods("1");
        entity.setTargetMembers("1");
        entity.setMemo("1");
        entity.setAdministratorId("1");
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
