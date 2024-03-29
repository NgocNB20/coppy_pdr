package jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupImageEntity;
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
public class GoodsGroupImageDaoTest {
    @Autowired
    GoodsGroupImageDao goodsGroupImageDao;

    @Test
    @Order(1)
    public void insert() {
        GoodsGroupImageEntity entity = makeGoodsGroupImageEntity();
        int result = goodsGroupImageDao.insert(entity);
        Assertions.assertNotNull(goodsGroupImageDao.getEntity(999));
    }

    @Test
    @Order(2)
    public void getGoodsGroupImageList() {
        List<Integer> goodsGroupSeqList = new ArrayList<>();
        goodsGroupSeqList.add(999);
        Assertions.assertEquals(1, goodsGroupImageDao.getGoodsGroupImageList(goodsGroupSeqList).size());
    }

    @Test
    @Order(3)
    public void delete() {
        int result = goodsGroupImageDao.delete(goodsGroupImageDao.getEntity(999));
        Assertions.assertNull(goodsGroupImageDao.getEntity(999));
    }

    private GoodsGroupImageEntity makeGoodsGroupImageEntity() {
        GoodsGroupImageEntity entity = new GoodsGroupImageEntity();
        entity.setGoodsGroupSeq(999);
        entity.setImageTypeVersionNo(1);
        entity.setRegistTime(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return entity;
    }
}
