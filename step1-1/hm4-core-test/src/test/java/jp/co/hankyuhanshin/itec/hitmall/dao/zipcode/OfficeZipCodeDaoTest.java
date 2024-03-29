package jp.co.hankyuhanshin.itec.hitmall.dao.zipcode;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.zipcode.OfficeZipCodeEntity;
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
public class OfficeZipCodeDaoTest {

    @Autowired
    OfficeZipCodeDao officeZipCodeDao;

    @Test
    @Order(1)
    public void insert() {
        OfficeZipCodeEntity entity = makeOfficeZipCodeEntity();
        int result = officeZipCodeDao.insert(entity);
        Assertions.assertNotNull(
                        officeZipCodeDao.getEntity("001", "シケン", "試験", "大阪", "大阪", "大阪", "12", "5550001", "55555"));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = officeZipCodeDao.delete(
                        officeZipCodeDao.getEntity("001", "シケン", "試験", "大阪", "大阪", "大阪", "12", "5550001", "55555"));
        Assertions.assertNull(
                        officeZipCodeDao.getEntity("001", "シケン", "試験", "大阪", "大阪", "大阪", "12", "5550001", "55555"));
    }

    private OfficeZipCodeEntity makeOfficeZipCodeEntity() {
        OfficeZipCodeEntity entity = new OfficeZipCodeEntity();
        entity.setOfficeCode("001");
        entity.setOfficeKana("シケン");
        entity.setOfficeName("試験");
        entity.setPrefectureName("大阪");
        entity.setCityName("大阪");
        entity.setTownName("大阪");
        entity.setNumbers("12");
        entity.setZipCode("5550001");
        entity.setOldZipCode("55555");
        entity.setHandlingBranchName("大阪");
        entity.setIndividualType("1");
        entity.setAnyZipFlag("1");
        entity.setUpdateCode("1");
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
