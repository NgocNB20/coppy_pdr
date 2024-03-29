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
import jp.co.hankyuhanshin.itec.hitmall.application.commoninfo.CommonInfoBase;
import jp.co.hankyuhanshin.itec.hitmall.application.commoninfo.impl.CommonInfoBaseImpl;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockResultEntity;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods.GoodsStatusCheckLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock.StockSupplementLogic;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockSupplementServiceTest {

    @Autowired
    StockSupplementService service;

    @MockBean
    private StockSupplementLogic logic;

    @MockBean
    private GoodsStatusCheckLogic goodsStatusCheckLogic;

    @Test
    @Order(1)
    public void executeTest() {

        StockResultEntity dto = new StockResultEntity();
        dto.setGoodsSeq(1);
        dto.setStockResultSeq(1);
        dto.setNote("abc");
        dto.setProcessPersonName("abc");

        GoodsEntity good = new GoodsEntity();
        good.setShopSeq(1);
        good.setGoodsSeq(1);
        good.setGoodsCode("abc");

        CommonInfoBaseImpl cf = new CommonInfoBaseImpl();
        cf.setShopSeq(1);

        doReturn(true).when(goodsStatusCheckLogic).execute(any(Integer.class), any(String.class));
        doReturn(1).when(logic).execute(any(StockResultEntity.class), any(Integer.class), any(String.class));

        int actual = service.execute(dto, "abc");

        verify(goodsStatusCheckLogic, times(1)).execute(any(Integer.class), any(String.class));
        verify(logic, times(1)).execute(any(StockResultEntity.class), any(Integer.class), any(String.class));
        assertThat(1).isEqualTo(actual);
    }
}
