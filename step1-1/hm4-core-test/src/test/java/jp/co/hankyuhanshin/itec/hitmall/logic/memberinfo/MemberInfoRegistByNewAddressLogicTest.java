package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.entity.memberinfo.MemberInfoEntity;
import jp.co.hankyuhanshin.itec.hmbase.exception.AppLevelListException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberInfoRegistByNewAddressLogicTest {

    @Autowired
    MemberInfoRegistByNewAddressLogic memberInfoRegistByNewAddressLogic;

    @MockBean
    MemberInfoRegistLogic memberInfoRegistLogic;

    @MockBean
    MemberInfoGetLogic memberInfoGetLogic;

    @Test
    @Order(1)
    void executeSuccess() {
        // Setup Data
        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        memberInfoEntity.setMemberInfoTel("0123456789");
        memberInfoEntity.setCustomerNo(10000000);

        // Mock
        doReturn(1).when(memberInfoRegistLogic).execute(memberInfoEntity);
        doReturn(memberInfoEntity).when(memberInfoGetLogic)
                                  .getMemberInfoEntityByMemberInfoTelAndCustomerNo(memberInfoEntity.getMemberInfoTel(),
                                                                                   memberInfoEntity.getCustomerNo()
                                                                                                   .toString()
                                                                                  );

        // Verify
        assertEquals(10000000, memberInfoRegistByNewAddressLogic.execute(memberInfoEntity));
    }

    @Test
    @Order(2)
    void executeRegistFail() {
        // Setup Data
        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        memberInfoEntity.setMemberInfoTel("0123456789");
        memberInfoEntity.setCustomerNo(10000000);

        // Mock
        doReturn(0).when(memberInfoRegistLogic).execute(memberInfoEntity);

        // Verify
        Throwable throwable = assertThrows(Throwable.class, () -> {
            memberInfoRegistByNewAddressLogic.execute(memberInfoEntity);
        });
        assertEquals(AppLevelListException.class, throwable.getClass());
    }

    @Test
    @Order(3)
    void executeGetInfoRegistFail() {
        // Setup data
        MemberInfoEntity memberInfoEntity = new MemberInfoEntity();
        memberInfoEntity.setMemberInfoTel("0123456789");
        memberInfoEntity.setCustomerNo(10000000);

        // Mock
        doReturn(1).when(memberInfoRegistLogic).execute(memberInfoEntity);
        doReturn(null).when(memberInfoGetLogic)
                      .getMemberInfoEntityByMemberInfoTelAndCustomerNo(memberInfoEntity.getMemberInfoTel(),
                                                                       memberInfoEntity.getCustomerNo().toString()
                                                                      );

        // Verify
        Throwable throwable = assertThrows(Throwable.class, () -> {
            memberInfoRegistByNewAddressLogic.execute(memberInfoEntity);
        });
        assertEquals(AppLevelListException.class, throwable.getClass());
    }
}
