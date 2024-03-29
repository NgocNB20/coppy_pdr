package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import com.gmo_pg.g_pay.client.output.SaveCardOutput;
import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CardDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmSaveCardInput;
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
public class SaveCardLogicTest {

    @Autowired
    SaveCardLogic saveCardLogic;

    @MockBean
    PaymentClient paymentClient;

    @Test
    @Order(1)
    public void execute() throws PaymentException {

        // 想定値設定
        SaveCardOutput result = new SaveCardOutput();
        List<String> errList = new ArrayList<>();
        errList.add("1");
        result.setErrList(errList);

        // モック設定
        doReturn(result).when(paymentClient).doSaveCard(any(HmSaveCardInput.class));

        // 試験実行
        CardDto cardDto = new CardDto();
        cardDto.setMemberInfoSeq(0);
        cardDto.setPaymentMemberId("");
        cardDto.setCardNumber("");
        cardDto.setExpirationDate("");
        cardDto.setSecurityCode("");
        cardDto.setOrderSeq(0);
        cardDto.setOrderCode("");
        cardDto.setOrderVersionNo(0);
        cardDto.setRegistCredit(false);
        cardDto.setUseRegistCardFlg(false);
        cardDto.setToken("");

        SaveCardOutput actual = saveCardLogic.execute(cardDto);

        // 戻り値及び呼び出し検証
        verify(paymentClient, times(1)).doSaveCard(any(HmSaveCardInput.class));
        Assertions.assertEquals(result, actual);
    }
}
