package jp.co.hankyuhanshin.itec.hitmall.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
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
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ShipmentRegistDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.OrderSummaryEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.order.OrderSummaryGetForUpdateLogic;

/**
 * Logic/Service移行： 出荷登録処理
 * 作成日：2021/03/26
 *
 * @author Pham Quang Dieu (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShipmentRegistServiceTest {

    @Autowired
    ShipmentRegistService service;

    @MockBean
    private OrderSummaryGetForUpdateLogic orderSummaryGetForUpdateLogic;

    @Test
    @Order(1)
    public void executeTest() {

        // 想定値設定
        Timestamp latestSaleStartTime = Timestamp.valueOf("2021-03-21 12:12:12");
        ShipmentRegistDto shipmentRegistDto = new ShipmentRegistDto();
        shipmentRegistDto.setOrderConsecutiveNo(1);
        shipmentRegistDto.setShipmentDate(latestSaleStartTime);

        OrderSummaryEntity orderSummaryEntity = null;

        // モック設定
        doReturn(orderSummaryEntity).when(orderSummaryGetForUpdateLogic).execute(any(), any(), any(Integer.class));

        try {

            // 試験実行
            service.execute(shipmentRegistDto);
            fail("例外がスローされなかった。");

        } catch (Exception e) {

            assertThat(e).isInstanceOf(RuntimeException.class);
            assertThat(e.getMessage()).isNull();

            // 戻り値及び呼び出し検証
            verify(orderSummaryGetForUpdateLogic, times(1)).execute(any(), any(), any(Integer.class));
        }
    }
}
