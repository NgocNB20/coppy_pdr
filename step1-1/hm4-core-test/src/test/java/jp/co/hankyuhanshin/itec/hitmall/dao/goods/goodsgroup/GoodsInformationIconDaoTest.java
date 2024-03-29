package jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsInformationIconEntity;
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
public class GoodsInformationIconDaoTest {

    @Autowired
    GoodsInformationIconDao goodsInformationIconDao;

    @Test
    @Order(1)
    public void insert() {
        GoodsInformationIconEntity entity = makeGoodsInformationIconEntity();
        int result = goodsInformationIconDao.insert(entity);
        Assertions.assertNotNull(goodsInformationIconDao.getEntity(999));
    }

    @Test
    @Order(2)
    public void updateOrderDisplay() {
        GoodsInformationIconEntity entity = goodsInformationIconDao.getGoodsInformationIconBySeqForUpdate(999);
        int result = goodsInformationIconDao.updateOrderDisplay(entity.getIconSeq(), entity.getShopSeq(), 101,
                                                                new Timestamp(System.currentTimeMillis())
                                                               );
        Assertions.assertEquals(101, goodsInformationIconDao.getEntity(999).getOrderDisplay());
    }

    @Test
    @Order(3)
    public void delete() {
        int result = goodsInformationIconDao.delete(goodsInformationIconDao.getEntity(999));
        Assertions.assertNull(goodsInformationIconDao.getEntity(999));
    }

    private GoodsInformationIconEntity makeGoodsInformationIconEntity() {
        GoodsInformationIconEntity entity = new GoodsInformationIconEntity();
        entity.setIconSeq(999);
        entity.setShopSeq(999);
        entity.setIconName("hello");
        entity.setRegistTime(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return entity;
    }
}
