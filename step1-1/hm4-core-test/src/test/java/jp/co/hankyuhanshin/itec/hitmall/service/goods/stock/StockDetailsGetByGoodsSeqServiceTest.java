package jp.co.hankyuhanshin.itec.hitmall.service.goods.stock;

import static org.assertj.core.api.Assertions.assertThat;
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
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockDetailsGetByGoodsSeqLogic;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockDetailsGetByGoodsSeqServiceTest {

    @Autowired
    StockDetailsGetByGoodsSeqService service;

    @MockBean
    private StockDetailsGetByGoodsSeqLogic logic;

    @Test
    @Order(1)
    public void executeTest() {

        StockDetailsDto dto = new StockDetailsDto();
        dto.setShopSeq(1);

        doReturn(dto).when(logic).execute(any(Integer.class));

        StockDetailsDto actual = service.execute(1);

        verify(logic, times(1)).execute(any(Integer.class));
        assertThat(actual).isEqualTo(dto);
    }
}
