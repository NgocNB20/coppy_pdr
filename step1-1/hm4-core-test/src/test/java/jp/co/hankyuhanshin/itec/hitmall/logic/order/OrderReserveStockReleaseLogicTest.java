package jp.co.hankyuhanshin.itec.hitmall.logic.order;

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
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock.StockDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.goods.OrderGoodsDao;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderReserveStockReleaseLogicTest {

    @Autowired
    OrderReserveStockReleaseLogic logic;

    @MockBean
    private StockDao stockDao;

    @MockBean
    private OrderGoodsDao orderGoodsDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        Integer orderSeq = 1;
        Integer orderGoodsVersionNo = 1;

        // モック設定
        doReturn(1).when(stockDao).updateStockRollback(any(Integer.class), any(Integer.class));
        doReturn(1).when(orderGoodsDao).deleteOrderGoodsList(any(Integer.class), any(Integer.class));

        // 試験実行
        logic.execute(orderSeq, orderGoodsVersionNo);

        // 戻り値及び呼び出し検証
        verify(stockDao, times(1)).updateStockRollback(any(Integer.class), any(Integer.class));
        verify(orderGoodsDao, times(1)).deleteOrderGoodsList(any(Integer.class), any(Integer.class));
    }
}
