package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

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

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.additional.OrderAdditionalChargeDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.additional.OrderAdditionalChargeEntity;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderAdditionalChargeListGetLogicTest {

    @Autowired
    OrderAdditionalChargeListGetLogic logic;

    @MockBean
    private OrderAdditionalChargeDao dao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        Integer orderSeq = 1;
        Integer orderAdditionalChargeVersionNo = 2;
        List<OrderAdditionalChargeEntity> result = new ArrayList<>();

        // モック設定
        doReturn(result).when(dao).getOrderAdditionalChargeList(any(Integer.class), any(Integer.class));

        // 試験実行
        List<OrderAdditionalChargeEntity> actual = logic.execute(orderSeq, orderAdditionalChargeVersionNo);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getOrderAdditionalChargeList(any(Integer.class), any(Integer.class));
        assertThat(actual).isEqualTo(result);
    }
}
