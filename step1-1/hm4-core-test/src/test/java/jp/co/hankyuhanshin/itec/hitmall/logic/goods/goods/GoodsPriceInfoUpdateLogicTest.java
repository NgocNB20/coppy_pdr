package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.goods.goods.GoodsDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goods.GoodsEntity;
import org.junit.jupiter.api.Assertions;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsPriceInfoUpdateLogicTest {

    @Autowired
    GoodsPriceInfoUpdateLogic goodsPriceInfoUpdateLogic;

    @MockBean
    GoodsDao goodsDao;

    @Test
    @Order(1)
    public void execute01() {
        GoodsEntity goodsEntity = new GoodsEntity();

        doReturn(0).when(goodsDao).update(any());

        int actual = goodsPriceInfoUpdateLogic.execute(goodsEntity);
        Assertions.assertEquals(0, actual);
    }

    @Test
    @Order(1)
    public void execute02() {
        GoodsEntity goodsEntity = new GoodsEntity();

        doReturn(1).when(goodsDao).update(any());

        int actual = goodsPriceInfoUpdateLogic.execute(goodsEntity);
        Assertions.assertEquals(1, actual);
    }
}
