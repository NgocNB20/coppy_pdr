package jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.coupon.CouponDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CouponCheckLogicTest {

    @Autowired
    CouponCheckLogic couponCheckLogic;

    @MockBean
    CouponDao couponDao;

    @MockBean
    GoodsDao goodsDao;

    @MockBean
    CouponTimeCheckLogic couponTimeCheckLogic;

    @MockBean
    CouponCodeCheckLogic couponCodeCheckLogic;

    @Test
    @Order(1)
    public void execute() {

        doReturn(0).when(couponDao).checkCouponId(any(String.class), any(Integer.class));
        doReturn(new ArrayList<>()).when(goodsDao).getEntityListByGoodsCodeList(any(List.class));
        doReturn(5).when(couponTimeCheckLogic).execute(any(Timestamp.class));
        doReturn(true).when(couponCodeCheckLogic).execute(any(CouponEntity.class));

        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setCouponId("1");
        couponEntity.setCouponStartTime(Timestamp.valueOf("2000-02-03 11:11:11.0"));
        couponCheckLogic.checkForRegist(couponEntity);

        verify(couponDao, times(1)).checkCouponId(any(String.class), any(Integer.class));
    }
}
