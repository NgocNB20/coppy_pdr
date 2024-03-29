package jp.co.hankyuhanshin.itec.hitmall.dao.inquiry;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryGroupEntity;
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
public class InquiryGroupDaoTest {

    @Autowired
    InquiryGroupDao inquiryGroupDao;

    @Test
    @Order(1)
    public void insert() {
        InquiryGroupEntity entity = makeInquiryGroupEntity();
        int result = inquiryGroupDao.insert(entity);
        Assertions.assertNotNull(inquiryGroupDao.getEntity(99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = inquiryGroupDao.delete(inquiryGroupDao.getEntity(99));
        Assertions.assertNull(inquiryGroupDao.getEntity(99));
    }

    private InquiryGroupEntity makeInquiryGroupEntity() {
        InquiryGroupEntity entity = new InquiryGroupEntity();
        entity.setInquiryGroupSeq(99);
        entity.setShopSeq(99);
        entity.setInquiryGroupName("1");
        entity.setOpenStatus(HTypeOpenDeleteStatus.OPEN);
        entity.setOrderDisplay(1);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
