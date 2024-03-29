package jp.co.hankyuhanshin.itec.hitmall.logic.administrator;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.administrator.AdministratorDao;
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
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminLogicTest {

    @Autowired
    AdminLogic adminLogic;

    @MockBean
    AdministratorDao adminDao;

    @Test
    @Order(1)
    public void register() {

        doReturn(1).when(adminDao).getAdministratorSeqNextVal();
        doReturn(1).when(adminDao).insert(any(AdministratorEntity.class));

        AdministratorEntity entity = new AdministratorEntity();
        entity.setShopSeq(999);
        adminLogic.register(entity);

        verify(adminDao, times(1)).getAdministratorSeqNextVal();
        verify(adminDao, times(1)).insert(any(AdministratorEntity.class));
    }

    @Test
    @Order(2)
    public void updateFailureCount() {
        doReturn(3).when(adminDao)
                   .updateLoginFailureCount(any(String.class), any(Integer.class), any(), any(Timestamp.class));

        int result = adminLogic.updateFailureCount("1", 1);

        verify(adminDao, times(1)).updateLoginFailureCount(
                        any(String.class), any(Integer.class), any(), any(Timestamp.class));
        Assertions.assertEquals(3, result);
    }
}
