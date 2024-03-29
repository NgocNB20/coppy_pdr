package jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery;

import java.util.Date;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.HolidayEntity;
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
public class HolidayDaoTest {

    @Autowired
    HolidayDao holidayDao;

    @Test
    @Order(1)
    public void insert() {
        HolidayEntity entity = makeHolidayEntity();
        int result = holidayDao.insert(entity);
        Assertions.assertNotNull(holidayDao.getHolidayByYearAndDate(2021, new Date(), 99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = holidayDao.delete(holidayDao.getHolidayByYearAndDate(2021, new Date(), 99));
        Assertions.assertNull(holidayDao.getHolidayByYearAndDate(2021, new Date(), 99));
    }

    private HolidayEntity makeHolidayEntity() {
        HolidayEntity entity = new HolidayEntity();
        entity.setDeliveryMethodSeq(99);
        entity.setDate(new Date());
        entity.setYear(2021);
        entity.setName("ITEC");
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
