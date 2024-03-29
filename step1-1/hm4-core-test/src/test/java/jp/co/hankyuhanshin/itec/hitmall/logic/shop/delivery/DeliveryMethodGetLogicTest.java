package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShortfallDisplayFlag;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryMethodDao;
import jp.co.hankyuhanshin.itec.hitmall.entity.shop.delivery.DeliveryMethodEntity;
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
public class DeliveryMethodGetLogicTest {

    @Autowired
    DeliveryMethodGetLogic deliveryMethodGetLogic;

    @MockBean
    DeliveryMethodDao deliveryMethodDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        DeliveryMethodEntity result = new DeliveryMethodEntity();
        result.setDeliveryMethodSeq(0);
        result.setShopSeq(0);
        result.setDeliveryMethodName("");
        result.setDeliveryMethodDisplayNamePC("");
        result.setDeliveryMethodDisplayNameMB("");
        result.setOpenStatusPC(HTypeOpenDeleteStatus.NO_OPEN);
        result.setOpenStatusMB(HTypeOpenDeleteStatus.NO_OPEN);
        result.setDeliveryNotePC("");
        result.setDeliveryNoteMB("");
        result.setDeliveryMethodType(HTypeDeliveryMethodType.FLAT);
        result.setEqualsCarriage(new BigDecimal("0"));
        result.setLargeAmountDiscountPrice(new BigDecimal("0"));
        result.setLargeAmountDiscountCarriage(new BigDecimal("0"));
        result.setShortfallDisplayFlag(HTypeShortfallDisplayFlag.OFF);
        result.setDeliveryLeadTime(0);
        result.setDeliveryChaseURL("");
        result.setDeliveryChaseURLDisplayPeriod(new BigDecimal("0"));
        result.setPossibleSelectDays(0);
        result.setReceiverTimeZone1("");
        result.setReceiverTimeZone2("");
        result.setReceiverTimeZone3("");
        result.setReceiverTimeZone4("");
        result.setReceiverTimeZone5("");
        result.setReceiverTimeZone6("");
        result.setReceiverTimeZone7("");
        result.setReceiverTimeZone8("");
        result.setReceiverTimeZone9("");
        result.setReceiverTimeZone10("");
        result.setOrderDisplay(0);
        result.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        result.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        // モック設定
        doReturn(result).when(deliveryMethodDao).getEntity(any(Integer.class));

        // 試験実行
        DeliveryMethodEntity actual = deliveryMethodGetLogic.execute(1);

        // 戻り値及び呼び出し検証
        verify(deliveryMethodDao, times(1)).getEntity(any(Integer.class));
        Assertions.assertEquals(result, actual);
    }
}
