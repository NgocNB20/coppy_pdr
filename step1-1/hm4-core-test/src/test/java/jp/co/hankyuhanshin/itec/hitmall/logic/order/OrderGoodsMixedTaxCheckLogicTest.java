package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
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
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.ReceiveOrderDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.utility.CheckMessageUtility;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderGoodsMixedTaxCheckLogicTest {

    @Autowired
    OrderGoodsMixedTaxCheckLogic logic;

    @MockBean
    private CheckMessageUtility checkMessageUtility;

    @Test
    @Order(1)
    public void checkOrderGoodsMixedTax() {

        // 想定値設定
        String messageId = "ID";
        OrderGoodsEntity orderGoodsEntity = new OrderGoodsEntity();
        orderGoodsEntity.setGoodsCount(BigDecimal.TEN);
        orderGoodsEntity.setGoodsCode("CODE");
        orderGoodsEntity.setTaxRate(BigDecimal.TEN);
        OrderGoodsEntity orderGoodsEntity2 = new OrderGoodsEntity();
        orderGoodsEntity2.setGoodsCount(BigDecimal.TEN);
        orderGoodsEntity2.setGoodsCode("CODE");
        orderGoodsEntity2.setTaxRate(BigDecimal.ONE);
        List<OrderGoodsEntity> listOrderGoodsEntity = new ArrayList<>();
        listOrderGoodsEntity.add(orderGoodsEntity);
        listOrderGoodsEntity.add(orderGoodsEntity2);
        OrderDeliveryDto orderDeliveryDto = new OrderDeliveryDto();
        orderDeliveryDto.setOrderGoodsEntityList(listOrderGoodsEntity);
        ReceiveOrderDto receiveOrderDto = new ReceiveOrderDto();
        receiveOrderDto.setOrderDeliveryDto(orderDeliveryDto);

        CheckMessageDto dto = new CheckMessageDto();
        List<CheckMessageDto> result = new ArrayList<>();
        result.add(dto);

        // モック設定
        doReturn(dto).when(checkMessageUtility).createCheckMessageDto(any(String.class), any(Object.class));

        // 試験実行
        List<CheckMessageDto> actual = logic.checkOrderGoodsMixedTax(receiveOrderDto, messageId);

        // 戻り値及び呼び出し検証
        verify(checkMessageUtility, times(1)).createCheckMessageDto(any(String.class), any(Object.class));
        assertThat(actual).isEqualTo(result);
    }
}
