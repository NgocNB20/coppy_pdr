package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment;

import java.util.List;

import com.gmo_pg.g_pay.client.output.SaveCardOutput;
import com.gmo_pg.g_pay.client.output.SaveMemberOutput;
import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.CardDto;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.SaveCardLogic;
import jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate.SaveMemberLogic;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CardRegistUpdateLogicTest {

    @Autowired
    CardRegistUpdateLogic cardRegistUpdateLogic;

    @MockBean
    SaveMemberLogic saveMemberLogic;

    @MockBean
    SaveCardLogic saveCardLogic;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        SaveMemberOutput saveMemberOutput = new SaveMemberOutput();
        saveMemberOutput.setMemberId("1");
        saveMemberOutput.setCsvResponse("1");

        SaveCardOutput saveCardOutput = new SaveCardOutput();
        saveCardOutput.setCardSeq(0);
        saveCardOutput.setCardNo("");

        // モック設定
        doReturn(saveMemberOutput).when(saveMemberLogic).execute(any(CardDto.class));
        doReturn(saveCardOutput).when(saveCardLogic).execute(any(CardDto.class));

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

        cardRegistUpdateLogic.execute(cardDto);

        // 戻り値及び呼び出し検証
        verify(saveMemberLogic, times(1)).execute(any(CardDto.class));
        verify(saveCardLogic, times(1)).execute(any(CardDto.class));
    }
}
