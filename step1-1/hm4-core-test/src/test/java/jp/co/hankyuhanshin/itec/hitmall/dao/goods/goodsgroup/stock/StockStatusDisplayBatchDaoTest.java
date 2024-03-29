package jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup.stock;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeStockStatusType;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.stock.StockStatusDisplayEntity;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockStatusDisplayBatchDaoTest {

    @Autowired
    StockStatusDisplayDaoMock stockStatusDisplayDaoMock;

    @Autowired
    StockStatusDisplayDao stockStatusDisplayDao;

    @Test
    @Order(1)
    public void getEntity() {
        StockStatusDisplayEntity entity = makeStockStatusDisplayEntity(999);
        int result = stockStatusDisplayDaoMock.insert(entity);
        Assertions.assertNotNull(stockStatusDisplayDao.getEntity(999));

        result = stockStatusDisplayDaoMock.delete(entity);
    }

    @Test
    @Order(2)
    public void getStockStatusDisplayList() {
        List<Integer> goodsGroupSeqList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            goodsGroupSeqList.add(i);
            StockStatusDisplayEntity entity = makeStockStatusDisplayEntity(i);
            int result = stockStatusDisplayDaoMock.insert(entity);
        }
        Assertions.assertNotNull(stockStatusDisplayDao.getStockStatusDisplayList(goodsGroupSeqList));

        for (int goodsGroupSeq : goodsGroupSeqList) {
            int result = stockStatusDisplayDaoMock.delete(stockStatusDisplayDao.getEntity(goodsGroupSeq));
        }
    }

    private StockStatusDisplayEntity makeStockStatusDisplayEntity(int goodsGroupSeq) {
        StockStatusDisplayEntity entity = new StockStatusDisplayEntity();
        entity.setGoodsGroupSeq(goodsGroupSeq);
        entity.setStockStatusPc(HTypeStockStatusType.NO_SALE);
        entity.setRegistTime(Timestamp.valueOf("2099-02-12 11:11:11.0"));
        entity.setUpdateTime(Timestamp.valueOf("2099-02-12 11:11:11.0"));
        return entity;
    }
}
