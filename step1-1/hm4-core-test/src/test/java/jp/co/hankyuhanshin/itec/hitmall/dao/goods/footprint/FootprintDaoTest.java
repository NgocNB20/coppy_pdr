package jp.co.hankyuhanshin.itec.hitmall.dao.goods.footprint;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.goods.footprint.FootprintEntity;
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
public class FootprintDaoTest {

    @Autowired
    FootprintDao footprintDao;

    @Test
    @Order(1)
    public void insert() {
        FootprintEntity entity = makeFootprintEntity();
        int result = footprintDao.insert(entity);
        Assertions.assertNotNull(footprintDao.getEntity("999", 999));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = footprintDao.deleteFootprint(999, "999");
        Assertions.assertNull(footprintDao.getEntity("999", 999));
    }

    private FootprintEntity makeFootprintEntity() {
        FootprintEntity entity = new FootprintEntity();
        entity.setAccessUid("999");
        entity.setGoodsGroupSeq(999);
        entity.setShopSeq(999);
        entity.setRegistTime(Timestamp.valueOf("2099-02-03 11:11:11.0"));
        entity.setUpdateTime(Timestamp.valueOf("2099-02-03 11:11:11.0"));

        return entity;
    }
}
