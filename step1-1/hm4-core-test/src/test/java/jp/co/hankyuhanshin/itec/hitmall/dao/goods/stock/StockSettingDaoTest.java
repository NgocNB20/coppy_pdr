package jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock;

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

import java.math.BigDecimal;
import java.sql.Timestamp;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockSettingDaoTest {

    @Autowired
    StockSettingDao stockSettingDao;

    @Test
    @Order(1)
    public void insert() {
        StockSettingEntity entity = makeStockSettingEntity();
        int result = stockSettingDao.insert(entity);

        Assertions.assertNotNull(stockSettingDao.getEntity(999));
    }

    @Test
    @Order(2)
    public void delete() {
        StockSettingEntity entity = stockSettingDao.getEntity(999);
        int result = stockSettingDao.delete(entity);

        Assertions.assertNull(stockSettingDao.getEntity(999));
    }

    private StockSettingEntity makeStockSettingEntity() {
        StockSettingEntity entity = new StockSettingEntity();
        entity.setGoodsSeq(999);
        entity.setShopSeq(999);
        entity.setRemainderFewStock(new BigDecimal(1));
        entity.setOrderPointStock(new BigDecimal(1));
        entity.setSafetyStock(new BigDecimal(1));
        entity.setRegistTime(Timestamp.valueOf("2099-02-15 11:11:11.0"));
        entity.setUpdateTime(Timestamp.valueOf("2099-02-15 11:11:11.0"));
        return entity;
    }
}
