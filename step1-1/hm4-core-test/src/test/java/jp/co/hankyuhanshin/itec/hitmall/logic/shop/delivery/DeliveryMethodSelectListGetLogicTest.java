package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeShortfallDisplayFlag;

import java.math.BigDecimal;

import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypePrefectureType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeDeliveryMethodType;
import jp.co.hankyuhanshin.itec.hitmall.constant.type.HTypeOpenDeleteStatus;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliveryMethodDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryDetailsDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliveryDto;
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
public class DeliveryMethodSelectListGetLogicTest {

    @Autowired
    DeliveryMethodSelectListGetLogic deliveryMethodSelectListGetLogic;

    @MockBean
    DeliveryMethodDao deliveryMethodDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        List<DeliveryDetailsDto> result = new ArrayList<>();
        DeliveryDetailsDto dto = new DeliveryDetailsDto();
        dto.setDeliveryMethodSeq(0);
        dto.setShopSeq(0);
        dto.setDeliveryMethodName("");
        dto.setDeliveryMethodDisplayNamePC("");
        dto.setDeliveryMethodDisplayNameMB("");
        dto.setOpenStatusPC(HTypeOpenDeleteStatus.NO_OPEN);
        dto.setOpenStatusMB(HTypeOpenDeleteStatus.NO_OPEN);
        dto.setDeliveryNotePC("");
        dto.setDeliveryNoteMB("");
        dto.setDeliveryMethodType(HTypeDeliveryMethodType.FLAT);
        dto.setEqualsCarriage(new BigDecimal("0"));
        dto.setLargeAmountDiscountPrice(new BigDecimal("0"));
        dto.setLargeAmountDiscountCarriage(new BigDecimal("0"));
        dto.setShortfallDisplayFlag(HTypeShortfallDisplayFlag.OFF);
        dto.setDeliveryLeadTime(0);
        dto.setPossibleSelectDays(0);
        dto.setReceiverTimeZone1("");
        dto.setReceiverTimeZone2("");
        dto.setReceiverTimeZone3("");
        dto.setReceiverTimeZone4("");
        dto.setReceiverTimeZone5("");
        dto.setReceiverTimeZone6("");
        dto.setReceiverTimeZone7("");
        dto.setReceiverTimeZone8("");
        dto.setReceiverTimeZone9("");
        dto.setReceiverTimeZone10("");
        dto.setOrderDisplay(0);
        dto.setPrefectureType(HTypePrefectureType.HOKKAIDO);
        dto.setMaxPrice(new BigDecimal("0"));
        dto.setCarriage(new BigDecimal("0"));

        result.add(dto);

        // モック設定
        doReturn(result).when(deliveryMethodDao).getDeliveryMethodListForLp(any(List.class), any(Integer.class));

        // 試験実行
        List<Integer> deliveryMethodSeqList = new ArrayList<>();
        deliveryMethodSeqList.add(1);
        List<DeliveryDto> actual = deliveryMethodSelectListGetLogic.execute(deliveryMethodSeqList);

        // 戻り値及び呼び出し検証
        verify(deliveryMethodDao, times(1)).getDeliveryMethodListForLp(any(List.class), any(Integer.class));
        Assertions.assertEquals(result.get(0), actual.get(0).getDeliveryDetailsDto());
    }
}
