package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.multipayment.MulPayBillDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayBillEntity;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MulPayBillLogicTest {

    @Autowired
    MulPayBillLogic mulPayBillLogic;

    @MockBean
    MulPayBillDao mulPayBillDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        MulPayBillEntity result = new MulPayBillEntity();
        result.setMulPayBillSeq(1);
        result.setPayType("1");
        result.setTranType("1");
        result.setOrderSeq(1);

        // モック設定
        doReturn(result).when(mulPayBillDao).getLatestEntityByOrderCodeWithPrefix(any(String.class));

        // 試験実行
        MulPayBillEntity actual = mulPayBillLogic.getMulPayBillByOrderCode("1");

        // 戻り値及び呼び出し検証
        verify(mulPayBillDao, times(1)).getLatestEntityByOrderCodeWithPrefix(any(String.class));
        Assertions.assertEquals(result, actual);
    }
}
