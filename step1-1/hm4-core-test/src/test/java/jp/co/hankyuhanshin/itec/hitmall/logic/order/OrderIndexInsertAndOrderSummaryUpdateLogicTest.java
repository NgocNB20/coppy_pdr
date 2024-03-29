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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeProcessType;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.OrderSummaryDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.index.OrderIndexDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.index.OrderIndexEntity;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderIndexInsertAndOrderSummaryUpdateLogicTest {

    @Autowired
    OrderIndexInsertAndOrderSummaryUpdateLogic logic;

    @MockBean
    private OrderSummaryDao orderSummaryDao;

    @MockBean
    private OrderIndexDao orderIndexDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        Integer orderSeq = 1;
        Integer shopSeq = 1;
        String processPersonName = "NAME";
        HTypeProcessType processType = HTypeProcessType.CHANGE;

        OrderIndexEntity orderIndexEntity = new OrderIndexEntity();
        orderIndexEntity.setOrderVersionNo(1);
        OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();

        boolean result = true;

        // モック設定
        doReturn(orderIndexEntity).when(orderIndexDao).getEntityOfMaxOrderVersionNo(any(Integer.class));
        doReturn(1).when(orderIndexDao).insert(any(OrderIndexEntity.class));
        doReturn(orderSummaryEntity).when(orderSummaryDao).getEntityForUpdatePrimaryKey(any(Integer.class));
        doReturn(1).when(orderSummaryDao).update(any(OrderSummaryEntity.class));

        // 試験実行
        boolean actual = logic.execute(orderSeq, shopSeq, processPersonName, processType);

        // 戻り値及び呼び出し検証
        verify(orderIndexDao, times(1)).getEntityOfMaxOrderVersionNo(any(Integer.class));
        verify(orderIndexDao, times(1)).insert(any(OrderIndexEntity.class));
        verify(orderSummaryDao, times(1)).getEntityForUpdatePrimaryKey(any(Integer.class));
        verify(orderSummaryDao, times(1)).update(any(OrderSummaryEntity.class));
        assertThat(actual).isEqualTo(result);
    }
}
