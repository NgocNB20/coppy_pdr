package jp.co.hankyuhanshin.itec.hitmall.dao.inquiry;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInquiryRequestType;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryDetailEntity;
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
public class InquiryDetailDaoTest {

    @Autowired
    InquiryDetailDao inquiryDetailDao;

    @Test
    @Order(1)
    public void insert() {
        InquiryDetailEntity entity = makeInquiryDetailEntity();
        int result = inquiryDetailDao.insert(entity);
        Assertions.assertNotNull(inquiryDetailDao.getEntity(99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = inquiryDetailDao.delete(inquiryDetailDao.getEntity(99));
        Assertions.assertNull(inquiryDetailDao.getEntity(99));
    }

    private InquiryDetailEntity makeInquiryDetailEntity() {
        InquiryDetailEntity entity = new InquiryDetailEntity();
        entity.setInquirySeq(99);
        entity.setInquiryVersionNo(99);
        entity.setRequestType(HTypeInquiryRequestType.OPERATOR);
        entity.setInquiryTime(new Timestamp(new java.util.Date().getTime()));
        entity.setInquiryBody("1");
        entity.setDivisionName("1");
        entity.setRepresentative("1");
        entity.setContactTel("1");
        entity.setOperator("1");
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
