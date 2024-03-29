package jp.co.hankyuhanshin.itec.hitmall.service.order;

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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShipmentStatus;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockOrderReserveUpdateLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderGoodsListDeleteLogic;

/**
 * Logic/Service移行：注文確保在庫をロールバックするサービス
 * 作成日：2021/03/24
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderGoodsRollbackServiceTest {

    @Autowired
    OrderGoodsRollbackService service;

    @MockBean
    private StockOrderReserveUpdateLogic stockOrderReserveUpdateLogic;

    @MockBean
    private OrderGoodsListDeleteLogic orderGoodsListDeleteLogic;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        Integer orderSeq = 1;
        Integer newOrderGoodsVersionNo = 1;
        Integer orderConsecutiveNo = 1;
        HTypeShipmentStatus shipmentStatus = HTypeShipmentStatus.SHIPPED;

        // モック設定
        doReturn(1).when(stockOrderReserveUpdateLogic)
                   .execute(any(Integer.class), any(Integer.class), any(Integer.class), any(Integer.class));
        doReturn(1).when(orderGoodsListDeleteLogic).execute(any(Integer.class), any(Integer.class), any(Integer.class));

        // 試験実行
        service.execute(orderSeq, newOrderGoodsVersionNo, orderConsecutiveNo, shipmentStatus);

        // 戻り値及び呼び出し検証
        verify(stockOrderReserveUpdateLogic, times(1)).execute(
                        any(Integer.class), any(Integer.class), any(Integer.class), any(Integer.class));
        verify(orderGoodsListDeleteLogic, times(1)).execute(any(Integer.class), any(Integer.class), any(Integer.class));
    }
}
