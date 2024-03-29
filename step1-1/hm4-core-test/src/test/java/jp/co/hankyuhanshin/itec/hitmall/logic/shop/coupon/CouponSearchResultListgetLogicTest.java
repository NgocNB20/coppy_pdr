package jp.co.hankyuhanshin.itec.hitmall.logic.shop.coupon;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.coupon.CouponDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.coupon.CouponSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.coupon.CouponEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CouponSearchResultListgetLogicTest {

    @Autowired
    CouponSearchResultListgetLogic couponSearchResultListgetLogic;

    @MockBean
    CouponDao couponDao;

    @Test
    @Order(1)
    public void execute() {
        List<CouponEntity> entityList = new ArrayList<>();
        CouponEntity entity = new CouponEntity();
        entity.setCouponId("1");
        entityList.add(entity);
        doReturn(entityList).when(couponDao)
                            .getCouponSearchResultList(any(CouponSearchForDaoConditionDto.class),
                                                       any(SelectOptions.class)
                                                      );

        CouponSearchForDaoConditionDto condition = new CouponSearchForDaoConditionDto();

        List<CouponEntity> couponEntityList = couponSearchResultListgetLogic.execute(condition);

        Assertions.assertEquals(entityList, couponEntityList);
    }
}
