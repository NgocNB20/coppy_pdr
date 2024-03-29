package jp.co.hankyuhanshin.itec.hitmall.dao.shop.top;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeGoodsRankingType;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.top.GoodsRankingEntity;
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
public class GoodsRankingDaoTest {

    @Autowired
    GoodsRankingDao goodsRankingDao;

    @Test
    @Order(1)
    public void insert() {
        GoodsRankingEntity entity = makeGoodsRankingEntity();
        int result = goodsRankingDao.insert(entity);
        Assertions.assertNotNull(goodsRankingDao.getEntity(99, "1", 99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = goodsRankingDao.delete(goodsRankingDao.getEntity(99, "1", 99));
        Assertions.assertNull(goodsRankingDao.getEntity(99, "1", 99));
    }

    private GoodsRankingEntity makeGoodsRankingEntity() {
        GoodsRankingEntity entity = new GoodsRankingEntity();
        entity.setShopSeq(99);
        entity.setGoodsRankingType(HTypeGoodsRankingType.ORDER_COUNT);
        entity.setGoodsGroupSeq(99);
        entity.setTotalValue(new BigDecimal("0"));
        entity.setTotalTargetName("1");
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
