package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import com.gmo_pg.g_pay.client.output.SearchCardOutput;
import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmSearchCardInput;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SearchCardLogicTest {

    @Autowired
    SearchCardLogic searchCardLogic;

    @MockBean
    PaymentClient paymentClient;

    @Test
    @Order(1)
    public void execute() throws PaymentException {

        // 想定値設定
        SearchCardOutput result = new SearchCardOutput();
        result.setCsvResponse("ok");

        // モック設定
        doReturn(result).when(paymentClient).doSearchCard(any(HmSearchCardInput.class));

        // 試験実行
        SearchCardOutput actual = searchCardLogic.execute("test");

        // 戻り値及び呼び出し検証
        verify(paymentClient, times(1)).doSearchCard(any(HmSearchCardInput.class));
        Assertions.assertEquals(result, actual);
    }
}
