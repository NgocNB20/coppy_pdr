package jp.co.hankyuhanshin.itec.hitmall.dao.shop.tax;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.tax.TaxEntity;
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
public class TaxDaoTest {

    @Autowired
    TaxDao taxDao;

    @Test
    @Order(1)
    public void insert() {
        TaxEntity entity = makeTaxEntity();
        int result = taxDao.insert(entity);
        Assertions.assertNotNull(taxDao.getEntity(99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = taxDao.delete(taxDao.getEntity(99));
        Assertions.assertNull(taxDao.getEntity(99));
    }

    private TaxEntity makeTaxEntity() {
        TaxEntity entity = new TaxEntity();
        entity.setTaxSeq(99);
        entity.setStartTime(new Timestamp(new java.util.Date().getTime()));
        entity.setEndTime(new Timestamp(new java.util.Date().getTime()));
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
