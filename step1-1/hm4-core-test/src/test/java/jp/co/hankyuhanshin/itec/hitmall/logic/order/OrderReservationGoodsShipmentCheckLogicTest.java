package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;

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
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteType;
import jp.co.hankyuhanshin.itec.hitmall.dao.order.goods.OrderGoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ShipmentRegistDto;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderReservationGoodsShipmentCheckLogicTest {

    @Autowired
    OrderReservationGoodsShipmentCheckLogic logic;

    @MockBean
    private OrderGoodsDao dao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        Integer orderSeq = 1;
        Timestamp latestSaleStartTime = Timestamp.valueOf("2021-03-21 12:12:12");
        ShipmentRegistDto shipmentRegistDto = new ShipmentRegistDto();
        shipmentRegistDto.setOrderConsecutiveNo(1);
        shipmentRegistDto.setShipmentDate(latestSaleStartTime);

        boolean result = false;

        // モック設定
        doReturn(latestSaleStartTime).when(dao)
                                     .getOrderReservationGoodsLatestSaleStartTime(any(Integer.class),
                                                                                  any(Integer.class)
                                                                                 );

        // 試験実行
        boolean actual = logic.execute(orderSeq, shipmentRegistDto);

        // 戻り値及び呼び出し検証
        verify(dao, times(1)).getOrderReservationGoodsLatestSaleStartTime(any(Integer.class), any(Integer.class));
        assertThat(actual).isEqualTo(result);
    }
}
