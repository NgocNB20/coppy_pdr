package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliverySpecialChargeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliverySpecialChargeAreaEntity;
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
public class DeliverySpecialChargeAreaRegistLogicTest {

    @Autowired
    DeliverySpecialChargeAreaRegistLogic deliverySpecialChargeAreaRegistLogic;

    @MockBean
    DeliverySpecialChargeAreaDao deliverySpecialChargeAreaDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        int result = 1;

        // モック設定
        doReturn(result).when(deliverySpecialChargeAreaDao).insert(any(DeliverySpecialChargeAreaEntity.class));

        // 試験実行
        DeliverySpecialChargeAreaEntity entity = new DeliverySpecialChargeAreaEntity();
        entity.setDeliveryMethodSeq(1);
        entity.setZipCode("1");
        entity.setCarriage(new BigDecimal("0"));
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        int actual = deliverySpecialChargeAreaRegistLogic.execute(entity);

        // 戻り値及び呼び出し検証
        verify(deliverySpecialChargeAreaDao, times(1)).insert(any(DeliverySpecialChargeAreaEntity.class));
        Assertions.assertEquals(result, actual);
    }
}
