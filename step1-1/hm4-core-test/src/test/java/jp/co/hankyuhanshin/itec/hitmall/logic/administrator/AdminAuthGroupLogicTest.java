package jp.co.hankyuhanshin.itec.hitmall.logic.administrator;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.administrator.AdminAuthGroupDao;
import jp.co.hankyuhanshin.itec.hitmall.dao.administrator.AdminAuthGroupDetailDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.administrator.AdminAuthGroupDetailEntity;
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
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminAuthGroupLogicTest {

    @Autowired
    AdminAuthGroupLogic adminAuthGroupLogic;

    @MockBean
    AdminAuthGroupDao groupDao;

    @MockBean
    AdminAuthGroupDetailDao detailDao;

    @Test
    @Order(1)
    public void execute() {
        doReturn(1).when(groupDao).insert(any(AdminAuthGroupEntity.class));
        doReturn(1).when(detailDao).insert(any(AdminAuthGroupDetailEntity.class));

        AdminAuthGroupEntity group = new AdminAuthGroupEntity();
        group.setAdminAuthGroupSeq(1);

        List<AdminAuthGroupDetailEntity> entityList = new ArrayList<>();
        AdminAuthGroupDetailEntity entity = new AdminAuthGroupDetailEntity();
        entity.setAdminAuthGroupSeq(1);
        entityList.add(entity);
        group.setAdminAuthGroupDetailList(entityList);
        adminAuthGroupLogic.register(group);

        verify(groupDao, times(1)).insert(any(AdminAuthGroupEntity.class));
        verify(detailDao, times(1)).insert(any(AdminAuthGroupDetailEntity.class));
    }
}
