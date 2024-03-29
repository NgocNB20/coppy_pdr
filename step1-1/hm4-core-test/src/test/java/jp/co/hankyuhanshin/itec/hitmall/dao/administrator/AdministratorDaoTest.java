package jp.co.hankyuhanshin.itec.hitmall.dao.administrator;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAdministratorStatus;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordNeedChangeFlag;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePasswordSHA256EncryptedFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupEntity;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdministratorEntity;
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
public class AdministratorDaoTest {

    @Autowired
    AdministratorDao administratorDao;

    @Test
    @Order(1)
    public void insert() {
        AdministratorEntity entity = makeAdministratorEntity();
        int result = administratorDao.insert(entity);
        Assertions.assertNotNull(administratorDao.getEntity(9999));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = administratorDao.delete(administratorDao.getEntity(9999));
        Assertions.assertNull(administratorDao.getEntity(9999));
    }

    private AdministratorEntity makeAdministratorEntity() {
        AdministratorEntity entity = new AdministratorEntity();
        entity.setAdministratorSeq(9999);
        entity.setShopSeq(9999);
        entity.setAdministratorStatus(HTypeAdministratorStatus.STOP);
        entity.setAdministratorId("demoadmin");
        entity.setAdministratorPassword("1248215h342b2fb124214");
        entity.setMail("test@mail");
        entity.setAdministratorLastName("管理");
        entity.setAdministratorFirstName("管理");
        entity.setAdministratorLastKana("カンリ");
        entity.setAdministratorFirstKana("カンリ");
        entity.setUseStartDate(new Timestamp(new java.util.Date().getTime()));
        entity.setUseEndDate(new Timestamp(new java.util.Date().getTime()));
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        entity.setAdminAuthGroupSeq(1001);
        entity.setPasswordChangeTime(new Timestamp(new java.util.Date().getTime()));
        entity.setPasswordExpiryDate(new Timestamp(new java.util.Date().getTime()));
        entity.setLoginFailureCount(0);
        entity.setAccountLockTime(new Timestamp(new java.util.Date().getTime()));
        entity.setPasswordNeedChangeFlag(HTypePasswordNeedChangeFlag.ON);
        entity.setPasswordSHA256EncryptedFlag(HTypePasswordSHA256EncryptedFlag.ENCRYPTED);
        return entity;
    }
}
