package jp.co.hankyuhanshin.itec.hitmall.dao.shop.tax;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeTaxRateType;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.tax.TaxRateEntity;
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
public class TaxRateDaoTest {

    @Autowired
    TaxRateDao taxRateDao;

    @Test
    @Order(1)
    public void insert() {
        TaxRateEntity entity = makeTaxRateEntity();
        int result = taxRateDao.insert(entity);
        Assertions.assertNotNull(taxRateDao.getEntity(99, 5));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = taxRateDao.delete(taxRateDao.getEntity(99, 5));
        Assertions.assertNull(taxRateDao.getEntity(99, 5));
    }

    private TaxRateEntity makeTaxRateEntity() {
        TaxRateEntity entity = new TaxRateEntity();
        entity.setTaxSeq(99);
        entity.setRate(new BigDecimal("5"));
        entity.setRateType(HTypeTaxRateType.REDUCED);
        entity.setOrderDisplay(99);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
