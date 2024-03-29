package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import jp.co.hankyuhanshin.itec.hitmall.dao.order.bill.OrderBillDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.bill.OrderBillEntity;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderBillRegistLogicTest {

    @Autowired
    OrderBillRegistLogic orderBillRegistLogic;

    @MockBean
    private OrderBillDao dao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        OrderBillEntity orderBillEntity = new OrderBillEntity();
        int result = 1;

        // モック設定
        doReturn(result).when(dao).insert(any(OrderBillEntity.class));

        // 試験実行
        int actual = orderBillRegistLogic.execute(orderBillEntity);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).insert(any(OrderBillEntity.class));
        assertThat(actual).isEqualTo(result);
    }
}
