package jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.coupon.CouponDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.coupon.CouponIndexDao;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CouponDeleteLogicTest {

    @Autowired
    CouponDeleteLogic couponDeleteLogic;

    @MockBean
    CouponDao couponDao;

    @MockBean
    CouponIndexDao couponIndexDao;

    @Test
    @Order(1)
    public void execute() {
        doReturn(1).when(couponDao).deleteCoupon(any(Integer.class), any(Integer.class));
        doReturn(1).when(couponIndexDao).deleteCouponIndex(any(Integer.class), any(Integer.class));

        CouponEntity coupon = new CouponEntity();
        coupon.setCouponSeq(1);
        coupon.setShopSeq(1);
        int result = couponDeleteLogic.execute(coupon);

        verify(couponIndexDao, times(1)).deleteCouponIndex(any(Integer.class), any(Integer.class));
        Assertions.assertEquals(1, result);
    }
}
