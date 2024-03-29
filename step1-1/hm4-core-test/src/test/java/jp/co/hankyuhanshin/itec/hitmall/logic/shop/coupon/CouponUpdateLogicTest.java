package jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.coupon.CouponDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.coupon.CouponIndexDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponIndexEntity;
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
public class CouponUpdateLogicTest {

    @Autowired
    CouponUpdateLogic couponUpdateLogic;

    @MockBean
    CouponDao couponDao;

    @MockBean
    CouponIndexDao couponIndexDao;

    @Test
    @Order(1)
    public void execute() {
        doReturn(1).when(couponDao).insert(any(CouponEntity.class));
        doReturn(1).when(couponIndexDao).update(any(CouponIndexEntity.class));

        CouponEntity coupon = new CouponEntity();
        couponUpdateLogic.execute(coupon);

        verify(couponDao, times(1)).insert(any(CouponEntity.class));
        verify(couponIndexDao, times(1)).update(any(CouponIndexEntity.class));
    }
}
