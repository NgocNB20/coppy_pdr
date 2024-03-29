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
public class DeliveryMethodRegistLogicTest {

    @Autowired
    DeliveryMethodRegistLogic deliveryMethodRegistLogic;

    @MockBean
    DeliveryMethodDao deliveryMethodDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        int result = 1;

        // モック設定
        doReturn(result).when(deliveryMethodDao).insert(any(DeliveryMethodEntity.class));

        // 試験実行
        DeliveryMethodEntity deliveryMethodEntity = new DeliveryMethodEntity();
        deliveryMethodEntity.setDeliveryMethodSeq(0);
        deliveryMethodEntity.setShopSeq(0);
        deliveryMethodEntity.setDeliveryMethodName("");
        deliveryMethodEntity.setDeliveryMethodDisplayNamePC("");
        deliveryMethodEntity.setDeliveryMethodDisplayNameMB("");
        deliveryMethodEntity.setOpenStatusPC(HTypeOpenDeleteStatus.NO_OPEN);
        deliveryMethodEntity.setOpenStatusMB(HTypeOpenDeleteStatus.NO_OPEN);
        deliveryMethodEntity.setDeliveryNotePC("");
        deliveryMethodEntity.setDeliveryNoteMB("");
        deliveryMethodEntity.setDeliveryMethodType(HTypeDeliveryMethodType.FLAT);
        deliveryMethodEntity.setEqualsCarriage(new BigDecimal("0"));
        deliveryMethodEntity.setLargeAmountDiscountPrice(new BigDecimal("0"));
        deliveryMethodEntity.setLargeAmountDiscountCarriage(new BigDecimal("0"));
        deliveryMethodEntity.setShortfallDisplayFlag(HTypeShortfallDisplayFlag.OFF);
        deliveryMethodEntity.setDeliveryLeadTime(0);
        deliveryMethodEntity.setDeliveryChaseURL("");
        deliveryMethodEntity.setDeliveryChaseURLDisplayPeriod(new BigDecimal("0"));
        deliveryMethodEntity.setPossibleSelectDays(0);
        deliveryMethodEntity.setReceiverTimeZone1("");
        deliveryMethodEntity.setReceiverTimeZone2("");
        deliveryMethodEntity.setReceiverTimeZone3("");
        deliveryMethodEntity.setReceiverTimeZone4("");
        deliveryMethodEntity.setReceiverTimeZone5("");
        deliveryMethodEntity.setReceiverTimeZone6("");
        deliveryMethodEntity.setReceiverTimeZone7("");
        deliveryMethodEntity.setReceiverTimeZone8("");
        deliveryMethodEntity.setReceiverTimeZone9("");
        deliveryMethodEntity.setReceiverTimeZone10("");
        deliveryMethodEntity.setOrderDisplay(0);
        deliveryMethodEntity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        deliveryMethodEntity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        int actual = deliveryMethodRegistLogic.execute(deliveryMethodEntity);

        // 戻り値及び呼び出し検証
        verify(deliveryMethodDao, times(1)).insert(any(DeliveryMethodEntity.class));
        Assertions.assertEquals(result, actual);
    }
}
