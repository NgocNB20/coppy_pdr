package jp.co.hankyuhanshin.itec.hitmall.logic.multipayment.communicate;

import java.sql.Timestamp;

import com.gmo_pg.g_pay.client.PaymentClient;
import com.gmo_pg.g_pay.client.common.PaymentException;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAmazonPaymentConfirmStatus;

import com.gmo_pg.g_pay.client.output.ChangeTranOutput;
import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dto.multipayment.HmChangeTranInput;
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

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreditChangeTranLogicTest {

    @Autowired
    CreditChangeTranLogic creditChangeTranLogic;

    @MockBean
    PaymentClient paymentClient;

    @Test
    @Order(1)
    public void execute() throws PaymentException {

        // 想定値設定
        ChangeTranOutput result = new ChangeTranOutput();
        result.setAccessId("1");

        // モック設定
        doReturn(result).when(paymentClient).doChangeTran(any(HmChangeTranInput.class));

        // 試験実行
        MulPayBillEntity mulPayBillEntity = new MulPayBillEntity();
        mulPayBillEntity.setMulPayBillSeq(0);
        mulPayBillEntity.setPayType("");
        mulPayBillEntity.setTranType("");
        mulPayBillEntity.setOrderSeq(0);
        mulPayBillEntity.setOrderVersionNo(0);
        mulPayBillEntity.setOrderId("");
        mulPayBillEntity.setAccessId("");
        mulPayBillEntity.setAccessPass("");
        mulPayBillEntity.setJobCd("");
        mulPayBillEntity.setMethod("");
        mulPayBillEntity.setPayTimes(0);
        mulPayBillEntity.setSeqMode("");
        mulPayBillEntity.setCardSeq(0);
        mulPayBillEntity.setAmount(new BigDecimal("0"));
        mulPayBillEntity.setTax(new BigDecimal("0"));
        mulPayBillEntity.setTdFlag("");
        mulPayBillEntity.setAcs("");
        mulPayBillEntity.setForward("");
        mulPayBillEntity.setApprove("");
        mulPayBillEntity.setTranId("");
        mulPayBillEntity.setTranDate("");
        mulPayBillEntity.setConvenience("");
        mulPayBillEntity.setConfNo("");
        mulPayBillEntity.setReceiptNo("");
        mulPayBillEntity.setPaymentTerm("");
        mulPayBillEntity.setCustId("");
        mulPayBillEntity.setBkCode("");
        mulPayBillEntity.setEncryptReceiptNo("");
        mulPayBillEntity.setMailAddress("");
        mulPayBillEntity.setEdyOrderNo("");
        mulPayBillEntity.setSuicaOrderNo("");
        mulPayBillEntity.setErrCode("");
        mulPayBillEntity.setErrInfo("");
        mulPayBillEntity.setPaymentURL("");
        mulPayBillEntity.setCancelAmount(new BigDecimal("0"));
        mulPayBillEntity.setCancelTax(new BigDecimal("0"));
        mulPayBillEntity.setAmazonOrderReferenceID("");
        mulPayBillEntity.setAmazonPaymentConfirmStatus(HTypeAmazonPaymentConfirmStatus.NOT_PAYMENT);
        mulPayBillEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        mulPayBillEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        mulPayBillEntity.setJobCd("AUTH");

        ChangeTranOutput actual = creditChangeTranLogic.execute(mulPayBillEntity, new BigDecimal(1));

        // 戻り値及び呼び出し検証
        verify(paymentClient, times(1)).doChangeTran(any(HmChangeTranInput.class));
        Assertions.assertEquals(result, actual);
    }
}
