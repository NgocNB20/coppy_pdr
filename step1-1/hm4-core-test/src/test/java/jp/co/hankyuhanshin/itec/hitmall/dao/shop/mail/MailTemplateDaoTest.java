package jp.co.hankyuhanshin.itec.hitmall.dao.shop.mail;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateDefaultFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeMailTemplateType;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.mail.MailTemplateEntity;
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
public class MailTemplateDaoTest {

    @Autowired
    MailTemplateDao mailTemplateDao;

    @Test
    @Order(1)
    public void insert() {
        MailTemplateEntity entity = makeMailTemplateEntity();
        int result = mailTemplateDao.insert(entity);
        Assertions.assertNotNull(mailTemplateDao.getEntity(99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = mailTemplateDao.delete(mailTemplateDao.getEntity(99));
        Assertions.assertNull(mailTemplateDao.getEntity(99));
    }

    private MailTemplateEntity makeMailTemplateEntity() {
        MailTemplateEntity entity = new MailTemplateEntity();
        entity.setMailTemplateSeq(99);
        entity.setShopSeq(99);
        entity.setMailTemplateName("1");
        entity.setMailTemplateType(HTypeMailTemplateType.MEMBER_PREREGISTRATION);
        entity.setMailTemplateDefaultFlag(HTypeMailTemplateDefaultFlag.ON);
        entity.setMailTemplateSubject("1");
        entity.setMailTemplateFromAddress("1");
        entity.setMailTemplateToAddress("1");
        entity.setMailTemplateCcAddress("1");
        entity.setMailTemplateBccAddress("1");
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
