package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.AbstractWebApiRequestDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetStockResponseDetailDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.webapi.goods.WebApiGetStockResponseDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.webapi.WebApiGetStockLogic;
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
public class OrderGetStockLogicTest {

    @Autowired
    OrderGetStockLogic orderGetStockLogic;

    @MockBean
    /**  WEB-API連携取得  商品在庫数取得*/ private WebApiGetStockLogic webApiGetStockLogic;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        List<String> goodsCodeList = new ArrayList<>();
        goodsCodeList.add("77-55-7039");
        List<String> quantityList = new ArrayList<>();
        quantityList.add("10");
        Map<String, WebApiGetStockResponseDetailDto> result = new HashMap<>();
        WebApiGetStockResponseDetailDto webApiGetStockResponseDetailDto = new WebApiGetStockResponseDetailDto();
        webApiGetStockResponseDetailDto.setGoodsCode("77-55-7039");
        webApiGetStockResponseDetailDto.setStockQuantity(BigDecimal.valueOf(9));

        result.put("77-55-7039", webApiGetStockResponseDetailDto);

        WebApiGetStockResponseDto stockDto = new WebApiGetStockResponseDto();
        List<WebApiGetStockResponseDetailDto> info = new ArrayList<>();
        info.add(webApiGetStockResponseDetailDto);
        stockDto.setInfo(info);

        // モック設定
        doReturn(stockDto).when(webApiGetStockLogic).execute(any(AbstractWebApiRequestDto.class));

        // 試験実行
        Map<String, WebApiGetStockResponseDetailDto> actual =
                        orderGetStockLogic.execute(goodsCodeList, quantityList, null);

        // 戻り値及び呼び出し検証
        verify(webApiGetStockLogic, times(1)).execute(any(AbstractWebApiRequestDto.class));
        assertThat(actual).isEqualTo(result);

    }
}
