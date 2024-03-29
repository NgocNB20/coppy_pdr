package jp.co.hankyuhanshin.itec.hitmall.dao.goods.stock;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.dto.goods.stock.StockResultSearchForDaoConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.stock.StockResultEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockResultDaoTest {

    @Autowired
    StockResultDao stockResultDao;

    @Test
    @Order(1)
    public void insert() {
        StockResultEntity entity = makeStockResultEntity();
        int result = stockResultDao.insert(entity);

        StockResultSearchForDaoConditionDto dto = new StockResultSearchForDaoConditionDto();
        dto.setGoodsSeq(999);

        List<StockResultEntity> listEntity = stockResultDao.getSearchStockResultList(dto, SelectOptions.get());

        Assertions.assertNotNull(listEntity);

        for (StockResultEntity entity1 : listEntity) {
            result = stockResultDao.delete(entity1);
        }

        Assertions.assertEquals(0, stockResultDao.getSearchStockResultList(dto, SelectOptions.get()).size());
    }

    private StockResultEntity makeStockResultEntity() {
        StockResultEntity entity = new StockResultEntity();
        entity.setGoodsSeq(999);
        entity.setSupplementTime(Timestamp.valueOf("2099-02-15 11:11:11.0"));
        entity.setSupplementCount(new BigDecimal(1));
        entity.setRealStock(new BigDecimal(1));
        entity.setProcessPersonName("1");
        entity.setNote("1");
        entity.setStockManagementFlag(HTypeStockManagementFlag.ON);
        entity.setRegistTime(Timestamp.valueOf("2099-02-15 11:11:11.0"));
        entity.setUpdateTime(Timestamp.valueOf("2099-02-15 11:11:11.0"));

        return entity;
    }
}
