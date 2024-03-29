package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockEntity;
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

import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockRegistLogicTest {

    @Autowired
    StockRegistLogic stockRegistLogic;

    @Test
    @Order(1)
    public void execute() {
        List<StockEntity> stockEntityList = new ArrayList<>();
        StockEntity entity = new StockEntity();
        entity.setGoodsSeq(999);
        entity.setShopSeq(999);
        entity.setRealStock(new BigDecimal("0"));
        entity.setOrderReserveStock(new BigDecimal("0"));
        entity.setRmsOrderBeforeSalesReserveStock(new BigDecimal("0"));
        entity.setRmsOrderAfterSalesReserveStock(new BigDecimal("0"));
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        stockEntityList.add(entity);

        int result = stockRegistLogic.execute(stockEntityList);
        Assertions.assertEquals(1, result);
    }
}
