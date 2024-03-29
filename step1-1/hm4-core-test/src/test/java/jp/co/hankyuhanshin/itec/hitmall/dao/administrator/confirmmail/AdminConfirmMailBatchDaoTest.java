package jp.co.hankyuhanshin.itec.hitmall.dao.administrator.confirmmail;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAdminConfirmMailType;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.confirmmail.AdminConfirmMailEntity;
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
public class AdminConfirmMailBatchDaoTest {

    @Autowired
    AdminConfirmMailDao adminConfirmMailDao;

    @Test
    @Order(1)
    public void insert() {
        AdminConfirmMailEntity entity = makeAdminConfirmMailEntity();
        int result = adminConfirmMailDao.insert(entity);
        Assertions.assertNotNull(adminConfirmMailDao.getEntity(999));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = adminConfirmMailDao.delete(adminConfirmMailDao.getEntity(999));
        Assertions.assertNull(adminConfirmMailDao.getEntity(999));
    }

    private AdminConfirmMailEntity makeAdminConfirmMailEntity() {
        AdminConfirmMailEntity entity = new AdminConfirmMailEntity();
        entity.setAdminConfirmMailSeq(999);
        entity.setShopSeq(999);
        entity.setAdministratorSeq(999);
        entity.setAdminConfirmMail("OK");
        entity.setAdminConfirmMailType(HTypeAdminConfirmMailType.PASSWORD_REISSUE);
        entity.setAdminConfirmMailPassword("OK");
        entity.setEffectiveTime(new Timestamp(System.currentTimeMillis()));
        entity.setRegistTime(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return entity;
    }
}
