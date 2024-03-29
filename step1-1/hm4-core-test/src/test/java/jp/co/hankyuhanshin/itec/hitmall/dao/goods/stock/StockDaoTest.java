package jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockEntity;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockSettingEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.seasar.doma.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockDaoTest {

    @Autowired
    StockDao stockDao;

    @Autowired
    StockSettingDao stockSettingDao;

    @Test
    @Order(1)
    public void insert() {
        StockEntity entity = makeStockEntity(999);
        int result = stockDao.insert(entity);

        Assertions.assertNotNull(stockDao.getEntity(999));

        result = stockDao.delete(entity);
        Assertions.assertNull(stockDao.getEntity(999));
    }

    @Test
    @Order(2)
    public void getStockList() {
        int result;
        List<Integer> listGoodsSeq = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            listGoodsSeq.add(i);
            result = stockDao.insert(makeStockEntity(i));
            result = stockSettingDao.insert(makeStockSettingEntity(i));
        }

        List<StockDto> listStockDto = stockDao.getStockList(listGoodsSeq);
        Assertions.assertEquals(3, listStockDto.size());

        for (int i : listGoodsSeq) {
            result = stockDao.delete(stockDao.getEntity(i));
            result = stockSettingDao.delete(stockSettingDao.getEntity(i));
        }
    }

    private StockEntity makeStockEntity(int goodsSeq) {
        StockEntity entity = new StockEntity();
        entity.setGoodsSeq(goodsSeq);
        entity.setShopSeq(999);
        entity.setRealStock(new BigDecimal(1));
        entity.setOrderReserveStock(new BigDecimal(1));
        entity.setRmsOrderBeforeSalesReserveStock(new BigDecimal(1));
        entity.setRmsOrderAfterSalesReserveStock(new BigDecimal(1));
        entity.setRegistTime(Timestamp.valueOf("2099-02-15 11:11:11.0"));
        entity.setUpdateTime(Timestamp.valueOf("2099-02-15 11:11:11.0"));
        return entity;
    }

    private StockSettingEntity makeStockSettingEntity(int goodsSeq) {
        StockSettingEntity entity = new StockSettingEntity();
        entity.setGoodsSeq(goodsSeq);
        entity.setShopSeq(999);
        entity.setRemainderFewStock(new BigDecimal(1));
        entity.setOrderPointStock(new BigDecimal(1));
        entity.setSafetyStock(new BigDecimal(1));
        entity.setRegistTime(Timestamp.valueOf("2099-02-15 11:11:11.0"));
        entity.setUpdateTime(Timestamp.valueOf("2099-02-15 11:11:11.0"));
        return entity;
    }
}
