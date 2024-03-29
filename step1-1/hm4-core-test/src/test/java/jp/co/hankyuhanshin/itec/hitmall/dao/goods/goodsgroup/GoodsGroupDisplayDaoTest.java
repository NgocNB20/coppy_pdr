package jp.co.hankyuhanshin.itec.hitmall.dao.goods.goodsgroup;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUnitManagementFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.goodsgroup.GoodsGroupDisplayEntity;
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
public class GoodsGroupDisplayDaoTest {

    @Autowired
    GoodsGroupDisplayDao goodsGroupDisplayDao;

    @Test
    @Order(1)
    public void insert() {
        GoodsGroupDisplayEntity entity = makeGoodsGroupDisplayEntity();
        int result = goodsGroupDisplayDao.insert(entity);
        Assertions.assertNotNull(goodsGroupDisplayDao.getEntity(999));
    }

    @Test
    @Order(2)
    public void getGoodsGroupDisplayList() {
        int result = goodsGroupDisplayDao.delete(goodsGroupDisplayDao.getEntity(999));
        Assertions.assertNull(goodsGroupDisplayDao.getEntity(999));
    }

    private GoodsGroupDisplayEntity makeGoodsGroupDisplayEntity() {
        GoodsGroupDisplayEntity entity = new GoodsGroupDisplayEntity();
        entity.setGoodsGroupSeq(999);
        entity.setUnitManagementFlag(HTypeUnitManagementFlag.ON);
        entity.setRegistTime(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return entity;
    }
}
