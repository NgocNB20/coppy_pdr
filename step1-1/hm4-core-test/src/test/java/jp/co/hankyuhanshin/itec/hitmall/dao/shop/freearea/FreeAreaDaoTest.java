package jp.co.hankyuhanshin.itec.hitmall.dao.shop.freearea;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeFreeAreaOpenStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeSiteMapFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.FreeAreaEntity;
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
public class FreeAreaDaoTest {

    @Autowired
    FreeAreaDao freeAreaDao;

    @Test
    @Order(1)
    public void insert() {
        FreeAreaEntity entity = makeFreeAreaEntity();
        int result = freeAreaDao.insert(entity);
        Assertions.assertNotNull(freeAreaDao.getEntity(99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = freeAreaDao.delete(freeAreaDao.getEntity(99));
        Assertions.assertNull(freeAreaDao.getEntity(99));
    }

    private FreeAreaEntity makeFreeAreaEntity() {
        FreeAreaEntity entity = new FreeAreaEntity();
        entity.setFreeAreaSeq(99);
        entity.setShopSeq(99);
        entity.setFreeAreaKey("1");
        entity.setOpenStartTime(new Timestamp(new java.util.Date().getTime()));
        entity.setFreeAreaTitle("1");
        entity.setFreeAreaBodyPc("1");
        entity.setFreeAreaBodySp("1");
        entity.setFreeAreaBodyMb("1");
        entity.setTargetGoods("1");
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        entity.setFreeAreaOpenStatus(HTypeFreeAreaOpenStatus.OPEN);
        entity.setSiteMapFlag(HTypeSiteMapFlag.ON);
        return entity;
    }
}
