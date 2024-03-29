package jp.co.hankyuhanshin.itec.hitmall.service.goods.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockSearchResultDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockSearchResultGetLogic;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockSearchResultGetServiceTest {

    @Autowired
    StockSearchResultGetService service;

    @MockBean
    private StockSearchResultGetLogic logic;

    @Test
    @Order(1)
    public void executeTest() {

        StockSearchForDaoConditionDto dto = new StockSearchForDaoConditionDto();
        List<StockSearchResultDto> resultList = new ArrayList<>();

        doReturn(resultList).when(logic).execute(any(StockSearchForDaoConditionDto.class));

        List<StockSearchResultDto> actual = service.execute(dto);

        verify(logic, times(1)).execute(any(StockSearchForDaoConditionDto.class));
        assertThat(actual).isEqualTo(resultList);
    }
}
