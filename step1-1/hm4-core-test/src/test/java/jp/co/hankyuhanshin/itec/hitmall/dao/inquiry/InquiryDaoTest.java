package jp.co.hankyuhanshin.itec.hitmall.dao.inquiry;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInquiryStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeInquiryType;
import jp.co.hankyuhanshin.itec.hitmall.entity.inquiry.InquiryEntity;
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
public class InquiryDaoTest {

    @Autowired
    InquiryDao inquiryDao;

    @Test
    @Order(1)
    public void insert() {
        InquiryEntity entity = makeInquiryEntity();
        int result = inquiryDao.insert(entity);
        Assertions.assertNotNull(inquiryDao.getEntity(99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = inquiryDao.delete(inquiryDao.getEntity(99));
        Assertions.assertNull(inquiryDao.getEntity(99));
    }

    private InquiryEntity makeInquiryEntity() {
        InquiryEntity entity = new InquiryEntity();
        entity.setInquirySeq(99);
        entity.setShopSeq(1);
        entity.setInquiryCode("1");
        entity.setInquiryLastName("1");
        entity.setInquiryFirstName("1");
        entity.setInquiryMail("1");
        entity.setInquiryTitle("1");
        entity.setInquiryBody("1");
        entity.setInquiryTime(new Timestamp(new java.util.Date().getTime()));
        entity.setInquiryStatus(HTypeInquiryStatus.SENDING);
        entity.setAnswerTime(new Timestamp(new java.util.Date().getTime()));
        entity.setAnswerTitle("1");
        entity.setAnswerBody("1");
        entity.setAnswerFrom("1");
        entity.setAnswerTo("1");
        entity.setAnswerBcc("1");
        entity.setInquiryGroupSeq(99);
        entity.setInquiryLastKana("1");
        entity.setInquiryFirstKana("1");
        entity.setInquiryZipCode("1");
        entity.setInquiryPrefecture("1");
        entity.setInquiryAddress1("1");
        entity.setInquiryAddress2("1");
        entity.setInquiryAddress3("1");
        entity.setInquiryTel("1");
        entity.setInquiryMobileTel("1");
        entity.setProcessPersonName("1");
        entity.setVersionNo(99);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        entity.setInquiryType(HTypeInquiryType.ORDER);
        entity.setMemberInfoSeq(99);
        entity.setOrderCode("1");
        entity.setFirstInquiryTime(new Timestamp(new java.util.Date().getTime()));
        entity.setLastUserInquiryTime(new Timestamp(new java.util.Date().getTime()));
        entity.setLastOperatorInquiryTime(new Timestamp(new java.util.Date().getTime()));
        entity.setLastRepresentative("1");
        entity.setMemo("1");
        entity.setCooperationMemo("1");
        entity.setMemberInfoId("1");
        return entity;
    }
}
