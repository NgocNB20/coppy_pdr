package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.common.CheckMessageDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiResponseResultDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDeliveryInformationRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDeliveryInformationResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetDeliveryInformationResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.order.goods.OrderGoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetDeliveryInformationLogic;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderDeliveryInformationLogicTest {

    @Autowired
    OrderDeliveryInformationLogic orderDeliveryInformationLogic;

    @MockBean
    WebApiGetDeliveryInformationLogic webApiGetDeliveryInformationLogic;

    @Test
    @Order(1)
    public void execute() {

        // Setup data
        WebApiGetDeliveryInformationResponseDetailDto expect = new WebApiGetDeliveryInformationResponseDetailDto();
        expect.setNodeliveryGoodsCode("nodeliveryGoodsCode");

        WebApiGetDeliveryInformationRequestDto webApiGetDeliveryInformationRequestDto =
                        new WebApiGetDeliveryInformationRequestDto();
        webApiGetDeliveryInformationRequestDto.setGoodsCode("goodsCode");

        // Mock

        WebApiGetDeliveryInformationResponseDto webApiGetDeliveryInformationResponseDto =
                        new WebApiGetDeliveryInformationResponseDto();
        List<WebApiGetDeliveryInformationResponseDetailDto> info = new ArrayList<>();
        WebApiGetDeliveryInformationResponseDetailDto webApiGetDeliveryInformationResponseDetailDto =
                        new WebApiGetDeliveryInformationResponseDetailDto();
        webApiGetDeliveryInformationResponseDetailDto.setNodeliveryGoodsCode("nodeliveryGoodsCode");
        info.add(webApiGetDeliveryInformationResponseDetailDto);

        AbstractWebApiResponseResultDto resultDto = new AbstractWebApiResponseResultDto();
        resultDto.setStatus("0");
        resultDto.setMessage("Success");

        webApiGetDeliveryInformationResponseDto.setInfo(info);
        webApiGetDeliveryInformationResponseDto.setResult(resultDto);

        List<OrderGoodsEntity> orderGoodsEntityList = new ArrayList<>();

        OrderGoodsEntity orderGoodsEntity = new OrderGoodsEntity();
        orderGoodsEntity.setGoodsCode("goodsCode");
        orderGoodsEntityList.add(orderGoodsEntity);

        // モック設定
        doReturn(webApiGetDeliveryInformationResponseDto).when(webApiGetDeliveryInformationLogic)
                                                         .execute(any(WebApiGetDeliveryInformationRequestDto.class));

        // Verify
        WebApiGetDeliveryInformationResponseDetailDto actual =
                        orderDeliveryInformationLogic.execute(orderGoodsEntityList,
                                                              webApiGetDeliveryInformationRequestDto
                                                             );

        assertThat(expect).isEqualTo(actual);
    }

    @Test
    @Order(2)
    public void execute02() {

        // Setup data
        WebApiGetDeliveryInformationResponseDetailDto expect = new WebApiGetDeliveryInformationResponseDetailDto();
        expect.setNodeliveryGoodsCode("goodsCode");
        expect.setDeliveryFlag(HTypeDeliveryFlag.OFF.getValue());

        WebApiGetDeliveryInformationRequestDto webApiGetDeliveryInformationRequestDto =
                        new WebApiGetDeliveryInformationRequestDto();
        webApiGetDeliveryInformationRequestDto.setGoodsCode("goodsCode");

        List<CheckMessageDto> checkMessageDtoList = new ArrayList<>();

        // Mock

        WebApiGetDeliveryInformationResponseDto webApiGetDeliveryInformationResponseDto =
                        new WebApiGetDeliveryInformationResponseDto();
        List<WebApiGetDeliveryInformationResponseDetailDto> info = new ArrayList<>();
        WebApiGetDeliveryInformationResponseDetailDto webApiGetDeliveryInformationResponseDetailDto =
                        new WebApiGetDeliveryInformationResponseDetailDto();
        webApiGetDeliveryInformationResponseDetailDto.setNodeliveryGoodsCode("goodsCode");
        webApiGetDeliveryInformationResponseDetailDto.setDeliveryFlag(HTypeDeliveryFlag.OFF.getValue());

        info.add(webApiGetDeliveryInformationResponseDetailDto);

        AbstractWebApiResponseResultDto resultDto = new AbstractWebApiResponseResultDto();
        resultDto.setStatus("0");
        resultDto.setMessage("Success");

        webApiGetDeliveryInformationResponseDto.setInfo(info);
        webApiGetDeliveryInformationResponseDto.setResult(resultDto);

        List<OrderGoodsEntity> orderGoodsEntityList = new ArrayList<>();

        OrderGoodsEntity orderGoodsEntity = new OrderGoodsEntity();
        orderGoodsEntity.setGoodsCode("goodsCode");
        orderGoodsEntity.setGoodsGroupName("goodsGroupName");
        orderGoodsEntity.setUnitValue1("unit1");
        orderGoodsEntity.setUnitValue2("unit2");
        orderGoodsEntityList.add(orderGoodsEntity);

        // モック設定
        doReturn(webApiGetDeliveryInformationResponseDto).when(webApiGetDeliveryInformationLogic)
                                                         .execute(any(WebApiGetDeliveryInformationRequestDto.class));

        // Verify
        WebApiGetDeliveryInformationResponseDetailDto actual =
                        orderDeliveryInformationLogic.execute(orderGoodsEntityList,
                                                              webApiGetDeliveryInformationRequestDto,
                                                              checkMessageDtoList
                                                             );

        assertThat(expect).isEqualTo(actual);
    }

}
