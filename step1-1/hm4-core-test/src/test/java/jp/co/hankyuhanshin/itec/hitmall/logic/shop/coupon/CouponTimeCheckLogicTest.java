package jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
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

import java.sql.Timestamp;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CouponTimeCheckLogicTest {

    @Autowired
    CouponTimeCheckLogic couponTimeCheckLogic;

    @Test
    @Order(1)
    public void execute() {
        int result = couponTimeCheckLogic.execute(Timestamp.valueOf("3000-02-03 11:11:11.0"));
        Assertions.assertEquals(1, result);
    }
}
