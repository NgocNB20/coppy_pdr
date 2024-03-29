package jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupPopularityEntity;
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

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsGroupPopularityDaoTest {

    @Autowired
    GoodsGroupPopularityDao goodsGroupPopularityDao;

    @Test
    @Order(1)
    public void insert() {
        GoodsGroupPopularityEntity entity = makeGoodsGroupPopularityEntity();
        int result = goodsGroupPopularityDao.insert(entity);
        Assertions.assertNotNull(goodsGroupPopularityDao.getEntity(999));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = goodsGroupPopularityDao.delete(goodsGroupPopularityDao.getEntity(999));
        Assertions.assertNull(goodsGroupPopularityDao.getEntity(999));
    }

    private GoodsGroupPopularityEntity makeGoodsGroupPopularityEntity() {
        GoodsGroupPopularityEntity entity = new GoodsGroupPopularityEntity();
        entity.setGoodsGroupSeq(999);
        entity.setPopularityCount(1);
        entity.setRegistTime(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return entity;
    }
}
