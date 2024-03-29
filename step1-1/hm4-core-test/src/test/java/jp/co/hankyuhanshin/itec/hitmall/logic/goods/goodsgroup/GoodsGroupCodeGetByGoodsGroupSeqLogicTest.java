package jp.co.hankyuhanshin.itec.hitmall.logic.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
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

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsGroupCodeGetByGoodsGroupSeqLogicTest {

    @Autowired
    GoodsGroupCodeGetByGoodsGroupSeqLogic goodsGroupCodeGetByGoodsGroupSeqLogic;

    @Test
    @Order(1)
    public void execute01() {
        Integer goodsGroupSeq = 10034116;
        String actual = goodsGroupCodeGetByGoodsGroupSeqLogic.execute(goodsGroupSeq);
        Assertions.assertEquals("series-1181", actual);
    }

    @Test
    @Order(1)
    public void execute02() {
        Integer goodsGroupSeq = 1;
        String actual = goodsGroupCodeGetByGoodsGroupSeqLogic.execute(goodsGroupSeq);
        Assertions.assertEquals(null, actual);
    }
}
