package jp.co.hankyuhanshin.itec.hitmall.logic.order;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.service.mail.MailSendService;
import jp.co.hankyuhanshin.itec.hmbase.util.common.PropertiesUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderRegistFailureAddressBookSendMailLogicTest {

    @Autowired
    OrderRegistFailureAddressBookSendMailLogic orderRegistFailureAddressBookSendMailLogic;

    @MockBean
    MailSendService mailSendService;

    @Mock
    Environment environment;

    @BeforeEach
    void setMockOutput() {
        when(environment.getProperty(anyString())).thenReturn("");
    }

    @Test
    @Order(1)
    public void execute() {

        try (MockedStatic<PropertiesUtil> propertiesUtil = Mockito.mockStatic(PropertiesUtil.class)) {
            // Mock
            propertiesUtil.when(() -> PropertiesUtil.getSystemPropertiesValue("admin.send.mail.flg")).thenReturn("1");
            propertiesUtil.when(() -> PropertiesUtil.getSystemPropertiesValue("mail_server")).thenReturn("localhost");
            propertiesUtil.when(() -> PropertiesUtil.getSystemPropertiesValue("mail_from"))
                          .thenReturn("info+hitmall4@itec.hankyu-hanshin.co.jp");
            propertiesUtil.when(() -> PropertiesUtil.getSystemPropertiesValue("template_file"))
                          .thenReturn("/OrderRegistFailureAddressBookAlert.mail");
            propertiesUtil.when(() -> PropertiesUtil.getSystemPropertiesValue("recipient"))
                          .thenReturn("XXXXX@itec.hankyu-hanshin.co.jp");

            // Verify
            orderRegistFailureAddressBookSendMailLogic.execute(10149571);
        }

    }

}
