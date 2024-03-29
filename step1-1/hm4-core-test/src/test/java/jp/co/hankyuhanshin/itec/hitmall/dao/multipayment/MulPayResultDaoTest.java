package jp.co.hankyuhanshin.itec.hitmall.dao.multipayment;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeProcessedFlag;
import jp.co.hankyuhanshin.itec.hitmall.entity.multipayment.MulPayResultEntity;
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
public class MulPayResultDaoTest {

    @Autowired
    MulPayResultDaoMock mulPayResultDaoMock;

    @Autowired
    MulPayResultDao mulPayResultDao;

    @Test
    @Order(1)
    public void insert() {
        MulPayResultEntity entity = makeMulPayResultEntity();
        int result = mulPayResultDao.insert(entity);
        List<MulPayResultEntity> entityList = mulPayResultDao.getUnprocessedPaySuccessEntityList(99);
        Assertions.assertEquals(1, entityList.size());
        for (MulPayResultEntity del : entityList) {
            result = mulPayResultDaoMock.delete(del);
        }
    }

    private MulPayResultEntity makeMulPayResultEntity() {
        MulPayResultEntity entity = new MulPayResultEntity();
        entity.setMulPayResultSeq(99);
        entity.setReceiveMode("1");
        entity.setProcessedFlag(HTypeProcessedFlag.PROCESSING_REQUIRED);
        entity.setShopSeq(99);
        entity.setOrderSeq(99);
        entity.setOrderId("1");
        entity.setStatus("1");
        entity.setJobCd("1");
        entity.setProcessDate("1");
        entity.setItemCode("1");
        entity.setAmount(new BigDecimal(1));
        entity.setTax(new BigDecimal(1));
        entity.setSiteId("1");
        entity.setMemberId("1");
        entity.setCardNo("1");
        entity.setExpire("1");
        entity.setCurrency("1");
        entity.setForward("1");
        entity.setMethod("1");
        entity.setPayTimes(1);
        entity.setTranId("1");
        entity.setApprove("1");
        entity.setTranDate("1");
        entity.setErrCode("1");
        entity.setErrInfo("1");
        entity.setClientField1("1");
        entity.setClientField2("1");
        entity.setClientField3("1");
        entity.setPayType("1");
        entity.setCvsCode("1");
        entity.setCvsConfNo("1");
        entity.setCvsReceiptNo("1");
        entity.setEdyReceiptNo("1");
        entity.setEdyOrderNo("1");
        entity.setSuicaReceiptNo("1");
        entity.setSuicaOrderNo("1");
        entity.setCustId("1");
        entity.setBkCode("1");
        entity.setConfNo("1");
        entity.setPaymentTerm("1");
        entity.setEncryptReceiptNo("1");
        entity.setFinishDate("1");
        entity.setReceiptDate("1");
        entity.setCancelAmount(new BigDecimal("1"));
        entity.setCancelTax(new BigDecimal("1"));
        entity.setAmazonOrderReferenceID("1");
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));
        return entity;
    }
}
