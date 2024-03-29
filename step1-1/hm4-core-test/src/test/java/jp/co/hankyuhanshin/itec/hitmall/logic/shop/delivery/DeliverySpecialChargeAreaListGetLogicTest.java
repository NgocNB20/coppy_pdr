package jp.co.hankyuhanshin.itec.hitmall.logic.shop.delivery;

import java.math.BigDecimal;

import jp.co.hankyuhanshin.itec.hitmall.TestApplication;
import jp.co.hankyuhanshin.itec.hitmall.dao.shop.delivery.DeliverySpecialChargeAreaDao;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySpecialChargeAreaConditionDto;
import jp.co.hankyuhanshin.itec.hitmall.dto.shop.delivery.DeliverySpecialChargeAreaResultDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.seasar.doma.jdbc.SelectOptions;
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
public class DeliverySpecialChargeAreaListGetLogicTest {

    @Autowired
    DeliverySpecialChargeAreaListGetLogic deliverySpecialChargeAreaListGetLogic;

    @MockBean
    DeliverySpecialChargeAreaDao deliverySpecialChargeAreaDao;

    @Test
    @Order(1)
    public void execute() {

        // 想定値設定
        List<DeliverySpecialChargeAreaResultDto> result = new ArrayList<>();

        // モック設定
        doReturn(result).when(deliverySpecialChargeAreaDao)
                        .getDeliverySpecialChargeAreaListInAddress(any(DeliverySpecialChargeAreaConditionDto.class),
                                                                   any(SelectOptions.class)
                                                                  );

        // 試験実行
        DeliverySpecialChargeAreaConditionDto conditionDto = new DeliverySpecialChargeAreaConditionDto();
        conditionDto.setDeliveryMethodSeq(1);
        conditionDto.setZipcode("1");
        conditionDto.setCarriage(new BigDecimal("0"));
        conditionDto.setPrefecture("1");

        List<DeliverySpecialChargeAreaResultDto> actual = deliverySpecialChargeAreaListGetLogic.execute(conditionDto);

        // 戻り値及び呼び出し検証
        verify(deliverySpecialChargeAreaDao, times(1)).getDeliverySpecialChargeAreaListInAddress(
                        any(DeliverySpecialChargeAreaConditionDto.class), any(SelectOptions.class));
        Assertions.assertEquals(result, actual);
    }
}
