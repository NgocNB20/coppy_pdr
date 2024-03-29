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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableAutoConfiguration(
                exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@SpringBootTest(classes = TestApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeliveryMethodListGetByShopSeqLogicTest {

    @Autowired
    DeliveryMethodListGetByShopSeqLogic deliveryMethodListGetByShopSeqLogic;

    @MockBean
    DeliveryMethodDao deliveryMethodDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        List<DeliveryMethodEntity> result = new ArrayList<>();
        DeliveryMethodEntity entity = new DeliveryMethodEntity();
        entity.setDeliveryMethodSeq(0);
        entity.setShopSeq(0);
        entity.setDeliveryMethodName("");
        entity.setDeliveryMethodDisplayNamePC("");
        entity.setDeliveryMethodDisplayNameMB("");
        entity.setOpenStatusPC(HTypeOpenDeleteStatus.NO_OPEN);
        entity.setOpenStatusMB(HTypeOpenDeleteStatus.NO_OPEN);
        entity.setDeliveryNotePC("");
        entity.setDeliveryNoteMB("");
        entity.setDeliveryMethodType(HTypeDeliveryMethodType.FLAT);
        entity.setEqualsCarriage(new BigDecimal("0"));
        entity.setLargeAmountDiscountPrice(new BigDecimal("0"));
        entity.setLargeAmountDiscountCarriage(new BigDecimal("0"));
        entity.setShortfallDisplayFlag(HTypeShortfallDisplayFlag.OFF);
        entity.setDeliveryLeadTime(0);
        entity.setDeliveryChaseURL("");
        entity.setDeliveryChaseURLDisplayPeriod(new BigDecimal("0"));
        entity.setPossibleSelectDays(0);
        entity.setReceiverTimeZone1("");
        entity.setReceiverTimeZone2("");
        entity.setReceiverTimeZone3("");
        entity.setReceiverTimeZone4("");
        entity.setReceiverTimeZone5("");
        entity.setReceiverTimeZone6("");
        entity.setReceiverTimeZone7("");
        entity.setReceiverTimeZone8("");
        entity.setReceiverTimeZone9("");
        entity.setReceiverTimeZone10("");
        entity.setOrderDisplay(0);
        entity.setRegistTime(new Timestamp(new java.util.Date().getTime()));
        entity.setUpdateTime(new Timestamp(new java.util.Date().getTime()));

        result.add(entity);

        // モック設定
        doReturn(result).when(deliveryMethodDao).getDeliveryMethodList(any(Integer.class));

        // 試験実行
        List<DeliveryMethodEntity> actual = deliveryMethodListGetByShopSeqLogic.execute(1);

        // 戻り値及び呼び出し検証
        verify(deliveryMethodDao, times(1)).getDeliveryMethodList(any(Integer.class));
        Assertions.assertEquals(result, actual);
    }
}
