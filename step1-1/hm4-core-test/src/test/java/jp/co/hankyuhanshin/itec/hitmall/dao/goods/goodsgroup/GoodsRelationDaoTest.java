package jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsRelationEntity;
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
import java.util.List;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsRelationDaoTest {

    @Autowired
    GoodsRelationDao goodsRelationDao;

    @Test
    @Order(1)
    public void insert() {
        GoodsRelationEntity entity = makeGoodsRelationEntity(998);
        int result = goodsRelationDao.insert(entity);
        Assertions.assertNotNull(goodsRelationDao.getEntity(999, 998));
    }

    @Test
    @Order(2)
    public void delete() {
        GoodsRelationEntity entity = makeGoodsRelationEntity(997);
        int result = goodsRelationDao.insert(entity);

        List<GoodsRelationEntity> entityList = goodsRelationDao.getGoodsRelationEntityListByGoodsGroupSeq(999);
        Assertions.assertEquals(2, entityList.size());

        for (GoodsRelationEntity e : entityList) {
            goodsRelationDao.delete(e);
        }
        Assertions.assertEquals(0, goodsRelationDao.getGoodsRelationEntityListByGoodsGroupSeq(999).size());
        ;
    }

    private GoodsRelationEntity makeGoodsRelationEntity(int goodsRelationGroupSeq) {
        GoodsRelationEntity entity = new GoodsRelationEntity();
        entity.setGoodsGroupSeq(999);
        entity.setGoodsRelationGroupSeq(goodsRelationGroupSeq);
        entity.setRegistTime(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return entity;
    }
}
