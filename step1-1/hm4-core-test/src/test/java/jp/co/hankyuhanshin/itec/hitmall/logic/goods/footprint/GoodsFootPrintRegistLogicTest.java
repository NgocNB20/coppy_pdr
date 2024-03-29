package jp.co.hankyuhanshin.itec.hitmall.logic.goods.footprint;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.footprint.FootprintEntity;
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
public class GoodsFootPrintRegistLogicTest {

    @Autowired
    GoodsFootPrintRegistLogic goodsFootPrintRegistLogic;

    @Test
    @Order(1)
    public void execute() {
        FootprintEntity footprintEntity = new FootprintEntity();
        footprintEntity.setAccessUid("999");
        footprintEntity.setGoodsGroupSeq(999);
        footprintEntity.setShopSeq(999);
        footprintEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        footprintEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        int result = goodsFootPrintRegistLogic.execute(footprintEntity);
        Assertions.assertEquals(1, result);
    }
}
