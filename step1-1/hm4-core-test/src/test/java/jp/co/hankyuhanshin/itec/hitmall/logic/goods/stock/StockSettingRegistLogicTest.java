package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockSettingEntity;
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
import java.util.Map;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockSettingRegistLogicTest {

    @Autowired
    StockSettingRegistLogic stockSettingRegistLogic;

    @Test
    @Order(1)
    public void execute() {
        List<StockSettingEntity> stockSettingEntityList = new ArrayList<>();
        StockSettingEntity entity = new StockSettingEntity();
        entity.setGoodsSeq(999);
        entity.setShopSeq(999);
        entity.setRemainderFewStock(new BigDecimal("0"));
        entity.setOrderPointStock(new BigDecimal("0"));
        entity.setSafetyStock(new BigDecimal("0"));
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        stockSettingEntityList.add(entity);
        Map<String, Integer> map = stockSettingRegistLogic.execute(999, stockSettingEntityList);
        Assertions.assertEquals(2, map.size());
    }
}
