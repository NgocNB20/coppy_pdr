package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CommunicateResult;
import jp.co.hankyuhanshin.itec.hmbase.utility.ApplicationContextUtility;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SettlememtMismatchCheckLogicTest {

    @Autowired
    SettlememtMismatchCheckLogic settlememtMismatchCheckLogic;

    @Test
    @Order(1)
    public void execute() {
        CommunicateResult communicateResult = ApplicationContextUtility.getBean(CommunicateResult.class);
        List<String[]> tranList = new ArrayList<>();
        String[] tran = new String[] {"true", "true", "true"};
        tranList.add(tran);
        communicateResult.setTranList(tranList);

        settlememtMismatchCheckLogic.execute(new Throwable());
    }
}
