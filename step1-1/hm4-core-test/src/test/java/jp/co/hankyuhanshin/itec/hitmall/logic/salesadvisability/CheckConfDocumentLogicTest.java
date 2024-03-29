package jp.co.hankyuhanshin.itec.hitmall.logic.salesadvisability;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
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

/**
 * Logic/Service移行：カート管理のリハーサル
 * 作成日：2021/02/26
 *
 * @author Phan Tien VU (VTI Japan Co., Ltd.)
 */
@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CheckConfDocumentLogicTest {

    @Autowired
    CheckConfDocumentLogic checkConfDocumentLogic;

    @Test
    @Order(1)
    public void executeTest() {

        boolean result = checkConfDocumentLogic.execute(null, null);
        Assertions.assertEquals(false, result);
    }

    @Test
    @Order(1)
    public void executeTest2() {

        boolean result = checkConfDocumentLogic.execute("1", "1");
        Assertions.assertEquals(true, result);
    }
}
