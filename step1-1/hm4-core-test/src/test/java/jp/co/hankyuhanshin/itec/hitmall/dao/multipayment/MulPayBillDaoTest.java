package jp.co.hankyuhanshin.itec.hitmall.dao.multipayment;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeAmazonPaymentConfirmStatus;
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

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MulPayBillDaoTest {

    @Autowired
    MulPayBillDaoMock mulPayBillDaoMock;

    @Autowired
    MulPayBillDao mulPayBillDao;

    @Test
    @Order(1)
    public void insert() {
        MulPayBillEntity entity = makeMulPayBillEntity();
        int result = mulPayBillDao.insert(entity);
        Assertions.assertNotNull(mulPayBillDao.getMulPayBill(99, 99));
    }

    @Test
    @Order(2)
    public void delete() {
        int result = mulPayBillDaoMock.delete(mulPayBillDao.getMulPayBill(99, 99));
        Assertions.assertNull(mulPayBillDao.getMulPayBill(99, 99));
    }

    private MulPayBillEntity makeMulPayBillEntity() {
        MulPayBillEntity entity = new MulPayBillEntity();
        entity.setMulPayBillSeq(999);
        entity.setPayType("1");
        entity.setTranType("1");
        entity.setOrderSeq(99);
        entity.setOrderVersionNo(99);
        entity.setOrderId("1");
        entity.setAccessId("1");
        entity.setAccessPass("1");
        entity.setJobCd("1");
        entity.setMethod("1");
        entity.setPayTimes(9);
        entity.setSeqMode("1");
        entity.setCardSeq(9);
        entity.setAmount(new BigDecimal(1));
        entity.setTax(new BigDecimal(1));
        entity.setTdFlag("1");
        entity.setAcs("1");
        entity.setForward("1");
        entity.setApprove("1");
        entity.setTranId("1");
        entity.setTranDate("1");
        entity.setConvenience("1");
        entity.setConfNo("1");
        entity.setReceiptNo("1");
        entity.setPaymentTerm("1");
        entity.setCustId("1");
        entity.setBkCode("1");
        entity.setEncryptReceiptNo("1");
        entity.setMailAddress("1");
        entity.setEdyOrderNo("1");
        entity.setSuicaOrderNo("1");
        entity.setErrCode("1");
        entity.setErrInfo("1");
        entity.setPaymentURL("1");
        entity.setCancelAmount(new BigDecimal(1));
        entity.setCancelTax(new BigDecimal(1));
        entity.setAmazonOrderReferenceID("1");
        entity.setAmazonPaymentConfirmStatus(HTypeAmazonPaymentConfirmStatus.NOT_PAYMENT);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
