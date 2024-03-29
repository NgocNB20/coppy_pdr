package jp.co.hankyuhanshin.itec.hitmall.dao.administrator;

import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupEntity;
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
public class AdminAuthGroupDaoTest {

    @Autowired
    AdminAuthGroupDao adminAuthGroupDao;

    @Test
    @Order(1)
    public void insert() {
        AdminAuthGroupEntity entity = makeAdminAuthGroupEntity();
        int result = adminAuthGroupDao.insert(entity);
        Assertions.assertNotNull(adminAuthGroupDao.getEntityByName(999, "TOP"));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = adminAuthGroupDao.delete(adminAuthGroupDao.getEntityByName(999, "TOP"));
        Assertions.assertNull(adminAuthGroupDao.getEntityByName(999, "TOP"));
    }

    private AdminAuthGroupEntity makeAdminAuthGroupEntity() {
        AdminAuthGroupEntity entity = new AdminAuthGroupEntity();
        entity.setShopSeq(999);
        entity.setAuthGroupDisplayName("TOP");
        entity.setRegistTime(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return entity;
    }
}
