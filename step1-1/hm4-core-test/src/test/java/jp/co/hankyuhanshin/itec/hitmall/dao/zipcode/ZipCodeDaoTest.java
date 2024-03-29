package jp.co.hankyuhanshin.itec.hitmall.dao.zipcode;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.zipcode.ZipCodeEntity;
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
public class ZipCodeDaoTest {

    @Autowired
    ZipCodeDao zipCodeDao;

    @Test
    @Order(1)
    public void insert() {
        ZipCodeEntity entity = makeZipCodeEntity();
        int result = zipCodeDao.insert(entity);
        Assertions.assertNotNull(zipCodeDao.getEntity("000001", "55555", "5550001", "大阪", "大阪", "大阪"));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = zipCodeDao.delete(zipCodeDao.getEntity("000001", "55555", "5550001", "大阪", "大阪", "大阪"));
        Assertions.assertNull(zipCodeDao.getEntity("000001", "55555", "5550001", "大阪", "大阪", "大阪"));
    }

    private ZipCodeEntity makeZipCodeEntity() {
        ZipCodeEntity entity = new ZipCodeEntity();
        entity.setLocalCode("000001");
        entity.setOldZipCode("55555");
        entity.setZipCode("5550001");
        entity.setPrefectureKana("オオサカ");
        entity.setCityKana("オオサカ");
        entity.setTownKana("オオサカ");
        entity.setPrefectureName("大阪");
        entity.setCityName("大阪");
        entity.setTownName("大阪");
        entity.setAnyZipFlag("1");
        entity.setNumberFlag1("1");
        entity.setNumberFlag2("1");
        entity.setNumberFlag3("1");
        entity.setUpdateVisibleType("1");
        entity.setUpdateNoteType("1");
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
