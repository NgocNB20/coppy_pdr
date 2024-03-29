package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.order.WebApiGetConsumptionTaxRateResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetConsumptionTaxRateLogic;
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

import java.math.BigDecimal;
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
public class OrderGetConsumptionTaxRateLogicTest {

    @Autowired
    OrderGetConsumptionTaxRateLogic orderGetConsumptionTaxRateLogic;

    @MockBean
    /** WEB-API連携クラス 消費税率取得  */ private WebApiGetConsumptionTaxRateLogic webApiGetConsumptionTaxRateLogic;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> result = new HashMap<>();
        List<String> goodsCodeList = new ArrayList<>();
        goodsCodeList.add("77-55-7039");

        WebApiGetConsumptionTaxRateResponseDto webApiGetConsumptionTaxRateResponseDto =
                        new WebApiGetConsumptionTaxRateResponseDto();
        List<WebApiGetConsumptionTaxRateResponseDetailDto> info = new ArrayList<>();
        WebApiGetConsumptionTaxRateResponseDetailDto webApiGetConsumptionTaxRateResponseDetailDto =
                        new WebApiGetConsumptionTaxRateResponseDetailDto();
        webApiGetConsumptionTaxRateResponseDetailDto.setGoodsCode("77-55-7039");
        webApiGetConsumptionTaxRateResponseDetailDto.setTaxRate(BigDecimal.valueOf(10));
        info.add(webApiGetConsumptionTaxRateResponseDetailDto);
        webApiGetConsumptionTaxRateResponseDto.setInfo(info);

        result.put("77-55-7039", webApiGetConsumptionTaxRateResponseDetailDto);

        // モック設定
        doReturn(webApiGetConsumptionTaxRateResponseDto).when(webApiGetConsumptionTaxRateLogic)
                                                        .execute(any(AbstractWebApiRequestDto.class));

        // 試験実行
        Map<String, WebApiGetConsumptionTaxRateResponseDetailDto> actual =
                        orderGetConsumptionTaxRateLogic.execute(goodsCodeList);

        // 戻り値及び呼び出し検証
        verify(webApiGetConsumptionTaxRateLogic, times(1)).execute(any(AbstractWebApiRequestDto.class));
        assertThat(actual).isEqualTo(result);

    }
}
