package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeRequisitionType;
import jp.co.hankyuhanshin.itec.hitmall.dto.order.delivery.OrderDeliveryDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetReserveResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.delivery.OrderDeliveryEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetReserveLogic;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderGetReserveLogicTest {

    @Autowired
    OrderGetReserveLogic orderGetReserveLogic;

    @MockBean
    /** WEB-API連携取得 取りおき情報取得 */ private WebApiGetReserveLogic webApiGetReserveLogic;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        Map<String, WebApiGetReserveResponseDetailDto> result = new HashMap<>();
        WebApiGetReserveResponseDetailDto webApiGetReserveResponseDetailDto = new WebApiGetReserveResponseDetailDto();
        webApiGetReserveResponseDetailDto.setGoodsCode("77-55-7039");

        result.put("77-55-7039", webApiGetReserveResponseDetailDto);

        List<OrderGoodsEntity> listOrderGoodsEntity = new ArrayList<>();
        OrderGoodsEntity orderGoodsEntity = new OrderGoodsEntity();
        orderGoodsEntity.setOrderGoodsVersionNo(1);
        orderGoodsEntity.setGoodsCode("77-55-7039");
        listOrderGoodsEntity.add(orderGoodsEntity);
        OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();
        OrderDeliveryDto orderDeliveryDto = new OrderDeliveryDto();
        orderDeliveryDto.setOrderGoodsEntityList(listOrderGoodsEntity);
        orderDeliveryDto.setOrderDeliveryEntity(orderDeliveryEntity);
        orderDeliveryDto.setRequisitionType(HTypeRequisitionType.INCLUDE);

        WebApiGetReserveResponseDto resDto = new WebApiGetReserveResponseDto();
        List<WebApiGetReserveResponseDetailDto> info = new ArrayList<>();
        WebApiGetReserveResponseDetailDto webApiGetConsumptionTaxRateResponseDetailDto =
                        new WebApiGetReserveResponseDetailDto();
        webApiGetConsumptionTaxRateResponseDetailDto.setGoodsCode("77-55-7039");
        info.add(webApiGetConsumptionTaxRateResponseDetailDto);
        resDto.setGoodCode("77-55-7039");
        resDto.setInfo(info);

        // モック設定
        doReturn(resDto).when(webApiGetReserveLogic).execute(any(AbstractWebApiRequestDto.class));

        // 試験実行
        Map<String, WebApiGetReserveResponseDetailDto> actual = orderGetReserveLogic.execute(orderDeliveryDto, 9);

        // 戻り値及び呼び出し検証
        verify(webApiGetReserveLogic, times(1)).execute(any(AbstractWebApiRequestDto.class));
        assertThat(actual).isEqualTo(result);

    }
}
