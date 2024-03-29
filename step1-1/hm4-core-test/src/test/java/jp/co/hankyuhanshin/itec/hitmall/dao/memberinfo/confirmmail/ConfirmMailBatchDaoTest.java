package jp.co.hankyuhanshin.itec.hitmall.dao.memberinfo.confirmmail;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeConfirmMailType;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.confirmmail.ConfirmMailEntity;
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
public class ConfirmMailBatchDaoTest {

    @Autowired
    ConfirmMailDao confirmMailDao;

    @Test
    @Order(1)
    public void insert() {
        ConfirmMailEntity entity = makeConfirmMailEntity();
        int result = confirmMailDao.insert(entity);
        Assertions.assertNotNull(confirmMailDao.getEntity(999));
    }

    @Test
    @Order(2)
    public void delete() {
        ConfirmMailEntity entity = confirmMailDao.getEntity(999);
        int result = confirmMailDao.delete(entity);
        Assertions.assertNull(confirmMailDao.getEntity(999));
    }

    private ConfirmMailEntity makeConfirmMailEntity() {
        ConfirmMailEntity entity = new ConfirmMailEntity();
        entity.setConfirmMailSeq(999);
        entity.setShopSeq(999);
        entity.setMemberInfoSeq(999);
        entity.setOrderSeq(999);
        entity.setConfirmMail("confirm");
        entity.setConfirmMailType(HTypeConfirmMailType.MEMBERINFO_MAIL_UPDATE);
        entity.setConfirmMailPassword("password");
        entity.setEffectiveTime(Timestamp.valueOf("2099-02-03 11:11:11.0"));
        entity.setRegistTime(Timestamp.valueOf("2099-02-03 11:11:11.0"));
        entity.setUpdateTime(Timestamp.valueOf("2099-02-03 11:11:11.0"));

        return entity;
    }
}
