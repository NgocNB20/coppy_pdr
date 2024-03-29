package jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.coupon.CouponDao;
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
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CouponGetLogicTest {

    @Autowired
    CouponGetLogic couponGetLogic;

    @MockBean
    CouponDao couponDao;

    @Test
    @Order(1)
    public void execute() {
        CouponEntity entity = new CouponEntity();
        entity.setCouponId("1");
        doReturn(entity).when(couponDao).getCouponByCouponSeq(any(Integer.class), any(Integer.class));

        CouponEntity couponEntity = couponGetLogic.execute(999);

        Assertions.assertEquals(entity, couponEntity);
    }
}
