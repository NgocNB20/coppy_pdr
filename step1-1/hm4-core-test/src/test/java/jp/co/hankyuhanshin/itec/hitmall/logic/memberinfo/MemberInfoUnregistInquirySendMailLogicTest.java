package jp.co.hankyuhanshin.itec.hitmall.logic.memberinfo;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberInfoUnregistInquirySendMailLogicTest {

    @Autowired
    MemberInfoUnregistInquirySendMailLogic memberInfoUnregistInquirySendMailLogic;

    @Test
    void executeSendMail() {
        // Setup data
        String customerNo = "10000000";
        String memberInfoTel = "0123456789";

        try (MockedStatic<PropertiesUtil> propertiesUtil = Mockito.mockStatic(PropertiesUtil.class)) {
            // Mock
            propertiesUtil.when(() -> PropertiesUtil.getSystemPropertiesValue("admin.send.mail.flg")).thenReturn("1");

            // Verify
            Assertions.assertDoesNotThrow(
                            () -> memberInfoUnregistInquirySendMailLogic.execute(customerNo, memberInfoTel));
        }
    }

    @Test
    void executeNotSendMail() {
        // Setup data
        String customerNo = "10000000";
        String memberInfoTel = "0123456789";

        try (MockedStatic<PropertiesUtil> propertiesUtil = Mockito.mockStatic(PropertiesUtil.class)) {
            // Mock
            propertiesUtil.when(() -> PropertiesUtil.getSystemPropertiesValue("admin.send.mail.flg")).thenReturn("0");

            // Verify
            Assertions.assertDoesNotThrow(
                            () -> memberInfoUnregistInquirySendMailLogic.execute(customerNo, memberInfoTel));
        }
    }
}
