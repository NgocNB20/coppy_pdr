package jp.co.hankyuhanshin.itec.hitmall.dao.administrator;

import java.sql.Timestamp;
import java.util.List;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupDetailEntity;
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
public class AdminAuthGroupDetailDaoTest {

    @Autowired
    AdminAuthGroupDetailDao adminAuthGroupDetailDao;

    @Test
    @Order(1)
    public void insert() {
        AdminAuthGroupDetailEntity entity = makeAdminAuthGroupDetailEntity();
        int result = adminAuthGroupDetailDao.insert(entity);
        List<AdminAuthGroupDetailEntity> entityList = adminAuthGroupDetailDao.getDetailList(1001);
        Assertions.assertEquals(1, entityList.size());
    }

    @Test
    @Order(2)
    public void delete() {
        int result = adminAuthGroupDetailDao.deleteAll(1001);
        List<AdminAuthGroupDetailEntity> entityList = adminAuthGroupDetailDao.getDetailList(1001);
        Assertions.assertEquals(0, entityList.size());
    }

    private AdminAuthGroupDetailEntity makeAdminAuthGroupDetailEntity() {
        AdminAuthGroupDetailEntity entity = new AdminAuthGroupDetailEntity();
        entity.setAdminAuthGroupSeq(1001);
        entity.setAuthTypeCode("1");
        entity.setAuthLevel(999);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
