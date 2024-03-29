package jp.co.hankyuhanshin.itec.hitmall.dao.conveni;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeUseConveni;
import jp.co.hankyuhanshin.itec.hitmall.entity.conveni.ConvenienceEntity;
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
public class ConvenienceDaoTest {

    @Autowired
    ConvenienceDao convenienceDao;

    @Test
    @Order(1)
    public void insert() {
        ConvenienceEntity entity = makeConvenienceEntity();
        int result = convenienceDao.insert(entity);
        Assertions.assertNotNull(convenienceDao.getEntity(99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = convenienceDao.delete(convenienceDao.getEntity(99));
        Assertions.assertNull(convenienceDao.getEntity(99));
    }

    private ConvenienceEntity makeConvenienceEntity() {
        ConvenienceEntity entity = new ConvenienceEntity();
        entity.setConveniSeq(99);
        entity.setPayCode("1");
        entity.setConveniCode("1");
        entity.setPayName("1");
        entity.setConveniName("1");
        entity.setShopSeq(0);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        entity.setDisplayOrder(2);
        entity.setUseFlag(HTypeUseConveni.ON);
        return entity;
    }
}
