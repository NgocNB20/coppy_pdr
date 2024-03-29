package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goods;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.goods.GoodsUnitDto;
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

import java.util.List;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsUnitListGetLogicTest {

    @Autowired
    GoodsUnitListGetLogic goodsUnitListGetLogic;

    @Test
    @Order(1)
    public void getUnit1List() {
        List<GoodsUnitDto> goodsUnitDtoList = goodsUnitListGetLogic.getUnit1List("1", "2");
        Assertions.assertEquals(0, goodsUnitDtoList.size());
    }

    @Test
    @Order(2)
    public void getUnit2List() {
        List<GoodsUnitDto> goodsUnitDtoList = goodsUnitListGetLogic.getUnit2List("1", "2");
        Assertions.assertEquals(0, goodsUnitDtoList.size());
    }
}
