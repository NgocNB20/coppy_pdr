package jp.co.hankyuhanshin.itec.hitmall.logic.goods.stock;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockResultEntity;
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
public class StockSupplementLogicTest {

    @Autowired
    StockSupplementLogic stockSupplementLogic;

    @Test
    @Order(1)
    public void execute() {
        StockResultEntity stockResultEntity = new StockResultEntity();
        stockResultEntity.setStockResultSeq(999);
        stockResultEntity.setGoodsSeq(999);
        stockResultEntity.setSupplementTime(new Timestamp(new java.util.Date().getTime()));
        stockResultEntity.setSupplementCount(new BigDecimal("0"));
        stockResultEntity.setRealStock(new BigDecimal("0"));
        stockResultEntity.setProcessPersonName("");
        stockResultEntity.setNote("");
        stockResultEntity.setStockManagementFlag(HTypeStockManagementFlag.ON);
        stockResultEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        stockResultEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        int result = stockSupplementLogic.execute(stockResultEntity, 999, "999");
        Assertions.assertEquals(0, result);
    }
}
